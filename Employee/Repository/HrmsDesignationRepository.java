package com.Hrms.Employee.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsDesignation;

@Repository
public interface HrmsDesignationRepository extends JpaRepository<HrmsDesignation, Integer> {

	boolean existsByName(String name);

	boolean existsByIdAndActive(int id, int i);

	List<HrmsDesignation> findByActive(int i);

	HrmsDesignation findByIdAndActive(int supervisordesignationid, int i);

}
