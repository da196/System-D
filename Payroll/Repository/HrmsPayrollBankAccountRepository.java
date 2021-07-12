package com.Hrms.Payroll.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Payroll.Entity.HrmsPayrollBankAccount;

@Repository
public interface HrmsPayrollBankAccountRepository extends JpaRepository<HrmsPayrollBankAccount, Integer> {

	List<HrmsPayrollBankAccount> findByYearAndMonthAndActive(int year, int month, int i);

	List<HrmsPayrollBankAccount> findByEmployeeidAndYearAndMonthAndActive(int empid, int year, int month, int i);

	List<HrmsPayrollBankAccount> findByYearAndMonthAndActiveOrderByEmployeeid(int year, int month, int i);

	int countDistinctEmployeeidByYearAndMonthAndActive(int year, int month, int i);

	int countDistinctEmployeeidByEmployeeidAndYearAndMonthAndActive(int empid, int year, int month, int i);

}
