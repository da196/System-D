package com.Hrms.Employee.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsEmployeeEmploymentStatusReason;

@Repository
public interface HrmsEmployeeEmploymentStatusReasonRepository
		extends JpaRepository<HrmsEmployeeEmploymentStatusReason, Integer> {

	boolean existsByIdAndActive(int employmentstatusreasonid, int i);

}
