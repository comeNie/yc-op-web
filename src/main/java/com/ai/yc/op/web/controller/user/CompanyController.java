package com.ai.yc.op.web.controller.user;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.sdk.components.ccs.CCSClientFactory;
import com.ai.opt.sdk.components.excel.client.AbstractExcelHelper;
import com.ai.opt.sdk.components.excel.factory.ExcelFactory;
import com.ai.opt.sdk.components.idps.IDPSClientFactory;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.opt.sdk.util.UUIDUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.ai.paas.ipaas.ccs.IConfigClient;
import com.ai.paas.ipaas.image.IImageClient;
import com.ai.slp.balance.api.accountmaintain.interfaces.IAccountMaintainSV;
import com.ai.slp.balance.api.accountmaintain.param.RegAccReq;
import com.ai.slp.balance.api.fundquery.interfaces.IFundQuerySV;
import com.ai.slp.balance.api.fundquery.param.AccountIdParam;
import com.ai.slp.balance.api.fundquery.param.FundInfo;
import com.ai.slp.balance.api.translatorbill.interfaces.IBillGenerateSV;
import com.ai.slp.balance.api.translatorbill.param.AccountParam;
import com.ai.slp.balance.api.translatorbill.param.FunAccountQueryRequest;
import com.ai.slp.balance.api.translatorbill.param.FunAccountQueryResponse;
import com.ai.slp.balance.api.translatorbill.param.FunAccountResponse;
import com.ai.yc.op.web.constant.Constants.ExcelConstants;
import com.ai.yc.op.web.model.bill.ExAllBill;
import com.ai.yc.op.web.model.sso.client.GeneralSSOClientUser;
import com.ai.yc.op.web.utils.TimeZoneTimeUtil;
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
import com.ai.yc.user.api.usercompanyrelation.interfaces.IYCUserCompanyRelationSV;
import com.ai.yc.user.api.usercompanyrelation.param.CompanyRelationInfo;
import com.ai.yc.user.api.usercompanyrelation.param.CompanyRelationResponse;
import com.ai.yc.user.api.usercompanyrelation.param.UserCompanyRelationPageInfoRequest;
import com.ai.yc.user.api.userservice.interfaces.IYCUserServiceSV;
import com.ai.yc.user.api.userservice.param.SearchYCUserRequest;
import com.ai.yc.user.api.userservice.param.YCUserInfoResponse;

@Controller
@RequestMapping("/company")
public class CompanyController {
	
	private static final Logger logger = Logger.getLogger(CompanyController.class);
	
