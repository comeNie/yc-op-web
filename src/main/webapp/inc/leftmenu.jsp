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
							<li menuAttr="menu"><a href="${_base}/order/toOrderList" target="mainFrame">订单列表</a></li>
						    <li menuAttr="menu"><a href="${_base}/order/orderdetails?orderId=2000000016140885" target="mainFrame">订单详情</a></li>
						</ul> <!--二级菜单结束--></li>

				</ul>
			</div>
		</div>
	</section>
</div>
<!--/左侧菜单结束-->