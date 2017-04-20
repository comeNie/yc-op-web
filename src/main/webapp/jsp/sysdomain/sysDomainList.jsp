<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>用途列表</title>
<%@include file="/inc/inc.jsp" %>
<input type="hidden" id="sysdomainIdUpdate"/>
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
											<p class="word">领域名称</p>
											<p><input class="int-text int-medium" id="domainCn" value="" type="text"></p>
										</li>
										<li class="col-md-6">
								           <p class="word">站点</p>
						            		<p>
							            		<select class="select select-medium" id="site">
							            			<option value="" selected="selected">全部</option>
													<!-- pc -->
													<option value="1">译云-中文站</option>
													<!-- app -->
													<option value="2">译云-英文站</option>
													<!-- app-hd -->
													<option value="3">百度</option>
													<!-- pc -->
													<option value="4">金山</option>
													<!-- app -->
													<option value="5">wap-中文</option>
													<!-- app-hd -->
													<option value="6">wap-英文</option>
													<!-- pc -->
													<option value="7">找翻译</option>
													<!-- app -->
													<option value="8">微信助手</option>
												</select>
						            		</p>
							            </li>
							            <li class="col-md-6">
								           <p class="word">状态</p>
						            		<p>
							            		<select class="select select-medium" id="state">
							            			<option value="" selected="selected">全部</option>
													<!-- 启用 -->
													<option value="0">显示</option>
													<!-- 禁用 -->
													<option value="1">隐藏</option>
												</select>
						            		</p>
							            </li>
							           <!--  <li class="col-md-6">
											<p class="word">创建人</p>
											<p><input class="int-text int-medium" id="" value="" type="text"></p>
										</li> -->
			                    	</ul>
		                    	</div> 
	                    		<ul>
									<li class="width-xlag">
										<p class="word">&nbsp;&nbsp;&nbsp;</p>
										<p class="word"><input type="button" class="biu-btn  btn-primary btn-blue btn-medium ml-5"
												  id="search" value="查  询"></p>
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
												<th>领域名称</th>
												<th>站点</th>
												<th>地区语言</th>
												<th>排序</th>
												<th>创建时间</th>
												<th>状态</th>
												<th>操作</th>
											</tr>
                                        </thead>
                                         <tbody id="sysDomainListData"></tbody>
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
<script id="sysDomainListTemple" type="text/template">
	<tr>
  	  <td>{{:domainId}}</td>
      <td>{{:domainCn}}</td>
	  <td>
		  {{if  site == '1'}}
		     译云-中文站
		  {{else site == '2'}}
		    译云-英文站
		  {{else site == '3'}}
		     百度
		  {{else site == '4'}}
		     金山
		  {{else site == '5'}}
		  wap-中文
		  {{else site == '6'}}
		  wap-英文
		  {{else site == '7'}}
		      找翻译
		  {{else site == '8'}}
		      微信助手
		  {{/if}}
	  </td>      
      <td>
		 {{if  language == '1'}}
		  	中文
		  {{else language == '2'}}
		  	英语
		  {{/if}}
	  </td>
	 
	  <td>{{:sort}}</td>
	  <td>
			{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', createTime)}}
	  </td>
	  <td>
		  {{if  state == '0'}}
		  	显示
		  {{else state == '1'}}
		  	隐藏
		  {{/if}}
	  </td>     
	  <td>
 		  <a href="javascript:void(0);" class="adopt" onclick="pager._delete('{{:domainId}}')">删除</a>
		  <a href="javascript:void(0);" class="adopt" onclick="pager._update('{{:domainId}}')">修改</a>
	  </td>
    </tr>
</script>
 <script type="text/javascript">
	var pager;
	(function () {
		seajs.use('app/jsp/sysdomain/sysDomainList', function (OrderListPager) {
			pager = new OrderListPager({element: document.body});
			pager.render();
		});
	})();
 </script>  
</body>
