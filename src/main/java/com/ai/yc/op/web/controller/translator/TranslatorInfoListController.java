package com.ai.yc.op.web.controller.translator;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.yc.translator.api.translatorservice.interfaces.IYCUserTranslatorSV;
import com.ai.yc.translator.api.translatorservice.param.TranslatorInfo;
import com.ai.yc.translator.api.translatorservice.param.TranslatorInfoQueryResponse;
import com.ai.yc.translator.api.translatorservice.param.UsrTranslatorPageInfoRequest;
import com.ai.yc.ucenter.api.members.interfaces.IYCUcMembersSV;
import com.ai.yc.ucenter.api.members.param.UcMembersInfo;


@Controller
@RequestMapping("/translator")
public class TranslatorInfoListController {
	private static final Logger logger = Logger.getLogger("TranslatorInfoListController.class");	
	@RequestMapping("/toTranslatorInfoList")
	public ModelAndView toTranslatorInfoList(HttpServletRequest request){
		return new ModelAndView("jsp/translator/translatorInfoList");
	}
	/**
	 * 译员列表查询
	 * @param <TranslatorInfoResponse>
	 * @param <TranslatorInfoQueryRequest>
	 */
	@RequestMapping("/getTranslatorPageData")
	@ResponseBody
	public ResponseData<PageInfo<TranslatorInfo>> getList(HttpServletRequest request,UsrTranslatorPageInfoRequest PageInfoRequest)throws Exception {
		ResponseData<PageInfo<TranslatorInfo>> responseData = null;
		IYCUserTranslatorSV iYCUserTranslatorSV = DubboConsumerFactory.getService(IYCUserTranslatorSV.class);
		IYCUcMembersSV iYCUcMembersSV = DubboConsumerFactory.getService(IYCUcMembersSV.class);
		try {
			String strPageNo=(null==request.getParameter("pageNo"))?"1":request.getParameter("pageNo");
		    String strPageSize=(null==request.getParameter("pageSize"))?"10":request.getParameter("pageSize");
		    PageInfoRequest.setPageNo(Integer.parseInt(strPageNo));
		    PageInfoRequest.setPageSize(Integer.parseInt(strPageSize));
		    TranslatorInfoQueryResponse response = iYCUserTranslatorSV.queryPageInfoTranslatorInfo(PageInfoRequest);
		    List<TranslatorInfo> list = new ArrayList<TranslatorInfo>();
			for(int i=0;i<response.getPageInfo().getResult().size();i++){
				TranslatorInfo translatorInfo =  response.getPageInfo().getResult().get(i);
				UcMembersInfo ucMembersInfo = iYCUcMembersSV.queryUcMember(Integer.parseInt(translatorInfo.getUserId()));
				translatorInfo.setUsersource(ucMembersInfo.getUsersource());
				translatorInfo.setEmail(ucMembersInfo.getEmail());
				list.add(translatorInfo);
			}
		    if(response.getResponseHeader().isSuccess()){
		    	PageInfo<TranslatorInfo> resultPageInfo = response.getPageInfo();
		    	responseData = new ResponseData<PageInfo<TranslatorInfo>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功",resultPageInfo);  	
		    } else {
		    	responseData = new ResponseData<PageInfo<TranslatorInfo>>(ResponseData.AJAX_STATUS_FAILURE,"查询失败",null);
		    }			
		} catch (Exception e){
			e.printStackTrace();
			responseData = new ResponseData<PageInfo<TranslatorInfo>>(ResponseData.AJAX_STATUS_FAILURE,"查询异常",null);
		}
		return responseData;
	}

}
