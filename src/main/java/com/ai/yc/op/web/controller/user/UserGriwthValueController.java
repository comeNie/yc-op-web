package com.ai.yc.op.web.controller.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.yc.op.web.model.user.InsertUsrGriwthValueParams;
import com.ai.yc.op.web.model.user.UserUpdateParams;
import com.ai.yc.user.api.usergriwthvalue.interfaces.IYCUserGriwthValueSV;
import com.ai.yc.user.api.usergriwthvalue.param.UsrGriwthValueRequest;
import com.ai.yc.user.api.userservice.interfaces.IYCUserServiceSV;

@Controller
@RequestMapping("/ycusergriwthvalue")
public class UserGriwthValueController {
	@RequestMapping("/insertGriwthValueInfo")
	@ResponseBody
	public ResponseData<Boolean> insertGriwthValueInfo(HttpServletRequest request,InsertUsrGriwthValueParams griwthValueParams) {
		IYCUserGriwthValueSV userGriwthValueSV = DubboConsumerFactory.getService(IYCUserGriwthValueSV.class);

		try {
			if(!StringUtil.isBlank(griwthValueParams.getUserId())){
				UsrGriwthValueRequest griwthValueInfo = new UsrGriwthValueRequest();
	    		BeanUtils.copyProperties(griwthValueInfo, griwthValueParams);
				userGriwthValueSV.insertGriwthValueInfo(griwthValueInfo);
			}else{
				return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_FAILURE, "参数不正确", false);

			}
		} catch (Exception e) {
			return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_FAILURE, "添加失败", false);
		}
		
		
		return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_SUCCESS, "添加成功", true);

		
	}
}
