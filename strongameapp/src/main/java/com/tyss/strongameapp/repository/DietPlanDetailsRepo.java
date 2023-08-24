package com.tyss.strongameapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tyss.strongameapp.entity.DietRecipeDetails;

public interface DietPlanDetailsRepo extends JpaRepository<DietRecipeDetails, Integer> {

	@Query("select d from DietRecipeDetails d where d.dietName=?1")
	DietRecipeDetails getDietByName(String dietName);

	@Query(value="select * FROM diet_recipe_details order by diet_name desc",nativeQuery = true)
	List<DietRecipeDetails> filterDietByNameDesc();
	
	@Query(value="select * FROM diet_recipe_details order by diet_name",nativeQuery = true)
	List<DietRecipeDetails> filterDietByName();

	@Query("SELECT d FROM DietRecipeDetails d WHERE d.dietName LIKE %?1%")
	List<DietRecipeDetails> serach(String keyword);

}
