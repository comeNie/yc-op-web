package com.ai.yc.op.web.controller.balance;

import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.sdk.components.ccs.CCSClientFactory;
import com.ai.opt.sdk.components.excel.client.AbstractExcelHelper;
import com.ai.opt.sdk.components.excel.factory.ExcelFactory;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.paas.ipaas.ccs.IConfigClient;
import com.ai.slp.balance.api.translatorbill.interfaces.IBillGenerateSV;
import com.ai.slp.balance.api.translatorbill.param.*;
import com.ai.yc.op.web.constant.Constants.ExcelConstants;
import com.ai.yc.op.web.model.bill.BillDetailResponse;
import com.ai.yc.op.web.model.bill.ExAllBill;
import com.ai.yc.op.web.utils.TimeZoneTimeUtil;
import com.ai.yc.translator.api.translatorservice.interfaces.IYCTranslatorServiceSV;
import com.ai.yc.translator.api.translatorservice.param.YCLSPInfoReponse;
import com.ai.yc.translator.api.translatorservice.param.searchYCLSPInfoRequest;
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
@RequestMapping("/lspBill")
public class LspBillListController {
	
	private static final Logger logger = Logger.getLogger(LspBillListController.class);
	
	@RequestMapping("/toLspBillList")
	public ModelAndView toLspBillList(HttpServletRequest request) {
		return new ModelAndView("jsp/balance/lspBillList");
	}
	
