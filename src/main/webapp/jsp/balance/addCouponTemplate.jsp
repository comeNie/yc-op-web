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
							<div class="main-box-body clearfix">
								<!-- form表单 -->
								<div class="form-label mt-10">
								<form id="dataForm">
									<ul>
										<li>
										    <p class="word">名称*:</p>
											<p><input type="text" id="couponName" name="couponName" onblur="couponName()" class="int-text int-small" ></p><span class="cname"></span>
										</li>
									</ul>
									<ul>
										<li>
										    <p class="word">描述:</p>
											<p><input type="text" id="couponDesc" name="couponDesc" class="int-text int-small" ></p>
										</li>
									</ul>
									<ul>
										<li>
										    <p class="word">发放数量*:</p>
											<p><input type="text" id="maxCountIssue" name="maxCountIssue" class="int-text int-small" ></p>
											<p><input type="radio" id="" name="">不限</p>
										</li>
									 </ul>
									<ul>
										<li>
										    <p class="word">币种单位:</p>
										    <p>
														<select class="select select-medium" id="currencyUnit" name="currencyUnit">
															<option value="" selected="selected">全部</option>
															<!-- CNY￥ -->
															<option value="1">CNY￥</option>
															<!-- USD$ -->
															<option value="2">USD$</option>
														</select>
													</p>
										</li>
									</ul>
									<ul>
										<li>
										    <p class="word">面值*:</p>
											<p><input type="text" id="faceValue" name="faceValue" class="int-text int-small" >(￥/$)</p>
											<p><input type="radio" id="" name="">随机值<input type="text" id="" name="">~<input type="text" id="" name=""></p>
										</li>
									</ul>
									<ul>
										<li>
										    <p class="word">使用限制:</p>
										    <p>
														<select class="select select-medium" id="useLimits" name="useLimits">
															<option value="" selected="selected">不限</option>
															<!-- 笔译-文本 -->
															<option value="1">笔译-文本</option>
															<!-- 笔译附件 -->
															<option value="2">笔译附件</option>
															<!-- 口译订单 -->
															<option value="3">口译订单</option>
															<!-- 流量充值 -->
															<option value="4">流量充值</option>
														</select>
													</p>
										</li>
									</ul>
									<ul>
										<li>
										    <p class="word">使用场景*:</p>
										    <p>
													<input type="checkbox" class="usedScene" name="usedScene" value="全选"/>全选
													<input type="checkbox" class="usedScene" name="usedScene" value="译云-中文站"/>译云-中文站
													<input type="checkbox" class="usedScene" name="usedScene" value="译云-英文站"/>译云-英文站
													<input type="checkbox" class="usedScene" name="usedScene" value="百度"/>百度
													<input type="checkbox" class="usedScene" name="usedScene" value="金山"/>金山<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<input type="checkbox" class="usedScene" name="usedScene" value="wap-中文"/>wap-中文
													<input type="checkbox" class="usedScene" name="usedScene" value="wap-英文"/>wap-英文
													<input type="checkbox" class="usedScene" name="usedScene" value="找翻译"/>找翻译
													<input type="checkbox" class="usedScene" name="usedScene" value="微信助手"/>微信助手
											</p>
										</li>
									</ul>
									<ul>
										<li>
											<p class="word">使用规则*:</p>
											<p><input type="radio" id="couponUserId" name="couponUserId">满 &nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id="" name="" class="int-text int-small" >(￥/$)可使用</p>
											<p><input type="radio" id="" name="">全额抵用</p>
										</li>
									</ul>
									<ul>
										<li>
										    <p class="word">领取时间:</p>
											<p>
												<input type="text" id="receiveStartTime" name="receiveStartTime" class="int-text int-small" onfocus="WdatePicker({isShowWeek:true})" >
												~<input type="text" id="receiveEndTime" name="receiveEndTime" class="int-text int-small" onfocus="WdatePicker({isShowWeek:true})" >
											</p>
										</li>
									</ul>
									<ul>
										<li>
										    <p class="word">有效期时间*:</p>
											<p>
												<input type="text" id="effectiveStartTime" name="effectiveStartTime" class="int-text int-small" onfocus="WdatePicker({isShowWeek:true})" >
												~<input type="text" id="effectiveEndTime" name="effectiveEndTime" class="int-text int-small" onfocus="WdatePicker({isShowWeek:true})" >
											</p>
											<p><input type="radio" id="" name="">天数&nbsp;&nbsp;<input type="text" id="" name="" class="int-text int-small"></p>(0表示长期有效)
										</li>
									</ul>
									<ul>
										<li>
										    <p class="word">状态:</p>
											<p>
												<input name="status" type="radio" value="" />启用
												<input name="status" type="radio" value="" />禁用
											</p>
										</li>
									</ul>
									
									</form>
								   </div>
								   
									<!--按钮-->
									<div class="row mt-15"><!--删格化-->
									    <p class="center pr-30 mt-30">
										   <input type="button" id="save" class="biu-btn  btn-primary  btn-auto  ml-5" value="提  交">
										   <input id="add-close" type="button" class="biu-btn  btn-primary  btn-auto  ml-5 edit-close" value="取  消">
										</p>
									</div>
											</div>	
											<div class="mask" id="eject-mask"></div>	
										</div>
								</div>
                            </div>
                        </div>
                    </div>
                </div>
 <script type="text/javascript">
	(function () {
		seajs.use('my97DatePicker/WdatePicker', function () {});
	})();
 </script>  
 <script type="text/javascript">
    var pager;
    (function () {
        seajs.use('app/jsp/couponTemplate/addCouponTemplate', function (orderDetailsPager) {
            pager = new orderDetailsPager({element: document.body});
            pager.render();
        });
    })();
</script>               
</body>
