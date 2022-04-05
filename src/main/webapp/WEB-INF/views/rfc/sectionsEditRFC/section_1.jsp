<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.soin.sgrm.model.ButtonCommand"%>


<div class="row clearfix activeSection">
	<div class="col-sm-12">
		<h5 class="titulares">Detalles del cambio</h5>
	</div>
	<div class="col-lg-3 col-md-3 col-sm-12 col-xs-12">
		<label for="email_address">C&oacute;digo de RFC</label>
		<div class="form-group m-b-0i">
			<div class="form-line disabled">
				<input type="text" disabled id="rfcCode" name="rfcCode"
					value="${rfc.codeProyect}" class="form-control" placeholder="">
				<input type="hidden" id="rfcId" name="rfcId" value="${rfc.id}">
			</div>
		</div>
	</div>
	<div class="col-lg-3 col-md-3 col-sm-12 col-xs-12 p-b-20">
		<p>
			<b>Tipo Cambio</b>
		</p>
		<div class="row clearfix">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<select class="form-control show-tick" id="typeChangeId" name="typeChangeId">
					<option value="">-- Seleccione una opci&oacute;n --</option>
					<c:forEach items="${typeChange}" var="typeChange">
						<c:choose>
							<c:when test="${typeChange.id == rfc.typeChange}">
								<option selected="selected" value="${typeChange.id }">${typeChange.name }</option>
							</c:when>
							<c:otherwise>
								<option value="${typeChange.id }">${typeChange.name }</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
			</div>
			<div class="form-group p-l-15 m-b-0i">
				<label id="riskId_error" class="error fieldError" for="name"
					style="visibility: hidden;">Campo Requerido.</label>
			</div>
		</div>
	</div>
	<div class="col-lg-3 col-md-3 col-sm-12 col-xs-12 p-b-20">
		<p>
			<b>Impacto</b>
		</p>
		<div class="row clearfix">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<select class="form-control show-tick" id="impactId" name="impactId">
					<option value="">-- Seleccione una opci&oacute;n --</option>
					<c:forEach items="${impacts}" var="impact">
						<c:choose>
							<c:when test="${impact.id == rfc.impact.id}">
								<option selected="selected" value="${impact.id }">${impact.name }</option>
							</c:when>
							<c:otherwise>
								<option value="${impact.id }">${impact.name }</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
			</div>
			<div class="form-group p-l-15 m-b-0i">
				<label id="impactId_error" class="error fieldError activeError"
					for="name" style="visibility: hidden;">Campo Requerido.</label>
			</div>
		</div>
	</div>

	<div class="col-lg-3 col-md-3 col-sm-12 col-xs-12 p-b-20">
		<p>
			<b>Prioridad</b>
		</p>
		<div class="row clearfix">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<div class="form-line">
					<select class="form-control show-tick" id="priorityId"
						name="priorityId">
						<option value="">-- Seleccione una opci&oacute;n --</option>
						<c:forEach items="${priorities}" var="priority">
							<c:choose>
								<c:when test="${priority.id == release.priority}">
									<option selected="selected" value="${priority.id }">${priority.name }</option>
								</c:when>
								<c:otherwise>
									<option value="${priority.id }">${priority.name }</option>
								</c:otherwise>
							</c:choose>

						</c:forEach>
					</select>
				</div>
			</div>
			<div class="form-group p-l-15 m-b-0i">
				<label id="priorityId_error" class="error fieldError" for="name"
					style="visibility: hidden;">Campo Requerido.</label>
			</div>
		</div>
	</div>
</div>
<div class="row clearfix activeSection">
	<div class="col-sm-12">
		<h5 class="titulares">Fechas y hora propuesta para ejecutar</h5>
	</div>

	<div class='col-lg-6 col-md-6 col-sm-12 col-xs-12'>
		<label>Fecha Inicio</label>
		<div class="form-group">
			<div class="form-line disabled">
				<input required="required" type='text'
					class="form-control datetimepicker" id='dateChange' />
			</div>
			<label id="dateChange_error" class="error fieldError" for="name"
				style="visibility: hidden">Campo Requerido.</label>
		</div>
	</div>
	<div class='col-lg-6 col-md-6 col-sm-12 col-xs-12'>
		<label>Fecha</label>
		<div class="form-group">
			<div class="form-line disabled">
				<input required="required" type='text'
					class="form-control datetimepicker" id='dateChange' />
			</div>
			<label id="dateChange_error" class="error fieldError" for="name"
				style="visibility: hidden">Campo Requerido.</label>
		</div>
	</div>

	<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6" style="margin-bottom: 70px;">
		<label for="email_address">Raz&oacute;n del cambio</label>
		<div class="form-group m-b-0i">
			<div class="form-line">
				<textarea rows="2" cols="" id="installationInstructions"
					class="form-control"
					placeholder="Ingrese la raz&oacute;n del cambio..."
					style="">${release.installation_instructions}</textarea>
			</div>
			<label id="installationInstructions_error" class="error fieldError"
				for="name" style="visibility: hidden;">Campo Requerido.</label>
		</div>
	</div>
	<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
		<label for="email_address">Efecto si no se implementa el
			cambio. </label>
		<div class="form-group m-b-0i">
			<div class="form-line">
				<textarea rows="2" cols="" id="verificationInstructions"
					name="verificationInstructions" class="form-control"
					placeholder="Ingrese el efecto si no se implementa..."
					style="">${release.verification_instructions}</textarea>
			</div>
			<label id="verificationInstructions_error" class="error fieldError"
				for="name" style="visibility: hidden;">Campo Requerido.</label>
		</div>
	</div>
</div>



