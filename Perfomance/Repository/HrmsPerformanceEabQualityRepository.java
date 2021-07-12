package com.Hrms.Perfomance.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Perfomance.Entity.HrmsPerformanceEabQuality;

@Repository
public interface HrmsPerformanceEabQualityRepository extends JpaRepository<HrmsPerformanceEabQuality, Integer> {

	boolean existsByNameAndFactoridAndActive(String name, int factorid, int i);

	boolean existsByIdAndActive(int id, int i);

	HrmsPerformanceEabQuality findByIdAndActive(int id, int i);

	List<HrmsPerformanceEabQuality> findByActive(int i);

	List<HrmsPerformanceEabQuality> findByFactoridAndActive(int factorid, int i);

	int countByFactoridAndActive(int id, int i);

}
