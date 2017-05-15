package com.ai.yc.op.web.controller.sysbasic;


import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.yc.common.api.sysbasic.interfaces.IQuerySysBasicSV;
import com.ai.yc.common.api.sysbasic.param.QuerySysBasicJfRegistListRes;
import com.ai.yc.common.api.sysbasic.param.SaveSysBasic;
@Controller
@RequestMapping("/sysbasic")
public class SysBasicListController {
	
	@RequestMapping("/toSysBasicList")
	public ModelAndView toSysPurposeList(HttpServletRequest request, Model model) {
		IQuerySysBasicSV querySysBasicSV = DubboConsumerFactory.getService(IQuerySysBasicSV.class);
		QuerySysBasicJfRegistListRes queryBasicJfRegist = querySysBasicSV.queryBasicJfRegist();
		model.addAttribute("queryBaJfRe", queryBasicJfRegist);
		return new ModelAndView("jsp/sysbasic/sysBasicList");
	}
	/**
     * 修改用途
     */
    @RequestMapping("/updateSysBasic")
    @ResponseBody
    public ResponseData<Boolean> updateSysBasic(SaveSysBasic req){
    	/*GeneralSSOClientUser loginUser = LoginUtil.getLoginUser();
    	req.setCreateOperator(loginUser.getLoginName());
    	req.setCreateOperatorId(loginUser.getUserId());
    	long time = DateUtil.getSysDate().getTime();
    	req.setUpdatetime(time);
    	req.setLanguage("1");*/
    	IQuerySysBasicSV querySysBasicSV = DubboConsumerFactory.getService(IQuerySysBasicSV.class);
    	BaseResponse saveBasic = querySysBasicSV.saveBasic(req);
    	if(saveBasic.getResponseHeader().getIsSuccess()==false){
			return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
		}
		return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_SUCCESS, "保存成功", true);
    }
}
