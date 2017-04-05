package com.ai.yc.op.web.controller.user;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ai.net.xss.util.CollectionUtil;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.components.ccs.CCSClientFactory;
import com.ai.opt.sdk.components.excel.client.AbstractExcelHelper;
import com.ai.opt.sdk.components.excel.factory.ExcelFactory;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.paas.ipaas.ccs.IConfigClient;
import com.ai.paas.ipaas.ccs.constants.ConfigException;
import com.ai.slp.balance.api.accountquery.interfaces.IAccountQuerySV;
import com.ai.slp.balance.api.fundquery.interfaces.IFundQuerySV;
import com.ai.slp.balance.api.fundquery.param.FundInfo;
import com.ai.slp.balance.api.accountquery.param.AccountIdParam;
import com.ai.slp.balance.api.accountquery.param.AccountInfoVo;
import com.ai.yc.common.api.cache.interfaces.ICacheSV;
import com.ai.yc.common.api.cache.param.SysParam;
import com.ai.yc.common.api.cache.param.SysParamSingleCond;
import com.ai.yc.op.web.constant.Constants;
import com.ai.yc.op.web.constant.Constants.ExcelConstants;
import com.ai.yc.op.web.model.order.ExAllOrder;
import com.ai.yc.op.web.model.order.OrderPageQueryParams;
import com.ai.yc.op.web.model.order.OrderPageResParam;
import com.ai.yc.op.web.model.user.ExAllUser;
import com.ai.yc.op.web.model.user.UserQueryParams;
import com.ai.yc.op.web.utils.AmountUtil;
import com.ai.yc.op.web.utils.TimeZoneTimeUtil;
import com.ai.yc.order.api.orderquery.interfaces.IOrderQuerySV;
import com.ai.yc.order.api.orderquery.param.OrdOrderVo;
import com.ai.yc.order.api.orderquery.param.QueryOrderRequest;
import com.ai.yc.order.api.orderquery.param.QueryOrderRsponse;
import com.ai.yc.ucenter.api.members.interfaces.IUcMembersSV;
import com.ai.yc.ucenter.api.members.param.get.UcMembersGetModeFlag;
import com.ai.yc.ucenter.api.members.param.get.UcMembersGetRequest;
import com.ai.yc.ucenter.api.members.param.get.UcMembersGetResponse;
import com.ai.yc.user.api.usercompany.interfaces.IYCUserCompanySV;
import com.ai.yc.user.api.usercompany.param.UserCompanyInfoRequest;
import com.ai.yc.user.api.usercompany.param.UserCompanyInfoResponse;
import com.ai.yc.user.api.userservice.interfaces.IYCUserServiceSV;
import com.ai.yc.user.api.userservice.param.QueryUserRequest;
import com.ai.yc.user.api.userservice.param.QueryUserResponse;
import com.ai.yc.user.api.userservice.param.YCUserInfoResponse;
import com.ai.yc.user.api.userservice.param.YCUsrUserVO;

@Controller
@RequestMapping("/user")
public class UserListController {

	private static final Logger logger = Logger.getLogger(UserListController.class);
	
	@RequestMapping("/toUserList")
	public ModelAndView toAlertOrder(HttpServletRequest request) {
		return new ModelAndView("jsp/user/userInfo/userList");
	}
	
