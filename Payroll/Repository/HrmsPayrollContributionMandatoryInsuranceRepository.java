package com.Hrms.Payroll.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Payroll.Entity.HrmsPayrollContributionMandatoryInsurance;

@Repository
public interface HrmsPayrollContributionMandatoryInsuranceRepository
		extends JpaRepository<HrmsPayrollContributionMandatoryInsurance, Integer> {

	boolean existsByContributiontypeidAndActive(int contributiontypeid, int i);

	boolean existsByIdAndActive(int id, int i);

	HrmsPayrollContributionMandatoryInsurance findByIdAndActive(int id, int i);

	List<HrmsPayrollContributionMandatoryInsurance> findByActive(int i);

	List<HrmsPayrollContributionMandatoryInsurance> findByActiveOrderByIdDesc(int i);

	HrmsPayrollContributionMandatoryInsurance findByContributiontypeid(int heathcontributiontype);

}
