package com.Hrms.Employee.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsSalaryScale;

@Repository
public interface HrmsSalaryScaleRepository extends JpaRepository<HrmsSalaryScale, Integer> {

	boolean existsByName(String name);

	boolean existsByIdAndActive(int scaleId, int i);

}
