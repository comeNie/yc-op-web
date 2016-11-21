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

import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.components.ccs.CCSClientFactory;
import com.ai.opt.sdk.components.excel.client.AbstractExcelHelper;
import com.ai.opt.sdk.components.excel.factory.ExcelFactory;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.ai.paas.ipaas.ccs.IConfigClient;
import com.ai.yc.common.api.cache.interfaces.ICacheSV;
import com.ai.yc.common.api.cache.param.SysParam;
import com.ai.yc.common.api.cache.param.SysParamSingleCond;
import com.ai.yc.op.web.constant.Constants;
import com.ai.yc.op.web.constant.Constants.ExcelConstants;
import com.ai.yc.op.web.model.order.ExAllOrder;
import com.ai.yc.op.web.model.order.OrderPageQueryParams;
import com.ai.yc.op.web.model.order.OrderPageResParam;
import com.ai.yc.op.web.model.sso.client.GeneralSSOClientUser;
import com.ai.yc.op.web.utils.AmountUtil;
import com.ai.yc.order.api.orderquery.interfaces.IOrderQuerySV;
import com.ai.yc.order.api.orderquery.param.OrdOrderVo;
import com.ai.yc.order.api.orderquery.param.QueryOrderRequest;
import com.ai.yc.order.api.orderquery.param.QueryOrderRsponse;
import com.ai.yc.order.api.paystatus.interfaces.IUpdatePayStatusSV;
import com.ai.yc.order.api.paystatus.param.OrderFeeVo;
import com.ai.yc.order.api.paystatus.param.UpdatePayStatusRequest;

@Controller
public class WaitPayController {
	private static final Logger logger = Logger.getLogger(WaitPayController.class);

	@RequestMapping("/toWaitPayOrderList")
	public ModelAndView toWaitPayOrderList(HttpServletRequest request) {
		return new ModelAndView("jsp/order/waitPayOrderList");
	}

