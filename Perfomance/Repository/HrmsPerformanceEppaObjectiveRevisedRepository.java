package com.Hrms.Perfomance.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Perfomance.Entity.HrmsPerformanceEppaObjectiveRevised;

@Repository
public interface HrmsPerformanceEppaObjectiveRevisedRepository
		extends JpaRepository<HrmsPerformanceEppaObjectiveRevised, Integer> {

	boolean existsByTargetsAndCriteriaAndObjectiveidAndActive(String targets, String criteria, int objectiveid, int i);

	boolean existsByIdAndActive(int id, int i);

	HrmsPerformanceEppaObjectiveRevised findByIdAndActive(int id, int i);

	List<HrmsPerformanceEppaObjectiveRevised> findByActive(int i);

	List<HrmsPerformanceEppaObjectiveRevised> findByObjectiveidAndActive(int objectiveid, int i);

}
