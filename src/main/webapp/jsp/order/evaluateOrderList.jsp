<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>已评价订单列表</title>
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
							            	<p class="word">译员昵称</p>
							            	<p><input class="int-text int-medium" id="interperName" type="text"></p>
						            	</li>
						            	<li class="col-md-6">
							            	<p class="word">lsp名称</p>
							            	<p><input class="int-text int-medium" id="lspName" type="text"></p>
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
		                    		<ul>
						                <li class="col-md-6">
						                    <p class="word">评论开始时间</p>
						                    <p><input readonly class="int-text int-medium " type="text"  id="evaluatTimeBegin" name="evaluatTimeBegin" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'evaluatEnd\')}'});"/>
						                    </p>
						                </li>
						                <li class="col-md-6">
						                    <p class="word">评论结束时间</p>
						                    <p><input  readonly class="int-text int-medium " type="text"  id="evaluatEnd" name="evaluatEnd" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'evaluatTimeBegin\')}'});"/>
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
                                            	<th>订单编号</th>
                                                <th>订单类型</th>
                                                <th>昵称</th>
                                                <th>订单级别</th>
                                                <th>语种方向</th>
                                                <th>质量</th>
                                                <th>速度</th>
                                                <th>态度</th>
                                                <th>评论时间</th>
                                                <th>译员昵称</th>
                                                <th>译员级别</th>
                                                <th>lsp名称</th>
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
                        </div>
                    </div>
                </div>
            </div>
    	</div>
   </div> 
<script id="orderListTemple" type="text/template">  
      <tr>
  	  <td>{{:chlIdPage}}</td>
	  <td><a href="javascript:void(0)" onclick="pager._detailPage('{{:orderId}}')">{{:orderId}}</a></td>
      <td>{{:translateTypePage}}</td>
 	  <td>{{:userName}}</td>
	  <td>{{:orderLevelPage}}</td>
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
      <td>{{:serveQuality}}</td>
 	  <td>{{:serveSpeed}}</td>
      <td>{{:serveManner}}</td>
	  <td>{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', evaluateTime)}}</td>
      <td>{{:interperName}}</td>
	  <td>{{:interperLevel}}</td>
	  <td>{{:lspName}}</td>
	 <td>{{:evaluateState}}</td>
     <td><a href="javascript:void(0)" onclick="pager._detailPage('{{:orderId}}')">查看</a></td>
  </tr>                             
</script> 
  <script type="text/javascript">
	var pager;
	(function () {
		seajs.use('app/jsp/order/evaluateOrderList', function (EvaluteOrderListPager) {
			pager = new EvaluteOrderListPager({element: document.body});
			pager.render();
		});
	})();
 </script>  
</body>

</html>