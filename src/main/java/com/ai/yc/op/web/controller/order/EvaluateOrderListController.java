package com.ai.yc.op.web.controller.order;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.sdk.components.ccs.CCSClientFactory;
import com.ai.opt.sdk.components.excel.client.AbstractExcelHelper;
import com.ai.opt.sdk.components.excel.factory.ExcelFactory;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.paas.ipaas.ccs.IConfigClient;
import com.ai.yc.common.api.cache.interfaces.ICacheSV;
import com.ai.yc.common.api.cache.param.SysParam;
import com.ai.yc.common.api.cache.param.SysParamSingleCond;
import com.ai.yc.op.web.constant.Constants;
import com.ai.yc.op.web.constant.Constants.ExcelConstants;
import com.ai.yc.op.web.model.order.ExAllOrder;
import com.ai.yc.op.web.model.order.OrderPageQueryParams;
import com.ai.yc.op.web.model.order.OrderPageResParam;
import com.ai.yc.op.web.utils.AmountUtil;
import com.ai.yc.op.web.utils.TimeZoneTimeUtil;
import com.ai.yc.order.api.orderquery.interfaces.IOrderQuerySV;
import com.ai.yc.order.api.orderquery.param.OrdOrderVo;
import com.ai.yc.order.api.orderquery.param.QueryOrderRequest;
import com.ai.yc.order.api.orderquery.param.QueryOrderRsponse;

@Controller
public class EvaluateOrderListController {
	private static final Logger logger = Logger.getLogger(EvaluateOrderListController.class);
	@RequestMapping("/toEvaluateOrderList")
	public ModelAndView toEvaluateOrder(HttpServletRequest request) {
		return new ModelAndView("jsp/order/evaluateOrderList");
	}
	
