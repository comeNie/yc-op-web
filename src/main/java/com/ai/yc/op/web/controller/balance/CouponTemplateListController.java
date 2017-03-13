package com.ai.yc.op.web.controller.balance;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ai.slp.balance.api.coupontemplate.interfaces.ICouponTemplateSV;
import com.ai.slp.balance.api.coupontemplate.param.FunCouponTemplateQueryRequest;
import com.ai.slp.balance.api.coupontemplate.param.FunCouponTemplateQueryResponse;
import com.ai.slp.balance.api.coupontemplate.param.FunCouponTemplateResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.web.model.ResponseData;

@Controller
@RequestMapping("/balance")
public class CouponTemplateListController {
	
	private static final Logger logger = Logger.getLogger(CouponTemplateListController.class);
	
	@RequestMapping("/toCouponTemplateList")
	public ModelAndView toCouponTemplateList(HttpServletRequest request) {
		return new ModelAndView("jsp/balance/couponTemplateList");
	}
	
	/**
     * 优惠券模板查询
     */
    @RequestMapping("/getCouponTemplatePageData")
    @ResponseBody
    public ResponseData<PageInfo<FunCouponTemplateResponse>> getList(HttpServletRequest request,FunCouponTemplateQueryRequest funCouponTemplateQueryRequest)throws Exception{
    	ResponseData<PageInfo<FunCouponTemplateResponse>> responseData = null;
    	/*List<FunAccountResponse> resultList = new ArrayList<FunAccountResponse>();*/
    	PageInfo<FunCouponTemplateResponse> resultPageInfo  = new PageInfo<FunCouponTemplateResponse>();
		try{

			String strPageNo=(null==request.getParameter("pageNo"))?"1":request.getParameter("pageNo");
		    String strPageSize=(null==request.getParameter("pageSize"))?"20":request.getParameter("pageSize");
			resultPageInfo.setPageNo(Integer.parseInt(strPageNo));
			resultPageInfo.setPageSize(Integer.parseInt(strPageSize));
			funCouponTemplateQueryRequest.setPageInfo(resultPageInfo);
			/*if (funAccountQueryRequest.getState()==null){
				funAccountQueryRequest.setState(1);
			}*/
			ICouponTemplateSV couponTemplateSV = DubboConsumerFactory.getService(ICouponTemplateSV.class);
			FunCouponTemplateQueryResponse funCouponTemplateQueryResponse = couponTemplateSV.queryFunCouponTemplate(funCouponTemplateQueryRequest);
			if (funCouponTemplateQueryResponse.getResponseHeader().isSuccess()) {
				PageInfo<FunCouponTemplateResponse> pageInfo = funCouponTemplateQueryResponse.getPageInfo();
				BeanUtils.copyProperties(resultPageInfo, pageInfo);
				List<FunCouponTemplateResponse> result = pageInfo.getResult();
				resultPageInfo.setResult(result);
				responseData = new ResponseData<PageInfo<FunCouponTemplateResponse>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功",resultPageInfo);
			}else {
				responseData = new ResponseData<PageInfo<FunCouponTemplateResponse>>(ResponseData.AJAX_STATUS_FAILURE, "查询失败", null);
			}
		} catch (Exception e) {
			logger.error("查询账单列表失败：", e);
			responseData = new ResponseData<PageInfo<FunCouponTemplateResponse>>(ResponseData.AJAX_STATUS_FAILURE, "查询信息异常", null);
		}
	    return responseData;
    }

}
