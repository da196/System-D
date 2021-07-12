package com.Hrms.Payroll.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Payroll.Entity.HrmsPayrollDeductionTax;

@Repository
public interface HrmsPayrollDeductionTaxRepository extends JpaRepository<HrmsPayrollDeductionTax, Integer> {

}
