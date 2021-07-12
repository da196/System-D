package com.Hrms.Employee.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsEmployeeDesignation;

@Repository
public interface HrmsEmployeeDesignationRepository extends JpaRepository<HrmsEmployeeDesignation, Integer> {

	boolean existsByEmployeeId(int employeeId);

	boolean existsByIdAndActive(int id, int i);

	List<HrmsEmployeeDesignation> findByActive(int i);

}
