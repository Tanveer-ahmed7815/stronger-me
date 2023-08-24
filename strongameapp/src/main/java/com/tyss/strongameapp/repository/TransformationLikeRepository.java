package com.tyss.strongameapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tyss.strongameapp.entity.TransformationLikeDetails;

@Repository
public interface TransformationLikeRepository extends JpaRepository<TransformationLikeDetails, Integer>{

	@Query(value="SELECT sum(transformation_like) FROM transformation_like_details where transformation_id=?1",nativeQuery = true)
	Integer getTransformationLikeCount(int transformationId);
	
	@Query(value="select transformation_like from transformation_like_details where transformation_id=?1 and user_id=?2", nativeQuery = true)
	Boolean getFlag(int transformationId, Integer userId);

	@Query(value="select * from transformation_like_details where user_id=:userId and transformation_id=:transformId",nativeQuery = true)
	TransformationLikeDetails findTransLikeId(int userId, int transformId);

	@Modifying
	@Query(value="update transformation_like_details set transformation_like=:flag where transformation_id=:transformId and user_id=:userId",nativeQuery = true)
	void update(int userId, int transformId, boolean flag);
}
