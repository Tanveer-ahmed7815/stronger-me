package com.tyss.strongameapp.repository;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tyss.strongameapp.dto.RecentUserLayer;
import com.tyss.strongameapp.entity.OrderInformation;
import com.tyss.strongameapp.entity.PlanInformation;
import com.tyss.strongameapp.entity.UserInformation;

public interface UserInformationRepository extends JpaRepository<UserInformation, Integer> {

	// @Query("SELECT new
	// com.tyss.strongameapp.dto.UserInfoDto(u.userId,u.name,u.dateOFBirth,u.email,u.mobileNo,u.height,u.weight,u.gender,u.photo)
	// FROM UserInformation u")
	// public List<UserInfoDto> getUserDetails();

	@Query("SELECT u FROM UserInformation u where email=?1")
	public List<UserInformation> fetchUserById(String email);

	@Query("SELECT u FROM UserInformation u where name=?1")
	public List<UserInformation> fetchUserByName(String name);

	@Query("SELECT u FROM UserInformation u where mobileNo=?1")
	public List<UserInformation> fetchUserByNumber(long mobileNo);

	@Transactional
	@Modifying
	@Query(value = "update user_information u set u.height=:height, u.weight=:weight, u.gender=:gender where u.user_id=:userId", nativeQuery = true)
	public void onboarding(int userId, String gender, double height, double weight);

	@Query(value = "select * from user_information", nativeQuery = true)
	public List<UserInformation> userList();

	@Query(value = "select * from user_information order by coins", nativeQuery = true)
	public List<UserInformation> leaderBoard();

	@Query(value = "select * from user_information left outer join rewards_details on user_information.reward_id=rewards_details.reward_id order by rewards_details.reward_coins desc", nativeQuery = true)
	public List<UserInformation> lead();

	@Query(value = "select * from order_information where user_id=:userId", nativeQuery = true)
	public List<OrderInformation> getOrder(@Param("userId") int userId);

	@Query(value = "select * from plan_information where user_id=:userId", nativeQuery = true)
	public List<PlanInformation> getPlan(@Param("userId") int userId);

	@Query(value = "SELECT * FROM user_information where user_id=:userId", nativeQuery = true)
	public UserInformation findUserById(int userId);

	@Transactional
	@Modifying
	@Query(value = "update user_information set date_of_birth=:dateOfBirth, email=:email, gender=:gender, height=:height,name=:name, password=:password,referal_code=:referalCode, weight=:weight, photo=:photo where user_id=:userId", nativeQuery = true)
	public int update(@Param("dateOfBirth") Date dateOfBirth, @Param("email") String email,
			@Param("gender") String gender, @Param("height") double height, @Param("name") String name,
			@Param("password") String password, @Param("referalCode") String referalCode,
			@Param("weight") double weight, @Param("userId") int userId, @Param("photo") String photo);

	@Query("select U from UserInformation U where email=?1 or mobileNo=?1")
	public UserInformation findByUsername(String email);

	@Query(value = "select * from user_information  where email=?1", nativeQuery = true)
	public UserInformation getuserId(String username);

	@Query(value = "select * from user_information where mobile_no=?1", nativeQuery = true)
	public UserInformation getUserByPhone(long mobileNo);

	@Query(value = "select * from user_information where mobile_no=?1 and password=?2", nativeQuery = true)
	public UserInformation getUserByPhonePassword(long number, String password);

	@Query(value = "select * from user_information where mobile_no=?1 and user_id!=?2", nativeQuery = true)
	public UserInformation getUserByPhoneUserId(long phone, int i);

	@Query(value = "select * from user_information order by user_id desc", nativeQuery = true)
	public List<UserInformation> getUserDescend();

	public UserInformation findByReferalCode(String friendReference);

	public int countByReferenceCount(boolean b);

	@Query(value = "select u.user_id  userId, u.name , u.photo FROM user_information u inner join user_package up on u.user_id=up.user_id inner join package_details p on up.package_id=p.package_id", nativeQuery = true)
	public List<RecentUserLayer> getAllSubscribedUsers();

	List<UserInformation> findByJwtTokenAndDeviceId(String jwtToken, String deviceId);

}
