<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>译员账单</title>
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
		                    	<!--查询条件-->
								<div class="main-box-body clearfix">
									<div class="bill-list-table">
										<ul>
											<li ><a href="#" id="domestic" class="current" >中文站账单</a></li>
											<li ><a href="#"  id="foreign" >英文站账单</a></li>
										</ul>
									</div>
								</div>
		                    	<div id="selectDiv">
			                    	<ul>
										<li class="col-md-6">
											<p class="word">昵称</p>
											<p><input class="int-text int-medium" id="nickName"  value="" type="text"></p>
										</li>
			                    		<li class="col-md-6">
								           <p class="word">结算方式</p>
						            		<p>
							            		<select class="select select-medium" id="accountType">
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
			                    	</ul>
			                    	<div id="orderTimeDiv">
			                    		<ul>
							                <li class="col-md-6">
							                    <p class="word">账单生成时间</p>
							                    <p><input  readonly class="int-text int-medium " type="text"  id="billTimeBegin" name="beginDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'billTimeEnd\')}'});" />~<input  readonly class="int-text int-medium " type="text"  id="billTimeEnd" name="endDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'billTimeBegin\')}'});"/>
												</p>
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
                        <!--标题-->
							<div class="main-box-body clearfix">
								<div class="order-list-table">
									<ul>
										<li ><a href="#" id="waitHandle" class="current" >待处理</a></li>
										<li ><a href="#"  id="refundId" >已完成</a></li>
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
												<th>编号</th>
												<th>昵称</th>
												<th>用户名</th>
												<th>开始时间</th>
												<th>结束时间</th>
												<th>本期账单金额</th>
												<th>平台费用</th>
												<th>应结金额</th>
												<th>账单周期</th>
												<th>账单生成时间</th>
												<th>结算方式</th>
												<th>结算账户</th>
												<th>结算时间</th>
												<th>结算状态</th>
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
                            <form id="dataForm" method="post">
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
															<!-银行汇款/转账 -->
															<option value="3">银行汇款/转账</option>
															<%--PayPal--%>
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
                            </form>
                            <!-- 弹出框 end-->
                        </div>
                    </div>
                </div>
            </div>
    	</div>
   </div> 
<script id="orderListTemple" type="text/template">
	<tr>
  	  <td>{{:billId}}</td>
      <td>{{:nickname}}</td>
      <td>{{:targetName}}</td>
 	  <td>{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', startAccountTime)}}</td>
      <td>{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', endAccountTime)}}</td>
      <td>
		  {{if  flag == '0'}}
		  ¥
		  {{else flag == '1'}}
		  $
		  {{/if}}
		  {{:~liToYuan(billFee)}}
	  </td>
      <td>
		  {{if  flag == '0'}}
		  ¥
		  {{else flag == '1'}}
		  $
		  {{/if}}
		  {{:~liToYuan(platFee)}}
	  </td>
      <td>
		  {{if  flag == '0'}}
		  ¥
		  {{else flag == '1'}}
		  $
		  {{/if}}
		  {{:~liToYuan(accountAmout)}}
	  </td>
      <td>1个月</td>
      <td>{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', createTime)}}</td>
	  <td>
		  {{if  accountType == '1'}}
		  支付宝
		  {{else accountType == '2'}}
		  微信
		  {{else accountType == '3'}}
		  银行汇款/转账
		  {{else accountType == '4'}}
		  PayPal
		  {{/if}}
	  </td>
	  <td>{{:settleAccount}}</td>
	  <td>{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', actAccountTime)}}</td>
	  <td>
		  {{if  state == '2'}}
		  已结算
		  {{else }}
		  未结算
		  {{/if}}
	  </td>
	  <td>
		  {{if  state == '2'}}
		  <a href="javascript:void(0);" class="adopt" onclick="pager._detailPage('{{:billId}}')">明细</a>
		  {{else }}
		  <a href="javascript:void(0);" onclick="pager._popUp('{{:billId}}','{{:state}}','{{:accountAmout}}')">结算</a>
		  <a href="javascript:void(0);" class="adopt" onclick="pager._detailPage('{{:billId}}')">明细</a>
		  {{/if}}
	  </td>
    </tr>
</script> 
  <script type="text/javascript">
	var pager;
	(function () {
		seajs.use('app/jsp/translatorBill/translatorBillList', function (OrderListPager) {
			pager = new OrderListPager({element: document.body});
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
