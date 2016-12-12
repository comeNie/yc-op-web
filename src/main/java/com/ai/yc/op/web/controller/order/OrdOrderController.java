package com.ai.yc.op.web.controller.order;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.yc.op.web.model.order.OrdOrderDetails;
import com.ai.yc.op.web.model.order.OrderDetailPagerRequest;
import com.ai.yc.op.web.model.sso.client.GeneralSSOClientUser;
import com.ai.yc.op.web.utils.LoginUtil;
import com.ai.yc.order.api.autooffer.interfaces.IQueryAutoOfferSV;
import com.ai.yc.order.api.autooffer.param.QueryAutoOfferReq;
import com.ai.yc.order.api.autooffer.param.QueryAutoOfferRes;
import com.ai.yc.order.api.orderdetails.interfaces.IQueryOrderDetailsSV;
import com.ai.yc.order.api.orderdetails.param.QueryOrderDetailsResponse;
import com.ai.yc.order.api.orderlevel.interfaces.IOrderLevelSV;
import com.ai.yc.order.api.orderlevel.param.OrderLevelRequest;
import com.ai.yc.order.api.orderlevel.param.OrderLevelResponse;
import com.ai.yc.order.api.updateorder.interfaces.IUpdateOrderSV;
import com.ai.yc.order.api.updateorder.param.UOrderFeeVo;
import com.ai.yc.order.api.updateorder.param.UProdFileVo;
import com.ai.yc.order.api.updateorder.param.UProdLevelVo;
import com.ai.yc.order.api.updateorder.param.UProdVo;
import com.ai.yc.order.api.updateorder.param.UpdateOrderRequest;
import com.ai.yc.order.api.updateorder.param.UpdateOrderResponse;
import com.ai.yc.translator.api.translatorservice.interfaces.IYCTranslatorServiceSV;
import com.ai.yc.translator.api.translatorservice.param.SearchYCTranslatorRequest;
import com.ai.yc.translator.api.translatorservice.param.YCLSPInfoReponse;
import com.ai.yc.translator.api.translatorservice.param.YCTranslatorInfoResponse;
import com.ai.yc.translator.api.translatorservice.param.searchYCLSPInfoRequest;
import com.ai.yc.ucenter.api.members.interfaces.IUcMembersSV;
import com.ai.yc.ucenter.api.members.param.get.UcMembersGetRequest;
import com.ai.yc.ucenter.api.members.param.get.UcMembersGetResponse;
import com.ai.yc.user.api.userservice.interfaces.IYCUserServiceSV;
import com.ai.yc.user.api.userservice.param.SearchYCUserRequest;
import com.ai.yc.user.api.userservice.param.YCUserInfoResponse;
import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/order")
public class OrdOrderController {
	
	private static final Logger log = LoggerFactory.getLogger(OrdOrderController.class);
	
	public final static String ORDER_DETAILS_PAGE = "jsp/order/orderDetails";
	
	 @RequestMapping("/orderdetails")
	 public ModelAndView toOrderDetailsPage(@RequestParam(value="mod",defaultValue="view")String mod,Long orderId) {
		 ModelAndView view = new ModelAndView(ORDER_DETAILS_PAGE);
		 view.addObject("model", mod);
		 view.addObject("orderId", orderId);
	     return view;
	 } 
	
