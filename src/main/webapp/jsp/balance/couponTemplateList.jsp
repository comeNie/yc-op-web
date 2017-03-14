<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>券码管理</title>
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
		                    	<div id="selectDiv">
			                    	<ul>
										<li class="col-md-6">
											<p class="word">名称</p>
											<p><input class="int-text int-medium" id="couponName"  value="" type="text"></p>
										</li>
										<li class="col-md-6">
											<p class="word">面值</p>
											<p><input class="int-text int-medium" id="faceValue"  value="" type="text"></p>
										</li>
			                    		<li class="col-md-6">
								           <p class="word">使用场景</p>
						            		<p>
							            		<select class="select select-medium" id="usedScene">
							            			<option value="" selected="selected">全部</option>
													<!-- pc -->
													<option value="1">pc</option>
													<!-- app -->
													<option value="2">app</option>
													<!-- app-hd -->
													<option value="3">app-hd</option>
												</select>
						            		</p>
							            </li>
							            <li class="col-md-6">
								           <p class="word">状态</p>
						            		<p>
							            		<select class="select select-medium" id="status">
							            			<option value="" selected="selected">全部</option>
													<!-- 启用 -->
													<option value="1">启用</option>
													<!-- 禁用 -->
													<option value="2">禁用</option>
												</select>
						            		</p>
							            </li>
							            <li class="col-md-6">
								           <p class="word">币种单位</p>
						            		<p>
							            		<select class="select select-medium" id="currencyUnit">
							            			<option value="" selected="selected">全部</option>
													<!-- CNY￥ -->
													<option value="1">CNY¥</option>
													<!-- USD$ -->
													<option value="2">USD$</option>
												</select>
						            		</p>
							            </li>
							            <li class="col-md-6">
											<p class="word">创建人</p>
											<p><input class="int-text int-medium" id="createOperator"  value="" type="text"></p>
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
							<div class="main-box-body clearfix">
								<div class="order-list-table">
									<p class="word"><input type="button" class="biu-btn  btn-primary btn-blue btn-medium ml-5"
												  id="add" value="新 增"></p>
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
												<th>序号</th>
												<th>名称</th>
												<th>面值(￥/$)</th>
												<th>使用规则</th>
												<th>领取时间</th>
												<th>有效期</th>
												<th>使用场景</th>
												<th>已使用/已领/总数</th>
												<!-- <th></th>
												<th></th>
												<th></th> -->
												<th>创建人</th>
												<th>创建时间</th>
												<th>状态</th>
												<th>操作</th>
											</tr>
                                        </thead>
                                         <tbody id="couponTemplateListData"></tbody>
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
<script id="couponTemplateListTemple" type="text/template">
	<tr>
  	  <td>{{:templateId}}</td>
      <td>{{:couponName}}</td>
      <td>
		  {{:faceValue}}
		  {{if  flag == '1'}}
		  CNY¥
		  {{else flag == '2'}}
		  USD$
		  {{/if}}
		  {{:~liToYuan(currencyUnit)}}
	  </td>
 	  <td>{{:couponUserId}}</td>
      <td>
			{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', receiveStartTime)}}—{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', receiveEndTime)}}
	  </td>
	  <td>
			{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', effectiveStartTime)}}—{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', effectiveEndTime)}}
	  </td>
	  <td>
		  {{if  usedScene == '1'}}
		  pc
		  {{else usedScene == '2'}}
		  app
		  {{else usedScene == '3'}}
		  app-hd
		  {{/if}}
	  </td>      
      <td>{{:maxCountIssue}}</td>
	 
	  <td>{{:createOperator}}</td>
	  <td>
			{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', createTime)}}
	  </td>
	  <td>
		  {{if  status == '1'}}
		  	启用
		  {{else status == '2'}}
		  	禁用
		  {{/if}}
	  </td>     


	  <td>
		  <a href="javascript:void(0);" class="adopt" onclick="pager._detailPage('{{:templateId}}')">明细</a>
 		  <a href="javascript:void(0);" class="adopt" onclick="pager._delete('{{:templateId}}')">删除</a>
 		  <a href="javascript:void(0);" class="adopt" onclick="pager._manualSend">手动发放</a>
	  </td>
    </tr>
</script>
 <script type="text/javascript">
	var pager;
	(function () {
		seajs.use('app/jsp/couponTemplate/couponTemplateList', function (OrderListPager) {
			pager = new OrderListPager({element: document.body});
			pager.render();
		});
	})();
 </script>  
</body>