	/**
     * 订单信息查询
     */
    @RequestMapping("/getEvluateOrdPageData")
    @ResponseBody
    public ResponseData<PageInfo<OrderPageResParam>> getList(HttpServletRequest request,OrderPageQueryParams queryRequest)throws Exception{
    	ResponseData<PageInfo<OrderPageResParam>> responseData = null;
    	List<OrderPageResParam> resultList = new ArrayList<OrderPageResParam>();
    	PageInfo<OrderPageResParam> resultPageInfo  = new PageInfo<OrderPageResParam>();
	    try{
	    	QueryOrderRequest ordReq = new QueryOrderRequest();
	    	BeanUtils.copyProperties(ordReq, queryRequest);
	    	String pgeOrderId = queryRequest.getOrderPageId();
	    	if(!StringUtil.isBlank(pgeOrderId)) {
				boolean isNum = pgeOrderId.matches("[0-9]+");
				if(isNum) {
					ordReq.setOrderId(Long.parseLong(pgeOrderId));
				}else {
					ordReq.setOrderId(0l);
				}
			}
			Long evaluateTimeBegin = queryRequest.getEvaluateTimeS();
			if (evaluateTimeBegin!=null) {
				Timestamp evaluateTimeS = new Timestamp(evaluateTimeBegin);
				ordReq.setEvaluateTimeStart(evaluateTimeS);
			}
			Long evaluateTimeEnd = queryRequest.getEvaluateTimeE();
			if (evaluateTimeEnd!=null) {
				Timestamp evaluateTimeE = new Timestamp(evaluateTimeEnd);
				ordReq.setEvaluateTimeEnd(evaluateTimeE);
			}
			ordReq.setState(Constants.State.EVALUATE_STATE);
			String strPageNo=(null==request.getParameter("pageNo"))?"1":request.getParameter("pageNo");
		    String strPageSize=(null==request.getParameter("pageSize"))?"10":request.getParameter("pageSize");
		    ordReq.setPageNo(Integer.parseInt(strPageNo));
		    ordReq.setPageSize(Integer.parseInt(strPageSize));
		    IOrderQuerySV orderQuerySV = DubboConsumerFactory.getService(IOrderQuerySV.class);
		    ICacheSV iCacheSV = DubboConsumerFactory.getService(ICacheSV.class);
		    QueryOrderRsponse orderListResponse = orderQuerySV.queryOrder(ordReq);
			if (orderListResponse != null && orderListResponse.getResponseHeader().isSuccess()) {
				PageInfo<OrdOrderVo> pageInfo = orderListResponse.getPageInfo();
				BeanUtils.copyProperties(resultPageInfo, pageInfo);
				List<OrdOrderVo> orderList = pageInfo.getResult();
				if(!CollectionUtil.isEmpty(orderList)){
					for(OrdOrderVo vo:orderList){
						OrderPageResParam resParam = new OrderPageResParam();
						BeanUtils.copyProperties(resParam, vo);
						//翻译订单来源
    					SysParamSingleCond	paramCond = new SysParamSingleCond();
    					paramCond.setTenantId(Constants.TENANT_ID);
    					paramCond.setColumnValue(vo.getChlId());
    					paramCond.setTypeCode(Constants.TYPE_CODE);
    					paramCond.setParamCode(Constants.ORD_CHL_ID);
                		SysParam chldParam = iCacheSV.getSysParamSingle(paramCond);
                		if(chldParam!=null){
                			resParam.setChlIdPage(chldParam.getColumnDesc());
                		}
                		//翻译订单级别
                		paramCond = new SysParamSingleCond();
                		paramCond.setTenantId(Constants.TENANT_ID);
    					paramCond.setColumnValue(vo.getOrderLevel());
    					paramCond.setTypeCode(Constants.TYPE_CODE);
    					paramCond.setParamCode(Constants.ORD_ORDER_LEVEL);
                		SysParam levelParam = iCacheSV.getSysParamSingle(paramCond);
                		if(levelParam!=null){
                			resParam.setOrderLevelPage(levelParam.getColumnDesc());
                		}
                		//翻译翻译类型
                		paramCond = new SysParamSingleCond();
                		paramCond.setTenantId(Constants.TENANT_ID);
                		paramCond.setColumnValue(vo.getTranslateType());
        				paramCond.setTypeCode(Constants.TYPE_CODE);
        				paramCond.setParamCode(Constants.ORD_TRANSLATE_TYPE);
                		SysParam orderTypeParam = iCacheSV.getSysParamSingle(paramCond);
                		if(orderTypeParam!=null){
                			resParam.setTranslateTypePage(orderTypeParam.getColumnDesc());
                		}
                		//翻译订单状态
                		paramCond = new SysParamSingleCond();
                		paramCond.setTenantId(Constants.TENANT_ID);
    					paramCond.setColumnValue(vo.getState());
    					paramCond.setTypeCode(Constants.TYPE_CODE);
    					paramCond.setParamCode(Constants.ORD_STATE);
                		SysParam stateParam = iCacheSV.getSysParamSingle(paramCond);
                		if(stateParam!=null){
                			resParam.setStatePage(stateParam.getColumnDesc());
                		}
						resultList.add(resParam);
					}
				}
				resultPageInfo.setResult(resultList);
				responseData = new ResponseData<PageInfo<OrderPageResParam>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功",resultPageInfo);
			} else {
				responseData = new ResponseData<PageInfo<OrderPageResParam>>(ResponseData.AJAX_STATUS_FAILURE, "查询失败", null);
			}
		} catch (Exception e) {
			logger.error("查询已评价订单列表失败：", e);
			responseData = new ResponseData<PageInfo<OrderPageResParam>>(ResponseData.AJAX_STATUS_FAILURE, "查询信息异常", null);
		}
	    return responseData;
    }
    
