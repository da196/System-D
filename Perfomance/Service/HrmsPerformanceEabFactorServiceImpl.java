package com.Hrms.Perfomance.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Perfomance.DTO.PerformanceEabFactorWithQualitiesResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEabFactor;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEabQuality;
import com.Hrms.Perfomance.Repository.HrmsPerformanceEabFactorRepository;
import com.Hrms.Perfomance.Repository.HrmsPerformanceEabQualityRepository;

@Service
public class HrmsPerformanceEabFactorServiceImpl implements HrmsPerformanceEabFactorService {

	@Autowired
	private HrmsPerformanceEabFactorRepository hrmsPerformanceEabFactorRepository;

	@Autowired
	private HrmsPerformanceEabQualityRepository hrmsPerformanceEabQualityRepository;

	@Override
	public ResponseEntity<HrmsPerformanceEabFactor> addPerformanceEabFactor(
			HrmsPerformanceEabFactor hrmsPerformanceEabFactor) {
		if (hrmsPerformanceEabFactorRepository.existsByNameAndActive(hrmsPerformanceEabFactor.getName(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsPerformanceEabFactor);
		} else {
			hrmsPerformanceEabFactor.setActive(1);
			hrmsPerformanceEabFactor.setApproved(0);
			hrmsPerformanceEabFactor.setUnique_id(UUID.randomUUID());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPerformanceEabFactorRepository.saveAndFlush(hrmsPerformanceEabFactor));

		}
	}

	@Override
	public ResponseEntity<HrmsPerformanceEabFactor> getPerformanceEabFactorById(int id) {
		if (hrmsPerformanceEabFactorRepository.existsByIdAndActive(id, 1)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPerformanceEabFactorRepository.findByIdAndActive(id, 1));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsPerformanceEabFactor> updatePerformanceEabFactor(
			HrmsPerformanceEabFactor hrmsPerformanceEabFactor, int id) {
		if (hrmsPerformanceEabFactorRepository.existsByIdAndActive(id, 1)) {
			hrmsPerformanceEabFactor.setActive(1);
			hrmsPerformanceEabFactor.setApproved(0);
			hrmsPerformanceEabFactor.setDate_updated(LocalDateTime.now());
			if (hrmsPerformanceEabFactorRepository.findById(id).get().getDate_created() != null) {
				hrmsPerformanceEabFactor
						.setDate_created(hrmsPerformanceEabFactorRepository.findById(id).get().getDate_created());
			}

			if (hrmsPerformanceEabFactorRepository.findById(id).get().getUnique_id() != null) {
				hrmsPerformanceEabFactor
						.setUnique_id(hrmsPerformanceEabFactorRepository.findById(id).get().getUnique_id());
			}

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPerformanceEabFactorRepository.saveAndFlush(hrmsPerformanceEabFactor));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deletePerformanceEabFactor(int id) {
		if (hrmsPerformanceEabFactorRepository.existsByIdAndActive(id, 1)) {
			HrmsPerformanceEabFactor hrmsPerformanceEabFactor = hrmsPerformanceEabFactorRepository.findByIdAndActive(id,
					1);
			hrmsPerformanceEabFactor.setActive(0);
			hrmsPerformanceEabFactor.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPerformanceEabFactorRepository.saveAndFlush(hrmsPerformanceEabFactor));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsPerformanceEabFactor>> getAllPerformanceEabFactor() {
		return ResponseEntity.status(HttpStatus.OK).body(hrmsPerformanceEabFactorRepository.findByActive(1));
	}

	@Override
	public ResponseEntity<List<PerformanceEabFactorWithQualitiesResponse>> getAllPerformanceEabFactorWithQualities() {

		List<PerformanceEabFactorWithQualitiesResponse> performanceEabFactorWithQualitiesResponselist = new ArrayList<>();

		List<HrmsPerformanceEabFactor> dbms = hrmsPerformanceEabFactorRepository.findByActive(1);

		for (HrmsPerformanceEabFactor dbm : dbms) {

			PerformanceEabFactorWithQualitiesResponse performanceEabFactorWithQualitiesResponse = new PerformanceEabFactorWithQualitiesResponse();

			performanceEabFactorWithQualitiesResponse.setActive(dbm.getActive());
			performanceEabFactorWithQualitiesResponse.setApproved(dbm.getApproved());
			performanceEabFactorWithQualitiesResponse.setDescription(dbm.getDescription());
			performanceEabFactorWithQualitiesResponse.setId(dbm.getId());
			performanceEabFactorWithQualitiesResponse.setName(dbm.getName());

			List<HrmsPerformanceEabQuality> performanceEabQualitylist = new ArrayList<>();

			List<HrmsPerformanceEabQuality> dbms1 = hrmsPerformanceEabQualityRepository
					.findByFactoridAndActive(dbm.getId(), 1);

			for (HrmsPerformanceEabQuality dbm1 : dbms1) {

				performanceEabQualitylist.add(dbm1);

			}

			performanceEabFactorWithQualitiesResponse.setPerformanceEabQualitylist(performanceEabQualitylist);

			performanceEabFactorWithQualitiesResponselist.add(performanceEabFactorWithQualitiesResponse);

		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceEabFactorWithQualitiesResponselist);
	}
}
