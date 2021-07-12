package com.Hrms.Employee.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsInsuranceProviderAddress;

@Repository
public interface HrmsInsuranceProviderAddressRepository extends JpaRepository<HrmsInsuranceProviderAddress, Integer> {

}
