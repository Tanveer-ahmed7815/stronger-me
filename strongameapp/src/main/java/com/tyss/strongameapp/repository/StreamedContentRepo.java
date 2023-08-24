package com.tyss.strongameapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tyss.strongameapp.entity.ModuleContent;
import com.tyss.strongameapp.entity.StreamedContent;

public interface StreamedContentRepo extends JpaRepository<StreamedContent, Integer> {

	@Query(value="select * from streamed_content where content_id=:contentId",nativeQuery = true)
	List<StreamedContent> countBycontentId(int contentId);

	List<StreamedContent> findAllByStreamedContent(ModuleContent content);

}
