package com.ai.yc.op.web.controller.balance;

import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.sdk.components.ccs.CCSClientFactory;
import com.ai.opt.sdk.components.excel.client.AbstractExcelHelper;
import com.ai.opt.sdk.components.excel.factory.ExcelFactory;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.paas.ipaas.ccs.IConfigClient;
import com.ai.slp.balance.api.incomeoutquery.interfaces.IncomeOutQuerySV;
import com.ai.slp.balance.api.incomeoutquery.param.FundBookQueryResponseAll;
import com.ai.slp.balance.api.incomeoutquery.param.IncomeDetailAll;
import com.ai.slp.balance.api.incomeoutquery.param.IncomeQueryRequestAll;
import com.ai.yc.op.web.constant.Constants.ExcelConstants;
import com.ai.yc.op.web.model.bill.ExAllBill;
import com.ai.yc.op.web.model.bill.ExAllBusiness;
import com.ai.yc.op.web.utils.AmountUtil;
import com.ai.yc.op.web.utils.TimeZoneTimeUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/businessList")
public class BusinessListController {
	
	private static final Logger logger = Logger.getLogger(BusinessListController.class);
	
	@RequestMapping("/toBusinessList")
	public ModelAndView toCompanyBillList(HttpServletRequest request) {
		return new ModelAndView("jsp/balance/businessList");
	}

	/**
	 * 信息查询
	 */

	@RequestMapping("/getIncomeOutData")
	@ResponseBody
	public ResponseData<FundBookQueryResponseAll> getData(HttpServletRequest request, IncomeQueryRequestAll incomeQueryRequestAll)throws Exception{
		ResponseData<FundBookQueryResponseAll> responseData = null;
		List<IncomeDetailAll> resultList = new ArrayList<IncomeDetailAll>();
		PageInfo<IncomeDetailAll> resultPageInfo  = new PageInfo<IncomeDetailAll>();
		if (incomeQueryRequestAll.getSerialCode()==""){
			incomeQueryRequestAll.setSerialCode(null);
		}
		if (incomeQueryRequestAll.getChannel()==""){
			incomeQueryRequestAll.setChannel(null);
		}
		if (incomeQueryRequestAll.getNickName()==""){
			incomeQueryRequestAll.setNickName(null);
		}
		if (incomeQueryRequestAll.getBeginDate()==""){
			incomeQueryRequestAll.setBeginDate(null);
		}
		if (incomeQueryRequestAll.getEndDate()==""){
			incomeQueryRequestAll.setEndDate(null);
		}
		if (incomeQueryRequestAll.getState()==""){
			incomeQueryRequestAll.setState(null);
		}
		if (incomeQueryRequestAll.getCurrencyUnit()==""){
			incomeQueryRequestAll.setCurrencyUnit(null);
		}
		if (incomeQueryRequestAll.getIncomeFlag()==""){
			incomeQueryRequestAll.setIncomeFlag(null);
		}
		if (incomeQueryRequestAll.getOptType()==""){
			incomeQueryRequestAll.setOptType(null);
		}
		try{
			String strPageNo=(null==request.getParameter("pageNo"))?"1":request.getParameter("pageNo");
			String strPageSize=(null==request.getParameter("pageSize"))?"10":request.getParameter("pageSize");
			resultPageInfo.setPageNo(Integer.parseInt(strPageNo));
			resultPageInfo.setPageSize(Integer.parseInt(strPageSize));
			incomeQueryRequestAll.setPageInfo(resultPageInfo);
			IncomeOutQuerySV incomeOutQuerySV = DubboConsumerFactory.getService(IncomeOutQuerySV.class);
			FundBookQueryResponseAll fundBookQueryResponseAll = incomeOutQuerySV.incomeOutQueryAll(incomeQueryRequestAll);
			if (fundBookQueryResponseAll.getResponseHeader().isSuccess()) {
				responseData = new ResponseData<FundBookQueryResponseAll>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功",fundBookQueryResponseAll);
			}else {
				responseData = new ResponseData<FundBookQueryResponseAll>(ResponseData.AJAX_STATUS_FAILURE, "查询失败", null);
			}
		} catch (Exception e) {
			logger.error("查询列表失败：", e);
			responseData = new ResponseData<FundBookQueryResponseAll>(ResponseData.AJAX_STATUS_FAILURE, "查询信息异常", null);
		}
		return responseData;
	}


