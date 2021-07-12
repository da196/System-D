package com.Hrms.Payroll.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Payroll.Entity.HrmsPayrollDeductionLoan;

@Repository
public interface HrmsPayrollDeductionLoanRepository extends JpaRepository<HrmsPayrollDeductionLoan, Integer> {

	boolean existsByLoantypeidAndEmployeeidAndPayrollidAndActive(int id, int employeeid, int id2, int i);

	HrmsPayrollDeductionLoan findByLoantypeidAndEmployeeidAndPayrollidAndActive(int id, int employeeid, int id2, int i);

	List<HrmsPayrollDeductionLoan> findByLoantypeidAndYearAndActive(int heslbloantypeid, int year, int i);

	List<HrmsPayrollDeductionLoan> findByLoantypeidAndYearAndMonthAndActive(int heslbloantypeid, int year, int month,
			int i);

	List<HrmsPayrollDeductionLoan> findByLoantypeidAndYearAndMonthAndEmployeeidAndActive(int heslbloantypeid, int year,
			int month, int empid, int i);

	List<HrmsPayrollDeductionLoan> findByLoantypeidAndYearAndEmployeeidAndActive(int heslbloantypeid, int year,
			int empid, int i);

	List<HrmsPayrollDeductionLoan> findByLoantypeidAndPayrollidAndYearAndMonthAndActive(int id, int id2, int year,
			int month, int i);

	List<HrmsPayrollDeductionLoan> findByLoantypeidAndYearAndMonthAndActiveAndPayrollidIn(int id, int year, int month,
			int i, List<Integer> payrollids);

}
