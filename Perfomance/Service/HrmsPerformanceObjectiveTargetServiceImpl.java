package com.Hrms.Perfomance.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Perfomance.DTO.PerformanceObjectiveTargetResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceObjective;
import com.Hrms.Perfomance.Entity.HrmsPerformanceObjectiveTarget;
import com.Hrms.Perfomance.Repository.HrmsPerformanceObjectiveRepository;
import com.Hrms.Perfomance.Repository.HrmsPerformanceObjectiveTargetRepository;

@Service
public class HrmsPerformanceObjectiveTargetServiceImpl implements HrmsPerformanceObjectiveTargetService {

	@Autowired
	private HrmsPerformanceObjectiveTargetRepository hrmsPerformanceObjectiveTargetRepository;

	@Autowired
	private HrmsPerformanceObjectiveRepository hrmsPerformanceObjectiveRepository;

	@Override
	public ResponseEntity<HrmsPerformanceObjectiveTarget> addPerformanceObjectiveTarget(
			HrmsPerformanceObjectiveTarget hrmsPerformanceObjectiveTarget) {
		if (hrmsPerformanceObjectiveTargetRepository.existsByNameAndObjectiveidAndActive(
				hrmsPerformanceObjectiveTarget.getName(), hrmsPerformanceObjectiveTarget.getObjectiveid(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsPerformanceObjectiveTarget);
		} else {
			if (hrmsPerformanceObjectiveRepository.existsByIdAndActive(hrmsPerformanceObjectiveTarget.getObjectiveid(),
					1)) {
				hrmsPerformanceObjectiveTarget.setActive(1);
				hrmsPerformanceObjectiveTarget.setApproved(0);
				hrmsPerformanceObjectiveTarget.setUnique_id(UUID.randomUUID());

				return ResponseEntity.status(HttpStatus.OK)
						.body(hrmsPerformanceObjectiveTargetRepository.saveAndFlush(hrmsPerformanceObjectiveTarget));
			} else {

				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsPerformanceObjectiveTarget);

			}

		}
	}

	@Override
	public ResponseEntity<PerformanceObjectiveTargetResponse> getPerformanceObjectiveTargetById(int id) {
		PerformanceObjectiveTargetResponse performanceObjectiveTargetResponse = new PerformanceObjectiveTargetResponse();

		if (hrmsPerformanceObjectiveTargetRepository.existsById(id)) {

			HrmsPerformanceObjectiveTarget dbm = hrmsPerformanceObjectiveTargetRepository.findById(id).get();

			performanceObjectiveTargetResponse.setActive(dbm.getActive());
			performanceObjectiveTargetResponse.setApproved(dbm.getApproved());
			performanceObjectiveTargetResponse.setDescription(dbm.getDescription());
			performanceObjectiveTargetResponse.setObjectiveid(dbm.getObjectiveid());
			performanceObjectiveTargetResponse.setId(dbm.getId());
			performanceObjectiveTargetResponse.setName(dbm.getName());

			HrmsPerformanceObjective performanceObjective = new HrmsPerformanceObjective();
			if (hrmsPerformanceObjectiveRepository.existsById(dbm.getObjectiveid())) {

				HrmsPerformanceObjective dbmpg = hrmsPerformanceObjectiveRepository.findById(dbm.getObjectiveid())
						.get();

				performanceObjective = dbmpg;

			}

			performanceObjectiveTargetResponse.setPerformanceObjective(performanceObjective);
		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceObjectiveTargetResponse);
	}

