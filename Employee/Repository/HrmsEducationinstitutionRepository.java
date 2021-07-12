package com.Hrms.Employee.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsEducationinstitution;

@Repository
public interface HrmsEducationinstitutionRepository extends JpaRepository<HrmsEducationinstitution, Integer> {

	boolean existsByName(String name);

	List<HrmsEducationinstitution> findAllByOrderByNameAsc();

	List<HrmsEducationinstitution> findByActiveOrderByNameAsc(int i);

	HrmsEducationinstitution findByIdAndActive(int id, int i);

	boolean existsByIdAndActive(int id, int i);

	List<HrmsEducationinstitution> findByCountryIdAndActiveOrderByNameAsc(int countryId, int i);

	boolean existsByNameAndActive(String name, int i);

}
