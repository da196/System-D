package com.Hrms.Employee.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsEmployeeAddress;

@Repository
public interface HrmsEmployeeAddressRepository extends JpaRepository<HrmsEmployeeAddress, Integer> {

	boolean existsByEmployeeid(int empid);

	HrmsEmployeeAddress findByAddressid(int aid);

	List<HrmsEmployeeAddress> findByEmployeeid(int empid);

	List<HrmsEmployeeAddress> findByEmployeeidAndActive(int empid, int i);

	List<HrmsEmployeeAddress> findByActiveOrderByEmployeeid(int i);

	List<HrmsEmployeeAddress> findByActiveAndApprovedOrderByEmployeeid(int i, int j);

}
