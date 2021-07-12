package com.Hrms.Payroll.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Payroll.Entity.HrmsPayrollType;

@Repository
public interface HrmsPayrollTypeRepository extends JpaRepository<HrmsPayrollType, Integer> {

	boolean existsByNameAndActive(String name, int i);

	HrmsPayrollType findByIdAndActive(int id, int i);

	boolean existsByIdAndActive(int id, int i);

	List<HrmsPayrollType> findByActive(int i);

}
