package com.ai.yc.op.web.controller.syssensitive;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.yc.common.api.syssensitive.interfaces.IQuerySysSensitiveSV;
import com.ai.yc.common.api.syssensitive.param.DeleteSysSensitive;
import com.ai.yc.common.api.syssensitive.param.SaveSysSensitive;
import com.ai.yc.common.api.syssensitive.param.SensitivePageQueryRequest;
import com.ai.yc.common.api.syssensitive.param.SensitivePageQueryResponse;
import com.ai.yc.common.api.syssensitive.param.SensitivePageVo;
import com.ai.yc.op.web.model.sso.client.GeneralSSOClientUser;
import com.ai.yc.op.web.model.utils.ImportEmployeeSensitive;
import com.ai.yc.op.web.utils.LoginUtil;

@Controller
@RequestMapping("/syssensitive")
public class SysSensitiveListController {
	
	private static final Logger logger = Logger.getLogger(SysSensitiveListController.class);

	@RequestMapping("/toSysSensitiveList")
	public ModelAndView toAddSysSensitive(HttpServletRequest request) {
		return new ModelAndView("jsp/syssensitive/sysSensitiveList");
	}
	
	/**
	 * 跳转到添加页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/toAddSysSensitive")
	public ModelAndView toAddSensitive(HttpServletRequest request) {
		return new ModelAndView("jsp/syssensitive/addSensitive");
	}
	
	/**
     * 敏感词查询
     */
    @RequestMapping("/getSysSensitivePageData")
    @ResponseBody
    public ResponseData<PageInfo<SensitivePageVo>> getList(HttpServletRequest request,SensitivePageQueryRequest sensitivePageQueryRequest)throws Exception{
    	ResponseData<PageInfo<SensitivePageVo>> responseData = null;
    	PageInfo<SensitivePageVo> resultPageInfo  = new PageInfo<SensitivePageVo>();
		try{

			String strPageNo=(null==request.getParameter("pageNo"))?"1":request.getParameter("pageNo");
		    String strPageSize=(null==request.getParameter("pageSize"))?"20":request.getParameter("pageSize");
			resultPageInfo.setPageNo(Integer.parseInt(strPageNo));
			resultPageInfo.setPageSize(Integer.parseInt(strPageSize));
			sensitivePageQueryRequest.setPageInfo(resultPageInfo);
			
			IQuerySysSensitiveSV querySysSensitiveSV = DubboConsumerFactory.getService(IQuerySysSensitiveSV.class);
			SensitivePageQueryResponse querySensitivePageQueryResponse = querySysSensitiveSV.querySensitivePage(sensitivePageQueryRequest);
			
			if (querySensitivePageQueryResponse.getResponseHeader().isSuccess()) {
				PageInfo<SensitivePageVo> pageInfo = querySensitivePageQueryResponse.getPageInfo();
				BeanUtils.copyProperties(resultPageInfo, pageInfo);
				List<SensitivePageVo> result = pageInfo.getResult();
				resultPageInfo.setResult(result);
				responseData = new ResponseData<PageInfo<SensitivePageVo>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功",resultPageInfo);
			}else {
				responseData = new ResponseData<PageInfo<SensitivePageVo>>(ResponseData.AJAX_STATUS_FAILURE, "查询失败", null);
			}
		} catch (Exception e) {
			logger.error("查询敏感词列表失败：", e);
			responseData = new ResponseData<PageInfo<SensitivePageVo>>(ResponseData.AJAX_STATUS_FAILURE, "查询信息异常", null);
		}
	    return responseData;
    }
    /**
     * 添加敏感词
     */
    @RequestMapping("/insertSysSensitive")
    @ResponseBody
    public ResponseData<Boolean> insertSysSensitive(SaveSysSensitive req){
    	GeneralSSOClientUser loginUser = LoginUtil.getLoginUser();
    	req.setCreatPeopleId(loginUser.getUserId());
    	req.setCreatPeople(loginUser.getUsername());
    	Timestamp sysDate = DateUtil.getSysDate();
    	req.setCreatTime(sysDate);
    	logger.info("controller开始添加敏感词》》》》》》》》》》》》》》》》》》》》"+req);
    	IQuerySysSensitiveSV querySysSensitiveSV = DubboConsumerFactory.getService(IQuerySysSensitiveSV.class);
    	BaseResponse saveSysSensitive = querySysSensitiveSV.saveSysSensitive(req);
    	if(saveSysSensitive.getResponseHeader().getIsSuccess()==false){
			return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
		}
		return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_SUCCESS, "添加敏感词成功", true);
    }
    /**
     * 批量删除
     */
    @RequestMapping("/deleteAllSysSensitive")
    @ResponseBody
    public ResponseData<Boolean> deleteAllSysSensitive(HttpServletRequest request,String ids){
    	String[] id = ids.split(",");
    	for(String item: id){
    		DeleteSysSensitive deleteSysSensitive = new DeleteSysSensitive();
    		deleteSysSensitive.setId(item);
    		IQuerySysSensitiveSV querySysSensitiveSV = DubboConsumerFactory.getService(IQuerySysSensitiveSV.class);
    		Integer deleteSysSensitiveRes = querySysSensitiveSV.deleteSysSensitive(deleteSysSensitive);
    		if(deleteSysSensitiveRes==null){
    			return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
    		}
    	}
    	return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_SUCCESS, "删除题库成功", true);
    }
    /**
     * 修改敏感词
     */
    @RequestMapping("/updateSysSensitive")
    @ResponseBody
    public ResponseData<Boolean> updateSysSensitive(SaveSysSensitive req){
    	GeneralSSOClientUser loginUser = LoginUtil.getLoginUser();
    	req.setCreatPeopleId(loginUser.getUserId());
    	req.setCreatPeople(loginUser.getUsername());
    	Timestamp sysDate = DateUtil.getSysDate();
    	req.setCreatTime(sysDate);
    	IQuerySysSensitiveSV querySysSensitiveSV = DubboConsumerFactory.getService(IQuerySysSensitiveSV.class);
    	BaseResponse updateSysSensitive = querySysSensitiveSV.updateSysSensitive(req);
    	if(updateSysSensitive.getResponseHeader().getIsSuccess()==false){
			return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
		}
		return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_SUCCESS, "修改敏感词成功", true);
    }
    /**
     * 删除
     */
    @RequestMapping("/deleteSysSensitive")
    @ResponseBody
    public ResponseData<Boolean> deleteSysSensitive(HttpServletRequest request,String id){
		DeleteSysSensitive deleteSysSensitive = new DeleteSysSensitive();
		deleteSysSensitive.setId(id);
		IQuerySysSensitiveSV querySysSensitiveSV = DubboConsumerFactory.getService(IQuerySysSensitiveSV.class);
		Integer deleteSysSensitiveRes = querySysSensitiveSV.deleteSysSensitive(deleteSysSensitive);
		if(deleteSysSensitiveRes==null){
			return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
		}
    	return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_SUCCESS, "删除题库成功", true);
    }
    
    /**
     * 导入敏感词
     * @throws Exception 
     */
    @RequestMapping("/upload")
    @ResponseBody
    public ResponseData<Boolean> upload(HttpServletRequest request,String bid) throws Exception{
    	 //判断请求是否为文件上传请求，若不是，则直接返回错误信息
    	if(request instanceof MultipartHttpServletRequest){
    		// 文件上传的请求
            MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
            // 获取请求的参数
            MultipartFile mFile = mRequest.getFile("file");
           //POI:得到解析Excel的实体集合  
           List<SaveSysSensitive> infos = ImportEmployeeSensitive.importEmployeeByPoi(mFile.getInputStream());  
             
           //遍历解析Excel的实体集合  
           for(SaveSysSensitive req:infos) {  
	          	 IQuerySysSensitiveSV querySysSensitiveSV = DubboConsumerFactory.getService(IQuerySysSensitiveSV.class);
	           	 BaseResponse saveSysSensitive = querySysSensitiveSV.saveSysSensitive(req);
	           	if(saveSysSensitive.getResponseHeader().getIsSuccess()==true){
	    			return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_SUCCESS, "导入选择题目成功", true);
	    		}
           }
    	}
    	return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
     }
}
