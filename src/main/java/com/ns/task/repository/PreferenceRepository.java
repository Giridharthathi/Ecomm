package com.ns.task.repository;

import com.ns.task.entity.UserPreferenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreferenceRepository extends JpaRepository<UserPreferenceEntity,Integer> {
}
