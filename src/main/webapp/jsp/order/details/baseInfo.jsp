<%@ page contentType="text/html;charset=UTF-8"%>
<script id="baseInfoTempl" type="text/x-jsrender">
                            <div class="nav-tplist-title bd-bottom pb-10  pt-15">
								<ul>
									<li>基本信息</li>
								</ul>
							</div>

                             <div id="baseInfoDiv" class="form-label">
								<ul>
									<li class="col-md-6">
										<p class="word">订单号：</p>
										<p>{{:orderId}}</p>
									</li>
									<li class="col-md-6">
										<p class="word">订单状态：</p>
										<p>{{:~getStateName(state)}}</p>
									</li>
									<li class="col-md-6">
										<p class="word">订单来源：</p>
										<p>{{:~getChlIdName(chlId)}}</p>
									</li>
									<li class="col-md-6">
                                        <input id="translateType" type="hidden" value="{{:translateType}}"/>
										<p class="word">订单类型：</p>
										<p>{{:~getTranslateTypeName(translateType)}}</p>
									</li>
									<li class="col-md-6">
										<p class="word">用户昵称：</p>
										<p>{{:usernick}}</p>
									</li>
									<li class="col-md-6">
										<p class="word">用户名：</p>
										<p>{{:username}}</p>
									</li>
					
                                    <li class="col-md-6">
										<p class="word">时区：</p>
										<p>{{:timeZone}}</p>
									</li>
									<li class="col-md-6">
										<p class="word">下单时间(北京)：</p>
										<p>{{:~timesToFmatter(orderTime)}}</p>
									</li>
								
                                    <li class="col-md-6">
										<p class="word">是否开发票：</p>
										<p>{{:prod.isInvoice}}
                                             {{if prod.isInvoice=='Y'}}是{{else}}否{{/if}}
                                        </p>
									</li>
                                    <li class="col-md-6">
										<p class="word">预计翻译耗时：</p>
										<p>
                                            {{if mod=='edit'&&(displayFlag=='11'||displayFlag=='13'||displayFlag=='23'||displayFlag=='50')}}
											  <input id="takeDay" name="prod.takeDay" type="text" value="{{:prod.takeDay==null?0:prod.takeDay}}" class="int-text int-minied" />天
                                              <input id="takeTime" name="prod.takeTime" type="text" value="{{:prod.takeTime==null?0:prod.takeTime}}" class="int-text int-minied" />小时
                                               &nbsp;<label id="takeDay-error" class="error" for="takeDay" style="display: none;"></label>
                                               &nbsp;<label id="takeTime-error" class="error" for="takeTime" style="display: none;"></label>
										    {{else}}
                                               {{:prod.takeDay}}天{{:prod.takeTime}}小时</p>
                                            {{/if}}

									</li>

                                    <li class="col-md-6">
										<p class="word">联系人姓名：</p>
										<p> 
                                            {{if mod=='edit'&&(displayFlag=='11'||displayFlag=='13'||displayFlag=='23'||displayFlag=='50')}}
											  <input id="contactName" name="contacts.contactName" type="text" value="{{:contacts.contactName}}" class="int-text int-medium" />
										    {{else}}
                                               {{:contacts.contactName}}
                                            {{/if}}
                                        </p>
									</li>
									<li class="col-md-6">
										<p class="word">联系人手机：</p>
										<p>
                                             {{if mod=='edit'&&(displayFlag=='11'||displayFlag=='13'||displayFlag=='23'||displayFlag=='50')}}
											   <input id="contactTel" name="contacts.contactTel" type="text" value="{{:contacts.contactTel}}" class="int-text int-medium" />
									     	{{else}}
                                               {{:contacts.contactTel}}
                                            {{/if}}
                                        </p>
									</li>
								
									<li class="col-md-6">
										<p class="word">联系人邮箱：</p>
										<p>
                                            {{if mod=='edit'&&(displayFlag=='11'||displayFlag=='13'||displayFlag=='23'||displayFlag=='50')}}
											  <input id="contactEmail" name="contacts.contactEmail" type="text" value="{{:contacts.contactEmail}}" class="int-text int-medium" />
                                            {{else}}
                                               {{:contacts.contactEmail}}
                                            {{/if}}
										</p>
									</li>

									<li class="col-md-6">
										<p class="word">客户留言：</p>
										<p class="col-md-8">
											{{:remark}}
										</p>
									</li>
                              </ul>
                              <ul>
								  {{if subFlag==1&&orderFee.updateOperId!=null}}
                                    <li class="col-md-6">
										<p class="word">报价人：</p>
										<p>{{:orderFee.updateOperName}}</p>
									</li>
									<li class="col-md-6">
										<p class="word">报价时间：</p>
										<p>{{:~timesToFmatter(orderFee.updateTime)}}</p>
									</li>
								  {{/if}}
                                  {{if displayFlag!='11'&&displayFlag!='13'}}
									<li class="col-md-6">
										<p class="word">支付方式：</p>
										<p>{{:~getPayStyleName(orderFee.payStyle)}}</p>
									</li>
									<li class="col-md-6">
										<p class="word">支付时间：</p>
										<p>{{:~timesToFmatter(orderFee.payTime)}}</p>
									</li>
                                  {{/if}}

                                  {{if state=='91'}}
									<li class="col-md-6">
										<p class="word">取消类型：</p>
										<p>
                                           {{if operId=='1000011'}}系统取消{{else}}用户取消{{/if}}
                                        </p>
									</li>
									<li class="col-md-6">
										<p class="word">取消时间：</p>
										<p>{{:~timesToFmatter(stateChgTime)}}</p>
									</li>
								  {{/if}}
                                  {{if state=='51'||state=='52'}}
									<li class="col-md-6">
										<p class="word">确认类型：</p>
										<p>{{if operId=='1000011'}}后台确认{{else}}用户确认{{/if}}</p>
									</li>
									<li class="col-md-6">
										<p class="word">订单确认时间：</p>
										<p>{{:~timesToFmatter(stateChgTime)}}</p>
									</li>
                                   {{/if}}

                                   {{if lockTime!=null}}
                                    <li class="col-md-6">
										<p class="word">接单译员：</p>
										<p>
											{{:interperId}}
										</p>
									</li>
									<li class="col-md-6">
										<p class="word">LSP名称：</p>
										<p>
											{{:lspId}}
										</p>
									</li>
                                    <li class="col-md-6">
										<p class="word">领取时间：</p>
										<p>
											{{:~timesToFmatter(lockTime)}}
										</p>
									</li>
                                    {{/if}}

                                   {{if state=='24'}}
                                    <li class="col-md-6">
										<p class="word">译文提交时间：</p>
										<p>
											{{:~timesToFmatter(lockTime)}}
										</p>
									</li>
								   {{/if}}
                                    
                                   {{if state=='25'}}
								    <li class="col-md-6">
										<p class="word">修改剩余时间：</p>
										<p>
											{{:~getOverplusTimes(stateChgTime,7)}}
										</p>
									</li>
                                   {{/if}}
                                   {{if displayFlag!='11'&&displayFlag!='13'}}
                                    <li class="col-md-6">
										<p class="word">支付备注：</p>
										<p class="col-md-8">
											{{:payRemark}}
										</p>
									</li>
								   {{/if}}
                                   {{if state=='25'}}
									<li class="col-md-6">
										<p class="word">重启订单备注：</p>
										<p class="col-md-8">
											{{:reasonDesc}}
										</p>
									</li>
                                    {{/if}}

                                   {{if displayFlag=='92'}}
                                    <li class="col-md-6">
										<p class="word">退款备注：</p>
										<p class="col-md-8">
											{{:reasonDesc}}
										</p>
									</li>
                                    {{/if}}
								</ul>
							</div>
</script>