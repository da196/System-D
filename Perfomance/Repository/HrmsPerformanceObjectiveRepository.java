package com.Hrms.Perfomance.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Perfomance.Entity.HrmsPerformanceObjective;

@Repository
public interface HrmsPerformanceObjectiveRepository extends JpaRepository<HrmsPerformanceObjective, Integer> {

	boolean existsByDescriptionAndActive(String description, int i);

	boolean existsByIdAndActive(int id, int i);

	HrmsPerformanceObjective findByIdAndActive(int id, int i);

	List<HrmsPerformanceObjective> findByActive(int i);

	List<HrmsPerformanceObjective> findByGoalidAndActive(int goalid, int i);

	HrmsPerformanceObjective findByGoalidAndId(int goalid, int objectiveid);

	boolean existsByGoalid(int id);

}
