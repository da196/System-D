package com.Hrms.Employee.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsInsuranceProviderContact;

@Repository
public interface HrmsInsuranceProviderContactRepository extends JpaRepository<HrmsInsuranceProviderContact, Integer> {

}
