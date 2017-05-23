<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>添加敏感词</title>
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
										    <p class="word">敏感词*:</p>
											<p><input type="text" id="sensitiveWords" name="sensitiveWords" class="int-text int-small" ></p>
										</li>
									 </ul>
									<ul>
										<li>
										    <p class="word">替换为*:</p>
											<p><input type="text" id="replaceWords" name="replaceWords" value="***" class="int-text int-small" ></p>
										</li>
									 </ul>
									<ul>
										<li>
										    <p class="word">站点*:</p>
										    <p>		&nbsp;&nbsp;
													<input type="checkbox" id="allSite"/>全部
													<input type="checkbox" class="site" name="site" value="1"/>译云
													<input type="checkbox" class="site" name="site" value="2"/>找翻译
													<input type="checkbox" class="site" name="site" value="3"/>wap站
											</p>
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
        seajs.use('app/jsp/syssensitive/addSysSensitive', function (OrderListPager) {
            pager = new OrderListPager({element: document.body});
            pager.render();
        });
    })();
    $(function() {
		$("#allSite").click(function() {
			if(this.checked){
				$("input[name='site']").each(function(){this.checked=true;});
			}else{
				$("input[name='site']").each(function(){this.checked=false;});
			}
		});
		var $site = $("input[name='site']");
		$site.click(function(){
			$("#allSite").attr("checked",$site.length == $("input[name='site']:checked").length ? true : false);
		});
	});
</script>               
</body>
