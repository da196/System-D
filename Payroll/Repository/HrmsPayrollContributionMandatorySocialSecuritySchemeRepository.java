package com.Hrms.Payroll.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Payroll.Entity.HrmsPayrollContributionMandatorySocialSecurityScheme;

@Repository
public interface HrmsPayrollContributionMandatorySocialSecuritySchemeRepository
		extends JpaRepository<HrmsPayrollContributionMandatorySocialSecurityScheme, Integer> {

	boolean existsByContributiontypeidAndActive(int contributiontypeid, int i);

	boolean existsByIdAndActive(int id, int i);

	HrmsPayrollContributionMandatorySocialSecurityScheme findByIdAndActive(int id, int i);

	List<HrmsPayrollContributionMandatorySocialSecurityScheme> findByActive(int i);

	List<HrmsPayrollContributionMandatorySocialSecurityScheme> findByActiveOrderByIdDesc(int i);

}
