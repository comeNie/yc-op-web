<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>订单详情</title>
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
					<script id="orderInfoTempl" type="text/x-jsrender">
						<!--白色背景-->
						<div class="main-box-body clearfix">
							
							{{include tmpl="#baseInfoTempl" /}}
							
                           
							{{include tmpl="#orderFeeTempl" /}}
							
						    {{include tmpl="#payInfoTempl" /}}

                            {{include tmpl="#prodInfoTempl" /}}
              
                            <div class="bc-ang mb-12">
                                  {{if mod=='edit'&&displayFlag!='52'&&displayFlag!='90'&&displayFlag!='91'&&displayFlag!='92'}}
                                   <input id="save" type="button" class="biu-btn btn-primary btn-blue btn-medium ml-10" value="保存" />
				                  {{/if}} 
                                  <input id="back" type="button" class="biu-btn  btn-yellow btn-medium ml-10" value="返回" />
				            </div>
                            
                          </div>
						</div>
                       
					</script>
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
<script id="orderStateChgTempl" type="text/x-jsrender">
  {{for orderStateChgs}}
   <tr>
     <td>{{if operName==null}}{{:operId}}{{else}}{{:operName}}{{/if}}</td>
     <td>{{:~getOrderOperName(subFlag,orgState,newState)}}</td>
     <td>{{:~timesToFmatter(stateChgTime)}}</td>
     <td>{{:~getStateName(newState)}}</td>
     <td style="text-align: left;">&nbsp;&nbsp;{{:chgDesc}}</td>
   </tr>
  {{/for}}
</script>

<%@ include file="details/baseInfo.jsp"%>
<%@ include file="details/orderFeeInfo.jsp"%>
<%@ include file="details/prodInfo.jsp"%>
<%@ include file="details/payInfo.jsp"%>
<script type="text/javascript">
    var pager;
    (function () {
        seajs.use('app/jsp/order/orderdetails', function (orderDetailsPager) {
            pager = new orderDetailsPager({element: document.body});
            pager.render();

        });
    })();
</script>
</html>