package com.Hrms.Perfomance.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Perfomance.Entity.HrmsPerformancePlan;

@Repository
public interface HrmsPerformancePlanRepository extends JpaRepository<HrmsPerformancePlan, Integer> {

	boolean existsByNameAndActive(String name, int i);

	HrmsPerformancePlan findByIdAndActive(int id, int i);

	boolean existsByIdAndActive(int id, int i);

	List<HrmsPerformancePlan> findByActive(int i);

}
