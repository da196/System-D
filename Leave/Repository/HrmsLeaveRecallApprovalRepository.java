package com.Hrms.Leave.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Leave.Entity.HrmsLeaveRecallApproval;

@Repository
public interface HrmsLeaveRecallApprovalRepository extends JpaRepository<HrmsLeaveRecallApproval, Integer> {

	Integer countByLeaveid(int leaveid);

	boolean existsByApproverdesignationidAndLeaveidAndActive(int approverdesignationid, int id, int i);

	HrmsLeaveRecallApproval findByApproverdesignationidAndLeaveidAndActive(int approverdesignationid, int id, int i);

	boolean existsByStatusAndLeaveidAndActive(int i, int id, int j);

	boolean existsByApproveruseridAndLeaveidAndActive(int supervisorid, int id, int i);

	HrmsLeaveRecallApproval findByApproveruseridAndLeaveidAndActive(int supervisordbm, int id, int i);

	boolean existsByApproverdesignationidAndLeaveidAndActiveAndStatus(int designationprev, int id, int i, int j);

	HrmsLeaveRecallApproval findFirstByApproveruseridAndLeaveidAndActive(int supervisordbm, int id, int i);

	boolean existsByLeaveidAndActiveAndStatus(int id, int i, int j);

	HrmsLeaveRecallApproval findFirstByLeaveidAndActiveAndStatus(int id, int i, int j);

	boolean existsByLeaveidAndActive(int id, int i);

}
