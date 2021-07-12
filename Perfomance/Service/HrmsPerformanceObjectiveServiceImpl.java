package com.Hrms.Perfomance.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Perfomance.DTO.PerformanceObjectiveResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceGoal;
import com.Hrms.Perfomance.Entity.HrmsPerformanceObjective;
import com.Hrms.Perfomance.Repository.HrmsPerformanceGoalRepository;
import com.Hrms.Perfomance.Repository.HrmsPerformanceObjectiveRepository;

@Service
public class HrmsPerformanceObjectiveServiceImpl implements HrmsPerformanceObjectiveService {
	@Autowired
	private HrmsPerformanceObjectiveRepository hrmsPerformanceObjectiveRepository;

	@Autowired
	private HrmsPerformanceGoalRepository hrmsPerformanceGoalRepository;

	@Override
	public ResponseEntity<HrmsPerformanceObjective> addPerformanceObjective(
			HrmsPerformanceObjective hrmsPerformanceObjective) {
		if (hrmsPerformanceObjectiveRepository.existsByDescriptionAndActive(hrmsPerformanceObjective.getDescription(),
				1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsPerformanceObjective);
		} else {
			if (hrmsPerformanceGoalRepository.existsByIdAndActive(hrmsPerformanceObjective.getGoalid(), 1)) {
				hrmsPerformanceObjective.setActive(1);
				hrmsPerformanceObjective.setApproved(0);
				hrmsPerformanceObjective.setUnique_id(UUID.randomUUID());

				return ResponseEntity.status(HttpStatus.OK)
						.body(hrmsPerformanceObjectiveRepository.saveAndFlush(hrmsPerformanceObjective));
			} else {

				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsPerformanceObjective);

			}

		}
	}

	@Override
	public ResponseEntity<PerformanceObjectiveResponse> getPerformanceObjectiveById(int id) {

		PerformanceObjectiveResponse performanceObjectiveResponse = new PerformanceObjectiveResponse();

		if (hrmsPerformanceObjectiveRepository.existsById(id)) {

			HrmsPerformanceObjective dbm = hrmsPerformanceObjectiveRepository.findById(id).get();

			performanceObjectiveResponse.setActive(dbm.getActive());
			performanceObjectiveResponse.setApproved(dbm.getApproved());
			performanceObjectiveResponse.setDescription(dbm.getDescription());
			performanceObjectiveResponse.setGoalid(dbm.getGoalid());
			performanceObjectiveResponse.setId(dbm.getId());

			HrmsPerformanceGoal performanceGoal = new HrmsPerformanceGoal();
			if (hrmsPerformanceGoalRepository.existsById(dbm.getGoalid())) {

				HrmsPerformanceGoal dbmpg = hrmsPerformanceGoalRepository.findById(dbm.getGoalid()).get();

				performanceGoal.setActive(dbmpg.getActive());
				performanceGoal.setApproved(dbmpg.getApproved());
				performanceGoal.setDate_created(dbmpg.getDate_created());
				performanceGoal.setDate_updated(dbmpg.getDate_updated());
				performanceGoal.setDescription(dbmpg.getDescription());
				performanceGoal.setId(dbmpg.getId());
				performanceGoal.setName(dbmpg.getName());
				performanceGoal.setPlanid(dbmpg.getPlanid());
				performanceGoal.setUnique_id(dbmpg.getUnique_id());

			}

			performanceObjectiveResponse.setPerformanceGoal(performanceGoal);
		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceObjectiveResponse);
	}

	@Override
	public ResponseEntity<HrmsPerformanceObjective> updatePerformanceObjective(
			HrmsPerformanceObjective hrmsPerformanceObjective, int id) {
		if (hrmsPerformanceObjectiveRepository.existsByIdAndActive(id, 1)) {
			if (hrmsPerformanceGoalRepository.existsByIdAndActive(hrmsPerformanceObjective.getGoalid(), 1)) {
				hrmsPerformanceObjective.setActive(1);
				hrmsPerformanceObjective.setApproved(0);
				hrmsPerformanceObjective.setDate_updated(LocalDateTime.now());
				if (hrmsPerformanceObjectiveRepository.findById(id).get().getDate_created() != null) {
					hrmsPerformanceObjective
							.setDate_created(hrmsPerformanceObjectiveRepository.findById(id).get().getDate_created());
				}

				if (hrmsPerformanceObjectiveRepository.findById(id).get().getUnique_id() != null) {
					hrmsPerformanceObjective
							.setUnique_id(hrmsPerformanceObjectiveRepository.findById(id).get().getUnique_id());
				}

				return ResponseEntity.status(HttpStatus.OK)
						.body(hrmsPerformanceObjectiveRepository.saveAndFlush(hrmsPerformanceObjective));
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsPerformanceObjective);
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deletePerformanceObjective(int id) {
		if (hrmsPerformanceObjectiveRepository.existsByIdAndActive(id, 1)) {
			HrmsPerformanceObjective hrmsPerformanceObjective = hrmsPerformanceObjectiveRepository.findByIdAndActive(id,
					1);
			hrmsPerformanceObjective.setActive(0);
			hrmsPerformanceObjective.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPerformanceObjectiveRepository.saveAndFlush(hrmsPerformanceObjective));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<PerformanceObjectiveResponse>> getAllPerformanceObjective() {
		List<PerformanceObjectiveResponse> performanceObjectiveResponselist = new ArrayList<>();

		List<HrmsPerformanceObjective> dbms = hrmsPerformanceObjectiveRepository.findByActive(1);

		for (HrmsPerformanceObjective dbm : dbms) {

			PerformanceObjectiveResponse performanceObjectiveResponse = new PerformanceObjectiveResponse();

			performanceObjectiveResponse.setActive(dbm.getActive());
			performanceObjectiveResponse.setApproved(dbm.getApproved());
			performanceObjectiveResponse.setDescription(dbm.getDescription());
			performanceObjectiveResponse.setGoalid(dbm.getGoalid());
			performanceObjectiveResponse.setId(dbm.getId());

			HrmsPerformanceGoal performanceGoal = new HrmsPerformanceGoal();
			if (hrmsPerformanceGoalRepository.existsById(dbm.getGoalid())) {

				HrmsPerformanceGoal dbmpg = hrmsPerformanceGoalRepository.findById(dbm.getGoalid()).get();

				performanceGoal.setActive(dbmpg.getActive());
				performanceGoal.setApproved(dbmpg.getApproved());
				performanceGoal.setDate_created(dbmpg.getDate_created());
				performanceGoal.setDate_updated(dbmpg.getDate_updated());
				performanceGoal.setDescription(dbmpg.getDescription());
				performanceGoal.setId(dbmpg.getId());
				performanceGoal.setName(dbmpg.getName());
				performanceGoal.setPlanid(dbmpg.getPlanid());
				performanceGoal.setUnique_id(dbmpg.getUnique_id());

			}

			performanceObjectiveResponse.setPerformanceGoal(performanceGoal);

			performanceObjectiveResponselist.add(performanceObjectiveResponse);

		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceObjectiveResponselist);
	}

	@Override
	public ResponseEntity<List<PerformanceObjectiveResponse>> getAllPerformanceObjectiveByGoalId(int goalid) {
		List<PerformanceObjectiveResponse> performanceObjectiveResponselist = new ArrayList<>();

		List<HrmsPerformanceObjective> dbms = hrmsPerformanceObjectiveRepository.findByGoalidAndActive(goalid, 1);

		for (HrmsPerformanceObjective dbm : dbms) {

			PerformanceObjectiveResponse performanceObjectiveResponse = new PerformanceObjectiveResponse();

			performanceObjectiveResponse.setActive(dbm.getActive());
			performanceObjectiveResponse.setApproved(dbm.getApproved());
			performanceObjectiveResponse.setDescription(dbm.getDescription());
			performanceObjectiveResponse.setGoalid(dbm.getGoalid());
			performanceObjectiveResponse.setId(dbm.getId());

			HrmsPerformanceGoal performanceGoal = new HrmsPerformanceGoal();
			if (hrmsPerformanceGoalRepository.existsById(dbm.getGoalid())) {

				HrmsPerformanceGoal dbmpg = hrmsPerformanceGoalRepository.findById(dbm.getGoalid()).get();

				performanceGoal.setActive(dbmpg.getActive());
				performanceGoal.setApproved(dbmpg.getApproved());
				performanceGoal.setDate_created(dbmpg.getDate_created());
				performanceGoal.setDate_updated(dbmpg.getDate_updated());
				performanceGoal.setDescription(dbmpg.getDescription());
				performanceGoal.setId(dbmpg.getId());
				performanceGoal.setName(dbmpg.getName());
				performanceGoal.setPlanid(dbmpg.getPlanid());
				performanceGoal.setUnique_id(dbmpg.getUnique_id());

			}

			performanceObjectiveResponse.setPerformanceGoal(performanceGoal);

			performanceObjectiveResponselist.add(performanceObjectiveResponse);

		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceObjectiveResponselist);
	}

}
