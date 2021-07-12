package com.Hrms.Employee.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsLocationCity;

@Repository
public interface HrmsLocationCityRepository extends JpaRepository<HrmsLocationCity, Integer> {

	boolean existsByName(String name);

	List<HrmsLocationCity> findByCountryid(int countryid);

	List<HrmsLocationCity> findByActive(int i);

	List<HrmsLocationCity> findByCountryidAndActive(int countryid, int i);

	boolean existsByIdAndActive(int id, int i);

}
