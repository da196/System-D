package com.Hrms.Employee.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsLocationWard;

@Repository
public interface HrmsLocationWardRepository extends JpaRepository<HrmsLocationWard, Integer> {

	boolean existsByName(String name);

	List<HrmsLocationWard> findByDistrictid(int districtId);

}
