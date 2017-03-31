<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>待审核企业详情</title>
<%@include file="/inc/inc.jsp"%>
<style type="text/css">
    .btn-ext{
       margin-bottom: 10px;
       margin-left: 60%;
    }
</style>
</head>
<body>
    <input id="mod" type="hidden" value="${model}"/>
    <form id="orderForm">
    <input id="orderId" name="orderId" type="hidden" value="${orderId}"/>
    <input id="stateCheck" name="stateCheck" type="hidden" value="${stateCheck}"/>
     <input id="isAll" name="isAll" type="hidden" value="${isAll}"/>
	 <div class="row">
	 
		<!--内侧框架-->
		<div class="col-lg-12">
			<!--删格化-->
			<div class="main-box clearfix">
				<!--白色背景-->
				<div class="main-box-body clearfix">
					<div class="order-list-table">
						<ul>
							<li><a href="#" class="current">查看详情</a></li>
						</ul>
					</div>
				</div>
				<!--选项卡1-->
				<div id="date1">
					
				</div>
				
			</div>
			
		</div>
	</div>	
	
   </form>
</body>
<script id="orderInfoTempl" type="text/x-jsrender">
	<!--白色背景-->
	<div class="main-box-body clearfix">
				  <div class="nav-tplist-title bd-bottom pb-10  pt-15">
					<ul>
						<li>通用信息</li>
					</ul>
				  </div>
                  <div class="form-label">
								<ul>
									<li class="col-md-6">
										<p class="word">申请来源：</p>
										<p>
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
									</p>
									</li>
									<li class="col-md-6">
										<p class="word">申请时间：</p>
										<p>{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', createTime)}}</p>
									</li>
									<li class="col-md-6">
										<p class="word">会员编号：</p>
										<p>{{:userId}}</p>
									</li>
									<li class="col-md-6">
										<p class="word">会员级别：</p>
										<p>{{:griwthLevelZH}}</p>
									</li>
									<li class="col-md-6">
										<p class="word">是否译员：</p>
										<p>
										{{if isTranslator==null}}
											否
										{{else isTranslator==1}}
											是
										{{/if}}
										</p>
									</li>
									<li class="col-md-6">
										<p class="word">译员等级：</p>
										<p></p>
									</li>
									<li class="col-md-6">
										<p class="word">昵称：</p>
										<p>{{:nickname}}</p>
									</li>
                                    <li class="col-md-6">
										<p class="word">用户名：</p>
										<p>{{:username}}</p>
									</li>
									<li class="col-md-6">
										<p class="word">姓名：</p>
										<p>{{:lastname}}{{:firstname}}</p>
									</li>
								
                                    <li class="col-md-6">
										<p class="word">性别：</p>
										<p> 
                                           {{if sex==0}}
                                               	男
                                           {{else sex==1}}
												女
                                           {{/if}}
                                        </p>
									</li>
									<li class="col-md-6">
										<p class="word">手机：</p>
										<p>
                                              {{:mobilePhone}}
                                        </p>
									</li>
									<li class="col-md-6">
										<p class="word">邮箱：</p>
										<p>
                                           {{:email}}
										</p>
									</li>
									<li class="col-md-6">
										<p class="word">个人账户余额：</p>
										<p>{{:balance}}</p>
									</li>
						</div>
						<div class="main-box-body clearfix">
						</div>
						<div class="nav-tplist-title bd-bottom pb-10  pt-15">
					<ul>
						<li>企业信息</li>
					</ul>
				  </div>
				   <div class="form-label">
								<ul>
									<li class="col-md-6">
										<p class="word">企业名称：</p>
										<p>
											{{:companyName}}
									    </p>
									</li>
									<li class="col-md-6">
										<p class="word">企业Logo：</p>
										<p><img src="{{:logoUrl}}" style="width: 25px;height: 25px"/></p>
									</li>
									<li class="col-md-6">
										<p class="word">企业座机：</p>
										<p>{{:telephone}}</p>
									</li>
									<li class="col-md-6">
										<p class="word">企业联系人：</p>
										<p>{{:linkman}}</p>
									</li>
									<li class="col-md-6">
										<p class="word">企业账户余额：</p>
										<p>
										
										</p>
									</li>
									<li class="col-md-6">
										<p class="word">营业执照：</p>
										<p>
											<img src="{{:licenseAttacurl}}" style="width: 25px;height: 25px"/>
										</p>
									</li>
									<li class="col-md-6">
										<p class="word">审核人：</p>
										<p>{{:nickname}}</p>
									</li>
                                    <li class="col-md-6">
										<p class="word">审核时间：</p>
										<p>{{:username}}</p>
									</li>
									<li class="col-md-6">
										<p class="word">省：</p>
										<p>{{:lastname}}{{:firstname}}</p>
									</li>
								
                                    <li class="col-md-6">
										<p class="word">城市：</p>
										<p> 
                                           
                                        </p>
									</li>
									<li class="col-md-6">
										<p class="word">区县：</p>
										<p>
                                              {{:mobilePhone}}
                                        </p>
									</li>
									<li class="col-md-6">
										<p class="word">详细地址：</p>
										<p>
                                           {{:address}}
										</p>
									</li>
									<li class="col-md-6">
										<p class="word">结算方式：</p>
										<p>{{:balance}}</p>
									</li>
									<li class="col-md-6">
										<p class="word">企业折扣：</p>
										<p>
                                           {{:email}}
										</p>
									</li>
									<li class="col-md-6">
										<p class="word">账单日：</p>
										<p>{{:balance}}</p>
									</li>
									<li class="col-md-6">
										<p class="word">结算周期：</p>
										<p>
                                           {{:email}}
										</p>
									</li>
									
								<ul>
									<li class="col-md-6">
										<p class="word">企业简介：</p>
										<p>{{:balance}}</p>
									</li>
								</ul>
								<ul>
									<li class="col-md-6">
										<p class="word">企业简介：</p>
										<p>{{:balance}}</p>
									</li>
								</ul>
						</div>
						<div class="main-box-body clearfix">
						</div>
				</script>	
				<div class="main-box-body clearfix">
					<div class="bill-list-table">
						<ul>
							<li ><a href="#"  id="foreign" class="current">企业成员信息</a></li>
						</ul>
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
												<th>昵称</th>
												<th>成员姓名</th>
												<th>企业角色</th>
												<th>会员级别 </th>
												<th>加入时间</th>
												<th>余额</th>
												<th>积分</th>
												<th>下单数</th>
											</tr>
                                        </thead>
                                         <tbody id="companyUsersListData"></tbody>
                                    </table>
                                    <div id="showMessage"></div>
                                </div>
                           		<!--/table表格结束-->
                                </div>
                                <!--分页-->
								 <div>
					 				 <nav style="text-align: center">
										<ul id="companyUsersPagination"></ul>
									</nav>
								  </div>
								<!--分页结束-->
                            </div>
                        </div>
                    </div>
                </div>
            </div>
    	</div>
 <script id="companyUserListTemple" type="text/template">
	<tr>
  	  <td>{{:#index+1}}</td>
      <td>{{:companyUserNickName}}</td>
      <td>{{:companyUserUserName}}</td>
 	  <td>
		  {{if isManagment==1}}
				企业管理员
		  {{else isManagment==0}}
                                               企业成员
		  {{/if}}
	  </td>
      <td>{{:companyUserLevel}}</td>
      <td>{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', companyJoinTime)}}</td>
	  <td></td>
      <td></td>
      <td>{{:companySingularNumber}}</td>
    </tr>
</script> 
<div class="bc-ang mb-12">
    <input id="cancel" type="button" class="biu-btn  btn-yellow btn-medium ml-10" value="返回" onclick="history.go(-1)">      
</div>
<script type="text/javascript">
	var userId = '${userId}';
	var usersource='${usersource}';
	var createTime='${createTime}';
	var companyId='${companyId}';
    var pager;
    (function () {
        seajs.use('app/jsp/user/companyInfo', function (CompanyInfoDetailsPager) {
            pager = new CompanyInfoDetailsPager({element: document.body});
            pager.render();

        });
    })();
</script>
</html>