<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="static/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="static/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="static/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> Application -
				Computer Database </a>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<div class="label label-default pull-right"> id :${computer.id }</div>
					<h1>Edit Computer</h1>

					<form action="editComputer" method="POST">
						<input type="hidden" value="${computer.id}" id="id" />
						<!-- TODO: Change this value with the computer id -->
						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer name<span class="requis"></span></label> 
								<input type="text" class="form-control" id="name" name="name" value="${computer.name}" placeholder="name"> 
								<span class="erreur">${erreurs['name']}</span>
							</div>
							<div class="form-group">
								<label for="introduced">Introduced date</label> 
								<input	type="date" class="form-control" id="introduced" name= "introduced" value="${computer.introduced}" placeholder="Introduced date">
									<span class="erreur">${erreurs['introduced']}</span>
							</div>
							<div class="form-group">
								<label for="discontinued">Discontinued date</label> 
								<input type="date" class="form-control" id="discontinued" name="discontinued" value="${computer.discontinued}"
									placeholder="Discontinued date">
									<span class="erreur">${erreurs['discontinued']}</span>
							</div>
							<div class="form-group">
								<label for="companyId">Company</label> 
								<select
									class="form-control" id="companyId" name="companyId" >
									<option value=0 >none</option>
									<c:forEach var="company" items="${companies}">
										<option value="${company.id}" >${company.name}</option>
									</c:forEach>
								</select>
								<span class="erreur">${erreurs['id_company']}</span>
							</div>
						</fieldset>
						<div class="actions pull-right">
						<input type="hidden" class="form-control" id="computerId" name="computerId" value="${computer.id }" placeholder="computerId" >
							<input type="submit"  value="Edit" class="btn btn-primary">
							or <a href="dashboard" class="btn btn-default">Cancel</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
	<script src="static/js/validation.js"></script>
</body>
</html>