package com.ai.yc.op.web.controller.balance;

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
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.slp.balance.api.rechargewithdrawals.interfaces.IRechargeWithdrawalsSV;
import com.ai.slp.balance.api.rechargewithdrawals.param.ReWiehPagePageQueryRequest;
import com.ai.slp.balance.api.rechargewithdrawals.param.ReWiehPageQueryResponse;
import com.ai.slp.balance.api.rechargewithdrawals.param.ReWiehPageVo;

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
	public ModelAndView toAddRechargeWithdrawals(HttpServletRequest request) {
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
}