	@RequestMapping("/queryOrderDetails")
	@ResponseBody
	public ResponseData<OrdOrderDetails> queryOrderDetails(Long orderId){
		IQueryOrderDetailsSV iQueryOrderDetailsSV = DubboConsumerFactory.getService(IQueryOrderDetailsSV.class);
		QueryOrderDetailsResponse resp = null;
		try {
			resp =iQueryOrderDetailsSV.queryOrderDetails(orderId);
		} catch (Exception e) {
			log.error("系统异常，请稍后重试", e);
			return new ResponseData<OrdOrderDetails>(ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
		}
		if(resp==null){
			log.error("系统异常，请稍后重试");
			return new ResponseData<OrdOrderDetails>(ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
		}
		if(!resp.getResponseHeader().isSuccess()){
			log.error(resp.getResponseHeader().getResultMessage());
			return new ResponseData<OrdOrderDetails>(ResponseData.AJAX_STATUS_FAILURE, resp.getResponseHeader().getResultMessage(), null);
		}
		OrdOrderDetails details = new OrdOrderDetails();
		BeanUtils.copyProperties(details, resp);
		//调用用户中心
		installUserInfo(details);
		return new ResponseData<OrdOrderDetails>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功", details);
		
	}
	
	private void installUserInfo(OrdOrderDetails details){
		IYCUserServiceSV iYCUserServiceSV = DubboConsumerFactory.getService(IYCUserServiceSV.class);
		if(!StringUtil.isBlank(details.getUserId())){
			SearchYCUserRequest searchYCUserReq = new SearchYCUserRequest();
			searchYCUserReq.setUserId(details.getUserId());
			try {
				YCUserInfoResponse user = iYCUserServiceSV.searchYCUserInfo(searchYCUserReq);
				details.setUsernick(user.getNickname());
			} catch (Exception e) {
				log.error("获取用户昵称失败", e);
			}
			IUcMembersSV ucMembersSV = DubboConsumerFactory.getService(IUcMembersSV.class);
			UcMembersGetRequest membersGetRequest = new UcMembersGetRequest();
			membersGetRequest.setUsername(details.getUserId());
			membersGetRequest.setGetmode("1");
			try {	
				UcMembersGetResponse ucMember = ucMembersSV.ucGetMember(membersGetRequest);	
				Object username = ucMember.getDate().get("username");
				details.setUsername((String)username);
			}catch (Exception e) {
				log.error("获取用户名称信息失败", e);
			}
			
			
            

		}
		IYCTranslatorServiceSV iYCTranslatorServiceSV = DubboConsumerFactory.getService(IYCTranslatorServiceSV.class);
		if(!StringUtil.isBlank(details.getLspId())){
			searchYCLSPInfoRequest lSPInfoRequest = new searchYCLSPInfoRequest();
			lSPInfoRequest.setLspId(details.getLspId());
			try {
				YCLSPInfoReponse lsp = iYCTranslatorServiceSV.searchLSPInfo(lSPInfoRequest);
				details.setLspName(lsp.getLspName());
			} catch (Exception e) {
				log.error("获取lsp信息失败", e);
			}
		}
		if(!StringUtil.isBlank(details.getInterperId())){
			SearchYCTranslatorRequest  translatorRequest = new SearchYCTranslatorRequest();
			translatorRequest.setTranslatorId(details.getInterperId());
			try {
				YCTranslatorInfoResponse  interper = iYCTranslatorServiceSV.searchYCTranslatorInfo(translatorRequest);
				details.setInterperName(interper.getNickname());
			} catch (Exception e) {
				log.error("获取译员信息失败", e);
			}
		}
	}
	
	 
	
	/**
	 * 获取价格
	 */
	@RequestMapping("/queryAutoOffer")
	@ResponseBody
    public ResponseData<QueryAutoOfferRes> queryAutoOffer(QueryAutoOfferReq req){
		IQueryAutoOfferSV iQueryAutoOfferSV = DubboConsumerFactory.getService(IQueryAutoOfferSV.class);
		QueryAutoOfferRes resp = null;
		try {
			resp = iQueryAutoOfferSV.queryAutoOffer(req);
		} catch (Exception e) {
			log.error("系统异常，请稍后重试", e);
			return new ResponseData<QueryAutoOfferRes>(ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
		}
		if(resp==null){
			log.error("系统异常，请稍后重试");
			return new ResponseData<QueryAutoOfferRes>(ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
		}
		if(!resp.getResponseHeader().isSuccess()){
			log.error(resp.getResponseHeader().getResultMessage());
			return new ResponseData<QueryAutoOfferRes>(ResponseData.AJAX_STATUS_FAILURE, resp.getResponseHeader().getResultMessage(), null);
		}
		return new ResponseData<QueryAutoOfferRes>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功", resp);
		
	}
	
	/**
	 * 获取订单级别
	 */
	@RequestMapping("/getOrderLevel")
	@ResponseBody
    public ResponseData<String> getOrderLevel(OrderLevelRequest req,String fee){
		IOrderLevelSV iOrderLevelSV = DubboConsumerFactory.getService(IOrderLevelSV.class);
		OrderLevelResponse resp = null;
		try {
			req.setTotalFee(yuanToli2(fee));
			resp = iOrderLevelSV.getOrderLevel(req);
		} catch (Exception e) {
			log.error("系统异常，请稍后重试", e);
			return new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
		}
		if(resp==null){
			log.error("系统异常，请稍后重试");
			return new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
		}
		if(!resp.getResponseHeader().isSuccess()){
			log.error(resp.getResponseHeader().getResultMessage());
			return new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, resp.getResponseHeader().getResultMessage(), null);
		}
		return new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功", resp.getOrderLevel());
		
	}
	
	/**
	 * 修改订单
	 */
	@RequestMapping("/updateOrderInfo")
	@ResponseBody
    public ResponseData<Boolean> updateOrderInfo(UpdateOrderRequest req,OrderDetailPagerRequest pager){
		log.info("req1:"+JSON.toJSONString(req));
		req = installUpdateOrderRequest(req,pager);
		log.info("req2:"+JSON.toJSONString(req));
		IUpdateOrderSV iUpdateOrderSV = DubboConsumerFactory.getService(IUpdateOrderSV.class);
		UpdateOrderResponse resp = null;
		try {
			resp = iUpdateOrderSV.updateOrderInfo(req);
		} catch (Exception e) {
			log.error("系统异常，请稍后重试", e);
			return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
		}
		if(resp==null){
			log.error("系统异常，请稍后重试");
			return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
		}
		if(!resp.getResponseHeader().isSuccess()){
			log.error(resp.getResponseHeader().getResultMessage());
			return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_FAILURE, resp.getResponseHeader().getResultMessage(), null);
		}
		return new ResponseData<Boolean>(ResponseData.AJAX_STATUS_SUCCESS, "修改订单成功", true);
		
	}
	
	private UpdateOrderRequest installUpdateOrderRequest(UpdateOrderRequest req,OrderDetailPagerRequest pager){
		if(req==null){
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "参数不能为空");
		}
		GeneralSSOClientUser loginUser = LoginUtil.getLoginUser();
		req.setOperId(loginUser.getUserId());
		req.setOperName(loginUser.getLoginName());
		if(pager==null){
			return req;
		}
		
		UOrderFeeVo orderFee = req.getOrderFee();
		if(orderFee!=null){
			if(StringUtil.isBlank(pager.getTotalFee())){
				orderFee.setTotalFee(0l);
			}else{
				orderFee.setTotalFee(yuanToli(pager.getTotalFee()));
			}
			
			if(StringUtil.isBlank(pager.getSetTypeFee())){
				orderFee.setSetTypeFee(0l);
			}else{
				orderFee.setSetTypeFee(yuanToli(pager.getSetTypeFee()));
			}
			
			if(StringUtil.isBlank(pager.getUrgentFee())){
				orderFee.setUrgentFee(0l);
			}else{
				orderFee.setUrgentFee(yuanToli(pager.getUrgentFee()));
			}
			
			if(StringUtil.isBlank(pager.getDescTypeFee())){
				orderFee.setDescTypeFee(0l);
			}else{
				orderFee.setDescTypeFee(yuanToli(pager.getDescTypeFee()));
			}
			orderFee.setDiscountFee(0l);
			orderFee.setOperDiscountFee(0l);
			orderFee.setAdjustFee(orderFee.getTotalFee());
			orderFee.setPaidFee(0l);
			orderFee.setPayFee(0l);
			
			//orderFee.setUpdateOperId("10000");
			orderFee.setUpdateOperId(loginUser.getUserId());
			req.setOrderFee(orderFee);
			
		}
		if(pager.getTranslateLevel()!=null&&pager.getTranslateLevel().length>0){
			List<UProdLevelVo> prodLevels = new ArrayList<UProdLevelVo>();
			for(String level:pager.getTranslateLevel()){
				UProdLevelVo prodLevel = new UProdLevelVo();
				prodLevel.setTranslateLevel(level);
				prodLevels.add(prodLevel);
			}
			UProdVo prod = req.getProd();
			if(prod==null){
				prod = new UProdVo();
				req.setProd(prod);
			}
			prod.setProdLevels(prodLevels);
			req.setProd(prod);
		}
		
		if(pager.getStartTime()!=null&&pager.getEndTime()!=null){
			UProdVo prod = req.getProd();
			if(prod==null){
				prod = new UProdVo();
				req.setProd(prod);
			}
			Timestamp stateTime = new Timestamp(pager.getStartTime());
			Timestamp endTime = new Timestamp(pager.getEndTime());
			//prod.setStateTime(DateUtil.getTimestamp(pager.getStartTime(), "yyyy-MM-dd HH:mm:ss"));
			//prod.setEndTime(DateUtil.getTimestamp(pager.getEndTime(), "yyyy-MM-dd HH:mm:ss"));
			prod.setStateTime(stateTime);
			prod.setEndTime(endTime);
			req.setProd(prod);
		}
		
		if(pager.getFileSaveIds()!=null&&pager.getFileSaveIds().length>0){
			List<UProdFileVo> prodFiles = new ArrayList<UProdFileVo>();
			for(int i=0;i<pager.getFileSaveIds().length;i++){
				UProdFileVo prodFile = new UProdFileVo();
				prodFile.setFileName(pager.getFileNames()[i]);
				prodFile.setFileSaveId(pager.getFileSaveIds()[i]);
				if(pager.getFileTranslateIds()!=null&&pager.getFileTranslateIds()[i]!=null){
					prodFile.setFileTranslateName(pager.getFileTranslateNames()[i]);
					prodFile.setFileTranslateId(pager.getFileTranslateIds()[i]);
				}
				prodFiles.add(prodFile);
			}
			req.setProdFiles(prodFiles);
		}
		return req;
	}
	
	private long yuanToli(String yuan){
		BigDecimal fee = new BigDecimal(yuan);
		BigDecimal li = fee.multiply(new BigDecimal(1000));
		return li.longValue();
	}
	
	private int yuanToli2(String yuan){
		BigDecimal fee = new BigDecimal(yuan);
		BigDecimal li = fee.multiply(new BigDecimal(1000));
		return li.intValue();
	}
}
