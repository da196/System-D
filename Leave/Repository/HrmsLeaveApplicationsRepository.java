package com.Hrms.Leave.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Leave.Entity.HrmsLeaveApplications;

@Repository
public interface HrmsLeaveApplicationsRepository extends JpaRepository<HrmsLeaveApplications, Integer> {

	boolean existsByEmployeeidAndLeavetypeidAndStartdateAndEnddateAndActive(int employeeid, int leavetypeid,
			Date startdate, Date enddate, int i);

	boolean existsByEmployeeidAndLeavetypeidAndLeaveallowanceapplicableAndApprovedAndActiveAndYear(int employeeid,
			int leavetypeid, int leaveallowanceapplicable, int i, int j, int k);

	HrmsLeaveApplications findByEmployeeidAndLeavetypeidAndLeaveallowanceapplicableAndApprovedAndActiveAndYear(
			int employeeid, int leavetypeid, int leaveallowanceapplicable, int i, int j, int currentYear);

	HrmsLeaveApplications findByEmployeeidAndLeavetypeidAndLeaveallowanceapplicableAndApprovedAndActiveAndYearOrYear(
			int employeeid, int leavetypeid, int leaveallowanceapplicable, int i, int j, int k, int currentYear);

	boolean existsByIdAndActiveAndApproved(int leaveid, int i, int j);

	List<HrmsLeaveApplications> findByActiveAndApproved(int i, int j);

	List<HrmsLeaveApplications> findByActive(int i);

	List<HrmsLeaveApplications> findByEmployeeidAndActive(int empid, int i);

	List<HrmsLeaveApplications> findByEmployeeidInAndActive(List<Integer> employeeundersupervisor, int i);

	HrmsLeaveApplications findByIdAndActive(int leaveid, int i);

	List<HrmsLeaveApplications> findByEmployeeidAndLeavetypeidAndApprovedAndActiveAndYearAndMonthIn(int empid,
			int leavetypeid, int i, int j, int currentYear, List<Integer> monthlist);

	List<HrmsLeaveApplications> findByEmployeeidAndLeavetypeidAndApprovedInAndActiveAndYearAndMonthIn(int empid,
			int leavetypeid, List<Integer> approved, int i, int currentYear, List<Integer> monthlist);

	boolean existsByEmployeeidAndLeavetypeidAndApprovedAndActive(int employeeid, int i, int j, int k);

	List<HrmsLeaveApplications> findByEmployeeidAndLeavetypeidAndApprovedAndActiveAndYearAndMonth(int employeeid,
			int leavetypeid, int i, int j, int year, int month);

	List<HrmsLeaveApplications> findByEmployeeidAndLeavetypeidAndApprovedAndActive(int employeeid, int i, int j, int k);

	List<HrmsLeaveApplications> findByActiveOrderByIdDesc(int i);

	List<HrmsLeaveApplications> findByEmployeeidAndActiveOrderByIdDesc(int empid, int i);

	List<HrmsLeaveApplications> findByActiveAndApprovedOrderByIdDesc(int i, int j);

	boolean existsByIdAndActive(int leaveid, int i);

	List<HrmsLeaveApplications> findByApprovedAndActive(int i, int j);

	List<HrmsLeaveApplications> findByEmployeeidInAndActiveAndApproved(List<Integer> employeeundersupervisor, int i,
			int j);

}
