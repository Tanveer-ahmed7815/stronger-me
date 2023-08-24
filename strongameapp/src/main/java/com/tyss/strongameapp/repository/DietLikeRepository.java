package com.tyss.strongameapp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.tyss.strongameapp.entity.DietRecipeLike;

public interface DietLikeRepository extends JpaRepository<DietRecipeLike, Integer> {

	@Query(value="SELECT sum(user_like) FROM diet_recipe_like where diet_id=:dietId" ,nativeQuery=true)
	Integer getDietLikeCount(int dietId);

	
	@Query(value="SELECT user_like FROM diet_recipe_like where diet_id=:dietId and user_id=:userId",nativeQuery = true)
	Boolean getFlag(int dietId, int userId);

	
    @Query(value="select * from diet_recipe_like where user_id=:userId and diet_id=:dietRecipeId",nativeQuery = true)
	DietRecipeLike findDietLike(int userId, int dietRecipeId);


    @Modifying
    @Query(value="update diet_recipe_like set user_like=:flag where diet_id=:dietRecipeId and user_id=:userId",nativeQuery = true)
	void updateDietLike(int userId, int dietRecipeId, boolean flag);

}
