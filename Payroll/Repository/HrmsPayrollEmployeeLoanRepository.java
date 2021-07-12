package com.Hrms.Payroll.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Payroll.Entity.HrmsPayrollEmployeeLoan;

@Repository
public interface HrmsPayrollEmployeeLoanRepository extends JpaRepository<HrmsPayrollEmployeeLoan, Integer> {

	boolean existsByEmployeeidAndLenderidAndLoantypeidAndActive(int employeeid, int lenderid, int loantypeid, int i);

	boolean existsByIdAndActive(int id, int i);

	HrmsPayrollEmployeeLoan findByIdAndActive(int id, int i);

	boolean existsByEmployeeidAndActive(int empId, int i);

	List<HrmsPayrollEmployeeLoan> findByEmployeeidAndActive(int empId, int i);

	List<HrmsPayrollEmployeeLoan> findByActive(int i);

	boolean existsByEmployeeidAndStatusAndActive(int id, int i, int j);

	List<HrmsPayrollEmployeeLoan> findByEmployeeidAndStatusAndActive(int id, int i, int j);

	boolean existsByEmployeeidAndStatusInAndActive(int id, Integer[] loanstatus, int i);

	List<HrmsPayrollEmployeeLoan> findByEmployeeidAndStatusInAndActive(int id, Integer[] loanstatus, int i);

	boolean existsByStatusAndActive(int status, int i);

	List<HrmsPayrollEmployeeLoan> findByStatusAndActive(int status, int i);

	List<HrmsPayrollEmployeeLoan> findByActiveOrderByIdDesc(int i);

	List<HrmsPayrollEmployeeLoan> findByEmployeeidAndActiveOrderByIdDesc(int empId, int i);

}
