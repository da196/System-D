package com.Hrms.Payroll.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Payroll.Entity.HrmsPayrollWorkflow;

@Repository
public interface HrmsPayrollWorkflowRepository extends JpaRepository<HrmsPayrollWorkflow, Integer> {

	boolean existsByNameAndActive(String name, int i);

	boolean existsByIdAndActive(int id, int i);

	HrmsPayrollWorkflow findByIdAndActive(int id, int i);

	List<HrmsPayrollWorkflow> findByActive(int i);

	List<HrmsPayrollWorkflow> findByActiveAndApproved(int i, int j);

}
