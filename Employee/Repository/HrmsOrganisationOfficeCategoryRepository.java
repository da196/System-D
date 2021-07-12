package com.Hrms.Employee.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsOrganisationOfficeCategory;

@Repository
public interface HrmsOrganisationOfficeCategoryRepository
		extends JpaRepository<HrmsOrganisationOfficeCategory, Integer> {

}
