<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8"/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
	<title>充值交易</title>
	<%@include file="/inc/inc.jsp" %>
</head>
<body><!--右侧灰色背景-->
<div class="content-wrapper-iframe" >
	<!--框架标签结束-->
	<div class="row"><!--外围框架-->
		<div class="col-lg-12"><!--删格化-->
			<div class="row"><!--内侧框架-->
				<div class="col-lg-12"><!--删格化-->
					<div class="main-box clearfix"><!--白色背景-->
						<div class="form-label">
							<!--查询条件-->
							<div class="main-box-body clearfix">
								<div class="bill-list-table">
									<ul>
										<li ><a href="#" id="detail" class="current" >收支明细</a></li>
										<li ><a href="#"  id="income" >充值记录</a></li>
										<li ><a href="#"  id="out" >支出记录</a></li>
									</ul>
								</div>
							</div>
							<div id="selectDiv">
								<ul>
									<li class="col-md-6">
										<p class="word">交易号</p>
										<p><input class="int-text int-medium" id="serialCode"  value="" type="text"></p>
									</li>
									<li class="col-md-6">
										<p class="word">企业名称</p>
										<p><input class="int-text int-medium" id="nickName"  value="" type="text"></p>
									</li>
									<li class="col-md-6">
										<p class="word">结算方式</p>
										<p>
											<select class="select select-medium" id="channel">
												<option value="" selected="selected">全部</option>
												<!-- 银联在线 -->
												<option value="银联在线">银联在线</option>
												<!--支付宝 -->
												<option value="支付宝">支付宝</option>
												<!-微信 -->
												<option value="微信">微信</option>
												<%--线下充值--%>
												<option value="线下充值">线下充值</option>
											</select>
										</p>
									</li>
									<li class="col-md-6">
										<p class="word">状态</p>
										<p>
											<select class="select select-medium" id="state">
												<option value="" selected="selected">全部</option>
												<!-- 交易成功 -->
												<option value="1">交易成功</option>
												<!--交易失败 -->
												<option value="2">交易失败</option>
											</select>
										</p>
									</li>
									<li class="col-md-6">
										<p class="word">币种单位</p>
										<p>
											<select class="select select-medium" id="currencyUnit">
												<option value="" selected="selected">全部</option>
												<!-- 交易成功 -->
												<option value="1">CNY ¥</option>
												<!--交易失败 -->
												<option value="2">USD $</option>
											</select>
										</p>
									</li>
									<li class="col-md-6">
										<p class="word">金额区间</p>
										<p><input class="int-text int-medium" type="text"  id="amountBegin" name="amountBegin" />~<input class="int-text int-medium" type="text"  id="amountEnd" name="amountEnd"/>
										</p>
									</li>
								</ul>
								<div id="orderTimeDiv">
									<ul>
										<li class="col-md-6">
											<p class="word">日期</p>
											<p><input  readonly class="int-text int-medium " type="text"  id="timeBegin" name="beginDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'timeEnd\')}'});" />~<input  readonly class="int-text int-medium " type="text"  id="timeEnd" name="endDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'timeBegin\')}'});"/>
											</p>
										</li>
									</ul>
								</div>
							</div>
							<ul>
								<li class="width-xlag">
									<p class="word">&nbsp;&nbsp;&nbsp;</p>
									<p class="word"><input type="button" class="biu-btn  btn-primary btn-blue btn-medium ml-5"
														   id="search" value="查  询"></p>
									<p><input type="button" class="biu-btn  btn-primary btn-blue btn-medium ml-5"
											  id="export" value="导  出"></p>
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
						<p id="show">
						</p>
						<div class="main-box-body clearfix">
							<!--table表格-->
							<div class="table-responsive clearfix">
								<!--table表格-->
								<div class="table-responsive clearfix mt-10">
									<table class="table table-hover table-border table-bordered ">
										<thead>
										<tr>
											<th>日期</th>
											<th>交易号</th>
											<th>名称</th>
											<th>昵称</th>
											<th>金额</th>
											<th>账户余额</th>
											<th>支付方式</th>
											<th>状态</th>
											<th>备注</th>
										</tr>
										</thead>
										<tbody id="businessListData"></tbody>
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
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script id="businessListTemple" type="text/template">
	<tr>
		<td>{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', payTime)}}</td>
		<td>{{:serialCode}}</td>
		<td>{{:accountName}}</td>
		<td>{{:accountName}}</td>
		<td>
			{{if  currencyUnit == '1'}}
			¥
			{{else currencyUnit == '2'}}
			$
			{{/if}}
			{{if incomeFlag == '1'}}
			+{{:~liToYuan(totalAmount)}}
			{{/if}}
			{{if incomeFlag == '0'}}
			{{:~liToYuan(totalAmount)}}
			{{/if}}
		</td>
		<td>
			{{if  currencyUnit == '1'}}
			¥
			{{else currencyUnit == '2'}}
			$
			{{/if}}
			{{:~liToYuan(totalAmount+balancePre)}}
		</td>
		<td>
			{{:channel}}
		</td>
		<td>
			{{if  payStatus == '1'}}
			交易成功
			{{else }}
			交易失败
			{{/if}}
		</td>
		<td>{{:remark}}</td>
	</tr>
</script>
<script type="text/javascript">
	var pager;
	(function () {
		seajs.use('app/jsp/translatorBill/businessList', function (BusinessListPager) {
			pager = new BusinessListPager({element: document.body});
			pager.render();
		});
	})();
	$(".bill-list-table ul li a").click(function () {
		$(".bill-list-table ul li a").each(function () {
			$(this).removeClass("current");
		});
		$(this).addClass("current");
	});
</script>
</body>
