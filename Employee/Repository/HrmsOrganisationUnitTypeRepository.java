package com.Hrms.Employee.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsOrganisationUnitType;

@Repository
public interface HrmsOrganisationUnitTypeRepository extends JpaRepository<HrmsOrganisationUnitType, Integer> {

}
