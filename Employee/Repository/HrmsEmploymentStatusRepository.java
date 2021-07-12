package com.Hrms.Employee.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsEmploymentStatus;

@Repository
public interface HrmsEmploymentStatusRepository extends JpaRepository<HrmsEmploymentStatus, Integer> {

	boolean existsByName(String name);

	List<HrmsEmploymentStatus> findByActive(int i);

}