	@RequestMapping("/getUserPageData")
    @ResponseBody
    public ResponseData<PageInfo<QueryUserResponse>> getList(HttpServletRequest request,UserQueryParams queryRequest)throws Exception{
    	ResponseData<PageInfo<QueryUserResponse>> responseData = null;
    	List<QueryUserResponse> resultList = new ArrayList<QueryUserResponse>();
    	PageInfo<QueryUserResponse> resultPageInfo  = new PageInfo<QueryUserResponse>();
    	try {
    		
    		IFundQuerySV foundQuerySV = DubboConsumerFactory.getService(IFundQuerySV.class);
    		IUcMembersSV ucMembersSV = DubboConsumerFactory.getService(IUcMembersSV.class);
    		IAccountQuerySV accountQuerySV = DubboConsumerFactory.getService(IAccountQuerySV.class);
    		IYCUserCompanySV userCompanySV = DubboConsumerFactory.getService(IYCUserCompanySV.class);
    		IYCUserServiceSV userServiceSV = DubboConsumerFactory.getService(IYCUserServiceSV.class);

    		QueryUserRequest queryReq = new QueryUserRequest();
   		 	String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";    

    		if(!StringUtil.isBlank(queryRequest.getMobilePhone())){
    			if(Pattern.compile(check).matcher(queryRequest.getMobilePhone()).matches()){
    				UcMembersGetRequest ucMemberReq = new UcMembersGetRequest();
    				ucMemberReq.setUsername(queryRequest.getMobilePhone());
    				ucMemberReq.setGetmode(UcMembersGetModeFlag.EMAIL_FLAG);
    				UcMembersGetResponse ucGetMember = ucMembersSV.ucGetMember(ucMemberReq);
    				if(null != ucGetMember.getDate()){
    					queryReq.setUserId(""+ucGetMember.getDate().get("uid"));
    					queryRequest.setMobilePhone(null);
    				}
    			}
    		}
    		queryReq.setPageSize(queryRequest.getPageSize());
    		queryReq.setPageNo(queryRequest.getPageNo());
    		if(null != queryRequest.getIsCompany()){
    			queryReq.setIsCompany(queryRequest.getIsCompany());
    		}
    		if(!StringUtil.isBlank(queryRequest.getNickname())){
    			queryReq.setNickname(queryRequest.getNickname());
    		}
    		if(!StringUtil.isBlank(queryRequest.getMobilePhone())){
    			queryReq.setMobilePhone(queryRequest.getMobilePhone());
    		}
    		if(null != (queryRequest.getIsTranslator()) && 2 != (queryRequest.getIsTranslator())){
    			queryReq.setIsTranslator(queryRequest.getIsTranslator());
    		}
    		if(null != (queryRequest.getState()) && 3 != (queryRequest.getState())){
    			queryReq.setState(queryRequest.getState());
    		}
    		if(null != (queryRequest.getRegistTimeStart())){
    			queryReq.setRegistTimeStart(new Timestamp(queryRequest.getRegistTimeStart()));
    		}
    		if(null != (queryRequest.getRegistTimeEnd())){
    			queryReq.setRegistTimeEnd(new Timestamp(queryRequest.getRegistTimeEnd()));
    		}
    		if(!StringUtil.isBlank(queryRequest.getSafetyLevel())){
    			queryReq.setSafetyLevel(queryRequest.getSafetyLevel());
    		}
    		if(!StringUtil.isBlank(queryRequest.getUsersource())){
    			queryReq.setUsersource(queryRequest.getUsersource());
    		}
    		QueryUserResponse queryUserPage = userServiceSV.queryUserPage(queryReq);
    		PageInfo<YCUsrUserVO> pageInfo = queryUserPage.getPageInfo();
			

    		List<YCUsrUserVO> result = pageInfo.getResult();
    		if(!CollectionUtil.isEmpty(result)){
    			for(YCUsrUserVO usrUser:result){
    				com.ai.slp.balance.api.fundquery.param.AccountIdParam accountIdParam = new com.ai.slp.balance.api.fundquery.param.AccountIdParam();
    				accountIdParam.setAccountId(usrUser.getAccountId());
    				FundInfo fundInfo = foundQuerySV.queryUsableFund(accountIdParam);
    				if(null != fundInfo){
        				usrUser.setBalance(fundInfo.getBalance());
    				}
    				UcMembersGetRequest ucMemberReq = new UcMembersGetRequest();
    				ucMemberReq.setUsername(usrUser.getUserId());
    				ucMemberReq.setGetmode(UcMembersGetModeFlag.USERID_FLAG);
    				UcMembersGetResponse ucGetMember = ucMembersSV.ucGetMember(ucMemberReq);
    				if(null != ucGetMember.getDate()){
    					String email = (String)ucGetMember.getDate().get("email");
        				usrUser.setEmail(email);
    				}
    				AccountIdParam accountParm = new AccountIdParam();
    				accountParm.setAccountId(usrUser.getAccountId());
    				accountParm.setTenantId("yeecloud");
    				AccountInfoVo accont = accountQuerySV.queryAccontById(accountParm);
    				if(null != accont && null != accont.getAcctStatus()){
    					usrUser.setAcctStatus(accont.getAcctStatus());
    				}
    				UserCompanyInfoRequest companyReq = new UserCompanyInfoRequest();
    	    		companyReq.setUserId(usrUser.getUserId());
    	    		try {
    	    			UserCompanyInfoResponse queryCompanyInfo = userCompanySV.queryCompanyInfo(companyReq);
        	    		if(null != queryCompanyInfo && null != queryCompanyInfo.getResponseHeader() && queryCompanyInfo.getResponseHeader().getResultCode().equals("000001")){
        	    			usrUser.setIsCompany(0);
        	    		}else {
        	    			usrUser.setIsCompany(1);
        	    		}
					} catch (Exception e) {
						logger.error(""+usrUser.getUserId());
					}
    				
    			}
    		}
    		BeanUtils.copyProperties(resultPageInfo, pageInfo);

		} catch (Exception e) {
			logger.error("查询会员列表失败：", e);
			responseData = new ResponseData<PageInfo<QueryUserResponse>>(ResponseData.AJAX_STATUS_FAILURE, "查询信息异常", null);
		}
    	
		return new ResponseData<PageInfo<QueryUserResponse>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功",resultPageInfo);
	}
	
