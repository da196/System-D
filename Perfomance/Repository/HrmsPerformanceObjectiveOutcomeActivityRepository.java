package com.Hrms.Perfomance.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Perfomance.Entity.HrmsPerformanceObjectiveOutcomeActivity;

@Repository
public interface HrmsPerformanceObjectiveOutcomeActivityRepository
		extends JpaRepository<HrmsPerformanceObjectiveOutcomeActivity, Integer> {

	boolean existsByDescriptionAndActive(String description, int i);

	boolean existsByIdAndActive(int id, int i);

	HrmsPerformanceObjectiveOutcomeActivity findByIdAndActive(int id, int i);

	List<HrmsPerformanceObjectiveOutcomeActivity> findByActive(int i);

	List<HrmsPerformanceObjectiveOutcomeActivity> findByOutcomeidAndActive(int objectiveOutcomeid, int i);

}
