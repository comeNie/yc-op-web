package com.ai.yc.op.web.controller.sysconfig;


import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
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
import com.ai.yc.op.web.controller.syspurpose.SysPurposeListController;
@Controller
@RequestMapping("/sysconfig")
public class SysConfigListController {
	private static final Logger logger = Logger.getLogger(SysPurposeListController.class);
	@RequestMapping("/toSysConfigList")
	public ModelAndView toSysConfigList(HttpServletRequest request, Model model) {
		logger.info("进入基本设置页面》》》》》》》》》》》》》》》》》》》》》》》》》");
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
     * 修改基本设置
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
    	logger.info("开始修改基本设置》》》》》》》》》》》》》》》》》》》》》》》》》");
    	IQuerySysConfigSV querySysConfigSV = DubboConsumerFactory.getService(IQuerySysConfigSV.class);
    	BaseResponse saveConfig = querySysConfigSV.saveSysConfig(req);
    	if(saveConfig.getResponseHeader().getIsSuccess()==false){
			return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
		}
		return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_SUCCESS, "保存成功", true);
    }
}
