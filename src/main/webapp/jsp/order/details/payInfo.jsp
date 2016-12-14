<%@ page contentType="text/html;charset=UTF-8"%>
<script id="payInfoTempl" type="text/x-jsrender">
                      {{if displayFlag!='11'&&displayFlag!='13'}}	
							<div class="nav-tplist-title bd-bottom pb-10  pt-15">
								<ul>
									<li>支付信息</li>
								</ul>
							</div>
							<!--标题带下划线结束-->
							<div id="payInfoDiv" class="form-label">
							   <ul>
							      <li class="col-md-6">
									  <p class="word">订单总价：</p>
									  <p>
										{{if orderFee.currencyUnit=='1' }}
										  {{:~liToYuan(orderFee.totalFee)}}
										{{else}}
											{{:orderFee.totalFee}}
										{{/if}}
										  {{:~getMoneyUnit(orderFee.currencyUnit)}}
									  </p>
								  </li>
								  <li class="col-md-6">
									  <p class="word">订单折扣：</p>
									  <p>
										  {{:discountSum==null?'无折扣':discountSum+'折'}}
									  </p>
								  </li>
								 </ul>
								 <ul>
								  <li class="col-md-6">
									  <p class="word">优惠券减免：</p>
									  <p>
										  {{:~liToYuan(orderFee.couponFee)}}
										  {{:~getMoneyUnit(orderFee.currencyUnit)}}
									  </p>
								  </li>
								   <li class="col-md-6">
									  <p class="word">应付金额：</p>
									  <p>
										{{if orderFee.currencyUnit=='1' }}
										  {{:~liToYuan(orderFee.adjustFee)}}
										{{else}}
											{{:orderFee.adjustFee}}
										{{/if}}
										  {{:~getMoneyUnit(orderFee.currencyUnit)}}
									  </p>
								  </li>
							   </ul>
							    <ul>
								  <li class="col-md-6">
									  <p class="word">实付金额：</p>
									  <p>
										{{if orderFee.currencyUnit=='1' }}
										  {{:~liToYuan(orderFee.paidFee)}}
										{{else}}
											{{:orderFee.paidFee}}
										{{/if}}
										  {{:~getMoneyUnit(orderFee.currencyUnit)}}
									  </p>
								  </li>
							   </ul>
							</div>
					{{/if}}
</script>