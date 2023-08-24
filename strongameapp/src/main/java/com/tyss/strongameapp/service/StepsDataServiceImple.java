package com.tyss.strongameapp.service;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tyss.strongameapp.dto.UserStepsStatsDto;
import com.tyss.strongameapp.entity.RewardDetails;
import com.tyss.strongameapp.entity.UserInformation;
import com.tyss.strongameapp.entity.UserStepsStats;
import com.tyss.strongameapp.repository.UserInformationRepository;
/**
 * StepsDataServiceImple is implemented to save and update user steps.
 * @author Sushma Guttal
 *
 */
@Service
public class StepsDataServiceImple implements StepsDataService {

	/**
	 * This field is used to invoke persistence layer method.
	 */
	@Autowired
	private UserInformationRepository userRepo;

	
	/**
	 * This method is implemented to save and update user steps.
	 * @param data
	 * @return UserStepsStatsDto
	 */
	@Override
	public UserStepsStatsDto saveSteps(UserStepsStatsDto data) {
		if(data!=null) {
			int userId=data.getUserId();
			UserInformation user= userRepo.findUserById(userId);
			UserStepsStats stepsEntity=user.getSteps();
			RewardDetails reward1= user.getReward();
			Date date=new Date(stepsEntity.getDay().getTime());
			if(data.getDay().compareTo(date)==0) {
				int previousSteps= stepsEntity.getCurrentSteps();
				int currentSteps= data.getCurrentSteps();
				double coins=0;
				double coinsEarned=(double)data.getCurrentSteps()/2000;
				int balanceSteps=currentSteps-previousSteps;
				if(currentSteps<=10000) {
					coins=(double)balanceSteps/2000;
					double coinsTwo = Double.parseDouble(String.format("%.2f", coins));
					double finalCoin=reward1.getRewardCoins()+coinsTwo;
					double restrictedFinalCoin=Double.parseDouble(String.format("%.2f", finalCoin));
					reward1.setRewardCoins(restrictedFinalCoin);
					stepsEntity.setCoinsEarned(Double.parseDouble(String.format("%.2f", coinsEarned)));
				}else {
					int previousStepsTwo=stepsEntity.getCurrentSteps();
					if(previousStepsTwo<=10000) {
						int	balancedSteps=10000-previousStepsTwo;
						double coinsTwo=(double)balancedSteps/2000;
						double coinsThree=Double.parseDouble(String.format("%.2f",coinsTwo));
						double finalCoin=reward1.getRewardCoins()+coinsThree;
						double restrictedFinalCoin=Double.parseDouble(String.format("%.2f", finalCoin));
						reward1.setRewardCoins(restrictedFinalCoin);
					}else {
						reward1.setRewardCoins(reward1.getRewardCoins());
						stepsEntity.setCoinsEarned(5);
					}
				}
				stepsEntity.setTargetSteps(10000);
				reward1.setNoOfSteps(reward1.getNoOfSteps()+balanceSteps);
				user.setReward(reward1);
				BeanUtils.copyProperties(data,stepsEntity);
				user.setSteps(stepsEntity);
				userRepo.save(user);
			}else {
				double coinsTwo=0;
				if(data.getCurrentSteps()<=10000) {
					double coins=(double)data.getCurrentSteps()/2000;
					reward1.setNoOfSteps(reward1.getNoOfSteps()+data.getCurrentSteps());
					coinsTwo = Double.parseDouble(String.format("%.2f", coins));
					double finalCoin=reward1.getRewardCoins()+coinsTwo;
					double restrictedfinalCoin=Double.parseDouble(String.format("%.2f", finalCoin));
					reward1.setRewardCoins(restrictedfinalCoin);
				}else {
					double finalCoin=reward1.getRewardCoins()+5;
					double restrictedfinalCoin=Double.parseDouble(String.format("%.2f", finalCoin));
					reward1.setRewardCoins(restrictedfinalCoin);
					reward1.setNoOfSteps(reward1.getNoOfSteps()+data.getCurrentSteps());
					stepsEntity.setCoinsEarned(5);
				}
				user.setReward(reward1);
				BeanUtils.copyProperties(data,stepsEntity);
				stepsEntity.setTargetSteps(10000);
				stepsEntity.setCoinsEarned(coinsTwo);
				user.setSteps(stepsEntity);
				userRepo.save(user);
			}

		}
		return data;
	}//End of saveSteps method.
}//End of StepsDataServiceImple class.
