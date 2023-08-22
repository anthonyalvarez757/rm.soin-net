package com.soin.sgrm.model.pos;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;

@SuppressWarnings("serial")
@Entity
@Table(name = "RELEASES_RELEASE")
public class PReleaseSummary implements Serializable {

	@Id
	@Column(name = "ID")
	private int id;

	@Column(name = "NUMERO_RELEASE")
	private String releaseNumber;

	@Column(name = "DESCRIPCION")
	private String description;

	@Column(name = "OBSERVACIONES")
	private String observations;

	// Tipos de reportes
	@Column(name = "TIENE_ARTE")
	private Boolean reportHaveArt;

	@Column(name = "REPORTE_TELEFONIA_FIJA")
	private Boolean reportfixedTelephony;

	@Column(name = "REPORTE_TABLAS_HISTORICAS")
	private Boolean reportHistoryTables;

	@Column(name = "SIN_ARTE")
	private Boolean reportNotHaveArt;

	@Column(name = "REPORTE_TELEFONIA_MOVIL")
	private Boolean reportMobileTelephony;

	@Column(name = "REPORTE_TABLAS_TEMPORALES")
	private Boolean reportTemporaryTables;

	@Column(name = "LLAMADAS_FACTURADAS")
	private Boolean billedCalls;

	@Column(name = "LLAMADAS_POR_FACTURAR")
	private Boolean notBilledCalls;

	@Column(name = "SOLUCION_FUNCIONAL")
	private String functionalSolution;

	@Column(name = "SOLUCION_TECNICA")
	private String technicalSolution;

