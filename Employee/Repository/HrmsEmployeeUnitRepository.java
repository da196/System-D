package com.Hrms.Employee.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsEmployeeUnit;

@Repository
public interface HrmsEmployeeUnitRepository extends JpaRepository<HrmsEmployeeUnit, Integer> {

	HrmsEmployeeUnit findByIdAndActive(int id, int i);

	boolean existsByEmployeeid(int id);

	HrmsEmployeeUnit findByEmployeeidAndActive(int id, int i);

	boolean existsByEmployeeidAndActive(int id, int i);

	HrmsEmployeeUnit findFirstByEmployeeidAndActive(int id, int i);

}
