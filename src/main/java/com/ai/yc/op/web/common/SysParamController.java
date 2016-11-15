package com.ai.yc.op.web.common;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.paas.ipaas.util.StringUtil;
import com.ai.yc.common.api.cache.interfaces.ICacheSV;
import com.ai.yc.common.api.cache.param.SysParam;
import com.ai.yc.common.api.cache.param.SysParamMultiCond;
import com.ai.yc.common.api.cache.param.SysParamSingleCond;
import com.ai.yc.common.api.sysduad.interfaces.IQuerySysDuadSV;
import com.ai.yc.common.api.sysduad.param.QuerySysDuadListReq;
import com.ai.yc.common.api.sysduad.param.QuerySysDuadListRes;
import com.ai.yc.common.api.sysduad.param.SysDuadVo;
import com.ai.yc.op.web.constant.Constants;
import com.ai.yc.op.web.model.order.SysDuadParam;

@RestController
public class SysParamController {
    /**
     * 获取下拉选项
     * @param param
     * @param request
     * @return
     * @author zhanglh
     * @ApiCode
     */
    @RequestMapping("/getSelect")
    @ResponseBody
    public ResponseData<List<SysParam>> getSelect(SysParamMultiCond param,HttpServletRequest request) {
        ResponseData<List<SysParam>> responseData=null;
        try{
            ICacheSV iCacheSV = DubboConsumerFactory.getService(ICacheSV.class);
            param.setTenantId(Constants.TENANT_ID);
            List<SysParam> info=iCacheSV.getSysParamList(param);
            responseData=new ResponseData<List<SysParam>>(ResponseData.AJAX_STATUS_SUCCESS,"获取数据成功",info);
        }catch(Exception e){
            responseData=new ResponseData<List<SysParam>>(ResponseData.AJAX_STATUS_FAILURE,"获取数据失败",null);
        }
        return responseData;
    }
    @RequestMapping("/getLangugeSelect")
    @ResponseBody
    public ResponseData<List<SysDuadParam>> getSelect() {
        ResponseData<List<SysDuadParam>> responseData=null;
        try{
        	QuerySysDuadListReq request = new QuerySysDuadListReq();
        	request.setLanguage(Constants.ZH_LANGE);
        	request.setOrderType("1");
        	IQuerySysDuadSV iQuerySysDuadSV = DubboConsumerFactory.getService(IQuerySysDuadSV.class);
        	ICacheSV iCacheSV = DubboConsumerFactory.getService(ICacheSV.class);
        	QuerySysDuadListRes res = iQuerySysDuadSV.querySysDuadList(request);
        	List<SysDuadVo> infoList = res.getDuads();
        	List<SysDuadParam> resultList = new ArrayList<SysDuadParam>();
        	if(res.getResponseHeader().isSuccess()==true){
        		for(SysDuadVo vo:infoList){
        			SysDuadParam param = new SysDuadParam();
        			BeanUtils.copyProperties(param, vo);
        			if(!StringUtil.isBlank(vo.getLanguage())){
        				if(Constants.ZH_LANGE.equals(vo.getLanguage())){
        					param.setLanguaNmae("中文站");
        				}else{
        					param.setLanguaNmae("英文站");
        				}
        			}
        			//翻译类型
        			SysParamSingleCond	paramCond = new SysParamSingleCond();
					paramCond.setTenantId(Constants.TENANT_ID);
					paramCond.setColumnValue(vo.getOrderType());
					paramCond.setTypeCode(Constants.TYPE_CODE);
					paramCond.setParamCode(Constants.ORD_TRANSLATE_TYPE);
	        		SysParam typeParam = iCacheSV.getSysParamSingle(paramCond);
	        		if(typeParam!=null){
	        			param.setTransTypeName(typeParam.getColumnDesc());
	        		}
        			resultList.add(param);
        		}
        	}
            responseData=new ResponseData<List<SysDuadParam>>(ResponseData.AJAX_STATUS_SUCCESS,"获取数据成功",resultList);
        }catch(Exception e){
            responseData=new ResponseData<List<SysDuadParam>>(ResponseData.AJAX_STATUS_FAILURE,"获取数据失败",null);
        }
        return responseData;
    }
}
