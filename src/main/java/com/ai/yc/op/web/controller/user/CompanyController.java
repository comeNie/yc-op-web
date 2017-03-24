package com.ai.yc.op.web.controller.user;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.sdk.components.idps.IDPSClientFactory;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.opt.sdk.util.UUIDUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.paas.ipaas.image.IImageClient;
import com.ai.slp.balance.api.accountmaintain.interfaces.IAccountMaintainSV;
import com.ai.slp.balance.api.accountmaintain.param.RegAccReq;
import com.ai.slp.balance.api.fundquery.interfaces.IFundQuerySV;
import com.ai.slp.balance.api.fundquery.param.AccountIdParam;
import com.ai.slp.balance.api.fundquery.param.FundInfo;
import com.ai.slp.balance.api.translatorbill.interfaces.IBillGenerateSV;
import com.ai.slp.balance.api.translatorbill.param.AccountParam;
import com.ai.yc.translator.api.translatorservice.interfaces.IYCTranslatorServiceSV;
import com.ai.yc.translator.api.translatorservice.param.SearchYCTranslatorRequest;
import com.ai.yc.translator.api.translatorservice.param.YCTranslatorInfoResponse;
import com.ai.yc.ucenter.api.members.interfaces.IUcMembersSV;
import com.ai.yc.ucenter.api.members.param.get.UcMembersGetRequest;
import com.ai.yc.ucenter.api.members.param.get.UcMembersGetResponse;
import com.ai.yc.user.api.usercompany.interfaces.IYCUserCompanySV;
import com.ai.yc.user.api.usercompany.param.CompanyUserInfo;
import com.ai.yc.user.api.usercompany.param.UserCompanyInfoDetailResponse;
import com.ai.yc.user.api.usercompany.param.UserCompanyInfoListResponse;
import com.ai.yc.user.api.usercompany.param.UserCompanyInfoRequest;
import com.ai.yc.user.api.usercompany.param.UserCompanyPageInfoRequest;
import com.ai.yc.user.api.usercompany.param.UsrCompanyInfo;
import com.ai.yc.user.api.userservice.interfaces.IYCUserServiceSV;
import com.ai.yc.user.api.userservice.param.SearchYCUserRequest;
import com.ai.yc.user.api.userservice.param.YCUserInfoResponse;
import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/company")
public class CompanyController {
	
	@RequestMapping("/toCompanyListPager")
	public ModelAndView toCouponTemplateList(HttpServletRequest request) {
		return new ModelAndView("jsp/user/company/companyList");
	}
	
	@RequestMapping("/getList")
	@ResponseBody
	public ResponseData<PageInfo<UsrCompanyInfo>> getList(HttpServletRequest request,UserCompanyPageInfoRequest pageInfoRequest){
		IYCUserCompanySV userCompanySV = DubboConsumerFactory.getService(IYCUserCompanySV.class);
		ResponseData<PageInfo<UsrCompanyInfo>> responseData = null;
		String strPageNo=(null==request.getParameter("pageNo"))?"1":request.getParameter("pageNo");
	    String strPageSize=(null==request.getParameter("pageSize"))?"10":request.getParameter("pageSize");
	    pageInfoRequest.setPageNo(Integer.parseInt(strPageNo));
	    pageInfoRequest.setPageSize(Integer.parseInt(strPageSize));
		try{
			UserCompanyInfoListResponse response = userCompanySV.queryPageInfoCompanyInfo(pageInfoRequest);
			if(response.getResponseHeader().isSuccess()){
				PageInfo<UsrCompanyInfo> resultPageInfo = response.getCompanyList();
				responseData = new ResponseData<PageInfo<UsrCompanyInfo>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功",resultPageInfo);
			}else{
				responseData = new ResponseData<PageInfo<UsrCompanyInfo>>(ResponseData.AJAX_STATUS_FAILURE, "查询失败",null);
			}
		}catch(Exception e){
			e.printStackTrace();
			responseData = new ResponseData<PageInfo<UsrCompanyInfo>>(ResponseData.AJAX_STATUS_FAILURE, "查询异常",null);
		}
		return responseData;
	}
	