    /**
     * 订单信息导出
     */
    @RequestMapping("/evaluateExport")
    @ResponseBody
    public void  export(HttpServletRequest request, HttpServletResponse response, OrderPageQueryParams queryRequest) {
    	logger.error("进入导出方法>>>>");
    	QueryOrderRequest ordReq = new QueryOrderRequest();
    	BeanUtils.copyProperties(ordReq, queryRequest);
    	String pgeOrderId = queryRequest.getOrderPageId();
    	if(!StringUtil.isBlank(pgeOrderId)) {
			boolean isNum = pgeOrderId.matches("[0-9]+");
			if(isNum) {
				ordReq.setOrderId(Long.parseLong(pgeOrderId));
			}else {
				ordReq.setOrderId(0l);
			}
		}
		Long evaluateTimeBegin = queryRequest.getEvaluateTimeS();
		if (evaluateTimeBegin!=null) {
			Timestamp evaluateTimeS = new Timestamp(evaluateTimeBegin);
			ordReq.setEvaluateTimeStart(evaluateTimeS);
		}
		Long evaluateTimeEnd = queryRequest.getEvaluateTimeE();
		if (evaluateTimeEnd!=null) {
			Timestamp evaluateTimeE = new Timestamp(evaluateTimeEnd);
			ordReq.setEvaluateTimeEnd(evaluateTimeE);
		}
		ordReq.setState(Constants.State.EVALUATE_STATE);
	    ordReq.setPageNo(1);
	    try {
	  //获取配置中的导出最大数值
	    logger.error("获取导出最大条数配置>>>>");
	    IConfigClient configClient = CCSClientFactory.getDefaultConfigClient();
        String maxRow =  configClient.get(ExcelConstants.EXCEL_OUTPUT_MAX_ROW);
        int excelMaxRow = Integer.valueOf(maxRow);
	    ordReq.setPageSize(excelMaxRow);
	    IOrderQuerySV orderQuerySV = DubboConsumerFactory.getService(IOrderQuerySV.class);
	    ICacheSV iCacheSV = DubboConsumerFactory.getService(ICacheSV.class);
	    logger.error("调用查询方法>>>>");
	    QueryOrderRsponse orderListResponse = orderQuerySV.queryOrder(ordReq);
	    PageInfo<OrdOrderVo> pageInfo = orderListResponse.getPageInfo();
		List<OrdOrderVo> orderList = pageInfo.getResult();
		List<ExAllOrder> exportList = new ArrayList<ExAllOrder>();
		if(!CollectionUtil.isEmpty(orderList)){
			logger.error("查询数据非空进行数据整合>>>>");
			for(OrdOrderVo vo:orderList){
				if(!CollectionUtil.isEmpty(vo.getOrdProdExtendList())){
					for(int i=0;i<vo.getOrdProdExtendList().size();i++){
						ExAllOrder exOrder = new ExAllOrder();
						////翻译订单来源
						SysParamSingleCond	paramCond = new SysParamSingleCond();
						paramCond.setTenantId(Constants.TENANT_ID);
						paramCond.setColumnValue(vo.getChlId());
						paramCond.setTypeCode(Constants.TYPE_CODE);
						paramCond.setParamCode(Constants.ORD_CHL_ID);
		        		SysParam chldParam = iCacheSV.getSysParamSingle(paramCond);
		        		if(chldParam!=null){
		        			exOrder.setChlId(chldParam.getColumnDesc());
		        		}
		        		//翻译订单类型
		        		paramCond = new SysParamSingleCond();
		        		paramCond.setTenantId(Constants.TENANT_ID);
						paramCond.setColumnValue(vo.getTranslateType());
						paramCond.setTypeCode(Constants.TYPE_CODE);
						paramCond.setParamCode(Constants.ORD_TRANSLATE_TYPE);
		        		SysParam orderTypeParam = iCacheSV.getSysParamSingle(paramCond);
		        		if(orderTypeParam!=null){
		        			exOrder.setOrderType(orderTypeParam.getColumnDesc());
		        		}
		        		
		        		//翻译评论状态
		        		paramCond = new SysParamSingleCond();
		        		paramCond.setTenantId(Constants.TENANT_ID);
						paramCond.setColumnValue(vo.getEvaluateState());
						paramCond.setTypeCode(Constants.TYPE_CODE);
						paramCond.setParamCode(Constants.ORD_EVALUATE_STATE);
		        		SysParam stateParam = iCacheSV.getSysParamSingle(paramCond);
		        		if(stateParam!=null){
		        			exOrder.setEvaluateState(stateParam.getColumnDesc());
		        		}
		        		if(vo.getEvaluateTime()!=null){
	            			exOrder.setEvaluateTime(TimeZoneTimeUtil.getTimes(vo.getEvaluateTime()));
	            		}
		        		exOrder.setInterperLevel(vo.getInterLevel());
		        		exOrder.setLspName(vo.getLspName());
		        		exOrder.setUserName(vo.getUserName());
		        		exOrder.setInterperName(vo.getInterperName());
		        		exOrder.setOrderId(vo.getOrderId());
		        		exOrder.setOrderLevel(vo.getOrderLevel());
		        		exOrder.setSpeed(vo.getServeSpeed().toString());
		        		exOrder.setQuality(vo.getServeQuality().toString());
		        		exOrder.setManner(vo.getServeManner().toString());
		        		exOrder.setLangire(vo.getOrdProdExtendList().get(i).getLangungePairChName());
		        		exportList.add(exOrder);
					}
				}else{
					ExAllOrder exOrder = new ExAllOrder();
					////翻译订单来源
					SysParamSingleCond	paramCond = new SysParamSingleCond();
					paramCond.setTenantId(Constants.TENANT_ID);
					paramCond.setColumnValue(vo.getChlId());
					paramCond.setTypeCode(Constants.TYPE_CODE);
					paramCond.setParamCode(Constants.ORD_CHL_ID);
	        		SysParam chldParam = iCacheSV.getSysParamSingle(paramCond);
	        		if(chldParam!=null){
	        			exOrder.setChlId(chldParam.getColumnDesc());
	        		}
	        		exOrder.setParentOrderId(vo.getParentOrderId());
	        		//翻译订单类型
	        		paramCond = new SysParamSingleCond();
	        		paramCond.setTenantId(Constants.TENANT_ID);
	        		paramCond.setColumnValue(vo.getTranslateType());
					paramCond.setTypeCode(Constants.TYPE_CODE);
					paramCond.setParamCode(Constants.ORD_TRANSLATE_TYPE);
	        		SysParam orderTypeParam = iCacheSV.getSysParamSingle(paramCond);
	        		if(orderTypeParam!=null){
	        			exOrder.setOrderType(orderTypeParam.getColumnDesc());
	        		}
	        		
	        		//翻译评论状态
	        		paramCond = new SysParamSingleCond();
	        		paramCond.setTenantId(Constants.TENANT_ID);
					paramCond.setColumnValue(vo.getEvaluateState());
					paramCond.setTypeCode(Constants.TYPE_CODE);
					paramCond.setParamCode(Constants.ORD_EVALUATE_STATE);
	        		SysParam stateParam = iCacheSV.getSysParamSingle(paramCond);
	        		if(stateParam!=null){
	        			exOrder.setEvaluateState(stateParam.getColumnDesc());
	        		}
	        		//
	        		exOrder.setInterperLevel(vo.getInterLevel());
	        		exOrder.setOrderLevel(vo.getOrderLevel());
	        		exOrder.setLspName(vo.getLspName());
	        		exOrder.setInterperName(vo.getInterperName());
            		if(vo.getEvaluateTime()!=null){
            			exOrder.setEvaluateTime(TimeZoneTimeUtil.getTimes(vo.getEvaluateTime()));
            		}
	        		exOrder.setUserName(vo.getUserName());
	        		exOrder.setOrderId(vo.getOrderId());
	        		exOrder.setSpeed(vo.getServeSpeed().toString());
	        		exOrder.setQuality(vo.getServeQuality().toString());
	        		exOrder.setManner(vo.getServeManner().toString());
	        		exportList.add(exOrder);
				}
			}
		}else{
			logger.error("查询数据为空>>>>");
		}
			logger.error("获取输出流>>>>");
			ServletOutputStream outputStream = response.getOutputStream();
			response.reset();// 清空输出流
            response.setContentType("application/msexcel");// 定义输出类型
            response.setHeader("Content-disposition", "attachment; filename=order"+new Date().getTime()+".xls");// 设定输出文件头
            String[] titles = new String[]{"订单来源", "订单类型", "订单编号",  "昵称", "订单级别","语种方向","质量","速度","态度","评论时间","译员昵称","译员级别","lsp名称","状态"};
    		String[] fieldNames = new String[]{"chlId", "orderType", "orderId",
    				"userName", "orderLevel","langire","quality","speed","manner","evaluateTime","interperName","interperLevel","lspName","evaluateState"};
			 AbstractExcelHelper excelHelper = ExcelFactory.getJxlExcelHelper();
			 logger.error("写入数据到excel>>>>");
			 excelHelper.writeExcel(outputStream, "order"+new Date().getTime(), ExAllOrder.class, exportList,fieldNames, titles);
		} catch (Exception e) {
			logger.error("导出订单列表失败："+e.getMessage(), e);
		}
	}

}
