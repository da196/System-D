package com.Hrms.Payroll.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Payroll.Entity.HrmsPayroll;

@Repository
public interface HrmsPayrollRepository extends JpaRepository<HrmsPayroll, Integer> {

	boolean existsByDatepayAndActive(Date period, int i);

	boolean existsByYearAndMonthAndActive(int year, int month, int i);

	Integer countByYearAndMonthAndActive(int year, int month, int i);

	boolean existsByEmployeeidAndYearAndMonthAndActive(int id, int year, int month, int i);

	HrmsPayroll findByEmployeeidAndYearAndMonthAndActive(int empId, int year, int month, int i);

	HrmsPayroll findByIdAndActive(int payrollid, int i);

	boolean existsByIdAndActive(int payrollid, int i);

	boolean existsByEmployeeidAndActive(int empid, int i);

	List<HrmsPayroll> findByEmployeeidAndActive(int empid, int i);

	List<HrmsPayroll> findByActive(int i);

	List<HrmsPayroll> findByYearAndMonthAndActive(int year, int month, int i);

	List<HrmsPayroll> findByYearAndActive(int year, int i);

	List<HrmsPayroll> findByYearAndMonthAndEmployeeidInAndActive(int year, int month, List<Integer> zssfmember, int i);

	List<HrmsPayroll> findByYearAndEmployeeidInAndActive(int year, List<Integer> zssfmember, int i);

}
