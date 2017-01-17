<%@ page contentType="text/html;charset=UTF-8"%>

<script id="orderFeeTempl" type="text/x-jsrender">
                          <input id="currencyUnit" type="hidden" value="{{:orderFee.currencyUnit}}"/>
                          <input type="hidden" name="currencytUnit" id="unitID" value="{{:orderFee.currencyUnit}}">
 						 <input type="hidden" id="typeDesc" value="{{:prod.typeDesc}}">    
						 
					<div class="nav-tplist-title bd-bottom pb-10  pt-15">
								<ul>
									<li>订单属性及金额信息</li>
								</ul>
						  </div>
                  {{if mod=='edit'&&(displayFlag=='11'||displayFlag=='13')}}
                        {{if translateType!='2'}}
                           <div id="penTraslateDiv" class="form-label">
							    <ul>
									<li class="col-md-6">
                                        <input type="hidden" id="duadId" value="{{:prodExtends[0].langungePair}}"/>
										<p class="word">语言对：</p>
										<p>
											{{:prodExtends[0].langungePairName}}
										</p>
									</li>
                                    <li class="col-md-6">
										<p class="word">订单主题：</p>
										<p>
											{{:translateName}}
										</p>
									</li>
                                 </ul>
                                 <ul>
									<li class="col-md-6">
										<p class="word">翻译级别：</p>
										<p>
											<select id="translateLevel" name="translateLevel" class="select select-medium requestP">
											   <option value="100210" {{if prodLevels[0].translateLevel=='100210'}}selected="selected"{{/if}}>标准级</option>
											   <option value="100220" {{if prodLevels[0].translateLevel=='100220'}}selected="selected"{{/if}}>专业级</option>
											   <option value="100230" {{if prodLevels[0].translateLevel=='100230'}}selected="selected"{{/if}}>出版级</option>
											</select>
											
										</p>
									</li>
                                    <li class="col-md-6">
										<p class="word">用途：</p>
										<p>
	                                       <select id="useCode" name="prod.useCode" class="select select-medium requestP">
                                           </select>
										</p>
									</li>
                                 </ul>
                                 <ul>
									<li class="col-md-6">
										<p class="word">领域：</p>
										<p>
                                             <select id="fieldCode" name="prod.fieldCode" class="select select-medium">
                                             </select>
										</p>
									</li>
									<li class="col-md-6">
										<p class="word">订单字数：</p>
										<p>
										   	<input id="translateSum" name="prod.translateSum" 
                                                type="text" value="{{:prod.translateSum==null?0:prod.translateSum}}" class="int-text int-medium requestP"/>
										</p>
									</li>
                                 </ul>
                                 <ul>
									<li class="col-md-6">
                                        <input id="basePrice" type="hidden" />
										<p class="word">单价：</p>
										<p id="priceShow">
                                               
										</p>
									</li>
									<li class="col-md-6">
										<p class="word">是否排版：</p>
										<p>
										   	<select id="isSetType" name="prod.isSetType" class="select select-medium changeP">
										   	  {{if prod.isSetType=='Y'}}
										   	       <option value="N">否</option>
										   	       <option value="Y" selected="selected">是</option>
										   	  {{else}}
										   	      <option value="N" selected="selected">否</option>
										   	      <option value="Y">是</option>
										   	  {{/if}}
											</select>
											 
										</p>
									</li>
                                 </ul>
                                 <ul>
									<li class="col-md-6">
                                        <input name="orderFee.currencyUnit" type="hidden" value="{{:orderFee.currencyUnit}}"/>
										<p class="word">排版费用：</p>
										<p id="setTypeFeeInputId">
											<input id="setTypeFee" name="setTypeFee" type="text" value="{{:~liToYuan3(orderFee.setTypeFee)}}" class="int-text int-small changeP" />
                                            {{:~getMoneyUnit(orderFee.currencyUnit)}}
                                            &nbsp;<label id="setTypeFee-error" class="error" for="setTypeFee" style="display: none;"></label>
										</p>
										<p id="setTypeFeeId">{{:~liToYuan3(orderFee.setTypeFee)}}{{:~getMoneyUnit(orderFee.currencyUnit)}}</p>
									</li>
									<li class="col-md-6">
										<p class="word">格式转化：</p>
										<p>
											<%--是否进行格式转换--%>
											<select id="selectFormatConv"  class="select select-mini">
										   	 {{if prod.typeDesc!=null && prod.typeDesc!=""}}
										   	       <option value="N">无格式转化</option>
										   	       <option value="Y" selected="selected">格式转化</option>
										   	  {{else}}
										   	      <option value="N" selected="selected">无格式转化</option>
										   	      <option value="Y">格式转化</option>
										   	  {{/if}}
											</select>
										</p>
										<p class="ml-20"  id="inputFormatConvP" style="display: none; margin-left:2px;">
											<input id="inputFormatConv" name="prod.typeDesc" style="width:75px;" maxlength="20" type="text" class="int-text int-mini"/>
										</p>
									</li>
                                 </ul>
                                 <ul>
									<li class="col-md-6">
										<p class="word">转换费用：</p>
										<p id="descTypeFeeInputId">
										    <input id="descTypeFee" name="descTypeFee" type="text" value="{{:~liToYuan3(orderFee.descTypeFee)}}" class="int-text int-small changeP"/>
										    {{:~getMoneyUnit(orderFee.currencyUnit)}}
                                            &nbsp;<label id="descTypeFee-error" class="error" for="descTypeFee" style="display: none;"></label>
										</p>
										<p id="descTypeFeeId">{{:~liToYuan3(orderFee.descTypeFee)}}{{:~getMoneyUnit(orderFee.currencyUnit)}}</p>
									</li>
									<li class="col-md-6">
										<p class="word">是否加急：</p>
										<p>
										   	<select id="isUrgent" name="prod.isUrgent" class="select select-medium requestP">
										   	   {{if prod.isUrgent=='Y'}}
										   	       <option value="N">否</option>
										   	       <option value="Y" selected="selected">是</option>
										   	   {{else}}
										   	      <option value="N" selected="selected">否</option>
										   	      <option value="Y">是</option>
										   	   {{/if}}
											</select>
										</p>
									</li>
                                 </ul>
                                 <ul>
                                    <li class="col-md-6">
										<p class="word">加急费用：</p>
										<p id="urGentFeeInputId">
											<input id="urGentFee" name="urgentFee" type="text" data-describedby="messages" value="{{:~liToYuan3(orderFee.urgentFee)}}" class="int-text int-small changeP" />
										    {{:~getMoneyUnit(orderFee.currencyUnit)}}
                                            &nbsp;<label id="urGentFee-error" class="error" for="urGentFee" style="display: none;"></label>
										</p>
										<p id="urGentFeeId">{{:~liToYuan3(orderFee.urgentFee)}}{{:~getMoneyUnit(orderFee.currencyUnit)}}</p>
									</li>
									<li class="col-md-6">
										<p class="word">订单总价：</p>
										<p>
										    <input id="totalFee" name="totalFee" type="text" value="{{:~liToYuan3(orderFee.totalFee)}}" class="int-text int-small" />
										    {{:~getMoneyUnit(orderFee.currencyUnit)}}
                                            &nbsp;<label id="totalFee-error" class="error" for="totalFee" style="display: none;"></label>
										</p>
									</li>
									<li class="col-md-6">
										<p class="word">订单级别：</p>
										<p>
										   	<select id="orderLevelSel" name="orderLevelSel" onchange="pager._orderLevelSelOnChange(this);" class="select select-medium">
										   	    <option value="">请选择</option>
                                                <option value="1" {{if orderLevel=='1'}}selected="selected"{{/if}}>V1</option>
										   	    <option value="2" {{if orderLevel=='2'}}selected="selected"{{/if}}>V2</option>
                                                <option value="3" {{if orderLevel=='3'}}selected="selected"{{/if}}>V3</option>
                                                <option value="4" {{if orderLevel=='4'}}selected="selected"{{/if}}>V4</option>
											</select>
											<input id="orderLevel" name="orderLevel" type="hidden" value="{{:orderLevel}}"/>
										</p>
									</li>
									<li class="col-md-6">
										<p class="word">分流译员等级：</p>
										<p id="interperLevel">
										   
										</p>
									</li>
								</ul>
							</div>
                        {{else}}
							<!-- 口译 -->
							<div id="oralTraslateDiv" class="form-label">
							   <ul>
							       <li class="col-md-12">
										<p class="word">订单主题：</p>
										<p>
										   {{:translateName}}
										</p>
									</li>
							   </ul>
							   <ul>
							       <li class="col-md-12">
										<p class="word">语言对：</p>
										<p>
										   {{for prodExtends}}
										      {{:langungePairName}}
										   {{/for}}
										</p>
									</li>
							   </ul>
							   <ul>
							       <li class="col-md-12">
										<p class="word">口译类型：</p>
										<p>
		                                      <input id="translateLevel" name="translateLevel" value="100110" type="checkbox" class="checkbox-100110">
		                                      <label for="checkbox-100110">陪同翻译</label>&nbsp;&nbsp;
                                              <input id="translateLevel" name="translateLevel" value="100130" type="checkbox" class="checkbox-100130">
		                                      <label id="translateLevel" name="translateLevel" for="checkbox-100130">同声传译</label>&nbsp;&nbsp;
		                                      <input id="translateLevel" name="translateLevel" value="100120" type="checkbox" class="checkbox-100120">
		                                      <label for="checkbox-100120">交替传译</label>
	                                    </p>
                                        <label id="translateLevel-error" class="error" for="translateLevel"></label>
									</li>
									<li class="col-md-6">
										<p class="word">会议地址：</p>
										<p>{{:prod.meetingAddress}}</p>
									</li>
									<li class="col-md-6">
										<p class="word">会场数量：</p>
										<p>
										   <input id="meetingSum" name="prod.meetingSum" type="text" value="{{:prod.meetingSum}}" class="int-text int-medium" />
										</p>
									</li>
									<li class="col-md-6">
										<p class="word">译员数量：</p>
										<p>
										   <input id="interperSum" name="prod.interperSum" type="text" value="{{:prod.interperSum}}" class="int-text int-medium" />
										</p>
									</li>
									<li class="col-md-6">
										<p class="word">译员性别：</p>
										<p>
										   	<select id="interperGen" name="prod.interperGen" class="select select-medium">
                                                <option value="2">不限</option>
										   	    <option value="0" {{if prod.interperGen=='0'}}selected="selected"{{/if}}>男</option>
										   	    <option value="1" {{if prod.interperGen=='1'}}selected="selected"{{/if}}>女</option>
											</select>
										</p>
									</li>
									<li class="col-md-6">
										<p class="word">开始时间：</p>
										 <p>
												{{:~timesToFmatter(prod.stateTime)}}
										    <!--<input id="startTime" name="startTime" readonly value="{{:~timesToFmatter(prod.stateTime)}}" class="int-text int-medium " type="text" />
							                	<span class="time"><i class="fa  fa-calendar" ></i></span>-->
							             </p>
                                          <label id="startTime-error" class="error" for="startTime" style="display: none;"></label>
									</li>
									<li class="col-md-6">
										<p class="word">结束时间：</p>
										 <p>
											{{:~timesToFmatter(prod.endTime)}}
										    <!-- <input id="endTime" name="endTime" readonly value="{{:~timesToFmatter(prod.endTime)}}" class="int-text int-medium " type="text" />
											 <span class="time"> <i class="fa  fa-calendar" ></i></span>-->
							             </p>
                                         <label id="endTime-error" class="error" for="endTime" style="display: none;"></label>
									</li>
									<li class="col-md-6">
                                        <input name="orderFee.currencyUnit" type="hidden" value="{{:orderFee.currencyUnit}}"/>
										<p class="word">订单总价：</p>
										<p>
										   <input id="totalFee" name="totalFee" type="text" value="{{:~liToYuan3(orderFee.totalFee)}}" class="int-text int-small" />
										    {{:~getMoneyUnit(orderFee.currencyUnit)}}
                                            <label id="totalFee-error" class="error" for="totalFee" style="display: none;"></label>
										</p>
									</li>
									<li class="col-md-6">
										<p class="word">订单级别：</p>
										<p>
										   	<select id="orderLevelSel" name="orderLevelSel" class="select select-medium" disabled="disabled">
                                                <option value="">请选择</option>
										   	    <option value="1" {{if orderLevel=='1'}}selected="selected"{{/if}}>V1</option>
										   	    <option value="2" {{if orderLevel=='2'}}selected="selected"{{/if}}>V2</option>
                                                <option value="3" {{if orderLevel=='3'}}selected="selected"{{/if}}>V3</option>
                                                <option value="4" {{if orderLevel=='4'}}selected="selected"{{/if}}>V4</option>
											</select>
											<input id="orderLevel" name="orderLevel" type="hidden" value="{{:orderLevel}}"/>
										</p>
									</li>
									<li class="col-md-6">
										<p class="word">分流译员等级：</p>
										<p id="interperLevel">
										   
										</p>
									</li>
							   </ul>
							</div>
                         {{/if}} 
              {{else}}
                      {{if translateType!='2'}}
                           <div id="penTraslateDiv" class="form-label">
							    <ul>
									<li class="col-md-6">
										<p class="word">语言对：</p>
										<p>
											{{:prodExtends[0].langungePairName}}
										</p>
									</li>
                                    <li class="col-md-6">
										<p class="word">订单主题：</p>
										<p>
											{{:translateName}}
										</p>
									</li>
                                 </ul>
                                 <ul>
									<li class="col-md-6">
										<p class="word">翻译级别：</p>
										<p>
                                            {{:~getTranslateLevelName(prodLevels[0].translateLevel)}} 
										</p>
									</li>
                                    <li class="col-md-6">
										<p class="word">用途：</p>
										<p>
                                            {{:prod.useCn}}
										</p>
									</li>
                                 </ul>
                                 <ul>
									<li class="col-md-6">
										<p class="word">领域：</p>
										<p>
                                            {{:prod.fieldCn}}
										</p>
									</li>
									<li class="col-md-6">
										<p class="word">订单字数：</p>
										<p>
										   	{{:prod.translateSum}}
										</p>
									</li>
                                 </ul>
                                 <ul>
									<li class="col-md-6">
										<p class="word">是否排版：</p>
										<p>
										    {{if prod.isSetType=='Y'}}
										   	                   是
										   	{{else}}
										   	                   否
										   	{{/if}} 
										</p>
									</li>
                                    <li class="col-md-6">
										<p class="word">排版费用：</p>
										<p>
										   	{{:~liToYuan3(orderFee.setTypeFee)}}
										   {{:~getMoneyUnit(orderFee.currencyUnit)}}
										</p>
									</li>
                                 </ul>
                                 <ul>
									<li class="col-md-6">
										<p class="word">格式转化：</p>
										<p>
											<%--是否进行格式转换--%>
										   	 {{if prod.typeDesc!=null && prod.typeDesc!=""}}
										   	      {{:prod.typeDesc}}
										   	  {{else}}
										   	      	无格式转化
										   	  {{/if}}
										</p>
									</li>
                                    <li class="col-md-6">
										<p class="word">转换费用：</p>
										<p>
										    {{:~liToYuan3(orderFee.descTypeFee)}}
										    {{:~getMoneyUnit(orderFee.currencyUnit)}}
										</p>
									</li>
                                 </ul>
                                 <ul>
									
									<li class="col-md-6">
										<p class="word">是否加急：</p>
										<p>
										   	{{if prod.isUrgent=='Y'}}
										   	                     是
										   	{{else}}
										   	                     否
										   	{{/if}}
										</p>
									</li>
                                    <li class="col-md-6">
										<p class="word">加急费用：</p>
										<p>
											{{:~liToYuan3(orderFee.urgentFee)}}
										    {{:~getMoneyUnit(orderFee.currencyUnit)}}
										</p>
									</li>
                                 </ul>
                                 <ul>
									<li class="col-md-6">
										<p class="word">订单总价：</p>
										<p>
										    {{:~liToYuan3(orderFee.totalFee)}}
										    {{:~getMoneyUnit(orderFee.currencyUnit)}}
										</p>
									</li>
									<li class="col-md-6">
										<p class="word">订单级别：</p>
										<p>
										   	{{:~getOrderLevelName(orderLevel)}}
										</p>
									</li>
									<li class="col-md-6">
										<p class="word">分流译员等级：</p>
										<p>
										   {{:~getInterperLevel(orderLevel)}}
										</p>
									</li>
								</ul>
							</div>
                        {{else}}
							<!-- 口译 -->
							<div id="oralTraslateDiv" class="form-label">
							   <ul>
							       <li class="col-md-12">
										<p class="word">订单主题：</p>
										<p>
										   {{:translateName}}
										</p>
									</li>
							   </ul>
							   <ul>
							       <li class="col-md-12">
										<p class="word">语言对：</p>
										<p>
										   {{for prodExtends}}
										      &nbsp;{{:langungePairName}}
										   {{/for}}
										</p>
									</li>
							   </ul>
							   <ul>
							       <li class="col-md-12">
										<p class="word">口译类型：</p>
										<p>
                                             {{for prodLevels}}
                                                  &nbsp;{{:~getTranslateLevelName(translateLevel)}} 
                                             {{/for}}
	                                    </p>
									</li>
									<li class="col-md-6">
										<p class="word">会议地址：</p>
										<p>
										  {{:prod.meetingAddress}}
										</p>
									</li>
									<li class="col-md-6">
										<p class="word">会场数量：</p>
										<p>
										   {{:prod.meetingSum}}
										</p>
									</li>
									<li class="col-md-6">
										<p class="word">译员数量：</p>
										<p>
										   {{:prod.interperSum}}
										</p>
									</li>
									<li class="col-md-6">
										<p class="word">译员性别：</p>
										<p>
										   {{:~getGenName(prod.interperGen)}}
										</p>
									</li>
									<li class="col-md-6">
										<p class="word">开始时间：</p>
										 <p>
									         {{:~timesToFmatter(prod.stateTime)}}
							             </p>
									</li>
									<li class="col-md-6">
										<p class="word">结束时间：</p>
										 <p>
										    {{:~timesToFmatter(prod.endTime)}}
							             </p>
									</li>
									<li class="col-md-6">
										<p class="word">订单总价：</p>
										<p>
										    {{:~liToYuan3(orderFee.totalFee)}}
										    {{:~getMoneyUnit(orderFee.currencyUnit)}}
										</p>
									</li>
									<li class="col-md-6">
										<p class="word">订单级别：</p>
										<p>
										   	{{:~getOrderLevelName(orderLevel)}}
										</p>
									</li>
									<li class="col-md-6">
										<p class="word">分流译员等级：</p>
										<p>
										   {{:~getInterperLevel(orderLevel)}}
										</p>
									</li>
							   </ul>
							</div>
                         {{/if}} 
              {{/if}}
                            
</script>