package com.tyss.strongameapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tyss.strongameapp.entity.SessionNotificationDetails;

public interface SessionNotificationRepository extends JpaRepository<SessionNotificationDetails, Integer> {

}
