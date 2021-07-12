package com.Hrms.Training.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Training.Entity.HrmsTrainingApproval;

@Repository
public interface HrmsTrainingApprovalRepository extends JpaRepository<HrmsTrainingApproval, Integer> {

	Integer countByTrainingid(int id);

	boolean existsByApproveruseridAndTrainingidAndActive(int supervisorid, int id, int i);

	HrmsTrainingApproval findByApproveruseridAndTrainingidAndActive(int supervisorid, int id, int i);

	boolean existsByApproverdesignationidAndTrainingidAndActive(int designationprev, int id, int i);

	boolean existsByApproverdesignationidAndTrainingidAndActiveAndStatus(int designationprev, int id, int i, int j);

	HrmsTrainingApproval findByApproverdesignationidAndTrainingidAndActive(int approverdesignationid, int id, int i);

	boolean existsByStatusAndTrainingidAndActive(int i, int id, int j);

	HrmsTrainingApproval findFirstByApproveruseridAndTrainingidAndActive(int supervisorid, int id, int i);

}
