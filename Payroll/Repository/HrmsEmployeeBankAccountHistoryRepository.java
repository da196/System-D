package com.Hrms.Payroll.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Payroll.Entity.HrmsEmployeeBankAccountHistory;

@Repository
public interface HrmsEmployeeBankAccountHistoryRepository
		extends JpaRepository<HrmsEmployeeBankAccountHistory, Integer> {

}
