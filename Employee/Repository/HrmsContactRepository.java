package com.Hrms.Employee.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsContact;

@Repository
public interface HrmsContactRepository extends JpaRepository<HrmsContact, Integer> {

	boolean existsByIdAndActive(int cid, int i);

	HrmsContact findByIdAndActive(int cid, int i);

}
