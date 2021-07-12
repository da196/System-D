package com.Hrms.Leave.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Leave.Entity.HrmsLeaveRecallApprovalWorkflowStepRedirection;

@Repository
public interface HrmsLeaveRecallApprovalWorkflowStepRedirectionRepository
		extends JpaRepository<HrmsLeaveRecallApprovalWorkflowStepRedirection, Integer> {

}
