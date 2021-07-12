package com.Hrms.Payroll.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Payroll.Entity.HrmsEmployeeBankAccount;

@Repository
public interface HrmsEmployeeBankAccountRepository extends JpaRepository<HrmsEmployeeBankAccount, Integer> {

	boolean existsByIdAndActive(int id, int i);

	boolean existsByEmployeeidAndActive(int empId, int i);

	List<HrmsEmployeeBankAccount> findByEmployeeid(int empId);

	List<HrmsEmployeeBankAccount> findByActive(int i);

	List<HrmsEmployeeBankAccount> findByActiveAndApproved(int i, int j);

	boolean existsByIdAndActiveAndApproved(int id, int i, int j);

	boolean existsByAccountnumberAndActive(String accountnumber, int i);

	List<HrmsEmployeeBankAccount> findByEmployeeidAndActive(int empid, int i);

	List<HrmsEmployeeBankAccount> findByEmployeeidAndActiveOrderByPriorityAsc(int empid, int i);

	int countByEmployeeidAndActive(int empid, int i);

	boolean existsByEmployeeidAndPriorityAndActive(int employeeid, int priority, int i);

}
