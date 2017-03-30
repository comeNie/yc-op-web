package com.ai.yc.op.web.controller.user;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.slp.balance.api.accountquery.interfaces.IAccountQuerySV;
import com.ai.slp.balance.api.accountquery.param.AccountIdParam;
import com.ai.slp.balance.api.accountquery.param.AccountInfoVo;
import com.ai.slp.balance.api.fundquery.interfaces.IFundQuerySV;
import com.ai.slp.balance.api.fundquery.param.FundInfo;
import com.ai.yc.op.web.model.user.UserUpdateParams;
import com.ai.yc.translator.api.translatorservice.interfaces.IYCTranslatorServiceSV;
import com.ai.yc.translator.api.translatorservice.param.SearchYCTranslatorRequest;
import com.ai.yc.translator.api.translatorservice.param.YCTranslatorInfoResponse;
import com.ai.yc.ucenter.api.members.interfaces.IUcMembersSV;
import com.ai.yc.ucenter.api.members.param.editemail.UcMembersEditEmailRequest;
import com.ai.yc.ucenter.api.members.param.editmobile.UcMembersEditMobileRequest;
import com.ai.yc.ucenter.api.members.param.editusername.UcMembersEditUserNameRequest;
import com.ai.yc.ucenter.api.members.param.get.UcMembersGetModeFlag;
import com.ai.yc.ucenter.api.members.param.get.UcMembersGetRequest;
import com.ai.yc.ucenter.api.members.param.get.UcMembersGetResponse;
import com.ai.yc.user.api.usercompany.interfaces.IYCUserCompanySV;
import com.ai.yc.user.api.usercompany.param.UserCompanyInfoRequest;
import com.ai.yc.user.api.usercompany.param.UserCompanyInfoResponse;
import com.ai.yc.user.api.userservice.interfaces.IYCUserServiceSV;
import com.ai.yc.user.api.userservice.param.QueryUserDetailRequest;
import com.ai.yc.user.api.userservice.param.QueryUserDetailRespones;
import com.ai.yc.user.api.userservice.param.UpdateYCUserRequest;

@Controller
@RequestMapping("/user")
public class UserDetailController {
	
	private static final Logger logger = Logger.getLogger(UserDetailController.class);

	
	@RequestMapping("/toUserDetail")
	public ModelAndView toUserDetail(HttpServletRequest request,String userId) {
		return new ModelAndView("jsp/user/userInfo/userDetails").addObject("userId", userId);
	}
	
	@RequestMapping("/userDetail")
	@ResponseBody
	public ResponseData<QueryUserDetailRespones> userDetail(HttpServletRequest request,String userId) {
		IYCUserServiceSV userServiceSV = DubboConsumerFactory.getService(IYCUserServiceSV.class);
		IUcMembersSV ucMembersSV = DubboConsumerFactory.getService(IUcMembersSV.class);
		IYCTranslatorServiceSV ycTranalatorSV = DubboConsumerFactory.getService(IYCTranslatorServiceSV.class);
		IYCUserCompanySV userCompanySV = DubboConsumerFactory.getService(IYCUserCompanySV.class);
		IFundQuerySV foundQuerySV = DubboConsumerFactory.getService(IFundQuerySV.class);

		ResponseData<QueryUserDetailRespones> responseData = null;
		try {
			if(!StringUtil.isBlank(userId)){
				QueryUserDetailRequest queryReq = new QueryUserDetailRequest();
				queryReq.setUserId(userId);
				QueryUserDetailRespones queryUserDetail = userServiceSV.queryUserDetail(queryReq);
				//查询uCenter
				UcMembersGetRequest ucMemberReq = new UcMembersGetRequest();
				ucMemberReq.setUsername(queryUserDetail.getUsrUser().getUserId());
				ucMemberReq.setGetmode(UcMembersGetModeFlag.USER_ID_FLAG);
				UcMembersGetResponse ucGetMember = ucMembersSV.ucGetMember(ucMemberReq);
				if(null != ucGetMember.getDate()){
					queryUserDetail.getUsrUser().setUsername((String) ucGetMember.getDate().get("username"));
					queryUserDetail.getUsrUser().setEmail((String) ucGetMember.getDate().get("email"));
					queryUserDetail.getUsrUser().setRegistIP((String) ucGetMember.getDate().get("regip"));
				}
				//查询译员
				if(null != queryUserDetail.getUsrUser().getIsTranslator() && queryUserDetail.getUsrUser().getIsTranslator()==0){
					SearchYCTranslatorRequest searchTranslatorReq = new SearchYCTranslatorRequest();
					searchTranslatorReq.setUserId(queryUserDetail.getUsrUser().getUserId());
					YCTranslatorInfoResponse ycTranslatorInfo = ycTranalatorSV.searchYCTranslatorInfo(searchTranslatorReq);
					if(null != ycTranslatorInfo && null != ycTranslatorInfo.getVipLevel()){
						queryUserDetail.getUsrUser().setTranslatorLevel("v"+ycTranslatorInfo.getVipLevel());
					}
				}
				//查询企业
				UserCompanyInfoRequest companyReq = new UserCompanyInfoRequest();
	    		companyReq.setUserId(queryUserDetail.getUsrUser().getUserId());
	    		try {
	    			UserCompanyInfoResponse queryCompanyInfo = userCompanySV.queryCompanyInfo(companyReq);
		    		if(null != queryCompanyInfo && null != queryCompanyInfo.getResponseHeader() && queryCompanyInfo.getResponseHeader().getResultCode().equals("000001")){
		    			queryUserDetail.getUsrUser().setIsCompany(0);
		    		}else {
		    			queryUserDetail.getUsrUser().setIsCompany(1);
		    		}
				} catch (Exception e) {
					logger.error(""+queryUserDetail.getUsrUser().getUserId());
				}
	    		
				//查询余额
	    		com.ai.slp.balance.api.fundquery.param.AccountIdParam accountIdParam = new com.ai.slp.balance.api.fundquery.param.AccountIdParam();
				accountIdParam.setAccountId(queryUserDetail.getUsrUser().getAccountId());
				FundInfo fundInfo = foundQuerySV.queryUsableFund(accountIdParam);
				if(null != fundInfo ){
					queryUserDetail.getUsrUser().setBalance(fundInfo.getBalance());
				}
				responseData = new ResponseData<QueryUserDetailRespones>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功", queryUserDetail);
			}else{
				responseData = new ResponseData<QueryUserDetailRespones>(ResponseData.AJAX_STATUS_FAILURE, "查询信息异常", null);
			}
		} catch (Exception e) {
			responseData = new ResponseData<QueryUserDetailRespones>(ResponseData.AJAX_STATUS_FAILURE, "查询信息异常", null);
		}
		
		return responseData;
	}
	
