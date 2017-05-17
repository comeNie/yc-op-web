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
											<input id="bid" name="bid" type="hidden" value="${bid}"/>
											<input id="questionType" name="qtype" type="hidden" value="${questionType}"/>
										    <p class="word">标题*:</p>
											<p><input type="text" id="choiceQuestion" name="choiceQuestion" class="int-text int-small" ></p>
										</li>
									 </ul>
									 <ul>
										<li>
										    <p class="word">选项A*:</p>
											<p><input type="text" id="optiona" name="optiona" class="int-text int-small" ></p>
										</li>
									 </ul>
									 <ul>
										<li>
										    <p class="word">选项B*:</p>
											<p><input type="text" id="optionb" name="optionb" class="int-text int-small" ></p>
										</li>
									 </ul>
									 <ul>
										<li>
										    <p class="word">选项C*:</p>
											<p><input type="text" id="optionc" name="optionc" class="int-text int-small" ></p>
										</li>
									 </ul>
									 <ul>
										<li>
										    <p class="word">选项D*:</p>
											<p><input type="text" id="optiond" name="optiond" class="int-text int-small" ></p>
										</li>
									 </ul>
									 <ul>
										<li>
										    <p class="word">正确答案:</p>
											<p>
												<select class="select select-medium" id="answer" name="answer">
													<option value="A">A</option>
													<option value="B">B</option>
													<option value="C">C</option>
													<option value="D">D</option>
												</select>
										</li>
									</ul>
									<ul>
										<li>
										    <p class="word">状态:</p>
											<p>
												<input name="status" type="radio" value="0" />显示
												<input name="status" type="radio" value="1" />隐藏
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
        seajs.use('app/jsp/sysitembank/addSysQuestionsChoice', function (OrderListPager) {
            pager = new OrderListPager({element: document.body});
            pager.render();
        });
    })();
</script>               
</body>
