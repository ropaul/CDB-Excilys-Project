<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="static/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="static/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="static/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div>
			<a class="navbar-brand" href="dashboard"> <spring:message
					code="dashboard.subtitle" /></a> <a class="navbar-brand pull-right"
				href="?lang=<spring:message code="alter_language"/>"> <img
				src="static/image/flag_<spring:message code="alter_language"/>.png"
				style="height: 50dp; width: 50dp; margin: 15dp;"
				alt=<spring:message code="alter_language"/> /></a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<c:if test="${param.error!=null}">
						<p class="form-control" style="background: #fba;">${param.error}</p>
					</c:if>
					<h1>
						<spring:message code="login" />
					</h1>
					<form action="login" method="POST">
						<fieldset>
							<div class="form-group">
								<label for="userName"><spring:message code="userName" /></label>
								<input type="text" class="form-control" id="username"
									name="username" placeholder="<spring:message code="userName"/>">
							</div>
							<div id="userNameError" style="display: none;">
								<p>
									<spring:message code="error.userName" />
								</p>
							</div>
							<div class="form-group">
								<label for="password"><spring:message code="password" /></label>
								<input type="password" class="form-control" id="password"
									name="password" placeholder="<spring:message code="password"/>">
							</div>
							<div id="passwordError" style="display: none;">
								<p>
									<spring:message code="error.password" />
								</p>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="<spring:message code="login"/>"
								class="btn btn-primary">
						</div>
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
					</form>
				</div>
			</div>
		</div>
	</section>

	<script src="static/js/jquery.min.js"></script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/dashboard.js"></script>
	<script src="static/js/validation.js"></script>

</body>
</html>