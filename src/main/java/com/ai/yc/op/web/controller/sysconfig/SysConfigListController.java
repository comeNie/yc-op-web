package com.ai.yc.op.web.controller.sysconfig;


import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.yc.common.api.sysconfig.interfaces.IQuerySysConfigSV;
import com.ai.yc.common.api.sysconfig.param.CommissionConfig;
import com.ai.yc.common.api.sysconfig.param.DonateIntegralConfig;
import com.ai.yc.common.api.sysconfig.param.HomeDataEidtConfig;
import com.ai.yc.common.api.sysconfig.param.MemberConfig;
import com.ai.yc.common.api.sysconfig.param.NoticeConfig;
import com.ai.yc.common.api.sysconfig.param.SaveSysConfig;
@Controller
@RequestMapping("/sysconfig")
public class SysConfigListController {
	
	@RequestMapping("/toSysConfigList")
	public ModelAndView toSysConfigList(HttpServletRequest request, Model model) {
		IQuerySysConfigSV querySysConfigSV = DubboConsumerFactory.getService(IQuerySysConfigSV.class);
		
		MemberConfig memberConfig = querySysConfigSV.getMemberConfig();
		DonateIntegralConfig donateIntegralConfig = querySysConfigSV.getDonateIntegralConfig();
		HomeDataEidtConfig homeDataEidtConfig = querySysConfigSV.getHomeDataEidtConfig();
		NoticeConfig noticeConfig = querySysConfigSV.getNoticeConfig();
		CommissionConfig commissionConfig = querySysConfigSV.getCommissionConfig();
		model.addAttribute("memberConfig", memberConfig);
		model.addAttribute("donateIntegralConfig", donateIntegralConfig);
		model.addAttribute("homeDataEidtConfig", homeDataEidtConfig);
		model.addAttribute("noticeConfig", noticeConfig);
		model.addAttribute("commissionConfig", commissionConfig);
		return new ModelAndView("jsp/sysconfig/sysConfigList");
	}
	/**
     * 修改用途
     */
    @RequestMapping("/updateSysConfig")
    @ResponseBody
    public ResponseData<Boolean> updateSysConfig(SaveSysConfig req){
    	/*GeneralSSOClientUser loginUser = LoginUtil.getLoginUser();
    	req.setCreateOperator(loginUser.getLoginName());
    	req.setCreateOperatorId(loginUser.getUserId());
    	long time = DateUtil.getSysDate().getTime();
    	req.setUpdatetime(time);
    	req.setLanguage("1");*/
    	IQuerySysConfigSV querySysConfigSV = DubboConsumerFactory.getService(IQuerySysConfigSV.class);
    	BaseResponse saveConfig = querySysConfigSV.saveSysConfig(req);
    	if(saveConfig.getResponseHeader().getIsSuccess()==false){
			return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
		}
		return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_SUCCESS, "保存成功", true);
    }
}