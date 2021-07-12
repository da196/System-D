package com.Hrms.Employee.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsEducationInstCategory;

@Repository
public interface HrmsEducationInstCategoryRepository extends JpaRepository<HrmsEducationInstCategory, Integer> {

	boolean existsByName(String name);

	List<HrmsEducationInstCategory> findByActive(int i);

	boolean existsByIdAndActive(int id, int i);

}
