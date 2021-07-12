package com.Hrms.Perfomance.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Perfomance.Entity.HrmsPerformanceEppaReviewAnnual;

@Repository
public interface HrmsPerformanceEppaReviewAnnualRepository
		extends JpaRepository<HrmsPerformanceEppaReviewAnnual, Integer> {

	boolean existsByEppaidAndObjectiveidAndActive(int eppaid, int objectiveid, int j);

	boolean existsByIdAndActive(int id, int i);

	HrmsPerformanceEppaReviewAnnual findByIdAndActive(int id, int i);

	List<HrmsPerformanceEppaReviewAnnual> findByActive(int i);

	List<HrmsPerformanceEppaReviewAnnual> findBySupervisordesignationidAndActive(int supervisordesignationid, int i);

	List<HrmsPerformanceEppaReviewAnnual> findBySupervisoridAndActive(int supervisorid, int i);

	List<HrmsPerformanceEppaReviewAnnual> findByObjectiveidAndActive(int objectiveid, int i);

	List<HrmsPerformanceEppaReviewAnnual> findByEppaidAndActive(int eppaid, int i);

}
