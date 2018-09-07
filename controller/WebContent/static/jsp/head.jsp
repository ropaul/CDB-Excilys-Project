<head>
<title><spring:message code="dashboard.title" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="static/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="static/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="static/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<%-- <div class="container">
			<a class="navbar-brand" href="dashboard"> <spring:message code="dashboard.subtitle" /></a>
		</div> --%>
		<div>
			<a class="navbar-brand" href="dashboard"> <spring:message
					code="dashboard.subtitle" /></a> <a class="navbar-brand pull-right"
				href="?lang=<spring:message code="alter_language"/>"> <img
				src="static/image/flag_<spring:message code="alter_language"/>.png"
				style="height: 50dp; width: 50dp; margin: 15dp;"
				alt=<spring:message code="alter_language"/> /></a>

			<form id="logout" action="logout" method="POST">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			</form>
			<div class="navbar-brand pull-right"
				style="margin-left: 15px; margin-top: -5px">
				<c:if test="${pageContext.request.userPrincipal.name != null}">
					<a href="javascript:document.getElementById('logout').submit()"
						class="btn btn-primary">Logout</a>
				</c:if>
			</div>
		</div>
	</header>