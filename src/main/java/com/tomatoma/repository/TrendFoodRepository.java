package com.tomatoma.repository;

import com.tomatoma.entity.TrendFood;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrendFoodRepository extends JpaRepository<TrendFood, Long> {

    Optional<TrendFood> findByName(String name);

    @Query("SELECT tf FROM TrendFood tf WHERE tf.category = :category ORDER BY tf.search_frequency DESC")
    Page<TrendFood> findByCategory(@Param("category") String category, Pageable pageable);

    @Query("SELECT tf FROM TrendFood tf ORDER BY tf.search_frequency DESC")
    Page<TrendFood> findAllByOrderBySearchFrequencyDesc(Pageable pageable);

    @Query("SELECT tf FROM TrendFood tf WHERE tf.category = :category ORDER BY tf.search_frequency DESC")
    List<TrendFood> findByCategoryList(@Param("category") String category);

    @Query("SELECT tf FROM TrendFood tf WHERE tf.name LIKE %:keyword% OR tf.category LIKE %:keyword% ORDER BY tf.search_frequency DESC")
    Page<TrendFood> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT tf FROM TrendFood tf ORDER BY tf.search_frequency DESC")
    List<TrendFood> findTopTrending(Pageable pageable);

}
