package com.tyss.strongameapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tyss.strongameapp.entity.ModuleContent;
import com.tyss.strongameapp.entity.StrongermeModule;

public interface ModuleContentRepository extends JpaRepository<ModuleContent, Integer>  {

	List<ModuleContent> findByContentName(String contentName);

	List<ModuleContent> findAllByStrongermeModule(StrongermeModule fetchModule);
}
