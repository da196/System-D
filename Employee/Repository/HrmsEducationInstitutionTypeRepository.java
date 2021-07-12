package com.Hrms.Employee.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsEducationInstitutionType;

@Repository
public interface HrmsEducationInstitutionTypeRepository extends JpaRepository<HrmsEducationInstitutionType, Integer> {

	List<HrmsEducationInstitutionType> findByActive(int i);

}
