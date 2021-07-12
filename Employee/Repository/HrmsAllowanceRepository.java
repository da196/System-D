package com.Hrms.Employee.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsAllowance;

@Repository
public interface HrmsAllowanceRepository extends JpaRepository<HrmsAllowance, Integer> {

	boolean existsByDesignationid(int id);

	List<HrmsAllowance> findByDesignationid(int id);

	boolean existsByDesignationidAndSalarystructureidAndEmploymentcategoryidAndAndActive(int designationId,
			int salarystructureId, int employmentcategoryId, int i);

	List<HrmsAllowance> findByDesignationidAndSalarystructureidAndEmploymentcategoryidAndAndActive(int designationId,
			int salarystructureId, int employmentcategoryId, int i);

	boolean existsByDesignationidAndAllowancetypeid(int designationId, int id);

	HrmsAllowance findByDesignationidAndSalarystructureidAndEmploymentcategoryidAndAllowancetypeidAndActive(
			int designationId, int salarystructureId, int employmentcategoryId, int id, int i);

	List<HrmsAllowance> findByActive(int i);

	List<HrmsAllowance> findBySalarystructureidAndActive(int salaryStructureId, int i);

	List<HrmsAllowance> findBySalarystructureidAndAllowancetypeidAndActive(int salaryStructureId, int i, int j);

	boolean existsBySalarystructureidAndAllowancetypeid(int salarystructureId, int id);

	HrmsAllowance findBySalarystructureidAndAllowancetypeidAndCurrencyidAndActive(int salarystructureId, int id,
			int currencyId, int i);

	List<HrmsAllowance> findByAllowancetypeidAndActive(int i, int j);

	boolean existsBySalarystructureidAndCurrencyidAndActive(int salarystructureId, int currencyId, int i);

	List<HrmsAllowance> findBySalarystructureidAndCurrencyidAndActive(int salarystructureId, int currencyId, int i);

}
