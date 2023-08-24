package com.tyss.strongameapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tyss.strongameapp.entity.StudioBannerInformation;

@Repository
public interface StudioBannerInformationRepository extends JpaRepository<StudioBannerInformation, Integer>{

}