	/**
	 * 交易信息查询
	 */
	@RequestMapping("/getIncomeOutPageData")
	@ResponseBody
	public ResponseData<PageInfo<IncomeDetailAll>> getList(HttpServletRequest request, IncomeQueryRequestAll incomeQueryRequestAll)throws Exception{
		ResponseData<PageInfo<IncomeDetailAll>> responseData = null;
		List<IncomeDetailAll> resultList = new ArrayList<IncomeDetailAll>();
		PageInfo<IncomeDetailAll> resultPageInfo  = new PageInfo<IncomeDetailAll>();
		if (incomeQueryRequestAll.getSerialCode()==""){
			incomeQueryRequestAll.setSerialCode(null);
		}
		if (incomeQueryRequestAll.getChannel()==""){
			incomeQueryRequestAll.setChannel(null);
		}
		if (incomeQueryRequestAll.getNickName()==""){
			incomeQueryRequestAll.setNickName(null);
		}
		if (incomeQueryRequestAll.getBeginDate()==""){
			incomeQueryRequestAll.setBeginDate(null);
		}
		if (incomeQueryRequestAll.getEndDate()==""){
			incomeQueryRequestAll.setEndDate(null);
		}
		if (incomeQueryRequestAll.getState()==""){
			incomeQueryRequestAll.setState(null);
		}
		if (incomeQueryRequestAll.getCurrencyUnit()==""){
			incomeQueryRequestAll.setCurrencyUnit(null);
		}
		if (incomeQueryRequestAll.getIncomeFlag()==""){
			incomeQueryRequestAll.setIncomeFlag(null);
		}
		if (incomeQueryRequestAll.getOptType()==""){
			incomeQueryRequestAll.setOptType(null);
		}
		try{
			String strPageNo=(null==request.getParameter("pageNo"))?"1":request.getParameter("pageNo");
			String strPageSize=(null==request.getParameter("pageSize"))?"10":request.getParameter("pageSize");
			resultPageInfo.setPageNo(Integer.parseInt(strPageNo));
			resultPageInfo.setPageSize(Integer.parseInt(strPageSize));
			incomeQueryRequestAll.setPageInfo(resultPageInfo);
			IncomeOutQuerySV incomeOutQuerySV = DubboConsumerFactory.getService(IncomeOutQuerySV.class);
			FundBookQueryResponseAll fundBookQueryResponseAll = incomeOutQuerySV.incomeOutQueryAll(incomeQueryRequestAll);
			if (fundBookQueryResponseAll.getResponseHeader().isSuccess()) {
				PageInfo<IncomeDetailAll> pageInfo = fundBookQueryResponseAll.getPageInfo();
				responseData = new ResponseData<PageInfo<IncomeDetailAll>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功",pageInfo);
			}else {
				responseData = new ResponseData<PageInfo<IncomeDetailAll>>(ResponseData.AJAX_STATUS_FAILURE, "查询失败", null);
			}
		} catch (Exception e) {
			logger.error("查询列表失败：", e);
			responseData = new ResponseData<PageInfo<IncomeDetailAll>>(ResponseData.AJAX_STATUS_FAILURE, "查询信息异常", null);
		}
		return responseData;
	}


