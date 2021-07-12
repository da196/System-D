package com.Hrms.Employee.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsSalarystructure;

@Repository
public interface HrmsSalarystructureRepository extends JpaRepository<HrmsSalarystructure, Integer> {

	boolean existsByIdAndActive(int salarystructureId, int i);

	boolean existsByNotchIdAndScaleIdAndActive(int notchId, int scaleId, int i);

	List<HrmsSalarystructure> findByActive(int i);

}
