package com.Hrms.Training.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Training.Entity.HrmsTrainingSponsor;

@Repository
public interface HrmsTrainingSponsorRepository extends JpaRepository<HrmsTrainingSponsor, Integer> {

	List<HrmsTrainingSponsor> findByActive(int i);

	boolean existsByIdAndActive(int trainingsponsorid, int i);

	HrmsTrainingSponsor findByIdAndActive(int trainingsponsorid, int i);

	boolean existsByNameAndActive(String name, int i);

	boolean existsByCodeAndActive(int code, int i);

}
