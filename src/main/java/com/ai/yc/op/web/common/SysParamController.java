package com.ai.yc.op.web.common;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.yc.common.api.cache.interfaces.ICacheSV;
import com.ai.yc.common.api.cache.param.SysParam;
import com.ai.yc.common.api.cache.param.SysParamMultiCond;
import com.ai.yc.common.api.sysduad.interfaces.IQuerySysDuadSV;
import com.ai.yc.common.api.sysduad.param.QuerySysDuadListReq;
import com.ai.yc.common.api.sysduad.param.QuerySysDuadListRes;
import com.ai.yc.common.api.sysduad.param.SysDuadVo;
import com.ai.yc.op.web.constant.Constants;
import com.ai.yc.op.web.model.order.SysDuadParam;

@RestController
public class SysParamController {
	private static final Logger logger = Logger.getLogger(SysParamController.class);
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
        	IQuerySysDuadSV iQuerySysDuadSV = DubboConsumerFactory.getService(IQuerySysDuadSV.class);
        	QuerySysDuadListRes res = iQuerySysDuadSV.querySysDuadList(request);
        	List<SysDuadVo> infoList = res.getDuads();
        	List<SysDuadParam> resultList = new ArrayList<SysDuadParam>();
        	if(true==res.getResponseHeader().isSuccess()){
        		for(SysDuadVo vo:infoList){
        			SysDuadParam param = new SysDuadParam();
        			BeanUtils.copyProperties(param, vo);
        			//翻译类型
        			/*SysParamSingleCond	paramCond = new SysParamSingleCond();
					paramCond.setTenantId(Constants.TENANT_ID);
					paramCond.setColumnValue(vo.getOrderType());
					paramCond.setTypeCode(Constants.TYPE_CODE);
					paramCond.setParamCode(Constants.ORD_TRANSLATE_TYPE);
	        		SysParam typeParam = iCacheSV.getSysParamSingle(paramCond);*/
	        		/*if(typeParam!=null){
	        			if(Constants.TRANSLATE_TYPE_COMMON.equals(vo.getOrderType())){
	        				param.setTransTypeName(typeParam.getColumnDesc());
	        			}else{
	        				
	        			}
	        			param.setTransTypeName(typeParam.getColumnDesc());
	        		}*/
        			if(Constants.TRANSLATE_TYPE_COMMON.equals(vo.getOrderType())){
        				param.setTransTypeName(Constants.TRANSLATE_TYPE_PEN);
        			}else{
        				param.setTransTypeName(Constants.TRANSLATE_TYPE_MOU);
        			}
        			resultList.add(param);
        		}
        	}
            responseData=new ResponseData<List<SysDuadParam>>(ResponseData.AJAX_STATUS_SUCCESS,"获取数据成功",resultList);
        }catch(Exception e){
        	logger.error("获取语言对数据失败", e);
            responseData=new ResponseData<List<SysDuadParam>>(ResponseData.AJAX_STATUS_FAILURE,"获取数据失败",null);
        }
        return responseData;
    }
}
