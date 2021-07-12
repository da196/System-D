package com.Hrms.Employee.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Hrms.Employee.Entity.HrmsInsuranceProvider;

public interface HrmsInsuranceProviderRepository extends JpaRepository<HrmsInsuranceProvider, Integer> {

	boolean existsByName(String name);

	boolean existsByIdAndActive(int id, int i);

	List<HrmsInsuranceProvider> findByActive(int i);

}