	/**
     * 账单信息查询
     */
    @RequestMapping("/getLspBillPageData")
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
    }

	@RequestMapping("/toLspDetailBillList")
	public ModelAndView toLspDetailBillList(String billID) {
		ModelAndView view = new ModelAndView("jsp/balance/lspDetailBillList");
		view.addObject("billID", billID);
		return view;
	}

	/**
	 * 账单明细查询
	 */
	@RequestMapping("/getBillDetailData")
	@ResponseBody
	public ResponseData<PageInfo<BillDetailResponse>> getDetailList(HttpServletRequest request,String billId)throws Exception{
		FunAccountDetailQueryRequest funAccountDetailQueryRequest = new FunAccountDetailQueryRequest();
		funAccountDetailQueryRequest.setBillID(billId);
		ResponseData<PageInfo<BillDetailResponse>> responseData = null;
		List<BillDetailResponse> resultList = new ArrayList<BillDetailResponse>();
		PageInfo<BillDetailResponse> resultPageInfo  = new PageInfo<BillDetailResponse>();
		
		try{
			IBillGenerateSV billGenerateSV = DubboConsumerFactory.getService(IBillGenerateSV.class);
			IYCTranslatorServiceSV translatorServiceSV = DubboConsumerFactory.getService(IYCTranslatorServiceSV.class);
			FunAccountDetailPageResponse funAccountDetailPageResponse = billGenerateSV.queryFunAccountDetail(funAccountDetailQueryRequest);
			if (funAccountDetailPageResponse.getResponseHeader().isSuccess()) {
				PageInfo<FunAccountDetailResponse> pageInfo = funAccountDetailPageResponse.getPageInfo();
				BeanUtils.copyProperties(resultPageInfo, pageInfo);
				List<FunAccountDetailResponse> result = pageInfo.getResult();
				for (int i=0;i<result.size();i++){
					Integer id = i+1;
					String lspID = result.get(i).getLspId();
					searchYCLSPInfoRequest searchLSPParams = new searchYCLSPInfoRequest();
					searchLSPParams.setLspId(lspID);
					YCLSPInfoReponse yclspInfoReponse = translatorServiceSV.searchLSPInfo(searchLSPParams);
					String lspName = yclspInfoReponse.getLspName();
					BillDetailResponse billDetailResponse = new BillDetailResponse();
					billDetailResponse.setId(id);
					billDetailResponse.setLspName(lspName);
					BeanUtils.copyProperties(billDetailResponse,result.get(i));
					resultList.add(billDetailResponse);
				}
				resultPageInfo.setResult(resultList);
				responseData = new ResponseData<PageInfo<BillDetailResponse>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功",resultPageInfo);
			}else {
				responseData = new ResponseData<PageInfo<BillDetailResponse>>(ResponseData.AJAX_STATUS_FAILURE, "查询失败", null);
			}

		} catch (Exception e) {
			logger.error("查询账单列表失败：", e);
			responseData = new ResponseData<PageInfo<BillDetailResponse>>(ResponseData.AJAX_STATUS_FAILURE, "查询信息异常", null);
		}
		return responseData;
	}


	/**
	 * 账单结算
	 */
	@RequestMapping("/lspBillSettle")
	@ResponseBody
	public ResponseData<Boolean> settleBill(SettleParam param)throws Exception{
		IBillGenerateSV billGenerateSV = DubboConsumerFactory.getService(IBillGenerateSV.class);
		String billId=null;
		try {
			billId = billGenerateSV.settleBill(param);
		}catch (Exception e){
			logger.error("系统异常，请稍后重试", e);
			return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
		}
		if(billId==null){
			logger.error("系统异常，请稍后重试");
			return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
		}
		if (!param.getBillID().equals(billId)){
			return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
		}
		return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_SUCCESS, "账单结算成功", true);
	}

	/**
     * 订单信息导出
     */
    @RequestMapping("/export")
    @ResponseBody
    public void  export(HttpServletRequest request, HttpServletResponse response, FunAccountQueryRequest funAccountQueryRequest) {
    	logger.error("进入导出方法>>>>");
		List<ExAllBill> exAllBills = new ArrayList<ExAllBill>();
		PageInfo<FunAccountResponse> resultPageInfo  = new PageInfo<FunAccountResponse>();
	    try {
	  //获取配置中的导出最大数值
	    	logger.error("获取导出最大条数配置>>>>");
	    IConfigClient configClient = CCSClientFactory.getDefaultConfigClient();
        String maxRow =  configClient.get(ExcelConstants.EXCEL_OUTPUT_MAX_ROW);
        int excelMaxRow = Integer.valueOf(maxRow);
		resultPageInfo.setPageSize(excelMaxRow);
		resultPageInfo.setPageNo(1);
		funAccountQueryRequest.setPageInfo(resultPageInfo);
		IBillGenerateSV billGenerateSV = DubboConsumerFactory.getService(IBillGenerateSV.class);
	    logger.error("调用查询方法>>>>");
		FunAccountQueryResponse funAccountQueryResponse = billGenerateSV.queryFunAccount(funAccountQueryRequest);
		PageInfo<FunAccountResponse> pageInfo = funAccountQueryResponse.getPageInfo();
		List<FunAccountResponse> billList = pageInfo.getResult();
		if(!CollectionUtil.isEmpty(billList)){
			for (FunAccountResponse funAccountResponse:billList){
				ExAllBill exAllBill = new ExAllBill();
				//编号
				exAllBill.setBillId(funAccountResponse.getBillId());
				//昵称
				exAllBill.setNickname(funAccountResponse.getNickname());
				//用户名
				exAllBill.setTargetName(funAccountResponse.getTargetName());
				//开始时间
				if (funAccountResponse.getStartAccountTime()!=null){
					exAllBill.setStartAccountTime(funAccountResponse.getStartAccountTime().toString());
				}
				//结束时间
				if (funAccountResponse.getEndAccountTime()!=null){
					exAllBill.setEndAccountTime(TimeZoneTimeUtil.getTimes(funAccountResponse.getEndAccountTime()));
				}
				//本期账单金额billFee
				if (funAccountResponse.getFlag().equals("0")){
					exAllBill.setBillFee("¥"+funAccountResponse.getBillFee());
				}else {
					exAllBill.setBillFee("$"+funAccountResponse.getBillFee());
				}
				//平台费用
				if (funAccountResponse.getFlag().equals("0")){
					exAllBill.setPlatFee("¥"+funAccountResponse.getPlatFee());
				}else {
					exAllBill.setPlatFee("$"+funAccountResponse.getPlatFee());
				}
				//应结金额
				if (funAccountResponse.getFlag().equals("0")){
					exAllBill.setAccountAmout("¥"+funAccountResponse.getAccountAmout());
				}else {
					exAllBill.setAccountAmout("$"+funAccountResponse.getAccountAmout());
				}
				//账单周期
				exAllBill.setAccountPeriod(funAccountResponse.getAccountPeriod()+"个月");
				//账单生成时间
				if (funAccountResponse.getCreateTime()!=null){
					exAllBill.setCreateTime(TimeZoneTimeUtil.getTimes(funAccountResponse.getCreateTime()));
				}
				//结算方式
				if (funAccountResponse.getAccountType()!=null){
					if (funAccountResponse.getAccountType().equals("1")){
						exAllBill.setAccountType("支付宝");
					}else if (funAccountResponse.getAccountType().equals("2")){
						exAllBill.setAccountType("微信");
					}else if (funAccountResponse.getAccountType().equals("3")){
						exAllBill.setAccountType("银行汇款/转账");
					}else if(funAccountResponse.getAccountType().equals("4")){
						exAllBill.setAccountType("PayPal");
					}
				}
				//结算账户
				if (funAccountResponse.getSettleAccount()!=null){
					exAllBill.setSettleAccount(funAccountResponse.getSettleAccount());
				}
				//结算时间
				if (funAccountResponse.getActAccountTime()!=null){
					exAllBill.setActAccountTime(TimeZoneTimeUtil.getTimes(funAccountResponse.getActAccountTime()));
				}
				//结算状态
				if (funAccountResponse.getState()!=null){
					if (funAccountResponse.getState()==1){
						exAllBill.setState("已结算");
					}else {
						exAllBill.setState("未结算");
					}
				}
				exAllBills.add(exAllBill);
			}
		}else{
			logger.error("查询数据为空>>>>");
		}
			logger.error("获取输出流>>>>");
			ServletOutputStream outputStream = response.getOutputStream();
			response.reset();// 清空输出流
            response.setContentType("application/msexcel");// 定义输出类型
            response.setHeader("Content-disposition", "attachment; filename=bill"+new Date().getTime()+".xls");// 设定输出文件头
            String[] titles = new String[]{"编号", "昵称", "用户名", "开始时间", "结束时间", "本期账单金额","平台费用","应结金额","账单周期","账单生成时间","结算方式","结算账户","结算时间"
											,"结算状态"};
    		String[] fieldNames = new String[]{"billId", "nickname", "targetName", "startAccountTime",
    				"endAccountTime", "billFee","platFee","accountAmout","accountPeriod","createTime","accountType","settleAccount","actAccountTime","state"};
			 AbstractExcelHelper excelHelper = ExcelFactory.getJxlExcelHelper();
			 logger.error("写入数据到excel>>>>");
			 excelHelper.writeExcel(outputStream, "bill"+new Date().getTime(), ExAllBill.class, exAllBills,fieldNames, titles);
		} catch (Exception e) {
			logger.error("导出账单列表失败："+e.getMessage(), e);
		}
	}

}
