package com.raspberry.raspberry.repository;

import com.raspberry.raspberry.model.MotionSensor;
import com.raspberry.raspberry.model.Pins;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotionSensorRepository extends JpaRepository<MotionSensor, Pins> {
    MotionSensor findByPin(Pins pin);
}
