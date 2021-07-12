package com.Hrms.Employee.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsEmployeeActing;

@Repository
public interface HrmsEmployeeActingRepository extends JpaRepository<HrmsEmployeeActing, Integer> {

	List<HrmsEmployeeActing> findByActive(int i);

}
