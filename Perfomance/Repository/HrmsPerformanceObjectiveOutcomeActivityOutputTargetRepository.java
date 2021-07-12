package com.Hrms.Perfomance.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Perfomance.Entity.HrmsPerformanceObjectiveOutcomeActivityOutputTarget;

@Repository
public interface HrmsPerformanceObjectiveOutcomeActivityOutputTargetRepository
		extends JpaRepository<HrmsPerformanceObjectiveOutcomeActivityOutputTarget, Integer> {

	boolean existsByIdAndActive(int targetid, int i);

	boolean existsByDescriptionAndActive(String description, int i);

	HrmsPerformanceObjectiveOutcomeActivityOutputTarget findByIdAndActive(int id, int i);

	List<HrmsPerformanceObjectiveOutcomeActivityOutputTarget> findByActive(int i);

	List<HrmsPerformanceObjectiveOutcomeActivityOutputTarget> findByOutputidAndActive(int outputid, int i);

}
