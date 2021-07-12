package com.Hrms.Employee.Entity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hrms_insurance_beneficiary")
public class HrmsInsuranceBeneficiary {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
	private int id;

	@Column(name = "insurance_number")
	private String insurancenumber;
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "date_start")
	private Date datestart;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "date_end")
	private Date dateend;

	@Column(name = "description_start")
	private String descriptionstart;

	@Column(name = "description_end")
	private String descriptionend;

	@Column(name = "beneficiary_id")
	private int beneficiaryid;

	@Column(name = "beneficiary_type_id")
	private int beneficiarytypeid;

	@Column(name = "principal_id")
	private int employeeid;

	@Column(name = "insurance_category_id")
	private int insurancecategoryid;

	@Column(name = "insurance_provider_id")
	private int insuranceproviderid;

	@Column(name = "approved")
	private int approved;

	@Column(name = "active")
	private int active;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "date_created")
	@CreationTimestamp
	private LocalDateTime date_created;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "date_updated")
	private LocalDateTime date_updated;

	@Column(name = "unique_id")
	private UUID unique_id;

}
