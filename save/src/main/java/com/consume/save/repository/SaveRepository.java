package com.consume.save.repository;

import com.consume.save.commons.entity.OperationEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SaveRepository extends JpaRepository<OperationEntity, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE OperationEntity o " +
            "SET o.estatus = :estatus " +
            "WHERE o.id = :id")
    int updateEstatusById(
            @Param("id") Long id,
            @Param("estatus") String estatus
    );
}
