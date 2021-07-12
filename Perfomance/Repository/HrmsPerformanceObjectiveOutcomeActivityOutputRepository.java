package com.Hrms.Perfomance.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Perfomance.Entity.HrmsPerformanceObjectiveOutcomeActivityOutput;

@Repository
public interface HrmsPerformanceObjectiveOutcomeActivityOutputRepository
		extends JpaRepository<HrmsPerformanceObjectiveOutcomeActivityOutput, Integer> {

	boolean existsByDescriptionAndActive(String description, int i);

	boolean existsByIdAndActive(int id, int i);

	HrmsPerformanceObjectiveOutcomeActivityOutput findByIdAndActive(int id, int i);

	List<HrmsPerformanceObjectiveOutcomeActivityOutput> findByActive(int i);

	List<HrmsPerformanceObjectiveOutcomeActivityOutput> findByActivityidAndActive(int activityid, int i);

}
