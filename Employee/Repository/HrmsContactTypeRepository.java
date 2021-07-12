package com.Hrms.Employee.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsContactType;

@Repository
public interface HrmsContactTypeRepository extends JpaRepository<HrmsContactType, Integer> {

	List<HrmsContactType> findByActive(int i);

}
