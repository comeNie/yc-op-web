<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>已评论订单详情</title>
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
							<li><a href="#" class="current">订单信息</a></li>
							<li><a href="#">订单日志</a></li>
						</ul>
					</div>
				</div>
				<!--选项卡1-->
				<div id="date1">
					
				</div>
				<!--选项卡2-->
				<div id="date2" style="display: none;">
				   <!--白色背景-->
					<div class="main-box-body clearfix">
		              <div class="table-responsive clearfix">
						<table class="table table-hover table-border table-bordered">
									<thead>
										<tr>
											<th>操作者</th>
											<th>操作名称</th>
											<th>操作时间</th>
											<th>订单状态</th>
											<th>备注</th>
										</tr>
									</thead>
									<tbody id="orderStateChgTable">
									  
									</tbody>
						 </table>
					  </div>	
				  </div>	
						    
				</div>
			</div>
			
		</div>
	</div>	
	
   </form>
</body>
<script id="orderInfoTempl" type="text/x-jsrender">
	<!--白色背景-->
    <input id="state" type="hidden" value="{{:state}}"/>
	<div class="main-box-body clearfix">
                  <div class="form-label">
								<ul>
									<li class="col-md-6">
										<p class="word">订单编号：</p>
										<p>{{:orderId}}</p>
									</li>
									<li class="col-md-6">
										<p class="word">订单来源：</p>
										<p>{{:~getChlIdName(chlId)}}</p>
									</li>
									<li class="col-md-6">
										<p class="word">订单级别：</p>
										<p>{{:~getOrderLevelName(orderLevel)}}</p>
									</li>
									<li class="col-md-6">
										<p class="word">订单类型：</p>
										<p>{{:~getTranslateTypeName(translateType)}}</p>
									</li>
									<li class="col-md-6">
										<p class="word">会员昵称：</p>
										<p>{{:usernick}}</p>
									</li>
                                    <li class="col-md-6">
										<p class="word">时区：</p>
										<p>{{:timeZone}}</p>
									</li>
									<li class="col-md-12">
										<p class="word">语种方向：</p>
										<p>
										   {{for prodExtends}}
										      {{:langungePairName}}
										   {{/for}}
										</p>
									</li>
									<li class="col-md-6">
										<p class="word">评论时间：</p>
										<p>{{:~timesToFmatter(orderTime)}}</p>
									</li>
									<li class="col-md-6">
										<p class="word">译员昵称：</p>
										<p>{{:interperName}}</p>
									</li>
                                    <li class="col-md-6">
										<p class="word">译员级别：</p>
										<p>{{:interperLevel}}</p>
									</li>
									<li class="col-md-6">
										<p class="word">lsp名称：</p>
										<p>{{:lspName}}</p>
									</li>
							 </ul>
							<ul>
								<li class="col-md-6">
									<p class="word">服务质量：</p>
									{{if evaluateVo.serveQuality==8)}}										  
										<p>
                                			<span><img class="star" name="star1" src="${uedroot}/images/xx-01.jpg"></span>
                            			</p>
									{{else evaluateVo.serveQuality==16}}
										<p>
                                			<span><img class="star" name="star1" src="${uedroot}/images/xx-01.jpg"></span>
                                			<span><img class="star" name="star2" src="${uedroot}/images/xx-01.jpg"></span>
                            			</p>
									{{else evaluateVo.serveQuality==24}}
										<p>
                                			<span><img class="star" name="star1" src="${uedroot}/images/xx-01.jpg"></span>
                                			<span><img class="star" name="star2" src="${uedroot}/images/xx-01.jpg"></span>
                                			<span><img class="star" name="star3" src="${uedroot}/images/xx-01.jpg"></span>
                            			</p>
									 {{else evaluateVo.serveQuality==32}}
										<p>
                                			<span><img class="star" name="star1" src="${uedroot}/images/xx-01.jpg"></span>
                                			<span><img class="star" name="star2" src="${uedroot}/images/xx-01.jpg"></span>
                                			<span><img class="star" name="star3" src="${uedroot}/images/xx-01.jpg"></span>
                                			<span><img class="star" name="star4" src="${uedroot}/images/xx-01.jpg"></span>
                            			</p>
									{{else evaluateVo.serveQuality==40}}
										<p>
                                			<span><img class="star" name="star1" src="${uedroot}/images/xx-01.jpg"></span>
                                			<span><img class="star" name="star2" src="${uedroot}/images/xx-01.jpg"></span>
                                			<span><img class="star" name="star3" src="${uedroot}/images/xx-01.jpg"></span>
                                			<span><img class="star" name="star4" src="${uedroot}/images/xx-01.jpg"></span>
                                			<span><img class="star" name="star5" src="${uedroot}/images/xx-01.jpg"></span>
                            			</p>
									{{/if}}
								</li>	
							</ul>
							<ul>
								<li class="col-md-6">
									<p class="word">服务速度：</p>
									{{if evaluateVo.serveSpeed==6)}}										  
										<p>
                                			<span><img class="star" name="star1" src="${uedroot}/images/xx-01.jpg"></span>
                            			</p>
									{{else evaluateVo.serveSpeed==12}}
										<p>
                                			<span><img class="star" name="star1" src="${uedroot}/images/xx-01.jpg"></span>
                                			<span><img class="star" name="star2" src="${uedroot}/images/xx-01.jpg"></span>
                            			</p>
									{{else evaluateVo.serveSpeed==18}}
										<p>
                                			<span><img class="star" name="star1" src="${uedroot}/images/xx-01.jpg"></span>
                                			<span><img class="star" name="star2" src="${uedroot}/images/xx-01.jpg"></span>
                                			<span><img class="star" name="star3" src="${uedroot}/images/xx-01.jpg"></span>
                            			</p>
									 {{else evaluateVo.serveSpeed==24}}
										<p>
                                			<span><img class="star" name="star1" src="${uedroot}/images/xx-01.jpg"></span>
                                			<span><img class="star" name="star2" src="${uedroot}/images/xx-01.jpg"></span>
                                			<span><img class="star" name="star3" src="${uedroot}/images/xx-01.jpg"></span>
                                			<span><img class="star" name="star4" src="${uedroot}/images/xx-01.jpg"></span>
                            			</p>
									{{else evaluateVo.serveSpeed==30}}
										<p>
                                			<span><img class="star" name="star1" src="${uedroot}/images/xx-01.jpg"></span>
                                			<span><img class="star" name="star2" src="${uedroot}/images/xx-01.jpg"></span>
                                			<span><img class="star" name="star3" src="${uedroot}/images/xx-01.jpg"></span>
                                			<span><img class="star" name="star4" src="${uedroot}/images/xx-01.jpg"></span>
                                			<span><img class="star" name="star5" src="${uedroot}/images/xx-01.jpg"></span>
                            			</p>
									{{/if}}
								</li>	
							</ul>
							<ul>
								<li class="col-md-6">
									<p class="word">服务态度：</p>
									{{if evaluateVo.serveManner==6)}}										  
										<p>
                                			<span><img class="star" name="star1" src="${uedroot}/images/xx-01.jpg"></span>
                            			</p>
									{{else evaluateVo.serveManner==12}}
										<p>
                                			<span><img class="star" name="star1" src="${uedroot}/images/xx-01.jpg"></span>
                                			<span><img class="star" name="star2" src="${uedroot}/images/xx-01.jpg"></span>
                            			</p>
									{{else evaluateVo.serveManner==18}}
										<p >
                                			<span><img class="star" name="star1" src="${uedroot}/images/xx-01.jpg"></span>
                                			<span><img class="star" name="star2" src="${uedroot}/images/xx-01.jpg"></span>
                                			<span><img class="star" name="star3" src="${uedroot}/images/xx-01.jpg"></span>
                            			</p>
									 {{else evaluateVo.serveManner==24}}
										<p>
                                			<span><img class="star" name="star1" src="${uedroot}/images/xx-01.jpg"></span>
                                			<span><img class="star" name="star2" src="${uedroot}/images/xx-01.jpg"></span>
                                			<span><img class="star" name="star3" src="${uedroot}/images/xx-01.jpg"></span>
                                			<span><img class="star" name="star4" src="${uedroot}/images/xx-01.jpg"></span>
                            			</p>
									{{else evaluateVo.serveManner==30}}
										<p>
                                			<span><img class="star" name="star1" src="${uedroot}/images/xx-01.jpg"></span>
                                			<span><img class="star" name="star2" src="${uedroot}/images/xx-01.jpg"></span>
                                			<span><img class="star" name="star3" src="${uedroot}/images/xx-01.jpg"></span>
                                			<span><img class="star" name="star4" src="${uedroot}/images/xx-01.jpg"></span>
                                			<span><img class="star" name="star5" src="${uedroot}/images/xx-01.jpg"></span>
                            			</p>
									{{/if}}
								</li>	
							</ul>
							<ul>
									<li class="col-md-6">
										<p class="word">内容：</p>
										<p>{{:evaluateVo.evaluateContent}}</p>
									</li>
							</ul>
							<ul>
								<li class="word">状态:</li>
								<li>
									<input type="radio" id="showId" name="demo-radio1" value="1">
	    							<label for="radio-1" calss="ml-10">显示</label>
	    							<input type="radio" id="hiddenId" name="demo-radio1" value="0">
									<label for="radio-1">隐藏</label>
 								</li>
							</ul>
							<ul>
								<li class="col-md-6">
										<p class="word">备注：</p>
										<p>
										<textarea class="int-text" maxlength="100" id="remark">
											{{:evaluateVo.remark}}
										</textarea>
										</p>
									</li>
							</ul>
						</div>
						 <div class="bc-ang mb-12">
            	 			<input id="save" type="button" class="biu-btn btn-primary btn-blue btn-medium ml-10" value="提交" />
							<input id="cancel" type="button" class="biu-btn  btn-yellow btn-medium ml-10" value="返回" />      
						</div>
				</script>				
    </div>                       
</script>
<script id="orderStateChgTempl" type="text/x-jsrender">
  {{for orderStateChgs  ~subFlag=subFlag}}
   <tr>
     <td>
		{{if operId=='1000011' }}
			系统操作
		{{else  operName==null || operName=="" && operId!='1000011'}}
			{{:operId}}
		{{else}}
			{{:operName}}
		{{/if}}
	</td>
     <td>{{:~getOrderOperName(~subFlag,orgState,newState)}} </td>
     <td>{{:~timesToFmatter(stateChgTime)}}</td>
     <td>{{:~getStateName(newState)}}</td>
     <td style="text-align: left;">&nbsp;&nbsp;{{:chgDesc}} </td>
   </tr>
  {{/for}}
</script>
<script type="text/javascript">
    var pager;
    (function () {
        seajs.use('app/jsp/order/evaluateOrderDetail', function (evaluteOrderDetailsPager) {
            pager = new evaluteOrderDetailsPager({element: document.body});
            pager.render();

        });
    })();
</script>
</html>