	@Override
	public ResponseEntity<HrmsPerformanceObjectiveTarget> updatePerformanceObjectiveTarget(
			HrmsPerformanceObjectiveTarget hrmsPerformanceObjectiveTarget, int id) {
		if (hrmsPerformanceObjectiveTargetRepository.existsByIdAndActive(id, 1)) {
			if (hrmsPerformanceObjectiveRepository.existsByIdAndActive(hrmsPerformanceObjectiveTarget.getObjectiveid(),
					1)) {
				hrmsPerformanceObjectiveTarget.setActive(1);
				hrmsPerformanceObjectiveTarget.setApproved(0);
				hrmsPerformanceObjectiveTarget.setDate_updated(LocalDateTime.now());
				if (hrmsPerformanceObjectiveTargetRepository.findById(id).get().getDate_created() != null) {
					hrmsPerformanceObjectiveTarget.setDate_created(
							hrmsPerformanceObjectiveTargetRepository.findById(id).get().getDate_created());
				}

				if (hrmsPerformanceObjectiveTargetRepository.findById(id).get().getUnique_id() != null) {
					hrmsPerformanceObjectiveTarget
							.setUnique_id(hrmsPerformanceObjectiveTargetRepository.findById(id).get().getUnique_id());
				}

				return ResponseEntity.status(HttpStatus.OK)
						.body(hrmsPerformanceObjectiveTargetRepository.saveAndFlush(hrmsPerformanceObjectiveTarget));
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsPerformanceObjectiveTarget);
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deletePerformanceObjectiveTarget(int id) {
		if (hrmsPerformanceObjectiveTargetRepository.existsByIdAndActive(id, 1)) {
			HrmsPerformanceObjectiveTarget hrmsPerformanceObjectiveTraget = hrmsPerformanceObjectiveTargetRepository
					.findByIdAndActive(id, 1);
			hrmsPerformanceObjectiveTraget.setActive(0);
			hrmsPerformanceObjectiveTraget.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPerformanceObjectiveTargetRepository.saveAndFlush(hrmsPerformanceObjectiveTraget));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<PerformanceObjectiveTargetResponse>> getAllPerformanceObjectiveTarget() {
		List<PerformanceObjectiveTargetResponse> performanceObjectiveTargetResponselist = new ArrayList<>();

		List<HrmsPerformanceObjectiveTarget> dbms = hrmsPerformanceObjectiveTargetRepository.findByActive(1);

		for (HrmsPerformanceObjectiveTarget dbm : dbms) {

			PerformanceObjectiveTargetResponse performanceObjectiveTargetResponse = new PerformanceObjectiveTargetResponse();

			performanceObjectiveTargetResponse.setActive(dbm.getActive());
			performanceObjectiveTargetResponse.setApproved(dbm.getApproved());
			performanceObjectiveTargetResponse.setDescription(dbm.getDescription());
			performanceObjectiveTargetResponse.setObjectiveid(dbm.getObjectiveid());
			performanceObjectiveTargetResponse.setId(dbm.getId());
			performanceObjectiveTargetResponse.setName(dbm.getName());

			HrmsPerformanceObjective performanceObjective = new HrmsPerformanceObjective();
			if (hrmsPerformanceObjectiveRepository.existsById(dbm.getObjectiveid())) {

				HrmsPerformanceObjective dbmpg = hrmsPerformanceObjectiveRepository.findById(dbm.getObjectiveid())
						.get();

				performanceObjective = dbmpg;

			}

			performanceObjectiveTargetResponse.setPerformanceObjective(performanceObjective);

			performanceObjectiveTargetResponselist.add(performanceObjectiveTargetResponse);

		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceObjectiveTargetResponselist);

	}

	@Override
	public ResponseEntity<List<PerformanceObjectiveTargetResponse>> getAllPerformanceObjectiveTargetByObjectiveId(
			int objectiveid) {
		List<PerformanceObjectiveTargetResponse> performanceObjectiveTargetResponselist = new ArrayList<>();

		List<HrmsPerformanceObjectiveTarget> dbms = hrmsPerformanceObjectiveTargetRepository
				.findByObjectiveidAndActive(objectiveid, 1);

		for (HrmsPerformanceObjectiveTarget dbm : dbms) {

			PerformanceObjectiveTargetResponse performanceObjectiveTargetResponse = new PerformanceObjectiveTargetResponse();

			performanceObjectiveTargetResponse.setActive(dbm.getActive());
			performanceObjectiveTargetResponse.setApproved(dbm.getApproved());
			performanceObjectiveTargetResponse.setDescription(dbm.getDescription());
			performanceObjectiveTargetResponse.setObjectiveid(dbm.getObjectiveid());
			performanceObjectiveTargetResponse.setId(dbm.getId());
			performanceObjectiveTargetResponse.setName(dbm.getName());

			HrmsPerformanceObjective performanceObjective = new HrmsPerformanceObjective();
			if (hrmsPerformanceObjectiveRepository.existsById(dbm.getObjectiveid())) {

				HrmsPerformanceObjective dbmpg = hrmsPerformanceObjectiveRepository.findById(dbm.getObjectiveid())
						.get();

				performanceObjective = dbmpg;

			}

			performanceObjectiveTargetResponse.setPerformanceObjective(performanceObjective);

			performanceObjectiveTargetResponselist.add(performanceObjectiveTargetResponse);

		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceObjectiveTargetResponselist);
	}

}
