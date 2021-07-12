package com.Hrms.Employee.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsEmployeeBirthDetails;

@Repository
public interface HrmsEmployeeBirthDetailsRepository extends JpaRepository<HrmsEmployeeBirthDetails, Integer> {

	HrmsEmployeeBirthDetails findByEmployeeidAndActive(int employeeid, int i);

	HrmsEmployeeBirthDetails findByEmployeeid(int empid);

	boolean existsByEmployeeidAndActive(int employeeid, int i);

	boolean existsByIdAndActive(int id, int i);

	List<HrmsEmployeeBirthDetails> findByActive(int i);

	List<HrmsEmployeeBirthDetails> findByActiveOrderByEmployeeid(int i);

	List<HrmsEmployeeBirthDetails> findByActiveAndApprovedOrderByEmployeeid(int i, int j);

}
