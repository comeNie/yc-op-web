package com.ai.yc.op.web.controller.order;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FramController {
	@RequestMapping("/frame")
	public ModelAndView register(HttpServletRequest request) {

		return new ModelAndView("inc/frame");
	}
}
