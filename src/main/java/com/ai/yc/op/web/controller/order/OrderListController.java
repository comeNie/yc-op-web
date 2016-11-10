package com.ai.yc.op.web.controller.order;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.yc.common.api.cache.interfaces.ICacheSV;
import com.ai.yc.common.api.cache.param.SysParam;
import com.ai.yc.common.api.cache.param.SysParamSingleCond;
import com.ai.yc.op.web.constant.Constants;
import com.ai.yc.op.web.model.order.OrderPageQueryParams;
import com.ai.yc.op.web.model.order.OrderPageResParam;
import com.ai.yc.order.api.orderquery.interfaces.IOrderQuerySV;
import com.ai.yc.order.api.orderquery.param.OrdOrderVo;
import com.ai.yc.order.api.orderquery.param.QueryOrderRequest;
import com.ai.yc.order.api.orderquery.param.QueryOrderRsponse;

@Controller
@RequestMapping("/order")
public class OrderListController {
	
	private static final Logger logger = Logger.getLogger(OrderListController.class);
	
	@RequestMapping("/toOrderList")
	public ModelAndView toAlertOrder(HttpServletRequest request) {
		return new ModelAndView("jsp/order/orderList");
	}
	
	/**
     * 订单信息查询
     */
    @RequestMapping("/getOrderPageData")
    @ResponseBody
    public ResponseData<PageInfo<OrderPageResParam>> getList(HttpServletRequest request,OrderPageQueryParams queryRequest){
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
                		//翻译订单类型
                		paramCond = new SysParamSingleCond();
                		paramCond.setTenantId(Constants.TENANT_ID);
    					paramCond.setColumnValue(vo.getOrderType());
    					paramCond.setTypeCode(Constants.TYPE_CODE);
    					paramCond.setParamCode(Constants.ORDER_TYPE);
                		SysParam orderTypeParam = iCacheSV.getSysParamSingle(paramCond);
                		if(orderTypeParam!=null){
                			resParam.setOrderTypePage(orderTypeParam.getColumnDesc());
                		}
                		//翻译支付方式
                		paramCond = new SysParamSingleCond();
                		paramCond.setTenantId(Constants.TENANT_ID);
    					paramCond.setColumnValue(vo.getPayStyle());
    					paramCond.setTypeCode(Constants.TYPE_CODE);
    					paramCond.setParamCode(Constants.ORD_PAY_STYLE);
                		SysParam payStyleParam = iCacheSV.getSysParamSingle(paramCond);
                		if(payStyleParam!=null){
                			resParam.setPayStylePage(payStyleParam.getColumnDesc());
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
			logger.error("查询订单列表失败：", e);
			responseData = new ResponseData<PageInfo<OrderPageResParam>>(ResponseData.AJAX_STATUS_FAILURE, "查询信息异常", null);
		}
	    return responseData;
    }
}
