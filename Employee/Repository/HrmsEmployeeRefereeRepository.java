package com.Hrms.Employee.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsEmployeeReferee;

@Repository
public interface HrmsEmployeeRefereeRepository extends JpaRepository<HrmsEmployeeReferee, Integer> {

	boolean existsByEmployeeid(int id);

	List<HrmsEmployeeReferee> findByEmployeeid(int id);

	List<HrmsEmployeeReferee> findByEmployeeidAndActive(int id, int i);

	HrmsEmployeeReferee findByIdAndActive(int id, int i);

	boolean existsByEmployeeidAndActive(int id, int i);

	boolean existsByIdAndActive(int id, int i);

	boolean existsByEmployeeidAndActiveAndFullname(int employeeid, int i, String fullname);

	List<HrmsEmployeeReferee> findByActiveOrderByEmployeeid(int i);

	List<HrmsEmployeeReferee> findByActiveAndApprovedOrderByEmployeeid(int i, int j);

}
