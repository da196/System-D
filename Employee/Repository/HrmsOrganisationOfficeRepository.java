package com.Hrms.Employee.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsOrganisationOffice;

@Repository
public interface HrmsOrganisationOfficeRepository extends JpaRepository<HrmsOrganisationOffice, Integer> {

	boolean existsByNameAndOfficetypeidAndActive(String name, int officetypeid, int i);

	boolean existsByIdAndActive(int dutystationid, int i);

	List<HrmsOrganisationOffice> findByActive(int i);

	List<HrmsOrganisationOffice> findByActiveOrderById(int i);

}
