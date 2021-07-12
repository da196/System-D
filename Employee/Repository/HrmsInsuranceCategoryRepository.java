package com.Hrms.Employee.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsInsuranceCategory;

@Repository
public interface HrmsInsuranceCategoryRepository extends JpaRepository<HrmsInsuranceCategory, Integer> {

	boolean existsByName(String name);

	boolean existsByIdAndActive(int insurancecategoryid, int i);

}
