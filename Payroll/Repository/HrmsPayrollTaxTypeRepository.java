package com.Hrms.Payroll.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Payroll.Entity.HrmsPayrollTaxType;

@Repository
public interface HrmsPayrollTaxTypeRepository extends JpaRepository<HrmsPayrollTaxType, Integer> {

	boolean existsByNameAndActive(String name, int i);

	boolean existsByIdAndActive(int id, int i);

	HrmsPayrollTaxType findByIdAndActive(int id, int i);

	List<HrmsPayrollTaxType> findByActive(int i);

}
