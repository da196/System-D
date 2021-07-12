package com.Hrms.Perfomance.Service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Perfomance.DTO.StrategicPlanSummaryResponse;
import com.Hrms.Perfomance.DTO.StrategicPlanglobalResponse;

@Service
public interface StrategicPlanGlobalService {

	public ResponseEntity<StrategicPlanglobalResponse> getPerformanceStrategicPlanGlobalByIdPlanIdGoalIdAndObjectiveId(
			int planid, int goalid, int objectiveid);

	public ResponseEntity<StrategicPlanglobalResponse> getPerformanceStrategicPlanGlobalByIdPlanIdGoalIdObjectiveIdAndUnitId(
			int planid, int goalid, int objectiveid, int unitid);

	public ResponseEntity<StrategicPlanSummaryResponse> getPerformanceStrategicPlanSummaryByPlanId(int planid);

}
