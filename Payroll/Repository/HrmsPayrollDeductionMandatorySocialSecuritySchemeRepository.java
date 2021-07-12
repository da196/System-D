package com.Hrms.Payroll.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Payroll.Entity.HrmsPayrollDeductionMandatorySocialSecurityScheme;

@Repository
public interface HrmsPayrollDeductionMandatorySocialSecuritySchemeRepository
		extends JpaRepository<HrmsPayrollDeductionMandatorySocialSecurityScheme, Integer> {

	boolean existsByIdAndActive(int id, int i);

	HrmsPayrollDeductionMandatorySocialSecurityScheme findByPayrollid(int payrollId);

	boolean existsByPayrollidAndActive(int payrollId, int i);

	boolean existsByEmployeeidAndActive(int empId, int i);

	List<HrmsPayrollDeductionMandatorySocialSecurityScheme> findByEmployeeidAndActive(int empId, int i);

	HrmsPayrollDeductionMandatorySocialSecurityScheme findByIdAndActive(int id, int i);

	List<HrmsPayrollDeductionMandatorySocialSecurityScheme> findByActive(int i);

	List<HrmsPayrollDeductionMandatorySocialSecurityScheme> findByActiveOrderByIdDesc(int i);

	List<HrmsPayrollDeductionMandatorySocialSecurityScheme> findByEmployeeidAndActiveOrderByIdDesc(int empId, int i);

	List<HrmsPayrollDeductionMandatorySocialSecurityScheme> findByYearAndMonthAndActive(int year, int month, int i);

	HrmsPayrollDeductionMandatorySocialSecurityScheme findByPayrollidAndServiceprovideridAndActive(int id,
			int serviceproviderid, int i);

	boolean existsByPayrollidAndEmployeeidAndActive(int id, int employeeid, int i);

	HrmsPayrollDeductionMandatorySocialSecurityScheme findByPayrollidAndEmployeeidAndActive(int id, int employeeid,
			int i);

	HrmsPayrollDeductionMandatorySocialSecurityScheme findByEmployeeidAndYearAndMonthAndActive(int employeeid, int year,
			int month, int i);

}
