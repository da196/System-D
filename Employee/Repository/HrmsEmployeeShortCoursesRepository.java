package com.Hrms.Employee.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsEmployeeShortCourses;

@Repository
public interface HrmsEmployeeShortCoursesRepository extends JpaRepository<HrmsEmployeeShortCourses, Integer> {

	boolean existsByIdAndActive(int id, int i);

	HrmsEmployeeShortCourses findByIdAndActive(int id, int i);

	boolean existsByEmployeeidAndActive(int empid, int i);

	List<HrmsEmployeeShortCourses> findByEmployeeidAndActive(int empid, int i);

	List<HrmsEmployeeShortCourses> findByActive(int i);

	List<HrmsEmployeeShortCourses> findByActiveAndApprovedOrderByEmployeeid(int i, int j);

}
