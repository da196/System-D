package com.Hrms.Perfomance.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsOrginisationUnit;
import com.Hrms.Employee.Repository.HrmsOrginisationUnitRepository;
import com.Hrms.Perfomance.DTO.PerformanceGoalSummary;
import com.Hrms.Perfomance.DTO.PerformanceObjectiveOutcomeActivityGlobalResponse;
import com.Hrms.Perfomance.DTO.PerformanceObjectiveOutcomeActivityOutputGlobalResponse;
import com.Hrms.Perfomance.DTO.PerformanceObjectiveOutcomeActivityOutputResponsibleResponse;
import com.Hrms.Perfomance.DTO.PerformanceObjectiveOutcomeActivityOutputTargetGlobalResponse;
import com.Hrms.Perfomance.DTO.PerformanceObjectiveOutcomeGlobalResponse;
import com.Hrms.Perfomance.DTO.PerformanceObjectiveSummary;
import com.Hrms.Perfomance.DTO.StrategicPlanSummaryResponse;
import com.Hrms.Perfomance.DTO.StrategicPlanglobalResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceGoal;
import com.Hrms.Perfomance.Entity.HrmsPerformanceObjective;
import com.Hrms.Perfomance.Entity.HrmsPerformanceObjectiveOutcome;
import com.Hrms.Perfomance.Entity.HrmsPerformanceObjectiveOutcomeActivity;
import com.Hrms.Perfomance.Entity.HrmsPerformanceObjectiveOutcomeActivityOutput;
import com.Hrms.Perfomance.Entity.HrmsPerformanceObjectiveOutcomeActivityOutputResponsible;
import com.Hrms.Perfomance.Entity.HrmsPerformanceObjectiveOutcomeActivityOutputTarget;
import com.Hrms.Perfomance.Entity.HrmsPerformanceObjectiveTarget;
import com.Hrms.Perfomance.Entity.HrmsPerformancePlan;
import com.Hrms.Perfomance.Repository.HrmsPerformanceGoalRepository;
import com.Hrms.Perfomance.Repository.HrmsPerformanceObjectiveOutcomeActivityOutputRepository;
import com.Hrms.Perfomance.Repository.HrmsPerformanceObjectiveOutcomeActivityOutputResponsibleRepository;
import com.Hrms.Perfomance.Repository.HrmsPerformanceObjectiveOutcomeActivityOutputTargetRepository;
import com.Hrms.Perfomance.Repository.HrmsPerformanceObjectiveOutcomeActivityRepository;
import com.Hrms.Perfomance.Repository.HrmsPerformanceObjectiveOutcomeRepository;
import com.Hrms.Perfomance.Repository.HrmsPerformanceObjectiveRepository;
import com.Hrms.Perfomance.Repository.HrmsPerformanceObjectiveTargetRepository;
import com.Hrms.Perfomance.Repository.HrmsPerformancePlanRepository;

@Service
public class StrategicPlanGlobalServiceImpl implements StrategicPlanGlobalService {

	@Autowired
	private HrmsPerformanceGoalRepository hrmsPerformanceGoalRepository;

	@Autowired
	private HrmsPerformancePlanRepository hrmsPerformancePlanRepository;

	@Autowired
	private HrmsPerformanceObjectiveOutcomeActivityOutputResponsibleRepository hrmsPerformanceObjectiveOutcomeActivityOutputResponsibleRepository;

	@Autowired
	private HrmsPerformanceObjectiveOutcomeActivityOutputTargetRepository hrmsPerformanceObjectiveOutcomeActivityOutputTargetRepository;

	@Autowired
	private HrmsPerformanceObjectiveOutcomeActivityOutputRepository hrmsPerformanceObjectiveOutcomeActivityOutputRepository;
	@Autowired
	private HrmsOrginisationUnitRepository hrmsOrginisationUnitRepository;

	@Autowired
	private HrmsPerformanceObjectiveOutcomeActivityRepository hrmsPerformanceObjectiveOutcomeActivityRepository;

	@Autowired
	private HrmsPerformanceObjectiveOutcomeRepository hrmsPerformanceObjectiveOutcomeRepository;

