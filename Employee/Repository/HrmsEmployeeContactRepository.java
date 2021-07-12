package com.Hrms.Employee.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsEmployeeContact;

@Repository
public interface HrmsEmployeeContactRepository extends JpaRepository<HrmsEmployeeContact, Integer> {

	boolean existsByEmployeeid(int empid);

	HrmsEmployeeContact findByContactid(int cid);

	List<HrmsEmployeeContact> findByEmployeeid(int empid);

	List<HrmsEmployeeContact> findByEmployeeidAndActive(int empid, int i);

	List<HrmsEmployeeContact> findByActiveOrderByEmployeeid(int i);

	List<HrmsEmployeeContact> findByActiveAndApprovedOrderByEmployeeid(int i, int j);

}
