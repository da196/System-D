package com.Hrms.Employee.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsEmployeeReligion;

@Repository
public interface HrmsEmployeeReligionRepository extends JpaRepository<HrmsEmployeeReligion, Integer> {

	boolean existsByName(String name);

	List<HrmsEmployeeReligion> findByActive(int i);

}