	@RequestMapping("/toCompanyDetailPager")
	public ModelAndView toCompanyDetailPager(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("userId", request.getParameter("userId"));
		model.put("usersource", request.getParameter("usersource"));
		model.put("createTime", request.getParameter("createTime"));
		model.put("companyId", request.getParameter("companyId"));
		return new ModelAndView("jsp/user/company/companyInfoDetail",model);
	}
	
	@RequestMapping("/toCompanyAuditPager")
	public ModelAndView toCompanyAuditPager(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("userId", request.getParameter("userId"));
		model.put("usersource", request.getParameter("usersource"));
		model.put("createTime", request.getParameter("createTime"));
		model.put("companyId", request.getParameter("companyId"));
		model.put("companyName", request.getParameter("companyName"));
		return new ModelAndView("jsp/user/company/companyInfoAudit",model);
	}
	
	@RequestMapping("/getCompanyDetail")
	@ResponseBody
	public ResponseData<CompanyUserInfo> getCompanyDetail(HttpServletRequest request){
		ResponseData<CompanyUserInfo> responseData = null;
		CompanyUserInfo companyuserInfo = new CompanyUserInfo();
		YCUserInfoResponse response = null;
		try{
			/**
			 * 获取用户基本信息
			 */
			String userId = request.getParameter("userId");
			IYCUserServiceSV ycUserServiceSV = DubboConsumerFactory.getService(IYCUserServiceSV.class);
			SearchYCUserRequest userRequest = new SearchYCUserRequest();
			userRequest.setUserId(userId);
			response = ycUserServiceSV.searchYCUserInfo(userRequest);
			BeanUtils.copyProperties(companyuserInfo, response);
			companyuserInfo.setUsersource(request.getParameter("usersource"));
			String createTime = request.getParameter("createTime");
			companyuserInfo.setCreateTime(DateUtil.getTimestamp(Long.parseLong(createTime)));
			/**
			 * 如果是译员，获取译员等级
			 */
			if(companyuserInfo.getIsTranslator()!=null&&companyuserInfo.getIsTranslator()==1){
				IYCTranslatorServiceSV translatorServiceSV = DubboConsumerFactory.getService(IYCTranslatorServiceSV.class);
				SearchYCTranslatorRequest translatorRequest = new SearchYCTranslatorRequest();
				translatorRequest.setUserId(userId);
				YCTranslatorInfoResponse translaotrResponse = translatorServiceSV.searchYCTranslatorInfo(translatorRequest);
				companyuserInfo.setVipLevel(translaotrResponse.getVipLevel());
			}
			/**
			 * 获取用户的用户名和邮箱地址
			 */
			IUcMembersSV ucMembersSV = DubboConsumerFactory.getService(IUcMembersSV.class);
			UcMembersGetRequest members = new UcMembersGetRequest();
			members.setUserId(userId);
			members.setGetmode("-1");
			members.setUsername("-1");
			UcMembersGetResponse memberResponse = ucMembersSV.ucGetMember(members);
			if(memberResponse.getDate()!=null){
				companyuserInfo.setUsername((String)memberResponse.getDate().get("username"));
				companyuserInfo.setEmail((String)memberResponse.getDate().get("email"));
			}
			/**
			 * 获取个人账户余额
			 */
			IFundQuerySV foundQuerySV = DubboConsumerFactory.getService(IFundQuerySV.class);
			AccountIdParam accountIdParam = new AccountIdParam();
			accountIdParam.setAccountId(response.getAccountId());
			FundInfo fundInfo = foundQuerySV.queryUsableFund(accountIdParam);
			companyuserInfo.setBalance(fundInfo.getBalance());
			
			/**
			 * 获取企业信息
			 */
			IYCUserCompanySV companyServiceSV = DubboConsumerFactory.getService(IYCUserCompanySV.class);
			UserCompanyInfoRequest companyInfoRequest = new UserCompanyInfoRequest();
			companyInfoRequest.setUserId(userId);
			UserCompanyInfoDetailResponse companyResponse = companyServiceSV.queryCompanyInfoDetail(companyInfoRequest);
			BeanUtils.copyProperties(companyuserInfo, companyResponse);
			String idpsns = "yc-portal-web";
			IImageClient im = IDPSClientFactory.getImageClient(idpsns);
			if (companyResponse.getLicenseAttacid() != null && !"".equals(companyResponse.getLicenseAttacid())) {
				String url = im.getImageUrl(companyResponse.getLicenseAttacid(), ".jpg", "40x20");
				companyuserInfo.setLicenseAttacurl(url);
			}
			if (companyResponse.getEntpAttacid() != null && !"".equals(companyResponse.getEntpAttacid())) {
				String url = im.getImageUrl(companyResponse.getLicenseAttacid(), ".jpg", "40x20");
				companyuserInfo.setLogoUrl(url);
			}
			responseData = new ResponseData<CompanyUserInfo>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功",companyuserInfo);
		}catch(Exception e){
			e.printStackTrace();
			responseData = new ResponseData<CompanyUserInfo>(ResponseData.AJAX_STATUS_FAILURE, "查询异常",companyuserInfo);
		}
		return responseData;
	}
	
