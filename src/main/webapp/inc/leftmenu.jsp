<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script type="text/javascript">
	var pager;
	(function () {
		seajs.use('app/inc/leftmenu', function (LeftMenuPager) {
			pager = new LeftMenuPager({element: document.body});
			pager.render();
		});
	})();
</script>
<!--左侧菜单-->
<div id="nav-col">
	<section id="col-left" class="col-left-nano">
		<div id="col-left-inner" class="col-left-nano-content">
			<div class="collapse navbar-collapse navbar-ex1-collapse"
				id="sidebar-nav">
				<ul class="nav nav-pills nav-stacked">

					<li><!-- 一级菜单--> <a href="#" class="dropdown-toggle"> <i
							class="fa fa-inbox"></i><span>订单管理</span> <i
							class="fa fa-chevron-circle-right drop-icon"></i>
					</a> <!-- 一级菜单结束--> <!--二级菜单-->
						<ul class="submenu">
							<li menuAttr="menu"><a href="${_base}/order/toOrderList" target="mainFrame">全部订单</a></li>
							<li menuAttr="menu"><a href="${_base}/toCancelOrderList" target="mainFrame">已取消订单</a></li>
							<li menuAttr="menu"><a href="${_base}/toWaitPriceOrderList" target="mainFrame">待报价订单</a></li>
						    <li menuAttr="menu"><a href="${_base}/toWaitPayOrderList" target="mainFrame">待支付订单</a></li>
						    <li menuAttr="menu"><a href="${_base}/toUnclaimOrderList" target="mainFrame">待领取订单</a></li>
						     <li menuAttr="menu"><a href="${_base}/toTranslateOrderList" target="mainFrame">翻译中订单</a></li>
						     <li menuAttr="menu"><a href="${_base}/order/toReviewOrderList" target="mainFrame">待审核订单</a></li>
						     <li menuAttr="menu"><a href="${_base}/toTbcOrderList" target="mainFrame">待确认订单</a></li>
						     <!-- <li menuAttr="menu"><a href="${_base}/toUpdateOrderList" target="mainFrame">修改中订单</a></li> -->
						     <li menuAttr="menu"><a href="${_base}/toDoneOrderList" target="mainFrame">已完成订单</a></li>
						     <li menuAttr="menu"><a href="${_base}/toEvaluateOrderList" target="mainFrame">已评价订单</a></li>
						      <li menuAttr="menu"><a href="${_base}/toRefundOrderList" target="mainFrame">已退款订单</a></li>
						</ul> <!--二级菜单结束--></li>
					<li><!-- 一级菜单--> <a href="#" class="dropdown-toggle"> <i
							class="fa fa-inbox"></i><span>账务资产</span> <i
							class="fa fa-chevron-circle-right drop-icon"></i>
					</a> <!-- 一级菜单结束--> <!--二级菜单-->
						<ul class="submenu">
							<li menuAttr="menu"><a href="${_base}/balance/toTranslatorBillList" target="mainFrame">译员账单</a></li>
							<%--<li menuAttr="menu"><a href="${_base}/toCancelOrderList" target="mainFrame">已取消订单</a></li>
							<li menuAttr="menu"><a href="${_base}/toWaitPriceOrderList" target="mainFrame">待报价订单</a></li>
							<li menuAttr="menu"><a href="${_base}/toWaitPayOrderList" target="mainFrame">待支付订单</a></li>
							<li menuAttr="menu"><a href="${_base}/toUnclaimOrderList" target="mainFrame">待领取订单</a></li>
							<li menuAttr="menu"><a href="${_base}/toTranslateOrderList" target="mainFrame">翻译中订单</a></li>
							<li menuAttr="menu"><a href="${_base}/order/toReviewOrderList" target="mainFrame">待审核订单</a></li>
							<li menuAttr="menu"><a href="${_base}/toTbcOrderList" target="mainFrame">待确认订单</a></li>
							<!-- <li menuAttr="menu"><a href="${_base}/toUpdateOrderList" target="mainFrame">修改中订单</a></li> -->
							<li menuAttr="menu"><a href="${_base}/toDoneOrderList" target="mainFrame">已完成订单</a></li>--%>
						</ul> <!--二级菜单结束--></li>

				</ul>
			</div>
		</div>
	</section>
</div>
<!--/左侧菜单结束-->
