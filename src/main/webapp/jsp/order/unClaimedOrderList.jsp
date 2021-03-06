<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>待领取订单列表</title>
<%@include file="/inc/inc.jsp" %>
<input type="hidden" id="selectOrderId">
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
					            		<p class="sos" id="showQuery">
					            			<a href="javascript:void(0);">高级搜索<i class="fa fa-caret-down"></i></a>
							         	</p>
							         </li>
		                    	</ul>
		                    	<div id="selectDiv" style="display:none;">
			                    	<ul>
			                    		<li class="col-md-6">
								           <p class="word">订单级别</p>
						            		<p>
							            		<select class="select select-medium" id="orderLevel">
							            			<option value="">全部</option>
							            		</select>
						            		</p>
							            </li>
							            <li class="col-md-6">
								            <p class="word">订单来源</p>
								            <p>
							            		<select class="select select-medium" id="orderSource">
							            			<option value="">全部</option>
							            		</select>
						            		</p>
							            </li>
			                    	</ul>
			                    	<div id="orderTimeDiv">
			                    		<ul>
							                <li class="col-md-6">
							                    <p class="word">下单开始时间</p>
							                    <p><input name="control_date" readonly class="int-text int-medium " type="text"  id="orderTimeBegin" name="orderTimeBegin" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'orderTimeEnd\')}'});"/>
							                    </p>
							                </li>
							                <li class="col-md-6">
							                    <p class="word">下单结束时间</p>
							                    <p><input readonly class="int-text int-medium " type="text"  id="orderTimeEnd" name="orderTimeEnd" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'orderTimeBegin\')}'});"/>
							                    </p>
							                </li> 
						            	</ul>
			                    	</div>
						           <div id="payTimeDiv">
							           	<ul>
							                <li class="col-md-6">
							                    <p class="word">支付开始时间</p>
							                    <p><input name="control_date" readonly class="int-text int-medium " type="text"  id="payTimeBegin" name="payTimeBegin" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'payTimeEnd\')}'});"/>
							                   <span class="time"> <i class="fa  fa-calendar" ></i></span>
							                    </p>
							                </li>
							                <li class="col-md-6">
							                    <p class="word">支付结束时间</p>
							                    <p><input  readonly class="int-text int-medium " type="text"  id="payTimeEnd" name="payTimeEnd" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'payTimeBegin\')}'});"/>
							                    </p>
							                </li> 
							            </ul> 
						           </div>
						            <ul>
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
                                                <th>翻译级别</th>
                                                <th>订单金额</th>
                                                <th>实付金额</th>
                                                <th>订单级别</th>
                                                <th>交稿剩余时间</th>
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
                            <!-- 选择译员开始 -->
								<div class="eject-big">
										<div class="eject-large" id="user">		
											<div class="eject-medium-title">
												<p>选择用户</p>
												<p class="img"><i class="fa fa-times" id="closeImage"></i></p>
											</div>
											<div class="eject-large-list">
										    <div class="search-firm">
										    		<p>搜索用户</p>
										    		<p><input type="input" class="int-text int-xlarge" /></p>
										    		<p><input type="button" value="查询" class="biu-btn btn-primary btn-mini blue-btn" id="userSearch"></p>
										    </div>
										    <div class="table-responsive clearfix">
				                                <!--table表格-->
				                          		<div class="table-responsive clearfix mt-10">
				                                    <table class="table table-hover table-border table-bordered ">
				                                        <thead>
				                                            <tr>
				                                            	<th>编号</th>
				                                            	<th>昵称</th>
				                                                <th>手机</th>
				                                                <th>级别</th>
				                                                <th>姓名</th>
				                                            </tr>
				                                        </thead>
				                                         <tbody id="userListData"></tbody>
				                                    </table>
				                                    <div id="showMessage"></div>
				                                </div>
				                           		<!--/table表格结束-->
				                                </div>
											</div>	
											<!--分页-->
									         <div>
								 				 <nav style="text-align: center">
													<ul id="paginationUser"></ul>
												</nav>
											  </div>
											   <!--分页结束-->
											<!--按钮-->
											<div class="row mt-15"><!--删格化-->
									               <p class="center pr-30 mt-30">
									               	   <input type="button" class="biu-btn  btn-primary  btn-auto  ml-5 " value="提  交">
									                   <input id="close" type="button" class="biu-btn  btn-primary  btn-auto  ml-5 " value="取  消">
									                </p>
									        </div>
										</div>	
										<div  class="mask" id="eject-mask"></div>	
									</div>
								<!-- 选择译员结束-->
                        </div>
                    </div>
                </div>
            </div>
    	</div>
   </div> 
   <!-- 用户信息 start -->
  <script id="userListTemple" type="text/template">
	<tr>
  	  <td><input type="radio" name="userno" value="{{:interperId}}">{{:index}}</td>
      <td>{{:translateTypePage}}</td>
      <td><a href="javascript:void(0);" onclick="pager._detailPage('{{:orderId}}')">{{:orderId}}</a></td>
 	  <td>{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', orderTime)}}</td>
      <td>{{:userName}}</td>
  </tr>                                                                       
</script> 
<!-- 用户信息 end -->
<script id="orderListTemple" type="text/template">
	<!--{{if levelSize>=extendSize && levelSize!=0 && extendSize!=0}} {{/if}}-->
{{if  levelSize!=0 && extendSize!=0}}
	{{for ordTransLevelList ~chlIdPage=chlIdPage ~translateTypePage=translateTypePage  ~orderId=orderId
		~orderTime=orderTime ~totalFeePage=totalFeePage ~lockTime=lockTime ~finishTime=finishTime
		 ~remainingTime=remainingTime  ~statePage=statePage  ~levelSize=levelSize ~userName=userName
		~ordProdExtendList=ordProdExtendList ~extendSize=extendSize ~orderLevelPage=orderLevelPage
		~remainingTimePage=remainingTimePage
	}}
		<tr>
			{{if #index ==0}}
				<td rowspan="{{:~levelSize}}">{{:~chlIdPage}}</td>
				<td rowspan="{{:~levelSize}}">{{:~translateTypePage}}</td>
				<td rowspan="{{:~levelSize}}"><a href="javascript:void(0);" onclick="pager._detailPage('{{:~orderId}}')">{{:~orderId}}</a></td>
				<td rowspan="{{:~levelSize}}">{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', ~orderTime)}}</td>
				<td rowspan="{{:~levelSize}}">{{:~userName}}</td>
				<td rowspan="{{:~levelSize}}">
					 <table width="100%" height="100%">
							{{if ~ordProdExtendList!=null}}
								{{for ~ordProdExtendList}}
									{{if #index <1}}
      								<tr>
      									<td class="new-td bot-none">{{:langungePairChName}}</td>	
      								</tr>
									{{/if}}
								{{/for}}
								{{if ~ordProdExtendList.length>1}}
									<tr>
      									<td class="new-td bot-none">...</td>	
      								</tr>
								{{/if}}
							{{/if}}
      				</table>
				</td>	
			{{/if}}
			<td>{{:translateLevelPage}}</td>
			{{if #index ==0 }}
				<td rowspan="{{:~levelSize}}">{{:~totalFeePage}}</td>
				<td rowspan="{{:~levelSize}}">{{:~totalFeePage}}</td>
				<td rowspan="{{:~levelSize}}">{{:~orderLevelPage}}</td>
				<td rowspan="{{:~levelSize}}">{{:~remainingTimePage}}</td>
				<td rowspan="{{:~levelSize}}">{{:~statePage}}</td>
				<td rowspan="{{:~levelSize}}">
					<a  href="javascript:void(0);" onclick="pager._detailPage('{{:~orderId}}')">查看</a>
					<a href="javascript:void(0)" class="adopt" onclick="pager._rejectRefundOrder('{{:~orderId}}')">退款</a>
					<a  href="javascript:void(0)" onclick="pager._popUp('{{:orderId}}')">分配</a>
				</td>
			{{/if}}	
		</tr>		
	{{/for}}
{{/if}}
<!--{{if levelSize<extendSize}}
	{{for ordProdExtendList ~chlIdPage=chlIdPage ~translateTypePage=translateTypePage  ~orderId=orderId
		~orderTime=orderTime ~totalFeePage=totalFeePage ~lockTime=lockTime ~finishTime=finishTime
		 ~remainingTime=remainingTime  ~statePage=statePage  ~levelSize=levelSize ~userName=userName
		~ordTransLevelList=ordTransLevelList ~extendSize=extendSize ~orderLevelPage=orderLevelPage
		~remainingTimePage=remainingTimePage
	}}
		<tr>
			{{if #index ==0}}
				<td rowspan="{{:~extendSize}}">{{:~chlIdPage}}</td>
				<td rowspan="{{:~extendSize}}">{{:~translateTypePage}}</td>
				<td rowspan="{{:~extendSize}}"><a href="javascript:void(0);" onclick="pager._detailPage('{{:~orderId}}')">{{:~orderId}}</a></td>
				<td rowspan="{{:~extendSize}}">{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', ~orderTime)}}</td>
				<td rowspan="{{:~extendSize}}">{{:~userName}}</td>
			{{/if}}
			<td>{{:langungePairChName}}</td>
			{{if #index ==0 }}
				<td rowspan="{{:~extendSize}}">
					 <table width="100%" height="100%">
							{{if ~ordTransLevelList!=null}}
								{{for ~ordTransLevelList}}
      								<tr>
      									<td class="new-td bot-none">{{:translateLevelPage}}</td>	
      								</tr>
								{{/for}}
							{{/if}}
      				</table>
				</td>	
				<td rowspan="{{:~extendSize}}">{{:~totalFeePage}}</td>
				<td rowspan="{{:~extendSize}}">{{:~totalFeePage}}</td>
				<td rowspan="{{:~extendSize}}">{{:~orderLevelPage}}</td>
				<td rowspan="{{:~extendSize}}">{{:~remainingTimePage}}</td>
				<td rowspan="{{:~extendSize}}">{{:~statePage}}</td>
				<td rowspan="{{:~extendSize}}">
					<a  href="javascript:void(0);" onclick="pager._detailPage('{{:~orderId}}')">查看</a>
					<a href="javascript:void(0)" class="adopt" onclick="pager._rejectRefundOrder('{{:~orderId}}')">退款</a>
				</td>

			{{/if}}	
		</tr>		
	{{/for}}
{{/if}}-->
  {{if levelSize===0 && extendSize===0}}
	<tr>
	<td>{{:chlIdPage}}</td>
	<td>{{:translateTypePage}}</td>
	<td><a href="javascript:void(0);" onclick="pager._detailPage('{{:orderId}}')">{{:orderId}}</a></td>
	<td>{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', orderTime)}}</td>
	<td>{{:userName}}</td>
	<td></td>
	<td></td>
	<td>{{:totalFeePage}}</td>
	<td>{{:totalFeePage}}</td>
	<td>{{:orderLevelPage}}</td>
	<td>{{:remainingTimePage}}</td>
	<td>{{:statePage}}</td>
	<td>
		<a  href="javascript:void(0)" onclick="pager._detailPage('{{:orderId}}')">查看</a>
		<a href="javascript:void(0)" class="adopt" onclick="pager._rejectRefundOrder('{{:orderId}}')">退款</a>
		<a  href="javascript:void(0)" onclick="pager._popUp('{{:orderId}}')">分配</a>
	</td>	
	</tr>			
{{/if}}                                                                  
</script> 
  <script type="text/javascript">
	var pager;
	(function () {
		seajs.use('app/jsp/order/unClaimedOrderList', function (UnClaimedOrderListPager) {
			pager = new UnClaimedOrderListPager({element: document.body});
			pager.render();
		});
	})();
 </script>  
</body>

</html>