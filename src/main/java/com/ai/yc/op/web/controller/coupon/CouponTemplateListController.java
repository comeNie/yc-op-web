package com.ai.yc.op.web.controller.coupon;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ai.slp.balance.api.coupontemplate.interfaces.ICouponTemplateSV;
import com.ai.slp.balance.api.coupontemplate.param.CouponTemplateParam;
import com.ai.slp.balance.api.coupontemplate.param.DeleteFunCouponTemplate;
import com.ai.slp.balance.api.coupontemplate.param.FunCouponDetailPageResponse;
import com.ai.slp.balance.api.coupontemplate.param.FunCouponDetailQueryRequest;
import com.ai.slp.balance.api.coupontemplate.param.FunCouponDetailResponse;
import com.ai.slp.balance.api.coupontemplate.param.FunCouponTemplateQueryRequest;
import com.ai.slp.balance.api.coupontemplate.param.FunCouponTemplateQueryResponse;
import com.ai.slp.balance.api.coupontemplate.param.FunCouponTemplateResponse;
import com.ai.slp.balance.api.coupontemplate.param.SaveFunCouponTemplate;
import com.ai.slp.balance.api.couponuserule.interfaces.ICouponUseRuleSV;
import com.ai.slp.balance.api.couponuserule.param.FunCouponUseRuleQueryResponse;
import com.ai.yc.op.web.constant.Constants.ExcelConstants;
import com.ai.yc.op.web.model.coupon.ExAllCouponTemplate;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.sdk.components.ccs.CCSClientFactory;
import com.ai.opt.sdk.components.excel.client.AbstractExcelHelper;
import com.ai.opt.sdk.components.excel.factory.ExcelFactory;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.paas.ipaas.ccs.IConfigClient;

@Controller
@RequestMapping("/coupon")
public class CouponTemplateListController {
	
	private static final Logger logger = Logger.getLogger(CouponTemplateListController.class);
	
