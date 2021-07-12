package com.Hrms.Payroll.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Payroll.Entity.HrmsPayrollContributionVoluntaryServiceProvider;

@Repository
public interface HrmsPayrollContributionVoluntaryServiceProviderRepository
		extends JpaRepository<HrmsPayrollContributionVoluntaryServiceProvider, Integer> {

	boolean existsByNameAndActive(String name, int i);

	boolean existsByIdAndActive(int id, int i);

	HrmsPayrollContributionVoluntaryServiceProvider findByIdAndActive(int id, int i);

	List<HrmsPayrollContributionVoluntaryServiceProvider> findByActive(int i);

}
