package com.Hrms.Employee.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsAttachment;

@Repository
public interface HrmsAttachmentRepository extends JpaRepository<HrmsAttachment, Integer> {

}
