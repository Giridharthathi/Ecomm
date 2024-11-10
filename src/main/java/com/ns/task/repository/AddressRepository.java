package com.ns.task.repository;

import com.ns.task.entity.AddressEntity;
import com.ns.task.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Integer> {
    Optional<AddressEntity> findByAddressid(int addressId);
    @Query(value = "SELECT * FROM address WHERE userid_fk = ?", nativeQuery = true)
    List<AddressEntity> findByUserid_Fk(int userId);
}
