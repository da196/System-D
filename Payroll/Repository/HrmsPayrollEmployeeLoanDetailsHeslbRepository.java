package com.Hrms.Payroll.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Payroll.Entity.HrmsPayrollEmployeeLoanDetailsHeslb;

@Repository
public interface HrmsPayrollEmployeeLoanDetailsHeslbRepository
		extends JpaRepository<HrmsPayrollEmployeeLoanDetailsHeslb, Integer> {

	boolean existsByCseeindexnumberAndActive(String cseeindexnumber, int i);

	boolean existsByIdAndActive(int id, int i);

	HrmsPayrollEmployeeLoanDetailsHeslb findByIdAndActive(int id, int i);

	List<HrmsPayrollEmployeeLoanDetailsHeslb> findByActive(int i);

	boolean existsByLoanidAndActive(int loanId, int i);

	HrmsPayrollEmployeeLoanDetailsHeslb findByLoanidAndActive(int loanId, int i);

	boolean existsByLoanid(int loanid);

	HrmsPayrollEmployeeLoanDetailsHeslb findByLoanid(int loanid);

}
