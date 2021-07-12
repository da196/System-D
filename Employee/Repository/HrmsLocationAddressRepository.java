package com.Hrms.Employee.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsLocationAddress;

@Repository
public interface HrmsLocationAddressRepository extends JpaRepository<HrmsLocationAddress, Integer> {

	boolean existsByIdAndActive(int aid, int i);

	HrmsLocationAddress findByIdAndActive(int aid, int i);

}
