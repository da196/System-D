package com.Hrms.Leave.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Leave.Entity.HrmsLeaveApproval;

@Repository
public interface HrmsLeaveApprovalRepository extends JpaRepository<HrmsLeaveApproval, Integer> {

	Integer countByLeaveid(int leaveid);

	boolean existsByApproverdesignationidAndLeaveidAndActive(int approverdesignationid, int id, int i);

	HrmsLeaveApproval findByApproverdesignationidAndLeaveidAndActive(int approverdesignationid, int id, int i);

	boolean existsByStatusAndLeaveidAndActive(int i, int id, int j);

	boolean existsByApproveruseridAndLeaveidAndActive(int supervisorid, int id, int i);

	HrmsLeaveApproval findByApproveruseridAndLeaveidAndActive(int supervisordbm, int id, int i);

	boolean existsByApproverdesignationidAndLeaveidAndActiveAndStatus(int designationprev, int id, int i, int j);

	HrmsLeaveApproval findFirstByApproveruseridAndLeaveidAndActive(int supervisordbm, int id, int i);

	boolean existsByLeaveidAndActiveAndStatus(int id, int i, int j);

	HrmsLeaveApproval findFirstByLeaveidAndActiveAndStatus(int id, int i, int j);

	boolean existsByLeaveidAndActive(int id, int i);

}
