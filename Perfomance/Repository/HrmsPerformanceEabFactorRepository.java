package com.Hrms.Perfomance.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Perfomance.Entity.HrmsPerformanceEabFactor;

@Repository
public interface HrmsPerformanceEabFactorRepository extends JpaRepository<HrmsPerformanceEabFactor, Integer> {

	boolean existsByNameAndActive(String name, int i);

	boolean existsByIdAndActive(int id, int i);

	HrmsPerformanceEabFactor findByIdAndActive(int id, int i);

	List<HrmsPerformanceEabFactor> findByActive(int i);

}
