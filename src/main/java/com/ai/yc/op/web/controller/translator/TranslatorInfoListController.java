package com.ai.yc.op.web.controller.translator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sso.client.filter.StringUtils;
import com.ai.yc.common.api.sysduad.interfaces.IQuerySysDuadSV;
import com.ai.yc.common.api.sysduad.param.QuerySysDuadListReq;
import com.ai.yc.common.api.sysduad.param.QuerySysDuadListRes;
import com.ai.yc.common.api.sysduad.param.SysDuadVo;
import com.ai.yc.op.web.model.order.SysDuadParam;
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
	 * 待审核列表
	 * @param request
	 * @return
     */
	@RequestMapping("/toCheckTranslatorInfoList")
	public ModelAndView tocheckTranslatorInfoList(HttpServletRequest request){
		return new ModelAndView("jsp/translator/checkTranslatorInfoList");
	}

	/**
	 * lsp列表
	 * @param request
	 * @return
     */
	@RequestMapping("/toLspList")
	public ModelAndView toLspList(HttpServletRequest request){
		return new ModelAndView("jsp/translator/lspInfoList");
	}
	/**
	 * 译员列表
	 * @param request
	 * @param PageInfoRequest
	 * @return
	 * @throws Exception
     */
	@RequestMapping("/getTranslatorPageData")
	@ResponseBody
	public ResponseData<PageInfo<TranslatorInfo>> getList(HttpServletRequest request,UsrTranslatorPageInfoRequest PageInfoRequest)throws Exception {
		ResponseData<PageInfo<TranslatorInfo>> responseData = null;
		IYCUserTranslatorSV iYCUserTranslatorSV = DubboConsumerFactory.getService(IYCUserTranslatorSV.class);
		IYCUcMembersSV iYCUcMembersSV = DubboConsumerFactory.getService(IYCUcMembersSV.class);
		QuerySysDuadListReq req = new QuerySysDuadListReq();
		IQuerySysDuadSV iQuerySysDuadSV = DubboConsumerFactory.getService(IQuerySysDuadSV.class);
		QuerySysDuadListRes res = iQuerySysDuadSV.querySysDuadList(req);
		List<SysDuadVo> infoList = res.getDuads();
		Map<String,String> usrLanguageMap = new HashMap<>();
		if (!CollectionUtil.isEmpty(infoList)){
			for (SysDuadVo sysDuadVo: infoList){
				usrLanguageMap.put(sysDuadVo.getDuadId(),sysDuadVo.getSourceCn()+"->"+sysDuadVo.getTargetCn());
			}
		}
		try {
			String strPageNo=(null==request.getParameter("pageNo"))?"1":request.getParameter("pageNo");
		    String strPageSize=(null==request.getParameter("pageSize"))?"10":request.getParameter("pageSize");
		    PageInfoRequest.setPageNo(Integer.parseInt(strPageNo));
		    PageInfoRequest.setPageSize(Integer.parseInt(strPageSize));
			if ("".equals(PageInfoRequest.getUserSource())){
				PageInfoRequest.setUserSource(null);
			}
		    TranslatorInfoQueryResponse response = iYCUserTranslatorSV.queryPageInfoTranslatorInfo(PageInfoRequest);
		    if(response.getResponseHeader().isSuccess()){
				for(int i=0;i<response.getPageInfo().getResult().size();i++){
					TranslatorInfo translatorInfo =  response.getPageInfo().getResult().get(i);
					UcMembersInfo ucMembersInfo = iYCUcMembersSV.queryUcMember(Integer.parseInt(translatorInfo.getUserId()));
					translatorInfo.setUsersource(ucMembersInfo.getUsersource());
					translatorInfo.setEmail(ucMembersInfo.getEmail());
					//语言方向
					String usrLanguage="";
					//语言方向ID
					List<String> usrLanguageIDlist = translatorInfo.getUsrLanguagelist();
					if (!CollectionUtil.isEmpty(usrLanguageIDlist)){
						for (String usrID: usrLanguageIDlist){
							if (usrLanguageMap.containsKey(usrID)){
								usrLanguage+=usrLanguageMap.get(usrID)+",";
							}
						}
						//去除最后一个","
						usrLanguage = usrLanguage.substring(0,usrLanguage.length()-1);
					}
					translatorInfo.setUsrLanguages(usrLanguage);
				}
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
