<%@include file="_meta.jsp"%>
<title>后台登录 - H-ui.admin.page v3.0</title>
<meta name="keywords" content="H-ui.admin v3.0,H-ui网站后台模版,后台模版下载,后台管理系统模版,HTML后台模版下载">
<meta name="description" content="H-ui.admin v3.0，是一款由国人开发的轻量级扁平化网站后台模板，完全免费开源的网站后台管理系统模版，适合中小型CMS后台系统。">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/h-ui.admin/css/H-ui.login.css" />
</head>
<body>
	<input type="hidden" id="TenantId" name="TenantId" value="" />
	<div class="header"></div>
	<div class="loginWraper">
		<div id="loginform" class="loginBox">
			<form class="form form-horizontal" action="index.html" method="post">
				<div class="row cl">
					<label class="form-label col-xs-2">
                        <i class="Hui-iconfont">&#xe60d;</i>
					</label>
					<div class="formControls col-xs-8">
						<input id="" name="" type="text" placeholder="账户" class="input-text size-L">
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-2"><i class="Hui-iconfont">&#xe60e;</i>
					</label>
					<div class="formControls col-xs-8">
						<input id="" name="" type="password" placeholder="密码" class="input-text size-L">
					</div>
				</div>
				<div class="row cl">
					<div class="formControls col-xs-10 col-xs-offset-2">
						<input class="input-text size-L" type="text" placeholder="验证码"
							onblur="if(this.value==''){this.value=''}"
							onclick="if(this.value=='验证码:'){this.value='';}" value=""
							style="width:150px;">&nbsp;&nbsp;
							<img id="loginCaptcha" src="captcha/getLoginCaptcha">
                            <a onclick="changeCaptcha()" href="javascript:;">看不清？换一张</a>
					</div>
				</div>
				<!-- <div class="row cl">
					<div class="formControls col-xs-8 col-xs-offset-3">
						<label for="online"> <input type="checkbox" name="online" id="online" value=""> 使我保持登录状态</label>
					</div>
				</div> -->
				<div class="row cl">
					<div class="formControls col-xs-8 col-xs-offset-2">
						<input name="" type="submit" class="btn btn-success radius size-L"
							value="&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;"> <input
							name="" type="reset" class="btn btn-default radius size-L"
							value="&nbsp;重&nbsp;&nbsp;&nbsp;&nbsp;置&nbsp;">
					</div>
				</div>
			</form>
		</div>
	</div>
	<div class="footer">Copyright ©2017 Hannaseyes|汉娜之眼--一名路过的小码农</div>
	<%@include file="_footer.jsp"%>
	<script type="text/javascript">
		function changeCaptcha() {
			var date = new Date();
			$("#loginCaptcha").attr("src", "captcha/getLoginCaptcha?date=" + date);
		}
	</script>
</body>
</html>