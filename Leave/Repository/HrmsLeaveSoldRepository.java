package com.Hrms.Leave.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Leave.Entity.HrmsLeaveSold;

@Repository
public interface HrmsLeaveSoldRepository extends JpaRepository<HrmsLeaveSold, Integer> {

	HrmsLeaveSold findByIdAndActive(int id, int i);

	boolean existsByIdAndActive(int id, int i);

	List<HrmsLeaveSold> findByActive(int i);

	List<HrmsLeaveSold> findByEmployeeidAndActive(int empId, int i);

	List<HrmsLeaveSold> findByEmployeeidAndLeavetypeidAndApprovedInAndActiveAndYearAndMonthIn(int empid,
			int leavetypeid, List<Integer> approved, int i, int currentYear, List<Integer> monthlist);

	List<HrmsLeaveSold> findByActiveAndApproved(int i, int j);

	List<HrmsLeaveSold> findByActiveOrderByIdDesc(int i);

	List<HrmsLeaveSold> findByEmployeeidAndActiveOrderByIdDesc(int empId, int i);

	boolean existsByIdAndActiveAndApproved(int leaveid, int i, int j);

	boolean existsByIdAndActiveAndEmpknowledge(int id, int i, int j);

	List<HrmsLeaveSold> findByApprovedAndActiveAndEmpknowledge(int i, int j, int k);

	boolean existsByIdAndEmployeeidAndActive(int id, int empid, int i);

	List<HrmsLeaveSold> findByActiveAndRequesteridAndApprovedOrderByIdDesc(int i, int supervisorid, int j);

	boolean existsByIdAndActiveAndApprovedAndEmpknowledge(int leaveid, int i, int j, int k);

}
