package com.Hrms.Employee.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsUserRead;

@Repository
public interface HrmsUserReadRepository extends JpaRepository<HrmsUserRead, Integer> {

	HrmsUserRead findByEmail(String username);

	boolean existsByEmail(String username);

}
