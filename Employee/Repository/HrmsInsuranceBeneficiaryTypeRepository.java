package com.Hrms.Employee.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsInsuranceBeneficiaryType;

@Repository
public interface HrmsInsuranceBeneficiaryTypeRepository extends JpaRepository<HrmsInsuranceBeneficiaryType, Integer> {

	boolean existsByName(String  name);

}