	@Autowired
	private HrmsPerformanceObjectiveRepository hrmsPerformanceObjectiveRepository;

	@Autowired
	private HrmsPerformanceObjectiveTargetRepository hrmsPerformanceObjectiveTargetRepository;

	@Override
	public ResponseEntity<StrategicPlanglobalResponse> getPerformanceStrategicPlanGlobalByIdPlanIdGoalIdAndObjectiveId(
			int planid, int goalid, int objectiveid) {
		StrategicPlanglobalResponse StrategicPlanglobalResponse = new StrategicPlanglobalResponse();

		if (hrmsPerformancePlanRepository.existsById(planid) && hrmsPerformanceGoalRepository.existsById(goalid)
				&& hrmsPerformanceObjectiveRepository.existsById(objectiveid)) {

			StrategicPlanglobalResponse.setPlanId(planid);
			StrategicPlanglobalResponse.setPlanName(hrmsPerformancePlanRepository.findById(planid).get().getName());
			StrategicPlanglobalResponse
					.setFinancialYear(hrmsPerformancePlanRepository.findById(planid).get().getFinancialyear());
			StrategicPlanglobalResponse.setGoalid(goalid);

			HrmsPerformanceGoal hrmsPerformanceGoal = hrmsPerformanceGoalRepository.findByIdAndPlanid(goalid, planid);
			StrategicPlanglobalResponse.setGoalName(hrmsPerformanceGoal.getName());
			StrategicPlanglobalResponse.setObjectiveId(objectiveid);

			HrmsPerformanceObjective hrmsPerformanceObjective = hrmsPerformanceObjectiveRepository
					.findByGoalidAndId(goalid, objectiveid);

			StrategicPlanglobalResponse.setObjectiveName(hrmsPerformanceObjective.getDescription());

			List<PerformanceObjectiveOutcomeGlobalResponse> performanceObjectiveOutcomeGlobalResponselist = new ArrayList<>();

			List<HrmsPerformanceObjectiveOutcome> dbms1 = hrmsPerformanceObjectiveOutcomeRepository
					.findByObjectiveidAndActive(objectiveid, 1);
			for (HrmsPerformanceObjectiveOutcome dbm1 : dbms1) {
				PerformanceObjectiveOutcomeGlobalResponse PerformanceObjectiveOutcomeGlobalResponse = new PerformanceObjectiveOutcomeGlobalResponse();

				PerformanceObjectiveOutcomeGlobalResponse.setActive(dbm1.getActive());
				PerformanceObjectiveOutcomeGlobalResponse.setApproved(dbm1.getApproved());
				PerformanceObjectiveOutcomeGlobalResponse.setDescription(dbm1.getDescription());
				PerformanceObjectiveOutcomeGlobalResponse.setId(dbm1.getId());
				PerformanceObjectiveOutcomeGlobalResponse.setObjectiveid(dbm1.getObjectiveid());

				List<PerformanceObjectiveOutcomeActivityGlobalResponse> performanceObjectiveOutcomeActivityGlobalResponselist = new ArrayList<>();

				List<HrmsPerformanceObjectiveOutcomeActivity> dbms2 = hrmsPerformanceObjectiveOutcomeActivityRepository
						.findByOutcomeidAndActive(dbm1.getId(), 1);

				for (HrmsPerformanceObjectiveOutcomeActivity dbm2 : dbms2) {
					PerformanceObjectiveOutcomeActivityGlobalResponse performanceObjectiveOutcomeActivityGlobalResponse = new PerformanceObjectiveOutcomeActivityGlobalResponse();

					performanceObjectiveOutcomeActivityGlobalResponse.setActive(dbm2.getActive());
					performanceObjectiveOutcomeActivityGlobalResponse.setApproved(dbm2.getApproved());
					performanceObjectiveOutcomeActivityGlobalResponse.setDescription(dbm2.getDescription());
					performanceObjectiveOutcomeActivityGlobalResponse.setId(dbm2.getId());
					performanceObjectiveOutcomeActivityGlobalResponse.setOutcomeid(dbm2.getOutcomeid());

					List<PerformanceObjectiveOutcomeActivityOutputGlobalResponse> performanceObjectiveOutcomeActivityOutputGlobalResponselist = new ArrayList<>();

					List<HrmsPerformanceObjectiveOutcomeActivityOutput> dbms3 = hrmsPerformanceObjectiveOutcomeActivityOutputRepository
							.findByActivityidAndActive(dbm2.getId(), 1);

					for (HrmsPerformanceObjectiveOutcomeActivityOutput dbm3 : dbms3) {
						PerformanceObjectiveOutcomeActivityOutputGlobalResponse performanceObjectiveOutcomeActivityOutputGlobalResponse = new PerformanceObjectiveOutcomeActivityOutputGlobalResponse();

						performanceObjectiveOutcomeActivityOutputGlobalResponse.setActive(dbm3.getActive());
						performanceObjectiveOutcomeActivityOutputGlobalResponse.setActivityid(dbm3.getActivityid());
						performanceObjectiveOutcomeActivityOutputGlobalResponse.setApproved(dbm3.getApproved());
						performanceObjectiveOutcomeActivityOutputGlobalResponse.setDescription(dbm3.getDescription());
						performanceObjectiveOutcomeActivityOutputGlobalResponse.setId(dbm3.getId());

						List<PerformanceObjectiveOutcomeActivityOutputTargetGlobalResponse> performanceObjectiveOutcomeActivityOutputTargetGlobalResponselist = new ArrayList<>();

						List<HrmsPerformanceObjectiveOutcomeActivityOutputTarget> dbms4 = hrmsPerformanceObjectiveOutcomeActivityOutputTargetRepository
								.findByOutputidAndActive(dbm3.getId(), 1);

						for (HrmsPerformanceObjectiveOutcomeActivityOutputTarget dbm4 : dbms4) {
							PerformanceObjectiveOutcomeActivityOutputTargetGlobalResponse performanceObjectiveOutcomeActivityOutputTargetGlobalResponse = new PerformanceObjectiveOutcomeActivityOutputTargetGlobalResponse();

							performanceObjectiveOutcomeActivityOutputTargetGlobalResponse.setActive(dbm4.getActive());
							performanceObjectiveOutcomeActivityOutputTargetGlobalResponse
									.setApproved(dbm4.getApproved());
							performanceObjectiveOutcomeActivityOutputTargetGlobalResponse
									.setDescription(dbm4.getDescription());
							performanceObjectiveOutcomeActivityOutputTargetGlobalResponse.setId(dbm4.getId());
							performanceObjectiveOutcomeActivityOutputTargetGlobalResponse
									.setKeyperformanceindicator(dbm4.getKeyperformanceindicator());
							performanceObjectiveOutcomeActivityOutputTargetGlobalResponse
									.setOutputid(dbm4.getOutputid());
							performanceObjectiveOutcomeActivityOutputTargetGlobalResponse.setTarget(dbm4.getTarget());
							performanceObjectiveOutcomeActivityOutputTargetGlobalResponse
									.setTimeline(dbm4.getTimeline());

							List<PerformanceObjectiveOutcomeActivityOutputResponsibleResponse> performanceObjectiveOutcomeActivityOutputResponsibleResponselist = new ArrayList<>();

							List<HrmsPerformanceObjectiveOutcomeActivityOutputResponsible> dbms5 = hrmsPerformanceObjectiveOutcomeActivityOutputResponsibleRepository
									.findByTargetidAndActive(dbm4.getId(), 1);

							for (HrmsPerformanceObjectiveOutcomeActivityOutputResponsible dbm5 : dbms5) {

								PerformanceObjectiveOutcomeActivityOutputResponsibleResponse performanceObjectiveOutcomeActivityOutputResponsibleResponse = new PerformanceObjectiveOutcomeActivityOutputResponsibleResponse();

								performanceObjectiveOutcomeActivityOutputResponsibleResponse
										.setActive(dbm5.getActive());
								performanceObjectiveOutcomeActivityOutputResponsibleResponse
										.setApproved(dbm5.getApproved());
								performanceObjectiveOutcomeActivityOutputResponsibleResponse.setId(dbm5.getId());
								performanceObjectiveOutcomeActivityOutputResponsibleResponse
										.setTargetid(dbm5.getTargetid());
								performanceObjectiveOutcomeActivityOutputResponsibleResponse
										.setUnitid(dbm5.getUnitid());
								if (dbm5.getUnitid() != 0
										&& hrmsOrginisationUnitRepository.existsById(dbm5.getUnitid())) {
									HrmsOrginisationUnit dbmz = hrmsOrginisationUnitRepository
											.findById(dbm5.getUnitid()).get();

									performanceObjectiveOutcomeActivityOutputResponsibleResponse
											.setUnitName(dbmz.getName());

								}

								performanceObjectiveOutcomeActivityOutputResponsibleResponse
										.setPerformanceObjectiveOutcomeActivityOutputTarget(null);

								performanceObjectiveOutcomeActivityOutputResponsibleResponselist
										.add(performanceObjectiveOutcomeActivityOutputResponsibleResponse);

							}

							performanceObjectiveOutcomeActivityOutputTargetGlobalResponse
									.setPerformanceObjectiveOutcomeActivityOutputResponsibleResponselist(
											performanceObjectiveOutcomeActivityOutputResponsibleResponselist);

							performanceObjectiveOutcomeActivityOutputTargetGlobalResponselist
									.add(performanceObjectiveOutcomeActivityOutputTargetGlobalResponse);

						}

						performanceObjectiveOutcomeActivityOutputGlobalResponse
								.setPerformanceObjectiveOutcomeActivityOutputTargetGlobalResponselist(
										performanceObjectiveOutcomeActivityOutputTargetGlobalResponselist);

						performanceObjectiveOutcomeActivityOutputGlobalResponselist
								.add(performanceObjectiveOutcomeActivityOutputGlobalResponse);
					}

					performanceObjectiveOutcomeActivityGlobalResponse
							.setPerformanceObjectiveOutcomeActivityOutputGlobalResponselist(
									performanceObjectiveOutcomeActivityOutputGlobalResponselist);

					performanceObjectiveOutcomeActivityGlobalResponselist
							.add(performanceObjectiveOutcomeActivityGlobalResponse);
				}

				PerformanceObjectiveOutcomeGlobalResponse.setPerformanceObjectiveOutcomeActivityGlobalResponselist(
						performanceObjectiveOutcomeActivityGlobalResponselist);

				performanceObjectiveOutcomeGlobalResponselist.add(PerformanceObjectiveOutcomeGlobalResponse);
			}

			StrategicPlanglobalResponse
					.setPerformanceObjectiveOutcomeGlobalResponselist(performanceObjectiveOutcomeGlobalResponselist);

		}

		return ResponseEntity.status(HttpStatus.OK).body(StrategicPlanglobalResponse);
	}

