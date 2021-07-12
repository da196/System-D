package com.Hrms.Employee.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsEmployeeOffice;

@Repository
public interface HrmsEmployeeOfficeRepository extends JpaRepository<HrmsEmployeeOffice, Integer> {

	HrmsEmployeeOffice findFirstByEmployeeidAndActive(int id, int i);

	boolean existsByEmployeeidAndActive(int id, int i);

}
