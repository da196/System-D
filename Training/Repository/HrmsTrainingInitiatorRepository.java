package com.Hrms.Training.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Training.Entity.HrmsTrainingInitiator;

@Repository
public interface HrmsTrainingInitiatorRepository extends JpaRepository<HrmsTrainingInitiator, Integer> {

	List<HrmsTrainingInitiator> findByActive(int i);

	boolean existsByIdAndActive(int traininginitiatorid, int i);

	HrmsTrainingInitiator findByIdAndActive(int traininginitiatorid, int i);

	boolean existsByNameAndActive(String name, int i);

	boolean existsByCodeAndActive(int code, int i);

}
