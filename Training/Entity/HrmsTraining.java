package com.Hrms.Training.Entity;

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
@Table(name = "hrms_training")
public class HrmsTraining {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
	private int id;

	@Column(name = "description")
	private String description;

	@Column(name = "institution_address")
	private String institutionaddress;

	@Column(name = "training_cost")
	private Double trainingcost;

	@Column(name = "fee_structure_attachment")
	private String feestructureattachment;

	@Column(name = "currency_id")
	private int currencyid;

	@Column(name = "training_purpose_id")
	private int trainingpurposeid;

	@Column(name = "name")
	private String name;

	@Column(name = "institution")
	private String institution;

	@Column(name = "un_planned")
	private int unplanned;

	@Column(name = "delayed")
	private int delayed;

	@Column(name = "delayed_reason")
	private String delayedreason;

	@Column(name = "un_attended")
	private int unattended;

	@Column(name = "un_attended_reason")
	private String unattendedreason;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "date_expected_start")

	private Date dateexpectedstart;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "date_expected_end")

	private Date date_expected_end;

	@Column(name = "financial_year_id")
	private int financialyearid;

	@Column(name = "training_category_id")
	private int trainingcategoryid;

	@Column(name = "training_type_id")
	private int trainingtypeid;

	@Column(name = "training_initiator_id")
	private int traininginitiatorid;

	@Column(name = "training_sponsor_id")
	private int trainingsponsorid;

	@Column(name = "employee_id")
	private int employeeid;

	@Column(name = "supervisor_id")
	private int supervisorid;

	@Column(name = "supervisor_designation_id")
	private int supervisordesignationid;

	@Column(name = "approved")
	private int approved;

	@Column(name = "active")
	private int active;

	@Column(name = "requested_by")
	private int requestedby;
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
