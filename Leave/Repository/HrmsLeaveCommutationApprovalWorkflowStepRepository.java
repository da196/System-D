package com.Hrms.Leave.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Leave.Entity.HrmsLeaveCommutationApprovalWorkflowStep;

@Repository
public interface HrmsLeaveCommutationApprovalWorkflowStepRepository
		extends JpaRepository<HrmsLeaveCommutationApprovalWorkflowStep, Integer> {

	boolean existsByWorkflowidAndStepnumberAndActive(int workflowid, int stepnumber, int i);

	List<HrmsLeaveCommutationApprovalWorkflowStep> findByWorkflowidAndActive(int workflowId, int i);

	boolean existsByIdAndActive(int id, int i);

	List<HrmsLeaveCommutationApprovalWorkflowStep> findByAndActiveOrderByWorkflowidDesc(int i);

	List<HrmsLeaveCommutationApprovalWorkflowStep> findByWorkflowidAndActiveOrderByIdDesc(int workflowId, int i);

	boolean existsByWorkflowidAndApproverdesignationidAndActive(int workflowid, int designationId, int i);

	HrmsLeaveCommutationApprovalWorkflowStep findByWorkflowidAndApproverdesignationidAndActive(int workflowid,
			int designationId, int i);

	Integer countByWorkflowid(int workflowid);

	boolean existsByWorkflowidAndActive(int workflowid, int i);

	List<HrmsLeaveCommutationApprovalWorkflowStep> findByWorkflowidAndActiveOrderByStepnumberAsc(int workflowid, int i);

	HrmsLeaveCommutationApprovalWorkflowStep findByIdAndActive(int id, int i);

	List<HrmsLeaveCommutationApprovalWorkflowStep> findByActive(int i);

}
