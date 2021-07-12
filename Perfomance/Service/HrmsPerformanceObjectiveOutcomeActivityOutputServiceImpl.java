package com.Hrms.Perfomance.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Perfomance.DTO.PerformanceObjectiveOutcomeActivityOutputResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceObjectiveOutcomeActivity;
import com.Hrms.Perfomance.Entity.HrmsPerformanceObjectiveOutcomeActivityOutput;
import com.Hrms.Perfomance.Repository.HrmsPerformanceObjectiveOutcomeActivityOutputRepository;
import com.Hrms.Perfomance.Repository.HrmsPerformanceObjectiveOutcomeActivityRepository;

@Service
public class HrmsPerformanceObjectiveOutcomeActivityOutputServiceImpl
		implements HrmsPerformanceObjectiveOutcomeActivityOutputService {

	@Autowired
	private HrmsPerformanceObjectiveOutcomeActivityOutputRepository hrmsPerformanceObjectiveOutcomeActivityOutputRepository;

	@Autowired
	private HrmsPerformanceObjectiveOutcomeActivityRepository hrmsPerformanceObjectiveOutcomeActivityRepository;

	@Override
	public ResponseEntity<HrmsPerformanceObjectiveOutcomeActivityOutput> addPerformanceObjectiveOutcomeActivityOutput(
			HrmsPerformanceObjectiveOutcomeActivityOutput hrmsPerformanceObjectiveOutcomeActivityOutput) {
		if (hrmsPerformanceObjectiveOutcomeActivityOutputRepository
				.existsByDescriptionAndActive(hrmsPerformanceObjectiveOutcomeActivityOutput.getDescription(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
					.body(hrmsPerformanceObjectiveOutcomeActivityOutput);
		} else {
			if (hrmsPerformanceObjectiveOutcomeActivityRepository
					.existsByIdAndActive(hrmsPerformanceObjectiveOutcomeActivityOutput.getActivityid(), 1)) {
				hrmsPerformanceObjectiveOutcomeActivityOutput.setActive(1);
				hrmsPerformanceObjectiveOutcomeActivityOutput.setApproved(0);
				hrmsPerformanceObjectiveOutcomeActivityOutput.setUnique_id(UUID.randomUUID());

				return ResponseEntity.status(HttpStatus.OK).body(hrmsPerformanceObjectiveOutcomeActivityOutputRepository
						.saveAndFlush(hrmsPerformanceObjectiveOutcomeActivityOutput));
			} else {

				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
						.body(hrmsPerformanceObjectiveOutcomeActivityOutput);

			}

		}
	}

	@Override
	public ResponseEntity<PerformanceObjectiveOutcomeActivityOutputResponse> getPerformanceObjectiveOutcomeActivityOutputById(
			int id) {
		PerformanceObjectiveOutcomeActivityOutputResponse performanceObjectiveOutcomeActivityOutputResponse = new PerformanceObjectiveOutcomeActivityOutputResponse();
		if (hrmsPerformanceObjectiveOutcomeActivityOutputRepository.existsById(id)) {
			HrmsPerformanceObjectiveOutcomeActivityOutput dbm = hrmsPerformanceObjectiveOutcomeActivityOutputRepository
					.findById(id).get();

			performanceObjectiveOutcomeActivityOutputResponse.setActive(dbm.getActive());
			performanceObjectiveOutcomeActivityOutputResponse.setApproved(dbm.getApproved());
			performanceObjectiveOutcomeActivityOutputResponse.setDescription(dbm.getDescription());
			performanceObjectiveOutcomeActivityOutputResponse.setId(dbm.getId());
			// performanceObjectiveOutcomeActivityOutputResponse
			// .setKeyperformanceindicator(dbm.getKeyperformanceindicator());
			// performanceObjectiveOutcomeActivityOutputResponse.setTarget(dbm.getTarget());
			// performanceObjectiveOutcomeActivityOutputResponse.setTimeline(dbm.getTimeline());
			performanceObjectiveOutcomeActivityOutputResponse.setActivityid(dbm.getActivityid());

			HrmsPerformanceObjectiveOutcomeActivity performanceObjectiveOutcomeActivity = new HrmsPerformanceObjectiveOutcomeActivity();

			if (dbm.getActivityid() != 0
					&& hrmsPerformanceObjectiveOutcomeActivityRepository.existsById(dbm.getActivityid())) {

				HrmsPerformanceObjectiveOutcomeActivity dbms = hrmsPerformanceObjectiveOutcomeActivityRepository
						.findById(dbm.getActivityid()).get();

				performanceObjectiveOutcomeActivity = dbms;

			}

			performanceObjectiveOutcomeActivityOutputResponse
					.setPerformanceObjectiveOutcomeActivity(performanceObjectiveOutcomeActivity);

		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceObjectiveOutcomeActivityOutputResponse);
	}

	@Override
	public ResponseEntity<HrmsPerformanceObjectiveOutcomeActivityOutput> updatePerformanceObjectiveOutcomeActivityOutput(
			HrmsPerformanceObjectiveOutcomeActivityOutput hrmsPerformanceObjectiveOutcomeActivityOutput, int id) {
		if (hrmsPerformanceObjectiveOutcomeActivityOutputRepository.existsByIdAndActive(id, 1)) {
			if (hrmsPerformanceObjectiveOutcomeActivityRepository
					.existsByIdAndActive(hrmsPerformanceObjectiveOutcomeActivityOutput.getActivityid(), 1)) {
				hrmsPerformanceObjectiveOutcomeActivityOutput.setActive(1);
				hrmsPerformanceObjectiveOutcomeActivityOutput.setApproved(0);
				hrmsPerformanceObjectiveOutcomeActivityOutput.setDate_updated(LocalDateTime.now());
				if (hrmsPerformanceObjectiveOutcomeActivityOutputRepository.findById(id).get()
						.getDate_created() != null) {
					hrmsPerformanceObjectiveOutcomeActivityOutput
							.setDate_created(hrmsPerformanceObjectiveOutcomeActivityOutputRepository.findById(id).get()
									.getDate_created());
				}

				if (hrmsPerformanceObjectiveOutcomeActivityOutputRepository.findById(id).get().getUnique_id() != null) {
					hrmsPerformanceObjectiveOutcomeActivityOutput.setUnique_id(
							hrmsPerformanceObjectiveOutcomeActivityOutputRepository.findById(id).get().getUnique_id());
				}

				return ResponseEntity.status(HttpStatus.OK).body(hrmsPerformanceObjectiveOutcomeActivityOutputRepository
						.saveAndFlush(hrmsPerformanceObjectiveOutcomeActivityOutput));
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
						.body(hrmsPerformanceObjectiveOutcomeActivityOutput);
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deletePerformanceObjectiveOutcomeActivityOutput(int id) {
		if (hrmsPerformanceObjectiveOutcomeActivityOutputRepository.existsByIdAndActive(id, 1)) {
			HrmsPerformanceObjectiveOutcomeActivityOutput hrmsPerformanceObjectiveOutcomeActivityOutput = hrmsPerformanceObjectiveOutcomeActivityOutputRepository
					.findByIdAndActive(id, 1);
			hrmsPerformanceObjectiveOutcomeActivityOutput.setActive(0);
			hrmsPerformanceObjectiveOutcomeActivityOutput.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK).body(hrmsPerformanceObjectiveOutcomeActivityOutputRepository
					.saveAndFlush(hrmsPerformanceObjectiveOutcomeActivityOutput));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<PerformanceObjectiveOutcomeActivityOutputResponse>> getAllPerformanceObjectiveOutcomeActivityOutput() {
		List<PerformanceObjectiveOutcomeActivityOutputResponse> performanceObjectiveOutcomeActivityOutputResponselist = new ArrayList<>();

		List<HrmsPerformanceObjectiveOutcomeActivityOutput> dbms = hrmsPerformanceObjectiveOutcomeActivityOutputRepository
				.findByActive(1);

		for (HrmsPerformanceObjectiveOutcomeActivityOutput dbm : dbms) {
			PerformanceObjectiveOutcomeActivityOutputResponse performanceObjectiveOutcomeActivityOutputResponse = new PerformanceObjectiveOutcomeActivityOutputResponse();

			performanceObjectiveOutcomeActivityOutputResponse.setActive(dbm.getActive());
			performanceObjectiveOutcomeActivityOutputResponse.setApproved(dbm.getApproved());
			performanceObjectiveOutcomeActivityOutputResponse.setDescription(dbm.getDescription());
			performanceObjectiveOutcomeActivityOutputResponse.setId(dbm.getId());
			// performanceObjectiveOutcomeActivityOutputResponse
			// .setKeyperformanceindicator(dbm.getKeyperformanceindicator());
			// performanceObjectiveOutcomeActivityOutputResponse.setTarget(dbm.getTarget());
			// performanceObjectiveOutcomeActivityOutputResponse.setTimeline(dbm.getTimeline());
			performanceObjectiveOutcomeActivityOutputResponse.setActivityid(dbm.getActivityid());

			HrmsPerformanceObjectiveOutcomeActivity performanceObjectiveOutcomeActivity = new HrmsPerformanceObjectiveOutcomeActivity();

			if (dbm.getActivityid() != 0
					&& hrmsPerformanceObjectiveOutcomeActivityRepository.existsById(dbm.getActivityid())) {

				HrmsPerformanceObjectiveOutcomeActivity dbmz = hrmsPerformanceObjectiveOutcomeActivityRepository
						.findById(dbm.getActivityid()).get();

				performanceObjectiveOutcomeActivity = dbmz;

			}

			performanceObjectiveOutcomeActivityOutputResponse
					.setPerformanceObjectiveOutcomeActivity(performanceObjectiveOutcomeActivity);

			performanceObjectiveOutcomeActivityOutputResponselist
					.add(performanceObjectiveOutcomeActivityOutputResponse);

		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceObjectiveOutcomeActivityOutputResponselist);
	}

	@Override
	public ResponseEntity<List<PerformanceObjectiveOutcomeActivityOutputResponse>> getAllPerformanceObjectiveOutcomeActivityOutputByActivityid(
			int activityid) {
		List<PerformanceObjectiveOutcomeActivityOutputResponse> performanceObjectiveOutcomeActivityOutputResponselist = new ArrayList<>();

		List<HrmsPerformanceObjectiveOutcomeActivityOutput> dbms = hrmsPerformanceObjectiveOutcomeActivityOutputRepository
				.findByActivityidAndActive(activityid, 1);

		for (HrmsPerformanceObjectiveOutcomeActivityOutput dbm : dbms) {
			PerformanceObjectiveOutcomeActivityOutputResponse performanceObjectiveOutcomeActivityOutputResponse = new PerformanceObjectiveOutcomeActivityOutputResponse();

			performanceObjectiveOutcomeActivityOutputResponse.setActive(dbm.getActive());
			performanceObjectiveOutcomeActivityOutputResponse.setApproved(dbm.getApproved());
			performanceObjectiveOutcomeActivityOutputResponse.setDescription(dbm.getDescription());
			performanceObjectiveOutcomeActivityOutputResponse.setId(dbm.getId());
			// performanceObjectiveOutcomeActivityOutputResponse
			// .setKeyperformanceindicator(dbm.getKeyperformanceindicator());
			// performanceObjectiveOutcomeActivityOutputResponse.setTarget(dbm.getTarget());
			// performanceObjectiveOutcomeActivityOutputResponse.setTimeline(dbm.getTimeline());
			performanceObjectiveOutcomeActivityOutputResponse.setActivityid(dbm.getActivityid());

			HrmsPerformanceObjectiveOutcomeActivity performanceObjectiveOutcomeActivity = new HrmsPerformanceObjectiveOutcomeActivity();

			if (dbm.getActivityid() != 0
					&& hrmsPerformanceObjectiveOutcomeActivityRepository.existsById(dbm.getActivityid())) {

				HrmsPerformanceObjectiveOutcomeActivity dbmz = hrmsPerformanceObjectiveOutcomeActivityRepository
						.findById(dbm.getActivityid()).get();

				performanceObjectiveOutcomeActivity = dbmz;

			}

			performanceObjectiveOutcomeActivityOutputResponse
					.setPerformanceObjectiveOutcomeActivity(performanceObjectiveOutcomeActivity);

			performanceObjectiveOutcomeActivityOutputResponselist
					.add(performanceObjectiveOutcomeActivityOutputResponse);

		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceObjectiveOutcomeActivityOutputResponselist);
	}

}
