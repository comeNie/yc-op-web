<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>添加题库</title>
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
										    <p class="word">昵称*:</p>
											<p>
												<input list="browsers" style="border: 1px solid black;">
												<datalist id="browsers">
												  <option value="Internet Explorer">
												  <option value="Firefox">
												  <option value="Chrome">
												  <option value="Opera">
												  <option value="Safari">
												</datalist>
											</p>
										</li>
									 </ul>
									 <ul>
										<li>
										    <p class="word">姓名*:</p>
											<p><input type="text" id="" name="" class="int-text int-small" ></p>
										</li>
									 </ul>
									 <ul>
										<li>
										    <p class="word">手机*:</p>
											<p><input type="text" id="" name="" class="int-text int-small" ></p>
										</li>
									 </ul>
									 <ul>
										<li>
										    <p class="word">邮箱*:</p>
											<p><input type="text" id="" name="" class="int-text int-small" ></p>
										</li>
									 </ul>
									 <ul>
										<li>
										    <p class="word">类型:</p>
											<p>
												<input name="" type="radio" value="0"/>充值
												<input name="" type="radio" value="1" />提现
											</p>
										</li>
									</ul>
									 <ul>
										<li>
										    <p class="word">方式:</p>
											<p>
												<select class="select select-medium" id="" name="">
													<option value="0">支付宝</option>
													<option value="1">微信</option>
													<option value="2">银行汇款/转账</option>
													<option value="3">PayPal</option>
												</select>
											</p>
										</li>
									</ul>
									<ul>
										<li>
										    <p class="word">账号*:</p>
											<p><input type="text" id="" name="" class="int-text int-small" ></p>
										</li>
									 </ul>
									 <ul>
										<li>
										    <p class="word">金额:</p>
											<p>
												<select class="select select-medium" id="" name="">
													<option value="0">CNY￥</option>
													<option value="1">USD$</option>
												</select>
											</p>
											<p><input type="text" id="" name="" class="int-text int-small" ></p>
										</li>
									</ul>
									<ul>
										<li>
										    <p class="word">申请备注:</p>
											<p> <textarea name="" cols="30" rows="4" style="border: 1px solid black;"></textarea></p>
										</li>
									 </ul>
									 <ul>
										<li>
										    <p class="word">审核备注:</p>
											<p> <textarea name="" cols="30" rows="4" style="border: 1px solid black;"></textarea></p>
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
    var pager;
    (function () {
        seajs.use('app/jsp/translatorBill/addRechargeWithdrawals', function (OrderListPager) {
            pager = new OrderListPager({element: document.body});
            pager.render();
        });
    })();
</script>               
</body>
