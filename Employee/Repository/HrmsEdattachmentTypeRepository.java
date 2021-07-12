package com.Hrms.Employee.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsEdattachmentType;

@Repository
public interface HrmsEdattachmentTypeRepository extends JpaRepository<HrmsEdattachmentType, Integer> {

	boolean existsByName(String name);

	boolean existsByIdAndActive(int id, int i);

	List<HrmsEdattachmentType> findByActive(int i);

}
