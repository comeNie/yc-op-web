package com.ai.yc.op.web.controller.sysitembank;

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
import com.ai.yc.common.api.sysduad.interfaces.IQuerySysDuadSV;
import com.ai.yc.common.api.sysduad.param.QuerySysDuadDetailsRes;
import com.ai.yc.common.api.sysitembank.interfaces.IQuerySysItemBankSV;
import com.ai.yc.common.api.sysitembank.param.DeleteSysItemBank;
import com.ai.yc.common.api.sysitembank.param.ItemBankPageQueryRequest;
import com.ai.yc.common.api.sysitembank.param.ItemBankPageQueryResponse;
import com.ai.yc.common.api.sysitembank.param.ItemBankPageVo;
import com.ai.yc.common.api.sysitembank.param.SaveSysItemBank;
import com.ai.yc.op.web.model.sso.client.GeneralSSOClientUser;
import com.ai.yc.op.web.utils.LoginUtil;

@Controller
@RequestMapping("/sysitembank")
public class SysItemBankListController {
	
	private static final Logger logger = Logger.getLogger(SysItemBankListController.class);
	
	@RequestMapping("/toSysItemBankList")
	public ModelAndView toSysItemBankList(HttpServletRequest request) {
		return new ModelAndView("jsp/sysitembank/sysItemBankList");
	}
	
	/**
	 * 跳转到添加页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/toAddSysItemBank")
	public ModelAndView toAddPurpose(HttpServletRequest request) {
		return new ModelAndView("jsp/sysitembank/addSysItemBank");
	}
	@RequestMapping("/toQuestionsList")
	public ModelAndView toQuestionsList(String bid,String questionType) {
		ModelAndView view = new ModelAndView("jsp/sysitembank/sysQuestionsChoiceList");
		ModelAndView view1 = new ModelAndView("jsp/sysitembank/sysQuestionsList");
		if(questionType.equals("0")){
			view.addObject("bid", bid);
			view.addObject("questionType", questionType);
			return view;
		}else{
			view1.addObject("bid", bid);
			view1.addObject("questionType", questionType);
			return view1;
		}
		
	}

	/**
     * 题库查询
     */
    @RequestMapping("/getSysItemBankPageData")
    @ResponseBody
    public ResponseData<PageInfo<ItemBankPageVo>> getList(HttpServletRequest request,ItemBankPageQueryRequest itemBankPageQueryRequest)throws Exception{
    	ResponseData<PageInfo<ItemBankPageVo>> responseData = null;
    	PageInfo<ItemBankPageVo> resultPageInfo  = new PageInfo<ItemBankPageVo>();
		try{

			String strPageNo=(null==request.getParameter("pageNo"))?"1":request.getParameter("pageNo");
		    String strPageSize=(null==request.getParameter("pageSize"))?"20":request.getParameter("pageSize");
			resultPageInfo.setPageNo(Integer.parseInt(strPageNo));
			resultPageInfo.setPageSize(Integer.parseInt(strPageSize));
			itemBankPageQueryRequest.setPageInfo(resultPageInfo);
			
			IQuerySysItemBankSV querySysItemBankSV = DubboConsumerFactory.getService(IQuerySysItemBankSV.class);
			ItemBankPageQueryResponse queryItemBankPageQueryResponse = querySysItemBankSV.queryItemBankPage(itemBankPageQueryRequest);
			List<ItemBankPageVo> result2 = queryItemBankPageQueryResponse.getPageInfo().getResult();
			
			for (ItemBankPageVo itemBankPageVo : result2) {
				if(itemBankPageVo.getLangDir() != null){
					IQuerySysDuadSV querySysDuadSV = DubboConsumerFactory.getService(IQuerySysDuadSV.class);
					QuerySysDuadDetailsRes querySysDuadDetails = querySysDuadSV.querySysDuadDetails(itemBankPageVo.getLangDir());
					itemBankPageVo.setLangDir(querySysDuadDetails.getSourceCn()+"->"+querySysDuadDetails.getTargetCn());
				}
			}
			if (queryItemBankPageQueryResponse.getResponseHeader().isSuccess()) {
				PageInfo<ItemBankPageVo> pageInfo = queryItemBankPageQueryResponse.getPageInfo();
				BeanUtils.copyProperties(resultPageInfo, pageInfo);
				List<ItemBankPageVo> result = pageInfo.getResult();
				resultPageInfo.setResult(result);
				responseData = new ResponseData<PageInfo<ItemBankPageVo>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功",resultPageInfo);
			}else {
				responseData = new ResponseData<PageInfo<ItemBankPageVo>>(ResponseData.AJAX_STATUS_FAILURE, "查询失败", null);
			}
		} catch (Exception e) {
			logger.error("查询题库列表失败：", e);
			responseData = new ResponseData<PageInfo<ItemBankPageVo>>(ResponseData.AJAX_STATUS_FAILURE, "查询信息异常", null);
		}
	    return responseData;
    }
    
    /**
     * 添加题库
     */
    @RequestMapping("/insertSysItemBank")
    @ResponseBody
    public ResponseData<Boolean> insertSysItemBank(SaveSysItemBank req){
    	GeneralSSOClientUser loginUser = LoginUtil.getLoginUser();
    	req.setCreateOperatorId(loginUser.getUserId());
    	req.setAditor(loginUser.getUsername());
    	Timestamp sysDate = DateUtil.getSysDate();
    	req.setCreateTime(sysDate);
    	
    	IQuerySysItemBankSV querySysItemBankSV = DubboConsumerFactory.getService(IQuerySysItemBankSV.class);
    	BaseResponse saveSysItemBank = querySysItemBankSV.saveSysItemBank(req);
    	if(saveSysItemBank.getResponseHeader().getIsSuccess()==false){
			return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
		}
		return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_SUCCESS, "添加题库成功", true);
    }
    
    /**
     * 逻辑删除
     */
    @RequestMapping("/deleteSysItemBank")
    @ResponseBody
    public ResponseData<Boolean> deleteSysItemBank(String bid){
    	DeleteSysItemBank deleteSysItemBank = new DeleteSysItemBank();
    	deleteSysItemBank.setBid(bid);
    	IQuerySysItemBankSV querySysItemBankSV = DubboConsumerFactory.getService(IQuerySysItemBankSV.class);
    	Integer deleteSysItemBankResponse = querySysItemBankSV.deleteSysItemBank(deleteSysItemBank);
    	if(deleteSysItemBankResponse==null){
			return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
		}
		return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_SUCCESS, "删除题库成功", true);
    }
    
    /**
     * 修改题库
     */
    @RequestMapping("/updateSysItemBank")
    @ResponseBody
    public ResponseData<Boolean> updateSysItemBank(SaveSysItemBank req){
    	GeneralSSOClientUser loginUser = LoginUtil.getLoginUser();
    	req.setCreateOperatorId(loginUser.getUserId());
    	req.setAditor(loginUser.getUsername());
    	Timestamp sysDate = DateUtil.getSysDate();
    	req.setCreateTime(sysDate);
    	IQuerySysItemBankSV querySysItemBankSV = DubboConsumerFactory.getService(IQuerySysItemBankSV.class);
    	BaseResponse updateSysItemBank = querySysItemBankSV.updateSysItemBank(req);
    	
    	if(updateSysItemBank.getResponseHeader().getIsSuccess()==false){
			return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
		}
		return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_SUCCESS, "修改题库成功", true);
    }
    
}
