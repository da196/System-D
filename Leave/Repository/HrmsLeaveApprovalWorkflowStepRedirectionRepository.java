package com.Hrms.Leave.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Leave.Entity.HrmsLeaveApprovalWorkflowStepRedirection;

@Repository
public interface HrmsLeaveApprovalWorkflowStepRedirectionRepository
		extends JpaRepository<HrmsLeaveApprovalWorkflowStepRedirection, Integer> {

}
