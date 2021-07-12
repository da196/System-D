package com.Hrms.Perfomance.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Perfomance.Entity.HrmsPerformanceEppaReviewMidYear;

@Repository
public interface HrmsPerformanceEppaReviewMidYearRepository
		extends JpaRepository<HrmsPerformanceEppaReviewMidYear, Integer> {

	boolean existsByEppaidAndObjectiveidAndSupervisoridAndActive(int eppaid, int objectiveid, int supervisorid, int i);

	boolean existsByIdAndActive(int id, int i);

	HrmsPerformanceEppaReviewMidYear findByIdAndActive(int id, int i);

	List<HrmsPerformanceEppaReviewMidYear> findByActive(int i);

	HrmsPerformanceEppaReviewMidYear findByEppaidAndActive(int eppaid, int i);

	List<HrmsPerformanceEppaReviewMidYear> findByObjectiveidAndActive(int objectiveid, int i);

	List<HrmsPerformanceEppaReviewMidYear> findBySupervisordesignationidAndActive(int supervisordesignationid, int i);

	boolean existsByEppaidAndActive(int eppaid, int i);

	List<HrmsPerformanceEppaReviewMidYear> findByEppaidAndActiveAndApprovedIn(int eppaid, int i,
			List<Integer> approveds);

}
