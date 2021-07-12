package com.Hrms.Payroll.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Payroll.Entity.HrmsBank;

@Repository
public interface HrmsBankRepository extends JpaRepository<HrmsBank, Integer> {

	boolean existsByNameAndActive(String name, int i);

	boolean existsByIdAndActive(int id, int i);

	HrmsBank findByIdAndActive(int id, int i);

	List<HrmsBank> findByActive(int i);

}
