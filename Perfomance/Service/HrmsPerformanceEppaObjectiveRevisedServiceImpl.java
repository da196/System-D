package com.Hrms.Perfomance.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Perfomance.DTO.PerformanceEppaObjectiveRevisedResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEppaObjective;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEppaObjectiveRevised;
import com.Hrms.Perfomance.Repository.HrmsPerformanceEppaObjectiveRepository;
import com.Hrms.Perfomance.Repository.HrmsPerformanceEppaObjectiveRevisedRepository;

@Service
public class HrmsPerformanceEppaObjectiveRevisedServiceImpl implements HrmsPerformanceEppaObjectiveRevisedService {

	@Autowired

	private HrmsPerformanceEppaObjectiveRevisedRepository hrmsPerformanceEppaObjectiveRevisedRepository;

	@Autowired
	private HrmsPerformanceEppaObjectiveRepository hrmsPerformanceEppaObjectiveRepository;

	@Override
	public ResponseEntity<HrmsPerformanceEppaObjectiveRevised> addPerformanceEppaObjectiveRevised(
			HrmsPerformanceEppaObjectiveRevised hrmsPerformanceEppaObjectiveRevised) {
		if (hrmsPerformanceEppaObjectiveRevisedRepository.existsByTargetsAndCriteriaAndObjectiveidAndActive(
				hrmsPerformanceEppaObjectiveRevised.getTargets(), hrmsPerformanceEppaObjectiveRevised.getCriteria(),
				hrmsPerformanceEppaObjectiveRevised.getObjectiveid(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsPerformanceEppaObjectiveRevised);
		} else {
			if (hrmsPerformanceEppaObjectiveRepository
					.existsByIdAndActive(hrmsPerformanceEppaObjectiveRevised.getObjectiveid(), 1)) {
				hrmsPerformanceEppaObjectiveRevised.setActive(1);
				hrmsPerformanceEppaObjectiveRevised.setApproved(0);
				hrmsPerformanceEppaObjectiveRevised.setUnique_id(UUID.randomUUID());

				// save revised object

				HrmsPerformanceEppaObjectiveRevised hrmsPerformanceEppaObjectiveRevised1 = hrmsPerformanceEppaObjectiveRevisedRepository
						.saveAndFlush(hrmsPerformanceEppaObjectiveRevised);
				if (hrmsPerformanceEppaObjectiveRevised1 != null) {
					HrmsPerformanceEppaObjective hrmsPerformanceEppaObjective = hrmsPerformanceEppaObjectiveRepository
							.findById(hrmsPerformanceEppaObjectiveRevised.getObjectiveid()).get();
					hrmsPerformanceEppaObjective.setRevised(1);
					hrmsPerformanceEppaObjective.setDate_updated(LocalDateTime.now());

					hrmsPerformanceEppaObjectiveRepository.saveAndFlush(hrmsPerformanceEppaObjective);

				}

				return ResponseEntity.status(HttpStatus.OK).body(hrmsPerformanceEppaObjectiveRevised1);
			} else {

				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsPerformanceEppaObjectiveRevised);

			}

		}
	}

	@Override
	public ResponseEntity<PerformanceEppaObjectiveRevisedResponse> getPerformanceEppaObjectiveRevisedById(int id) {

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		PerformanceEppaObjectiveRevisedResponse performanceEppaObjectiveRevisedResponse = new PerformanceEppaObjectiveRevisedResponse();

		if (hrmsPerformanceEppaObjectiveRevisedRepository.existsByIdAndActive(id, 1)) {

			HrmsPerformanceEppaObjectiveRevised dbm = hrmsPerformanceEppaObjectiveRevisedRepository
					.findByIdAndActive(id, 1);

			performanceEppaObjectiveRevisedResponse.setActive(dbm.getActive());
			performanceEppaObjectiveRevisedResponse.setApproved(dbm.getApproved());
			performanceEppaObjectiveRevisedResponse.setCriteria(dbm.getCriteria());
			performanceEppaObjectiveRevisedResponse.setId(dbm.getId());
			performanceEppaObjectiveRevisedResponse.setDescription(dbm.getDescription());
			if (dbm.getDaterevised() != null) {
				performanceEppaObjectiveRevisedResponse.setDaterevised(simpleDateFormat.format(dbm.getDaterevised()));
			}
			performanceEppaObjectiveRevisedResponse.setTargets(dbm.getTargets());
			performanceEppaObjectiveRevisedResponse.setObjectiveid(dbm.getObjectiveid());

			if (dbm.getObjectiveid() != 0 && hrmsPerformanceEppaObjectiveRepository.existsById(dbm.getObjectiveid())) {
				performanceEppaObjectiveRevisedResponse.setPerformanceEppaObjective(
						hrmsPerformanceEppaObjectiveRepository.findById(dbm.getObjectiveid()).get());

			}

		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceEppaObjectiveRevisedResponse);
	}

