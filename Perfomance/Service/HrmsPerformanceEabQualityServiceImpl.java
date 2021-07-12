package com.Hrms.Perfomance.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Perfomance.DTO.PerformanceEabQualityResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEabFactor;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEabQuality;
import com.Hrms.Perfomance.Repository.HrmsPerformanceEabFactorRepository;
import com.Hrms.Perfomance.Repository.HrmsPerformanceEabQualityRepository;

@Service
public class HrmsPerformanceEabQualityServiceImpl implements HrmsPerformanceEabQualityService {

	@Autowired
	private HrmsPerformanceEabQualityRepository hrmsPerformanceEabQualityRepository;

	@Autowired
	private HrmsPerformanceEabFactorRepository hrmsPerformanceEabFactorRepository;

	@Override
	public ResponseEntity<HrmsPerformanceEabQuality> addPerformanceEabQuality(
			HrmsPerformanceEabQuality hrmsPerformanceEabQuality) {
		if (hrmsPerformanceEabQualityRepository.existsByNameAndFactoridAndActive(hrmsPerformanceEabQuality.getName(),
				hrmsPerformanceEabQuality.getFactorid(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsPerformanceEabQuality);
		} else {
			if (hrmsPerformanceEabFactorRepository.existsByIdAndActive(hrmsPerformanceEabQuality.getFactorid(), 1)) {
				hrmsPerformanceEabQuality.setActive(1);
				hrmsPerformanceEabQuality.setApproved(0);
				hrmsPerformanceEabQuality.setUnique_id(UUID.randomUUID());

				return ResponseEntity.status(HttpStatus.OK)
						.body(hrmsPerformanceEabQualityRepository.saveAndFlush(hrmsPerformanceEabQuality));
			} else {

				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsPerformanceEabQuality);

			}

		}
	}

	@Override
	public ResponseEntity<PerformanceEabQualityResponse> getPerformanceEabQualityById(int id) {
		PerformanceEabQualityResponse performanceEabQualityResponse = new PerformanceEabQualityResponse();

		if (hrmsPerformanceEabQualityRepository.existsByIdAndActive(id, 1)) {

			HrmsPerformanceEabQuality dbm = hrmsPerformanceEabQualityRepository.findById(id).get();

			performanceEabQualityResponse.setActive(dbm.getActive());
			performanceEabQualityResponse.setApproved(dbm.getApproved());
			performanceEabQualityResponse.setDescription(dbm.getDescription());
			performanceEabQualityResponse.setId(dbm.getId());
			performanceEabQualityResponse.setName(dbm.getName());
			performanceEabQualityResponse.setFactorid(dbm.getFactorid());

			HrmsPerformanceEabFactor performanceEabFactor = new HrmsPerformanceEabFactor();
			if (dbm.getFactorid() != 0 && hrmsPerformanceEabFactorRepository.existsById(dbm.getFactorid())) {

				performanceEabFactor = hrmsPerformanceEabFactorRepository.findById(dbm.getFactorid()).get();

			}
			performanceEabQualityResponse.setPerformanceEabFactor(performanceEabFactor);

		}
		return ResponseEntity.status(HttpStatus.OK).body(performanceEabQualityResponse);
	}

