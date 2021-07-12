package com.Hrms.Training.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Training.Entity.HrmsTrainingApprovalWorkflowStep;

@Repository
public interface HrmsTrainingApprovalWorkflowStepRepository
		extends JpaRepository<HrmsTrainingApprovalWorkflowStep, Integer> {

	List<HrmsTrainingApprovalWorkflowStep> findByActive(int i);

	boolean existsByIdAndActive(int stepid, int i);

	HrmsTrainingApprovalWorkflowStep findByIdAndActive(int stepid, int i);

	boolean existsByWorkflowidAndActive(int workflowid, int i);

	boolean existsByWorkflowidAndApproverdesignationidAndActive(int workflowid, int designationId, int i);

	HrmsTrainingApprovalWorkflowStep findByWorkflowidAndApproverdesignationidAndActive(int workflowid,
			int designationId, int i);

	Integer countByWorkflowid(int workflowid);

	List<HrmsTrainingApprovalWorkflowStep> findByWorkflowidAndActive(int workflowid, int i);

	List<HrmsTrainingApprovalWorkflowStep> findByWorkflowidAndActiveOrderByStepnumberAsc(int workflowid, int i);

}
