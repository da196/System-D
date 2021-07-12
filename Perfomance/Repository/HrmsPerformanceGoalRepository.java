package com.Hrms.Perfomance.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Perfomance.Entity.HrmsPerformanceGoal;

@Repository
public interface HrmsPerformanceGoalRepository extends JpaRepository<HrmsPerformanceGoal, Integer> {

	boolean existsByNameAndActive(String name, int i);

	boolean existsByIdAndActive(int id, int i);

	HrmsPerformanceGoal findByIdAndActive(int id, int i);

	List<HrmsPerformanceGoal> findByActive(int i);

	List<HrmsPerformanceGoal> findByPlanidAndActive(int planid, int i);

	HrmsPerformanceGoal findByIdAndPlanid(int goalid, int planid);

	boolean existsByPlanidAndActive(int planid, int i);

}
