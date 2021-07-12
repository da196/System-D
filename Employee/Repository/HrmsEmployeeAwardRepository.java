package com.Hrms.Employee.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsEmployeeAward;

@Repository
public interface HrmsEmployeeAwardRepository extends JpaRepository<HrmsEmployeeAward, Integer> {

}