	@Override
	public ResponseEntity<StrategicPlanglobalResponse> getPerformanceStrategicPlanGlobalByIdPlanIdGoalIdObjectiveIdAndUnitId(
			int planid, int goalid, int objectiveid, int unitid) {

		StrategicPlanglobalResponse StrategicPlanglobalResponse = new StrategicPlanglobalResponse();

		if (hrmsPerformancePlanRepository.existsById(planid) && hrmsPerformanceGoalRepository.existsById(goalid)
				&& hrmsPerformanceObjectiveRepository.existsById(objectiveid)) {

			StrategicPlanglobalResponse.setPlanId(planid);
			StrategicPlanglobalResponse.setPlanName(hrmsPerformancePlanRepository.findById(planid).get().getName());
			StrategicPlanglobalResponse
					.setFinancialYear(hrmsPerformancePlanRepository.findById(planid).get().getFinancialyear());
			StrategicPlanglobalResponse.setGoalid(goalid);

			HrmsPerformanceGoal hrmsPerformanceGoal = hrmsPerformanceGoalRepository.findByIdAndPlanid(goalid, planid);
			StrategicPlanglobalResponse.setGoalName(hrmsPerformanceGoal.getName());
			StrategicPlanglobalResponse.setObjectiveId(objectiveid);

			HrmsPerformanceObjective hrmsPerformanceObjective = hrmsPerformanceObjectiveRepository
					.findByGoalidAndId(goalid, objectiveid);

			StrategicPlanglobalResponse.setObjectiveName(hrmsPerformanceObjective.getDescription());

			List<PerformanceObjectiveOutcomeGlobalResponse> performanceObjectiveOutcomeGlobalResponselist = new ArrayList<>();

			List<HrmsPerformanceObjectiveOutcome> dbms1 = hrmsPerformanceObjectiveOutcomeRepository
					.findByObjectiveidAndActive(objectiveid, 1);
			for (HrmsPerformanceObjectiveOutcome dbm1 : dbms1) {
				PerformanceObjectiveOutcomeGlobalResponse PerformanceObjectiveOutcomeGlobalResponse = new PerformanceObjectiveOutcomeGlobalResponse();

				PerformanceObjectiveOutcomeGlobalResponse.setActive(dbm1.getActive());
				PerformanceObjectiveOutcomeGlobalResponse.setApproved(dbm1.getApproved());
				PerformanceObjectiveOutcomeGlobalResponse.setDescription(dbm1.getDescription());
				PerformanceObjectiveOutcomeGlobalResponse.setId(dbm1.getId());
				PerformanceObjectiveOutcomeGlobalResponse.setObjectiveid(dbm1.getObjectiveid());

				List<PerformanceObjectiveOutcomeActivityGlobalResponse> performanceObjectiveOutcomeActivityGlobalResponselist = new ArrayList<>();

				List<HrmsPerformanceObjectiveOutcomeActivity> dbms2 = hrmsPerformanceObjectiveOutcomeActivityRepository
						.findByOutcomeidAndActive(dbm1.getId(), 1);

				for (HrmsPerformanceObjectiveOutcomeActivity dbm2 : dbms2) {
					PerformanceObjectiveOutcomeActivityGlobalResponse performanceObjectiveOutcomeActivityGlobalResponse = new PerformanceObjectiveOutcomeActivityGlobalResponse();

					performanceObjectiveOutcomeActivityGlobalResponse.setActive(dbm2.getActive());
					performanceObjectiveOutcomeActivityGlobalResponse.setApproved(dbm2.getApproved());
					performanceObjectiveOutcomeActivityGlobalResponse.setDescription(dbm2.getDescription());
					performanceObjectiveOutcomeActivityGlobalResponse.setId(dbm2.getId());
					performanceObjectiveOutcomeActivityGlobalResponse.setOutcomeid(dbm2.getOutcomeid());

					List<PerformanceObjectiveOutcomeActivityOutputGlobalResponse> performanceObjectiveOutcomeActivityOutputGlobalResponselist = new ArrayList<>();

					List<HrmsPerformanceObjectiveOutcomeActivityOutput> dbms3 = hrmsPerformanceObjectiveOutcomeActivityOutputRepository
							.findByActivityidAndActive(dbm2.getId(), 1);

					for (HrmsPerformanceObjectiveOutcomeActivityOutput dbm3 : dbms3) {
						PerformanceObjectiveOutcomeActivityOutputGlobalResponse performanceObjectiveOutcomeActivityOutputGlobalResponse = new PerformanceObjectiveOutcomeActivityOutputGlobalResponse();

						performanceObjectiveOutcomeActivityOutputGlobalResponse.setActive(dbm3.getActive());
						performanceObjectiveOutcomeActivityOutputGlobalResponse.setActivityid(dbm3.getActivityid());
						performanceObjectiveOutcomeActivityOutputGlobalResponse.setApproved(dbm3.getApproved());
						performanceObjectiveOutcomeActivityOutputGlobalResponse.setDescription(dbm3.getDescription());
						performanceObjectiveOutcomeActivityOutputGlobalResponse.setId(dbm3.getId());

						List<PerformanceObjectiveOutcomeActivityOutputTargetGlobalResponse> performanceObjectiveOutcomeActivityOutputTargetGlobalResponselist = new ArrayList<>();

						List<HrmsPerformanceObjectiveOutcomeActivityOutputTarget> dbms4 = hrmsPerformanceObjectiveOutcomeActivityOutputTargetRepository
								.findByOutputidAndActive(dbm3.getId(), 1);

						for (HrmsPerformanceObjectiveOutcomeActivityOutputTarget dbm4 : dbms4) {
							PerformanceObjectiveOutcomeActivityOutputTargetGlobalResponse performanceObjectiveOutcomeActivityOutputTargetGlobalResponse = new PerformanceObjectiveOutcomeActivityOutputTargetGlobalResponse();

							performanceObjectiveOutcomeActivityOutputTargetGlobalResponse.setActive(dbm4.getActive());
							performanceObjectiveOutcomeActivityOutputTargetGlobalResponse
									.setApproved(dbm4.getApproved());
							performanceObjectiveOutcomeActivityOutputTargetGlobalResponse
									.setDescription(dbm4.getDescription());
							performanceObjectiveOutcomeActivityOutputTargetGlobalResponse.setId(dbm4.getId());
							performanceObjectiveOutcomeActivityOutputTargetGlobalResponse
									.setKeyperformanceindicator(dbm4.getKeyperformanceindicator());
							performanceObjectiveOutcomeActivityOutputTargetGlobalResponse
									.setOutputid(dbm4.getOutputid());
							performanceObjectiveOutcomeActivityOutputTargetGlobalResponse.setTarget(dbm4.getTarget());
							performanceObjectiveOutcomeActivityOutputTargetGlobalResponse
									.setTimeline(dbm4.getTimeline());

							List<PerformanceObjectiveOutcomeActivityOutputResponsibleResponse> performanceObjectiveOutcomeActivityOutputResponsibleResponselist = new ArrayList<>();

							List<HrmsPerformanceObjectiveOutcomeActivityOutputResponsible> dbms5 = hrmsPerformanceObjectiveOutcomeActivityOutputResponsibleRepository
									.findByTargetidAndActive(dbm4.getId(), 1);

							for (HrmsPerformanceObjectiveOutcomeActivityOutputResponsible dbm5 : dbms5) {

								PerformanceObjectiveOutcomeActivityOutputResponsibleResponse performanceObjectiveOutcomeActivityOutputResponsibleResponse = new PerformanceObjectiveOutcomeActivityOutputResponsibleResponse();

								performanceObjectiveOutcomeActivityOutputResponsibleResponse
										.setActive(dbm5.getActive());
								performanceObjectiveOutcomeActivityOutputResponsibleResponse
										.setApproved(dbm5.getApproved());
								performanceObjectiveOutcomeActivityOutputResponsibleResponse.setId(dbm5.getId());
								performanceObjectiveOutcomeActivityOutputResponsibleResponse
										.setTargetid(dbm5.getTargetid());
								performanceObjectiveOutcomeActivityOutputResponsibleResponse
										.setUnitid(dbm5.getUnitid());
								if (dbm5.getUnitid() != 0
										&& hrmsOrginisationUnitRepository.existsById(dbm5.getUnitid())) {
									HrmsOrginisationUnit dbmz = hrmsOrginisationUnitRepository
											.findById(dbm5.getUnitid()).get();

									performanceObjectiveOutcomeActivityOutputResponsibleResponse
											.setUnitName(dbmz.getName());

								}

								performanceObjectiveOutcomeActivityOutputResponsibleResponse
										.setPerformanceObjectiveOutcomeActivityOutputTarget(null);

								performanceObjectiveOutcomeActivityOutputResponsibleResponselist
										.add(performanceObjectiveOutcomeActivityOutputResponsibleResponse);

							}

							performanceObjectiveOutcomeActivityOutputTargetGlobalResponse
									.setPerformanceObjectiveOutcomeActivityOutputResponsibleResponselist(
											performanceObjectiveOutcomeActivityOutputResponsibleResponselist);

							performanceObjectiveOutcomeActivityOutputTargetGlobalResponselist
									.add(performanceObjectiveOutcomeActivityOutputTargetGlobalResponse);

						}

						performanceObjectiveOutcomeActivityOutputGlobalResponse
								.setPerformanceObjectiveOutcomeActivityOutputTargetGlobalResponselist(
										performanceObjectiveOutcomeActivityOutputTargetGlobalResponselist);

						performanceObjectiveOutcomeActivityOutputGlobalResponselist
								.add(performanceObjectiveOutcomeActivityOutputGlobalResponse);
					}

					performanceObjectiveOutcomeActivityGlobalResponse
							.setPerformanceObjectiveOutcomeActivityOutputGlobalResponselist(
									performanceObjectiveOutcomeActivityOutputGlobalResponselist);

					performanceObjectiveOutcomeActivityGlobalResponselist
							.add(performanceObjectiveOutcomeActivityGlobalResponse);
				}

				PerformanceObjectiveOutcomeGlobalResponse.setPerformanceObjectiveOutcomeActivityGlobalResponselist(
						performanceObjectiveOutcomeActivityGlobalResponselist);

				performanceObjectiveOutcomeGlobalResponselist.add(PerformanceObjectiveOutcomeGlobalResponse);
			}

			StrategicPlanglobalResponse
					.setPerformanceObjectiveOutcomeGlobalResponselist(performanceObjectiveOutcomeGlobalResponselist);

		}

		return ResponseEntity.status(HttpStatus.OK).body(StrategicPlanglobalResponse);
	}

