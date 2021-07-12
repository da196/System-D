package com.Hrms.Payroll.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Payroll.Entity.HrmsPayrollContributionVoluntary;

@Repository
public interface HrmsPayrollContributionVoluntaryRepository
		extends JpaRepository<HrmsPayrollContributionVoluntary, Integer> {

	boolean existsByContributiontypeidAndServiceprovideridAndActive(int contributiontypeid, int serviceproviderid,
			int i);

	boolean existsByIdAndActive(int id, int i);

	Optional<HrmsPayrollContributionVoluntary> findByIdAndActive(int id, int i);

	List<HrmsPayrollContributionVoluntary> findByEmployeeidAndActive(int empId, int i);

	List<HrmsPayrollContributionVoluntary> findByActive(int i);

	boolean existsByEmployeeidAndActive(int id, int i);

	boolean existsByEmployeeidAndActiveAndLocked(int id, int i, int j);

	List<HrmsPayrollContributionVoluntary> findByEmployeeidAndActiveAndLocked(int id, int i, int j);

	boolean existsByEmployeeidAndContributiontypeidAndServiceprovideridAndActive(int employeeid, int contributiontypeid,
			int serviceproviderid, int i);

	List<HrmsPayrollContributionVoluntary> findByEmployeeidAndActiveOrderByIdDesc(int empId, int i);

	List<HrmsPayrollContributionVoluntary> findByActiveOrderByIdDesc(int i);

}
