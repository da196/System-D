package com.Hrms.Payroll.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Payroll.Entity.HrmsPayrollContributionType;

@Repository
public interface HrmsPayrollContributionTypeRepository extends JpaRepository<HrmsPayrollContributionType, Integer> {

	boolean existsByNameAndActive(String name, int i);

	boolean existsByIdAndActive(int id, int i);

	HrmsPayrollContributionType findByIdAndActive(int id, int i);

	List<HrmsPayrollContributionType> findByActive(int i);

}
