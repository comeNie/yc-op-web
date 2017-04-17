<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>待审核</title>
<%@include file="/inc/inc.jsp" %>
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
		                    	<div id="selectDiv" style="display: none;">
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
							               <p class="word">译员昵称</p>
							               <p><input class="int-text int-medium" id="interperName"  type="text"></p>
						                </li>
		                    		</ul>
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
			                    	<div id="orderTimeDiv">
			                    		<ul>
							                <li class="col-md-6">
							                    <p class="word">下单开始时间</p>
							                    <p><input  readonly class="int-text int-medium " type="text"  id="orderTimeBegin" name="orderTimeBegin" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'orderTimeEnd\')}'});"/>
							                    </p>
							                </li>
							                <li class="col-md-6">
							                    <p class="word">下单结束时间</p>
							                    <p><input  readonly class="int-text int-medium " type="text"  id="orderTimeEnd" name="orderTimeEnd" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'orderTimeBegin\')}'});"/>
							                    </p>
							                </li> 
						            	</ul>
			                    	</div>
						           <div id="lockTimeDiv">
							           	<ul>
							                <li class="col-md-6">
							                    <p class="word">领取开始时间</p>
							                    <p><input  readonly class="int-text int-medium " type="text"  id="lockTimeBegin" name="lockTimeBegin" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'lockTimeEnd\')}'});"/>
							                    </p>
							                </li>
							                <li class="col-md-6">
							                    <p class="word">领取结束时间</p>
							                    <p><input readonly class="int-text int-medium " type="text"  id="lockTimeEnd" name="lockTimeEnd" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'lockTimeBegin\')}'});"/>
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
                            <header class="main-box-header clearfix">
                              <h5 class="pull-left">订单列表</h5>
                              <p class="right pr-30">
                         	     <input type="button" id="batchAdopt" class="biu-btn  btn-primary btn-blue btn-auto  ml-5" value="批量通过">
                              </p>
                            </header>
                        <!--标题结束-->   
                            <div class="main-box-body clearfix">
                            	<!--table表格-->
                                <div class="table-responsive clearfix">
                                   	<!--table表格-->
                          		<div class="table-responsive clearfix mt-10">
                                    <table id="data-table" class="table table-hover table-border table-bordered ">
                                        <thead>
                                            <tr>
                                                <th>
                                                  <span><input type="checkbox" class="checkbox-medium all"/></span>
					         				      <span>全选</span>
                                                </th>
                                            	<th>订单来源</th>
                                                <th>订单类型</th>
                                                <th>订单编号</th>
                                                <th>下单时间</th>
                                                <th>昵称</th>
                                                <th>语种方向</th>
                                                <th>订单级别</th>
                                                <th>订单金额</th>
                                                <th>实付金额</th>
                                                <th>译员昵称</th>
                                                <th>译员领取时间</th>
                                                <th>译员提交时间</th>
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
                        </div>
                    </div>
                </div>
            </div>
    	</div>
   </div> 
<script id="orderListTemple" type="text/template">
	<tr>
      <td><span><input type="checkbox" value="{{:orderId}}" class="checkbox-medium single"/></span></td>
  	  <td>{{:chlIdPage}}</td>
      <td>{{:translateTypePage}}</td>
	  <td title="{{:orderId}}"><a href="../order/orderdetails?mod=edit&orderId={{:orderId}}">{{:~subStr(10,orderId)}}</a></td>	
      <td>{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', orderTime)}}</td>
      <td>{{:userName}}</td>
 	  <td>
      	 <table class="table close-border" width="100%">
      		<tbody>
				{{if ordProdExtendList!=null}}
					{{for ordProdExtendList}}
						{{if #index <1}}
      					<tr>
      						<td class="new-td bot-none">{{:langungePairChName}}</td>	
      					</tr>
						{{/if}}
					{{/for}}
					{{if ordProdExtendList.length>1}}
						<tr>
      						<td class="new-td bot-none">...</td>	
      					</tr>
					{{/if}}
				{{/if}}
      		</tbody>	
      	</table>
      </td>
 	  <td>{{:orderLevelPage}}</td>
      <td>{{:totalFeePage}}</td>
      <td>{{:totalFeePage}}</td>
      <td>{{:interperName}}</td>
      <td>{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', lockTime)}}</td>
      <td>{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', stateChgTime)}}</td>
      <td>{{:remainingTimePage}}</td>
     <td>{{:statePage}}</td>
     <td>
		<a href="../order/orderdetails?mod=edit&orderId={{:orderId}}">查看</a>
		<a href="javascript:void(0);" class="adopt">通过</a>
		<a href="javascript:void(0);" class="reject">驳回</a>
		<a href="javascript:void(0)" class="adopt1" onclick="pager._rejectRefundOrder('{{:orderId}}')">退款</a>
	</td>
  </tr>                                                                       
</script> 
  <script type="text/javascript">
	var pager;
	(function () {
		seajs.use('app/jsp/order/reviewOrderList', function (ReviewOrdListPager) {
			pager = new ReviewOrdListPager({element: document.body});
			pager.render();
		});
	})();
 </script>  
</body>

</html>