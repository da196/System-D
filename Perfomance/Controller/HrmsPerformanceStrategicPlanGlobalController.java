package com.Hrms.Perfomance.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hrms.Perfomance.DTO.StrategicPlanSummaryResponse;
import com.Hrms.Perfomance.DTO.StrategicPlanglobalResponse;
import com.Hrms.Perfomance.Service.StrategicPlanGlobalService;

@RestController
@RequestMapping("v1/performanceStrategicPlanGlobal")
public class HrmsPerformanceStrategicPlanGlobalController {

	@Autowired
	private StrategicPlanGlobalService strategicPlanGlobalService;

	@GetMapping(value = "/getPerformanceStrategicPlanGlobalByIdPlanIdGoalIdAndObjectiveId/{planid}/{goalid}/{objectiveid}/")
	public ResponseEntity<StrategicPlanglobalResponse> getPerformanceStrategicPlanGlobalByIdPlanIdGoalIdAndObjectiveId(
			@PathVariable("planid") int planid, @PathVariable("goalid") int goalid,
			@PathVariable("objectiveid") int objectiveid) {

		return strategicPlanGlobalService.getPerformanceStrategicPlanGlobalByIdPlanIdGoalIdAndObjectiveId(planid,
				goalid, objectiveid);

	}

	@GetMapping(value = "/getPerformanceStrategicPlanGlobalByIdPlanIdGoalIdObjectiveIdAndUnitId/{planid}/{goalid}/{objectiveid}/{unitid}")
	public ResponseEntity<StrategicPlanglobalResponse> getPerformanceStrategicPlanGlobalByIdPlanIdGoalIdObjectiveIdAndUnitId(
			@PathVariable("planid") int planid, @PathVariable("goalid") int goalid,
			@PathVariable("objectiveid") int objectiveid, @PathVariable("unitid") int unitid) {

		return strategicPlanGlobalService.getPerformanceStrategicPlanGlobalByIdPlanIdGoalIdObjectiveIdAndUnitId(planid,
				goalid, objectiveid, unitid);

	}

	@GetMapping(value = "/getPerformanceStrategicPlanSummaryByPlanId/{planid}")
	public ResponseEntity<StrategicPlanSummaryResponse> getPerformanceStrategicPlanSummaryByPlanId(
			@PathVariable("planid") int planid) {

		return strategicPlanGlobalService.getPerformanceStrategicPlanSummaryByPlanId(planid);

	}

}
