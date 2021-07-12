package com.Hrms.Employee.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsRefereeCategory;

@Repository
public interface HrmsRefereeCategoryRepository extends JpaRepository<HrmsRefereeCategory, Integer> {

	boolean existsByName(String  name);

}