	@RequestMapping("/updateUserDetail")
	@ResponseBody
	public ResponseData<Boolean> updateUserDetail(HttpServletRequest request,UserUpdateParams userUpdateParams) {
		IUcMembersSV ucMembersSV = DubboConsumerFactory.getService(IUcMembersSV.class);
		IYCUserServiceSV userServiceSV = DubboConsumerFactory.getService(IYCUserServiceSV.class);
		try {
			if(!StringUtil.isBlank(userUpdateParams.getUserId())){
				String userId = userUpdateParams.getUserId();
				if(!StringUtil.isBlank(userUpdateParams.getEmail())){
					UcMembersEditEmailRequest ucMembersEditEmailRequest = new UcMembersEditEmailRequest();
					ucMembersEditEmailRequest.setUid(Integer.parseInt(userId));
					ucMembersEditEmailRequest.setEmail(userUpdateParams.getEmail());
					ucMembersSV.ucEditEmail(ucMembersEditEmailRequest);
				}
				if(!StringUtil.isBlank(userUpdateParams.getMobilePhone())){
					UcMembersEditMobileRequest ucMembersEditMobileRequest = new UcMembersEditMobileRequest();
					ucMembersEditMobileRequest.setUid(Integer.parseInt(userId));
					ucMembersEditMobileRequest.setMobilephone(userUpdateParams.getMobilePhone());
					ucMembersSV.ucEditMobilephone(ucMembersEditMobileRequest);
				}
				if(!StringUtil.isBlank(userUpdateParams.getUsername())){
					UcMembersEditUserNameRequest ucMembersEditUserNameRequest = new UcMembersEditUserNameRequest();
					ucMembersEditUserNameRequest.setUid(Integer.parseInt(userId));
					ucMembersEditUserNameRequest.setUsername(userUpdateParams.getUsername());
					ucMembersSV.ucEditUserName(ucMembersEditUserNameRequest);
				}
				if(!StringUtil.isBlank(userUpdateParams.getUsername())){
					UpdateYCUserRequest updateYCUserRequest = new UpdateYCUserRequest();
					updateYCUserRequest.setUserId(userId);
					if(!StringUtil.isBlank(userUpdateParams.getNickname())){
						updateYCUserRequest.setNickname(userUpdateParams.getNickname());
					}
					if(!StringUtil.isBlank(userUpdateParams.getName())){
						updateYCUserRequest.setLastname(userUpdateParams.getName().substring(0, 1));
						updateYCUserRequest.setFirstname(userUpdateParams.getName().substring(1));
					}
					if(!StringUtil.isBlank(userUpdateParams.getSex())){
						updateYCUserRequest.setSex(Integer.parseInt(userUpdateParams.getSex()));
					}
					updateYCUserRequest.setLastModifyTime(new Timestamp(new Date().getTime()));
					userServiceSV.updateYCUserInfo(updateYCUserRequest);
				}
			}else{
				return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", false);
			}
			
		} catch (Exception e) {
			return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", false);

		}
		return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_SUCCESS, "修改会员信息成功", true);
	}
}
