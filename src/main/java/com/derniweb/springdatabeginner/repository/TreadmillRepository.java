package com.derniweb.springdatabeginner.repository;

import com.derniweb.springdatabeginner.entity.Treadmill;
import org.springframework.data.repository.CrudRepository;

public interface TreadmillRepository  extends CrudRepository<Treadmill, String> {
}
