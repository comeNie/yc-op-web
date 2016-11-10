package com.ai.yc.op.web.common;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.yc.common.api.cache.interfaces.ICacheSV;
import com.ai.yc.common.api.cache.param.SysParam;
import com.ai.yc.common.api.cache.param.SysParamMultiCond;
import com.ai.yc.common.api.sysduad.interfaces.IQuerySysDuadSV;
import com.ai.yc.common.api.sysduad.param.QuerySysDuadListReq;
import com.ai.yc.common.api.sysduad.param.QuerySysDuadListRes;
import com.ai.yc.common.api.sysduad.param.SysDuadVo;
import com.ai.yc.op.web.constant.Constants;

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
    public ResponseData<List<SysDuadVo>> getSelect(String orderType) {
        ResponseData<List<SysDuadVo>> responseData=null;
        try{
        	QuerySysDuadListReq request = new QuerySysDuadListReq();
        	request.setLanguage("zh_CN");
        	List<SysDuadVo> info = new ArrayList<SysDuadVo>();
        	IQuerySysDuadSV iQuerySysDuadSV = DubboConsumerFactory.getService(IQuerySysDuadSV.class);
        	QuerySysDuadListRes res = iQuerySysDuadSV.querySysDuadList(request);
        	if(res.getResponseHeader().isSuccess()==true){
        		info=res.getDuads();
        	}
            responseData=new ResponseData<List<SysDuadVo>>(ResponseData.AJAX_STATUS_SUCCESS,"获取数据成功",info);
        }catch(Exception e){
            responseData=new ResponseData<List<SysDuadVo>>(ResponseData.AJAX_STATUS_FAILURE,"获取数据失败",null);
        }
        return responseData;
    }
}