	@RequestMapping("/toCompanyListPager")
	public ModelAndView toCouponTemplateList(HttpServletRequest request) {
		return new ModelAndView("jsp/user/company/auditCompanyList");
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
	
	@RequestMapping("/toAllCompanyListPager")
	public ModelAndView toAllCompanyListPager(HttpServletRequest request) {
		return new ModelAndView("jsp/user/company/allCompanyList");
	}
	
	@RequestMapping("/getAllCompanyList")
	@ResponseBody
	public ResponseData<PageInfo<UsrCompanyInfo>> getAllCompanyList(HttpServletRequest request,UserCompanyPageInfoRequest pageInfoRequest){
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
	
	@RequestMapping("/getCompanyUsersList")
	@ResponseBody
	public ResponseData<PageInfo<UsrCompanyInfo>> getCompanyUsersList(HttpServletRequest request){
		
		ResponseData<PageInfo<UsrCompanyInfo>> responseData = null;
		
		PageInfo<UsrCompanyInfo> resultPageInfo = new PageInfo<UsrCompanyInfo>();
		try{
			IYCUserCompanyRelationSV companyRelationSV = DubboConsumerFactory.getService(IYCUserCompanyRelationSV.class);
			
			IUcMembersSV ucMembersSV = DubboConsumerFactory.getService(IUcMembersSV.class);
			
			IYCUserServiceSV ycUserServiceSV = DubboConsumerFactory.getService(IYCUserServiceSV.class);
			/**
			 * 获取企业成员信息
			 */
			UserCompanyRelationPageInfoRequest pageInfoRequest = new UserCompanyRelationPageInfoRequest();
			pageInfoRequest.setCompanyId(request.getParameter("companyId"));
			String strPageNo=(null==request.getParameter("pageNo"))?"1":request.getParameter("pageNo");
		    String strPageSize=(null==request.getParameter("pageSize"))?"10":request.getParameter("pageSize");
			pageInfoRequest.setPageNo(Integer.parseInt(strPageNo));
			pageInfoRequest.setPageSize(Integer.parseInt(strPageSize));
			CompanyRelationResponse companyRelationresponse = companyRelationSV.getCompanyUsers(pageInfoRequest);
			
			if(companyRelationresponse!=null&&companyRelationresponse.getList()!=null){
				
				List<CompanyRelationInfo> relationList = companyRelationresponse.getList().getResult();
			    YCUserInfoResponse userInfoResponse = null;
			    List<UsrCompanyInfo> usrCompanyInfoList = new ArrayList<UsrCompanyInfo>();
			    
			    for(int i=0;i<relationList.size();i++){
			    	
					CompanyRelationInfo relationInfo = relationList.get(i);
					/**
					 * 获取ucenter信息
					 */
					String userId = relationInfo.getUserId();
					UcMembersGetRequest membersRequest = new UcMembersGetRequest();
					membersRequest.setUserId(userId);
					membersRequest.setGetmode("-1");
					membersRequest.setUsername("-1");
					UcMembersGetResponse getResponse = ucMembersSV.ucGetMember(membersRequest);
					/**
					 * 获取用户信息
					 */
					ycUserServiceSV = DubboConsumerFactory.getService(IYCUserServiceSV.class);
					SearchYCUserRequest ucUser = new SearchYCUserRequest();
					ucUser.setUserId(userId);
					userInfoResponse = ycUserServiceSV.searchYCUserInfo(ucUser);
					/**
					 * 组装信息
					 */
					UsrCompanyInfo companyInfo = new UsrCompanyInfo();
					companyInfo.setCompanyUserUserName((String)getResponse.getDate().get("username"));
					companyInfo.setCompanyUserNickName(userInfoResponse.getNickname());
					companyInfo.setCompanyUserLevel(userInfoResponse.getGriwthLevelZH());
					companyInfo.setCompanyJoinTime(relationInfo.getJoinTime());
					companyInfo.setIsManagment(relationInfo.getIsManagment());
					
					usrCompanyInfoList.add(companyInfo);
				}
			    resultPageInfo.setPageCount(companyRelationresponse.getList().getPageCount());
			    resultPageInfo.setPageNo(companyRelationresponse.getList().getPageNo());
			    resultPageInfo.setPageSize(companyRelationresponse.getList().getPageSize());
			    resultPageInfo.setCount(companyRelationresponse.getList().getCount());
				resultPageInfo.setResult(usrCompanyInfoList);
			}
			responseData = new ResponseData<PageInfo<UsrCompanyInfo>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功",resultPageInfo);
		}catch(Exception e){
			e.printStackTrace();
			responseData = new ResponseData<PageInfo<UsrCompanyInfo>>(ResponseData.AJAX_STATUS_FAILURE, "查询失败",resultPageInfo);
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
			 GeneralSSOClientUser loginUser = (GeneralSSOClientUser) request.getSession().getAttribute(
			    		SSOClientConstants.USER_SESSION_KEY);
			IYCUserCompanySV companySV = DubboConsumerFactory.getService(IYCUserCompanySV.class);
			UserCompanyInfoRequest companyInfoRequest = new UserCompanyInfoRequest();
			companyInfoRequest.setCreateTime(DateUtil.getSysDate());
			companyInfoRequest.setAuditor(loginUser.getLoginName());
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
	
	/**
     * 未审核企业列表导出
     */
    @RequestMapping("/export")
    @ResponseBody
    public void  export(HttpServletRequest request,HttpServletResponse response,UserCompanyPageInfoRequest pageInfoRequest,String flag) {
    	logger.error("进入导出方法>>>>");
		PageInfo<UsrCompanyInfo> resultPageInfo  = new PageInfo<UsrCompanyInfo>();
	    try {
	    //获取配置中的导出最大数值
	    logger.error("获取导出最大条数配置>>>>");
	    IConfigClient configClient = CCSClientFactory.getDefaultConfigClient();
        String maxRow =  configClient.get(ExcelConstants.EXCEL_OUTPUT_MAX_ROW);
        int excelMaxRow = Integer.valueOf(maxRow);
        pageInfoRequest.setPageSize(excelMaxRow);
        pageInfoRequest.setPageNo(1);
		IYCUserCompanySV userCompanySV = DubboConsumerFactory.getService(IYCUserCompanySV.class);
		List<UsrCompanyInfo> resultCompanyInfo = new ArrayList<UsrCompanyInfo>();
		try{
			UserCompanyInfoListResponse companyIfoResponse = userCompanySV.queryPageInfoCompanyInfo(pageInfoRequest);
			if(companyIfoResponse.getResponseHeader().isSuccess()){
				resultPageInfo = companyIfoResponse.getCompanyList();
				List<UsrCompanyInfo> usrCompanyInfoList = resultPageInfo.getResult();
				for(int i=0;i<usrCompanyInfoList.size();i++){
					UsrCompanyInfo  companyInfo = usrCompanyInfoList.get(i);
					String usersource = companyInfo.getUsersource();
					if("0".equals(usersource)){
						companyInfo.setUsersource("pc");
					}else if("10".equals(usersource)){
						companyInfo.setUsersource("译云中文站");
					}else if("11".equals(usersource)){
						companyInfo.setUsersource("译云英文站");
					}else if("2".equals(usersource)){
						companyInfo.setUsersource("百度");
					}else if("1".equals(usersource)){
						companyInfo.setUsersource("金山");
					}else if("12".equals(usersource)){
						companyInfo.setUsersource("找翻译");
					}else if("13".equals(usersource)){
						companyInfo.setUsersource("wap-中文站");
					}else if("14".equals(usersource)){
						companyInfo.setUsersource("wap-英文站");
					}else{
						companyInfo.setUsersource("微信助手");
					}
					if(companyInfo.getState()==0){
						companyInfo.setStateName("未审核");
					}else if(companyInfo.getState()==1){
						companyInfo.setStateName("已审核");
					}
					companyInfo.setContent("用户");
					companyInfo.setFullName(companyInfo.getFirstname()+companyInfo.getLastname());
					resultCompanyInfo.add(companyInfo);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
			logger.error("获取输出流>>>>");
			ServletOutputStream outputStream = response.getOutputStream();
			response.reset();// 清空输出流
            response.setContentType("application/msexcel");// 定义输出类型
            response.setHeader("Content-disposition", "attachment; filename=company"+new Date().getTime()+".xls");// 设定输出文件头
            String[] titles = new String[]{"申请来源", "企业名称", "创建人昵称", "姓名", "企业座机", "联系人","申请时间","状态"};
    		String[] fieldNames = new String[]{"usersource", "companyName", "nickName", "fullName",	"telephone", "linkman","createTime","stateName"};
			if("allCompanyDate".equals(flag)){
				 titles = new String[]{"申请来源", "企业名称", "创建人昵称", "创建人角色", "联系人", "联系电话","申请时间","成员数量","企业账户余额","企业折扣","结算方式","审核人","审核时间","状态"};
		    	 fieldNames = new String[]{"usersource", "companyName", "nickName", "content","linkman","telephone", "createTime","membersCount","companyAccount","corporateDiscount","settlingAccounts","auditor","checkTime","stateName"};
			}
    		
    		AbstractExcelHelper excelHelper = ExcelFactory.getJxlExcelHelper();
			logger.error("写入数据到excel>>>>");
			excelHelper.writeExcel(outputStream, "company"+new Date().getTime(), UsrCompanyInfo.class,resultCompanyInfo,fieldNames, titles);
		} catch (Exception e) {
			logger.error("导出账单列表失败："+e.getMessage(), e);
		}
	}
}
