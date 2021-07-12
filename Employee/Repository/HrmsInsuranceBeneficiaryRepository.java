package com.Hrms.Employee.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsInsuranceBeneficiary;

@Repository
public interface HrmsInsuranceBeneficiaryRepository extends JpaRepository<HrmsInsuranceBeneficiary, Integer> {

}
