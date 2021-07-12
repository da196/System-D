package com.Hrms.Employee.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.Hrmsemployeesalary;

@Repository
public interface HrmsemployeesalaryRepository extends JpaRepository<Hrmsemployeesalary, Integer> {

	Hrmsemployeesalary findByIdAndActive(int id, int i);

	Hrmsemployeesalary findByEmployeeidAndActive(int id, int i);

	boolean existsByEmployeeidAndActive(int id, int i);

	boolean existsByIdAndActive(int id, int i);

	List<Hrmsemployeesalary> findByActive(int i);

}