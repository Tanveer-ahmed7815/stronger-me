package com.tyss.strongameapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tyss.strongameapp.entity.PrivacyPolicyFiles;

/**
 * PrivacyPolicyRepository is used to perform CRUD operations for file.
 * @author Sushma Guttal
 *
 */
@Repository
public interface PrivacyPolicyRepository extends JpaRepository<PrivacyPolicyFiles, String> {

}
