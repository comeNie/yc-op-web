<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>题目列表</title>
<%@include file="/inc/inc.jsp" %>
<input id="bid" name="bid" type="hidden" value="${bid}"/>
<input id="questionType" name="questionType" type="hidden" value="${questionType}"/>
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
								<div class="order-list-table">
									<p class="word"><input type="button" class="biu-btn  btn-primary btn-blue btn-medium ml-5"
												  id="add" value="新 增"></p>
								</div>
							</div>
							<div class="main-box-body clearfix">
                            	<!--table表格-->
                                <div class="table-responsive clearfix">
                                   	<!--table表格-->
                          		<div class="table-responsive clearfix mt-10">
                                    <table class="table table-hover table-border table-bordered ">
                                        <thead>
                                            <tr>
												<th>序号</th>
												<th>原文</th>
												<th>译文</th>
												<th>创建人</th>
												<th>创建时间</th>
												<th>状态</th>
												<th>操作</th>
											</tr>
                                        </thead>
                                         <tbody id="orderListData"></tbody>
                                    </table>
                                    <div id="showMessage"></div>
                                </div>
                           		<!--/table表格结束-->
                                </div>
                                <!--分页-->
								 <div>
					 				 <nav style="text-align: center">
										<ul id="pagination"></ul>
									</nav>
								  </div>
								<!--分页结束-->
								<form id="dataForm" method="post">
                            	<div class="eject-big">
									<div class="eject-medium" id="add-samll" style="overflow:auto;width: 70%; left: 40%; top: 35%;height:500px;">
										<div class="eject-medium-title">
											<p>修改</p>
											<p class="img" id="colseImage"><i class="fa fa-times" ></i></p>
										</div>
										<div class="form-label mt-10">
							           		 <ul>
												<li>
													<input id="bid" name="bid" type="hidden" value="${bid}"/>
													<input id="questionType" name="qtype" type="hidden" value="${questionType}"/>
													<input id="qid" name="qid" type="hidden"/>
												    <p class="word">原文*:</p>
													<p> <textarea id="original" name="original" cols="50" rows="4" style="border: 1px solid black;"></textarea></p>
												</li>
											 </ul>
											 <ul>
												<li>
												    <p class="word">译文*:</p>
													<p> <textarea id="translation" name="translation" cols="50" rows="4" style="border: 1px solid black;"></textarea></p>
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
									    </div>	
										<!--按钮-->
										<div class="row mt-15"><!--删格化-->
								               <p class="center pr-30 mt-30">
								                   <input type="button" id="update" class="biu-btn  btn-primary  btn-auto  ml-5" value="提  交">
								                   <input id="add-close" type="button" class="biu-btn  btn-primary  btn-auto  ml-5 edit-close" value="取  消">
								                </p>
								        </div>
									</div>	
									<div class="mask" id="eject-mask"></div>	
								</div>
                            </form>
                            <!-- 弹出框 end-->
                            </div>
                        </div>
                    </div>
                </div>
            </div>
    	</div>
   </div> 
<script id="questionsListTemple" type="text/template">
	<tr>
  	  <td>{{:qid}}</td>
      <td>{{:original}}</td>
      <td>{{:translation}}</td>
	  <td>{{:createOperator}}</td>
	  <td>{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', createTime)}}</td>
	  <td>
		  {{if  status == '0'}}
		  	显示
		  {{else status == '1'}}
		  	隐藏
		  {{/if}}
	  </td>    
	  <td>
		  <a href="javascript:void(0);" class="adopt" onclick="pager._look()">查看</a>
 		  <a href="javascript:void(0);" class="adopt" onclick="pager._delete('{{:qid}}')">删除</a>
		  <a href="javascript:void(0);" class="adopt" onclick="pager._show('{{:qid}}','{{:original}}','{{:translation}}','{{:status}}')">修改</a>
	  </td>
    </tr>
</script> 
  <script type="text/javascript">
	var pager;
	(function () {
		seajs.use('app/jsp/sysitembank/sysQuestionsList', function (OrderListPager) {
			pager = new OrderListPager({element: document.body});
			pager.render();
		});
	})();
 </script>  
</body>

</html>