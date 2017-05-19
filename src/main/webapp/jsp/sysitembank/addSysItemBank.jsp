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
										    <p class="word">题库名称*:</p>
											<p><input type="text" id="qname" name="qname" class="int-text int-small" ></p>
										</li>
									 </ul>
									<ul>
										<li>
										    <p class="word">语言方向:</p>
											<p>
												<select class="select select-medium" id="langugePaire" name="langDir" onblur="langugePaire()"></select>
												<span class="check"></span>
										</li>
									</ul>
									<ul>
										<li>
										    <p class="word">站点*:</p>
										    <p>		&nbsp;&nbsp;
													<input type="radio" class="site" name="site" value="1"/>译云-中文站
													<input type="radio" class="site" name="site" value="2"/>译云-英文站
													<input type="radio" class="site" name="site" value="3"/>wap-中文<br/>&nbsp;&nbsp;
													<input type="radio" class="site" name="site" value="4"/>wap-英文
													<input type="radio" class="site" name="site" value="5"/>找翻译
													<input type="radio" class="site" name="site" value="6"/>微信助手
											</p>
										</li>
									</ul>
									 <ul>
										<li>
										    <p class="word">题型:</p>
											<p>
												<select class="select select-medium" id="questionType" name="questionType">
													<option value="0">单选题</option>
													<option value="1">简答题</option>
												</select>
										</li>
									</ul>
									<ul>
										<li>
										    <p class="word">状态:</p>
											<p>
												<input name="state" type="radio" value="0" checked="checked"/>显示
												<input name="state" type="radio" value="1" />隐藏
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
    var pager;
    (function () {
        seajs.use('app/jsp/sysitembank/addSysItemBank', function (OrderListPager) {
            pager = new OrderListPager({element: document.body});
            pager.render();
        });
    })();
</script>               
</body>
