package com.Hrms.Payroll.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Payroll.Entity.HrmsPayrollLoanLender;

@Repository
public interface HrmsPayrollLoanLenderRepository extends JpaRepository<HrmsPayrollLoanLender, Integer> {

	boolean existsByNameAndActive(String name, int i);

	boolean existsByIdAndActive(int id, int i);

	HrmsPayrollLoanLender findByIdAndActive(int id, int i);

	List<HrmsPayrollLoanLender> findByActive(int i);

}
