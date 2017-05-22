package com.ai.yc.op.web.controller.balance;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;

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
}
