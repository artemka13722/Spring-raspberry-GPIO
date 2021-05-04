package com.raspberry.raspberry.repository;

import com.raspberry.raspberry.model.GPIOSettings;
import com.raspberry.raspberry.model.Pins;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GPIOSettingsRepository extends JpaRepository<GPIOSettings, Pins> {
}
