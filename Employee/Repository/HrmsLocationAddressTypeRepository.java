package com.Hrms.Employee.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsLocationAddressType;

@Repository
public interface HrmsLocationAddressTypeRepository extends JpaRepository<HrmsLocationAddressType, Integer> {

	boolean existsByName(String  name);

}