	@Override
	public ResponseEntity<HrmsPerformanceEppaObjectiveRevised> updatePerformanceEppaObjectiveRevised(
			HrmsPerformanceEppaObjectiveRevised hrmsPerformanceEppaObjectiveRevised, int id) {
		if (hrmsPerformanceEppaObjectiveRevisedRepository.existsByIdAndActive(id, 1)) {
			if (hrmsPerformanceEppaObjectiveRepository
					.existsByIdAndActive(hrmsPerformanceEppaObjectiveRevised.getObjectiveid(), 1)) {
				hrmsPerformanceEppaObjectiveRevised.setActive(1);
				hrmsPerformanceEppaObjectiveRevised.setApproved(0);
				hrmsPerformanceEppaObjectiveRevised.setDate_updated(LocalDateTime.now());
				if (hrmsPerformanceEppaObjectiveRevisedRepository.findById(id).get().getDate_created() != null) {
					hrmsPerformanceEppaObjectiveRevised.setDate_created(
							hrmsPerformanceEppaObjectiveRevisedRepository.findById(id).get().getDate_created());
				}

				if (hrmsPerformanceEppaObjectiveRevisedRepository.findById(id).get().getUnique_id() != null) {
					hrmsPerformanceEppaObjectiveRevised.setUnique_id(
							hrmsPerformanceEppaObjectiveRevisedRepository.findById(id).get().getUnique_id());
				}

				return ResponseEntity.status(HttpStatus.OK).body(hrmsPerformanceEppaObjectiveRevisedRepository
						.saveAndFlush(hrmsPerformanceEppaObjectiveRevised));
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsPerformanceEppaObjectiveRevised);
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deletePerformanceEppaObjectiveRevised(int id) {
		if (hrmsPerformanceEppaObjectiveRevisedRepository.existsByIdAndActive(id, 1)) {
			HrmsPerformanceEppaObjectiveRevised hrmsPerformanceEppaObjectiveRevised = hrmsPerformanceEppaObjectiveRevisedRepository
					.findByIdAndActive(id, 1);
			hrmsPerformanceEppaObjectiveRevised.setActive(0);
			hrmsPerformanceEppaObjectiveRevised.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK).body(
					hrmsPerformanceEppaObjectiveRevisedRepository.saveAndFlush(hrmsPerformanceEppaObjectiveRevised));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<PerformanceEppaObjectiveRevisedResponse>> getAllPerformanceEppaObjectiveRevised() {

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		List<PerformanceEppaObjectiveRevisedResponse> performanceEppaObjectiveRevisedResponselist = new ArrayList<>();

		List<HrmsPerformanceEppaObjectiveRevised> dbms = hrmsPerformanceEppaObjectiveRevisedRepository.findByActive(1);

		for (HrmsPerformanceEppaObjectiveRevised dbm : dbms) {

			PerformanceEppaObjectiveRevisedResponse performanceEppaObjectiveRevisedResponse = new PerformanceEppaObjectiveRevisedResponse();

			performanceEppaObjectiveRevisedResponse.setActive(dbm.getActive());
			performanceEppaObjectiveRevisedResponse.setApproved(dbm.getApproved());
			performanceEppaObjectiveRevisedResponse.setCriteria(dbm.getCriteria());
			performanceEppaObjectiveRevisedResponse.setId(dbm.getId());
			performanceEppaObjectiveRevisedResponse.setDescription(dbm.getDescription());
			if (dbm.getDaterevised() != null) {
				performanceEppaObjectiveRevisedResponse.setDaterevised(simpleDateFormat.format(dbm.getDaterevised()));
			}
			performanceEppaObjectiveRevisedResponse.setTargets(dbm.getTargets());
			performanceEppaObjectiveRevisedResponse.setObjectiveid(dbm.getObjectiveid());

			if (dbm.getObjectiveid() != 0 && hrmsPerformanceEppaObjectiveRepository.existsById(dbm.getObjectiveid())) {
				performanceEppaObjectiveRevisedResponse.setPerformanceEppaObjective(
						hrmsPerformanceEppaObjectiveRepository.findById(dbm.getObjectiveid()).get());

			}

			performanceEppaObjectiveRevisedResponselist.add(performanceEppaObjectiveRevisedResponse);

		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceEppaObjectiveRevisedResponselist);
	}

	@Override
	public ResponseEntity<List<PerformanceEppaObjectiveRevisedResponse>> getAllPerformanceEppaObjectiveRevisedByObjectiveId(
			int objectiveid) {

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		List<PerformanceEppaObjectiveRevisedResponse> performanceEppaObjectiveRevisedResponselist = new ArrayList<>();

		List<HrmsPerformanceEppaObjectiveRevised> dbms = hrmsPerformanceEppaObjectiveRevisedRepository
				.findByObjectiveidAndActive(objectiveid, 1);

		for (HrmsPerformanceEppaObjectiveRevised dbm : dbms) {

			PerformanceEppaObjectiveRevisedResponse performanceEppaObjectiveRevisedResponse = new PerformanceEppaObjectiveRevisedResponse();

			performanceEppaObjectiveRevisedResponse.setActive(dbm.getActive());
			performanceEppaObjectiveRevisedResponse.setApproved(dbm.getApproved());
			performanceEppaObjectiveRevisedResponse.setCriteria(dbm.getCriteria());
			performanceEppaObjectiveRevisedResponse.setId(dbm.getId());
			performanceEppaObjectiveRevisedResponse.setDescription(dbm.getDescription());
			if (dbm.getDaterevised() != null) {
				performanceEppaObjectiveRevisedResponse.setDaterevised(simpleDateFormat.format(dbm.getDaterevised()));
			}
			performanceEppaObjectiveRevisedResponse.setTargets(dbm.getTargets());
			performanceEppaObjectiveRevisedResponse.setObjectiveid(dbm.getObjectiveid());

			if (dbm.getObjectiveid() != 0 && hrmsPerformanceEppaObjectiveRepository.existsById(dbm.getObjectiveid())) {
				performanceEppaObjectiveRevisedResponse.setPerformanceEppaObjective(
						hrmsPerformanceEppaObjectiveRepository.findById(dbm.getObjectiveid()).get());

			}

			performanceEppaObjectiveRevisedResponselist.add(performanceEppaObjectiveRevisedResponse);

		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceEppaObjectiveRevisedResponselist);
	}

}
