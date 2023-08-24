package com.tyss.strongameapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tyss.strongameapp.entity.LiveModuleContent;
import com.tyss.strongameapp.entity.StreamedStudioContent;

public interface StreamedStudioContentRepo extends JpaRepository<StreamedStudioContent, Integer> {

	List<StreamedStudioContent> findAllByStreamedStudioContent(LiveModuleContent liveModuleContent);

}
