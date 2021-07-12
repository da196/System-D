package com.Hrms.Training.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Training.Entity.HrmsTrainingApprovalWorkflow;

@Repository
public interface HrmsTrainingApprovalWorkflowRepository extends JpaRepository<HrmsTrainingApprovalWorkflow, Integer> {

	List<HrmsTrainingApprovalWorkflow> findByActive(int i);

	boolean existsBySupervisordesignationidAndActive(int id, int i);

	HrmsTrainingApprovalWorkflow findBySupervisordesignationidAndActive(int id, int i);

	boolean existsByNameAndActive(String name, int i);

	boolean existsByIdAndActive(int workflowid, int i);

	HrmsTrainingApprovalWorkflow findByIdAndActive(int workflowid, int i);

}
