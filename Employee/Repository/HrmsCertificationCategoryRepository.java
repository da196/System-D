package com.Hrms.Employee.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsCertificationCategory;

@Repository
public interface HrmsCertificationCategoryRepository extends JpaRepository<HrmsCertificationCategory, Integer> {

	List<HrmsCertificationCategory> findByActive(int i);

	boolean existsByNameAndActive(String name, int i);

	boolean existsByIdAndActive(int id, int i);

}
