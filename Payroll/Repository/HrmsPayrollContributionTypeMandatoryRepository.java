package com.Hrms.Payroll.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Payroll.Entity.HrmsPayrollContributionTypeMandatory;

@Repository
public interface HrmsPayrollContributionTypeMandatoryRepository
		extends JpaRepository<HrmsPayrollContributionTypeMandatory, Integer> {

	boolean existsByNameAndActive(String name, int i);

	boolean existsByIdAndActive(int id, int i);

	HrmsPayrollContributionTypeMandatory findByIdAndActive(int id, int i);

	List<HrmsPayrollContributionTypeMandatory> findByActive(int i);

}
