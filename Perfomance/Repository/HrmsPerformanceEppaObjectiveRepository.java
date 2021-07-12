package com.Hrms.Perfomance.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Perfomance.Entity.HrmsPerformanceEppaObjective;

@Repository
public interface HrmsPerformanceEppaObjectiveRepository extends JpaRepository<HrmsPerformanceEppaObjective, Integer> {

	boolean existsByTargetsAndCriteriaAndEppaidAndActive(String targets, String criteria, int eppaid, int i);

	boolean existsByIdAndActive(int id, int i);

	HrmsPerformanceEppaObjective findByIdAndActive(int id, int i);

	List<HrmsPerformanceEppaObjective> findByActive(int i);

	List<HrmsPerformanceEppaObjective> findByEppaidAndActive(int i, int j);

}
