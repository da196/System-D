package com.Hrms.Training.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Training.Entity.HrmsTrainingApprovalWorkflowStepRedirection;

@Repository
public interface HrmsTrainingApprovalWorkflowStepRedirectionRepository
		extends JpaRepository<HrmsTrainingApprovalWorkflowStepRedirection, Integer> {

	boolean existsByStepidAndActive(int stepid, int i);

	boolean existsByIdAndActive(int id, int i);

	HrmsTrainingApprovalWorkflowStepRedirection findByIdAndActive(int id, int i);

	List<HrmsTrainingApprovalWorkflowStepRedirection> findByActive(int i);

}
