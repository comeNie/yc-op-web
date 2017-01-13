package com.ai.yc.op.web.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ai.opt.sso.client.filter.SSOClientUtil;

/**
 * 登出controller
 * @author Zh
 *
 */
@Controller
public class LogoutController {
	private static final Logger LOG = LoggerFactory.getLogger(LogoutController.class);

	/**
	 * 单点登出
	 * @param request
	 * @param response
	 */
	@RequestMapping("/ssologout")
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		String logOutServerUrl = SSOClientUtil.getLogOutServerUrlRuntime(request);
		String logOutBackUrl = SSOClientUtil.getLogOutBackUrlRuntime(request);
		try {
			response.sendRedirect(logOutServerUrl + "?service=" + logOutBackUrl);;
			LOG.error("+++++++++++++++++logout is success");
		} catch (Exception e) {
			LOG.error("用户登出失败", e); 
		}

	}

}