	/**
	 * 待支付订单信息查询
	 */
	@RequestMapping("/getWaitPayOrderData")
	@ResponseBody
	public ResponseData<PageInfo<OrderPageResParam>> getgetWaitPayList(HttpServletRequest request,
			OrderPageQueryParams queryRequest) {
		ResponseData<PageInfo<OrderPageResParam>> responseData = null;
		List<OrderPageResParam> resultList = new ArrayList<OrderPageResParam>();
		PageInfo<OrderPageResParam> resultPageInfo = new PageInfo<OrderPageResParam>();
		try {
			QueryOrderRequest ordReq = new QueryOrderRequest();
			BeanUtils.copyProperties(ordReq, queryRequest);
			String pgeOrderId = queryRequest.getOrderPageId();
			if (!StringUtil.isBlank(pgeOrderId)) {
				boolean isNum = pgeOrderId.matches("[0-9]+");
				if (isNum) {
					ordReq.setOrderId(Long.parseLong(pgeOrderId));
				} else {
					ordReq.setOrderId(0l);
				}
			}
			String orderTimeBegin = queryRequest.getOrderTimeS();
			if (!StringUtil.isBlank(orderTimeBegin)) {
				orderTimeBegin = orderTimeBegin + " 00:00:00";
				Timestamp orderTimeS = Timestamp.valueOf(orderTimeBegin);
				ordReq.setOrderTimeStart(orderTimeS);
			}
			String orderTimeEnd = queryRequest.getOrderTimeE();
			if (!StringUtil.isBlank(orderTimeEnd)) {
				orderTimeEnd = orderTimeEnd + " 23:59:59";
				Timestamp orderTimeE = Timestamp.valueOf(orderTimeEnd);
				ordReq.setOrderTimeEnd(orderTimeE);
			}
			//报价时间
			String updateTimeBegin = queryRequest.getUpdateTimeS();
			if (!StringUtil.isBlank(updateTimeBegin)) {
				updateTimeBegin = updateTimeBegin + " 00:00:00";
				Timestamp updateTimeS = Timestamp.valueOf(updateTimeBegin);
				ordReq.setUpdateTimeStart(updateTimeS);
			}
			String updateTimeEnd = queryRequest.getUpdateTimeE();
			if (!StringUtil.isBlank(updateTimeEnd)) {
				updateTimeEnd = updateTimeEnd + " 23:59:59";
				Timestamp updateTimeE = Timestamp.valueOf(updateTimeEnd);
				ordReq.setUpdateTimeEnd(updateTimeE);
			}
			ordReq.setState(Constants.State.WAIT_PAY_STATE);

			String strPageNo = (null == request.getParameter("pageNo")) ? "1" : request.getParameter("pageNo");
			String strPageSize = (null == request.getParameter("pageSize")) ? "10" : request.getParameter("pageSize");
			ordReq.setPageNo(Integer.parseInt(strPageNo));
			ordReq.setPageSize(Integer.parseInt(strPageSize));
			IOrderQuerySV orderQuerySV = DubboConsumerFactory.getService(IOrderQuerySV.class);
			ICacheSV iCacheSV = DubboConsumerFactory.getService(ICacheSV.class);
			QueryOrderRsponse orderListResponse = orderQuerySV.queryOrder(ordReq);
			if (orderListResponse != null && orderListResponse.getResponseHeader().isSuccess()) {
				PageInfo<OrdOrderVo> pageInfo = orderListResponse.getPageInfo();
				BeanUtils.copyProperties(resultPageInfo, pageInfo);
				List<OrdOrderVo> orderList = pageInfo.getResult();
				if (!CollectionUtil.isEmpty(orderList)) {
					for (OrdOrderVo vo : orderList) {
						OrderPageResParam resParam = new OrderPageResParam();
						BeanUtils.copyProperties(resParam, vo);
						// 翻译订单来源
						SysParamSingleCond paramCond = new SysParamSingleCond();
						paramCond.setTenantId(Constants.TENANT_ID);
						paramCond.setColumnValue(vo.getChlId());
						paramCond.setTypeCode(Constants.TYPE_CODE);
						paramCond.setParamCode(Constants.ORD_CHL_ID);
						SysParam chldParam = iCacheSV.getSysParamSingle(paramCond);
						if (chldParam != null) {
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
						// 翻译订单类型
						paramCond = new SysParamSingleCond();
						paramCond.setTenantId(Constants.TENANT_ID);
						paramCond.setColumnValue(vo.getOrderType());
						paramCond.setTypeCode(Constants.TYPE_CODE);
						paramCond.setParamCode(Constants.ORDER_TYPE);
						SysParam orderTypeParam = iCacheSV.getSysParamSingle(paramCond);
						if (orderTypeParam != null) {
							resParam.setOrderTypePage(orderTypeParam.getColumnDesc());
						}
						// 翻译订单状态
						paramCond = new SysParamSingleCond();
						paramCond.setTenantId(Constants.TENANT_ID);
						paramCond.setColumnValue(vo.getState());
						paramCond.setTypeCode(Constants.TYPE_CODE);
						paramCond.setParamCode(Constants.ORD_STATE);
						SysParam stateParam = iCacheSV.getSysParamSingle(paramCond);
						if (stateParam != null) {
							resParam.setStatePage(stateParam.getColumnDesc());
						}
						//转换金额格式
                		if(!StringUtil.isBlank(vo.getCurrencyUnit())){
                			if(Constants.CURRENCY_UNIT_S.equals(vo.getCurrencyUnit())){
                				resParam.setTotalFeePage(vo.getTotalFee()+"$");
                			}else{
                				resParam.setTotalFeePage(AmountUtil.LiToYuan(vo.getTotalFee())+"¥");
                			}
                		}
						resultList.add(resParam);
					}
				}
				resultPageInfo.setResult(resultList);
				responseData = new ResponseData<PageInfo<OrderPageResParam>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功",
						resultPageInfo);
			} else {
				responseData = new ResponseData<PageInfo<OrderPageResParam>>(ResponseData.AJAX_STATUS_FAILURE, "查询失败",
						null);
			}
		} catch (Exception e) {
			logger.error("查询待支付订单列表失败：", e);
			responseData = new ResponseData<PageInfo<OrderPageResParam>>(ResponseData.AJAX_STATUS_FAILURE, "查询信息异常",
					null);
		}
		return responseData;
	}
	 /**
     * 订单信息导出
     */
    @RequestMapping("/exportWaitPayOrd")
    @ResponseBody
    public void  export(HttpServletRequest request, HttpServletResponse response, OrderPageQueryParams queryRequest) {
    	QueryOrderRequest ordReq = new QueryOrderRequest();
		BeanUtils.copyProperties(ordReq, queryRequest);
		String pgeOrderId = queryRequest.getOrderPageId();
		if (!StringUtil.isBlank(pgeOrderId)) {
			boolean isNum = pgeOrderId.matches("[0-9]+");
			if (isNum) {
				ordReq.setOrderId(Long.parseLong(pgeOrderId));
			} else {
				ordReq.setOrderId(0l);
			}
		}
		String orderTimeBegin = queryRequest.getOrderTimeS();
		if (!StringUtil.isBlank(orderTimeBegin)) {
			orderTimeBegin = orderTimeBegin + " 00:00:00";
			Timestamp orderTimeS = Timestamp.valueOf(orderTimeBegin);
			ordReq.setOrderTimeStart(orderTimeS);
		}
		String orderTimeEnd = queryRequest.getOrderTimeE();
		if (!StringUtil.isBlank(orderTimeEnd)) {
			orderTimeEnd = orderTimeEnd + " 23:59:59";
			Timestamp orderTimeE = Timestamp.valueOf(orderTimeEnd);
			ordReq.setOrderTimeEnd(orderTimeE);
		}
		//报价时间
		String updateTimeBegin = queryRequest.getUpdateTimeS();
		if (!StringUtil.isBlank(updateTimeBegin)) {
			updateTimeBegin = updateTimeBegin + " 00:00:00";
			Timestamp updateTimeS = Timestamp.valueOf(updateTimeBegin);
			ordReq.setUpdateTimeStart(updateTimeS);
		}
		String updateTimeEnd = queryRequest.getUpdateTimeE();
		if (!StringUtil.isBlank(updateTimeEnd)) {
			updateTimeEnd = updateTimeEnd + " 23:59:59";
			Timestamp updateTimeE = Timestamp.valueOf(updateTimeEnd);
			ordReq.setUpdateTimeEnd(updateTimeE);
		}
		ordReq.setState(Constants.State.WAIT_PAY_STATE);
	    ordReq.setPageNo(1);
	    try {
	  //获取配置中的导出最大数值
	    IConfigClient configClient = CCSClientFactory.getDefaultConfigClient();
        String maxRow =  configClient.get(ExcelConstants.EXCEL_OUTPUT_MAX_ROW);
        int excelMaxRow = Integer.valueOf(maxRow);
	    ordReq.setPageSize(excelMaxRow);
	    IOrderQuerySV orderQuerySV = DubboConsumerFactory.getService(IOrderQuerySV.class);
	    ICacheSV iCacheSV = DubboConsumerFactory.getService(ICacheSV.class);
	    QueryOrderRsponse orderListResponse = orderQuerySV.queryOrder(ordReq);
	    PageInfo<OrdOrderVo> pageInfo = orderListResponse.getPageInfo();
		List<OrdOrderVo> orderList = pageInfo.getResult();
		List<ExAllOrder> exportList = new ArrayList<ExAllOrder>();
		if(!CollectionUtil.isEmpty(orderList)){
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
						paramCond.setColumnValue(vo.getOrderType());
						paramCond.setTypeCode(Constants.TYPE_CODE);
						paramCond.setParamCode(Constants.ORDER_TYPE);
		        		SysParam orderTypeParam = iCacheSV.getSysParamSingle(paramCond);
		        		if(orderTypeParam!=null){
		        			exOrder.setOrderType(orderTypeParam.getColumnDesc());
		        		}
		        		//翻译订单状态
		        		paramCond = new SysParamSingleCond();
		        		paramCond.setTenantId(Constants.TENANT_ID);
						paramCond.setColumnValue(vo.getState());
						paramCond.setTypeCode(Constants.TYPE_CODE);
						paramCond.setParamCode(Constants.ORD_STATE);
		        		SysParam stateParam = iCacheSV.getSysParamSingle(paramCond);
		        		if(stateParam!=null){
		        			exOrder.setState(stateParam.getColumnDesc());
		        		}
		        		//转换金额格式
                		if(!StringUtil.isBlank(vo.getCurrencyUnit())){
                			if(Constants.CURRENCY_UNIT_S.equals(vo.getCurrencyUnit())){
                				exOrder.setTotalFee(vo.getTotalFee()+"$");
                			}else{
                				exOrder.setTotalFee(AmountUtil.LiToYuan(vo.getTotalFee())+"¥");
                			}
                		}
                		//下单时间
                		if(vo.getOrderTime()!=null){
                			exOrder.setOrderTime(vo.getOrderTime().toString());
                		}
		        		exOrder.setUserName(vo.getUserName());
		        		exOrder.setOrderId(vo.getOrderId());
		        		//报价时间
		        		if(vo.getUpdateTime()!=null){
		        			exOrder.setUpdateTime(vo.getUpdateTime().toString());
		        		}
		        		exOrder.setLangire(vo.getOrdProdExtendList().get(i).getLangungePairChName());
		        		//翻译订单级别
                		paramCond = new SysParamSingleCond();
                		paramCond.setTenantId(Constants.TENANT_ID);
    					paramCond.setColumnValue(vo.getOrderLevel());
    					paramCond.setTypeCode(Constants.TYPE_CODE);
    					paramCond.setParamCode(Constants.ORD_ORDER_LEVEL);
                		SysParam levelParam = iCacheSV.getSysParamSingle(paramCond);
                		if(levelParam!=null){
                			exOrder.setOrderLevel(levelParam.getColumnDesc());
                		}
		        		exOrder.setUpdateName(vo.getUpdateOperName());
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
	        		//翻译订单类型
	        		paramCond = new SysParamSingleCond();
	        		paramCond.setTenantId(Constants.TENANT_ID);
					paramCond.setColumnValue(vo.getOrderType());
					paramCond.setTypeCode(Constants.TYPE_CODE);
					paramCond.setParamCode(Constants.ORDER_TYPE);
	        		SysParam orderTypeParam = iCacheSV.getSysParamSingle(paramCond);
	        		if(orderTypeParam!=null){
	        			exOrder.setOrderType(orderTypeParam.getColumnDesc());
	        		}
	        		//翻译订单状态
	        		paramCond = new SysParamSingleCond();
	        		paramCond.setTenantId(Constants.TENANT_ID);
					paramCond.setColumnValue(vo.getState());
					paramCond.setTypeCode(Constants.TYPE_CODE);
					paramCond.setParamCode(Constants.ORD_STATE);
	        		SysParam stateParam = iCacheSV.getSysParamSingle(paramCond);
	        		if(stateParam!=null){
	        			exOrder.setState(stateParam.getColumnDesc());
	        		}
	        		//转换金额格式
            		if(!StringUtil.isBlank(vo.getCurrencyUnit())){
            			if(Constants.CURRENCY_UNIT_S.equals(vo.getCurrencyUnit())){
            				exOrder.setTotalFee(vo.getTotalFee()+"$");
            			}else{
            				exOrder.setTotalFee(AmountUtil.LiToYuan(vo.getTotalFee())+"¥");
            			}
            		}
            		if(vo.getOrderTime()!=null){
            			exOrder.setOrderTime(vo.getOrderTime().toString());
            		}
	        		
	        		exOrder.setUserName(vo.getUserName());
	        		//报价时间
	        		if(vo.getUpdateTime()!=null){
	        			exOrder.setUpdateTime(vo.getUpdateTime().toString());
	        		}
	        		exOrder.setOrderId(vo.getOrderId());
	        		//翻译订单级别
            		paramCond = new SysParamSingleCond();
            		paramCond.setTenantId(Constants.TENANT_ID);
					paramCond.setColumnValue(vo.getOrderLevel());
					paramCond.setTypeCode(Constants.TYPE_CODE);
					paramCond.setParamCode(Constants.ORD_ORDER_LEVEL);
            		SysParam levelParam = iCacheSV.getSysParamSingle(paramCond);
            		if(levelParam!=null){
            			exOrder.setOrderLevel(levelParam.getColumnDesc());
            		}
	        		exOrder.setUpdateName(vo.getUpdateOperName());
	        		exportList.add(exOrder);
				}
			}
		}
		
		
			ServletOutputStream outputStream = response.getOutputStream();
			response.reset();// 清空输出流
            response.setContentType("application/msexcel");// 定义输出类型
            response.setHeader("Content-disposition", "attachment; filename=order"+new Date().getTime()+".xls");// 设定输出文件头
            String[] titles = new String[]{"订单来源", "订单类型", "订单编号", "下单时间", "昵称", "语种方向","订单金额","订单级别","报价时间","报价人","状态"};
    		String[] fieldNames = new String[]{"chlId", "orderType", "orderId", "orderTime",
    				"userName", "langire","totalFee","orderLevel","updateTime","updateName","state"};
			 AbstractExcelHelper excelHelper = ExcelFactory.getJxlExcelHelper();
             excelHelper.writeExcel(outputStream, "订单信息"+new Date().getTime(), ExAllOrder.class, exportList,fieldNames, titles);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    /**
	 * 修改支付状态
	 */
	@RequestMapping("/updatePayState")
	@ResponseBody
	public ResponseData<String> updateAttr(HttpServletRequest request,String orderId,String remark,String updateFee,String currencyUnit,String payStyle) {
		ResponseData<String> responseData = null;
		IUpdatePayStatusSV iUpdatePayStatusSV = DubboConsumerFactory.getService(IUpdatePayStatusSV.class);
		GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
		UpdatePayStatusRequest payRequest  = new UpdatePayStatusRequest();
		payRequest.setOperId(user.getUserId());
		payRequest.setOrderId(Long.valueOf(orderId));
		OrderFeeVo feeVo = new OrderFeeVo();
		feeVo.setCurrencyUnit(currencyUnit);
		feeVo.setPayStyle(payStyle);
		feeVo.setRemark(remark);
		if(currencyUnit==null){
			responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "修改失败：货币不能为空");
			return responseData;
		}else{
			if(Constants.CURRENCY_UNIT_R.equals(currencyUnit)){
				//将元转换为厘
				feeVo.setTotalFee(AmountUtil.YuanToLi(Long.valueOf(updateFee)));
			}else{
				feeVo.setTotalFee(Long.valueOf(updateFee));
			}
		}
		
		feeVo.setAdjustFee(0L);
		feeVo.setDiscountFee(0L);
		feeVo.setPaidFee(0L);
		feeVo.setPayFee(0L);
		feeVo.setOperDiscountFee(0L);
		feeVo.setPayStyle(payStyle);
		payRequest.setOrderFee(feeVo);
		BaseResponse response = iUpdatePayStatusSV.updatePayStatus(payRequest);
		ResponseHeader header = response.getResponseHeader();
		if (header!=null && !header.isSuccess()){
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "修改失败:"+header.getResultMessage());
        }else{
        	responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "修改成功"+header.getResultMessage());
        }
        return responseData;
	}
}
