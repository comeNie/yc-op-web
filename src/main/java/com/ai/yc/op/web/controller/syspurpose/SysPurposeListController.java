package com.ai.yc.op.web.controller.syspurpose;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.yc.common.api.syspurpose.interfaces.IQuerySysPurposeSV;
import com.ai.yc.common.api.syspurpose.param.CheckPurposeCn;
import com.ai.yc.common.api.syspurpose.param.DeleteSysPurpose;
import com.ai.yc.common.api.syspurpose.param.PurposePageQueryRequest;
import com.ai.yc.common.api.syspurpose.param.PurposePageQueryResponse;
import com.ai.yc.common.api.syspurpose.param.PurposePageVo;
import com.ai.yc.common.api.syspurpose.param.SaveSysPurpose;
import com.ai.yc.op.web.model.sso.client.GeneralSSOClientUser;
import com.ai.yc.op.web.utils.LoginUtil;

@Controller
@RequestMapping("/syspurpose")
public class SysPurposeListController {
	
	private static final Logger logger = Logger.getLogger(SysPurposeListController.class);
	
	@RequestMapping("/toSysPurposeList")
	public ModelAndView toSysPurposeList(HttpServletRequest request) {
		return new ModelAndView("jsp/syspurpose/sysPurposeList");
	}
	/**
	 * 跳转到添加页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/toAddPurpose")
	public ModelAndView toAddPurpose(HttpServletRequest request) {
		return new ModelAndView("jsp/syspurpose/addSysPurpose");
	}
	/**
	 * 跳转到修改页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/toUpdatePurpose")
	public ModelAndView toUpdatesPurpose(HttpServletRequest request,String purposeId) {
		ModelAndView view = new ModelAndView("jsp/syspurpose/updatePurpose");
		view.addObject("purposeId", purposeId);
		return view;
	}
	
	/**
     * 用途查询
     */
    @RequestMapping("/getSysPurposePageData")
    @ResponseBody
    public ResponseData<PageInfo<PurposePageVo>> getList(HttpServletRequest request,PurposePageQueryRequest purposePageQueryRequest)throws Exception{
    	ResponseData<PageInfo<PurposePageVo>> responseData = null;
    	PageInfo<PurposePageVo> resultPageInfo  = new PageInfo<PurposePageVo>();
		try{

			String strPageNo=(null==request.getParameter("pageNo"))?"1":request.getParameter("pageNo");
		    String strPageSize=(null==request.getParameter("pageSize"))?"20":request.getParameter("pageSize");
			resultPageInfo.setPageNo(Integer.parseInt(strPageNo));
			resultPageInfo.setPageSize(Integer.parseInt(strPageSize));
			purposePageQueryRequest.setPageInfo(resultPageInfo);
			IQuerySysPurposeSV querySysPurposeSV = DubboConsumerFactory.getService(IQuerySysPurposeSV.class);
			PurposePageQueryResponse purposePageQueryResponse = querySysPurposeSV.queryPurposePage(purposePageQueryRequest);
			if (purposePageQueryResponse.getResponseHeader().isSuccess()) {
				PageInfo<PurposePageVo> pageInfo = purposePageQueryResponse.getPageInfo();
				BeanUtils.copyProperties(resultPageInfo, pageInfo);
				List<PurposePageVo> result = pageInfo.getResult();
				resultPageInfo.setResult(result);
				responseData = new ResponseData<PageInfo<PurposePageVo>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功",resultPageInfo);
			}else {
				responseData = new ResponseData<PageInfo<PurposePageVo>>(ResponseData.AJAX_STATUS_FAILURE, "查询失败", null);
			}
		} catch (Exception e) {
			logger.error("查询用途列表失败：", e);
			responseData = new ResponseData<PageInfo<PurposePageVo>>(ResponseData.AJAX_STATUS_FAILURE, "查询信息异常", null);
		}
	    return responseData;
    }
    
    
    /**
     * 添加用途
     */
    @RequestMapping("/insertSysPurpose")
    @ResponseBody
    public ResponseData<Boolean> insertSysPurpose(SaveSysPurpose req){
    	GeneralSSOClientUser loginUser = LoginUtil.getLoginUser();
    	req.setCreateOperator(loginUser.getLoginName());
    	long time = DateUtil.getSysDate().getTime();
    	req.setUpdatetime(time);
    	IQuerySysPurposeSV querySysPurposeSV = DubboConsumerFactory.getService(IQuerySysPurposeSV.class);
    	BaseResponse saveSysPurpose = querySysPurposeSV.saveSysPurpose(req);
    	if(saveSysPurpose.getResponseHeader().getIsSuccess()==false){
			return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
		}
		return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_SUCCESS, "添加用途成功", true);
    }
    /**
     * 修改用途
     */
    @RequestMapping("/updateSysPurpose")
    @ResponseBody
    public ResponseData<Boolean> updateSysPurpose(SaveSysPurpose req){
    	GeneralSSOClientUser loginUser = LoginUtil.getLoginUser();
    	req.setCreateOperator(loginUser.getLoginName());
    	long time = DateUtil.getSysDate().getTime();
    	req.setUpdatetime(time);
    	IQuerySysPurposeSV querySysPurposeSV = DubboConsumerFactory.getService(IQuerySysPurposeSV.class);
    	BaseResponse updateSysPurpose = querySysPurposeSV.updateSysPurpose(req);
    	if(updateSysPurpose.getResponseHeader().getIsSuccess()==false){
			return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
		}
		return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_SUCCESS, "修改用途成功", true);
    }
    /**
     * 删除用途
     */
    @RequestMapping("/deleteSysPurpose")
    @ResponseBody
    public ResponseData<Boolean> deleteSysPurpose(String purposeId){
    	DeleteSysPurpose deleteSysPurpose = new DeleteSysPurpose();
    	deleteSysPurpose.setPurposeId(purposeId);
    	IQuerySysPurposeSV querySysPurposeSV = DubboConsumerFactory.getService(IQuerySysPurposeSV.class);
    	Integer deleteSysPurposeResponse = querySysPurposeSV.deleteSysPurpose(deleteSysPurpose);
    	if(deleteSysPurposeResponse==null){
			return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
		}
		return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_SUCCESS, "删除用途成功", true);
    }
    /**
     * 同语言下的已存在相同名称的用途不可再次新建
     */
    @RequestMapping("/checkPurposeCn")
    @ResponseBody
    public Integer checkPurposeCn(CheckPurposeCn param)throws Exception{
    	IQuerySysPurposeSV querySysPurposeSV = DubboConsumerFactory.getService(IQuerySysPurposeSV.class);
    	Integer checkPurposeCn = querySysPurposeSV.checkPurposeCn(param);
		return checkPurposeCn;
    }

	
}
