package com.Hrms.Perfomance.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Perfomance.Entity.HrmsPerformanceObjectiveOutcomeActivityOutputResponsible;

@Repository
public interface HrmsPerformanceObjectiveOutcomeActivityOutputResponsibleRepository
		extends JpaRepository<HrmsPerformanceObjectiveOutcomeActivityOutputResponsible, Integer> {

	boolean existsByIdAndActive(int id, int i);

	HrmsPerformanceObjectiveOutcomeActivityOutputResponsible findByIdAndActive(int id, int i);

	List<HrmsPerformanceObjectiveOutcomeActivityOutputResponsible> findByActive(int i);

	List<HrmsPerformanceObjectiveOutcomeActivityOutputResponsible> findByUnitidAndActive(int unitid, int i);

	boolean existsByUnitidAndTargetidAndActive(int unitid, int targetid, int i);

	List<HrmsPerformanceObjectiveOutcomeActivityOutputResponsible> findByTargetidAndActive(int targetid, int i);

	List<HrmsPerformanceObjectiveOutcomeActivityOutputResponsible> findByTargetidAndUnitidAndActive(int targetid,
			int unitid, int i);

}
