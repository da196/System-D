package com.Hrms.Payroll.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Payroll.Entity.HrmsBankBranch;

@Repository
public interface HrmsBankBranchRepository extends JpaRepository<HrmsBankBranch, Integer> {

	boolean existsByNameAndActive(String name, int i);

	boolean existsByIdAndActive(int id, int i);

	HrmsBankBranch findByIdAndActive(int id, int i);

	List<HrmsBankBranch> findByActive(int i);

	List<HrmsBankBranch> findByBankidAndActive(int bankid, int i);

}
