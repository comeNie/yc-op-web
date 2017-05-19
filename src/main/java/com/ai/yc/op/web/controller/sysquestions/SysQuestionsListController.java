package com.ai.yc.op.web.controller.sysquestions;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Timestamp;
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
import com.ai.yc.common.api.sysquestions.interfaces.IQuerySysQuestionsSV;
import com.ai.yc.common.api.sysquestions.param.DeleteSysQuestions;
import com.ai.yc.common.api.sysquestions.param.QuestionsPageQueryRequest;
import com.ai.yc.common.api.sysquestions.param.QuestionsPageQueryResponse;
import com.ai.yc.common.api.sysquestions.param.QuestionsPageVo;
import com.ai.yc.common.api.sysquestions.param.SaveSysQuestions;
import com.ai.yc.op.web.model.sso.client.GeneralSSOClientUser;
import com.ai.yc.op.web.model.utils.ImportEmployee;
import com.ai.yc.op.web.utils.LoginUtil;

@Controller
@RequestMapping("/sysquestions")
public class SysQuestionsListController {
	
	private static final Logger logger = Logger.getLogger(SysQuestionsListController.class);
	/**
	 * 跳转到选择添加页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/toAddSysQuestionsChoice")
	public ModelAndView toAddSysQuestionsChioce(HttpServletRequest request,String bid,String questionType) {
		ModelAndView view = new ModelAndView("jsp/sysitembank/addSysQuestionsChoice");
		view.addObject("bid", bid);
		view.addObject("questionType", questionType);
		return view;
	}
	/**
	 * 跳转到简答添加页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/toAddSysQuestions")
	public ModelAndView toAddSysQuestions(HttpServletRequest request,String bid,String questionType) {
		ModelAndView view = new ModelAndView("jsp/sysitembank/addSysQuestions");
		view.addObject("bid", bid);
		view.addObject("questionType", questionType);
		return view;
	}
	/**
     * 题目查询
     */
    @RequestMapping("/getSysQuestionsPageData")
    @ResponseBody
    public ResponseData<PageInfo<QuestionsPageVo>> getList(HttpServletRequest request,String bid,String questionType)throws Exception{
    	QuestionsPageQueryRequest questionsPageQueryRequest = new QuestionsPageQueryRequest();
    	questionsPageQueryRequest.setBid(bid);
    	questionsPageQueryRequest.setQtype(questionType);
    	ResponseData<PageInfo<QuestionsPageVo>> responseData = null;
    	PageInfo<QuestionsPageVo> resultPageInfo  = new PageInfo<QuestionsPageVo>();
		try{

			String strPageNo=(null==request.getParameter("pageNo"))?"1":request.getParameter("pageNo");
		    String strPageSize=(null==request.getParameter("pageSize"))?"20":request.getParameter("pageSize");
			resultPageInfo.setPageNo(Integer.parseInt(strPageNo));
			resultPageInfo.setPageSize(Integer.parseInt(strPageSize));
			questionsPageQueryRequest.setPageInfo(resultPageInfo);
			
			IQuerySysQuestionsSV querySysQuestionsSV = DubboConsumerFactory.getService(IQuerySysQuestionsSV.class);
			QuestionsPageQueryResponse queryQuestionsPageQueryResponse = querySysQuestionsSV.queryQuestionsPage(questionsPageQueryRequest);
			
			if (queryQuestionsPageQueryResponse.getResponseHeader().isSuccess()) {
				PageInfo<QuestionsPageVo> pageInfo = queryQuestionsPageQueryResponse.getPageInfo();
				BeanUtils.copyProperties(resultPageInfo, pageInfo);
				List<QuestionsPageVo> result = pageInfo.getResult();
				resultPageInfo.setResult(result);
				responseData = new ResponseData<PageInfo<QuestionsPageVo>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功",resultPageInfo);
			}else {
				responseData = new ResponseData<PageInfo<QuestionsPageVo>>(ResponseData.AJAX_STATUS_FAILURE, "查询失败", null);
			}
		} catch (Exception e) {
			logger.error("查询题目列表失败：", e);
			responseData = new ResponseData<PageInfo<QuestionsPageVo>>(ResponseData.AJAX_STATUS_FAILURE, "查询信息异常", null);
		}
	    return responseData;
    }
    
