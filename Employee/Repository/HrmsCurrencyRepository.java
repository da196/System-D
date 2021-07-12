package com.Hrms.Employee.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsCurrency;

@Repository
public interface HrmsCurrencyRepository extends JpaRepository<HrmsCurrency, Integer> {

	boolean existsByIdAndActive(int currencyid, int i);

	List<HrmsCurrency> findByActive(int i);

}
