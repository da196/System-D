package com.Hrms.Employee.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsLocationCountry;

@Repository
public interface HrmsLocationCountryRepository extends JpaRepository<HrmsLocationCountry, Integer> {

	boolean existsByName(String  name);

	List<HrmsLocationCountry> findAllByOrderByIdAsc();

	List<HrmsLocationCountry> findByActiveOrderByIdAsc(int i);

}
