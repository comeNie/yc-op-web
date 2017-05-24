package com.ai.yc.op.web.controller.balance;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.slp.balance.api.deduct.interfaces.IDeductSV;
import com.ai.slp.balance.api.deduct.param.DeductParamGeneral;
import com.ai.slp.balance.api.deposit.interfaces.IDepositSV;
import com.ai.slp.balance.api.deposit.param.DepositParamGeneral;
import com.ai.slp.balance.api.deposit.param.TransSummary;
import com.ai.slp.balance.api.rechargewithdrawals.interfaces.IRechargeWithdrawalsSV;
import com.ai.slp.balance.api.rechargewithdrawals.param.ChangeStateRequest;
import com.ai.slp.balance.api.rechargewithdrawals.param.QueryByIdRequest;
import com.ai.slp.balance.api.rechargewithdrawals.param.QueryInfoResponse;
import com.ai.slp.balance.api.rechargewithdrawals.param.ReWiehPagePageQueryRequest;
import com.ai.slp.balance.api.rechargewithdrawals.param.ReWiehPageQueryResponse;
import com.ai.slp.balance.api.rechargewithdrawals.param.ReWiehPageVo;
import com.ai.yc.ucenter.api.members.interfaces.IUcMembersSV;

@Controller
@RequestMapping("/rechargeWithdrawals")
public class RechargeWithdrawalsController {
	
	private static final Logger logger = Logger.getLogger(RechargeWithdrawalsController.class);
	
	@RequestMapping("/toRechargeWithdrawals")
	public ModelAndView toRechargeWithdrawals(HttpServletRequest request) {
		return new ModelAndView("jsp/balance/rechargeWithdrawalsList");
	}
	
