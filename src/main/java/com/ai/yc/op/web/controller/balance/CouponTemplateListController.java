package com.ai.yc.op.web.controller.balance;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ai.slp.balance.api.translatorbill.interfaces.IBillGenerateSV;
import com.ai.slp.balance.api.translatorbill.param.*;
import com.ai.yc.op.web.model.bill.BillDetailResponse;
import com.ai.yc.op.web.model.bill.ExAllBill;
import com.ai.yc.translator.api.translatorservice.interfaces.IYCTranslatorServiceSV;
import com.ai.yc.translator.api.translatorservice.param.YCLSPInfoReponse;
import com.ai.yc.translator.api.translatorservice.param.searchYCLSPInfoRequest;
import com.ai.yc.user.api.userservice.interfaces.IYCUserServiceSV;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("/balance")
public class CouponTemplateListController {
	
	private static final Logger logger = Logger.getLogger(CouponTemplateListController.class);
	
	@RequestMapping("/toCouponTemplateList")
	public ModelAndView toCouponTemplateList(HttpServletRequest request) {
		return new ModelAndView("jsp/balance/couponTemplateList");
	}
	
	/**
     * 账单信息查询
     */
   /* @RequestMapping("/getBillPageData")
    @ResponseBody
    public ResponseData<PageInfo<FunAccountResponse>> getList(HttpServletRequest request,FunAccountQueryRequest funAccountQueryRequest)throws Exception{
    	ResponseData<PageInfo<FunAccountResponse>> responseData = null;
    	List<FunAccountResponse> resultList = new ArrayList<FunAccountResponse>();
    	PageInfo<FunAccountResponse> resultPageInfo  = new PageInfo<FunAccountResponse>();
		try{

			String strPageNo=(null==request.getParameter("pageNo"))?"1":request.getParameter("pageNo");
		    String strPageSize=(null==request.getParameter("pageSize"))?"10":request.getParameter("pageSize");
			resultPageInfo.setPageNo(Integer.parseInt(strPageNo));
			resultPageInfo.setPageSize(Integer.parseInt(strPageSize));
			funAccountQueryRequest.setPageInfo(resultPageInfo);
			if (funAccountQueryRequest.getState()==null){
				funAccountQueryRequest.setState(1);
			}
			IBillGenerateSV billGenerateSV = DubboConsumerFactory.getService(IBillGenerateSV.class);
			FunAccountQueryResponse funAccountQueryResponse = billGenerateSV.queryFunAccount(funAccountQueryRequest);
			if (funAccountQueryResponse.getResponseHeader().isSuccess()) {
				PageInfo<FunAccountResponse> pageInfo = funAccountQueryResponse.getPageInfo();
				BeanUtils.copyProperties(resultPageInfo, pageInfo);
				List<FunAccountResponse> result = pageInfo.getResult();
				resultPageInfo.setResult(result);
				responseData = new ResponseData<PageInfo<FunAccountResponse>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功",resultPageInfo);
			}else {
				responseData = new ResponseData<PageInfo<FunAccountResponse>>(ResponseData.AJAX_STATUS_FAILURE, "查询失败", null);
			}
		} catch (Exception e) {
			logger.error("查询账单列表失败：", e);
			responseData = new ResponseData<PageInfo<FunAccountResponse>>(ResponseData.AJAX_STATUS_FAILURE, "查询信息异常", null);
		}
	    return responseData;
    }*/

}
