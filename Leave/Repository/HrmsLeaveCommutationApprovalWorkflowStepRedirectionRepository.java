package com.Hrms.Leave.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Leave.Entity.HrmsLeaveCommutationApprovalWorkflowStepRedirection;

@Repository
public interface HrmsLeaveCommutationApprovalWorkflowStepRedirectionRepository
		extends JpaRepository<HrmsLeaveCommutationApprovalWorkflowStepRedirection, Integer> {

}
