package com.Hrms.Employee.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsEmployeeSocialSecurityScheme;

@Repository
public interface HrmsEmployeeSocialSecuritySchemeRepositoty
		extends JpaRepository<HrmsEmployeeSocialSecurityScheme, Integer> {

	boolean existsByEmployeeidAndActive(int employeeid, int i);

	boolean existsByEmployeeidAndServiceprovideridAndActive(int employeeid, int serviceproviderid, int i);

	boolean existsByIdAndActive(int id, int i);

	List<HrmsEmployeeSocialSecurityScheme> findByEmployeeidAndActive(int empId, int i);

	List<HrmsEmployeeSocialSecurityScheme> findByActive(int i);

	List<HrmsEmployeeSocialSecurityScheme> findByEmployeeidAndActiveOrderByIdDesc(int empId, int i);

	List<HrmsEmployeeSocialSecurityScheme> findByActiveOrderByIdDesc(int i);

	HrmsEmployeeSocialSecurityScheme findFirstByEmployeeidAndActive(int id, int i);

}
