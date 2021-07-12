package com.Hrms.Leave.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Leave.Entity.HrmsLeaveApprovalWorkflowStep;

@Repository
public interface HrmsLeaveApprovalWorkflowStepRepository extends JpaRepository<HrmsLeaveApprovalWorkflowStep, Integer> {

	boolean existsByWorkflowidAndStepnumberAndActive(int workflowid, int stepnumber, int i);

	List<HrmsLeaveApprovalWorkflowStep> findByWorkflowidAndActive(int workflowId, int i);

	boolean existsByIdAndActive(int id, int i);

	List<HrmsLeaveApprovalWorkflowStep> findByAndActiveOrderByWorkflowidDesc(int i);

	List<HrmsLeaveApprovalWorkflowStep> findByWorkflowidAndActiveOrderByIdDesc(int workflowId, int i);

	boolean existsByWorkflowidAndApproverdesignationidAndActive(int workflowid, int designationId, int i);

	HrmsLeaveApprovalWorkflowStep findByWorkflowidAndApproverdesignationidAndActive(int workflowid, int designationId,
			int i);

	Integer countByWorkflowid(int workflowid);

	boolean existsByWorkflowidAndActive(int workflowid, int i);

	List<HrmsLeaveApprovalWorkflowStep> findByWorkflowidAndActiveOrderByStepnumberAsc(int workflowid, int i);

	HrmsLeaveApprovalWorkflowStep findByIdAndActive(int id, int i);

	List<HrmsLeaveApprovalWorkflowStep> findByActive(int i);

}
