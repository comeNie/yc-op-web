<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>会员列表</title>
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
							            <p class="word">昵称</p>
							            <p><input class="int-text int-medium" id="nickName" type="text"></p>
						            </li>
		                    		<li class="col-md-6">
							            <p class="word">手机/邮箱</p>
							            <p><input class="int-text int-medium"  id="mobilePhone" type="text"></p>
						            </li>
		                    	</ul>
		                    	<ul>
							         <li class="col-md-6">
							            <p class="word">译员</p>
							            <p>
						            		<select class="select select-medium" id="userType">
						            			<option value="2">全部</option>
						            			<option value="0">是</option>
						            			<option value="1">否</option>
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
								           <p class="word">注册来源</p>
						            		<p>
							            		<select class="select select-medium" id="userSource">
							            			<option value="">全部</option>
							            			<option value="0">内部系统</option>
							            			<option value="1">金山</option>
							            			<option value="2">百度用户</option>
							            			<option value="3">微信</option>
							            			<option value="4">找翻译</option>
							            			<option value="5">wap端</option>
							            			<option value="6">pc</option>
							            		</select>
						            		</p>
							            </li>
			                    		<li class="col-md-6">
								            <p class="word">企业用户</p>
								            <p>
							            		<select class="select select-medium" id="isCompany">
							            			<option value="">全部</option>
							            		</select>
						            		</p>
							            </li>
			                    	</ul>
			                    	<ul>
			                    		<li class="col-md-6">
								            <p class="word">会员等级</p>
								            <p>
							            		<select class="select select-medium" id="safetyLevel">
							            			<option value="">全部</option>
							            			<option value="1">普通会员</option>
							            			<option value="2">VIP会员</option>
							            			<option value="3">SVIP会员</option>
							            			<option value="4">SVIP白金会员</option>
							            		</select>
						            		</p>
							            </li>
							            <li class="col-md-6">
							            	<p class="word">账户状态</p>
						            		<p>
							            		<select class="select select-medium" id="userState">
							            			<option value="3">全部</option>
							            			<option value="0">正常</option>
							            			<option value="1">忙碌</option>
							            			<option value="2">停用</option>
							            		</select>
						            		</p>
							         	</li>
		                    		</ul>
			                    	<div id="orderTimeDiv">
			                    		<ul>
							                <li class="col-md-6">
							                    <p class="word">注册开始时间</p>
							                    <p><input  readonly class="int-text int-medium " type="text"  id="registTimeStart" name="registTimeBegin" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'registTimeStart\')}'});" />
							                    </p>
							                </li>
							                <li class="col-md-6">
							                    <p class="word">注册结束时间</p>
							                    <p><input  readonly class="int-text int-medium " type="text"  id="registTimeEnd" name="registTimeEnd" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'registTimeEnd\')}'});"/>
							                    </p>
							                </li> 
						            	</ul>
			                    	</div>
						           <div id="payTimeDiv">
							           	<ul>
							                <li class="col-md-6">
							                    <p class="word">登录开始时间</p>
							                    <p><input  readonly class="int-text int-medium " type="text"  id="loginTimeBegin" name="loginTimeBegin" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'loginTimeBegin\')}'});" />
							                    </p>
							                </li>
							                <li class="col-md-6">
							                    <p class="word">登录开始时间</p>
							                    <p><input  readonly class="int-text int-medium " type="text"  id="loginTimeEnd" name="loginTimeEnd" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'loginTimeEnd\')}'});"/>
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
                                            	<th>编号</th>
                                                <th>注册来源</th>
                                                <th>昵称</th>
                                                <th>手机号</th>
                                                <th>邮箱</th>
                                                <th>会员级别</th>
                                                <th>是否译员</th>
                                                <th>是否企业用户</th>
                                                <th>余额</th>
                                                <th>激活状态</th>
                                                <th>账户状态</th>
                                                <th>操作</th>
                                            </tr>
                                        </thead>
                                         <tbody id="userListData"></tbody>
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
											<p>成长值操作</p>
											<p class="img" id="colseImage"><i class="fa fa-times" ></i></p>
										</div>
										<input type="hidden" name="userId" />
										<div class="form-label mt-10">
							           		<ul>
								                <li>
								                    <p class="word">方式:</p>
								                    <p class=" int-small"><input type="radio" id = "type" name = "type" value="0" />奖励</p>
								                    <p class=" int-small"><input type="radio" id = "type1" name = "type" value="1" />扣除</p>
								                </li>
								                 <li>
								                    <p class="word">成长值:</p>
								                    <p>
								                    	<input type="text" class="int-text int-small" name ="griwthValue" >
								                    </p>
								                    <p id="errorMessage"></p>
								                </li>
								                <li>
								                    <p class="word">操作原因:</p>
								                	<p><textarea id="remark" name="griwthResource" rows="4" cols="25" class="int-text" maxlength="100"></textarea></p>
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
<script id="userListTemple" type="text/template">
	<tr>
  	  <td>{{:userId}}</td>
      	{{if usersource==0 }}
 			<td>内部系统</td>
		{{else usersource==1}}
 			<td>金山</td>
	    {{else usersource==2}}
 			<td>百度用户</td>
	   	{{else usersource==3}}
 			<td>微信</td>
	    {{else usersource==4}}
 			<td>找翻译</td>
	    {{else usersource==5}}
 			<td>wap端</td>
	    {{else usersource==6}}
 			<td>pc</td>
		{{else }}
			<td></td>
	    {{/if}}

	  <td>{{:nickname}}</td>
	  <td>{{:mobilePhone}}</td>
	  <td>{{:email}}</td>
	  <td>{{:safetyLevel}}</td>
		{{if isTranslator==0 }}
 			<td>是</td>
	    {{else isTranslator==1}}
 			<td>否</td>
	    {{else}}
 			<td>null</td>
	    {{/if}}

		{{if isCompany==0 }}
 			<td>否</td>
	    {{else isCompany==1}}
 			<td>是</td>
	    {{else}}
 			<td>null</td>
	    {{/if}}
		<td>{{:balance}}</td>
		{{if state==0 }}
 			<td>正常</td>
	    {{else state==1}}
 			<td>忙碌</td>
	    {{else state==3}}
 			<td>停用</td>
	    {{else}}
 			<td></td>
	    {{/if}}
		<td>正常</td>
		<td>
			<a href="javascript:void(0);" onclick="pager._detailPage('{{:userId}}')">查看(锁定/解锁)</a>
			<a href="javascript:void(0);" onclick="pager._popUp('{{:userId}}')">奖励</a>
		</td>
		
  </tr>                                                                       
</script> 
  <script type="text/javascript">
	var pager;
	(function () {
		seajs.use('app/jsp/user/userList', function (UserListPager) {
			pager = new UserListPager({element: document.body});
			pager.render();
		});
	})();
 </script>  
</body>

</html>