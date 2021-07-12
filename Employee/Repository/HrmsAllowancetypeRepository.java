package com.Hrms.Employee.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsAllowancetype;

@Repository
public interface HrmsAllowancetypeRepository extends JpaRepository<HrmsAllowancetype, Integer> {

	boolean existsByName(String name);

	boolean existsByIdAndActive(int id, int i);

	List<HrmsAllowancetype> findByActive(int i);

}
