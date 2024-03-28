var releaseTable = $('#dtReleases').DataTable();
var formChangeUser = $('#changeUserForm');
var formChangeStatus = $('#changeStatusForm');
var trackingReleaseForm = $('#trackingReleaseForm');
var rowData;
$(function() {
	loadTableRelease();
	activeItemMenu("managerWorkFlowItem",true);
	$('input[name="daterange"]').daterangepicker({
		"autoUpdateInput": false,
		"opens": 'left',
		"orientation": 'right',
		"locale": {
			"format": "DD/MM/YYYY",
			"separator": " - ",
			"applyLabel": "Aplicar",
			"cancelLabel": "Cancelar",
			"fromLabel": "Desde",
			"toLabel": "Hasta",
			"customRangeLabel": "Custom",
			"daysOfWeek": [
				"Do",
				"Lu",
				"Ma",
				"Mi",
				"Ju",
				"Vi",
				"Sa"
				],
				"monthNames": [
					"Enero",
					"Febrero",
					"Marzo",
					"Abril",
					"Mayo",
					"Junio",
					"Julio",
					"Agosto",
					"Septiembre",
					"Octubre",
					"Noviembre",
					"Deciembre"
					],
					"firstDay": 1
		}
	});

	// Datetimepicker plugin
	$('.datetimepicker').datetimepicker({
		locale: 'es',
		format: 'DD/MM/YYYY hh:mm a',
		maxDate : new Date()
	});

	formChangeStatus.find('#nodeId').change(function() {
		formChangeStatus.find('#motive').val($(this).children("option:selected").attr('data-motive'));
	});
	dropDownChange();
});

$('input[name="daterange"]').on('apply.daterangepicker', function(ev, picker) {
	$(this).val(picker.startDate.format('DD/MM/YYYY') + ' - ' + picker.endDate.format('DD/MM/YYYY'));
	releaseTable.ajax.reload();
});

$('input[name="daterange"]').on('cancel.daterangepicker', function(ev, picker) {
	$(this).val('');
	releaseTable.ajax.reload();
});

$('#tableFilters #systemId').change(function() {
	releaseTable.ajax.reload();
});

$('#tableFilters #statusId').change(function() {
	releaseTable.ajax.reload();
});

function loadTableRelease() {
	if (releaseTable != undefined) {
		releaseTable.destroy();
	}
	releaseTable = $('#dtReleases')
	.on(
			'error.dt',
			function(e, settings, techNote, message) {
				unblockUI();
				swal("Error!",
						"Se ha presentado un error en la solicitud."
						+ "\n Por favor intente de nuevo.",
				"error");
			})
			.DataTable(
					{
						"columnDefs" : [ {
							"targets" : [ 0, 1 ],
							"visible" : false,
							"searchable" : false
						} ],
						"iDisplayLength" : 10,
						"language" : {
							"emptyTable" : "No existen registros",
							"zeroRecords" : "No existen registros",
							"processing" : "Cargando",
						},
						"iDisplayStart" : 0,
						"processing" : true,
						"serverSide" : true,
						"sAjaxSource" : getCont() + "manager/wf/workFlowRelease",
						"fnServerParams": function ( aoData ) {
							aoData.push({"name": "dateRange", "value": $('#tableFilters input[name="daterange"]').val()},
									{"name": "systemId", "value": $('#tableFilters #systemId').children("option:selected").val()},
									{"name": "statusId", "value": $('#tableFilters #statusId').children("option:selected").val()});
						}, 
						"aoColumns" : [
							{
								"mDataProp" : "id"
							},
							{
								"mDataProp" : "node"
							},
							{
								"mDataProp" : "releaseNumber",
							},
							{
								"mDataProp" : "user.fullName",
								"sDefaultContent" : "admin",
							},
							{
								mRender : function(data, type, row) {
									return moment(row.createDate).format('DD/MM/YYYY h:mm:ss a');
								}
							},
							{
								"mDataProp" : "status.name",
							},
							{
								mRender : function(data, type, row) {
									let userId = $('#userInfo_Id').val();
									var options = '<div class="iconLine"> ';
									if(row.node.actors.find(element => element.id == userId)){
										options = options
										+ '<a onclick="changeStatusRelease('+row.id+')" title="Tr\u00E1mite"><i class="material-icons gris" style="font-size: 25px;">fact_check</i></a>';
									}
									options = options
									options = options
									+ '<a onclick="openReleaseTrackingModal('
									+ row.id
									+ ')" title="Rastreo"><i class="material-icons gris" style="font-size: 25px;">location_on</i> </a>';
									options = options
									+ '<a href="'
									+ getCont()
									+ 'release/summary-'
									+ row.id
									+ '" title="Resumen"><i class="material-icons gris" style="font-size: 25px;">info</i></a>'
									+ ' </div>';
									return options;
								}
							} ],
							responsive : true,
							ordering : false,
							info : true
					});
}

