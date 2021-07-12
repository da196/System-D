package com.Hrms.Payroll.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Payroll.Entity.HrmsPayrollContributionTypeVoluntary;

@Repository
public interface HrmsPayrollContributionTypeVoluntaryRepository
		extends JpaRepository<HrmsPayrollContributionTypeVoluntary, Integer> {

	boolean existsByNameAndActive(String name, int i);

	boolean existsByIdAndActive(int id, int i);

	HrmsPayrollContributionTypeVoluntary findByIdAndActive(int id, int i);

	List<HrmsPayrollContributionTypeVoluntary> findByActive(int i);

	List<HrmsPayrollContributionTypeVoluntary> findByProvideridAndActive(int serviceproviderid, int i);

}
