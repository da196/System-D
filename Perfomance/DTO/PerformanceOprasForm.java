package com.Hrms.Perfomance.DTO;

import java.util.List;

import org.springframework.stereotype.Component;

import com.Hrms.Perfomance.Entity.HrmsPerformanceEppa;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEppaObjective;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEppaObjectiveRevised;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEppaReviewAnnual;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEppaReviewMidYear;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PerformanceOprasForm {
	private int financialYearId;
	private String financialYear;
	private int employeeid;
	private String employeeName;
	private int gender;
	private String dateOfBirth;
	private String dateOfFirstAppointment;
	private String dateOfPresentAppointment;
	private String dutyPost;
	private String substantivePost;
	private String salaryScale;
	private Double durationserverUnderSupervisor;
	private String drectorateOrUnit;
	private String TermsOfEmployment;// permanent or Contract
	private String dutyStation;
	private String reviewPeriod;

	private HrmsPerformanceEppa performanceEppa;

	// performance planning and agreement

	private List<HrmsPerformanceEppaObjective> performanceEppaObjectiveagreedlist;

	// mid year review

	private List<HrmsPerformanceEppaReviewMidYear> performanceEppaReviewMidYearlist;

	// revised objectives

	private List<HrmsPerformanceEppaObjectiveRevised> performanceEppaObjectiveRevisedlist;

	// Annual performance review

	private List<HrmsPerformanceEppaReviewAnnual> performanceEppaReviewAnnuallist;

	// General Attribute And Behavior Around Work
	private List<PerformanceEabFactorOprasResponse> performanceEabFactorOprasResponselist;

	private Double sumQualityAtributeApraisee;
	private Double averageQualityAtributeApraisee;

	private Double sumQualityAtributeSupervisor;

	private Double averageQualityAtributeSupervisor;

	private Double sumQualityAtributeAgreed;

	private Double averageQualityAtributeAgreed;

	// Overall Performance(Annual performance review + General Attribute And
	// Behavior Around Work )
	private Double overallPerformance;

}
