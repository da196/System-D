package com.Hrms.Leave.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Leave.Entity.HrmsLeaveBalance;

@Repository
public interface HrmsLeaveBalanceRepository extends JpaRepository<HrmsLeaveBalance, Integer> {

	boolean existsByEmployeeidAndLeavetypeidAndActive(int employeeid, int leavetypeid, int i);

	HrmsLeaveBalance findByEmployeeidAndLeavetypeidAndActive(int employeeid, int leavetypeid, int i);

	List<HrmsLeaveBalance> findByEmployeeidAndActive(int empid, int i);

	List<HrmsLeaveBalance> findByEmployeeidInAndActive(List<Integer> employeeundersupervisor, int i);

}