function confirmCancelRelease(index){
	Swal.fire({
		title: '\u00BFEst\u00e1s seguro que desea cancelar el release?',
		text: "Esta acci\u00F3n no se puede reversar.",
		icon: 'question',
		showCancelButton: true,
		customClass: 'swal-wide',
		cancelButtonText: 'Cancelar',
		cancelButtonColor: '#f14747',
		confirmButtonColor: '#3085d6',
		confirmButtonText: 'Aceptar',
	}).then((result) => {
		if(result.value){
			cancelRelease(index);
		}		
	});
}

function cancelRelease(index) {
	blockUI();
	$.ajax({
		type : "GET",
		url : getCont() + "manager/release/" + "cancelRelease",
		timeout : 60000,
		data : {
			idRelease : index
		},
		success : function(response) {
			responseCancelRelease(response);
		},
		error : function(x, t, m) {
			notifyAjaxError(x, t, m);
		}
	});
}

function responseCancelRelease(response) {
	switch (response.status) {
	case 'success':
		swal("Correcto!", "El release ha sido anulado exitosamente.",
				"success", 2000)
				releaseTable.ajax.reload();
		break;
	case 'fail':
		swal("Error!", response.exception, "error")
		break;
	case 'exception':
		swal("Error!", response.exception, "error")
		break;
	}
}
function dropDownChange(){
	
	$('#nodeId').on('change', function(){
		
		var status =$("#nodeId").find("option:selected").text();
		console.log(status);
		console.log(rowData);
	    console.log(rowData.node.edges);
	    var edges=rowData.node.edges;
	   var veriStatus=false;
		$.each(edges, function(i, value) {
			console.log(value.nodeTo.status.name+ "aca estoy si sirvo");
			if(status===value.nodeTo.label){
				if(value.nodeTo.status.name==="Error"){
					veriStatus=true;
				}else{
					veriStatus=false;
				}
			}


		});

		if(veriStatus){
			console.log("aca estoy en false");
			$('#divError').attr( "hidden",false);
		}else{
			console.log("aca estoy en true");
			$('#divError').attr( "hidden",true);
		}
		
	});
}


function changeStatusRelease(releaseId) {
	var dtReleases = $('#dtReleases').dataTable(); // tabla
	var idRow = dtReleases.fnFindCellRowIndexes(releaseId, 0); // idRow
	rowData = releaseTable.row(idRow).data();
	console.log(rowData.node);
	formChangeStatus[0].reset();
	formChangeStatus.find("#nodeId").find('option').remove();
	
	formChangeStatus.find('#nodeId').append('<option value="">-- Seleccione una opci\u00F3n --</option>' );

	let userId = $('#userInfo_Id').val();
	let allowActor = null;
	$.each(rowData.node.edges, function(i, value) {
		console.log(value);
		if(value.nodeTo.status && value.nodeTo.status !== null){
			allowActor = rowData.node.actors.find(element => element.id == userId);
			if( typeof allowActor !== 'undefined'){
				var motive=(value.nodeTo.status.motive===null)?"":value.nodeTo.status.motive;
				formChangeStatus.find('#nodeId').append('<option data-motive="'+motive+'"  value="'+value.nodeTo.id+'">'+value.nodeTo.label+'</option>' );

			}
		}
	});

	formChangeStatus.find('.selectpicker').selectpicker('refresh');
	formChangeStatus.find('#idRelease').val(rowData.id);
	formChangeStatus.find('#releaseNumber').val(rowData.releaseNumber);
	formChangeStatus.find("#nodeId_error").css("visibility", "hidden");
	//formChangeStatus.find('#motive').val('');
	formChangeStatus.find('.selectpicker').selectpicker('refresh');
	formChangeStatus.find('#idRelease').val(rowData.id);
	formChangeStatus.find(".fieldError").css("visibility", "hidden");
	formChangeStatus.find('.fieldError').removeClass('activeError');
	formChangeStatus.find('.form-line').removeClass('error');
	formChangeStatus.find('.form-line').removeClass('focused');
	$('#divError').attr( "hidden",true);
	$('#changeStatusModal').modal('show');
}

function saveChangeStatusModal(){
	if (!validStatusRelease())
		return false;
	blockUI();
	$.ajax({
		type : "POST",
		url : getCont() + "manager/wf/" + "wfStatus",
		timeout : 60000,
		data : {
			idRelease : formChangeStatus.find('#idRelease').val(),
			idNode: formChangeStatus.find('#nodeId').children("option:selected").val(),
			idError: formChangeStatus.find('#errorId').children("option:selected").val(),
			dateChange: formChangeStatus.find('#dateChange').val(),
			motive: formChangeStatus.find('#motive').val()
		},
		success : function(response) {
			responseStatusRelease(response);
		},
		error : function(x, t, m) {
			notifyAjaxError(x, t, m);
		}
	});
}

