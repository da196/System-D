package com.Hrms.Employee.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsEmployeeEmploymentStatus;

@Repository
public interface HrmsEmployeeEmploymentStatusRepository extends JpaRepository<HrmsEmployeeEmploymentStatus, Integer> {

	HrmsEmployeeEmploymentStatus findByEmployeeidAndStatusid(int id, int i);

	boolean existsByEmployeeidAndStatusidInAndActive(int id, Integer[] statusleavers, int i);

	HrmsEmployeeEmploymentStatus findFirstByEmployeeidAndStatusidInAndActive(int id, Integer[] statusleavers, int i);

	boolean existsByEmployeeidAndActive(int id, int i);

	HrmsEmployeeEmploymentStatus findFirstByEmployeeidAndActive(int id, int i);

}
