package com.Hrms.Perfomance.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Perfomance.DTO.PerformanceGoalResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceGoal;
import com.Hrms.Perfomance.Entity.HrmsPerformancePlan;
import com.Hrms.Perfomance.Repository.HrmsPerformanceGoalRepository;
import com.Hrms.Perfomance.Repository.HrmsPerformancePlanRepository;

@Service
public class HrmsPerformanceGoalServiceImpl implements HrmsPerformanceGoalService {

	@Autowired
	private HrmsPerformanceGoalRepository hrmsPerformanceGoalRepository;

	@Autowired
	private HrmsPerformancePlanRepository hrmsPerformancePlanRepository;

	@Override
	public ResponseEntity<HrmsPerformanceGoal> addPerformanceGoal(HrmsPerformanceGoal hrmsPerformanceGoal) {
		if (hrmsPerformanceGoalRepository.existsByNameAndActive(hrmsPerformanceGoal.getName(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsPerformanceGoal);
		} else {
			if (hrmsPerformancePlanRepository.existsByIdAndActive(hrmsPerformanceGoal.getPlanid(), 1)) {
				hrmsPerformanceGoal.setActive(1);
				hrmsPerformanceGoal.setApproved(0);
				hrmsPerformanceGoal.setUnique_id(UUID.randomUUID());

				return ResponseEntity.status(HttpStatus.OK)
						.body(hrmsPerformanceGoalRepository.saveAndFlush(hrmsPerformanceGoal));
			} else {

				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsPerformanceGoal);

			}

		}
	}

	@Override
	public ResponseEntity<PerformanceGoalResponse> getPerformanceGoalById(int id) {

		PerformanceGoalResponse performanceGoalResponse = new PerformanceGoalResponse();
		if (hrmsPerformanceGoalRepository.existsById(id)) {
			HrmsPerformanceGoal hrmsPerformanceGoal = hrmsPerformanceGoalRepository.findById(id).get();
			performanceGoalResponse.setActive(hrmsPerformanceGoal.getActive());
			performanceGoalResponse.setApproved(hrmsPerformanceGoal.getApproved());
			performanceGoalResponse.setDescription(hrmsPerformanceGoal.getDescription());
			performanceGoalResponse.setId(hrmsPerformanceGoal.getId());
			performanceGoalResponse.setName(hrmsPerformanceGoal.getName());
			performanceGoalResponse.setPlanid(hrmsPerformanceGoal.getPlanid());
			if (hrmsPerformanceGoal.getPlanid() != 0
					&& hrmsPerformancePlanRepository.existsById(hrmsPerformanceGoal.getPlanid())) {
				HrmsPerformancePlan hrmsPerformancePlan = hrmsPerformancePlanRepository
						.findById(hrmsPerformanceGoal.getPlanid()).get();

				performanceGoalResponse.setPerformancePlan(hrmsPerformancePlan);
			}

		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceGoalResponse);
	}

	@Override
	public ResponseEntity<HrmsPerformanceGoal> updatePerformanceGoal(HrmsPerformanceGoal hrmsPerformanceGoal, int id) {
		if (hrmsPerformanceGoalRepository.existsByIdAndActive(id, 1)) {
			hrmsPerformanceGoal.setActive(1);
			hrmsPerformanceGoal.setApproved(0);
			hrmsPerformanceGoal.setDate_updated(LocalDateTime.now());
			if (hrmsPerformancePlanRepository.findById(id).get().getDate_created() != null) {
				hrmsPerformanceGoal.setDate_created(hrmsPerformancePlanRepository.findById(id).get().getDate_created());
			}

			if (hrmsPerformancePlanRepository.findById(id).get().getUnique_id() != null) {
				hrmsPerformanceGoal.setUnique_id(hrmsPerformancePlanRepository.findById(id).get().getUnique_id());
			}

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPerformanceGoalRepository.saveAndFlush(hrmsPerformanceGoal));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deletePerformanceGoal(int id) {
		if (hrmsPerformanceGoalRepository.existsByIdAndActive(id, 1)) {
			HrmsPerformanceGoal hrmsPerformanceGoal = hrmsPerformanceGoalRepository.findByIdAndActive(id, 1);
			hrmsPerformanceGoal.setActive(0);
			hrmsPerformanceGoal.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPerformanceGoalRepository.saveAndFlush(hrmsPerformanceGoal));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<PerformanceGoalResponse>> getAllPerformanceGoal() {

		List<PerformanceGoalResponse> performanceGoalResponselist = new ArrayList<>();
		List<HrmsPerformanceGoal> dbms = hrmsPerformanceGoalRepository.findByActive(1);

		for (HrmsPerformanceGoal dbm : dbms) {
			PerformanceGoalResponse performanceGoalResponse = new PerformanceGoalResponse();

			HrmsPerformanceGoal hrmsPerformanceGoal = hrmsPerformanceGoalRepository.findById(dbm.getId()).get();
			performanceGoalResponse.setActive(hrmsPerformanceGoal.getActive());
			performanceGoalResponse.setApproved(hrmsPerformanceGoal.getApproved());
			performanceGoalResponse.setDescription(hrmsPerformanceGoal.getDescription());
			performanceGoalResponse.setId(hrmsPerformanceGoal.getId());
			performanceGoalResponse.setName(hrmsPerformanceGoal.getName());
			performanceGoalResponse.setPlanid(hrmsPerformanceGoal.getPlanid());
			if (hrmsPerformanceGoal.getPlanid() != 0
					&& hrmsPerformancePlanRepository.existsById(hrmsPerformanceGoal.getPlanid())) {
				HrmsPerformancePlan hrmsPerformancePlan = hrmsPerformancePlanRepository
						.findById(hrmsPerformanceGoal.getPlanid()).get();

				performanceGoalResponse.setPerformancePlan(hrmsPerformancePlan);
			}

			performanceGoalResponselist.add(performanceGoalResponse);
		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceGoalResponselist);
	}

	@Override
	public ResponseEntity<List<PerformanceGoalResponse>> getAllPerformanceGoalByPlanId(int planid) {
		List<PerformanceGoalResponse> performanceGoalResponselist = new ArrayList<>();
		List<HrmsPerformanceGoal> dbms = hrmsPerformanceGoalRepository.findByPlanidAndActive(planid, 1);

		for (HrmsPerformanceGoal dbm : dbms) {
			PerformanceGoalResponse performanceGoalResponse = new PerformanceGoalResponse();

			HrmsPerformanceGoal hrmsPerformanceGoal = hrmsPerformanceGoalRepository.findById(dbm.getId()).get();
			performanceGoalResponse.setActive(hrmsPerformanceGoal.getActive());
			performanceGoalResponse.setApproved(hrmsPerformanceGoal.getApproved());
			performanceGoalResponse.setDescription(hrmsPerformanceGoal.getDescription());
			performanceGoalResponse.setId(hrmsPerformanceGoal.getId());
			performanceGoalResponse.setName(hrmsPerformanceGoal.getName());
			performanceGoalResponse.setPlanid(hrmsPerformanceGoal.getPlanid());
			if (hrmsPerformanceGoal.getPlanid() != 0
					&& hrmsPerformancePlanRepository.existsById(hrmsPerformanceGoal.getPlanid())) {
				HrmsPerformancePlan hrmsPerformancePlan = hrmsPerformancePlanRepository
						.findById(hrmsPerformanceGoal.getPlanid()).get();

				performanceGoalResponse.setPerformancePlan(hrmsPerformancePlan);
			}

			performanceGoalResponselist.add(performanceGoalResponse);
		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceGoalResponselist);
	}

}
