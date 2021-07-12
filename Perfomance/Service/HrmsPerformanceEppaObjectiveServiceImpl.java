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

import com.Hrms.Perfomance.DTO.PerformanceEppaObjectiveResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEppaObjective;
import com.Hrms.Perfomance.Repository.HrmsPerformanceEppaObjectiveRepository;
import com.Hrms.Perfomance.Repository.HrmsPerformanceEppaRepository;

@Service
public class HrmsPerformanceEppaObjectiveServiceImpl implements HrmsPerformanceEppaObjectiveService {

	@Autowired
	private HrmsPerformanceEppaObjectiveRepository hrmsPerformanceEppaObjectiveRepository;

	@Autowired
	private HrmsPerformanceEppaRepository hrmsPerformanceEppaRepository;

	@Override
	public ResponseEntity<HrmsPerformanceEppaObjective> addPerformanceEppaObjective(
			HrmsPerformanceEppaObjective hrmsPerformanceEppaObjective) {
		if (hrmsPerformanceEppaObjectiveRepository.existsByTargetsAndCriteriaAndEppaidAndActive(
				hrmsPerformanceEppaObjective.getTargets(), hrmsPerformanceEppaObjective.getCriteria(),
				hrmsPerformanceEppaObjective.getEppaid(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsPerformanceEppaObjective);
		} else {
			if (hrmsPerformanceEppaRepository.existsByIdAndActive(hrmsPerformanceEppaObjective.getEppaid(), 1)) {
				hrmsPerformanceEppaObjective.setActive(1);
				hrmsPerformanceEppaObjective.setApproved(0);
				hrmsPerformanceEppaObjective.setUnique_id(UUID.randomUUID());

				return ResponseEntity.status(HttpStatus.OK)
						.body(hrmsPerformanceEppaObjectiveRepository.saveAndFlush(hrmsPerformanceEppaObjective));
			} else {

				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsPerformanceEppaObjective);

			}

		}
	}

	@Override
	public ResponseEntity<PerformanceEppaObjectiveResponse> getPerformanceEppaObjectiveById(int id) {

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		PerformanceEppaObjectiveResponse performanceEppaObjectiveResponse = new PerformanceEppaObjectiveResponse();

		if (hrmsPerformanceEppaObjectiveRepository.existsByIdAndActive(id, 1)) {

			HrmsPerformanceEppaObjective dbm = hrmsPerformanceEppaObjectiveRepository.findById(id).get();

			performanceEppaObjectiveResponse.setActive(dbm.getActive());
			performanceEppaObjectiveResponse.setApproved(dbm.getApproved());
			performanceEppaObjectiveResponse.setCriteria(dbm.getCriteria());
			performanceEppaObjectiveResponse.setDescription(dbm.getDescription());

			if (dbm.getDateagreed() != null) {
				performanceEppaObjectiveResponse.setDateagreed(simpleDateFormat.format(dbm.getDateagreed()));
			}
			performanceEppaObjectiveResponse.setId(dbm.getId());
			performanceEppaObjectiveResponse.setEppaid(dbm.getEppaid());
			if (dbm.getEppaid() != 0 && hrmsPerformanceEppaRepository.existsByIdAndActive(dbm.getEppaid(), 1)) {
				performanceEppaObjectiveResponse
						.setPerformanceEppa(hrmsPerformanceEppaRepository.findByIdAndActive(dbm.getEppaid(), 1));
			}
			performanceEppaObjectiveResponse.setRevised(dbm.getRevised());
			performanceEppaObjectiveResponse.setTargets(dbm.getTargets());
			performanceEppaObjectiveResponse.setWeighting(dbm.getWeighting());

		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceEppaObjectiveResponse);
	}

