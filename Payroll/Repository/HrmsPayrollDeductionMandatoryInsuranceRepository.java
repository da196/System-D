package com.Hrms.Payroll.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Payroll.Entity.HrmsPayrollDeductionMandatoryInsurance;

@Repository
public interface HrmsPayrollDeductionMandatoryInsuranceRepository
		extends JpaRepository<HrmsPayrollDeductionMandatoryInsurance, Integer> {

	boolean existsByIdAndActive(int id, int i);

	boolean existsByEmployeeidAndActive(int empId, int i);

	List<HrmsPayrollDeductionMandatoryInsurance> findByEmployeeidAndActive(int empId, int i);

	List<HrmsPayrollDeductionMandatoryInsurance> findByActive(int i);

	boolean existsByPayrollidAndActive(int payrollId, int i);

	HrmsPayrollDeductionMandatoryInsurance findByPayrollid(int payrollId);

	HrmsPayrollDeductionMandatoryInsurance findByIdAndActive(int id, int i);

	List<HrmsPayrollDeductionMandatoryInsurance> findByActiveOrderByIdDesc(int i);

	List<HrmsPayrollDeductionMandatoryInsurance> findByEmployeeidAndActiveOrderByIdDesc(int empId, int i);

	HrmsPayrollDeductionMandatoryInsurance findByPayrollidAndContributiontypeidAndActive(int id,
			int wcfcontributiontypeid, int i);

	boolean existsByPayrollidAndContributiontypeidAndActive(int id, int wcfcontributiontypeid, int i);

	HrmsPayrollDeductionMandatoryInsurance findByPayrollidAndContributiontypeidAndServiceprovideridAndActive(int id,
			int wcfcontributiontypeid, int serviceproviderid, int i);

}
