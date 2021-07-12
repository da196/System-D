package com.Hrms.Perfomance.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Perfomance.DTO.PerformanceObjectiveOutcomeResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceObjective;
import com.Hrms.Perfomance.Entity.HrmsPerformanceObjectiveOutcome;
import com.Hrms.Perfomance.Repository.HrmsPerformanceObjectiveOutcomeRepository;
import com.Hrms.Perfomance.Repository.HrmsPerformanceObjectiveRepository;

@Service
public class HrmsPerformanceObjectiveOutcomeServiceImpl implements HrmsPerformanceObjectiveOutcomeService {

	@Autowired
	private HrmsPerformanceObjectiveOutcomeRepository hrmsPerformanceObjectiveOutcomeRepository;

	@Autowired
	private HrmsPerformanceObjectiveRepository hrmsPerformanceObjectiveRepository;

	@Override
	public ResponseEntity<HrmsPerformanceObjectiveOutcome> addPerformanceObjectiveOutcome(
			HrmsPerformanceObjectiveOutcome hrmsPerformanceObjectiveOutcome) {
		if (hrmsPerformanceObjectiveOutcomeRepository
				.existsByDescriptionAndActive(hrmsPerformanceObjectiveOutcome.getDescription(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsPerformanceObjectiveOutcome);
		} else {
			if (hrmsPerformanceObjectiveRepository.existsByIdAndActive(hrmsPerformanceObjectiveOutcome.getObjectiveid(),
					1)) {
				hrmsPerformanceObjectiveOutcome.setActive(1);
				hrmsPerformanceObjectiveOutcome.setApproved(0);
				hrmsPerformanceObjectiveOutcome.setUnique_id(UUID.randomUUID());

				return ResponseEntity.status(HttpStatus.OK)
						.body(hrmsPerformanceObjectiveOutcomeRepository.saveAndFlush(hrmsPerformanceObjectiveOutcome));
			} else {

				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsPerformanceObjectiveOutcome);

			}

		}
	}

	@Override
	public ResponseEntity<PerformanceObjectiveOutcomeResponse> getPerformanceObjectiveOutcomeById(int id) {

		PerformanceObjectiveOutcomeResponse performanceObjectiveOutcomeResponse = new PerformanceObjectiveOutcomeResponse();

		if (hrmsPerformanceObjectiveOutcomeRepository.existsById(id)) {

			HrmsPerformanceObjectiveOutcome dbm = hrmsPerformanceObjectiveOutcomeRepository.findById(id).get();

			performanceObjectiveOutcomeResponse.setActive(dbm.getActive());
			performanceObjectiveOutcomeResponse.setApproved(dbm.getApproved());
			performanceObjectiveOutcomeResponse.setDescription(dbm.getDescription());
			performanceObjectiveOutcomeResponse.setId(dbm.getId());
			performanceObjectiveOutcomeResponse.setObjectiveid(dbm.getObjectiveid());

			HrmsPerformanceObjective performanceObjective = new HrmsPerformanceObjective();

			if (hrmsPerformanceObjectiveRepository.existsById(dbm.getObjectiveid())) {
				HrmsPerformanceObjective hrmsPerformanceObjective = hrmsPerformanceObjectiveRepository
						.findById(dbm.getObjectiveid()).get();

				performanceObjective = hrmsPerformanceObjective;

			}

			performanceObjectiveOutcomeResponse.setPerformanceObjective(performanceObjective);

		}
		return ResponseEntity.status(HttpStatus.OK).body(performanceObjectiveOutcomeResponse);
	}

