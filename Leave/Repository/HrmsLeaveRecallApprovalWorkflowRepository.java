package com.Hrms.Leave.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Leave.Entity.HrmsLeaveRecallApprovalWorkflow;

@Repository
public interface HrmsLeaveRecallApprovalWorkflowRepository
		extends JpaRepository<HrmsLeaveRecallApprovalWorkflow, Integer> {

	boolean existsByIdAndActive(int id, int i);

	HrmsLeaveRecallApprovalWorkflow findByIdAndActive(int id, int i);

	List<HrmsLeaveRecallApprovalWorkflow> findByActive(int i);

	boolean existsBySupervisordesignationidAndActive(int firstsupervisordesignationid, int i);

	HrmsLeaveRecallApprovalWorkflow findBySupervisordesignationidAndActive(int firstsupervisordesignationid, int i);

	boolean existsByNameAndActive(String name, int i);

}
