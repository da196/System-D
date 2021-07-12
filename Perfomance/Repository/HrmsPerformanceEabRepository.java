package com.Hrms.Perfomance.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Perfomance.Entity.HrmsPerformanceEab;

@Repository
public interface HrmsPerformanceEabRepository extends JpaRepository<HrmsPerformanceEab, Integer> {

	boolean existsByEppaidAndQualityidAndEmployeeidAndActive(int eppaid, int qualityid, int employeeid, int i);

	boolean existsByIdAndActive(int id, int i);

	HrmsPerformanceEab findByIdAndActive(int id, int i);

	List<HrmsPerformanceEab> findByActive(int i);

	List<HrmsPerformanceEab> findByQualityidAndActive(int qualityid, int i);

	List<HrmsPerformanceEab> findByEppaidAndActive(int eppaid, int i);

	List<HrmsPerformanceEab> findByEmployeeidAndActive(int employeeid, int i);

	HrmsPerformanceEab findByEppaidAndQualityidAndEmployeeidAndActive(int eppaid, int id, int employeeid, int i);

	boolean existsByEppaidAndQualityidAndActive(int eppaid, int id, int i);

	HrmsPerformanceEab findByEppaidAndQualityidAndActive(int eppaid, int id, int i);

}
