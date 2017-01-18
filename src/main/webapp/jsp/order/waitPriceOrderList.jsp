<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>待报价订单列表</title>
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
		                    	<div id="selectDiv" style="display:none;">
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
  	  <td>{{:chlIdPage}}</td>
      <td>{{:translateTypePage}}</td>
      <td><a href="javascript:void(0);" onclick="pager._detailPage('{{:orderId}}')">{{:orderId}}</a></td>
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
     <td>{{:statePage}}</td>
     <td><a href="javascript:void(0)" onclick="pager._detailPage('{{:orderId}}')">查看</a></td>
  </tr>                                                                       
</script> 
  <script type="text/javascript">
	var pager;
	(function () {
		seajs.use('app/jsp/order/waitPriceOrderList', function (WaitPriceOrderListPager) {
			pager = new WaitPriceOrderListPager({element: document.body});
			pager.render();
		});
	})();
 </script>  
</body>

</html>