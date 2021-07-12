package com.Hrms.Employee.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsEducationcourse;

@Repository
public interface HrmsEducationcourseRepository extends JpaRepository<HrmsEducationcourse, Integer> {

	boolean existsByName(String name);

	List<HrmsEducationcourse> findAllByOrderByNameAsc();

	boolean existsByEducationlevelid(int educationlevelid);

	List<HrmsEducationcourse> findByActiveOrderByNameAsc(int i);

}
