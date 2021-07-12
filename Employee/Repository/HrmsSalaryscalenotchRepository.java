package com.Hrms.Employee.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsSalaryscalenotch;

@Repository
public interface HrmsSalaryscalenotchRepository extends JpaRepository<HrmsSalaryscalenotch, Integer> {

	boolean existsByName(String name);

	boolean existsByIdAndActive(int notchId, int i);

}
