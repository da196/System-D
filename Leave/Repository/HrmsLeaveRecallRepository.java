package com.Hrms.Leave.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Leave.Entity.HrmsLeaveRecall;

@Repository
public interface HrmsLeaveRecallRepository extends JpaRepository<HrmsLeaveRecall, Integer> {

	boolean existsByIdAndActiveAndApproved(int id, int i, int j);

	HrmsLeaveRecall findByIdAndActiveAndApproved(int id, int i, int j);

	boolean existsByIdAndActive(int id, int i);

	List<HrmsLeaveRecall> findByActive(int i);

	List<HrmsLeaveRecall> findByActiveAndApproved(int i, int j);

	List<HrmsLeaveRecall> findByActiveOrderByIdDesc(int i);

	boolean existsByIdAndActiveAndApprovedAndEmpknowledge(int leaveid, int i, int j, int k);

	HrmsLeaveRecall findByIdAndActive(int leaveid, int i);

	boolean existsByIdAndActiveAndEmpknowledge(int id, int i, int j);

	List<HrmsLeaveRecall> findByApprovedAndActiveAndEmpknowledge(int i, int j, int k);

	List<HrmsLeaveRecall> findByActiveAndEmployeeidOrderByIdDesc(int i, int employeeid);

	boolean existsByIdAndEmployeeidAndActive(int id, int empid, int i);

}
