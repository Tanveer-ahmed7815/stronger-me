package com.tyss.strongameapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tyss.strongameapp.entity.LiveModule;
import com.tyss.strongameapp.entity.SpecializationContent;

public interface SpecializationContentRepository extends JpaRepository<SpecializationContent, Integer>{

	List<SpecializationContent> findBySpecializationName(String specializationName);

	List<SpecializationContent> findAllByLiveModule(LiveModule fetchModule);

}
