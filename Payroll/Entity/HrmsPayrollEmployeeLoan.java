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
@Table(name = "hrms_payroll_employee_loan")
public class HrmsPayrollEmployeeLoan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
	private int id;

	@Column(name = "amount_debt")
	private Double amountdebt;

	@Column(name = "monthly_payment")
	private Double amountprincipal;

	@Column(name = "amount_outstanding")
	private Double amountoutstanding;

	@Column(name = "amount_loan_balance")
	private Double amountloanbalance;

	@Column(name = "interest_rate")
	private Double interestrate;

	@Column(name = "duration")
	private Double duration;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "date_issued")
	private Date dateissued;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "date_repayment_start")
	private Date daterepaymentstart;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "date_repayment_end")
	private Date daterepaymentend;

	@Column(name = "description")
	private String description;

	@Column(name = "loan_type_id")
	private int loantypeid;

	@Column(name = "loan_frequency_id")
	private int loanfrequencyid;

	@Column(name = "employee_id")
	private int employeeid;

	@Column(name = "lender_id")
	private int lenderid;

	@Column(name = "repayment_mode")
	private int repaymentmode;

	@Column(name = "currency_id")
	private int currencyid;

	@Column(name = "status") // 0 not yet started paying , 1 paid partially ,
								// one, 2 completed payment
	private int status;

	@Column(name = "approved")
	private int approved;

	@Column(name = "active")
	private int active;

	@Column(name = "year")
	private int year;

	@Column(name = "month")
	private int month;

	@Column(name = "rate")
	private Double rate;

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
