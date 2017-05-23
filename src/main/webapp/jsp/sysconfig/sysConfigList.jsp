<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>基本设置</title>
<style type="text/css">
        input{
            width:80px;
            height:30px;
            border:1px solid black;
        }
    </style>
<%@include file="/inc/inc.jsp" %>
</head>
<body>
   
   <div class="content-wrapper-iframe" ><!--右侧灰色背景-->
        <!--框架标签结束-->
      <div class="row"><!--外围框架-->
     	<div class="col-lg-12"><!--删格化-->
             <div class="row"><!--内侧框架-->
	                 <div class="col-lg-12"><!--删格化--></div>
              </div>
         </div>
     </div>	
   	  	<div class="row"><!--外围框架-->
            <div class="col-lg-12"><!--删格化-->
                <div class="row"><!--内侧框架-->
                    <div class="col-lg-12"><!--删格化-->
                        <div class="main-box clearfix"><!--白色背景-->
                            <div class="main-box-body clearfix">
                            	<!--table表格-->
                                <div class="table-responsive clearfix">
                                   	<!--table表格-->
                          		<div class="table-responsive clearfix mt-10">
                          		<form id="dataForm">
                          		<div>
                          			<input type="hidden" name="configId" value="${memberConfig.configId}">
	                          		<p>
	                          			会员等级设置：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                          			普通会员：&nbsp;&nbsp;&nbsp;&nbsp;<input id="ordinayryMember" name="ordinaryMember" type="text" value="${memberConfig.ordinaryMember}">&nbsp;&nbsp;&nbsp;&nbsp;
	                          			黄金会员：&nbsp;&nbsp;&nbsp;&nbsp;<input id="goldMember" name="goldMember" type="text" value="${memberConfig.goldMember}">&nbsp;&nbsp;&nbsp;&nbsp;
	                          			白金会员：&nbsp;&nbsp;&nbsp;&nbsp;<input id="platinumMember" name="platinumMember" type="text" value="${memberConfig.platinumMember}">&nbsp;&nbsp;&nbsp;&nbsp;
	                          			钻石会员：&nbsp;&nbsp;&nbsp;&nbsp;<input id="masonyMember" name="masonryMember" type="text" value="${memberConfig.masonryMember}">
	                          		</p><br/>
	                          		<p>
	                          			平台佣金比例：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                          			封顶值：&nbsp;&nbsp;&nbsp;&nbsp;<input id="capValue" name="capValue" type="text" value="${commissionConfig.capValue}">元/美元&nbsp;&nbsp;&nbsp;&nbsp;
	                          			v1译员：&nbsp;&nbsp;&nbsp;&nbsp;<input id="v1Points" name="v1Points" type="text" value="${commissionConfig.v1Points}">%&nbsp;&nbsp;&nbsp;&nbsp;
	                          			v2译员：&nbsp;&nbsp;&nbsp;&nbsp;<input id="v2Points" name="v2Points" type="text" value="${commissionConfig.v2Points}">%&nbsp;&nbsp;&nbsp;&nbsp;
	                          			v3译员：&nbsp;&nbsp;&nbsp;&nbsp;<input id="v3Points" name="v3Points" type="text" value="${commissionConfig.v3Points}">%&nbsp;&nbsp;&nbsp;&nbsp;
	                          			lsp团队：&nbsp;&nbsp;&nbsp;&nbsp;<input id="lspPoints" name="lspPoints" type="text" value="${commissionConfig.lspPoints}">%
	                          		</p><br/>
	                          		<p>
	                          			首页数据编辑：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                          			语料：&nbsp;&nbsp;&nbsp;&nbsp;<input id="lgdateNum" name="lgdateNum" type="text" value="${homeDataEidtConfig.lgdataNum}">&nbsp;&nbsp;&nbsp;&nbsp;
	                          			客户：&nbsp;&nbsp;&nbsp;&nbsp;<input id="customNum" name="customNum" type="text" value="${homeDataEidtConfig.customNum}">&nbsp;&nbsp;&nbsp;&nbsp;
	                          			译员：&nbsp;&nbsp;&nbsp;&nbsp;<input id="interpreterNum" name="interpreterNum" type="text" value="${homeDataEidtConfig.interpreterNum}">&nbsp;&nbsp;&nbsp;&nbsp;
	                          			订单：&nbsp;&nbsp;&nbsp;&nbsp;<input id="orderNum" name="orderNum" type="text" value="${homeDataEidtConfig.orderNum}">&nbsp;&nbsp;&nbsp;&nbsp;
	                          			语种: &nbsp;&nbsp;&nbsp;&nbsp;<input id="languageNum" name="languageNum" type="text" value="${homeDataEidtConfig.languageNum}">
	                          		</p>
                          		</div>
                          		<div style="border: 1px solid black;">
                          			<p>
                          				&nbsp;&nbsp;登录送积分:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                          				<input type="radio" style="height: 16px;" name="lstate" value="0" ${donateIntegralConfig.lstate=='0'?'checked':'' }>禁用
                          				<input type="radio" style="height: 16px;" name="lstate" value="1" ${donateIntegralConfig.lstate=='1'?'checked':'' }>启用
                          			</p><br/>
                          			<p>
	                          			&nbsp;&nbsp;
	                          			连续1日：&nbsp;&nbsp;&nbsp;&nbsp;<input id="oneDay" name="oneDay" type="text" value="${donateIntegralConfig.oneday}">&nbsp;&nbsp;&nbsp;&nbsp;
	                          			连续2日：&nbsp;&nbsp;&nbsp;&nbsp;<input id="twoDay" name="twoDay" type="text" value="${donateIntegralConfig.twoday}">&nbsp;&nbsp;&nbsp;&nbsp;
	                          			连续3日：&nbsp;&nbsp;&nbsp;&nbsp;<input id="threeDay" name="threeDay" type="text" value="${donateIntegralConfig.threeday}">&nbsp;&nbsp;&nbsp;&nbsp;
	                          			连续4日：&nbsp;&nbsp;&nbsp;&nbsp;<input id="fourDay" name="fourDay" type="text" value="${donateIntegralConfig.fourday}">
	                          		</p><br/>
	                          		<p>
	                          			&nbsp;&nbsp;
	                          			连续5日：&nbsp;&nbsp;&nbsp;&nbsp;<input id="fiveDay" name="fiveDay" type="text" value="${donateIntegralConfig.fiveday}">&nbsp;&nbsp;&nbsp;&nbsp;
	                          			连续6日：&nbsp;&nbsp;&nbsp;&nbsp;<input id="sixDay" name="sixDay" type="text" value="${donateIntegralConfig.sixday}">&nbsp;&nbsp;&nbsp;&nbsp;
	                          			连续7日：&nbsp;&nbsp;&nbsp;&nbsp;<input id="sevenDay" name="sevenDay" type="text" value="${donateIntegralConfig.sevenday}">
	                          		</p>
                          		</div><br/>
                          		<div style="border: 1px solid black;">
                          			<p>
                          				&nbsp;&nbsp;注册活动<br/>
                          				&nbsp;&nbsp;注册送积分：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                          				<input id="regIntegration" name=regIntegration style="width: 300px;" type="text" value="${donateIntegralConfig.regIntegration}">&nbsp;&nbsp;&nbsp;&nbsp;
                          				<input type="radio" style="height: 16px;" name="rstate" value="1" ${donateIntegralConfig.rstate=='1'?'checked':'' }>开启
                          				<input type="radio" style="height: 16px;" name="rstate" value="0" ${donateIntegralConfig.rstate=='0'?'checked':'' }>关闭<br/><br/>
                          				&nbsp;&nbsp;注册送成长值：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                          				<input id="regGrowth" name="regGrowth" style="width: 300px;" type="text" value="${donateIntegralConfig.regGrowth}">&nbsp;&nbsp;&nbsp;&nbsp;
                          				<input type="radio" style="height: 16px;" name="gstate" value="1" ${donateIntegralConfig.gstate=='1'?'checked':'' }>开启
										<input type="radio" style="height: 16px;" name="gstate" value="0" ${donateIntegralConfig.gstate=='0'?'checked':'' }>关闭<br/><br/>
										&nbsp;&nbsp;邀请注册送积分：&nbsp;&nbsp;
										<input id="inviteIntegrati" name="inviteIntegrati" style="width: 300px;" type="text" value="${donateIntegralConfig.inviteIntegrati}">&nbsp;&nbsp;&nbsp;&nbsp;
										<input type="radio" style="height: 16px;" name="istate" value="1" ${donateIntegralConfig.istate=='1'?'checked':'' }>开启
										<input type="radio" style="height: 16px;" name="istate" value="0" ${donateIntegralConfig.istate=='0'?'checked':'' }>关闭
                          			</p>
                          		</div><br/>
                          		<div style="border: 1px solid black;">
                          			<p>
	                          			&nbsp;&nbsp;通告：<br/><br/>
	                          			&nbsp;&nbsp;PC端通告：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                          			<input id="pcNotice" name="pcNotice" style="width: 500px;" type="text" value="${noticeConfig.pcNotice}"><br/><br/>
	                          			&nbsp;&nbsp;WAP端通告：&nbsp;&nbsp;&nbsp;&nbsp;
	                          			<input id="wapNotice" name="wapNotice" style="width: 500px;" type="text" value="${noticeConfig.wapNotice}">
                          			</p>
                          		</div>
                          		</form>
                          		<div class="row mt-15">
									    <p class="center pr-30 mt-30">
										   <input type="button" style="width: 150px;" id="save" value="保存设置">
										</p>
									</div>
                                </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
    	</div>
   </div> 
 <script type="text/javascript">
	var pager;
	(function () {
		seajs.use('app/jsp/sysconfig/sysConfigList', function (OrderListPager) {
			pager = new OrderListPager({element: document.body});
			pager.render();
		});
	})();
 </script>  
</body>
