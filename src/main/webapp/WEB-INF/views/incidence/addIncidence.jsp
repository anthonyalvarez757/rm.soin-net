
<div id="addIncidenceSection">
	<form id="fmIncidence" action="changePassword" method="post">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" /> <input type="hidden" id="idRFC"
			value="" />
			
		<div class="m-l-15">

			<div class=" row clearfix ">
				<div class="col-lg-3 col-md-4 col-sm-12 col-xs-12">
					<label>Tipo de ticket </label>
					<div class="form-group m-b-0">
						<select id="tId" name="tId"
							class="form-control show-tick selectpicker"
							data-live-search="true">
							<option value="">-- Seleccione una opci&oacute;n --</option>
							<c:forEach items="${typeincidences}" var="typeIncidence">
								<option value="${typeIncidence.id }">${typeIncidence.code }</option>
							</c:forEach>
							</select>
							
					</div>

				</div>
				<div class="col-lg-3 col-md-4 col-sm-12 col-xs-12">
					<label>Breve descripci&oacute;n del ticket</label>
					<div class="form-group m-b-0">
						<input type="text" class="form-control" id="title" maxlength="50"
							name="title" placeholder="Escriba un texto corto"
							style="height: 60px; padding-bottom: 20px;">
						<div class="help-info">M�x. 50 caracteres</div>
					</div>

				</div>
			</div>

		</div>
	</form>

	<div class="button-demo flr">
		<button type="button" class="btn btn-default"
			onclick="closeIncidenceSection()">CANCELAR</button>
		<button id="createIncidence" type="button" 
			class="btn btn-primary waves-effect" onclick="createIncidence()">CREAR TICKET</button>
	</div>

</div>