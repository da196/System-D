package com.Hrms.Payroll.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Payroll.Entity.HrmsPayrollLoanType;

@Repository
public interface HrmsPayrollLoanTypeRepository extends JpaRepository<HrmsPayrollLoanType, Integer> {

	boolean existsByNameAndActive(String name, int i);

	boolean existsByIdAndActive(int id, int i);

	HrmsPayrollLoanType findByIdAndActive(int id, int i);

	List<HrmsPayrollLoanType> findByActive(int i);

	List<HrmsPayrollLoanType> findByLenderid(int lenderid);

	List<HrmsPayrollLoanType> findByLenderidAndActive(int lenderid, int i);

}
