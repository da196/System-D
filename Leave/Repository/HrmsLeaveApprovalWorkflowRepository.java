package com.Hrms.Leave.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Leave.Entity.HrmsLeaveApprovalWorkflow;

@Repository
public interface HrmsLeaveApprovalWorkflowRepository extends JpaRepository<HrmsLeaveApprovalWorkflow, Integer> {

	boolean existsByIdAndActive(int id, int i);

	HrmsLeaveApprovalWorkflow findByIdAndActive(int id, int i);

	List<HrmsLeaveApprovalWorkflow> findByActive(int i);

	boolean existsBySupervisordesignationidAndActive(int firstsupervisordesignationid, int i);

	HrmsLeaveApprovalWorkflow findBySupervisordesignationidAndActive(int firstsupervisordesignationid, int i);

	boolean existsByNameAndActive(String name, int i);

}
