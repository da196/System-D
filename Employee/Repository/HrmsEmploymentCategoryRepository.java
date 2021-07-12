package com.Hrms.Employee.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsEmploymentCategory;

@Repository
public interface HrmsEmploymentCategoryRepository extends JpaRepository<HrmsEmploymentCategory, Integer> {

	boolean existsByName(String  name);

}
