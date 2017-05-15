package com.ai.yc.op.web.controller.sysitembank;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.yc.common.api.sysitembank.interfaces.IQuerySysItemBankSV;
import com.ai.yc.common.api.sysitembank.param.ItemBankPageQueryRequest;
import com.ai.yc.common.api.sysitembank.param.ItemBankPageQueryResponse;
import com.ai.yc.common.api.sysitembank.param.ItemBankPageVo;

@Controller
@RequestMapping("/sysitembank")
public class SysItemBankListController {
	
	private static final Logger logger = Logger.getLogger(SysItemBankListController.class);
	
	@RequestMapping("/toSysItemBankList")
	public ModelAndView toSysItemBankList(HttpServletRequest request) {
		return new ModelAndView("jsp/sysitembank/sysItemBankList");
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
			logger.error("查询用途列表失败：", e);
			responseData = new ResponseData<PageInfo<ItemBankPageVo>>(ResponseData.AJAX_STATUS_FAILURE, "查询信息异常", null);
		}
	    return responseData;
    }
    
}