	@Override
	public ResponseEntity<HrmsPerformanceObjectiveOutcome> updatePerformanceObjectiveOutcome(
			HrmsPerformanceObjectiveOutcome hrmsPerformanceObjectiveOutcome, int id) {

		if (hrmsPerformanceObjectiveRepository.existsByIdAndActive(id, 1)) {
			if (hrmsPerformanceObjectiveOutcomeRepository
					.existsByIdAndActive(hrmsPerformanceObjectiveOutcome.getObjectiveid(), 1)) {
				hrmsPerformanceObjectiveOutcome.setActive(1);
				hrmsPerformanceObjectiveOutcome.setApproved(0);
				hrmsPerformanceObjectiveOutcome.setDate_updated(LocalDateTime.now());
				if (hrmsPerformanceObjectiveRepository.findById(id).get().getDate_created() != null) {
					hrmsPerformanceObjectiveOutcome
							.setDate_created(hrmsPerformanceObjectiveRepository.findById(id).get().getDate_created());
				}

				if (hrmsPerformanceObjectiveRepository.findById(id).get().getUnique_id() != null) {
					hrmsPerformanceObjectiveOutcome
							.setUnique_id(hrmsPerformanceObjectiveRepository.findById(id).get().getUnique_id());
				}

				return ResponseEntity.status(HttpStatus.OK)
						.body(hrmsPerformanceObjectiveOutcomeRepository.saveAndFlush(hrmsPerformanceObjectiveOutcome));
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsPerformanceObjectiveOutcome);
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deletePerformanceObjectiveOutcome(int id) {
		if (hrmsPerformanceObjectiveOutcomeRepository.existsByIdAndActive(id, 1)) {
			HrmsPerformanceObjectiveOutcome hrmsPerformanceObjectiveOutcome = hrmsPerformanceObjectiveOutcomeRepository
					.findByIdAndActive(id, 1);
			hrmsPerformanceObjectiveOutcome.setActive(0);
			hrmsPerformanceObjectiveOutcome.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPerformanceObjectiveOutcomeRepository.saveAndFlush(hrmsPerformanceObjectiveOutcome));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<PerformanceObjectiveOutcomeResponse>> getAllPerformanceObjectiveOutcome() {

		List<PerformanceObjectiveOutcomeResponse> performanceObjectiveOutcomeResponselist = new ArrayList<>();

		List<HrmsPerformanceObjectiveOutcome> dbms = hrmsPerformanceObjectiveOutcomeRepository.findByActive(1);

		for (HrmsPerformanceObjectiveOutcome dbm : dbms) {
			PerformanceObjectiveOutcomeResponse performanceObjectiveOutcomeResponse = new PerformanceObjectiveOutcomeResponse();

			performanceObjectiveOutcomeResponse.setActive(dbm.getActive());
			performanceObjectiveOutcomeResponse.setApproved(dbm.getApproved());
			performanceObjectiveOutcomeResponse.setDescription(dbm.getDescription());
			performanceObjectiveOutcomeResponse.setId(dbm.getId());
			performanceObjectiveOutcomeResponse.setObjectiveid(dbm.getObjectiveid());

			HrmsPerformanceObjective performanceObjective = new HrmsPerformanceObjective();

			if (hrmsPerformanceObjectiveRepository.existsById(dbm.getObjectiveid())) {
				HrmsPerformanceObjective hrmsPerformanceObjective = hrmsPerformanceObjectiveRepository
						.findById(dbm.getObjectiveid()).get();

				performanceObjective.setActive(hrmsPerformanceObjective.getActive());
				performanceObjective.setApproved(hrmsPerformanceObjective.getApproved());
				performanceObjective.setDate_created(hrmsPerformanceObjective.getDate_created());
				performanceObjective.setDate_updated(hrmsPerformanceObjective.getDate_updated());
				performanceObjective.setDescription(hrmsPerformanceObjective.getDescription());
				performanceObjective.setGoalid(hrmsPerformanceObjective.getGoalid());
				performanceObjective.setId(hrmsPerformanceObjective.getId());
				performanceObjective.setUnique_id(hrmsPerformanceObjective.getUnique_id());

			}

			performanceObjectiveOutcomeResponse.setPerformanceObjective(performanceObjective);

			performanceObjectiveOutcomeResponselist.add(performanceObjectiveOutcomeResponse);
		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceObjectiveOutcomeResponselist);
	}

	@Override
	public ResponseEntity<List<PerformanceObjectiveOutcomeResponse>> getAllPerformanceObjectiveOutcomeByObjectiveId(
			int objectiveid) {

		List<PerformanceObjectiveOutcomeResponse> performanceObjectiveOutcomeResponselist = new ArrayList<>();

		List<HrmsPerformanceObjectiveOutcome> dbms = hrmsPerformanceObjectiveOutcomeRepository
				.findByObjectiveidAndActive(objectiveid, 1);

		for (HrmsPerformanceObjectiveOutcome dbm : dbms) {
			PerformanceObjectiveOutcomeResponse performanceObjectiveOutcomeResponse = new PerformanceObjectiveOutcomeResponse();

			performanceObjectiveOutcomeResponse.setActive(dbm.getActive());
			performanceObjectiveOutcomeResponse.setApproved(dbm.getApproved());
			performanceObjectiveOutcomeResponse.setDescription(dbm.getDescription());
			performanceObjectiveOutcomeResponse.setId(dbm.getId());
			performanceObjectiveOutcomeResponse.setObjectiveid(dbm.getObjectiveid());

			HrmsPerformanceObjective performanceObjective = new HrmsPerformanceObjective();

			if (hrmsPerformanceObjectiveRepository.existsById(dbm.getObjectiveid())) {
				HrmsPerformanceObjective hrmsPerformanceObjective = hrmsPerformanceObjectiveRepository
						.findById(dbm.getObjectiveid()).get();

				performanceObjective.setActive(hrmsPerformanceObjective.getActive());
				performanceObjective.setApproved(hrmsPerformanceObjective.getApproved());
				performanceObjective.setDate_created(hrmsPerformanceObjective.getDate_created());
				performanceObjective.setDate_updated(hrmsPerformanceObjective.getDate_updated());
				performanceObjective.setDescription(hrmsPerformanceObjective.getDescription());
				performanceObjective.setGoalid(hrmsPerformanceObjective.getGoalid());
				performanceObjective.setId(hrmsPerformanceObjective.getId());
				performanceObjective.setUnique_id(hrmsPerformanceObjective.getUnique_id());

			}

			performanceObjectiveOutcomeResponse.setPerformanceObjective(performanceObjective);

			performanceObjectiveOutcomeResponselist.add(performanceObjectiveOutcomeResponse);
		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceObjectiveOutcomeResponselist);
	}

}
