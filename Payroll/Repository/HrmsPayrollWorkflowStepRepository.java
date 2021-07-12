package com.Hrms.Payroll.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Payroll.Entity.HrmsPayrollWorkflowStep;

@Repository
public interface HrmsPayrollWorkflowStepRepository extends JpaRepository<HrmsPayrollWorkflowStep, Integer> {

	boolean existsByIdAndActive(int id, int i);

	List<HrmsPayrollWorkflowStep> findByActive(int i);

	List<HrmsPayrollWorkflowStep> findByActiveAndApproved(int i, int j);

	boolean existsByStepnumberAndActive(int stepnumber, int i);

}
