package com.Hrms.Payroll.Entity;

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
@Table(name = "hrms_payroll_deduction_loan")
public class HrmsPayrollDeductionLoan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
	private int id;

	@Column(name = "amount")
	private Double amount;

	@Column(name = "amount_penalty")
	private Double amountpenalty;

	@Column(name = "amount_outstanding")
	private Double amountoutstanding;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "date_deducted")
	private Date datededucted;

	@Column(name = "loan_type_id")
	private int loantypeid;

	@Column(name = "loan_id")
	private int loanid;

	@Column(name = "payroll_id")
	private int payrollid;

	@Column(name = "employee_id")
	private int employeeid;

	@Column(name = "currency_id")
	private int currencyid;

	@Column(name = "approved")
	private int approved;

	@Column(name = "year")
	private int year;

	@Column(name = "month")
	private int month;

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
