package com.Hrms.Leave.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Leave.Entity.HrmsLeaveType;

@Repository
public interface HrmsLeaveTypeRepository extends JpaRepository<HrmsLeaveType, Integer> {

	boolean existsByCodeAndActive(int code, int i);

	boolean existsByNameAndActive(String name, int i);

	boolean existsByIdAndActive(int id, int i);

	HrmsLeaveType findByIdAndActive(int id, int i);

	List<HrmsLeaveType> findByActive(int i);

}
