package com.Hrms.Leave.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Leave.Entity.HrmsLeaveRecallApprovalRedirectionReason;

@Repository
public interface HrmsLeaveRecallApprovalRedirectionReasonRepository
		extends JpaRepository<HrmsLeaveRecallApprovalRedirectionReason, Integer> {

}
