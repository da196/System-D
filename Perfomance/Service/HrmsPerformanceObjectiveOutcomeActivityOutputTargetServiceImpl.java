package com.Hrms.Perfomance.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Perfomance.DTO.PerformanceObjectiveOutcomeActivityOutputTargetResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceObjectiveOutcomeActivityOutput;
import com.Hrms.Perfomance.Entity.HrmsPerformanceObjectiveOutcomeActivityOutputTarget;
import com.Hrms.Perfomance.Repository.HrmsPerformanceObjectiveOutcomeActivityOutputRepository;
import com.Hrms.Perfomance.Repository.HrmsPerformanceObjectiveOutcomeActivityOutputTargetRepository;

@Service
public class HrmsPerformanceObjectiveOutcomeActivityOutputTargetServiceImpl
		implements HrmsPerformanceObjectiveOutcomeActivityOutputTargetService {

	@Autowired
	private HrmsPerformanceObjectiveOutcomeActivityOutputTargetRepository hrmsPerformanceObjectiveOutcomeActivityOutputTargetRepository;

	@Autowired
	private HrmsPerformanceObjectiveOutcomeActivityOutputRepository hrmsPerformanceObjectiveOutcomeActivityOutputRepository;

	@Override
	public ResponseEntity<HrmsPerformanceObjectiveOutcomeActivityOutputTarget> addPerformanceObjectiveOutcomeActivityOutputTarget(
			HrmsPerformanceObjectiveOutcomeActivityOutputTarget hrmsPerformanceObjectiveOutcomeActivityOutputTarget) {
		if (hrmsPerformanceObjectiveOutcomeActivityOutputTargetRepository.existsByDescriptionAndActive(
				hrmsPerformanceObjectiveOutcomeActivityOutputTarget.getDescription(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
					.body(hrmsPerformanceObjectiveOutcomeActivityOutputTarget);
		} else {
			if (hrmsPerformanceObjectiveOutcomeActivityOutputRepository
					.existsByIdAndActive(hrmsPerformanceObjectiveOutcomeActivityOutputTarget.getOutputid(), 1)) {
				hrmsPerformanceObjectiveOutcomeActivityOutputTarget.setActive(1);
				hrmsPerformanceObjectiveOutcomeActivityOutputTarget.setApproved(0);
				hrmsPerformanceObjectiveOutcomeActivityOutputTarget.setUnique_id(UUID.randomUUID());

				return ResponseEntity.status(HttpStatus.OK)
						.body(hrmsPerformanceObjectiveOutcomeActivityOutputTargetRepository
								.saveAndFlush(hrmsPerformanceObjectiveOutcomeActivityOutputTarget));
			} else {

				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
						.body(hrmsPerformanceObjectiveOutcomeActivityOutputTarget);

			}

		}
	}

	@Override
	public ResponseEntity<PerformanceObjectiveOutcomeActivityOutputTargetResponse> getPerformanceObjectiveOutcomeActivityOutputTargetById(
			int id) {
		PerformanceObjectiveOutcomeActivityOutputTargetResponse performanceObjectiveOutcomeActivityOutputTargetResponse = new PerformanceObjectiveOutcomeActivityOutputTargetResponse();
		if (hrmsPerformanceObjectiveOutcomeActivityOutputTargetRepository.existsById(id)) {
			HrmsPerformanceObjectiveOutcomeActivityOutputTarget dbm = hrmsPerformanceObjectiveOutcomeActivityOutputTargetRepository
					.findById(id).get();

			performanceObjectiveOutcomeActivityOutputTargetResponse.setActive(dbm.getActive());
			performanceObjectiveOutcomeActivityOutputTargetResponse.setApproved(dbm.getApproved());
			performanceObjectiveOutcomeActivityOutputTargetResponse.setDescription(dbm.getDescription());
			performanceObjectiveOutcomeActivityOutputTargetResponse.setId(dbm.getId());

			performanceObjectiveOutcomeActivityOutputTargetResponse.setOutputid(dbm.getOutputid());

			HrmsPerformanceObjectiveOutcomeActivityOutput performanceObjectiveOutcomeActivityOutput = new HrmsPerformanceObjectiveOutcomeActivityOutput();

			if (dbm.getOutputid() != 0
					&& hrmsPerformanceObjectiveOutcomeActivityOutputRepository.existsById(dbm.getOutputid())) {

				HrmsPerformanceObjectiveOutcomeActivityOutput dbms = hrmsPerformanceObjectiveOutcomeActivityOutputRepository
						.findById(dbm.getOutputid()).get();

				performanceObjectiveOutcomeActivityOutput = dbms;

			}

			performanceObjectiveOutcomeActivityOutputTargetResponse
					.setPerformanceObjectiveOutcomeActivityOutput(performanceObjectiveOutcomeActivityOutput);

		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceObjectiveOutcomeActivityOutputTargetResponse);
	}

	@Override
	public ResponseEntity<HrmsPerformanceObjectiveOutcomeActivityOutputTarget> updatePerformanceObjectiveOutcomeActivityOutputTarget(
			HrmsPerformanceObjectiveOutcomeActivityOutputTarget hrmsPerformanceObjectiveOutcomeActivityOutputTarget,
			int id) {
		if (hrmsPerformanceObjectiveOutcomeActivityOutputTargetRepository.existsByIdAndActive(id, 1)) {
			if (hrmsPerformanceObjectiveOutcomeActivityOutputRepository
					.existsByIdAndActive(hrmsPerformanceObjectiveOutcomeActivityOutputTarget.getOutputid(), 1)) {
				hrmsPerformanceObjectiveOutcomeActivityOutputTarget.setActive(1);
				hrmsPerformanceObjectiveOutcomeActivityOutputTarget.setApproved(0);
				hrmsPerformanceObjectiveOutcomeActivityOutputTarget.setDate_updated(LocalDateTime.now());
				if (hrmsPerformanceObjectiveOutcomeActivityOutputTargetRepository.findById(id).get()
						.getDate_created() != null) {
					hrmsPerformanceObjectiveOutcomeActivityOutputTarget
							.setDate_created(hrmsPerformanceObjectiveOutcomeActivityOutputTargetRepository.findById(id)
									.get().getDate_created());
				}

				if (hrmsPerformanceObjectiveOutcomeActivityOutputTargetRepository.findById(id).get()
						.getUnique_id() != null) {
					hrmsPerformanceObjectiveOutcomeActivityOutputTarget
							.setUnique_id(hrmsPerformanceObjectiveOutcomeActivityOutputTargetRepository.findById(id)
									.get().getUnique_id());
				}

				return ResponseEntity.status(HttpStatus.OK)
						.body(hrmsPerformanceObjectiveOutcomeActivityOutputTargetRepository
								.saveAndFlush(hrmsPerformanceObjectiveOutcomeActivityOutputTarget));
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
						.body(hrmsPerformanceObjectiveOutcomeActivityOutputTarget);
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deletePerformanceObjectiveOutcomeActivityOutputTarget(int id) {
		if (hrmsPerformanceObjectiveOutcomeActivityOutputTargetRepository.existsByIdAndActive(id, 1)) {
			HrmsPerformanceObjectiveOutcomeActivityOutputTarget hrmsPerformanceObjectiveOutcomeActivityOutputTarget = hrmsPerformanceObjectiveOutcomeActivityOutputTargetRepository
					.findByIdAndActive(id, 1);
			hrmsPerformanceObjectiveOutcomeActivityOutputTarget.setActive(0);
			hrmsPerformanceObjectiveOutcomeActivityOutputTarget.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPerformanceObjectiveOutcomeActivityOutputTargetRepository
							.saveAndFlush(hrmsPerformanceObjectiveOutcomeActivityOutputTarget));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<PerformanceObjectiveOutcomeActivityOutputTargetResponse>> getAllPerformanceObjectiveOutcomeActivityOutputTarget() {

		List<PerformanceObjectiveOutcomeActivityOutputTargetResponse> performanceObjectiveOutcomeActivityOutputTargetResponselist = new ArrayList<>();

		List<HrmsPerformanceObjectiveOutcomeActivityOutputTarget> dbms = hrmsPerformanceObjectiveOutcomeActivityOutputTargetRepository
				.findByActive(1);

		for (HrmsPerformanceObjectiveOutcomeActivityOutputTarget dbm : dbms) {
			PerformanceObjectiveOutcomeActivityOutputTargetResponse performanceObjectiveOutcomeActivityOutputTargetResponse = new PerformanceObjectiveOutcomeActivityOutputTargetResponse();

			performanceObjectiveOutcomeActivityOutputTargetResponse.setActive(dbm.getActive());
			performanceObjectiveOutcomeActivityOutputTargetResponse.setApproved(dbm.getApproved());
			performanceObjectiveOutcomeActivityOutputTargetResponse.setDescription(dbm.getDescription());
			performanceObjectiveOutcomeActivityOutputTargetResponse.setId(dbm.getId());

			performanceObjectiveOutcomeActivityOutputTargetResponse.setOutputid(dbm.getOutputid());
			performanceObjectiveOutcomeActivityOutputTargetResponse.setTimeline(dbm.getTimeline());
			performanceObjectiveOutcomeActivityOutputTargetResponse.setTarget(dbm.getTarget());
			performanceObjectiveOutcomeActivityOutputTargetResponse
					.setKeyperformanceindicator(dbm.getKeyperformanceindicator());

			HrmsPerformanceObjectiveOutcomeActivityOutput performanceObjectiveOutcomeActivityOutput = new HrmsPerformanceObjectiveOutcomeActivityOutput();

			if (dbm.getOutputid() != 0
					&& hrmsPerformanceObjectiveOutcomeActivityOutputRepository.existsById(dbm.getOutputid())) {

				HrmsPerformanceObjectiveOutcomeActivityOutput dbmz = hrmsPerformanceObjectiveOutcomeActivityOutputRepository
						.findById(dbm.getOutputid()).get();

				performanceObjectiveOutcomeActivityOutput = dbmz;

			}

			performanceObjectiveOutcomeActivityOutputTargetResponse
					.setPerformanceObjectiveOutcomeActivityOutput(performanceObjectiveOutcomeActivityOutput);

			performanceObjectiveOutcomeActivityOutputTargetResponselist
					.add(performanceObjectiveOutcomeActivityOutputTargetResponse);

		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceObjectiveOutcomeActivityOutputTargetResponselist);
	}

	@Override
	public ResponseEntity<List<PerformanceObjectiveOutcomeActivityOutputTargetResponse>> getAllPerformanceObjectiveOutcomeActivityOutputByOutputid(
			int outputid) {
		List<PerformanceObjectiveOutcomeActivityOutputTargetResponse> performanceObjectiveOutcomeActivityOutputTargetResponselist = new ArrayList<>();

		List<HrmsPerformanceObjectiveOutcomeActivityOutputTarget> dbms = hrmsPerformanceObjectiveOutcomeActivityOutputTargetRepository
				.findByOutputidAndActive(outputid, 1);

		for (HrmsPerformanceObjectiveOutcomeActivityOutputTarget dbm : dbms) {
			PerformanceObjectiveOutcomeActivityOutputTargetResponse performanceObjectiveOutcomeActivityOutputTargetResponse = new PerformanceObjectiveOutcomeActivityOutputTargetResponse();

			performanceObjectiveOutcomeActivityOutputTargetResponse.setActive(dbm.getActive());
			performanceObjectiveOutcomeActivityOutputTargetResponse.setApproved(dbm.getApproved());
			performanceObjectiveOutcomeActivityOutputTargetResponse.setDescription(dbm.getDescription());
			performanceObjectiveOutcomeActivityOutputTargetResponse.setId(dbm.getId());

			performanceObjectiveOutcomeActivityOutputTargetResponse.setOutputid(dbm.getOutputid());
			performanceObjectiveOutcomeActivityOutputTargetResponse.setTimeline(dbm.getTimeline());
			performanceObjectiveOutcomeActivityOutputTargetResponse.setTarget(dbm.getTarget());
			performanceObjectiveOutcomeActivityOutputTargetResponse
					.setKeyperformanceindicator(dbm.getKeyperformanceindicator());

			HrmsPerformanceObjectiveOutcomeActivityOutput performanceObjectiveOutcomeActivityOutput = new HrmsPerformanceObjectiveOutcomeActivityOutput();

			if (dbm.getOutputid() != 0
					&& hrmsPerformanceObjectiveOutcomeActivityOutputRepository.existsById(dbm.getOutputid())) {

				HrmsPerformanceObjectiveOutcomeActivityOutput dbmz = hrmsPerformanceObjectiveOutcomeActivityOutputRepository
						.findById(dbm.getOutputid()).get();

				performanceObjectiveOutcomeActivityOutput = dbmz;

			}

			performanceObjectiveOutcomeActivityOutputTargetResponse
					.setPerformanceObjectiveOutcomeActivityOutput(performanceObjectiveOutcomeActivityOutput);

			performanceObjectiveOutcomeActivityOutputTargetResponselist
					.add(performanceObjectiveOutcomeActivityOutputTargetResponse);

		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceObjectiveOutcomeActivityOutputTargetResponselist);
	}

}
