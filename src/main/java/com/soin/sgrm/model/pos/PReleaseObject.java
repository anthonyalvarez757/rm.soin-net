package com.soin.sgrm.model.pos;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Value;

@SuppressWarnings("serial")
@Entity
@Table(name = "SISTEMAS_OBJETO")
public class PReleaseObject implements Serializable, Cloneable {

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(name = "ID")
	private int id;

	@Column(name = "NOMBRE")
	private String name;

	@Column(name = "DESCRIPCION")
	private String description;

	@Column(name = "REVISION_RESPOSITORIO")
	private String revision_SVN;

	@Column(name = "FECHA_REVISION")
	private Date revision_Date;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "\"TIPO_OBJETO_ID\"", nullable = false)
	private PTypeObject typeObject;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "\"ITEM_DE_CONFIGURACION_ID\"", nullable = false)
	private PConfigurationItem configurationItem;

	@Value("${execute:0}")
	@Column(name = "OCUPA_EJECUTAR")
	private int execute;

	@Column(name = "ESQUEMA")
	private String dbScheme;

	@Value("${executePlan:0}")
	@Column(name = "PLAN_EJECUCION")
	private int executePlan;

	@Value("${isSql:0}")
	@Column(name = "SQL")
	private int isSql;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "\"MODULO_ID\"", nullable = false)
	private PModule module;

	@Transient
	private String numRelease;
	
	public PReleaseObject() {

	}

	public PReleaseObject(String name, String description, String revision_SVN, Date revision_Date,
			PTypeObject typeObject, PConfigurationItem configurationItem, PModule module)
			throws CloneNotSupportedException {
		super();
		this.id = 0;
		this.name = name;
		this.description = description;
		this.revision_SVN = revision_SVN;
		this.revision_Date = revision_Date;
		this.typeObject = typeObject;
		this.configurationItem = (PConfigurationItem) configurationItem.clone();
		this.execute = 0;
		this.dbScheme = "";
		this.executePlan = 0;
		this.isSql = (configurationItem.getName().equalsIgnoreCase("Base Datos") ? 1 : 0);
		this.module = module;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRevision_SVN() {
		return revision_SVN;
	}

	public void setRevision_SVN(String revision_SVN) {
		this.revision_SVN = revision_SVN;
	}

	public Date getRevision_Date() {
		return revision_Date;
	}

	public void setRevision_Date(Date revision_Date) {
		this.revision_Date = revision_Date;
	}

	public PTypeObject getTypeObject() {
		return typeObject;
	}

	public void setTypeObject(PTypeObject typeObject) {
		this.typeObject = typeObject;
	}

	public PConfigurationItem getConfigurationItem() {
		return configurationItem;
	}

	public void setConfigurationItem(PConfigurationItem configurationItem) {
		this.configurationItem = configurationItem;
	}

	public int getExecute() {
		return execute;
	}

	public void setExecute(int execute) {
		this.execute = execute;
	}

	public String getDbScheme() {
		return dbScheme;
	}

	public void setDbScheme(String dbScheme) {
		this.dbScheme = dbScheme;
	}

	public int getExecutePlan() {
		return executePlan;
	}

	public void setExecutePlan(int executePlan) {
		this.executePlan = executePlan;
	}

	public int getIsSql() {
		return isSql;
	}

	public void setIsSql(int isSql) {
		this.isSql = isSql;
	}

	public PModule getModule() {
		return module;
	}

	public void setModule(PModule module) {
		this.module = module;
	}

	public boolean equals(PReleaseObject obj) {
		boolean equals = true;
		if (!this.name.equals(obj.getName())) {
			equals = false;
		}

		if (!this.description.equals(obj.getDescription())) {
			equals = false;
		}

		if (!this.revision_SVN.equals(obj.getRevision_SVN())) {
			equals = false;
		}
		if (!this.revision_Date.equals(obj.getRevision_Date())) {
			equals = false;
		}
		if (this.typeObject.getId() != obj.getTypeObject().getId()) {
			equals = false;
		}
		if (this.configurationItem.getId() != obj.getConfigurationItem().getId()) {
			equals = false;
		}

		return equals;
	}
	
	public String getNumRelease() {
		return numRelease;
	}

	public void setNumRelease(String numRelease) {
		this.numRelease = numRelease;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
