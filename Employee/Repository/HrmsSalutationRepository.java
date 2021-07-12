package com.Hrms.Employee.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsSalutation;

@Repository
public interface HrmsSalutationRepository extends JpaRepository<HrmsSalutation, Integer> {

}
