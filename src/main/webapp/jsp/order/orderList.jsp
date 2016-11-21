<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>订单列表</title>
<%@include file="/inc/inc.jsp" %>
<input type="hidden" id="orderIdUpdate"/>
<input type="hidden" id="payStyleUpdate"/>
<input type="hidden" id="currencyUnitUpdate"/>
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
		                    	<ul>
		                    		<li class="col-md-6">
							            <p class="word">订单编号</p>
							            <p><input class="int-text int-medium"  id="orderId" type="text"></p>
						            </li>
		                    		<li class="col-md-6">
							            <p class="word">昵称</p>
							            <p><input class="int-text int-medium" id="nickName" type="text"></p>
						            </li>
		                    	</ul>
		                    	<ul>
							         <li class="col-md-6">
							            <p class="word">订单类型</p>
							            <p>
						            		<select class="select select-medium" id="orderType">
						            			<option value="">全部</option>
						            		</select>
					            		</p>
					            		<p>
					            			高级搜索<a href="javascript:void(0);"><i class="fa fa-caret-down" id="showQuery"></i></a>
							         	</p>
							         </li>
		                    	</ul>
		                    	<div id="selectDiv" style="display:none;">
			                    	<ul>
			                    		<li class="col-md-6">
								           <p class="word">支付方式</p>
						            		<p>
							            		<select class="select select-medium" id="payStyle">
							            			<option value="">全部</option>
							            		</select>
						            		</p>
							            </li>
			                    		<li class="col-md-6">
								            <p class="word">订单状态</p>
								            <p>
							            		<select class="select select-medium" id="searchOrderState">
							            			<option value="">全部</option>
							            		</select>
						            		</p>
							            </li>
			                    	</ul>
			                    	<div id="orderTimeDiv">
			                    		<ul>
							                <li class="col-md-6">
							                    <p class="word">下单开始时间</p>
							                    <p><input name="control_date" readonly class="int-text int-medium " type="text"  id="orderTimeBegin" name="orderTimeBegin"/>
							                   <span class="time"> <i class="fa  fa-calendar" ></i></span>
							                    </p>
							                </li>
							                <li class="col-md-6">
							                    <p class="word">下单结束时间</p>
							                    <p><input name="control_date" readonly class="int-text int-medium " type="text"  id="orderTimeEnd" name="orderTimeEnd"/>
							                     <span class="time"><i class="fa  fa-calendar" ></i></span>
							                    </p>
							                </li> 
						            	</ul>
			                    	</div>
						           <div id="payTimeDiv">
							           	<ul>
							                <li class="col-md-6">
							                    <p class="word">支付开始时间</p>
							                    <p><input name="control_date" readonly class="int-text int-medium " type="text"  id="payTimeBegin" name="payTimeBegin"/>
							                   <span class="time"> <i class="fa  fa-calendar" ></i></span>
							                    </p>
							                </li>
							                <li class="col-md-6">
							                    <p class="word">支付结束时间</p>
							                    <p><input name="control_date" readonly class="int-text int-medium " type="text"  id="payTimeEnd" name="payTimeEnd"/>
							                     <span class="time"><i class="fa  fa-calendar" ></i></span>
							                    </p>
							                </li> 
							            </ul> 
						           </div>
						            <ul>
			                    		<li class="col-md-6">
								            <p class="word">订单来源</p>
								            <p>
							            		<select class="select select-medium" id="orderSource">
							            			<option value="">全部</option>
							            		</select>
						            		</p>
							            </li>
							            <li class="col-md-6">
							            	<p class="word">语种方向</p>
						            		<p>
							            		<select class="select select-medium" id="langugePaire">
							            			<option value="">全部</option>
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
                            <header class="main-box-header clearfix">
                            <h5 class="pull-left">订单列表</h5>
                            </header>
                        <!--标题结束-->   
                            <div class="main-box-body clearfix">
                            	<!--table表格-->
                                <div class="table-responsive clearfix">
                                   	<!--table表格-->
                          		<div class="table-responsive clearfix mt-10">
                                    <table class="table table-hover table-border table-bordered ">
                                        <thead>
                                            <tr>
                                            	<th>订单来源</th>
                                                <th>订单类型</th>
                                                <th>订单编号</th>
                                                <th>下单时间</th>
                                                <th>昵称</th>
                                                <th>语种方向</th>
                                                <th>订单金额</th>
                                                <th>实付金额</th>
                                                <th>支付方式</th>
                                                <th>支付时间</th>
                                                <th>订单状态</th>
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
											<p>修改支付状态</p>
											<p class="img"><i class="fa fa-times"></i></p>
										</div>
										<div class="form-label mt-10">
							           		<ul>
								                <li>
								                    <p class="word">支付方式:</p>
								                    <p><input type="text" id="payStyle" class="int-text int-small"value="银行汇款/转账" readonly ></p>
								                </li>
								                 <li>
								                    <p class="word"><span>*</span>金额:</p>
								                    <p><input type="text" class="int-text int-small" id="updateMoney" name="updateMoney"></p>
								                    <p id="errorMessage"></p>
								                </li>
								                <li>
								                    <p class="word"><span>*</span>备注:</p>
								                	<p><textarea id="remark" name="remark" rows="4" cols="25" class="int-text" maxlength="100"></textarea></p>
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
									<div class="eject-mask" id="eject-mask"></div>	
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
  	  <td>{{:chlIdPage}}</td>
      <td>{{:orderTypePage}}</td>
      <td>{{:orderId}}</td>
 	  <td>{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', orderTime)}}</td>
      <td>{{:userName}}</td>
 	  <td>
      	 <table class="table close-border" width="100%">
      		<tbody>
				{{if ordProdExtendList!=null}}
					{{for ordProdExtendList}}
      					<tr>
      						<td class="new-td" style="">{{:langungePairChName}}</td>	
      					</tr>
					{{/for}}
				{{/if}}
      		</tbody>	
      	</table>
      </td>
      <td>{{:totalFeePage}}</td>
      <td>{{:totalFeePage}}</td>
      <td>{{:payStylePage}}</td>
      <td>{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', payTime)}}</td>
     <td>{{:statePage}}</td>
     <td>
		<a href="javascript:void(0)">查看</a>
 		{{if state==11}}
			<a href="javascript:void(0)" onclick="pager._popUp('{{:orderId}}','{{:payStylePage}}','{{:currencyUnit}}','{{:payStyle}}')">修改支付状态</a>
		{{/if}}	
	</td>
  </tr>                                                                       
</script> 
  <script type="text/javascript">
  <%-- 展示日历 --%>
	$('#orderTimeDiv').delegate('.fa-calendar','click',function(){
		var calInput = $(this).parent().prev();
		var timeId = calInput.attr('id');
		console.log("click calendar "+timeId);
		WdatePicker({el:timeId,readOnly:true});
	});
	$('#payTimeDiv').delegate('.fa-calendar','click',function(){
		var calInput = $(this).parent().prev();
		var timeId = calInput.attr('id');
		console.log("click calendar "+timeId);
		WdatePicker({el:timeId,readOnly:true});
	});
	var pager;
	(function () {
		seajs.use('app/jsp/order/orderList', function (OrderListPager) {
			pager = new OrderListPager({element: document.body});
			pager.render();
		});
	})();
 </script>  
</body>

</html>