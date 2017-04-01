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
    <form id="userForm">
    <input id="userId" name="userId" type="hidden" value="${userId}"/>
    <input id="state" name=""state"" type="hidden" />
	 <div class="row">
	 
		<!--内侧框架-->
		<div class="col-lg-12">
			<!--删格化-->
			<div class="main-box clearfix">
				<!--白色背景-->
				<div class="main-box-body clearfix">
					<div class="order-list-table">
						<ul>
							<li><a href="#" class="current">通用信息</a></li>
							<li><a href="#">成长值记录</a></li>
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
											<th>时间</th>
											<th>来源</th>
											<th>成长值</th>
											<th>总成长值</th>
											<th>备注信息</th>
										</tr>
									</thead>
									<tbody id="growthValueTable">
									  
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
<script id="userInfoTempl" type="text/x-jsrender">
	<!--白色背景-->
	<div class="main-box-body clearfix">
							
		    {{include tmpl="#baseInfoTempl" /}}

            <div class="bc-ang mb-12">
                     <input id="save" type="button" class="biu-btn btn-primary btn-blue btn-medium ml-10" value="保存" />
					<input id="cancel" type="button" class="biu-btn  btn-yellow btn-medium ml-10" value="返回" />      
			</div>
    </div>                       
</script>
<script id="growthValueTempl" type="text/x-jsrender">
	{{if usrGriwthValueList != null }}
  		{{for usrGriwthValueList  ~subFlag=subFlag}}
   			<tr>
				<td>{{:~timesToFmatter(resourceTime)}}</td>
				<td>{{:resourceDetail}}</td>
				<td>{{:griwthValue}}</td>
				<td>{{:griwthValue}}</td>
				<td>{{:griwthResource}}</td>
   			</tr>
  		{{/for}}
	{{/if}}
</script>

<script id="usrContactTempl" type="text/x-jsrender">
		{{if usrContact != null }}
				{{for usrContact  ~subFlag=subFlag}}
   					<tr>
						<td>{{:userId}}</td>
   					</tr>
  				{{/for}}
		{{/if}}
</script>



<%@ include file="baseInfo.jsp"%>
<%-- 
<%@ include file="details/orderFeeInfo.jsp"%>
<%@ include file="details/prodInfo.jsp"%>
<%@ include file="details/payInfo.jsp"%> --%>
<script type="text/javascript">
    var pager;
    (function () {
        seajs.use('app/jsp/user/userdetails', function (userDetailsPager) {
            pager = new userDetailsPager({element: document.body});
            pager.render();

        });
    })();
</script>
</html>