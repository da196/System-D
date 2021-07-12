package com.Hrms.Employee.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsEducationlevel;

@Repository
public interface HrmsEducationlevelRepository extends JpaRepository<HrmsEducationlevel, Integer> {

	boolean existsByName(String  name);

	List<HrmsEducationlevel> findByActive(int i);

}
