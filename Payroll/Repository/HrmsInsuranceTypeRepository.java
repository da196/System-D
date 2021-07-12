package com.Hrms.Payroll.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Payroll.Entity.HrmsInsuranceType;

@Repository
public interface HrmsInsuranceTypeRepository extends JpaRepository<HrmsInsuranceType, Integer> {

	boolean existsByNameAndActive(String name, int i);

	boolean existsByIdAndActive(int id, int i);

	HrmsInsuranceType findByIdAndActive(int id, int i);

	List<HrmsInsuranceType> findByActive(int i);

}
