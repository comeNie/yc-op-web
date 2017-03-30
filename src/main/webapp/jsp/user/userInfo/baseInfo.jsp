<%@ page contentType="text/html;charset=UTF-8"%>
<script id="baseInfoTempl" type="text/x-jsrender">
                            <div class="nav-tplist-title bd-bottom pb-10  pt-15">
								<ul>
									<li>通用信息</li>
								</ul>
							</div>

                             <div id="baseInfoDiv" class="form-label">
								<ul>
									<li class="col-md-6">
										<p class="word">会员编号：</p>
										<p>{{:usrUser.userId}}</p>
									</li>
									<li class="col-md-6">
										<p class="word">logo头像：</p>
										<img src="{{:usrUser.portraitId}}"/>
									</li>
									<li class="col-md-6">
										<p class="word">昵称：</p>
										<input id="usrUser.nickname" name="nickname" type="text" value="{{:usrUser.nickname}}" class="int-text int-medium" />
									</li>
									<li class="col-md-6">
                                        <input id="translateType" type="hidden" value="{{:translateType}}"/>
										<p class="word">用户名：</p>
										<input id="usrUser.username" name="username" type="text" value="{{:usrUser.username}}" class="int-text int-medium" />
									</li>
									<li class="col-md-6">
										<p class="word">姓名：</p>
										<input id="usrUser.name" name="name" type="text" value="{{:usrUser.lastname}}{{:usrUser.firstname}}" class="int-text int-medium" />
									</li>
									<li class="col-md-6">
										<p class="word">性别：</p>
										<select id="usrUser.sex" name="sex" class="select select-medium requestP valid" aria-required="true" aria-invalid="false">
											   <option value="1">男</option>
											   <option value="0">女</option>
											</select>
									</li>
                                    <li class="col-md-6">
										<p class="word">手机：</p>
										<input id="usrUser.mobilePhone" name="mobilePhone" type="text" value="{{:usrUser.mobilePhone}}" class="int-text int-medium" />
									</li>
									<li class="col-md-6">
										<p class="word">邮箱：</p>
										<input id="usrUser.email" name="email" type="text" value="{{:usrUser.email}}" class="int-text int-medium" />

									</li>
                                    <li class="col-md-6">
										<p class="word">账户状态：</p>
										<p>正常
                                        </p>
									</li>
									<li class="col-md-6">
										<p class="word">激活状态：</p>
										<p>{{if usrUser.state==0}}正常{{else usrUser.state ==1}}忙碌{{else usrUser.state ==2}}停用{{/if}}</p>
									</li>
									<li class="col-md-6">
										<p class="word">会员等级：</p>
										<p>{{:usrUser.safetyLevel}}</p>
									</li>
									<li class="col-md-6">
										<p class="word">生日：</p>
										<p>{{:~timesToFmatter(usrUser.birthday)}}</p>
									</li>
									<li class="col-md-6">
										<p class="word">译员：</p>
										<p>
										{{if usrUser.isTranslator=='0'}}是{{else}}否{{/if}}</p>
									</li>
									<li class="col-md-6">
										<p class="word">译员级别：</p>
										<p>{{:usrUser.translatorLevel}}</p>
									</li>
									<li class="col-md-6">
										<p class="word">企业用户：</p>
										<p>{{if usrUser.isCompany==0 }}否{{else usrUser.isCompany==1}}是{{else}}{{/if}}</p>
									</li>
									<li class="col-md-6">
										<p class="word">注册时间：</p>
										<p>{{:~timesToFmatter(usrUser.registTime)}}</p>
									</li>
									<li class="col-md-6">
										<p class="word">注册来源：</p>
										<p>{{if usrUser.usersource==0}}内部系统{{else usrUser.usersource ==1}}金山{{else usrUser.usersource ==2}}百度用户{{else usrUser.usersource ==3}}金山{{else usrUser.usersource ==4}}微信{{else usrUser.usersource ==5}}wap端{{else usrUser.usersource ==6}}pc{{else}}其他{{/if}}</p>
									</li>
									<li class="col-md-6">
										<p class="word">已支付订单数量：</p>
									</li>
									<li class="col-md-6">
										<p class="word">账户余额：</p>
										<p>{{:usrUser.balance}}</p>
									</li>
									<li class="col-md-6">
										<p class="word">收藏量：</p>
										<p>{{:usrUser.collectCount}}</p>
									</li>
									<li class="col-md-6">
										<p class="word">注册时IP：</p>
										<p>{{:usrUser.registIP}}</p>
									</li>
									<li class="col-md-6">
										<p class="word">注册时省：</p>
										<p>{{:usrUser.province}}</p>
									</li>
									<li class="col-md-6">
										<p class="word">城市：</p>
										<p>{{:usrUser.cnCity}}</p>
									</li>
									<li class="col-md-6">
										<p class="word">区县：</p>
										<p></p>
									</li>
                              </ul>
							  
							</div>
						{{if usrContact != null }}
							  <div class="nav-tplist-title bd-bottom pb-10  pt-15">
								<ul>
									<li>联系人信息</li>
								</ul>
							</div>
							<table class="table table-hover table-border table-bordered">
									<thead>
										<tr>
											<th>联系人</th>
											<th>手机</th>
											<th>电话</th>
											<th>省</th>
											<th>市</th>
											<th>区</th>
											<th>详细地址</th>
											<th>邮政编码</th>
											<th>默认地址</th>
											<th>记录下单时的联系人</th>
										</tr>
									</thead>
									<tbody id="usrContactValueTable">
									  		{{for usrContact  ~subFlag=subFlag}}
   												<tr>
													<td>{{:userName}}</td>
													<td>{{:mobilePhone}}</td>
													<td>{{:phone}}</td>
													<td>省份</td>
													<td>市</td>
													<td>区</td>
													<td>{{:address}}</td>
													<td>邮政编码</td>
													<td>{{if isDefault==0}}非默认{{else}}默认{{/if}}</td>
													<td>记录下单时的联系人</td>
   												</tr>
  											{{/for}}
									</tbody>
						 	</table>
						{{/if}}



</script>