	@RequestMapping("/auditCompanyInfo")
	@ResponseBody
	public ResponseData<Boolean> auditCompanyInfo(HttpServletRequest request){
		ResponseData<Boolean> responseData = null;
		try{
			/**
			 * 保存企业账单日、结算周期等信息
			 */
			IBillGenerateSV billGenerateSV = DubboConsumerFactory.getService(IBillGenerateSV.class);
			AccountParam accountParam = new AccountParam();
			accountParam.setTargetID(request.getParameter("companyId"));
			accountParam.setTargetName(request.getParameter("companyName"));
			accountParam.setAccountPeriod(request.getParameter("settlementPeriod"));
			accountParam.setAccountDay(request.getParameter("statementDate"));
			accountParam.setAccountType(request.getParameter("settlingAccounts"));
			accountParam.setDiscount(new BigDecimal(request.getParameter("corporateDiscount")).divide(new BigDecimal(100)));
			billGenerateSV.insertParam(accountParam);
			int state = Integer.parseInt(request.getParameter("state"));
			/**
			 * 审核通过创建企业账户
			 */
			long accountId = 0;
			if(state==1){
				// 支付账户信息
				IAccountMaintainSV iAccountMaintainSV = DubboConsumerFactory.getService(IAccountMaintainSV.class);
				RegAccReq vo = new RegAccReq();
				vo.setExternalId(UUIDUtil.genId32());// 外部流水号ID
				vo.setSystemId("Cloud-UAC_WEB");// 系统ID
				vo.setTenantId("yeecloud");// 租户ID
				vo.setRegCustomerId(request.getParameter("companyId"));
				vo.setAcctName(request.getParameter("companyId"));
				/**
				 * 1、预付费 0、后付费
				 */
				vo.setAcctType(request.getParameter("settlingAccounts"));

				accountId = iAccountMaintainSV.createAccount(vo);
			}
			/**
			 * 审核结果
			 */
			IYCUserCompanySV companySV = DubboConsumerFactory.getService(IYCUserCompanySV.class);
			UserCompanyInfoRequest companyInfoRequest = new UserCompanyInfoRequest();
			companyInfoRequest.setCompanyId(request.getParameter("companyId"));
			companyInfoRequest.setState(state);
			companyInfoRequest.setAccountId(accountId);
			companySV.updateCompanyInfo(companyInfoRequest);
			responseData = new ResponseData<Boolean>(ResponseData.AJAX_STATUS_SUCCESS, "审核成功",true);
		}catch(Exception e){
			responseData = new ResponseData<Boolean>(ResponseData.AJAX_STATUS_FAILURE, "审核失败",false);
			e.printStackTrace();
		}
		return responseData;
	}
	
}
