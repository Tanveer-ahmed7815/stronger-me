package com.tyss.strongameapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tyss.strongameapp.entity.TopTenTrend;

public interface TopTenTrendRepo  extends JpaRepository<TopTenTrend, Integer>{

	@Query(value="select content_id from top_ten_trend",nativeQuery = true)
	List<Integer> findAllContentId();

}
