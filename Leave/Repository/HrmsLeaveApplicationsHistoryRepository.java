package com.Hrms.Leave.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Leave.Entity.HrmsLeaveApplicationsHistory;

@Repository
public interface HrmsLeaveApplicationsHistoryRepository extends JpaRepository<HrmsLeaveApplicationsHistory, Integer> {

}
