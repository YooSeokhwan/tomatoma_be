package com.tomatoma.repository;

import com.tomatoma.entity.FoodPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodPlaceRepository extends JpaRepository<FoodPlace, Long> {

    @Query("SELECT fp FROM FoodPlace fp WHERE fp.trend_food_id = :trendFoodId")
    List<FoodPlace> findByTrendFoodId(@Param("trendFoodId") Long trendFoodId);

    @Query("SELECT fp FROM FoodPlace fp WHERE fp.google_place_id = :googlePlaceId")
    Optional<FoodPlace> findByGooglePlaceId(@Param("googlePlaceId") String googlePlaceId);

    @Query("SELECT fp FROM FoodPlace fp WHERE fp.trend_food_id = :trendFoodId ORDER BY fp.rating DESC NULLS LAST")
    List<FoodPlace> findByTrendFoodIdOrderByRating(@Param("trendFoodId") Long trendFoodId);

    @Query("SELECT fp FROM FoodPlace fp WHERE fp.trend_food_id = :trendFoodId " +
           "ORDER BY SQRT(POWER(fp.latitude - :latitude, 2) + POWER(fp.longitude - :longitude, 2)) ASC")
    List<FoodPlace> findNearestPlaces(@Param("trendFoodId") Long trendFoodId,
                                       @Param("latitude") Double latitude,
                                       @Param("longitude") Double longitude);

}
