package com.Hrms.Perfomance.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsOrginisationUnit;
import com.Hrms.Employee.Repository.HrmsOrginisationUnitRepository;
import com.Hrms.Perfomance.DTO.PerformanceObjectiveOutcomeActivityOutputResponsibleResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceObjectiveOutcomeActivityOutputResponsible;
import com.Hrms.Perfomance.Entity.HrmsPerformanceObjectiveOutcomeActivityOutputTarget;
import com.Hrms.Perfomance.Repository.HrmsPerformanceObjectiveOutcomeActivityOutputResponsibleRepository;
import com.Hrms.Perfomance.Repository.HrmsPerformanceObjectiveOutcomeActivityOutputTargetRepository;

@Service
public class HrmsPerformanceObjectiveOutcomeActivityOutputResponsibleServiceImpl
		implements HrmsPerformanceObjectiveOutcomeActivityOutputResponsibleService {

	@Autowired
	private HrmsPerformanceObjectiveOutcomeActivityOutputResponsibleRepository hrmsPerformanceObjectiveOutcomeActivityOutputResponsibleRepository;

	@Autowired
	private HrmsPerformanceObjectiveOutcomeActivityOutputTargetRepository hrmsPerformanceObjectiveOutcomeActivityOutputTargetRepository;

	@Autowired
	private HrmsOrginisationUnitRepository hrmsOrginisationUnitRepository;

	@Override
	public ResponseEntity<HrmsPerformanceObjectiveOutcomeActivityOutputResponsible> addPerformanceObjectiveOutcomeActivityOutputResponsible(
			HrmsPerformanceObjectiveOutcomeActivityOutputResponsible hrmsPerformanceObjectiveOutcomeActivityOutputResponsible) {
		if (hrmsPerformanceObjectiveOutcomeActivityOutputResponsibleRepository.existsByUnitidAndTargetidAndActive(
				hrmsPerformanceObjectiveOutcomeActivityOutputResponsible.getUnitid(),
				hrmsPerformanceObjectiveOutcomeActivityOutputResponsible.getTargetid(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
					.body(hrmsPerformanceObjectiveOutcomeActivityOutputResponsible);
		} else {
			if (hrmsPerformanceObjectiveOutcomeActivityOutputTargetRepository
					.existsByIdAndActive(hrmsPerformanceObjectiveOutcomeActivityOutputResponsible.getTargetid(), 1)
					&& hrmsOrginisationUnitRepository.existsByIdAndActive(
							hrmsPerformanceObjectiveOutcomeActivityOutputResponsible.getUnitid(), 1)) {
				hrmsPerformanceObjectiveOutcomeActivityOutputResponsible.setActive(1);
				hrmsPerformanceObjectiveOutcomeActivityOutputResponsible.setApproved(0);
				hrmsPerformanceObjectiveOutcomeActivityOutputResponsible.setUnique_id(UUID.randomUUID());

				return ResponseEntity.status(HttpStatus.OK)
						.body(hrmsPerformanceObjectiveOutcomeActivityOutputResponsibleRepository
								.saveAndFlush(hrmsPerformanceObjectiveOutcomeActivityOutputResponsible));
			} else {

				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
						.body(hrmsPerformanceObjectiveOutcomeActivityOutputResponsible);

			}

		}
	}

	@Override
	public ResponseEntity<PerformanceObjectiveOutcomeActivityOutputResponsibleResponse> getPerformanceObjectiveOutcomeActivityOutputResponsibleById(
			int id) {
		PerformanceObjectiveOutcomeActivityOutputResponsibleResponse performanceObjectiveOutcomeActivityOutputResponsibleResponse = new PerformanceObjectiveOutcomeActivityOutputResponsibleResponse();

		if (hrmsPerformanceObjectiveOutcomeActivityOutputResponsibleRepository.existsById(id)) {

			HrmsPerformanceObjectiveOutcomeActivityOutputResponsible dbm = hrmsPerformanceObjectiveOutcomeActivityOutputResponsibleRepository
					.findById(id).get();

			performanceObjectiveOutcomeActivityOutputResponsibleResponse.setActive(dbm.getActive());
			performanceObjectiveOutcomeActivityOutputResponsibleResponse.setApproved(dbm.getApproved());
			performanceObjectiveOutcomeActivityOutputResponsibleResponse.setId(dbm.getId());
			performanceObjectiveOutcomeActivityOutputResponsibleResponse.setTargetid(dbm.getTargetid());
			HrmsPerformanceObjectiveOutcomeActivityOutputTarget performanceObjectiveOutcomeActivityOutputTarget = new HrmsPerformanceObjectiveOutcomeActivityOutputTarget();
			if (dbm.getTargetid() != 0
					&& hrmsPerformanceObjectiveOutcomeActivityOutputTargetRepository.existsById(dbm.getTargetid())) {
				HrmsPerformanceObjectiveOutcomeActivityOutputTarget dbmzo = hrmsPerformanceObjectiveOutcomeActivityOutputTargetRepository
						.findById(dbm.getTargetid()).get();
				performanceObjectiveOutcomeActivityOutputTarget = dbmzo;
			}
			performanceObjectiveOutcomeActivityOutputResponsibleResponse
					.setPerformanceObjectiveOutcomeActivityOutputTarget(
							performanceObjectiveOutcomeActivityOutputTarget);
			performanceObjectiveOutcomeActivityOutputResponsibleResponse.setUnitid(dbm.getUnitid());

			if (dbm.getUnitid() != 0 && hrmsOrginisationUnitRepository.existsById(dbm.getUnitid())) {
				HrmsOrginisationUnit dbmz = hrmsOrginisationUnitRepository.findById(dbm.getUnitid()).get();

				performanceObjectiveOutcomeActivityOutputResponsibleResponse.setUnitName(dbmz.getName());

			}

		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceObjectiveOutcomeActivityOutputResponsibleResponse);
	}

	@Override
	public ResponseEntity<HrmsPerformanceObjectiveOutcomeActivityOutputResponsible> updatePerformanceObjectiveOutcomeActivityOutputResponsible(
			HrmsPerformanceObjectiveOutcomeActivityOutputResponsible hrmsPerformanceObjectiveOutcomeActivityOutputResponsible,
			int id) {
		if (hrmsPerformanceObjectiveOutcomeActivityOutputResponsibleRepository.existsByIdAndActive(id, 1)) {
			if (hrmsPerformanceObjectiveOutcomeActivityOutputTargetRepository
					.existsByIdAndActive(hrmsPerformanceObjectiveOutcomeActivityOutputResponsible.getTargetid(), 1)

					&& hrmsOrginisationUnitRepository.existsByIdAndActive(
							hrmsPerformanceObjectiveOutcomeActivityOutputResponsible.getUnitid(), 1)) {
				hrmsPerformanceObjectiveOutcomeActivityOutputResponsible.setActive(1);
				hrmsPerformanceObjectiveOutcomeActivityOutputResponsible.setApproved(0);
				hrmsPerformanceObjectiveOutcomeActivityOutputResponsible.setDate_updated(LocalDateTime.now());
				if (hrmsPerformanceObjectiveOutcomeActivityOutputResponsibleRepository.findById(id).get()
						.getDate_created() != null) {
					hrmsPerformanceObjectiveOutcomeActivityOutputResponsible
							.setDate_created(hrmsPerformanceObjectiveOutcomeActivityOutputTargetRepository.findById(id)
									.get().getDate_created());
				}

				if (hrmsPerformanceObjectiveOutcomeActivityOutputResponsibleRepository.findById(id).get()
						.getUnique_id() != null) {
					hrmsPerformanceObjectiveOutcomeActivityOutputResponsible
							.setUnique_id(hrmsPerformanceObjectiveOutcomeActivityOutputTargetRepository.findById(id)
									.get().getUnique_id());
				}

				return ResponseEntity.status(HttpStatus.OK)
						.body(hrmsPerformanceObjectiveOutcomeActivityOutputResponsibleRepository
								.saveAndFlush(hrmsPerformanceObjectiveOutcomeActivityOutputResponsible));
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
						.body(hrmsPerformanceObjectiveOutcomeActivityOutputResponsible);
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deletePerformanceObjectiveOutcomeActivityOutputResponsible(int id) {
		if (hrmsPerformanceObjectiveOutcomeActivityOutputResponsibleRepository.existsByIdAndActive(id, 1)) {
			HrmsPerformanceObjectiveOutcomeActivityOutputResponsible hrmsPerformanceObjectiveOutcomeActivityOutputResponsible = hrmsPerformanceObjectiveOutcomeActivityOutputResponsibleRepository
					.findByIdAndActive(id, 1);
			hrmsPerformanceObjectiveOutcomeActivityOutputResponsible.setActive(0);
			hrmsPerformanceObjectiveOutcomeActivityOutputResponsible.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPerformanceObjectiveOutcomeActivityOutputResponsibleRepository
							.saveAndFlush(hrmsPerformanceObjectiveOutcomeActivityOutputResponsible));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<PerformanceObjectiveOutcomeActivityOutputResponsibleResponse>> getAllPerformanceObjectiveOutcomeActivityOutputResponsible() {

		List<PerformanceObjectiveOutcomeActivityOutputResponsibleResponse> performanceObjectiveOutcomeActivityOutputResponsibleResponselist = new ArrayList<>();

		List<HrmsPerformanceObjectiveOutcomeActivityOutputResponsible> dbms = hrmsPerformanceObjectiveOutcomeActivityOutputResponsibleRepository
				.findByActive(1);

		for (HrmsPerformanceObjectiveOutcomeActivityOutputResponsible dbm : dbms) {
			PerformanceObjectiveOutcomeActivityOutputResponsibleResponse performanceObjectiveOutcomeActivityOutputResponsibleResponse = new PerformanceObjectiveOutcomeActivityOutputResponsibleResponse();

			performanceObjectiveOutcomeActivityOutputResponsibleResponse.setActive(dbm.getActive());
			performanceObjectiveOutcomeActivityOutputResponsibleResponse.setApproved(dbm.getApproved());
			performanceObjectiveOutcomeActivityOutputResponsibleResponse.setId(dbm.getId());
			performanceObjectiveOutcomeActivityOutputResponsibleResponse.setTargetid(dbm.getTargetid());
			HrmsPerformanceObjectiveOutcomeActivityOutputTarget performanceObjectiveOutcomeActivityOutputTarget = new HrmsPerformanceObjectiveOutcomeActivityOutputTarget();
			if (dbm.getTargetid() != 0
					&& hrmsPerformanceObjectiveOutcomeActivityOutputTargetRepository.existsById(dbm.getTargetid())) {
				HrmsPerformanceObjectiveOutcomeActivityOutputTarget dbmzo = hrmsPerformanceObjectiveOutcomeActivityOutputTargetRepository
						.findById(dbm.getTargetid()).get();
				performanceObjectiveOutcomeActivityOutputTarget = dbmzo;
			}
			performanceObjectiveOutcomeActivityOutputResponsibleResponse
					.setPerformanceObjectiveOutcomeActivityOutputTarget(
							performanceObjectiveOutcomeActivityOutputTarget);
			performanceObjectiveOutcomeActivityOutputResponsibleResponse.setUnitid(dbm.getUnitid());

			if (dbm.getUnitid() != 0 && hrmsOrginisationUnitRepository.existsById(dbm.getUnitid())) {
				HrmsOrginisationUnit dbmz = hrmsOrginisationUnitRepository.findById(dbm.getUnitid()).get();

				performanceObjectiveOutcomeActivityOutputResponsibleResponse.setUnitName(dbmz.getName());

			}

			performanceObjectiveOutcomeActivityOutputResponsibleResponselist
					.add(performanceObjectiveOutcomeActivityOutputResponsibleResponse);
		}

		return ResponseEntity.status(HttpStatus.OK)
				.body(performanceObjectiveOutcomeActivityOutputResponsibleResponselist);

	}

	@Override
	public ResponseEntity<List<PerformanceObjectiveOutcomeActivityOutputResponsibleResponse>> getAllPerformanceObjectiveOutcomeActivityOutputResponsibleByTargetid(
			int targetid) {
		List<PerformanceObjectiveOutcomeActivityOutputResponsibleResponse> performanceObjectiveOutcomeActivityOutputResponsibleResponselist = new ArrayList<>();

		List<HrmsPerformanceObjectiveOutcomeActivityOutputResponsible> dbms = hrmsPerformanceObjectiveOutcomeActivityOutputResponsibleRepository
				.findByTargetidAndActive(targetid, 1);

		for (HrmsPerformanceObjectiveOutcomeActivityOutputResponsible dbm : dbms) {
			PerformanceObjectiveOutcomeActivityOutputResponsibleResponse performanceObjectiveOutcomeActivityOutputResponsibleResponse = new PerformanceObjectiveOutcomeActivityOutputResponsibleResponse();

			performanceObjectiveOutcomeActivityOutputResponsibleResponse.setActive(dbm.getActive());
			performanceObjectiveOutcomeActivityOutputResponsibleResponse.setApproved(dbm.getApproved());
			performanceObjectiveOutcomeActivityOutputResponsibleResponse.setId(dbm.getId());
			performanceObjectiveOutcomeActivityOutputResponsibleResponse.setTargetid(dbm.getTargetid());
			HrmsPerformanceObjectiveOutcomeActivityOutputTarget performanceObjectiveOutcomeActivityOutputTarget = new HrmsPerformanceObjectiveOutcomeActivityOutputTarget();
			if (dbm.getTargetid() != 0
					&& hrmsPerformanceObjectiveOutcomeActivityOutputTargetRepository.existsById(dbm.getTargetid())) {
				HrmsPerformanceObjectiveOutcomeActivityOutputTarget dbmzo = hrmsPerformanceObjectiveOutcomeActivityOutputTargetRepository
						.findById(dbm.getTargetid()).get();
				performanceObjectiveOutcomeActivityOutputTarget = dbmzo;
			}
			performanceObjectiveOutcomeActivityOutputResponsibleResponse
					.setPerformanceObjectiveOutcomeActivityOutputTarget(
							performanceObjectiveOutcomeActivityOutputTarget);
			performanceObjectiveOutcomeActivityOutputResponsibleResponse.setUnitid(dbm.getUnitid());

			if (dbm.getUnitid() != 0 && hrmsOrginisationUnitRepository.existsById(dbm.getUnitid())) {
				HrmsOrginisationUnit dbmz = hrmsOrginisationUnitRepository.findById(dbm.getUnitid()).get();

				performanceObjectiveOutcomeActivityOutputResponsibleResponse.setUnitName(dbmz.getName());

			}

			performanceObjectiveOutcomeActivityOutputResponsibleResponselist
					.add(performanceObjectiveOutcomeActivityOutputResponsibleResponse);
		}

		return ResponseEntity.status(HttpStatus.OK)
				.body(performanceObjectiveOutcomeActivityOutputResponsibleResponselist);
	}

	@Override
	public ResponseEntity<List<PerformanceObjectiveOutcomeActivityOutputResponsibleResponse>> getAllPerformanceObjectiveOutcomeActivityOutputResponsibleByTargetIdAndUnitId(
			int targetid, int unitid) {
		List<PerformanceObjectiveOutcomeActivityOutputResponsibleResponse> performanceObjectiveOutcomeActivityOutputResponsibleResponselist = new ArrayList<>();

		List<HrmsPerformanceObjectiveOutcomeActivityOutputResponsible> dbms = hrmsPerformanceObjectiveOutcomeActivityOutputResponsibleRepository
				.findByTargetidAndUnitidAndActive(targetid, unitid, 1);

		for (HrmsPerformanceObjectiveOutcomeActivityOutputResponsible dbm : dbms) {
			PerformanceObjectiveOutcomeActivityOutputResponsibleResponse performanceObjectiveOutcomeActivityOutputResponsibleResponse = new PerformanceObjectiveOutcomeActivityOutputResponsibleResponse();

			performanceObjectiveOutcomeActivityOutputResponsibleResponse.setActive(dbm.getActive());
			performanceObjectiveOutcomeActivityOutputResponsibleResponse.setApproved(dbm.getApproved());
			performanceObjectiveOutcomeActivityOutputResponsibleResponse.setId(dbm.getId());
			performanceObjectiveOutcomeActivityOutputResponsibleResponse.setTargetid(dbm.getTargetid());
			HrmsPerformanceObjectiveOutcomeActivityOutputTarget performanceObjectiveOutcomeActivityOutputTarget = new HrmsPerformanceObjectiveOutcomeActivityOutputTarget();
			if (dbm.getTargetid() != 0
					&& hrmsPerformanceObjectiveOutcomeActivityOutputTargetRepository.existsById(dbm.getTargetid())) {
				HrmsPerformanceObjectiveOutcomeActivityOutputTarget dbmzo = hrmsPerformanceObjectiveOutcomeActivityOutputTargetRepository
						.findById(dbm.getTargetid()).get();
				performanceObjectiveOutcomeActivityOutputTarget = dbmzo;
			}
			performanceObjectiveOutcomeActivityOutputResponsibleResponse
					.setPerformanceObjectiveOutcomeActivityOutputTarget(
							performanceObjectiveOutcomeActivityOutputTarget);
			performanceObjectiveOutcomeActivityOutputResponsibleResponse.setUnitid(dbm.getUnitid());

			if (dbm.getUnitid() != 0 && hrmsOrginisationUnitRepository.existsById(dbm.getUnitid())) {
				HrmsOrginisationUnit dbmz = hrmsOrginisationUnitRepository.findById(dbm.getUnitid()).get();

				performanceObjectiveOutcomeActivityOutputResponsibleResponse.setUnitName(dbmz.getName());

			}

			performanceObjectiveOutcomeActivityOutputResponsibleResponselist
					.add(performanceObjectiveOutcomeActivityOutputResponsibleResponse);
		}

		return ResponseEntity.status(HttpStatus.OK)
				.body(performanceObjectiveOutcomeActivityOutputResponsibleResponselist);
	}

	@Override
	public ResponseEntity<List<PerformanceObjectiveOutcomeActivityOutputResponsibleResponse>> getAllPerformanceObjectiveOutcomeActivityOutputResponsibleByUnitid(
			int unitid) {
		List<PerformanceObjectiveOutcomeActivityOutputResponsibleResponse> performanceObjectiveOutcomeActivityOutputResponsibleResponselist = new ArrayList<>();

		List<HrmsPerformanceObjectiveOutcomeActivityOutputResponsible> dbms = hrmsPerformanceObjectiveOutcomeActivityOutputResponsibleRepository
				.findByUnitidAndActive(unitid, 1);

		for (HrmsPerformanceObjectiveOutcomeActivityOutputResponsible dbm : dbms) {
			PerformanceObjectiveOutcomeActivityOutputResponsibleResponse performanceObjectiveOutcomeActivityOutputResponsibleResponse = new PerformanceObjectiveOutcomeActivityOutputResponsibleResponse();

			performanceObjectiveOutcomeActivityOutputResponsibleResponse.setActive(dbm.getActive());
			performanceObjectiveOutcomeActivityOutputResponsibleResponse.setApproved(dbm.getApproved());
			performanceObjectiveOutcomeActivityOutputResponsibleResponse.setId(dbm.getId());
			performanceObjectiveOutcomeActivityOutputResponsibleResponse.setTargetid(dbm.getTargetid());
			HrmsPerformanceObjectiveOutcomeActivityOutputTarget performanceObjectiveOutcomeActivityOutputTarget = new HrmsPerformanceObjectiveOutcomeActivityOutputTarget();
			if (dbm.getTargetid() != 0
					&& hrmsPerformanceObjectiveOutcomeActivityOutputTargetRepository.existsById(dbm.getTargetid())) {
				HrmsPerformanceObjectiveOutcomeActivityOutputTarget dbmzo = hrmsPerformanceObjectiveOutcomeActivityOutputTargetRepository
						.findById(dbm.getTargetid()).get();
				performanceObjectiveOutcomeActivityOutputTarget = dbmzo;
			}
			performanceObjectiveOutcomeActivityOutputResponsibleResponse
					.setPerformanceObjectiveOutcomeActivityOutputTarget(
							performanceObjectiveOutcomeActivityOutputTarget);
			performanceObjectiveOutcomeActivityOutputResponsibleResponse.setUnitid(dbm.getUnitid());

			if (dbm.getUnitid() != 0 && hrmsOrginisationUnitRepository.existsById(dbm.getUnitid())) {
				HrmsOrginisationUnit dbmz = hrmsOrginisationUnitRepository.findById(dbm.getUnitid()).get();

				performanceObjectiveOutcomeActivityOutputResponsibleResponse.setUnitName(dbmz.getName());

			}

			performanceObjectiveOutcomeActivityOutputResponsibleResponselist
					.add(performanceObjectiveOutcomeActivityOutputResponsibleResponse);
		}

		return ResponseEntity.status(HttpStatus.OK)
				.body(performanceObjectiveOutcomeActivityOutputResponsibleResponselist);
	}

}