	@Column(name = "NO_INSTALACION")
	private String notInstalling;

	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Column(name = "FECHA_CREACION")
	private Timestamp createDate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "\"ESTADO_ID\"", nullable = true)
	private PStatus status;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "\"SOLICITADO_POR_ID\"", nullable = true)
	private PUser user;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "\"SISTEMA_ID\"", nullable = true)
	private PSystemInfo system;

	@Column(name = "PRE_CONDICIONES")
	private String preConditions;

	@Column(name = "POST_CONDICIONES")
	private String postConditions;

	@Lob
	@Column(name = "INSTRUCCIONES_DE_INSTALACION")
	private String installation_instructions;

	@Lob
	@Column(name = "INSTRUCCIONES_DE_VERIFICACION")
	private String verification_instructions;

	@Lob
	@Column(name = "PLAN_DE_ROLLBACK")
	private String rollback_plan;

	@Column(name = "REQUIERE_BAJAR")
	private boolean down_required;

	@Column(name = "PLANES_EJECUCION_LISTOS")
	private boolean plan_required;

	@Column(name = "TIENE_CAMBIOS_EN_BASE_DE_DATOS")
	private boolean has_changes_in_bd;

	@Column(name = "PRUEBAS_MINIMAS_SUGERIDAS_CF17")
	private String minimal_evidence;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "RELEASES_RELEASE_OBJETOS", joinColumns = {
			@JoinColumn(name = "\"RELEASE_ID\"") }, inverseJoinColumns = { @JoinColumn(name = "\"OBJETO_ID\"") })
	private Set<PReleaseObject> objects = new HashSet<PReleaseObject>();

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "\"PRIORIDAD_ID\"", nullable = true)
	private PPriority priority;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "\"RIESGO_ID\"", nullable = true)
	private PRisk risk;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "\"IMPACTO_ID\"", nullable = true)
	private PImpact impact;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "RELEASES_RELEASE_REQUERIMIENTO", joinColumns = {
			@JoinColumn(name = "\"RELEASE_ID\"") }, inverseJoinColumns = { @JoinColumn(name = "\"REQUERIMIENTO_ID\"") })
	private Set<PRequest> requestList = new HashSet<PRequest>();

	@OneToMany(mappedBy = "release", fetch = FetchType.EAGER)
	private Set<PDependency> dependencies;

	@OneToMany(mappedBy = "release", fetch = FetchType.EAGER)
	private Set<PCrontab> crontabs = new HashSet<PCrontab>();

	@OneToMany(mappedBy = "release", fetch = FetchType.EAGER)
	private Set<PButtonCommand> buttons = new HashSet<PButtonCommand>();

	@OneToMany(mappedBy = "release", fetch = FetchType.EAGER)
	private Set<PButtonFile> buttonsFile = new HashSet<PButtonFile>();

	@Column(name = "DESCRIPCION_FUNCIONAL")
	private String functionalDescription;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "RELEASES_RELEASE_ARCHIVOS", joinColumns = {
			@JoinColumn(name = "\"RELEASE_ID\"") }, inverseJoinColumns = { @JoinColumn(name = "\"ARCHIVO_ID\"") })
	private Set<PReleaseFile> files = new HashSet<PReleaseFile>();

	@Column(name = "INCIDENTES")
	private String incidents;

	@Column(name = "PROBLEMAS")
	private String problems;

	@Column(name = "SOLICITUDES_DE_SERVICIO")
	private String serviceRequests;

	@Column(name = "SOPORTE_OPERATIVO_ICE")
	private String operativeSupport;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "RELEASES_RELEASE_ACCION", joinColumns = {
			@JoinColumn(name = "\"RELEASE_ID\"") }, inverseJoinColumns = { @JoinColumn(name = "\"ACCION_ID\"") })
	private Set<PReleaseAction> actions = new HashSet<PReleaseAction>();

	// Definición de Ambientes
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "RELEASES_RELEASE_AMBIENTES", joinColumns = {
			@JoinColumn(name = "\"RELEASE_ID\"") }, inverseJoinColumns = { @JoinColumn(name = "\"AMBIENTE_ID\"") })
	private Set<PAmbient> ambients = new HashSet<PAmbient>();

	// Componentes Modificados
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "RELEASES_RELEASE_COMPONENTE", joinColumns = {
			@JoinColumn(name = "\"RELEASE_ID\"") }, inverseJoinColumns = { @JoinColumn(name = "\"COMPONENTEMODIFICADO_ID\"") })
	private Set<PModifiedComponent> modifiedComponents = new HashSet<PModifiedComponent>();

	// Instrucciones de Instalación de Base de Datos
	@Column(name = "INSTRUCCIONES_DE_INSTALACI93F7")
	private String certInstallationInstructions;

	@Column(name = "INSTRUCCIONES_DE_VERIFICACAE52")
	private String certVerificationInstructions;

	@Column(name = "PLAN_DE_ROLLBACK_BASE_DATO51A8")
	private String certRollbackPlan;

	@Column(name = "INSTRUCCIONES_DE_INSTALACI8FEF")
	private String prodInstallationInstructions;

	@Column(name = "INSTRUCCIONES_DE_VERIFICAC2149")
	private String prodVerificationInstructions;

	@Column(name = "PLAN_DE_ROLLBACK_BASE_DATOD9B1")
	private String prodRollbackPlan;

	// Observaciones
	@Column(name = "OBSERVACIONES_AMBIENTE_PRO6612")
	private String observationsProd;

	@Column(name = "OBSERVACIONES_AMBIENTE_PREQA")
	private String observationsPreQa;

	@Column(name = "OBSERVACIONES_AMBIENTE_QA")
	private String observationsQa;

	@Column(name = "NUMEROVERSION")
	private String versionNumber;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getReleaseNumber() {
		return releaseNumber;
	}

	public void setReleaseNumber(String releaseNumber) {
		this.releaseNumber = releaseNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public Boolean getReportHaveArt() {
		return reportHaveArt;
	}

	public void setReportHaveArt(Boolean reportHaveArt) {
		this.reportHaveArt = reportHaveArt;
	}

	public Boolean getReportfixedTelephony() {
		return reportfixedTelephony;
	}

	public void setReportfixedTelephony(Boolean reportfixedTelephony) {
		this.reportfixedTelephony = reportfixedTelephony;
	}

	public Boolean getReportHistoryTables() {
		return reportHistoryTables;
	}

	public void setReportHistoryTables(Boolean reportHistoryTables) {
		this.reportHistoryTables = reportHistoryTables;
	}

	public Boolean getReportNotHaveArt() {
		return reportNotHaveArt;
	}

	public void setReportNotHaveArt(Boolean reportNotHaveArt) {
		this.reportNotHaveArt = reportNotHaveArt;
	}

	public Boolean getReportMobileTelephony() {
		return reportMobileTelephony;
	}

	public void setReportMobileTelephony(Boolean reportMobileTelephony) {
		this.reportMobileTelephony = reportMobileTelephony;
	}

	public Boolean getReportTemporaryTables() {
		return reportTemporaryTables;
	}

	public void setReportTemporaryTables(Boolean reportTemporaryTables) {
		this.reportTemporaryTables = reportTemporaryTables;
	}

	public Boolean getBilledCalls() {
		return billedCalls;
	}

	public void setBilledCalls(Boolean billedCalls) {
		this.billedCalls = billedCalls;
	}

	public Boolean getNotBilledCalls() {
		return notBilledCalls;
	}

	public void setNotBilledCalls(Boolean notBilledCalls) {
		this.notBilledCalls = notBilledCalls;
	}

	public String getFunctionalSolution() {
		return functionalSolution;
	}

	public void setFunctionalSolution(String functionalSolution) {
		this.functionalSolution = functionalSolution;
	}

	public String getTechnicalSolution() {
		return technicalSolution;
	}

	public void setTechnicalSolution(String technicalSolution) {
		this.technicalSolution = technicalSolution;
	}

	public String getNotInstalling() {
		return notInstalling;
	}

	public void setNotInstalling(String notInstalling) {
		this.notInstalling = notInstalling;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public PStatus getStatus() {
		return status;
	}

	public void setStatus(PStatus status) {
		this.status = status;
	}

	public PUser getUser() {
		return user;
	}

	public void setUser(PUser user) {
		this.user = user;
	}

	public PSystemInfo getSystem() {
		return system;
	}

	public void setSystem(PSystemInfo system) {
		this.system = system;
	}

	public String getPreConditions() {
		return preConditions;
	}

	public void setPreConditions(String preConditions) {
		this.preConditions = preConditions;
	}

	public String getPostConditions() {
		return postConditions;
	}

	public void setPostConditions(String postConditions) {
		this.postConditions = postConditions;
	}

	public String getInstallation_instructions() {
		return installation_instructions;
	}

	public void setInstallation_instructions(String installation_instructions) {
		this.installation_instructions = installation_instructions;
	}

	public String getVerification_instructions() {
		return verification_instructions;
	}

	public void setVerification_instructions(String verification_instructions) {
		this.verification_instructions = verification_instructions;
	}

	public String getRollback_plan() {
		return rollback_plan;
	}

	public void setRollback_plan(String rollback_plan) {
		this.rollback_plan = rollback_plan;
	}

	public boolean isDown_required() {
		return down_required;
	}

	public void setDown_required(boolean down_required) {
		this.down_required = down_required;
	}

	public boolean isPlan_required() {
		return plan_required;
	}

	public void setPlan_required(boolean plan_required) {
		this.plan_required = plan_required;
	}

	public String getMinimal_evidence() {
		return minimal_evidence;
	}

	public void setMinimal_evidence(String minimal_evidence) {
		this.minimal_evidence = minimal_evidence;
	}

	public Set<PReleaseObject> getObjects() {
		return objects;
	}

	public void setObjects(Set<PReleaseObject> objects) {
		this.objects = objects;
	}

	public PPriority getPriority() {
		return priority;
	}

	public void setPriority(PPriority priority) {
		this.priority = priority;
	}

	public PRisk getRisk() {
		return risk;
	}

	public void setRisk(PRisk risk) {
		this.risk = risk;
	}

	public PImpact getImpact() {
		return impact;
	}

	public void setImpact(PImpact impact) {
		this.impact = impact;
	}

//	public Set<Request> getRequestList() {
//		return requestList;
//	}
//
//	public void setRequestList(Set<Request> requestList) {
//		this.requestList = requestList;
//	}

	public Set<PDependency> getDependencies() {
		return dependencies;
	}

	public void setDependencies(Set<PDependency> dependencies) {
		this.dependencies = dependencies;
	}

	public Set<PCrontab> getCrontabs() {
		return crontabs;
	}

	public void setCrontabs(Set<PCrontab> crontabs) {
		this.crontabs = crontabs;
	}

	public Set<PButtonCommand> getButtons() {
		return buttons;
	}

	public void setButtons(Set<PButtonCommand> buttons) {
		this.buttons = buttons;
	}

	public Set<PButtonFile> getButtonsFile() {
		return buttonsFile;
	}

	public void setButtonsFile(Set<PButtonFile> buttonsFile) {
		this.buttonsFile = buttonsFile;
	}

	public String getFunctionalDescription() {
		return functionalDescription;
	}

	public void setFunctionalDescription(String functionalDescription) {
		this.functionalDescription = functionalDescription;
	}

	public Set<PReleaseFile> getFiles() {
		return files;
	}

	public void setFiles(Set<PReleaseFile> files) {
		this.files = files;
	}

	public String getIncidents() {
		return incidents;
	}

	public void setIncidents(String incidents) {
		this.incidents = incidents;
	}

	public String getProblems() {
		return problems;
	}

	public void setProblems(String problems) {
		this.problems = problems;
	}

	public String getServiceRequests() {
		return serviceRequests;
	}

	public void setServiceRequests(String serviceRequests) {
		this.serviceRequests = serviceRequests;
	}

	public String getOperativeSupport() {
		return operativeSupport;
	}

	public void setOperativeSupport(String operativeSupport) {
		this.operativeSupport = operativeSupport;
	}

	public Set<PReleaseAction> getActions() {
		return actions;
	}

	public void setActions(Set<PReleaseAction> actions) {
		this.actions = actions;
	}

	public Set<PAmbient> getAmbients() {
		return ambients;
	}

	public void setAmbients(Set<PAmbient> ambients) {
		this.ambients = ambients;
	}

	public Set<PModifiedComponent> getModifiedComponents() {
		return modifiedComponents;
	}

	public void setModifiedComponents(Set<PModifiedComponent> modifiedComponents) {
		this.modifiedComponents = modifiedComponents;
	}

	public String getCertInstallationInstructions() {
		return certInstallationInstructions;
	}

	public void setCertInstallationInstructions(String certInstallationInstructions) {
		this.certInstallationInstructions = certInstallationInstructions;
	}

	public String getCertVerificationInstructions() {
		return certVerificationInstructions;
	}

	public void setCertVerificationInstructions(String certVerificationInstructions) {
		this.certVerificationInstructions = certVerificationInstructions;
	}

	public String getCertRollbackPlan() {
		return certRollbackPlan;
	}

	public void setCertRollbackPlan(String certRollbackPlan) {
		this.certRollbackPlan = certRollbackPlan;
	}

	public String getProdInstallationInstructions() {
		return prodInstallationInstructions;
	}

	public void setProdInstallationInstructions(String prodInstallationInstructions) {
		this.prodInstallationInstructions = prodInstallationInstructions;
	}

	public String getProdVerificationInstructions() {
		return prodVerificationInstructions;
	}

	public void setProdVerificationInstructions(String prodVerificationInstructions) {
		this.prodVerificationInstructions = prodVerificationInstructions;
	}

	public String getProdRollbackPlan() {
		return prodRollbackPlan;
	}

	public void setProdRollbackPlan(String prodRollbackPlan) {
		this.prodRollbackPlan = prodRollbackPlan;
	}

	public String getObservationsProd() {
		return observationsProd;
	}

	public void setObservationsProd(String observationsProd) {
		this.observationsProd = observationsProd;
	}

	public String getObservationsPreQa() {
		return observationsPreQa;
	}

	public void setObservationsPreQa(String observationsPreQa) {
		this.observationsPreQa = observationsPreQa;
	}

	public String getObservationsQa() {
		return observationsQa;
	}

	public void setObservationsQa(String observationsQa) {
		this.observationsQa = observationsQa;
	}

	public boolean getHas_changes_in_bd() {
		return has_changes_in_bd;
	}

	public void setHas_changes_in_bd(boolean has_changes_in_bd) {
		this.has_changes_in_bd = has_changes_in_bd;
	}

	public String getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	public Set<PRequest> getRequestList() {
		return requestList;
	}

	public void setRequestList(Set<PRequest> requestList) {
		this.requestList = requestList;
	}

	public String getManager() {
		String name = "";
		int i = 0;
		for (PRequest request : this.requestList) {
			if (i == 0) {
				name = request.getSoinManagement();
			}
			i++;
		}
		return name;
	}

	public String getIdentificator() {
		if (this.requestList.size() > 0) {
			for (PRequest request : this.requestList) {
				return request.getCode_soin() + " " + (request.getCode_ice() != null ? request.getCode_ice() : "") + " "
						+ request.getDescription();
			}
		}
		return "";
	}

	public String getTPO_BT() {
		if (this.requestList.size() > 0) {
			for (PRequest request : this.requestList) {
				return request.getCode_soin();
			}
		}
		return "";
	}

	public String getDependenciesList() {
		String list = "";
		for (PDependency dependency : this.dependencies) {
			list += dependency.getTo_release().getReleaseNumber() + ", ";
		}
		list = (list.equals("")) ? "No tiene" : list;

		list = list.trim();
		String regexTarget = "(,)$";
		list = list.trim().replaceAll(regexTarget, " ");

		return list;
	}

	public Boolean existComponent(String name) {
		for (PModifiedComponent component : modifiedComponents) {
			if (component.getName().equalsIgnoreCase(name))
				return true;
		}
		return false;
	}

}