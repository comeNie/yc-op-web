package com.ai.yc.op.web.common.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.opt.base.exception.SystemException;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.yc.common.api.sysdomain.interfaces.IQuerySysDomainSV;
import com.ai.yc.common.api.sysdomain.param.QuerySysDomainListReq;
import com.ai.yc.common.api.sysdomain.param.QuerySysDomainListRes;
import com.ai.yc.common.api.sysdomain.param.SysDomainVo;
import com.ai.yc.common.api.syspurpose.interfaces.IQuerySysPurposeSV;
import com.ai.yc.common.api.syspurpose.param.QuerySysPurposeListReq;
import com.ai.yc.common.api.syspurpose.param.QuerySysPurposeListRes;
import com.ai.yc.common.api.syspurpose.param.SysPurposeVo;
import com.ai.yc.op.web.controller.order.OrdOrderController;

@Controller
@RequestMapping("/sys")
public class SysCommonController {
	
	private static final Logger log = LoggerFactory
			.getLogger(OrdOrderController.class);

	@RequestMapping(value = "/querySysDomainList")
	@ResponseBody
	public ResponseData<List<SysDomainVo>>  querySysDomainList(){
		IQuerySysDomainSV iQuerySysDomainSV  = DubboConsumerFactory.getService(IQuerySysDomainSV.class);
		QuerySysDomainListRes res = null;
		QuerySysDomainListReq req = new QuerySysDomainListReq();
		try {
			req.setLanguage("1");
			res =iQuerySysDomainSV.querySysDomainList(req);
		} catch (SystemException e) {
			log.error("系统错误，请稍后重试", e);
			return new ResponseData<List<SysDomainVo>>(
					ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
		}
		if(res==null){
			log.error("系统错误，请稍后重试");
			return new ResponseData<List<SysDomainVo>>(
					ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
		}
		if(!res.getResponseHeader().isSuccess()){
			log.error(res.getResponseHeader().getResultMessage());
			return new ResponseData<List<SysDomainVo>>(
					ResponseData.AJAX_STATUS_FAILURE, res.getResponseHeader().getResultMessage(), null);
		}
		return new ResponseData<List<SysDomainVo>>(
				ResponseData.AJAX_STATUS_SUCCESS, "查询成功", res.getDomainVos());
	}
	
	
	
	@RequestMapping(value = "/querySysPurposeList")
	@ResponseBody
	public ResponseData<List<SysPurposeVo>>  querySysPurposeList(){
		IQuerySysPurposeSV iQuerySysPurposeSV  = DubboConsumerFactory.getService(IQuerySysPurposeSV.class);
		QuerySysPurposeListRes res = null;
		QuerySysPurposeListReq req = new QuerySysPurposeListReq();
		try {
			req.setLanguage("1");
			res = iQuerySysPurposeSV.querySysPurposeList(req);
		} catch (SystemException e) {
			log.error("系统错误，请稍后重试", e);
			return new ResponseData<List<SysPurposeVo>>(
					ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
		}
		if(res==null){
			log.error("系统错误，请稍后重试");
			return new ResponseData<List<SysPurposeVo>>(
					ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
		}
		if(!res.getResponseHeader().isSuccess()){
			log.error(res.getResponseHeader().getResultMessage());
			return new ResponseData<List<SysPurposeVo>>(
					ResponseData.AJAX_STATUS_FAILURE, res.getResponseHeader().getResultMessage(), null);
		}
		return new ResponseData<List<SysPurposeVo>>(
				ResponseData.AJAX_STATUS_SUCCESS, "查询成功", res.getPurposes());
	}
	
	
}
