<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>题库列表</title>
<%@include file="/inc/inc.jsp" %>
<input type="hidden" id="bIdUpdate"/>
</head>
<body>
   <div class="content-wrapper-iframe" ><!--右侧灰色背景-->
        <!--框架标签结束-->
      <div class="row"><!--外围框架-->
     	<div class="col-lg-12"><!--删格化-->
             <div class="row"><!--内侧框架-->
	                 <div class="col-lg-12"><!--删格化-->
	                    <div class="main-box clearfix"><!--白色背景-->
	                    	<div class="form-label">
		                    	<!--查询条件-->
		                    	<div id="selectDiv">
			                    	<ul>
										<li class="col-md-6">
											<p class="word">题库名称</p>
											<p><input class="int-text int-medium" id="qname" value="" type="text"></p>
										</li>
										<li class="col-md-6">
								           <p class="word">站点</p>
						            		<p>
							            		<select class="select select-medium" id="site">
							            			<option value="" selected="selected">全部</option>
													<!-- pc -->
													<option value="1">译云-中文站</option>
													<!-- app -->
													<option value="2">译云-英文站</option>
													<!-- app -->
													<option value="3">wap-中文</option>
													<!-- app-hd -->
													<option value="4">wap-英文</option>
													<!-- pc -->
													<option value="5">找翻译</option>
													<!-- app -->
													<option value="6">微信助手</option>
												</select>
						            		</p>
							            </li>
							            <li class="col-md-6">
								           <p class="word">状态</p>
						            		<p>
							            		<select class="select select-medium" id="state">
							            			<option value="" selected="selected">全部</option>
													<!-- 启用 -->
													<option value="0">启用</option>
													<!-- 禁用 -->
													<option value="1">停用</option>
												</select>
						            		</p>
							            </li>
							            <li class="col-md-6">
											<p class="word">创建人</p>
											<p><input class="int-text int-medium" id="createOperator" value="" type="text"></p>
										</li>
										<li class="col-md-6">
											<p class="word">语言方向</p>
											<p>
												<select class="select select-medium" id="langugePaire">
													<option value="">全部</option>
												</select>
											</p>
										</li>
										<li class="col-md-6">
								           <p class="word">题型</p>
						            		<p>
							            		<select class="select select-medium" id="questionType">
							            			<option value="" selected="selected">全部</option>
													<!-- 启用 -->
													<option value="0">单选题</option>
													<!-- 禁用 -->
													<option value="1">简答题</option>
												</select>
						            		</p>
							            </li>
			                    	</ul>
		                    	</div> 
	                    		<ul>
									<li class="width-xlag">
										<p class="word">&nbsp;&nbsp;&nbsp;</p>
										<p class="word"><input type="button" class="biu-btn  btn-primary btn-blue btn-medium ml-5"
												  id="search" value="查  询"></p>
									</li>
								</ul>
					         </div>
					   	<!--查询结束-->      
	         			</div>
	                </div>
              </div>
         </div>
     </div>	
   	  	<div class="row"><!--外围框架-->
            <div class="col-lg-12"><!--删格化-->
                <div class="row"><!--内侧框架-->
                    <div class="col-lg-12"><!--删格化-->
                        <div class="main-box clearfix"><!--白色背景-->
                        <!--标题-->
							<div class="main-box-body clearfix">
								<div class="order-list-table">
									<p class="word">
										<input type="button" class="biu-btn  btn-primary btn-blue btn-medium ml-5" id="add" value="新 增">&nbsp;&nbsp;&nbsp;&nbsp;
									</p>
								</div>
							</div>
                        <!--标题结束-->   
                            <div class="main-box-body clearfix">
                            	<!--table表格-->
                                <div class="table-responsive clearfix">
                                   	<!--table表格-->
                          		<div class="table-responsive clearfix mt-10">
                                    <table class="table table-hover table-border table-bordered ">
                                        <thead>
                                            <tr>
												<th>序号</th>
												<th>题库名称</th>
												<th>题型</th>
												<th>语言方向</th>
												<th>题目数量</th>
												<th>站点</th>
												<th>创建人</th>
												<th>创建时间</th>
												<th>状态</th>
												<th>操作</th>
											</tr>
                                        </thead>
                                         <tbody id="sysItemBankListData"></tbody>
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
										<input id="bid" name="bid" type="hidden"/>
							           		 <ul>
												<li>
												    <p class="word">题库名称*:</p>
													<p><input type="text" id="updateqname" name="qname" class="int-text int-small" ></p>
												</li>
											 </ul>
											 <ul>
												<li>
												    <p class="word">语言方向:</p>
													<p>
														<select class="select select-medium" id="updatelangugePaire" name="langDir">
														</select>
												</li>
											</ul>
											<ul>
								                <li>
								                    <p class="word">站点*:</p>
								                    <p>
														&nbsp;&nbsp;
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
															<select class="select select-medium" id="updatequestionType" name="questionType">
																<option value="0">单选题</option>
																<option value="1">简答题</option>
															</select>
													</li>
												</ul>
											  <ul>
													<li>
													    <p class="word">状态:</p>
														<p>
															<input name="state" type="radio" value="0" />启用
															<input name="state" type="radio" value="1" />停用
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
   <script type="text/javascript">
   	$("#updatequestionType").attr("disabled",true);
   	$("#updatelangugePaire").attr("disabled",true);
   </script>
<script id="sysItemBankListTemple" type="text/template">
	<tr>
  	  <td>{{:bid}}</td>
      <td>{{:qname}}</td>
	  <td>
		  {{if  questionType == '0'}}
		    单选题
		  {{else questionType == '1'}}
		    简答题
		  {{/if}}
	  </td>      
      <td>{{:langDir}}</td>
	  <td>{{:qnumber}}</td>
	  <td>
		  {{if  site == '1'}}
		     译云-中文站
		  {{else site == '2'}}
		    译云-英文站
		  {{else site == '3'}}
		  wap-中文
		  {{else site == '4'}}
		  wap-英文
		  {{else site == '5'}}
		      找翻译
		  {{else site == '6'}}
		      微信助手
		  {{/if}}
	  </td>    
	  <td>{{:aditor}}</td>
	  <td>
			{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', createTime)}}
	  </td>
	  <td>
		  {{if  state == '0'}}
		  	启用
		  {{else state == '1'}}
		  	停用
		  {{/if}}
	  </td>     
	  <td>
		  <a href="javascript:void(0);" class="adopt" onclick="pager._look('{{:bid}}','{{:questionType}}')">查看</a>
 		  <a href="javascript:void(0);" class="adopt" onclick="pager._delete('{{:bid}}')">删除</a>
		  <a href="javascript:void(0);" class="adopt" onclick="pager._show('{{:bid}}','{{:qname}}','{{:questionType}}','{{:site}}','{{:state}}')">修改</a>
	  </td>
    </tr>
</script>
 <script type="text/javascript">
	var pager;
	(function () {
		seajs.use('app/jsp/sysitembank/sysItemBankList', function (OrderListPager) {
			pager = new OrderListPager({element: document.body});
			pager.render();
		});
	})();
 </script>  
</body>