	@Override
	public ResponseEntity<HrmsPerformanceEabQuality> updatePerformanceEabQuality(
			HrmsPerformanceEabQuality hrmsPerformanceEabQuality, int id) {
		if (hrmsPerformanceEabQualityRepository.existsByIdAndActive(id, 1)) {
			if (hrmsPerformanceEabFactorRepository.existsByIdAndActive(hrmsPerformanceEabQuality.getFactorid(), 1)) {
				hrmsPerformanceEabQuality.setActive(1);
				hrmsPerformanceEabQuality.setApproved(0);
				hrmsPerformanceEabQuality.setDate_updated(LocalDateTime.now());
				if (hrmsPerformanceEabQualityRepository.findById(id).get().getDate_created() != null) {
					hrmsPerformanceEabQuality
							.setDate_created(hrmsPerformanceEabQualityRepository.findById(id).get().getDate_created());
				}

				if (hrmsPerformanceEabQualityRepository.findById(id).get().getUnique_id() != null) {
					hrmsPerformanceEabQuality
							.setUnique_id(hrmsPerformanceEabQualityRepository.findById(id).get().getUnique_id());
				}

				return ResponseEntity.status(HttpStatus.OK)
						.body(hrmsPerformanceEabQualityRepository.saveAndFlush(hrmsPerformanceEabQuality));
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsPerformanceEabQuality);
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deletePerformanceEabQuality(int id) {
		if (hrmsPerformanceEabQualityRepository.existsByIdAndActive(id, 1)) {
			HrmsPerformanceEabQuality hrmsPerformanceEabQuality = hrmsPerformanceEabQualityRepository
					.findByIdAndActive(id, 1);
			hrmsPerformanceEabQuality.setActive(0);
			hrmsPerformanceEabQuality.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPerformanceEabQualityRepository.saveAndFlush(hrmsPerformanceEabQuality));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<PerformanceEabQualityResponse>> getAllPerformanceEabQuality() {

		List<PerformanceEabQualityResponse> performanceEabQualityResponselist = new ArrayList<>();

		List<HrmsPerformanceEabQuality> dbms = hrmsPerformanceEabQualityRepository.findByActive(1);

		for (HrmsPerformanceEabQuality dbm : dbms) {

			PerformanceEabQualityResponse performanceEabQualityResponse = new PerformanceEabQualityResponse();

			performanceEabQualityResponse.setActive(dbm.getActive());
			performanceEabQualityResponse.setApproved(dbm.getApproved());
			performanceEabQualityResponse.setDescription(dbm.getDescription());
			performanceEabQualityResponse.setId(dbm.getId());
			performanceEabQualityResponse.setName(dbm.getName());
			performanceEabQualityResponse.setFactorid(dbm.getFactorid());

			HrmsPerformanceEabFactor performanceEabFactor = new HrmsPerformanceEabFactor();
			if (dbm.getFactorid() != 0 && hrmsPerformanceEabFactorRepository.existsById(dbm.getFactorid())) {

				performanceEabFactor = hrmsPerformanceEabFactorRepository.findById(dbm.getFactorid()).get();

			}
			performanceEabQualityResponse.setPerformanceEabFactor(performanceEabFactor);

			performanceEabQualityResponselist.add(performanceEabQualityResponse);

		}
		return ResponseEntity.status(HttpStatus.OK).body(performanceEabQualityResponselist);
	}

	@Override
	public ResponseEntity<List<PerformanceEabQualityResponse>> getAllPerformanceEabQualityByFactorId(int factorid) {
		List<PerformanceEabQualityResponse> performanceEabQualityResponselist = new ArrayList<>();

		List<HrmsPerformanceEabQuality> dbms = hrmsPerformanceEabQualityRepository.findByFactoridAndActive(factorid, 1);

		for (HrmsPerformanceEabQuality dbm : dbms) {

			PerformanceEabQualityResponse performanceEabQualityResponse = new PerformanceEabQualityResponse();

			performanceEabQualityResponse.setActive(dbm.getActive());
			performanceEabQualityResponse.setApproved(dbm.getApproved());
			performanceEabQualityResponse.setDescription(dbm.getDescription());
			performanceEabQualityResponse.setId(dbm.getId());
			performanceEabQualityResponse.setName(dbm.getName());
			performanceEabQualityResponse.setFactorid(dbm.getFactorid());

			HrmsPerformanceEabFactor performanceEabFactor = new HrmsPerformanceEabFactor();
			if (dbm.getFactorid() != 0 && hrmsPerformanceEabFactorRepository.existsById(dbm.getFactorid())) {

				performanceEabFactor = hrmsPerformanceEabFactorRepository.findById(dbm.getFactorid()).get();

			}
			performanceEabQualityResponse.setPerformanceEabFactor(performanceEabFactor);

			performanceEabQualityResponselist.add(performanceEabQualityResponse);

		}
		return ResponseEntity.status(HttpStatus.OK).body(performanceEabQualityResponselist);
	}

}
