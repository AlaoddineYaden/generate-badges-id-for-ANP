package com.example.demo.badge;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BadgeRepository extends JpaRepository<Badge,Long> {

//   @Query("SELECT b FROM Badge b where b.cnie = ?1")
   Optional<Badge> findFirstByCnie(String cnie);

}
