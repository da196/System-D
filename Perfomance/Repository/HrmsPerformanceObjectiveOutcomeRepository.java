package com.Hrms.Perfomance.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Perfomance.Entity.HrmsPerformanceObjectiveOutcome;

@Repository
public interface HrmsPerformanceObjectiveOutcomeRepository
		extends JpaRepository<HrmsPerformanceObjectiveOutcome, Integer> {

	boolean existsByDescriptionAndActive(String description, int i);

	boolean existsByIdAndActive(int objectiveid, int i);

	HrmsPerformanceObjectiveOutcome findByIdAndActive(int id, int i);

	List<HrmsPerformanceObjectiveOutcome> findByActive(int i);

	List<HrmsPerformanceObjectiveOutcome> findByObjectiveidAndActive(int objectiveid, int i);

}
