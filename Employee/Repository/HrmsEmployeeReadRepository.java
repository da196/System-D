package com.Hrms.Employee.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsEmployeeRead;

@Repository
public interface HrmsEmployeeReadRepository extends JpaRepository<HrmsEmployeeRead, Integer> {

	boolean existsByEmail(String email);

	Optional<HrmsEmployeeRead> findByEmail(String email);

	List<HrmsEmployeeRead> findAllByOrderByIdDesc();

	List<HrmsEmployeeRead> findByActiveOrderByIdDesc(int i);

}
