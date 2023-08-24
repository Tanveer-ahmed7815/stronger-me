package com.tyss.strongameapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tyss.strongameapp.entity.SpecializationMaster;

@Repository
public interface SpecializationMasterRepository extends JpaRepository<SpecializationMaster, Integer>{

}
