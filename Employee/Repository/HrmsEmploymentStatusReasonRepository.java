package com.Hrms.Employee.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsEmploymentStatusReason;

@Repository
public interface HrmsEmploymentStatusReasonRepository extends JpaRepository<HrmsEmploymentStatusReason, Integer> {

	List<HrmsEmploymentStatusReason> findByActive(int i);

	boolean existsByIdAndActive(int employmentstatusreasonid, int i);

}
