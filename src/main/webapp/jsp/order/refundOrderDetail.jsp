<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>退款订单详情</title>
<%@include file="/inc/inc.jsp"%>
<style type="text/css">
    .btn-ext{
       margin-bottom: 10px;
       margin-left: 60%;
    }
</style>
</head>
<body>
    <input id="mod" type="hidden" value="${model}"/>
    <form id="orderForm">
    <input id="orderId" name="orderId" type="hidden" value="${orderId}"/>
     <input id="isAll" name="isAll" type="hidden" value="${isAll}"/>
	 <div class="row">
	 
		<!--内侧框架-->
		<div class="col-lg-12">
			<!--删格化-->
			<div class="main-box clearfix">
				<!--白色背景-->
				<div class="main-box-body clearfix">
					<div class="order-list-table">
						<ul>
							<li><a href="#" class="current">订单信息</a></li>
							<li><a href="#">订单日志</a></li>
						</ul>
					</div>
				</div>
				<!--选项卡1-->
				<div id="date1">
					
				</div>
				<!--选项卡2-->
				<div id="date2" style="display: none;">
				   <!--白色背景-->
					<div class="main-box-body clearfix">
		              <div class="table-responsive clearfix">
						<table class="table table-hover table-border table-bordered">
									<thead>
										<tr>
											<th>操作者</th>
											<th>操作名称</th>
											<th>操作时间</th>
											<th>订单状态</th>
											<th>备注</th>
										</tr>
									</thead>
									<tbody id="orderStateChgTable">
									  
									</tbody>
						 </table>
					  </div>	
				  </div>	
						    
				</div>
			</div>
			
		</div>
	</div>	
	
   </form>
</body>
<script id="orderInfoTempl" type="text/x-jsrender">
	<!--白色背景-->
    <input id="state" type="hidden" value="{{:state}}"/>
	<div class="main-box-body clearfix">
                  <div class="form-label">
								<ul>
									<li class="col-md-6">
										<p class="word">订单号：</p>
										<p>{{:orderId}}</p>
									</li>
									<li class="col-md-6">
										<p class="word">订单状态：</p>
										<p>{{:~getStateName(state)}}</p>
									</li>
									<li class="col-md-6">
										<p class="word">订单来源：</p>
										<p>{{:~getChlIdName(chlId)}}</p>
									</li>
									<li class="col-md-6">
										<p class="word">订单类型：</p>
										<p>{{:~getTranslateTypeName(translateType)}}</p>
									</li>
									<li class="col-md-6">
										<p class="word">用户昵称：</p>
										<p>{{:usernick}}</p>
									</li>
                                    <li class="col-md-6">
										<p class="word">时区：</p>
										<p>{{:timeZone}}</p>
									</li>
									<li class="col-md-6">
										<p class="word">下单时间：</p>
										<p>{{:~timesToFmatter(orderTime)}}</p>
									</li>
								
                                    <li class="col-md-6">
										<p class="word">联系人姓名：</p>
										<p> 
                                           {{:contacts.contactName}}
                                        </p>
									</li>
									<li class="col-md-6">
										<p class="word">联系人手机：</p>
										<p>
                                              {{:contacts.contactTel}}
                                        </p>
									</li>
									<li class="col-md-6">
										<p class="word">联系人邮箱：</p>
										<p>
                                            {{:contacts.contactEmail}}
										</p>
									</li>
									<li class="col-md-6">
										<p class="word">支付方式：</p>
										<p>{{:~getPayStyleName(orderFee.payStyle)}}</p>
									</li>
									<li class="col-md-6">
										<p class="word">支付时间：</p>
										<p>{{:~timesToFmatter(orderFee.payTime)}}</p>
									</li>
									<li class="col-md-6">
									  <p class="word">订单金额：</p>
									  <p>
										  {{:~liToYuan(orderFee.totalFee)}}
										  {{:~getMoneyUnit(orderFee.currencyUnit)}}
									  </p>
								    </li>
									<li class="col-md-6">
									  <p class="word">实付金额：</p>
									  <p>
										  {{:~liToYuan(orderFee.paidFee)}}
										  {{:~getMoneyUnit(orderFee.currencyUnit)}}
									  </p>
								    </li>
									<li class="col-md-6">
									  <p class="word">优惠金额：</p>
									  <p>
										  {{:~liToYuan(orderFee.discountFee)}}
										  {{:~getMoneyUnit(orderFee.currencyUnit)}}
									  </p>
								    </li>
									<li class="col-md-12">
										<p class="word">语言对：</p>
										<p>
										   {{for prodExtends}}
										      {{:langungePairName}}
										   {{/for}}
										</p>
									</li>
									<li class="col-md-6">
										<p class="word">客户留言：</p>
										<p class="col-md-8">
											{{:remark}}
										</p>
									</li>			
							 </ul>
							</div>
				</script>				
            <div class="bc-ang mb-12">
				<input id="cancel" type="button" class="biu-btn  btn-yellow btn-medium ml-10" value="返回" />      
			</div>
    </div>                       
</script>
<script id="orderStateChgTempl" type="text/x-jsrender">
  {{for orderStateChgs  ~subFlag=subFlag}}
   <tr>
     <td>
		{{if operId=='1000011' }}
			系统操作
		{{else  operName==null || operName=="" && operId!='1000011'}}
			{{:operId}}
		{{else}}
			{{:operName}}
		{{/if}}
	</td>
     <td>{{:~getOrderOperName(~subFlag,orgState,newState)}} </td>
     <td>{{:~timesToFmatter(stateChgTime)}}</td>
     <td>{{:~getStateName(newState)}}</td>
     <td style="text-align: left;">&nbsp;&nbsp;{{:chgDesc}} </td>
   </tr>
  {{/for}}
</script>
<script type="text/javascript">
    var pager;
    (function () {
        seajs.use('app/jsp/order/refundOrderDetail', function (refudnOrderDetailsPager) {
            pager = new refudnOrderDetailsPager({element: document.body});
            pager.render();

        });
    })();
</script>
</html>