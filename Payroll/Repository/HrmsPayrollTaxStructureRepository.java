package com.Hrms.Payroll.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Payroll.Entity.HrmsPayrollTaxStructure;

@Repository
public interface HrmsPayrollTaxStructureRepository extends JpaRepository<HrmsPayrollTaxStructure, Integer> {

	boolean existsByTaxtypeidAndCurrencyidAndActive(int taxtypeid, int currencyid, int i);

	boolean existsByIdAndActive(int id, int i);

	HrmsPayrollTaxStructure findByIdAndActive(int id, int i);

	List<HrmsPayrollTaxStructure> findByActive(int i);

	boolean existsByTaxtypeidAndCurrencyidAndActiveAndAmountmaxAndAmountmin(int taxtypeid, int currencyid, int i,
			Double amountmax, Double amountmin);

}
