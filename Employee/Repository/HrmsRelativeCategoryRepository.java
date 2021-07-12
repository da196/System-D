package com.Hrms.Employee.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsRelativeCategory;

@Repository
public interface HrmsRelativeCategoryRepository extends JpaRepository<HrmsRelativeCategory, Integer> {

	boolean existsByName(String  name);

}
