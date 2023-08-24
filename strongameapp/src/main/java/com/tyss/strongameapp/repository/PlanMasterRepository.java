package com.tyss.strongameapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tyss.strongameapp.entity.PlanMaster;

@Repository
public interface PlanMasterRepository extends JpaRepository<PlanMaster, Integer> {

}
