/** Declaración de variables globales del contexto * */
var $dtRequests;

$(function() {
	activeItemMenu("managerWorkFlowItem", true);
	
	initRequestTable();
});
$('#proyectFilter').change(function() {
	$dtRequests.ajax.reload();
});

$('#typeRequestFilter').change(function() {
	$dtRequests.ajax.reload();
});
let $nodeModal = $('#nodeModal');
let $nodeForm = $('#nodeModalForm');


function initRequestTable() {
	
	$dtRequests = $('#requestTable').DataTable(
			{
				
				'columnDefs' : [ {
					'visible' : false,
					'targets' : [ 0]
				} ],
				lengthMenu : [ [ 10, 25, 50, -1 ],
					[ '10', '25', '50', 'Mostrar todo' ] ],
					"iDisplayLength" : 10,
					"language" : optionLanguaje,
					"iDisplayStart" : 0,
					"processing" : true,
					"serverSide" : true,
					"sAjaxSource" : getCont() + "manager/wf/listTpos",
					"fnServerParams" : function(aoData) {
						aoData.push(
								{"name": "proyectFilter", "value": $(' #proyectFilter').children("option:selected").val()},
								{"name": "typeRequestFilter", "value": $('#typeRequestFilter').children("option:selected").val()}
						);
					},
					"aoColumns" : [
						{
							"mDataProp" : "id",
						},
						{
						
						"mDataProp" : "code_soin"
					},
					{	
						
						"mDataProp" : "code_ice"
					},
					{
						
						"mDataProp" : "description"
					},
					{
						
						"mDataProp" : "proyect.code"
					},
					{
						
						"mDataProp" : "typeRequest.code"
					}
					,
					{
						render : function(data, type, row, meta) {
							var options = '<div class="iconLineC">';
							if(row.auto){
								options += '<a onclick="changeAuto('+row.id+','+row.auto+','+row.proyect.id+')" title="Editar"><i class="material-icons gris" style="font-size: 30px;">check_circle</i></a>';
								}else{
									options += '<a onclick="changeAuto('+row.id+','+row.auto+','+row.proyect.id+')" title="Editar"><i class="material-icons gris" style="font-size: 30px;">cancel</i></a>';
								}
							
							options += ' </div>';

							return options;
						}
					},
					{
						
						"mDataProp" : "nodeName"
					}
					 ],
					ordering : false,
			});
}
function closeNode(){
	$nodeModal.modal('hide');
}
function changeAuto(index,auto,requestId) {	
	console.log(getCont() + "manager/wf/updateRequest/"+index);
	
	if(auto){
		Swal.fire({
			title: '\u00BFEst\u00e1s seguro que desea modificar el registro?',
			text: 'Esta acci\u00F3n no se puede reversar.',
			...swalDefault
		}).then((result) => {
			if(result.value){
				blockUI();
				$.ajax({
					type : "PUT",
					url : getCont() + "manager/wf/updateRequest/"+index ,
					timeout : 60000,
					data : {},
					success : function(response) {
						unblockUI();
						notifyMs(response.message, response.status)
						$dtRequests.ajax.reload();
					},
					error : function(x, t, m) {
						unblockUI();
						
					}
				});
			}
		});
	}else{
		
		$.ajax({
			type: 'GET',
			url: getCont() + "manager/wf/listNodeName/"+requestId,
			success: function(result) {
				console.log(result);
	
				if(result.length!=0){
					var s = '';
					s+='<option value="">-- Ninguno --</option>';
					for(var i = 0; i < result.length; i++) {
						s += '<option value="' + i + '">' + result[i] + '</option>';
					}
					$('#statusSkipId').html(s);
					$('#statusSkipId').prop('disabled', false);
					$('#statusSkipId').selectpicker('refresh');
					$nodeModal.modal('show');
				}else{
					swal("Error!", "No existen tramites para el proyecto de este TPO", "warning")
				}
				
				
			}
		});
		

	}

}

