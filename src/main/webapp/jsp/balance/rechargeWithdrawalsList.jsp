<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8"/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
	<title>充值提现管理</title>
	<%@include file="/inc/inc.jsp" %>
	<input type="hidden" id="billIdUpdate"/>
	<input type="hidden" id="billState"/>
	<input type="hidden" id="accountAmoutUpdate"/>
	<input type="hidden" id="settleAccountUpdate"/>
</head>
<body>
<div class="content-wrapper-iframe" ><!--右侧灰色背景-->
	<!--框架标签结束-->
	<div class="row"><!--外围框架-->
		<div class="col-lg-12"><!--删格化-->
			<div class="row"><!--内侧框架-->
				<div class="col-lg-12"><!--删格化-->
					<div class="main-box clearfix"><!--白色背景-->
						<div class="form-label">
							<div id="selectDiv">
								<ul>
									<li class="col-md-6">
										<p class="word">用户名</p>
										<p><input class="int-text int-medium" id="username"  value="" type="text"></p>
									</li>
									<!-- <li class="col-md-6">
										<p class="word">姓名</p>
										<p><input class="int-text int-medium" id="fullname"  value="" type="text"></p>
									</li> -->
									<li class="col-md-6">
										<p class="word">申请人/处理人</p>
										<p><input class="int-text int-medium" id=""  value="" type="text"></p>
									</li>
									<li class="col-md-6">
										<p class="word">结算方式</p>
										<p>
											<select class="select select-medium" id="pattem">
												<option value="" selected="selected">全部</option>
												<!-- 支付宝 -->
												<option value="1">支付宝</option>
												<!--微信 -->
												<option value="2">微信</option>
												<!-- 银行汇款/转账 -->
												<option value="3">银行汇款/转账</option>
												<%--PayPal--%>
												<option value="4">PayPal</option>
											</select>
										</p>
									</li>
									<li class="col-md-6">
										<p class="word">状态</p>
										<p>
											<select class="select select-medium" id="state">
												<option value="" selected="selected">全部</option>
												<!-- 待审核 -->
												<option value="1">待审核</option>
												<!--已确认 -->
												<option value="2">已确认</option>
												<!-- 锁定 -->
												<option value="3">锁定</option>
											</select>
										</p>
									</li>
									<li class="col-md-6">
										<p class="word">类型</p>
										<p>
											<select class="select select-medium" id="type">
												<option value="" selected="selected">全部</option>
												<!-- 充值 -->
												<option value="1">充值</option>
												<!--提现 -->
												<option value="2">提现</option>
											</select>
										</p>
									</li>
									<li class="col-md-6">
										<p class="word">币种单位</p>
										<p>
											<select class="select select-medium" id="currency">
												<option value="" selected="selected">全部</option>
												<!-- 充值 -->
												<option value="1">CNY ¥</option>
												<!--提现 -->
												<option value="2">USD $</option>
											</select>
										</p>
									</li>
								</ul>

							</div>
							<ul>
								<li class="width-xlag">
									<p class="word">&nbsp;&nbsp;&nbsp;</p>
									<p class="word"><input type="button" class="biu-btn  btn-primary btn-blue btn-medium ml-5"
														   id="search" value="查  询"></p>
									<p><input type="button" class="biu-btn  btn-primary btn-blue btn-medium ml-5"
											  id="export" value="导  出"></p>
									<p><input type="button" class="biu-btn  btn-primary btn-blue btn-medium ml-5"
											  id="confirm" value="批量确认"></p>
								</li>
							</ul>
						</div>
						<!--查询结束-->
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="row"><!--外围框架-->
		<div class="col-lg-12"><!--删格化-->
			<div class="row"><!--内侧框架-->
				<div class="col-lg-12"><!--删格化-->
					<div class="main-box clearfix"><!--白色背景-->
						<p><input type="button" class="biu-btn  btn-primary btn-blue btn-medium ml-5"
								  id="add" value="新建"></p>
						<div class="main-box-body clearfix">
							<div class="order-list-table">
								<ul>
									<li ><a href="#" id="waitHandle" class="current" >待处理</a></li>
									<li ><a href="#"  id="all" >全部列表</a></li>
								</ul>
							</div>
						</div>
						<!--标题结束-->
						<div class="main-box-body clearfix">
							<!--table表格-->
							<div class="table-responsive clearfix">
								<!--table表格-->
								<div class="table-responsive clearfix mt-10">
									<table class="table table-hover table-border table-bordered ">
										<thead>
										<tr>
											<th><input type="checkbox" id="allBox">  编号</th>
											<th>用户名</th>
											<th>手机</th>
											<th>类型</th>
											<th>金额/明细</th>
											<th>方式</th>
											<th>申请人</th>
											<th>申请时间</th>
											<th>处理人</th>
											<th>处理时间</th>
											<th>状态</th>
											<th>操作</th>
										</tr>
										</thead>
										<tbody id="orderListData"></tbody>
									</table>
									<div id="showMessage"></div>
								</div>
								<!--/table表格结束-->
							</div>
							<!--分页-->
							<div>
								<nav style="text-align: center">
									<ul id="pagination"></ul>
								</nav>
							</div>
							<!--分页结束-->
						</div>
						<!-- 弹出框 start-->
						<%-- <form id="dataForm" method="post">
							<div class="eject-big">
								<div class="eject-medium" id="add-samll">
									<div class="eject-medium-title">
										<p>结算</p>
										<p class="img" id="colseImage"><i class="fa fa-times" ></i></p>
									</div>
									<div class="form-label mt-10">
										<ul>
											<li>
												<p class="word">应结金额:</p>
												<p>
													<input type="text" class="int-text int-small" id="updateMoney" readonly>
													<input type="hidden"  id="realmoney">
												</p>
												<p id="errorMessage"></p>
											</li>
											<li>
												<p class="word">支付方式:</p>
												<p>
													<select class="select select-medium" id="payStyle" name="payStyle">
														<option value="" selected="selected">全部</option>
														<!-- 支付宝 -->
														<option value="1">支付宝</option>
														<!--微信 -->
														<option value="2">微信</option>
														<!--银行汇款/转账  -->
														<option value="3">银行汇款/转账</option>
														PayPal
														<option value="4">PayPal</option>
													</select>
												</p>
											</li>
											<li>
												<p class="word">账户*:</p>
												<p><input type="text" id="account" name="account" class="int-text int-small" ></p>
											</li>
										</ul>
									</div>
									<!--按钮-->
									<div class="row mt-15"><!--删格化-->
										<p class="center pr-30 mt-30">
											<input type="button" id="update" class="biu-btn  btn-primary  btn-auto  ml-5" value="确  认">
											<input id="add-close" type="button" class="biu-btn  btn-primary  btn-auto  ml-5 edit-close" value="取  消">
										</p>
									</div>
								</div>
								<div class="mask" id="eject-mask"></div>
							</div>
						</form> --%>
						<!-- 弹出框 end-->
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script id="orderListTemple" type="text/template">
	<tr>
		<td><input type="checkbox" name="subBox">{{:wid}}</td>
		<td>{{:username}}</td>
		<td>{{:mobilephone}}</td>
		<td>
			{{if  type == '1'}}
			充值
			{{else type == '2'}}
			提现
			{{/if}}
		</td>
		<td>
			{{if  currency == '1'}}
			CNY¥
			{{else currency == '2'}}
			USD$
			{{/if}}/
			{{:~liToYuan(money)}}
		</td>
		<td>
			{{if  pattem == '1'}}
			支付宝
			{{else pattem == '2'}}
			微信
			{{else pattem == '3'}}
			银行汇款/转账
			{{else pattem == '4'}}
			PayPal
			{{/if}}
		</td>
		<td>{{:applyPeople}}</td>
		<td>{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', applyTime)}}</td>
		<td>{{:bruisesPeople}}</td>
		<td>{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', bruisesTime)}}</td>
		<td>
			{{if  pattem == '1'}}
			待审核
			{{else pattem == '2'}}
			已确认
			{{else pattem == '3'}}
			锁定
			{{/if}}
		</td>
		<td>
			{{if  state == '3'}}
			<a href="javascript:void(0);" class="adopt" onclick="pager._detailPage('{{:wid}}')">查看</a>
			{{else }}
			<a href="javascript:void(0);" class="adopt" onclick="pager._detailPage('{{:wid}}')">查看</a>
			<a href="javascript:void(0);" class="adopt" onclick="pager._popUp('{{:wid}}')">确认</a>
			<a href="javascript:void(0);" class="adopt" onclick="pager._detailPage('{{:wid}}')">锁定</a>
			{{/if}}
		</td>
	</tr>
</script>
<script type="text/javascript">
	var pager;
	(function () {
		seajs.use('app/jsp/translatorBill/rechargeWithdrawalsList', function (OrderListPager) {
			pager = new OrderListPager({element: document.body});
			pager.render();
		});
	})();
	$(function() {
		$("#allBox").click(function() {
			if(this.checked){
				$("input[name='subBox']").each(function(){this.checked=true;});
			}else{
				$("input[name='subBox']").each(function(){this.checked=false;});
			}
		});
		var $subBox = $("input[name='subBox']");
		$subBox.click(function(){
			$("#allBox").attr("checked",$subBox.length == $("input[name='subBox']:checked").length ? true : false);
		});
	});
</script>
</body>
