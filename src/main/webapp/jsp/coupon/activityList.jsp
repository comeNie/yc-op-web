<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>活动策略</title>
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
	                    	<div class="form-label">
		                    	<!--查询条件-->
		                    	<div id="selectDiv">
			                    	<ul>
										<li class="col-md-6">
											<p class="word">活动名称</p>
											<p><input class="int-text int-medium" id="" value="" type="text"></p>
										</li>
										<li class="col-md-6">
											<p class="word">活动时间</p>
											<p>
												<input type="text" id="" name="" class="int-text int-small" onfocus="WdatePicker({isShowWeek:true})" >
												~<input type="text" id="" name="" class="int-text int-small" onfocus="WdatePicker({isShowWeek:true})" >										
			                    			</p>
			                    		<li class="col-md-6">
								           <p class="word">使用场景</p>
						            		<p>
							            		<select class="select select-medium" id="usedScene">
							            			<option value="" selected="selected">全部</option>
													<!-- pc -->
													<option value="1">译 云-中文站</option>
													<!-- app -->
													<option value="2">译云-英文站</option>
													<!-- app-hd -->
													<option value="3">百度</option>
													<!-- pc -->
													<option value="4">金山</option>
													<!-- app -->
													<option value="5">wap-中文</option>
													<!-- app-hd -->
													<option value="6">wap-英文</option>
													<!-- pc -->
													<option value="7">找翻译</option>
													<!-- app -->
													<option value="8">微信助手</option>
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
										<p><input type="button" class="biu-btn  btn-primary btn-blue btn-medium ml-5"
												   id="export" value="导  出"></p>
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
									<p class="word"><input type="button" class="biu-btn  btn-primary btn-blue btn-medium ml-5"
												  id="add" value="新 增"></p>
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
												<th>活动名称</th>
												<th>金额</th>
												<th>活动时间</th>
												<th>领取时间</th>
												<th>使用场景</th>
												<th>创建人</th>
												<th>创建时间</th>
												<th>最近修改时间</th>
												<th>状态</th>
												<th>操作</th>
											</tr>
                                        </thead>
                                         <tbody id="couponTemplateListData"></tbody>
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
                            </div>
                            <!-- 弹出框 start-->
                            <form id="dataForm" method="post">
                            	<div class="eject-big">
									<div class="eject-medium" id="add-samll">
										<div class="form-label mt-10">
										<ul>
											<li>手动发放:</li>
										</ul>
										<div>
													<p align="center"><input type="radio" id="allVip">全部会员
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<input type="radio" id="specifyVip">指定会员</p>
										</div>
									    </div>	
										<!--按钮-->
										<div class="row mt-15"><!--删格化-->
								               <p class="center pr-30 mt-30">
								                   <input type="button" id="addCouponVip" class="biu-btn  btn-primary  btn-auto  ml-5" value="确  认">
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
<script id="couponTemplateListTemple" type="text/template">
	<tr>
  	  <td>{{:}}</td>
      <td>{{:}}</td>
      <td>{{:}}</td>
 	  <td>{{:}}</td>
	  <td>
		  {{if  usedScene == '1'}}
		     译云-中文站
		  {{else usedScene == '2'}}
		    译云-英文站
		  {{else usedScene == '3'}}
		     百度
		  {{else usedScene == '4'}}
		     金山
		  {{else usedScene == '5'}}
		  wap-中文
		  {{else usedScene == '6'}}
		  wap-英文
		  {{else usedScene == '7'}}
		      找翻译
		  {{else usedScene == '8'}}
		      微信助手
		  {{/if}}
	  </td>      
	  <td>{{:}}</td>
	  <td>
			{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', createTime)}}
	  </td>
	  <td>
			{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', )}}
	  </td>
	  <td>
		  {{if  status == '1'}}
		  	启用
		  {{else status == '2'}}
		  	禁用
		  {{/if}}
	  </td>     


	  <td>
		  <a href="javascript:void(0);" class="adopt" onclick="pager._detailPage('{{:templateId}}')">查看</a>
 		  <a href="javascript:void(0);" class="adopt" onclick="pager._manualSend('{{:templateId}}')">修改</a>
 		  <a href="javascript:void(0);" class="adopt" onclick="pager._delete('{{:templateId}}')">删除</a>
	  </td>
    </tr>
</script>
 <script type="text/javascript">
	(function () {
		seajs.use('my97DatePicker/WdatePicker', function () {});
	})();
 </script>  
 <script type="text/javascript">
	var pager;
	(function () {
		seajs.use('app/jsp/activity/activityList', function (OrderListPager) {
			pager = new OrderListPager({element: document.body});
			pager.render();
		});
	})();
 </script>  
</body>
