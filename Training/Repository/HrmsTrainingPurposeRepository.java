package com.Hrms.Training.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Training.Entity.HrmsTrainingPurpose;

@Repository
public interface HrmsTrainingPurposeRepository extends JpaRepository<HrmsTrainingPurpose, Integer> {

	List<HrmsTrainingPurpose> findByActive(int i);

	boolean existsByCodeAndActive(int code, int i);

	boolean existsByNameAndActive(String name, int i);

	boolean existsByIdAndActive(int id, int i);

	HrmsTrainingPurpose findByIdAndActive(int id, int i);

}