	/**
     * 订单信息导出
	 * @throws ConfigException 
	 * @throws UnsupportedEncodingException 
     */
    @RequestMapping("/export")
    @ResponseBody
    public void  export(HttpServletRequest request, HttpServletResponse response, UserQueryParams queryRequest) throws ConfigException, UnsupportedEncodingException {
    	ResponseData<PageInfo<QueryUserResponse>> responseData = null;
    	List<QueryUserResponse> resultList = new ArrayList<QueryUserResponse>();
    	PageInfo<QueryUserResponse> resultPageInfo  = new PageInfo<QueryUserResponse>();
    		
    		IFundQuerySV foundQuerySV = DubboConsumerFactory.getService(IFundQuerySV.class);
    		IUcMembersSV ucMembersSV = DubboConsumerFactory.getService(IUcMembersSV.class);
    		IAccountQuerySV accountQuerySV = DubboConsumerFactory.getService(IAccountQuerySV.class);
    		IYCUserCompanySV userCompanySV = DubboConsumerFactory.getService(IYCUserCompanySV.class);
    		IYCUserServiceSV userServiceSV = DubboConsumerFactory.getService(IYCUserServiceSV.class);
    		QueryUserRequest queryReq = new QueryUserRequest();
   		 	String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";    

    		if(!StringUtil.isBlank(queryRequest.getMobilePhone())){
    			if(Pattern.compile(check).matcher(queryRequest.getMobilePhone()).matches()){
    				UcMembersGetRequest ucMemberReq = new UcMembersGetRequest();
    				ucMemberReq.setUsername(queryRequest.getMobilePhone());
    				ucMemberReq.setGetmode(UcMembersGetModeFlag.EMAIL_FLAG);
    				UcMembersGetResponse ucGetMember = ucMembersSV.ucGetMember(ucMemberReq);
    				if(null != ucGetMember.getDate()){
    					queryReq.setUserId(""+ucGetMember.getDate().get("uid"));
    					queryRequest.setMobilePhone(null);
    				}
    			}
    		}
    		if(null != queryRequest.getIsCompany()){
    			queryReq.setIsCompany(queryRequest.getIsCompany());
    		}
    		if(!StringUtil.isBlank(queryRequest.getNickname())){
    			String nickname = URLDecoder.decode(queryRequest.getNickname(),"UTF-8");
    			queryReq.setNickname(nickname);
    		}
    		if(!StringUtil.isBlank(queryRequest.getMobilePhone())){
    			queryReq.setMobilePhone(queryRequest.getMobilePhone());
    		}
    		if(null != (queryRequest.getIsTranslator()) && 2 != (queryRequest.getIsTranslator())){
    			queryReq.setIsTranslator(queryRequest.getIsTranslator());
    		}
    		if(null != (queryRequest.getState()) && 3 != (queryRequest.getState())){
    			queryReq.setState(queryRequest.getState());
    		}
    		if(null != (queryRequest.getRegistTimeStart())){
    			queryReq.setRegistTimeStart(new Timestamp(queryRequest.getRegistTimeStart()));
    		}
    		if(null != (queryRequest.getRegistTimeEnd())){
    			queryReq.setRegistTimeEnd(new Timestamp(queryRequest.getRegistTimeEnd()));
    		}
    		if(!StringUtil.isBlank(queryRequest.getSafetyLevel())){
    			queryReq.setSafetyLevel(queryRequest.getSafetyLevel());
    		}
    		if(!StringUtil.isBlank(queryRequest.getUsersource())){
    			queryReq.setUsersource(queryRequest.getUsersource());
    		}
    		
    		IConfigClient configClient = CCSClientFactory.getDefaultConfigClient();
            String maxRow =  configClient.get(ExcelConstants.EXCEL_OUTPUT_MAX_ROW);
            int excelMaxRow = Integer.valueOf(maxRow);
            queryReq.setPageSize(excelMaxRow);
    		
    		queryReq.setPageNo(1);
    		
    		QueryUserResponse queryUserPage = userServiceSV.queryUserPage(queryReq);
    		PageInfo<YCUsrUserVO> pageInfo = queryUserPage.getPageInfo();
			

    		List<YCUsrUserVO> result = pageInfo.getResult();
    		List<ExAllUser> exportList = new ArrayList<ExAllUser>();

    		if(!CollectionUtil.isEmpty(result)){
    			for(YCUsrUserVO usrUser:result){

    				ExAllUser exUser = new ExAllUser();
    				exUser.setUserId(usrUser.getUserId());
    				if(null != usrUser.getUsersource() && usrUser.getUsersource().equals(0)){
        				exUser.setUsersource("内部系统");
    				}else if(null != usrUser.getUsersource() && usrUser.getUsersource().equals(1)){
        				exUser.setUsersource("金山");
    				}else if(null != usrUser.getUsersource() && usrUser.getUsersource().equals(2)){
        				exUser.setUsersource("百度用户");
    				}else if(null != usrUser.getUsersource() && usrUser.getUsersource().equals(3)){
        				exUser.setUsersource("微信");
    				}else if(null != usrUser.getUsersource() && usrUser.getUsersource().equals(4)){
        				exUser.setUsersource("找翻译");
    				}else if(null != usrUser.getUsersource() && usrUser.getUsersource().equals(5)){
        				exUser.setUsersource("wap");
    				}else if(null != usrUser.getUsersource() && usrUser.getUsersource().equals(6)){
        				exUser.setUsersource("pc");
    				}else{
    					exUser.setUsersource("其他");
    				}
    				exUser.setNickname(usrUser.getNickname());
    				exUser.setMobilePhone(usrUser.getMobilePhone());
    				UcMembersGetRequest ucMemberReq = new UcMembersGetRequest();
    				ucMemberReq.setUsername(usrUser.getUserId());
    				ucMemberReq.setGetmode(UcMembersGetModeFlag.USERID_FLAG);
    				UcMembersGetResponse ucGetMember = ucMembersSV.ucGetMember(ucMemberReq);
    				if(null != ucGetMember.getDate()){
    					String email = (String)ucGetMember.getDate().get("email");
    					exUser.setEmail(email);
    				}
    				
    				if(usrUser.getGriwthValue()==null||usrUser.getGriwthValue()!=null&&usrUser.getGriwthValue()>=0 && usrUser.getGriwthValue()<=5999){
    					exUser.setSafetyLevel("普通会员");
    				}else if(usrUser.getGriwthValue()!=null&&usrUser.getGriwthValue()<=14999){
    					exUser.setSafetyLevel("VIP会员");
    				}else if(usrUser.getGriwthValue()!=null&&usrUser.getGriwthValue()<=29999){
    					exUser.setSafetyLevel("SVIP会员");
    				}else{
    					exUser.setSafetyLevel("SVIP白金会员");
    				}

    				if(null != usrUser.getIsTranslator() && usrUser.getIsTranslator().equals(0)){
    					exUser.setIsTranslator("是");
    				}else{
    					exUser.setIsTranslator("不是");
    				}
    				
    				try {
    					UserCompanyInfoRequest companyReq = new UserCompanyInfoRequest();
        	    		companyReq.setUserId(usrUser.getUserId());
    	    			UserCompanyInfoResponse queryCompanyInfo = userCompanySV.queryCompanyInfo(companyReq);
        	    		if(null != queryCompanyInfo && null != queryCompanyInfo.getResponseHeader() && queryCompanyInfo.getResponseHeader().getResultCode().equals("000001")){
        	    			exUser.setIsCompany("不是");
        	    		}else {
        	    			exUser.setIsCompany("是");
        	    		}
					} catch (Exception e) {
						logger.error(""+usrUser.getUserId());
					}
    				
    				com.ai.slp.balance.api.fundquery.param.AccountIdParam accountIdParam = new com.ai.slp.balance.api.fundquery.param.AccountIdParam();
    				accountIdParam.setAccountId(usrUser.getAccountId());
    				FundInfo fundInfo = foundQuerySV.queryUsableFund(accountIdParam);
    				if(null != fundInfo){
    					exUser.setBalance(fundInfo.getBalance()+"");
    				}
    				if(null != usrUser.getState() && usrUser.getState().equals(0) ){
    					exUser.setState("正常");
    				}else if(null != usrUser.getState() && usrUser.getState().equals(1) ){
    					exUser.setState("忙碌");
    				}else if(null != usrUser.getState() && usrUser.getState().equals(2)){
    					exUser.setState("停用");
    				}
    				exUser.setAcctStatus("正常");
    				
    				exportList.add(exUser);
    			}
    			
    			try {
        			logger.error("获取输出流>>>>");
        			ServletOutputStream outputStream = response.getOutputStream();
        			response.reset();// 清空输出流
                    response.setContentType("application/msexcel");// 定义输出类型
                    response.setHeader("Content-disposition", "attachment; filename=user"+new Date().getTime()+".xls");// 设定输出文件头
                    String[] titles = new String[]{"编号", "注册来源", "昵称", "手机号", "邮箱", "会员等级","是否是译员","是否是企业","余额","激活状态","账户状态"};
            		String[] fieldNames = new String[]{"userId", "usersource", "nickname", "mobilePhone",
            				"email", "safetyLevel","isTranslator","isCompany","balance","state","acctStatus"};
        			 AbstractExcelHelper excelHelper = ExcelFactory.getJxlExcelHelper();
        			 logger.error("写入数据到excel>>>>");
        			 excelHelper.writeExcel(outputStream, "user"+new Date().getTime(), ExAllUser.class, exportList,fieldNames, titles);
        		
    			} catch (Exception e) {
    				logger.error("导出订单列表失败："+e.getMessage(), e);
    			}
    		}else{
    			logger.error("查询数据为空>>>>");
    		
		}
	}
}
