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
@Table(name = "hrms_payroll")
public class HrmsPayroll {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
	private int id;

	@Column(name = "amount_salary_gross")
	private Double amountsalarygross;

	@Column(name = "amount_salary_basic")
	private Double amountsalarybasic;

	@Column(name = "amount_salary_net")
	private Double amountsalarynet;

	@Column(name = "amount_other_income")
	private Double amountotherincome;

	@Column(name = "amount_taxable")
	private Double amounttaxable;

	@Column(name = "amount_salary_allowance_transport")
	private Double amountsalaryallowancetransport;

	@Column(name = "amount_salary_allowance_housing")
	private Double amountsalaryallowancehousing;

	@Column(name = "amount_salary_allowance")
	private Double amountsalaryallowance;

	@Column(name = "amount_salary_servant_and_hospitality")
	private Double amountsalaryservantAndHospitality;

	@Column(name = "amount_tax_paye")
	private Double amounttaxpaye;

	@Column(name = "amount_tax_other")
	private Double amounttaxother;

	@Column(name = "amount_tax")
	private Double amounttax;

	@Column(name = "amount_deduction_mandatory_sssf")
	private Double amountdeductionmandatorysssf;

	@Column(name = "amount_deduction_mandatory_health")
	private Double amountdeductionmandatoryhealth;

	@Column(name = "amount_deduction_mandatory")
	private Double amountdeductionmandatory;

	@Column(name = "amount_deduction_voluntary")
	private Double amountdeductionvoluntary;

	@Column(name = "amount_deduction")
	private Double amountdeduction;

	@Column(name = "amount_deduction_loan_salary_advance")
	private Double amountdeductionloansalaryadvance;

	@Column(name = "amount_deduction_loan_saccos")
	private Double amountdeductionloansaccos;

	@Column(name = "amount_deduction_loan_education_heslb")
	private Double amountdeductionloaneducationheslb;

	@Column(name = "amount_deduction_loan_education_other")
	private Double amountdeductionloaneducationother;

	@Column(name = "amount_deduction_loan_other")
	private Double amountdeductionloanother;

	@Column(name = "amount_deduction_loan")
	private Double amountdeductionloan;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "date_pay")
	private Date datepay;

	@Column(name = "payroll_type_id")
	private int payrolltypeid;

	@Column(name = "employee_id")
	private int employeeid;

	@Column(name = "currency_id")
	private int currencyid;

	@Column(name = "approved")
	private int approved;

	@Column(name = "active")
	private int active;

	@Column(name = "year")
	private int year;

	@Column(name = "month")
	private int month;

	@Column(name = "day")
	private int day;

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