	@RequestMapping("/toCouponTemplateList")
	public ModelAndView toCouponTemplateList(HttpServletRequest request) {
		return new ModelAndView("jsp/coupon/couponTemplateList");
	}
	/**
	 * 跳转到添加页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/toAddCouponTemplate")
	public ModelAndView toAddCouponTemplate(HttpServletRequest request) {
		return new ModelAndView("jsp/coupon/addCouponTemplate");
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
			List<FunCouponTemplateResponse> result2 = funCouponTemplateQueryResponse.getPageInfo().getResult();
			for (FunCouponTemplateResponse funCouponTemplateResponse : result2) {
				String usedScene = funCouponTemplateResponse.getUsedScene();
				String[] usedSceneArray = usedScene.split(",");
		        for (int i = 0; i < usedSceneArray.length; i++) {
		            funCouponTemplateResponse.setUsedScene(usedSceneArray[i]);
		        }
			}
			for (FunCouponTemplateResponse funCouponTemplateResponse : result2) {
				if(!(funCouponTemplateResponse.getCouponUserId().equals("0"))){
					String requiredMoneyAmounts = null;
					String couponUserId = funCouponTemplateResponse.getCouponUserId();
					ICouponUseRuleSV couponUseRuleSV = DubboConsumerFactory.getService(ICouponUseRuleSV.class);
					List<FunCouponUseRuleQueryResponse> queryFunCouponUseRule = couponUseRuleSV.queryFunCouponUseRule(couponUserId);
					for (FunCouponUseRuleQueryResponse funCouponUseRuleQueryResponse : queryFunCouponUseRule) {
						Integer requiredMoneyAmount = funCouponUseRuleQueryResponse.getRequiredMoneyAmount();
						requiredMoneyAmounts = requiredMoneyAmount.toString();
					}
					funCouponTemplateResponse.setCouponUserId(requiredMoneyAmounts);
				}
			}
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
			logger.error("查询优惠券模板列表失败：", e);
			responseData = new ResponseData<PageInfo<FunCouponTemplateResponse>>(ResponseData.AJAX_STATUS_FAILURE, "查询信息异常", null);
		}
	    return responseData;
    }
    
    
    
    /**
     * 检测名称唯一
     */
    @RequestMapping("/checkName")
    @ResponseBody
    public Integer checkName(CouponTemplateParam param)throws Exception{
    	ICouponTemplateSV couponTemplateSV = DubboConsumerFactory.getService(ICouponTemplateSV.class);
    	Integer checkCouponByCouponName = couponTemplateSV.checkCouponTemplateName(param);
		return checkCouponByCouponName;
    }
    /**
     * 添加优惠券模板
     */
    @RequestMapping("/insertCouponTemplate")
    @ResponseBody
    public ResponseData<Boolean> insertCouponTemplate(SaveFunCouponTemplate req){
    	ICouponTemplateSV couponTemplateSV = DubboConsumerFactory.getService(ICouponTemplateSV.class);
    	BaseResponse savaCouponTemplate = couponTemplateSV.savaCouponTemplate(req);
    	if(savaCouponTemplate.getResponseHeader().getIsSuccess()==false){
			return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
		}
		return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_SUCCESS, "添加优惠券模板成功", true);
    }
    
    
    @RequestMapping("/toCouponDetailList")
	public ModelAndView toCouponDetailList(Integer templateId) {
		ModelAndView view = new ModelAndView("jsp/coupon/couponDetailList");
		view.addObject("templateId", templateId);
		return view;
	}
    /**
	 * 优惠券明细查询
	 */
	@RequestMapping("/getCouponDetailData")
	@ResponseBody
	public ResponseData<PageInfo<FunCouponDetailResponse>> getDetailList(HttpServletRequest request,Integer templateId)throws Exception{
		FunCouponDetailQueryRequest funCouponDetailQueryRequest = new FunCouponDetailQueryRequest();
		funCouponDetailQueryRequest.setTemplateId(templateId);
		ResponseData<PageInfo<FunCouponDetailResponse>> responseData = null;
		PageInfo<FunCouponDetailResponse> resultPageInfo  = new PageInfo<FunCouponDetailResponse>();
		try{
			ICouponTemplateSV couponTemplateSV = DubboConsumerFactory.getService(ICouponTemplateSV.class);
			FunCouponDetailPageResponse funCouponDetailPageResponse = couponTemplateSV.queryCouponDetail(funCouponDetailQueryRequest);
			if (funCouponDetailPageResponse.getResponseHeader().isSuccess()) {
				PageInfo<FunCouponDetailResponse> pageInfo = funCouponDetailPageResponse.getPageInfo();
				BeanUtils.copyProperties(resultPageInfo, pageInfo);
				List<FunCouponDetailResponse> result = pageInfo.getResult();
				resultPageInfo.setResult(result);
				
				responseData = new ResponseData<PageInfo<FunCouponDetailResponse>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功",resultPageInfo);
			}else {
				responseData = new ResponseData<PageInfo<FunCouponDetailResponse>>(ResponseData.AJAX_STATUS_FAILURE, "查询失败", null);
			}
		} catch (Exception e) {
			logger.error("查询账单列表失败：", e);
			responseData = new ResponseData<PageInfo<FunCouponDetailResponse>>(ResponseData.AJAX_STATUS_FAILURE, "查询信息异常", null);
		}
		return responseData;
	}
    /**
     * 优惠券模板信息导出
     */
    @RequestMapping("/export")
    @ResponseBody
    public void export(HttpServletRequest request, HttpServletResponse response, FunCouponTemplateQueryRequest funCouponTemplateQueryRequest) {
    	logger.error("进入导出方法>>>>");
		List<ExAllCouponTemplate> exAllCouponTemplates = new ArrayList<ExAllCouponTemplate>();
		PageInfo<FunCouponTemplateResponse> resultPageInfo  = new PageInfo<FunCouponTemplateResponse>();
	    try {
	  //获取配置中的导出最大数值
	    	logger.error("获取导出最大条数配置>>>>");
	    IConfigClient configClient = CCSClientFactory.getDefaultConfigClient();
        String maxRow =  configClient.get(ExcelConstants.EXCEL_OUTPUT_MAX_ROW);
        int excelMaxRow = Integer.valueOf(maxRow);
		resultPageInfo.setPageSize(excelMaxRow);
		resultPageInfo.setPageNo(1);
		funCouponTemplateQueryRequest.setPageInfo(resultPageInfo);
		ICouponTemplateSV couponTemplateSV = DubboConsumerFactory.getService(ICouponTemplateSV.class);
	    logger.error("调用查询方法>>>>");
	    FunCouponTemplateQueryResponse funCouponTemplateQueryResponse = couponTemplateSV.queryFunCouponTemplate(funCouponTemplateQueryRequest);
		PageInfo<FunCouponTemplateResponse> pageInfo = funCouponTemplateQueryResponse.getPageInfo();
		List<FunCouponTemplateResponse> couponTemplateList = pageInfo.getResult();
		if(!CollectionUtil.isEmpty(couponTemplateList)){
			for (FunCouponTemplateResponse funCouponTemplateResponse:couponTemplateList){
				ExAllCouponTemplate exAllCouponTemplate = new ExAllCouponTemplate();
				//序号
				exAllCouponTemplate.setTemplateId(funCouponTemplateResponse.getTemplateId());;
				//名称
				exAllCouponTemplate.setCouponName(funCouponTemplateResponse.getCouponName());
				//面值
				exAllCouponTemplate.setFaceValue(funCouponTemplateResponse.getFaceValue());
				//币种单位
				if (funCouponTemplateResponse.getCurrencyUnit()!=null){
					if (funCouponTemplateResponse.getCurrencyUnit().equals("1")){
						exAllCouponTemplate.setCurrencyUnit("CNY￥");
					}else {
						exAllCouponTemplate.setCurrencyUnit("USD$");
					}
				}
				//使用规则
				exAllCouponTemplate.setCouponUserId(funCouponTemplateResponse.getCouponUserId());
				//领取开始时间
				if (funCouponTemplateResponse.getReceiveStartTime()!=null){
					exAllCouponTemplate.setReceiveStartTime(funCouponTemplateResponse.getReceiveStartTime().toString());
				}
				//领取结束时间
				if (funCouponTemplateResponse.getReceiveEndTime()!=null){
					exAllCouponTemplate.setReceiveEndTime(funCouponTemplateResponse.getReceiveEndTime().toString());
				}
				//有效期开始时间
				if (funCouponTemplateResponse.getEffectiveStartTime()!=null){
					exAllCouponTemplate.setEffectiveStartTime(funCouponTemplateResponse.getEffectiveStartTime().toString());
				}
				//有效期结束时间
				if (funCouponTemplateResponse.getEffectiveEndTime()!=null){
					exAllCouponTemplate.setEffectiveEndTime(funCouponTemplateResponse.getEffectiveEndTime().toString());
				}
				//使用场景
				if (funCouponTemplateResponse.getUsedScene()!=null){
					if (funCouponTemplateResponse.getUsedScene().equals("1")){
						exAllCouponTemplate.setUsedScene("pc");
					}else if (funCouponTemplateResponse.getUsedScene().equals("2")){
						exAllCouponTemplate.setUsedScene("app");
					}else if(funCouponTemplateResponse.getUsedScene().equals("3")){
						exAllCouponTemplate.setUsedScene("app-hd");
					}
				}
				//已使用/已领/总数
				exAllCouponTemplate.setMaxCountIssue(funCouponTemplateResponse.getMaxCountIssue());
				//创建人
				exAllCouponTemplate.setCreateOperator(funCouponTemplateResponse.getCreateOperator());
				//创建时间
				if (funCouponTemplateResponse.getCreateTime()!=null){
					exAllCouponTemplate.setCreateTime(funCouponTemplateResponse.getCreateTime().toString());
				}
				//结算状态
				if (funCouponTemplateResponse.getStatus()!=null){
					if (funCouponTemplateResponse.getStatus().equals("1")){
						exAllCouponTemplate.setStatus("启用");
					}else {
						exAllCouponTemplate.setStatus("禁用");
					}
				}
				exAllCouponTemplates.add(exAllCouponTemplate);
			}
		}else{
			logger.error("查询数据为空>>>>");
		}
			logger.error("获取输出流>>>>");
			ServletOutputStream outputStream = response.getOutputStream();
			response.reset();// 清空输出流
            response.setContentType("application/msexcel");// 定义输出类型
            response.setHeader("Content-disposition", "attachment; filename=couponTemplate"+new Date().getTime()+".xls");// 设定输出文件头
            String[] titles = new String[]{"序号", "名称", "面值","币种单位", "使用规则", "领取开始时间", "领取结束时间","有效期开始时间","有效期结束时间","使用场景","已使用/已领/总数","创建人","创建时间","状态"};
    		String[] fieldNames = new String[]{"templateId", "couponName", "faceValue", "currencyUnit",
    				"couponUserId", "receiveStartTime","receiveEndTime","effectiveStartTime","effectiveEndTime","usedScene","maxCountIssue","createOperator","createTime","status"};
			 AbstractExcelHelper excelHelper = ExcelFactory.getJxlExcelHelper();
			 logger.error("写入数据到excel>>>>");
			 excelHelper.writeExcel(outputStream, "couponTemplate"+new Date().getTime(), ExAllCouponTemplate.class, exAllCouponTemplates,fieldNames, titles);
		} catch (Exception e) {
			logger.error("导出模板列表失败："+e.getMessage(), e);
		}
	}
    
    /**
     * 删除优惠券模板
     */
    @RequestMapping("/deleteCouponTemplate")
    @ResponseBody
    public ResponseData<Boolean> deleteCouponTemplate(Integer templateId){
    	DeleteFunCouponTemplate deleteFunCouponTemplate = new DeleteFunCouponTemplate();
    	deleteFunCouponTemplate.setTemplateId(templateId);;
    	ICouponTemplateSV couponTemplateSV = DubboConsumerFactory.getService(ICouponTemplateSV.class);
    	Integer deleteCouponTemplate = couponTemplateSV.deleteCouponTemplate(deleteFunCouponTemplate);
    	if(deleteCouponTemplate==null){
			return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
		}
		return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_SUCCESS, "删除优惠券模板成功", true);
    }

}