	/**
	 * 跳转到添加页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/toAddRechargeWithdrawals")
	public ModelAndView toAddRechargeWithdrawals(HttpServletRequest request,Model model) {
		IUcMembersSV queryIUcMembersSV = DubboConsumerFactory.getService(IUcMembersSV.class);
//		queryIUcMembersSV.
		return new ModelAndView("jsp/balance/addRechargeWithdrawals");
	}
	
	/**
     * 用途查询
     */
    @RequestMapping("/getRechargeWithdrawalsPageData")
    @ResponseBody
    public ResponseData<PageInfo<ReWiehPageVo>> getList(HttpServletRequest request,ReWiehPagePageQueryRequest reWiehPagePageQueryRequest)throws Exception{
    	ResponseData<PageInfo<ReWiehPageVo>> responseData = null;
    	PageInfo<ReWiehPageVo> resultPageInfo  = new PageInfo<ReWiehPageVo>();
		try{

			String strPageNo=(null==request.getParameter("pageNo"))?"1":request.getParameter("pageNo");
		    String strPageSize=(null==request.getParameter("pageSize"))?"20":request.getParameter("pageSize");
			resultPageInfo.setPageNo(Integer.parseInt(strPageNo));
			resultPageInfo.setPageSize(Integer.parseInt(strPageSize));
			reWiehPagePageQueryRequest.setPageInfo(resultPageInfo);
			IRechargeWithdrawalsSV queryRechargeWithdrawalsSV = DubboConsumerFactory.getService(IRechargeWithdrawalsSV.class);
			ReWiehPageQueryResponse rechargewithdrawalsPage = queryRechargeWithdrawalsSV.rechargewithdrawalsPage(reWiehPagePageQueryRequest);
			if (rechargewithdrawalsPage.getResponseHeader().isSuccess()) {
				PageInfo<ReWiehPageVo> pageInfo = rechargewithdrawalsPage.getPageInfo();
				BeanUtils.copyProperties(resultPageInfo, pageInfo);
				List<ReWiehPageVo> result = pageInfo.getResult();
				resultPageInfo.setResult(result);
				responseData = new ResponseData<PageInfo<ReWiehPageVo>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功",resultPageInfo);
			}else {
				responseData = new ResponseData<PageInfo<ReWiehPageVo>>(ResponseData.AJAX_STATUS_FAILURE, "查询失败", null);
			}
		} catch (Exception e) {
			logger.error("查询用途列表失败：", e);
			responseData = new ResponseData<PageInfo<ReWiehPageVo>>(ResponseData.AJAX_STATUS_FAILURE, "查询信息异常", null);
		}
	    return responseData;
    }
    /**
     * 批量确认
     */
    @RequestMapping("/allChangeState")
    @ResponseBody
    public ResponseData<Boolean> allChangeState(HttpServletRequest request,String ids){
    	String[] wid = ids.split(",");
    	for(String item: wid){
    		ChangeStateRequest param = new ChangeStateRequest();
    		param.setWid(item);
    		IRechargeWithdrawalsSV queryRechargeWithdrawalsSV = DubboConsumerFactory.getService(IRechargeWithdrawalsSV.class);
    		BaseResponse updateStateConfirm = queryRechargeWithdrawalsSV.updateStateConfirm(param);
    		
    		QueryByIdRequest QueryByIdRequest = new QueryByIdRequest();
    		QueryByIdRequest.setWid(item);
    		QueryInfoResponse queryRechWith = queryRechargeWithdrawalsSV.queryRechWith(QueryByIdRequest);
    		
    		if(queryRechWith.getType().equals("1")){
    	        IDepositSV iDepositSV = DubboConsumerFactory.getService(IDepositSV.class);
    	        DepositParamGeneral depositParamGeneral = new DepositParamGeneral();
    	        
    	        TransSummary summary = new TransSummary();
    	        summary.setAmount(new Double(Long.valueOf(queryRechWith.getMoney()).longValue()).longValue());
    	        //资金科目ID,从公共域查,该充值模块为预存款,科目编码100000
    	        summary.setSubjectId(Long.parseLong("100000"));
    	        List<TransSummary> transSummaryList = new ArrayList<TransSummary>();
    	        transSummaryList.add(summary);
    	        depositParamGeneral.setTransSummary(transSummaryList);
    	        depositParamGeneral.setSystemId("Cloud-UAC_WEB");
    	        depositParamGeneral.setOptType("1");
    	        /*depositParamGeneral.setAccountId();*/
//    	        depositParamGeneral.setBusiSerialNo();
    	        depositParamGeneral.setCurrencyUnit(queryRechWith.getCurrency());
    	        
    	        
    	        
    	        
    	        
    	        
        		if(updateStateConfirm==null){
        			return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
        		}
    		}else{
    			IDeductSV iDeductSV = DubboConsumerFactory.getService(IDeductSV.class);
        		DeductParamGeneral deductParamGeneral = new DeductParamGeneral();
        		deductParamGeneral.setSystemId("Cloud-UAC_WEB");
        		deductParamGeneral.setExternalId(queryRechWith.getWid());
        		deductParamGeneral.setBusinessCode("300000");
        		/*deductParamGeneral.getAccountId();*/
        		deductParamGeneral.setOptType("3");
        		deductParamGeneral.setTotalAmount(Long.valueOf(queryRechWith.getMoney()).longValue());
        		deductParamGeneral.setCurrencyUnit(queryRechWith.getCurrency());
        		deductParamGeneral.setChannel(queryRechWith.getPattem());
        		deductParamGeneral.setBusiDesc(queryRechWith.getApplyRemark());
        		iDeductSV.deductFundGeneral(deductParamGeneral);
        		if(updateStateConfirm==null){
        			return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
        		}
    		}
    	}
    	return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_SUCCESS, "批量确认成功", true);
    }
    
    /**
     * 确认
     */
    @RequestMapping("/changeStateConfirm")
    @ResponseBody
    public ResponseData<Boolean> changeStateConfirm(HttpServletRequest request,String wid){
    	ChangeStateRequest param = new ChangeStateRequest();
		param.setWid(wid);
		IRechargeWithdrawalsSV queryRechargeWithdrawalsSV = DubboConsumerFactory.getService(IRechargeWithdrawalsSV.class);
		BaseResponse updateStateConfirm = queryRechargeWithdrawalsSV.updateStateConfirm(param);
		QueryByIdRequest QueryByIdRequest = new QueryByIdRequest();
		QueryByIdRequest.setWid(wid);
		QueryInfoResponse queryRechWith = queryRechargeWithdrawalsSV.queryRechWith(QueryByIdRequest);
		
		
		
		
		
		IDeductSV iDeductSV = DubboConsumerFactory.getService(IDeductSV.class);
		DeductParamGeneral deductParamGeneral = new DeductParamGeneral();
		deductParamGeneral.setSystemId("Cloud-UAC_WEB");
		deductParamGeneral.setExternalId(queryRechWith.getWid());
		deductParamGeneral.setBusinessCode("300000");
		/*deductParamGeneral.getAccountId();*/
		deductParamGeneral.setOptType(queryRechWith.getType());
		deductParamGeneral.setTotalAmount(Long.valueOf(queryRechWith.getMoney()).longValue());
		deductParamGeneral.setCurrencyUnit(queryRechWith.getCurrency());
		deductParamGeneral.setChannel(queryRechWith.getPattem());
		deductParamGeneral.setBusiDesc(queryRechWith.getApplyRemark());
		iDeductSV.deductFundGeneral(deductParamGeneral);
		if(updateStateConfirm==null){
			return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
		}
    	return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_SUCCESS, "删除题库成功", true);
    }
    
    /**
     * 锁定
     */
    @RequestMapping("/changeStateLock")
    @ResponseBody
    public ResponseData<Boolean> changeStateLock(HttpServletRequest request,String wid){
    	ChangeStateRequest param = new ChangeStateRequest();
		param.setWid(wid);
		IRechargeWithdrawalsSV queryRechargeWithdrawalsSV = DubboConsumerFactory.getService(IRechargeWithdrawalsSV.class);
		BaseResponse updateStateLock = queryRechargeWithdrawalsSV.updateStateLock(param);
		if(updateStateLock==null){
			return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
		}
    	return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_SUCCESS, "删除题库成功", true);
    }
    
    
}
