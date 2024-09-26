package com.soin.sgrm.model.pos;

import java.io.Serializable;

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

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "SOLICITUD_TIPO")
public class PTypePetition implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "solicitud_tipo_seq")
	@SequenceGenerator(name = "solicitud_tipo_seq", sequenceName = "solicitud_tipo_id_seq", allocationSize = 1)
	@Column(name = "ID")
	private Long id;

	@Column(name = "CODIGO")
	private String code;

	@Column(name = "DESCRIPCION")
	private String description;
	
	@Column(name = "ESTADO")
	private Integer status;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "\"EMAIL_ID\"")
	private PEmailTemplate emailTemplate;
	
	@Transient
	private int emailTemplateId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public PEmailTemplate getEmailTemplate() {
		return emailTemplate;
	}

	public void setEmailTemplate(PEmailTemplate emailTemplate) {
		this.emailTemplate = emailTemplate;
	}

	public int getEmailTemplateId() {
		return emailTemplateId;
	}

	public void setEmailTemplateId(int emailTemplateId) {
		this.emailTemplateId = emailTemplateId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
	
}
