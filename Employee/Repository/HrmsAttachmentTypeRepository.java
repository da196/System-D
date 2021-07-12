package com.Hrms.Employee.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsAttachmentType;

@Repository
public interface HrmsAttachmentTypeRepository extends JpaRepository<HrmsAttachmentType, Integer> {

	List<HrmsAttachmentType> findByActive(int i);

}
