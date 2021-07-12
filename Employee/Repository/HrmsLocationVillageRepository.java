package com.Hrms.Employee.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsLocationVillage;

@Repository
public interface HrmsLocationVillageRepository extends JpaRepository<HrmsLocationVillage, Integer> {

}