	/**
     * 充值交易信息导出
     */
    @RequestMapping("/export")
    @ResponseBody
    public void  export(HttpServletRequest request, HttpServletResponse response, IncomeQueryRequestAll incomeQueryRequestAll) {
    	logger.error("进入导出方法>>>>");
		List<ExAllBusiness> exAllBusinesses = new ArrayList<ExAllBusiness>();
		PageInfo<IncomeDetailAll> resultPageInfo  = new PageInfo<IncomeDetailAll>();
		if (incomeQueryRequestAll.getSerialCode()==""){
			incomeQueryRequestAll.setSerialCode(null);
		}
		if (incomeQueryRequestAll.getChannel()==""){
			incomeQueryRequestAll.setChannel(null);
		}
		if (incomeQueryRequestAll.getNickName()==""){
			incomeQueryRequestAll.setNickName(null);
		}
		if (incomeQueryRequestAll.getBeginDate()==""){
			incomeQueryRequestAll.setBeginDate(null);
		}
		if (incomeQueryRequestAll.getEndDate()==""){
			incomeQueryRequestAll.setEndDate(null);
		}
		if (incomeQueryRequestAll.getState()==""){
			incomeQueryRequestAll.setState(null);
		}
		if (incomeQueryRequestAll.getCurrencyUnit()==""){
			incomeQueryRequestAll.setCurrencyUnit(null);
		}
		if (incomeQueryRequestAll.getIncomeFlag()==""){
			incomeQueryRequestAll.setIncomeFlag(null);
		}
		if (incomeQueryRequestAll.getOptType()==""){
			incomeQueryRequestAll.setOptType(null);
		}
	    try {
	  //获取配置中的导出最大数值
	    	logger.error("获取导出最大条数配置>>>>");
	    IConfigClient configClient = CCSClientFactory.getDefaultConfigClient();
        String maxRow =  configClient.get(ExcelConstants.EXCEL_OUTPUT_MAX_ROW);
        int excelMaxRow = Integer.valueOf(maxRow);
		resultPageInfo.setPageSize(excelMaxRow);
		resultPageInfo.setPageNo(1);
		incomeQueryRequestAll.setPageInfo(resultPageInfo);
		IncomeOutQuerySV incomeOutQuerySV = DubboConsumerFactory.getService(IncomeOutQuerySV.class);
		logger.error("调用查询方法>>>>");
		FundBookQueryResponseAll fundBookQueryResponseAll = incomeOutQuerySV.incomeOutQueryAll(incomeQueryRequestAll);
		PageInfo<IncomeDetailAll> pageInfo = fundBookQueryResponseAll.getPageInfo();
		List<IncomeDetailAll> incomeDetailAlls = pageInfo.getResult();
		if(!CollectionUtil.isEmpty(incomeDetailAlls)){
			for (IncomeDetailAll incomeDetailAll:incomeDetailAlls){
				ExAllBusiness exAllBusiness = new ExAllBusiness();
				//交易号
				exAllBusiness.setSerialCode(incomeDetailAll.getSerialCode());
				//时间
				exAllBusiness.setPayTime(TimeZoneTimeUtil.getTimes(incomeDetailAll.getPayTime()));
				//类型1:充值；2:下单；3:提现；4: 退款
				if (incomeDetailAll.getOptType()!=null){
					if (incomeDetailAll.getOptType().equals("1")){
						exAllBusiness.setOptType("充值");
					}else if (incomeDetailAll.getOptType().equals("2")){
						exAllBusiness.setOptType("下单");
					}else if (incomeDetailAll.getOptType().equals("3")){
						exAllBusiness.setOptType("提现");
					}else if(incomeDetailAll.getOptType().equals("4")){
						exAllBusiness.setOptType("退款");
					}
				}
				//名称/昵称
				exAllBusiness.setAccountName(incomeDetailAll.getAccountName());
				//金额
				if (incomeDetailAll.getCurrencyUnit().equals("1")){
					exAllBusiness.setTotalAmount("¥"+ AmountUtil.liToYuan(incomeDetailAll.getTotalAmount()));
				}else {
					exAllBusiness.setTotalAmount("$"+AmountUtil.liToYuan(incomeDetailAll.getTotalAmount()));
				}
				//帐户余额
				if (incomeDetailAll.getCurrencyUnit().equals("1")){
					exAllBusiness.setBalanceAmount("¥"+ AmountUtil.liToYuan(incomeDetailAll.getTotalAmount()+incomeDetailAll.getBalancePre()));
				}else {
					exAllBusiness.setBalanceAmount("$"+AmountUtil.liToYuan(incomeDetailAll.getTotalAmount()+incomeDetailAll.getBalancePre()));
				}
				//支付方式
				if (incomeDetailAll.getChannel()!=null){
					exAllBusiness.setChannel(incomeDetailAll.getChannel());
				}
				//状态
				if ("1".equals(incomeDetailAll.getPayStatus())){
					exAllBusiness.setPayStatus("交易成功");
				}else {
					exAllBusiness.setPayStatus("交易失败");
				}
				if (incomeDetailAll.getRemark()!=null){
					exAllBusiness.setRemark(incomeDetailAll.getRemark());
				}
				exAllBusinesses.add(exAllBusiness);
			}
		}else{
			logger.error("查询数据为空>>>>");
		}
			logger.error("获取输出流>>>>");
			ServletOutputStream outputStream = response.getOutputStream();
			response.reset();// 清空输出流
            response.setContentType("application/msexcel");// 定义输出类型
            response.setHeader("Content-disposition", "attachment; filename=recharge"+new Date().getTime()+".xls");// 设定输出文件头
            String[] titles = new String[]{"日期", "交易号", "名称", "昵称", "金额", "账户余额","支付方式","状态","备注"};
    		String[] fieldNames = new String[]{"payTime", "serialCode", "accountName", "accountName",
    				"totalAmount", "balanceAmount","channel","payStatus","remark"};
			 AbstractExcelHelper excelHelper = ExcelFactory.getJxlExcelHelper();
			 logger.error("写入数据到excel>>>>");
			 excelHelper.writeExcel(outputStream, "recharge"+new Date().getTime(), ExAllBusiness.class, exAllBusinesses,fieldNames, titles);
		} catch (Exception e) {
			logger.error("导出账单列表失败："+e.getMessage(), e);
		}
	}

}
