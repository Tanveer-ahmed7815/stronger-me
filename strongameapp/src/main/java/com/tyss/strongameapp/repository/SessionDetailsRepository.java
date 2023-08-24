package com.tyss.strongameapp.repository;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.tyss.strongameapp.entity.SessionDetails;

public interface SessionDetailsRepository extends JpaRepository<SessionDetails, Integer> {

	@Modifying
	@Query(value = "update session_details set session_date=:sessionDate, session_duration=:sessionDuration, session_link=:sessionLink, session_time=:sessionTime,"
			+ " session_type=:sessionType, slots_available=:slotsAvailable, coach_for_session_id=:coachForSessionId where session_id=:sessionId", nativeQuery = true)
	void updateSession(int sessionId, String sessionLink, String sessionType, Date sessionDate, Time sessionTime,
			double sessionDuration, double slotsAvailable, int coachForSessionId);

	@Query(value = "Select * from session_details where session_id=?1", nativeQuery = true)
	SessionDetails findSessionById(int sessionId);

	@Query(value = "select s.session_id, s.session_link, s.session_date, s.session_time, s.session_duration, s.slots_available from session_details s join user_session us on s.session_id=us.session_id where us.user_id=:userId and us.session_id=:sessionId", nativeQuery = true)
	SessionDetails isUserSessionMapped(int userId, int sessionId);

	@Modifying
	@Transactional
	@Query(value = "update session_details set slots_available=slots_available-1 where session_id=:sessionId", nativeQuery = true)
	void updateSlots(int sessionId);

	@Query(value = "select * from session_details where session_type=:specializationType and session_date>=:date", nativeQuery = true)
	List<SessionDetails> getAllSessions(String specializationType, Date date);

	@Query(value = "select * from session_details where session_date=:date order by session_time desc", nativeQuery = true)
	List<SessionDetails> getTodaySession(java.sql.Date date);

}
