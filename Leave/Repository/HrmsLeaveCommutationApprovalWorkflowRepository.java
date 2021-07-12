package com.Hrms.Leave.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Leave.Entity.HrmsLeaveCommutationApprovalWorkflow;

@Repository
public interface HrmsLeaveCommutationApprovalWorkflowRepository
		extends JpaRepository<HrmsLeaveCommutationApprovalWorkflow, Integer> {

	boolean existsByIdAndActive(int id, int i);

	HrmsLeaveCommutationApprovalWorkflow findByIdAndActive(int id, int i);

	List<HrmsLeaveCommutationApprovalWorkflow> findByActive(int i);

	boolean existsBySupervisordesignationidAndActive(int firstsupervisordesignationid, int i);

	HrmsLeaveCommutationApprovalWorkflow findBySupervisordesignationidAndActive(int firstsupervisordesignationid,
			int i);

	boolean existsByNameAndActive(String name, int i);

}
