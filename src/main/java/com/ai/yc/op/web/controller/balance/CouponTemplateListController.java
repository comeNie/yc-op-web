package com.ai.yc.op.web.controller.balance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ai.slp.balance.api.coupontemplate.interfaces.ICouponTemplateSV;
import com.ai.slp.balance.api.coupontemplate.param.CouponTemplateParam;
import com.ai.slp.balance.api.coupontemplate.param.FunCouponTemplateQueryRequest;
import com.ai.slp.balance.api.coupontemplate.param.FunCouponTemplateQueryResponse;
import com.ai.slp.balance.api.coupontemplate.param.FunCouponTemplateResponse;
import com.ai.yc.op.web.constant.Constants.ExcelConstants;
import com.ai.yc.op.web.model.coupon.ExAllCouponTemplate;

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
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.paas.ipaas.ccs.IConfigClient;

@Controller
@RequestMapping("/coupon")
public class CouponTemplateListController {
	
	private static final Logger logger = Logger.getLogger(CouponTemplateListController.class);
	
	@RequestMapping("/toCouponTemplateList")
	public ModelAndView toCouponTemplateList(HttpServletRequest request) {
		return new ModelAndView("jsp/balance/couponTemplateList");
	}
	/**
	 * 跳转到添加页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/toAddCouponTemplate")
	public ModelAndView toAddCouponTemplate(HttpServletRequest request) {
		return new ModelAndView("jsp/balance/addCouponTemplate");
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

}
