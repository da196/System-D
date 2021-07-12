package com.Hrms.Payroll.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Payroll.Entity.HrmsPayrollLoanFrequency;

@Repository
public interface HrmsPayrollLoanFrequencyRepository extends JpaRepository<HrmsPayrollLoanFrequency, Integer> {

	boolean existsByNameAndActive(String name, int i);

	boolean existsByIdAndActive(int id, int i);

	HrmsPayrollLoanFrequency findByIdAndActive(int id, int i);

	List<HrmsPayrollLoanFrequency> findByActive(int i);

}
