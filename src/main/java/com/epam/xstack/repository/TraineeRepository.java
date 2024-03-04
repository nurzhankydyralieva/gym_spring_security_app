package com.epam.xstack.repository;

import com.epam.xstack.models.entity.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TraineeRepository extends JpaRepository<Trainee, UUID> {
}
