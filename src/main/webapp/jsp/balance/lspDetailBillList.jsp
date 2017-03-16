<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>译员账单</title>
<%@include file="/inc/inc.jsp" %>
<input id="billID" name="billID" type="hidden" value="${billID}"/>
</head>
<body>
   <div class="content-wrapper-iframe" ><!--右侧灰色背景-->
        <!--框架标签结束-->
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
                                    <table class="table table-hover table-border table-bordered ">
                                        <thead>
                                            <tr>
												<th>序号</th>
												<th>订单编号</th>
												<th>下单昵称</th>
												<th>下单时间</th>
												<th>语种方向</th>
												<th>订单金额</th>
												<th>平台费用</th>
												<th>译员支出</th>
												<th>LSP结余</th>
												<th>译员昵称</th>
												<th>订单状态</th>
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
                        </div>
                    </div>
                </div>
            </div>
    	</div>
   </div> 
<script id="billDetailListTemple" type="text/template">
	<tr>
  	  <td>{{:id}}</td>
      <td>{{:orderId}}</td>
      <td>{{:nickname}}</td>
 	  <td>{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', orderTime)}}</td>
      <td>{{:langungePairName}}</td>
      <td>
		  {{if  currencyUnit == '1'}}
		  ¥
		  {{else currencyUnit == '2'}}
		  $
		  {{/if}}
		  {{:~liToYuan(totalFee)}}
	  </td>
      <td>
		  {{if  currencyUnit == '1'}}
		  ¥
		  {{else currencyUnit == '2'}}
		  $
		  {{/if}}
		  {{:~liToYuan(platFee)}}
	  </td>
      <td>
		  {{if  currencyUnit == '1'}}
		  ¥
		  {{else currencyUnit == '2'}}
		  $
		  {{/if}}
		  {{:~liToYuan(translatorFee)}}
	  </td>
	 <%-- <td>
		  {{if  accountType == '1'}}
		  支付宝
		  {{else accountType == '2'}}
		  微信
		  {{else accountType == '3'}}
		  银行汇款/转账
		  {{else accountType == '4'}}
		  PayPal
		  {{/if}}
	  </td>--%>
		<td>
			{{if  currencyUnit == '1'}}
			¥
			{{else currencyUnit == '2'}}
			$
			{{/if}}
			{{:~liToYuan(lspFee)}}
		</td>
	  <td>{{:nickname2}}</td>
	  <%--<td>{{:lspName}}</td>--%>
	  <td>
		  {{if  state == '10'}}
		  提交
		  {{else state == '11'}}
		  待支付
		  {{else state == '12'}}
		  已支付
		  {{else state == '13'}}
		  待报价
		  {{else state == '20'}}
		  待领取
		  {{else state == '21'}}
		  已领取
		  {{else state == '211'}}
		  已分配
		  {{else state == '23'}}
		  翻译中
		  {{else state == '24'}}
		  已提交
		  {{else state == '25'}}
		  修改中
		  {{else state == '40'}}
		  待审核
		  {{else state == '41'}}
		  已审核
		  {{else state == '42'}}
		  审核失败
		  {{else state == '50'}}
		  待确认
		  {{else state == '51'}}
		  已确认
		  {{else state == '52'}}
		  待评价
		  {{else state == '53'}}
		  已评价
		  {{else state == '90'}}
		  完成
		  {{else state == '91'}}
		  关闭
		  {{else state == '92'}}
		  已退款
		  {{/if}}
	  </td>
    </tr>
</script> 
  <script type="text/javascript">
	var pager;
	(function () {
		seajs.use('app/jsp/translatorBill/lspDetailBill', function (OrderListPager) {
			pager = new OrderListPager({element: document.body});
			pager.render();
		});
	})();
 </script>  
</body>

</html>