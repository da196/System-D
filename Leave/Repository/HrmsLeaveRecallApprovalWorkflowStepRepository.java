package com.Hrms.Leave.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Leave.Entity.HrmsLeaveRecallApprovalWorkflowStep;

@Repository
public interface HrmsLeaveRecallApprovalWorkflowStepRepository
		extends JpaRepository<HrmsLeaveRecallApprovalWorkflowStep, Integer> {

	boolean existsByWorkflowidAndStepnumberAndActive(int workflowid, int stepnumber, int i);

	List<HrmsLeaveRecallApprovalWorkflowStep> findByWorkflowidAndActive(int workflowId, int i);

	boolean existsByIdAndActive(int id, int i);

	List<HrmsLeaveRecallApprovalWorkflowStep> findByAndActiveOrderByWorkflowidDesc(int i);

	List<HrmsLeaveRecallApprovalWorkflowStep> findByWorkflowidAndActiveOrderByIdDesc(int workflowId, int i);

	boolean existsByWorkflowidAndApproverdesignationidAndActive(int workflowid, int designationId, int i);

	HrmsLeaveRecallApprovalWorkflowStep findByWorkflowidAndApproverdesignationidAndActive(int workflowid,
			int designationId, int i);

	Integer countByWorkflowid(int workflowid);

	boolean existsByWorkflowidAndActive(int workflowid, int i);

	List<HrmsLeaveRecallApprovalWorkflowStep> findByWorkflowidAndActiveOrderByStepnumberAsc(int workflowid, int i);

	HrmsLeaveRecallApprovalWorkflowStep findByIdAndActive(int id, int i);

	List<HrmsLeaveRecallApprovalWorkflowStep> findByActive(int i);
}
