package com.raspberry.raspberry.repository;

import com.raspberry.raspberry.model.DHTValue;
import com.raspberry.raspberry.model.Pins;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DHTValueRepository extends JpaRepository<DHTValue, Pins> {
    DHTValue findByPin(Pins pin);
}
