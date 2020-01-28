package com.repostats.ghbackend.repository;

import com.repostats.ghbackend.entity.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchRepository extends JpaRepository<Search, Long> {
    List<Search> findByOwner_IdAndSearchedUsernameStartsWithIgnoreCase(Long ownerId, String query);
    boolean existsByOwner_IdAndSearchedUsernameIgnoreCase(Long ownerId, String query);
}
