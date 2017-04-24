package com.ai.yc.op.web.controller.sysduad;

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
import com.ai.yc.common.api.sysduad.interfaces.IQuerySysDuadSV;
import com.ai.yc.common.api.sysduad.param.DuadPageQueryRequest;
import com.ai.yc.common.api.sysduad.param.DuadPageQueryResponse;
import com.ai.yc.common.api.sysduad.param.DuadPageVo;
import com.ai.yc.common.api.sysduad.param.SaveSysDuad;

@Controller
@RequestMapping("/sysduad")
public class SysDuadListController {
	
	private static final Logger logger = Logger.getLogger(SysDuadListController.class);
	
	@RequestMapping("/toSysDuadList")
	public ModelAndView toSysDomainList(HttpServletRequest request) {
		return new ModelAndView("jsp/sysduad/sysDuadList");
	}
	/**
	 * 跳转到添加页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/toAddDuad")
	public ModelAndView toAddDomain(HttpServletRequest request) {
		return new ModelAndView("jsp/sysduad/addDuad");
	}
	
	/**
     * 语言对查询
     */
    @RequestMapping("/getSysDuadPageData")
    @ResponseBody
    public ResponseData<PageInfo<DuadPageVo>> getList(HttpServletRequest request,DuadPageQueryRequest duadPageQueryRequest)throws Exception{
    	ResponseData<PageInfo<DuadPageVo>> responseData = null;
    	PageInfo<DuadPageVo> resultPageInfo  = new PageInfo<DuadPageVo>();
		try{

			String strPageNo=(null==request.getParameter("pageNo"))?"1":request.getParameter("pageNo");
		    String strPageSize=(null==request.getParameter("pageSize"))?"20":request.getParameter("pageSize");
			resultPageInfo.setPageNo(Integer.parseInt(strPageNo));
			resultPageInfo.setPageSize(Integer.parseInt(strPageSize));
			duadPageQueryRequest.setPageInfo(resultPageInfo);
			IQuerySysDuadSV querySysDuadSV = DubboConsumerFactory.getService(IQuerySysDuadSV.class);
			DuadPageQueryResponse queryDuadPage = querySysDuadSV.queryDuadPage(duadPageQueryRequest);
			
			if (queryDuadPage.getResponseHeader().isSuccess()) {
				PageInfo<DuadPageVo> pageInfo = queryDuadPage.getPageInfo();
				BeanUtils.copyProperties(resultPageInfo, pageInfo);
				List<DuadPageVo> result = pageInfo.getResult();
				resultPageInfo.setResult(result);
				responseData = new ResponseData<PageInfo<DuadPageVo>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功",resultPageInfo);
			}else {
				responseData = new ResponseData<PageInfo<DuadPageVo>>(ResponseData.AJAX_STATUS_FAILURE, "查询失败", null);
			}
		} catch (Exception e) {
			logger.error("查询语言对列表失败：", e);
			responseData = new ResponseData<PageInfo<DuadPageVo>>(ResponseData.AJAX_STATUS_FAILURE, "查询信息异常", null);
		}
	    return responseData;
    }
    
    
    /**
     * 添加语言对
     */
    @RequestMapping("/insertSysDuad")
    @ResponseBody
    public ResponseData<Boolean> insertSysDuad(SaveSysDuad req){
    	/*GeneralSSOClientUser loginUser = LoginUtil.getLoginUser();
    	req.setCreateOperator(loginUser.getLoginName());*/
    	req.setDuadId("114");
    	IQuerySysDuadSV querySysDuadSV = DubboConsumerFactory.getService(IQuerySysDuadSV.class);
    	BaseResponse saveSysDuad = querySysDuadSV.saveSysDuad(req);
    	if(saveSysDuad.getResponseHeader().getIsSuccess()==false){
			return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
		}
		return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_SUCCESS, "添加语言对成功", true);
    }
    /**
     * 修改语言对
     */
    @RequestMapping("/updateSysDuad")
    @ResponseBody
    public ResponseData<Boolean> updateSysDuad(SaveSysDuad req){
    	/*GeneralSSOClientUser loginUser = LoginUtil.getLoginUser();
    	req.setCreateOperator(loginUser.getLoginName());*/
    	IQuerySysDuadSV querySysDuadSV = DubboConsumerFactory.getService(IQuerySysDuadSV.class);
    	BaseResponse updateSysDuad = querySysDuadSV.updateSysDuad(req);
    	if(updateSysDuad.getResponseHeader().getIsSuccess()==false){
			return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
		}
		return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_SUCCESS, "添加语言对成功", true);
    }
	
}