	@Override
	public ResponseEntity<StrategicPlanSummaryResponse> getPerformanceStrategicPlanSummaryByPlanId(int planid) {

		StrategicPlanSummaryResponse strategicPlanSummaryResponse = new StrategicPlanSummaryResponse();

		if (hrmsPerformancePlanRepository.existsByIdAndActive(planid, 1)
				&& hrmsPerformanceGoalRepository.existsByPlanidAndActive(planid, 1)

		) {
			HrmsPerformancePlan dbm = hrmsPerformancePlanRepository.findByIdAndActive(planid, 1);

			strategicPlanSummaryResponse.setPlanid(planid);
			strategicPlanSummaryResponse.setPerformancePlan(dbm);

			List<PerformanceGoalSummary> performanceGoallist = new ArrayList<>();

			List<HrmsPerformanceGoal> dbms1 = hrmsPerformanceGoalRepository.findByPlanidAndActive(planid, 1);

			for (HrmsPerformanceGoal dbm1 : dbms1) {
				PerformanceGoalSummary performanceGoalSummary = new PerformanceGoalSummary();

				performanceGoalSummary.setGoalid(dbm1.getId());
				performanceGoalSummary.setPerformanceGoal(dbm1);

				List<PerformanceObjectiveSummary> performanceObjectiveSummarylist = new ArrayList<>();
				if (dbm1.getId() != 0 && hrmsPerformanceObjectiveRepository.existsByGoalid(dbm1.getId())) {
					List<HrmsPerformanceObjective> dbms2 = hrmsPerformanceObjectiveRepository
							.findByGoalidAndActive(dbm1.getId(), 1);

					for (HrmsPerformanceObjective dbm2 : dbms2) {

						PerformanceObjectiveSummary performanceObjectiveSummary = new PerformanceObjectiveSummary();

						performanceObjectiveSummary.setObjectiveid(dbm2.getId());

						performanceObjectiveSummary.setPerformanceObjective(dbm2);

						List<HrmsPerformanceObjectiveTarget> targetlist = new ArrayList<>();
						if (dbm2.getId() != 0
								&& hrmsPerformanceObjectiveTargetRepository.existsByObjectiveid(dbm2.getId())) {
							List<HrmsPerformanceObjectiveTarget> dbms3 = hrmsPerformanceObjectiveTargetRepository
									.findByObjectiveidAndActive(dbm2.getId(), 1);

							for (HrmsPerformanceObjectiveTarget dbm3 : dbms3) {

								if (dbm3 != null) {
									targetlist.add(dbm3);
								}

							}

						}

						performanceObjectiveSummary.setTargetlist(targetlist);

						performanceObjectiveSummarylist.add(performanceObjectiveSummary);
					}
				}
				performanceGoalSummary.setPerformanceObjectiveSummarylist(performanceObjectiveSummarylist);

				performanceGoallist.add(performanceGoalSummary);

			}

			strategicPlanSummaryResponse.setPerformanceGoallist(performanceGoallist);

		}

		return ResponseEntity.status(HttpStatus.OK).body(strategicPlanSummaryResponse);
	}

}
