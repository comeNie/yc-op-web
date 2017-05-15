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
            width:50px;
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
                          			<input type="hidden" name="id" value="${queryBaJfRe.id}">
                          			<input type="hidden" name="aid" value="${queryBaJfRe.aid}">
                          			<input type="hidden" name="did" value="${queryBaJfRe.did}">
	                          		<p>
	                          			会员等级设置：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                          			普通会员：&nbsp;&nbsp;&nbsp;&nbsp;<input id="ordinayryMember" name="ordinayryMember" type="text" value="${queryBaJfRe.ordinayryMember}">&nbsp;&nbsp;&nbsp;&nbsp;
	                          			黄金会员：&nbsp;&nbsp;&nbsp;&nbsp;<input id="goldMember" name="goldMember" type="text" value="${queryBaJfRe.goldMember}">&nbsp;&nbsp;&nbsp;&nbsp;
	                          			白金会员：&nbsp;&nbsp;&nbsp;&nbsp;<input id="platinumMember" name="platinumMember" type="text" value="${queryBaJfRe.platinumMember}">&nbsp;&nbsp;&nbsp;&nbsp;
	                          			钻石会员：&nbsp;&nbsp;&nbsp;&nbsp;<input id="masonyMember" name="masonyMember" type="text" value="${queryBaJfRe.masonyMember}">
	                          		</p><br/>
	                          		<p>
	                          			平台佣金比例：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                          			封顶值：&nbsp;&nbsp;&nbsp;&nbsp;<input id="capValue" name="capValue" type="text" value="${queryBaJfRe.capValue}">元/美元&nbsp;&nbsp;&nbsp;&nbsp;
	                          			v1译员：&nbsp;&nbsp;&nbsp;&nbsp;<input id="v1Points" name="v1Points" type="text" value="${queryBaJfRe.v1Points}">%&nbsp;&nbsp;&nbsp;&nbsp;
	                          			v1译员：&nbsp;&nbsp;&nbsp;&nbsp;<input id="v2Points" name="v2Points" type="text" value="${queryBaJfRe.v2Points}">%&nbsp;&nbsp;&nbsp;&nbsp;
	                          			v1译员：&nbsp;&nbsp;&nbsp;&nbsp;<input id="v3Points" name="v3Points" type="text" value="${queryBaJfRe.v3Points}">%&nbsp;&nbsp;&nbsp;&nbsp;
	                          			lsp团队：&nbsp;&nbsp;&nbsp;&nbsp;<input id="lspPoints" name="lspPoints" type="text" value="${queryBaJfRe.lspPoints}">%
	                          		</p><br/>
	                          		<p>
	                          			首页数据编辑：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                          			语料：&nbsp;&nbsp;&nbsp;&nbsp;<input id="lgdateNum" name="lgdateNum" type="text" value="${queryBaJfRe.lgdateNum}">&nbsp;&nbsp;&nbsp;&nbsp;
	                          			客户：&nbsp;&nbsp;&nbsp;&nbsp;<input id="customNum" name="customNum" type="text" value="${queryBaJfRe.customNum}">&nbsp;&nbsp;&nbsp;&nbsp;
	                          			译员：&nbsp;&nbsp;&nbsp;&nbsp;<input id="interpreterNum" name="interpreterNum" type="text" value="${queryBaJfRe.interpreterNum}">&nbsp;&nbsp;&nbsp;&nbsp;
	                          			订单：&nbsp;&nbsp;&nbsp;&nbsp;<input id="orderNum" name="orderNum" type="text" value="${queryBaJfRe.orderNum}">&nbsp;&nbsp;&nbsp;&nbsp;
	                          			语种: &nbsp;&nbsp;&nbsp;&nbsp;<input id="languageNum" name="languageNum" type="text" value="${queryBaJfRe.languageNum}">
	                          		</p>
                          		</div>
                          		<div style="border: 1px solid black;">
                          			<p>
                          				&nbsp;&nbsp;登录送积分:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                          				<input type="radio" style="height: 16px;" name="dstate" value="0" ${queryBaJfRe.dstate=='0'?'checked':'' }>禁用
                          				<input type="radio" style="height: 16px;" name="dstate" value="1" ${queryBaJfRe.dstate=='1'?'checked':'' }>启用
                          			</p><br/>
                          			<p>
	                          			&nbsp;&nbsp;
	                          			连续1日：&nbsp;&nbsp;&nbsp;&nbsp;<input id="oneDay" name="oneDay" type="text" value="${queryBaJfRe.oneDay}">&nbsp;&nbsp;&nbsp;&nbsp;
	                          			连续2日：&nbsp;&nbsp;&nbsp;&nbsp;<input id="twoDay" name="twoDay" type="text" value="${queryBaJfRe.twoDay}">&nbsp;&nbsp;&nbsp;&nbsp;
	                          			连续3日：&nbsp;&nbsp;&nbsp;&nbsp;<input id="threeDay" name="threeDay" type="text" value="${queryBaJfRe.threeDay}">&nbsp;&nbsp;&nbsp;&nbsp;
	                          			连续4日：&nbsp;&nbsp;&nbsp;&nbsp;<input id="fourDay" name="fourDay" type="text" value="${queryBaJfRe.fourDay}">
	                          		</p><br/>
	                          		<p>
	                          			&nbsp;&nbsp;
	                          			连续5日：&nbsp;&nbsp;&nbsp;&nbsp;<input id="fiveDay" name="fiveDay" type="text" value="${queryBaJfRe.fiveDay}">&nbsp;&nbsp;&nbsp;&nbsp;
	                          			连续6日：&nbsp;&nbsp;&nbsp;&nbsp;<input id="sixDay" name="sixDay" type="text" value="${queryBaJfRe.sixDay}">&nbsp;&nbsp;&nbsp;&nbsp;
	                          			连续7日：&nbsp;&nbsp;&nbsp;&nbsp;<input id="sevenDay" name="sevenDay" type="text" value="${queryBaJfRe.sevenDay}">
	                          		</p>
                          		</div><br/>
                          		<div style="border: 1px solid black;">
                          			<p>
                          				&nbsp;&nbsp;注册活动<br/>
                          				&nbsp;&nbsp;注册送积分：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                          				<input id="activiceNum" name="activiceNum" style="width: 300px;" type="text" value="${queryBaJfRe.activiceNum}">&nbsp;&nbsp;&nbsp;&nbsp;
                          				<input type="radio" style="height: 16px;" name="astate" value="0" ${queryBaJfRe.astate=='1'?'checked':'' }>开启
                          				<input type="radio" style="height: 16px;" name="astate" value="0" ${queryBaJfRe.astate=='0'?'checked':'' }>关闭<br/><br/>
                          				&nbsp;&nbsp;注册送成长值：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                          				<input id="activiceNum" name="activiceNum" style="width: 300px;" type="text" value="${queryBaJfRe.activiceNum}">&nbsp;&nbsp;&nbsp;&nbsp;
                          				<input type="radio" style="height: 16px;" name="astate" value="1" ${queryBaJfRe.astate=='1'?'checked':'' }>开启
										<input type="radio" style="height: 16px;" name="astate" value="0" ${queryBaJfRe.astate=='0'?'checked':'' }>关闭<br/><br/>
										&nbsp;&nbsp;邀请注册送积分：&nbsp;&nbsp;
										<input id="activiceNum" name="activiceNum" style="width: 300px;" type="text" value="${queryBaJfRe.activiceNum}">&nbsp;&nbsp;&nbsp;&nbsp;
										<input type="radio" style="height: 16px;" name="astate" value="0" ${queryBaJfRe.astate=='1'?'checked':'' }>开启
										<input type="radio" style="height: 16px;" name="astate" value="1" ${queryBaJfRe.astate=='0'?'checked':'' }>关闭
                          			</p>
                          		</div><br/>
                          		<div style="border: 1px solid black;">
                          			<p>
	                          			&nbsp;&nbsp;通告：<br/><br/>
	                          			&nbsp;&nbsp;PC端通告：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                          			<input id="pcNotice" name="pcNotice" style="width: 500px;" type="text" value="${queryBaJfRe.pcNotice}"><br/><br/>
	                          			&nbsp;&nbsp;WAP端通告：&nbsp;&nbsp;&nbsp;&nbsp;
	                          			<input id="wapNotice" name="wapNotice" style="width: 500px;" type="text" value="${queryBaJfRe.wapNotice}">
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
   
   
<script id="sysPurposeListTemple" type="text/template">
	
</script>
 <script type="text/javascript">
	var pager;
	(function () {
		seajs.use('app/jsp/sysbasic/sysBasicList', function (OrderListPager) {
			pager = new OrderListPager({element: document.body});
			pager.render();
		});
	})();
 </script>  
</body>