function responseStatusRelease(response) {
	switch (response.status) {
	case 'success':
		swal("Correcto!", "El release ha sido modificado exitosamente.",
				"success", 2000);
		closeChangeStatusModal();
		releaseTable.ajax.reload();
		break;
	case 'fail':
		swal("Error!", response.exception, "error")
		break;
	case 'exception':
		swal("Error!", response.exception, "error")
		break;
	}
}

function closeChangeStatusModal(){
	formChangeStatus[0].reset();
	formChangeStatus.find('#userId').selectpicker('val', '');
	$('#changeStatusModal').modal('hide');
}


function validStatusRelease() {
	let valid = true;
	formChangeStatus.find(".fieldError").css("visibility", "hidden");
	formChangeStatus.find('.fieldError').removeClass('activeError');
	formChangeStatus.find('.form-line').removeClass('error');
	formChangeStatus.find('.form-line').removeClass('focused');
	$.each(formChangeStatus.find('input[required]'), function( index, input ) {
		if($.trim(input.value) == ""){
			
			formChangeStatus.find('#'+input.id+"_error").css("visibility","visible");
			formChangeStatus.find('#'+input.id+"_error").addClass('activeError');
			formChangeStatus.find('#'+input.id+"").parent().attr("class",
			"form-line error focused");
			valid = false;
		}
	});
	$.each(formChangeStatus.find('select[required]'), function( index, select ) {
	
		if($.trim(select.value).length == 0 || select.value == ""){
			
			var statusSelected =$("#nodeId").find("option:selected").text();
			if(select.id==="errorId"&&statusSelected!=="Error"){
				valid = true;
			}else{
				formChangeStatus.find('#'+select.id+"_error").css("visibility","visible");
				formChangeStatus.find('#'+select.id+"_error").addClass('activeError');
				valid = false;
			}
		
		}
	});

	$.each(formChangeStatus.find('textarea[required]'), function( index, textarea ) {
		
		if($.trim(textarea.value).length == 0 || textarea.value == ""){
			formChangeStatus.find('#'+textarea.id+"_error").css("visibility","visible");
			formChangeStatus.find('#'+textarea.id+"_error").addClass('activeError');
			formChangeStatus.find('#'+textarea.id+"").parent().attr("class",
			"form-line error focused");
			valid = false;
		}
	});

	return valid;
}


function openReleaseTrackingModal(releaseId) {
	var dtReleases = $('#dtReleases').dataTable(); // tabla
	var idRow = dtReleases.fnFindCellRowIndexes(releaseId, 0); // idRow
	rowData = releaseTable.row(idRow).data();
	trackingReleaseForm.find('#idRelease').val(rowData.id);
	trackingReleaseForm.find('#releaseNumber').text(rowData.releaseNumber);
	$.ajax({
		type : "GET",
		url : getCont() + "release/tracking/"+ rowData.id ,
		timeout : 600000,
		data : {},
		success : function(response) {
			tracking=response.obj;
			loadTrackingRelease(tracking);
			$('#trackingReleaseModal').modal('show');
		},
		error : function(x, t, m) {
			notifyAjaxError(x, t, m);
		}
	});
	$('#trackingReleaseModal').modal('show');
}

function loadTrackingRelease(rowData){
	trackingReleaseForm.find('tbody tr').remove();
	if(rowData.tracking.length == 0){
		trackingReleaseForm.find('tbody').append('<tr><td colspan="4" style="text-align: center;">No hay movimientos</td></tr>');
	}
	$.each(rowData.tracking, function(i, value) {
		trackingReleaseForm.find('tbody').append('<tr style="padding: 10px 0px 0px 0px;" > <td><span style="background-color: '+getColorNode(value.status)+';" class="round-step"></span></td>	<td>'+value.status+'</td>	<td>'+moment(value.trackingDate).format('DD/MM/YYYY h:mm:ss a')+'</td>	<td>'+value.operator+'</td> <td>'+(value.motive && value.motive != null && value.motive != 'null' ? value.motive:'' )+'</td>	</tr>');
	});
}

function closeTrackingReleaseModal(){
	trackingReleaseForm[0].reset();
	$('#trackingReleaseModal').modal('hide');
}

function getColorNode(status){
	switch (status) {
	case 'Produccion':
		return 'rgb(0, 150, 136)';
		break;
	case 'Certificacion':
		return 'rgb(255, 152, 0)';
		break;
	case 'Solicitado':
		return 'rgb(76, 175, 80)';
		break;
	case 'Borrador':
		return 'rgb(31, 145, 243)';
		break;
	case 'Error':
		return 'rgb(255,0,0)';
		break;
	case 'Anulado':
		return 'rgb(233, 30, 99)';
		break;
	default:
		return 'rgb(0, 181, 212)';
	break;
	}
}