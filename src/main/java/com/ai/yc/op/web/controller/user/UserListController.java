package com.ai.yc.op.web.controller.user;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ai.net.xss.util.CollectionUtil;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.slp.balance.api.accountquery.interfaces.IAccountQuerySV;
import com.ai.slp.balance.api.fundquery.interfaces.IFundQuerySV;
import com.ai.slp.balance.api.fundquery.param.FundInfo;
import com.ai.slp.balance.api.accountquery.param.AccountIdParam;
import com.ai.slp.balance.api.accountquery.param.AccountInfoVo;
import com.ai.yc.op.web.model.order.OrderPageQueryParams;
import com.ai.yc.op.web.model.order.OrderPageResParam;
import com.ai.yc.op.web.model.user.UserQueryParams;
import com.ai.yc.order.api.orderquery.interfaces.IOrderQuerySV;
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
    		
    		
    		
//    		QueryUserResponse userReq
//    		QueryUserResponse queryUserPage(QueryUserRequest request)
    		queryReq.setPageSize(queryRequest.getPageSize());
    		queryReq.setPageNo(queryRequest.getPageNo());
    		IYCUserServiceSV userServiceSV = DubboConsumerFactory.getService(IYCUserServiceSV.class);
//    		List<YCUserInfoResponse> allUserInfo = userServiceSV.getAllUserInfo();
//	    	BeanUtils.copyProperties(queryReq, queryRequest);
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
//    		IYCUserServiceSV sss = new YCUserServiceSVImpl();
    		
//    		responseData.setData(resultPageInfo);

		} catch (Exception e) {
			logger.error("查询会员列表失败：", e);
			responseData = new ResponseData<PageInfo<QueryUserResponse>>(ResponseData.AJAX_STATUS_FAILURE, "查询信息异常", null);
		}
    	
    	
    	
		return new ResponseData<PageInfo<QueryUserResponse>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功",resultPageInfo);
	}
}
