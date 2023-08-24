package com.tyss.strongameapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tyss.strongameapp.entity.StrongermeModule;

public interface ModuleRepository extends JpaRepository<StrongermeModule, Integer> {

	StrongermeModule findByModuleName(String moduleName);


}
