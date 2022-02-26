<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<%@include file="../../plantilla/header.jsp"%>

<!-- Style Section -->
<%@include file="../../plantilla/styleSection.jsp"%>
<!-- #END# Style Section -->

</head>
<body class="theme-grey">
	<input type="hidden" id="postMSG" name="postMSG" value="${data}">
	<!-- Page Loader -->
	<%@include file="../../plantilla/pageLoader.jsp"%>
	<!-- #END# Page Loader -->

	<!-- Overlay For Sidebars -->
	<div class="overlay"></div>
	<!-- #END# Overlay For Sidebars -->

	<!-- Top Bar -->
	<%@include file="../../plantilla/admin/topbar.jsp"%>
	<!-- #Top Bar -->

	<section>
		<!-- EmailModal -->
		<%@include file="../../admin/priority/priorityModal.jsp"%>
		<!-- #END# EmailModal -->
	</section>

	<section>
		<!-- Left Sidebar -->
		<%@include file="../../plantilla/admin/leftbar.jsp"%>
		<!-- #END# Left Sidebar -->
	</section>
	<section class="content m-t-90I">
		<div class="container-fluid">
			<div class="row clearfix">
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
					<div class="" style="padding-top: -5pc;">
						<h2 class="title-Adm m-t-0">PRIORIDADES</h2>
					</div>
					<hr>
				</div>
			</div>
			<div class="row clearfix">
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
					<div class="table-responsive m-b-20">
						<table
							class="table table-bordered table-striped table-hover dataTable"
							id="priorityTable">
							<thead>
								<tr>
									<th>Nombre</th>
									<th>Descripción</th>
									<th class="actCol" style="text-align: center;">Acciones</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${prioritys}" var="priority">
									<tr id="${priority.id}">
										<td>${priority.name}</td>
										<td>${priority.description}</td>
										<td><div class="iconLineC">
												<a onclick="updatePriorityModal(${priority.id})"><i
													class="material-icons gris" style="font-size: 30px;">mode_edit</i></a>

												<a onclick="confirmDeletePriority(${priority.id})"><i
													class="material-icons gris" style="font-size: 30px;">delete</i></a>
											</div></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		<a id="buttonAddPriority" type="button"
			class="btn btn-primary btn-fab waves-effect fixedDown"
			onclick="addPriority()"> <i class="material-icons lh-1-8">add</i>
		</a>
	</section>

	<!-- Script Section -->
	<%@include file="../../plantilla/scriptSection.jsp"%>
	<!-- #END# Script Section -->

	<script src="<c:url value='/static/js/admin/priority.js'/>"></script>

</body>

</html>