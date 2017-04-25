package com.ai.yc.op.web.controller.sysdomain;

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
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.yc.common.api.sysdomain.interfaces.IQuerySysDomainSV;
import com.ai.yc.common.api.sysdomain.param.CheckDomainCn;
import com.ai.yc.common.api.sysdomain.param.DeleteSysDomain;
import com.ai.yc.common.api.sysdomain.param.DomainPageQueryResponse;
import com.ai.yc.common.api.sysdomain.param.DomainPageVo;
import com.ai.yc.common.api.sysdomain.param.DomainQueryRequest;
import com.ai.yc.common.api.sysdomain.param.SaveSysDomain;
import com.ai.yc.common.api.syspurpose.interfaces.IQuerySysPurposeSV;
import com.ai.yc.common.api.syspurpose.param.CheckPurposeCn;

@Controller
@RequestMapping("/sysdomain")
public class SysDomainListController {
	
	private static final Logger logger = Logger.getLogger(SysDomainListController.class);
	
	@RequestMapping("/toSysDomainList")
	public ModelAndView toSysDomainList(HttpServletRequest request) {
		return new ModelAndView("jsp/sysdomain/sysDomainList");
	}
	/**
	 * 跳转到添加页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/toAddDomain")
	public ModelAndView toAddDomain(HttpServletRequest request) {
		return new ModelAndView("jsp/sysdomain/addDomain");
	}
	
	/**
     * 领域查询
     */
    @RequestMapping("/getSysDomainPageData")
    @ResponseBody
    public ResponseData<PageInfo<DomainPageVo>> getList(HttpServletRequest request,DomainQueryRequest domainQueryRequest)throws Exception{
    	ResponseData<PageInfo<DomainPageVo>> responseData = null;
    	PageInfo<DomainPageVo> resultPageInfo  = new PageInfo<DomainPageVo>();
		try{

			String strPageNo=(null==request.getParameter("pageNo"))?"1":request.getParameter("pageNo");
		    String strPageSize=(null==request.getParameter("pageSize"))?"20":request.getParameter("pageSize");
			resultPageInfo.setPageNo(Integer.parseInt(strPageNo));
			resultPageInfo.setPageSize(Integer.parseInt(strPageSize));
			domainQueryRequest.setPageInfo(resultPageInfo);
			IQuerySysDomainSV querySysDomainSV = DubboConsumerFactory.getService(IQuerySysDomainSV.class);
			DomainPageQueryResponse domainPageQueryResponse = querySysDomainSV.queryDomainPage(domainQueryRequest);
			
			if (domainPageQueryResponse.getResponseHeader().isSuccess()) {
				PageInfo<DomainPageVo> pageInfo = domainPageQueryResponse.getPageInfo();
				BeanUtils.copyProperties(resultPageInfo, pageInfo);
				List<DomainPageVo> result = pageInfo.getResult();
				resultPageInfo.setResult(result);
				responseData = new ResponseData<PageInfo<DomainPageVo>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功",resultPageInfo);
			}else {
				responseData = new ResponseData<PageInfo<DomainPageVo>>(ResponseData.AJAX_STATUS_FAILURE, "查询失败", null);
			}
		} catch (Exception e) {
			logger.error("查询领域列表失败：", e);
			responseData = new ResponseData<PageInfo<DomainPageVo>>(ResponseData.AJAX_STATUS_FAILURE, "查询信息异常", null);
		}
	    return responseData;
    }
    
    
    /**
     * 添加领域
     */
    @RequestMapping("/insertSysDomain")
    @ResponseBody
    public ResponseData<Boolean> insertSysDomain(SaveSysDomain req){
    	/*GeneralSSOClientUser loginUser = LoginUtil.getLoginUser();
    	req.setCreateOperator(loginUser.getLoginName());*/
    	req.setDomainId("5");
    	IQuerySysDomainSV querySysDomainSV = DubboConsumerFactory.getService(IQuerySysDomainSV.class);
    	BaseResponse saveSysDomain = querySysDomainSV.saveSysDomain(req);
    	if(saveSysDomain.getResponseHeader().getIsSuccess()==false){
			return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
		}
		return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_SUCCESS, "添加领域成功", true);
    }
    /**
     * 修改领域
     */
    @RequestMapping("/updateSysDomain")
    @ResponseBody
    public ResponseData<Boolean> updateSysDomain(SaveSysDomain req){
    	/*GeneralSSOClientUser loginUser = LoginUtil.getLoginUser();
    	req.setCreateOperator(loginUser.getLoginName());*/
    	IQuerySysDomainSV querySysDomainSV = DubboConsumerFactory.getService(IQuerySysDomainSV.class);
    	BaseResponse updateSysDomain = querySysDomainSV.updateSysDomain(req);
    	if(updateSysDomain.getResponseHeader().getIsSuccess()==false){
			return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
		}
		return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_SUCCESS, "添加领域成功", true);
    }
    /**
     * 删除领域
     */
    @RequestMapping("/deleteSysDomain")
    @ResponseBody
    public ResponseData<Boolean> deleteSysDomain(String domainId){
    	DeleteSysDomain deleteSysDomain = new DeleteSysDomain();
    	deleteSysDomain.setDomainId(domainId);
    	IQuerySysDomainSV querySysDomainSV = DubboConsumerFactory.getService(IQuerySysDomainSV.class);
    	Integer deleteSysDomainResponse = querySysDomainSV.deleteSysDomain(deleteSysDomain);
    	if(deleteSysDomainResponse==null){
			return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
		}
		return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_SUCCESS, "删除领域成功", true);
    }

    /**
     * 同语言下的已存在相同名称的领域不可再次新建
     */
    @RequestMapping("/checkDomainCn")
    @ResponseBody
    public Integer checkDomainCn(CheckDomainCn param)throws Exception{
    	IQuerySysDomainSV querySysDomainSV = DubboConsumerFactory.getService(IQuerySysDomainSV.class);
    	Integer checkDomainCn = querySysDomainSV.checkDomainCn(param);
		return checkDomainCn;
    }
}
