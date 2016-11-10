package com.ai.yc.op.web.controller.order;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.yc.op.web.model.order.OrderPageQueryParams;
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
    public ResponseData<PageInfo<OrdOrderVo>> getList(HttpServletRequest request,OrderPageQueryParams queryRequest){
    	ResponseData<PageInfo<OrdOrderVo>> responseData = null;
	    try{
	    	QueryOrderRequest ordReq = new QueryOrderRequest();
	    	BeanUtils.copyProperties(ordReq, queryRequest);
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
		    QueryOrderRsponse orderListResponse = orderQuerySV.queryOrder(ordReq);
			if (orderListResponse != null && orderListResponse.getResponseHeader().isSuccess()) {
				PageInfo<OrdOrderVo> pageInfo = orderListResponse.getPageInfo();
				responseData = new ResponseData<PageInfo<OrdOrderVo>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功",pageInfo);
			} else {
				responseData = new ResponseData<PageInfo<OrdOrderVo>>(ResponseData.AJAX_STATUS_FAILURE, "查询失败", null);
			}
		} catch (Exception e) {
			logger.error("查询订单列表失败：", e);
			responseData = new ResponseData<PageInfo<OrdOrderVo>>(ResponseData.AJAX_STATUS_FAILURE, "查询信息异常", null);
		}
	    return responseData;
    }
}