    /**
     * 添加题目
     */
    @RequestMapping("/insertSysQuestions")
    @ResponseBody
    public ResponseData<Boolean> insertSysQuestions(SaveSysQuestions req){
    	GeneralSSOClientUser loginUser = LoginUtil.getLoginUser();
    	req.setCreateOperatorId(loginUser.getUserId());
    	req.setCreateOperator(loginUser.getUsername());
    	Timestamp sysDate = DateUtil.getSysDate();
    	req.setCreateTime(sysDate);
    	IQuerySysQuestionsSV querySysQuestionsSV = DubboConsumerFactory.getService(IQuerySysQuestionsSV.class);
    	BaseResponse saveSysQuestions = querySysQuestionsSV.saveSysQuestions(req);
    	if(saveSysQuestions.getResponseHeader().getIsSuccess()==false){
			return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
		}
		return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_SUCCESS, "添加题目成功", true);
    }
    
    /**
     * 逻辑删除
     */
    @RequestMapping("/deleteSysQuestions")
    @ResponseBody
    public ResponseData<Boolean> deleteSysQuestionsChoice(String qid){
    	DeleteSysQuestions deleteSysQuestions = new DeleteSysQuestions();
    	deleteSysQuestions.setQid(qid);
    	IQuerySysQuestionsSV querySysQuestionsSV = DubboConsumerFactory.getService(IQuerySysQuestionsSV.class);
    	Integer deleteSysQuestionsResponse = querySysQuestionsSV.deleteSysQuestions(deleteSysQuestions);
    	if(deleteSysQuestionsResponse==null){
			return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
		}
		return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_SUCCESS, "删除题目成功", true);
    }
    
    /**
     * 修改题目
     */
    @RequestMapping("/updateSysQuestions")
    @ResponseBody
    public ResponseData<Boolean> updateSysQuestions(SaveSysQuestions req){
    	GeneralSSOClientUser loginUser = LoginUtil.getLoginUser();
    	req.setCreateOperatorId(loginUser.getUserId());
    	req.setCreateOperator(loginUser.getUsername());
    	Timestamp sysDate = DateUtil.getSysDate();
    	req.setCreateTime(sysDate);
    	IQuerySysQuestionsSV querySysQuestionsSV = DubboConsumerFactory.getService(IQuerySysQuestionsSV.class);
    	BaseResponse updateSysQuestions = querySysQuestionsSV.updateSysQuestions(req);
    	if(updateSysQuestions.getResponseHeader().getIsSuccess()==false){
			return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
		}
		return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_SUCCESS, "修改题目成功", true);
    }
    /**
     * 修改题目
     * @throws Exception 
     */
    @RequestMapping("/upload")
    @ResponseBody
    public void upload(HttpServletRequest request) throws Exception{
    	 String excelPath = request.getParameter("excelPath");
    	 //输入流 
    	 File excelPaths=new File(excelPath);   
    	String absolutePath = excelPaths.getAbsolutePath();
        InputStream fis = new FileInputStream(absolutePath);  
          
        //POI:得到解析Excel的实体集合  
        List<SaveSysQuestions> infos = ImportEmployee.importEmployeeByPoi(fis);  
          
        //遍历解析Excel的实体集合  
        for(SaveSysQuestions req:infos) {  
        	IQuerySysQuestionsSV querySysQuestionsSV = DubboConsumerFactory.getService(IQuerySysQuestionsSV.class);
        	querySysQuestionsSV.saveSysQuestions(req);
        }  
        //关闭流  
        fis.close();  
    }
}
