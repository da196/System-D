package com.Hrms.Payroll.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Payroll.Entity.HrmsPayrollDeductionVoluntary;

@Repository
public interface HrmsPayrollDeductionVoluntaryRepository extends JpaRepository<HrmsPayrollDeductionVoluntary, Integer> {

	boolean existsByIdAndActive(int id, int i);

	boolean existsByPayrollidAndActive(int payrollId, int i);

	List<HrmsPayrollDeductionVoluntary> findByPayrollid(int payrollId);

	boolean existsByEmployeeidAndActive(int empId, int i);

	List<HrmsPayrollDeductionVoluntary> findByEmployeeidAndActive(int empId, int i);

	HrmsPayrollDeductionVoluntary findByIdAndActive(int id, int i);

	List<HrmsPayrollDeductionVoluntary> findByActive(int i);

	List<HrmsPayrollDeductionVoluntary> findByEmployeeidAndActiveOrderByIdDesc(int empId, int i);

	List<HrmsPayrollDeductionVoluntary> findByPayrollidOrderByIdDesc(int payrollId);

	List<HrmsPayrollDeductionVoluntary> findByActiveOrderByIdDesc(int i);

	boolean existsByEmployeeidAndPayrollidAndActive(int employeeid, int id, int i);

	boolean existsByEmployeeidAndPayrollidAndContributiontypeidAndActive(int employeeid, int id, int id2, int i);

	HrmsPayrollDeductionVoluntary findByEmployeeidAndPayrollidAndContributiontypeidAndActive(int employeeid, int id,
			int id2, int i);

	List<HrmsPayrollDeductionVoluntary> findByContributiontypeidAndYearAndMonthAndActive(int id, int year, int month,
			int i);

	List<HrmsPayrollDeductionVoluntary> findByContributiontypeidAndYearAndMonthAndActiveAndPayrollidIn(int id, int year,
			int month, int i, List<Integer> payrollids);

}
