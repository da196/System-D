package com.Hrms.Employee.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsLocationDistrict;

@Repository
public interface HrmsLocationDistrictRepository extends JpaRepository<HrmsLocationDistrict, Integer> {

	boolean existsByName(String name);

	List<HrmsLocationDistrict> findByCityid(int cityId);

}
