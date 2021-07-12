package com.Hrms.Perfomance.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Perfomance.Entity.HrmsPerformanceObjectiveTarget;

@Repository
public interface HrmsPerformanceObjectiveTargetRepository
		extends JpaRepository<HrmsPerformanceObjectiveTarget, Integer> {

	boolean existsByNameAndObjectiveidAndActive(String name, int objectiveid, int i);

	boolean existsByIdAndActive(int id, int i);

	HrmsPerformanceObjectiveTarget findByIdAndActive(int id, int i);

	List<HrmsPerformanceObjectiveTarget> findByActive(int i);

	List<HrmsPerformanceObjectiveTarget> findByObjectiveidAndActive(int objectiveid, int i);

	boolean existsByObjectiveid(int id);

}
