package com.Hrms.Training.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Training.Entity.HrmsTrainingApprovalRedirectionReason;

@Repository
public interface HrmsTrainingApprovalRedirectionReasonRepository
		extends JpaRepository<HrmsTrainingApprovalRedirectionReason, Integer> {

	List<HrmsTrainingApprovalRedirectionReason> findByActive(int i);

	boolean existsByIdAndActive(int riderectionreasonid, int i);

}
