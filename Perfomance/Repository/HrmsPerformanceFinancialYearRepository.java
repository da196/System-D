package com.Hrms.Perfomance.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Perfomance.Entity.HrmsPerformanceFinancialYear;

@Repository
public interface HrmsPerformanceFinancialYearRepository extends JpaRepository<HrmsPerformanceFinancialYear, Integer> {

	boolean existsByIdAndActive(int financialyearid, int i);

	boolean existsByYearstartingAndYearendingAndActive(int yearstarting, int yearending, int i);

	HrmsPerformanceFinancialYear findByIdAndActive(int id, int i);

	List<HrmsPerformanceFinancialYear> findByActive(int i);

	HrmsPerformanceFinancialYear findByYearstartingAndYearendingAndActive(int yearstart, int yearend, int i);

	boolean existsByYearstartingAndYearendingAndActiveAndApproved(int yearstart, int yearend, int i, int j);

}
