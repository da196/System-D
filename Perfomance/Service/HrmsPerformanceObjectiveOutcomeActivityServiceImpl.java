package com.Hrms.Perfomance.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Perfomance.DTO.PerformanceObjectiveOutcomeActivityResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceObjectiveOutcome;
import com.Hrms.Perfomance.Entity.HrmsPerformanceObjectiveOutcomeActivity;
import com.Hrms.Perfomance.Repository.HrmsPerformanceObjectiveOutcomeActivityRepository;
import com.Hrms.Perfomance.Repository.HrmsPerformanceObjectiveOutcomeRepository;

@Service
public class HrmsPerformanceObjectiveOutcomeActivityServiceImpl
		implements HrmsPerformanceObjectiveOutcomeActivityService {

	@Autowired
	private HrmsPerformanceObjectiveOutcomeActivityRepository hrmsPerformanceObjectiveOutcomeActivityRepository;

	@Autowired
	private HrmsPerformanceObjectiveOutcomeRepository hrmsPerformanceObjectiveOutcomeRepository;

	@Override
	public ResponseEntity<HrmsPerformanceObjectiveOutcomeActivity> addPerformanceObjectiveOutcomeActivity(
			HrmsPerformanceObjectiveOutcomeActivity hrmsPerformanceObjectiveOutcomeActivity) {
		if (hrmsPerformanceObjectiveOutcomeActivityRepository
				.existsByDescriptionAndActive(hrmsPerformanceObjectiveOutcomeActivity.getDescription(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsPerformanceObjectiveOutcomeActivity);
		} else {
			if (hrmsPerformanceObjectiveOutcomeRepository
					.existsByIdAndActive(hrmsPerformanceObjectiveOutcomeActivity.getOutcomeid(), 1)) {
				hrmsPerformanceObjectiveOutcomeActivity.setActive(1);
				hrmsPerformanceObjectiveOutcomeActivity.setApproved(0);
				hrmsPerformanceObjectiveOutcomeActivity.setUnique_id(UUID.randomUUID());

				return ResponseEntity.status(HttpStatus.OK).body(hrmsPerformanceObjectiveOutcomeActivityRepository
						.saveAndFlush(hrmsPerformanceObjectiveOutcomeActivity));
			} else {

				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
						.body(hrmsPerformanceObjectiveOutcomeActivity);

			}

		}
	}

	@Override
	public ResponseEntity<PerformanceObjectiveOutcomeActivityResponse> getPerformanceObjectiveOutcomeActivityById(
			int id) {

		PerformanceObjectiveOutcomeActivityResponse performanceObjectiveOutcomeActivityResponse = new PerformanceObjectiveOutcomeActivityResponse();
		if (hrmsPerformanceObjectiveOutcomeActivityRepository.existsById(id)) {
			HrmsPerformanceObjectiveOutcomeActivity dbm = hrmsPerformanceObjectiveOutcomeActivityRepository.findById(id)
					.get();

			performanceObjectiveOutcomeActivityResponse.setActive(dbm.getActive());
			performanceObjectiveOutcomeActivityResponse.setApproved(dbm.getApproved());
			performanceObjectiveOutcomeActivityResponse.setDescription(dbm.getDescription());
			performanceObjectiveOutcomeActivityResponse.setId(dbm.getId());
			performanceObjectiveOutcomeActivityResponse.setOutcomeid(dbm.getOutcomeid());

			HrmsPerformanceObjectiveOutcome hrmsPerformanceObjectiveOutcome = new HrmsPerformanceObjectiveOutcome();

			if (dbm.getOutcomeid() != 0 && hrmsPerformanceObjectiveOutcomeRepository.existsById(dbm.getOutcomeid())) {

				HrmsPerformanceObjectiveOutcome dbms = hrmsPerformanceObjectiveOutcomeRepository
						.findById(dbm.getOutcomeid()).get();

				hrmsPerformanceObjectiveOutcome = dbms;

			}

			performanceObjectiveOutcomeActivityResponse
					.setHrmsPerformanceObjectiveOutcome(hrmsPerformanceObjectiveOutcome);

		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceObjectiveOutcomeActivityResponse);
	}

	@Override
	public ResponseEntity<HrmsPerformanceObjectiveOutcomeActivity> updatePerformanceObjectiveOutcomeActivity(
			HrmsPerformanceObjectiveOutcomeActivity hrmsPerformanceObjectiveOutcomeActivity, int id) {
		if (hrmsPerformanceObjectiveOutcomeActivityRepository.existsByIdAndActive(id, 1)) {
			if (hrmsPerformanceObjectiveOutcomeRepository
					.existsByIdAndActive(hrmsPerformanceObjectiveOutcomeActivity.getOutcomeid(), 1)) {
				hrmsPerformanceObjectiveOutcomeActivity.setActive(1);
				hrmsPerformanceObjectiveOutcomeActivity.setApproved(0);
				hrmsPerformanceObjectiveOutcomeActivity.setDate_updated(LocalDateTime.now());
				if (hrmsPerformanceObjectiveOutcomeActivityRepository.findById(id).get().getDate_created() != null) {
					hrmsPerformanceObjectiveOutcomeActivity.setDate_created(
							hrmsPerformanceObjectiveOutcomeActivityRepository.findById(id).get().getDate_created());
				}

				if (hrmsPerformanceObjectiveOutcomeActivityRepository.findById(id).get().getUnique_id() != null) {
					hrmsPerformanceObjectiveOutcomeActivity.setUnique_id(
							hrmsPerformanceObjectiveOutcomeActivityRepository.findById(id).get().getUnique_id());
				}

				return ResponseEntity.status(HttpStatus.OK).body(hrmsPerformanceObjectiveOutcomeActivityRepository
						.saveAndFlush(hrmsPerformanceObjectiveOutcomeActivity));
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
						.body(hrmsPerformanceObjectiveOutcomeActivity);
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deletePerformanceObjectiveOutcomeActivity(int id) {
		if (hrmsPerformanceObjectiveOutcomeActivityRepository.existsByIdAndActive(id, 1)) {
			HrmsPerformanceObjectiveOutcomeActivity hrmsPerformanceObjectiveOutcomeActivity = hrmsPerformanceObjectiveOutcomeActivityRepository
					.findByIdAndActive(id, 1);
			hrmsPerformanceObjectiveOutcomeActivity.setActive(0);
			hrmsPerformanceObjectiveOutcomeActivity.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK).body(hrmsPerformanceObjectiveOutcomeActivityRepository
					.saveAndFlush(hrmsPerformanceObjectiveOutcomeActivity));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<PerformanceObjectiveOutcomeActivityResponse>> getAllPerformanceObjectiveOutcomeActivity() {

		List<PerformanceObjectiveOutcomeActivityResponse> performanceObjectiveOutcomeActivityResponselist = new ArrayList<>();

		List<HrmsPerformanceObjectiveOutcomeActivity> dbms = hrmsPerformanceObjectiveOutcomeActivityRepository
				.findByActive(1);

		for (HrmsPerformanceObjectiveOutcomeActivity dbm : dbms) {
			PerformanceObjectiveOutcomeActivityResponse performanceObjectiveOutcomeActivityResponse = new PerformanceObjectiveOutcomeActivityResponse();

			performanceObjectiveOutcomeActivityResponse.setActive(dbm.getActive());
			performanceObjectiveOutcomeActivityResponse.setApproved(dbm.getApproved());
			performanceObjectiveOutcomeActivityResponse.setDescription(dbm.getDescription());
			performanceObjectiveOutcomeActivityResponse.setId(dbm.getId());
			performanceObjectiveOutcomeActivityResponse.setOutcomeid(dbm.getOutcomeid());

			HrmsPerformanceObjectiveOutcome hrmsPerformanceObjectiveOutcome = new HrmsPerformanceObjectiveOutcome();

			if (dbm.getOutcomeid() != 0 && hrmsPerformanceObjectiveOutcomeRepository.existsById(dbm.getOutcomeid())) {

				HrmsPerformanceObjectiveOutcome dbmz = hrmsPerformanceObjectiveOutcomeRepository
						.findById(dbm.getOutcomeid()).get();

				hrmsPerformanceObjectiveOutcome = dbmz;

			}

			performanceObjectiveOutcomeActivityResponse
					.setHrmsPerformanceObjectiveOutcome(hrmsPerformanceObjectiveOutcome);

			performanceObjectiveOutcomeActivityResponselist.add(performanceObjectiveOutcomeActivityResponse);

		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceObjectiveOutcomeActivityResponselist);
	}

	@Override
	public ResponseEntity<List<PerformanceObjectiveOutcomeActivityResponse>> getAllPerformanceObjectiveOutcomeActivityByObjectiveOutcomeId(
			int objectiveOutcomeid) {

		List<PerformanceObjectiveOutcomeActivityResponse> performanceObjectiveOutcomeActivityResponselist = new ArrayList<>();

		List<HrmsPerformanceObjectiveOutcomeActivity> dbms = hrmsPerformanceObjectiveOutcomeActivityRepository
				.findByOutcomeidAndActive(objectiveOutcomeid, 1);

		for (HrmsPerformanceObjectiveOutcomeActivity dbm : dbms) {
			PerformanceObjectiveOutcomeActivityResponse performanceObjectiveOutcomeActivityResponse = new PerformanceObjectiveOutcomeActivityResponse();

			performanceObjectiveOutcomeActivityResponse.setActive(dbm.getActive());
			performanceObjectiveOutcomeActivityResponse.setApproved(dbm.getApproved());
			performanceObjectiveOutcomeActivityResponse.setDescription(dbm.getDescription());
			performanceObjectiveOutcomeActivityResponse.setId(dbm.getId());
			performanceObjectiveOutcomeActivityResponse.setOutcomeid(dbm.getOutcomeid());

			HrmsPerformanceObjectiveOutcome hrmsPerformanceObjectiveOutcome = new HrmsPerformanceObjectiveOutcome();

			if (dbm.getOutcomeid() != 0 && hrmsPerformanceObjectiveOutcomeRepository.existsById(dbm.getOutcomeid())) {

				HrmsPerformanceObjectiveOutcome dbmz = hrmsPerformanceObjectiveOutcomeRepository
						.findById(dbm.getOutcomeid()).get();

				hrmsPerformanceObjectiveOutcome = dbmz;

			}

			performanceObjectiveOutcomeActivityResponse
					.setHrmsPerformanceObjectiveOutcome(hrmsPerformanceObjectiveOutcome);

			performanceObjectiveOutcomeActivityResponselist.add(performanceObjectiveOutcomeActivityResponse);

		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceObjectiveOutcomeActivityResponselist);
	}

}
