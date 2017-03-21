<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>译员账单</title>
<%@include file="/inc/inc.jsp" %>
<input id="templateId" name="templateId" type="hidden" value="${templateId}"/>
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
                            	<!--table表格-->
                                <div class="table-responsive clearfix">
                                   	<!--table表格-->
                          		<div class="table-responsive clearfix mt-10">
                                    <table class="table table-hover table-border table-bordered ">
                                        <thead>
                                            <tr>
												<th>序号</th>
												<th>券码值</th>
												<th>面值(元)</th>
												<th>昵称</th>
												<th>有效期</th>
												<th>获得时间</th>
												<th>使用时间</th>
												<!-- <th></th>
												<th></th> -->
												<th>订单编号</th>
												<th>订单来源</th>
												<th>状态</th>
												<th>类型/备注</th>
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
                            </div>
                        </div>
                    </div>
                </div>
            </div>
    	</div>
   </div> 
<script id="couponDetailListTemple" type="text/template">
	<tr>
  	  <td>{{:couponId}}</td>
      <td></td>
      <td>{{:faceValue}}</td>
	  <td>{{:couponName}}</td>
 	  <td>{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', effectiveStartTime)}}-{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', effectiveEndTime)}}</td>
      <td></td>
	  <td>{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', effectiveStartTime)}}</td>
      <td></td>
	  <td></td>
	  <td>
		  {{if  status == '1'}}
		  未领取
		  {{else status == '2'}}
		  已领取  未使用
		  {{else status == '3'}}
		  已领取  已使用
		  {{/if}}
	  </td>
 	  <td>
		 {{if  remark == '1'}}
		  线下发放
		  {{else remark == '2'}}
		  用户领取
		  {{else remark == '3'}}
		  后台派送
		  {{/if}}
	  </td>
	  <td>
			<a href="javascript:void(0);" class="adopt" onclick="pager._delete('{{:templateId}}')">移除</a>
			<a href="javascript:void(0);" class="adopt" onclick="pager._detailPage('{{:templateId}}')">延长有效期</a>
	  </td>
    </tr>
</script>
  <script type="text/javascript">
	var pager;
	(function () {
		seajs.use('app/jsp/couponTemplate/couponDetail', function (OrderListPager) {
			pager = new OrderListPager({element: document.body});
			pager.render();
		});
	})();
 </script>  
</body>

</html>