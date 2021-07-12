package com.Hrms.Employee.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsGender;

@Repository
public interface HrmsGenderRepository extends JpaRepository<HrmsGender, Integer> {

}
