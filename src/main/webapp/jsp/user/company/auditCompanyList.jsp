<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>企业用户</title>
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
								<div class="main-box-body clearfix">
									<div class="bill-list-table">
										<ul>
											<li ><a href="#" id="domestic"  class="current">待审核</a></li>
											<li ><a href="#"  id="foreign" >企业列表</a></li>
										</ul>
									</div>
								</div>
		                    	<div id="selectDiv">
			                    	<ul>
										<li class="col-md-6">
											<p class="word">昵称</p>
											<p><input class="int-text int-medium" id="nickName"  value="" type="text"></p>
											<label id="nickNameErrMsg" style="display: none;"><span class="ash" id="nickNameErrText"></span></label>
										</li >
										<li class="col-md-6">
											<p class="word">企业名称</p>
											<p><input class="int-text int-medium" id="companyName"  value="" type="text"></p>
										</li>
										<li class="col-md-6">
											<p class="word">手机</p>
											<p><input class="int-text int-medium" id="mobilephone"  value="" type="text"></p>
										</li>
										<li class="col-md-6">
											<p class="word">审核人</p>
											<p><input class="int-text int-medium" id="checkName"  value="" type="text"></p>
										</li>
										<li class="col-md-6">
								           <p class="word">申请来源</p>
						            		<p>
							            		<select class="select select-medium" id="userSource">
							            			<option value="" selected="selected">全部</option>
													<option value="10">译云中文站</option>
													<option value="11">译云英文站</option>
													<option value="2">百度</option>
													<option value="1">金山</option>
													<option value="12">找翻译</option>
													<option value="13">wap-中文站</option>
													<option value="14">wap-英文站</option>
													<option value="15">微信助手</option>
												</select>
						            		</p>
							            </li>
			                    	</ul>
			                    	<div id="orderTimeDiv">
			                    		<ul>
							                <li class="col-md-6">
							                    <p class="word">创建时间</p>
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
												<th>申请来源</th>
												<th>企业名称</th>
												<th>创建人昵称</th>
												<th>姓名</th>
												<th>企业座机</th>
												<th>联系人</th>
												<th>申请时间</th>
												<th>状态</th>
												<th>操作</th>
											</tr>
                                        </thead>
                                         <tbody id="companyListData"></tbody>
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
<script id="companyListTemple" type="text/template">
	<tr>
  	  <td>{{:#index+1}}</td>
      <td>
		{{if usersource == '0'}}
           	 pc
        {{else usersource == '10'}}
           	   译云中文站
        {{else usersource=='11'}}
                                       译云英文站
		{{else usersource=='2'}}
                                       百度
		{{else usersource=='1'}}
                                       金山
		{{else usersource=='12'}}
                                       找翻译
		{{else usersource=='13'}}
             wap-中文站
		{{else usersource=='13'}}
             wap-英文站
		{{else usersource=='14'}}
             	微信助手
		{{/if}}
      </td>
      <td>{{:companyName}}</td>
 	  <td>{{:nickName}}</td>
      <td>{{:firstname}}{{:lastname}}</td>
      <td>{{:telephone}}</td>
      <td>{{:linkman}}</td>
      <td>{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', createTime)}}</td>
      <td>
	     {{if  state == 0}}
		 	未审核
		  {{/if}}
		 {{if  state == 1}}
		 	已审核
		  {{/if}}
		 {{if  state == 2}}
		 	解锁
		  {{/if}}
		 {{if  state == 3}}
		 	锁定
		 {{/if}}
	  </td>
	<td>
        <a href="javaScript:void(0);" class="adopt" onclick="pager._toCompanyDetail('{{:companyId}}','{{:adminUserId}}','{{:usersource}}','{{:createTime}}')">查看</a>  
        <a href="javaScript:void(0);" class="adopt" onclick="pager._toCompanyAudit('{{:companyId}}','{{:companyName}}','{{:adminUserId}}','{{:usersource}}','{{:createTime}}')">通过</a>
                           锁定
    </td>
    </tr>
</script> 
  <script type="text/javascript">
	var pager;
	(function () {
		seajs.use('app/jsp/user/auditCompanyInfolList', function (OrderListPager) {
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
