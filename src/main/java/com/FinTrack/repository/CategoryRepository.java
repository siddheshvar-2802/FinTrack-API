package com.FinTrack.repository;

import com.FinTrack.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(value = "SELECT * FROM {h-schema}category ;", nativeQuery = true)
    Optional<Category> findCategoryByCategoryName(String category);
}