	@Override
	public ResponseEntity<HrmsPerformanceEppaObjective> updatePerformanceEppaObjective(
			HrmsPerformanceEppaObjective hrmsPerformanceEppaObjective, int id) {
		if (hrmsPerformanceEppaObjectiveRepository.existsByIdAndActive(id, 1)) {
			if (hrmsPerformanceEppaRepository.existsByIdAndActive(hrmsPerformanceEppaObjective.getEppaid(), 1)) {
				hrmsPerformanceEppaObjective.setActive(1);
				hrmsPerformanceEppaObjective.setApproved(0);
				hrmsPerformanceEppaObjective.setDate_updated(LocalDateTime.now());
				if (hrmsPerformanceEppaObjectiveRepository.findById(id).get().getDate_created() != null) {
					hrmsPerformanceEppaObjective.setDate_created(
							hrmsPerformanceEppaObjectiveRepository.findById(id).get().getDate_created());
				}

				if (hrmsPerformanceEppaObjectiveRepository.findById(id).get().getUnique_id() != null) {
					hrmsPerformanceEppaObjective
							.setUnique_id(hrmsPerformanceEppaObjectiveRepository.findById(id).get().getUnique_id());
				}

				if (hrmsPerformanceEppaObjectiveRepository.findById(id).get().getRevised() != 0) {
					hrmsPerformanceEppaObjective
							.setRevised(hrmsPerformanceEppaObjectiveRepository.findById(id).get().getRevised());
				}

				return ResponseEntity.status(HttpStatus.OK)
						.body(hrmsPerformanceEppaObjectiveRepository.saveAndFlush(hrmsPerformanceEppaObjective));
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsPerformanceEppaObjective);
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deletePerformanceEppaObjective(int id) {
		if (hrmsPerformanceEppaObjectiveRepository.existsByIdAndActive(id, 1)) {
			HrmsPerformanceEppaObjective hrmsPerformanceEppaObjective = hrmsPerformanceEppaObjectiveRepository
					.findByIdAndActive(id, 1);
			hrmsPerformanceEppaObjective.setActive(0);
			hrmsPerformanceEppaObjective.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPerformanceEppaObjectiveRepository.saveAndFlush(hrmsPerformanceEppaObjective));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<PerformanceEppaObjectiveResponse>> getAllPerformanceEppaObjective() {

		List<PerformanceEppaObjectiveResponse> performanceEppaObjectiveResponselist = new ArrayList<>();
		List<HrmsPerformanceEppaObjective> dbms = hrmsPerformanceEppaObjectiveRepository.findByActive(1);

		for (HrmsPerformanceEppaObjective dbm : dbms) {

			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

			PerformanceEppaObjectiveResponse performanceEppaObjectiveResponse = new PerformanceEppaObjectiveResponse();

			performanceEppaObjectiveResponse.setActive(dbm.getActive());
			performanceEppaObjectiveResponse.setApproved(dbm.getApproved());
			performanceEppaObjectiveResponse.setCriteria(dbm.getCriteria());
			performanceEppaObjectiveResponse.setDescription(dbm.getDescription());

			if (dbm.getDateagreed() != null) {
				performanceEppaObjectiveResponse.setDateagreed(simpleDateFormat.format(dbm.getDateagreed()));
			}
			performanceEppaObjectiveResponse.setId(dbm.getId());
			performanceEppaObjectiveResponse.setEppaid(dbm.getEppaid());
			if (dbm.getEppaid() != 0 && hrmsPerformanceEppaRepository.existsByIdAndActive(dbm.getEppaid(), 1)) {
				performanceEppaObjectiveResponse
						.setPerformanceEppa(hrmsPerformanceEppaRepository.findByIdAndActive(dbm.getEppaid(), 1));
			}
			performanceEppaObjectiveResponse.setRevised(dbm.getRevised());
			performanceEppaObjectiveResponse.setTargets(dbm.getTargets());
			performanceEppaObjectiveResponse.setWeighting(dbm.getWeighting());

			performanceEppaObjectiveResponselist.add(performanceEppaObjectiveResponse);
		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceEppaObjectiveResponselist);
	}

	@Override
	public ResponseEntity<List<PerformanceEppaObjectiveResponse>> getAllPerformanceEppaObjectiveByEppaId(int eppaid) {

		List<PerformanceEppaObjectiveResponse> performanceEppaObjectiveResponselist = new ArrayList<>();
		List<HrmsPerformanceEppaObjective> dbms = hrmsPerformanceEppaObjectiveRepository.findByEppaidAndActive(eppaid,
				1);

		for (HrmsPerformanceEppaObjective dbm : dbms) {

			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

			PerformanceEppaObjectiveResponse performanceEppaObjectiveResponse = new PerformanceEppaObjectiveResponse();

			performanceEppaObjectiveResponse.setActive(dbm.getActive());
			performanceEppaObjectiveResponse.setApproved(dbm.getApproved());
			performanceEppaObjectiveResponse.setCriteria(dbm.getCriteria());
			performanceEppaObjectiveResponse.setDescription(dbm.getDescription());

			if (dbm.getDateagreed() != null) {
				performanceEppaObjectiveResponse.setDateagreed(simpleDateFormat.format(dbm.getDateagreed()));
			}
			performanceEppaObjectiveResponse.setId(dbm.getId());
			performanceEppaObjectiveResponse.setEppaid(dbm.getEppaid());
			if (dbm.getEppaid() != 0 && hrmsPerformanceEppaRepository.existsByIdAndActive(dbm.getEppaid(), 1)) {
				performanceEppaObjectiveResponse
						.setPerformanceEppa(hrmsPerformanceEppaRepository.findByIdAndActive(dbm.getEppaid(), 1));
			}
			performanceEppaObjectiveResponse.setRevised(dbm.getRevised());
			performanceEppaObjectiveResponse.setTargets(dbm.getTargets());
			performanceEppaObjectiveResponse.setWeighting(dbm.getWeighting());

			performanceEppaObjectiveResponselist.add(performanceEppaObjectiveResponse);
		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceEppaObjectiveResponselist);
	}

}
