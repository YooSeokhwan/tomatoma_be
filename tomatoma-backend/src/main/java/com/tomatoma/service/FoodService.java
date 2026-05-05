package com.tomatoma.service;

import com.tomatoma.dto.TrendFoodDTO;
import com.tomatoma.dto.FoodPlaceDTO;
import com.tomatoma.entity.TrendFood;
import com.tomatoma.entity.FoodPlace;
import com.tomatoma.entity.Category;
import com.tomatoma.repository.TrendFoodRepository;
import com.tomatoma.repository.FoodPlaceRepository;
import com.tomatoma.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodService {

    private static final Logger log = LoggerFactory.getLogger(FoodService.class);

    private final TrendFoodRepository trendFoodRepository;
    private final FoodPlaceRepository foodPlaceRepository;
    private final CategoryRepository categoryRepository;

    public FoodService(TrendFoodRepository trendFoodRepository,
                       FoodPlaceRepository foodPlaceRepository,
                       CategoryRepository categoryRepository) {
        this.trendFoodRepository = trendFoodRepository;
        this.foodPlaceRepository = foodPlaceRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Get trending foods with pagination
     */
    public Page<TrendFoodDTO> getTrendingFoods(Pageable pageable) {
        Page<TrendFood> trendFoods = trendFoodRepository.findAllByOrderBySearchFrequencyDesc(pageable);
        return convertToDTO(trendFoods);
    }

    /**
     * Get trending foods by category
     */
    public Page<TrendFoodDTO> getTrendingFoodsByCategory(String category, Pageable pageable) {
        Page<TrendFood> trendFoods = trendFoodRepository.findByCategory(category, pageable);
        return convertToDTO(trendFoods);
    }

    /**
     * Search trending foods by keyword
     */
    public Page<TrendFoodDTO> searchTrendingFoods(String keyword, Pageable pageable) {
        Page<TrendFood> trendFoods = trendFoodRepository.searchByKeyword(keyword, pageable);
        return convertToDTO(trendFoods);
    }

    /**
     * Get places for a specific food
     */
    public List<FoodPlaceDTO> getPlacesForFood(Long trendFoodId) {
        List<FoodPlace> places = foodPlaceRepository.findByTrendFoodId(trendFoodId);
        return places.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get places for a specific food, sorted by rating
     */
    public List<FoodPlaceDTO> getPlacesForFoodByRating(Long trendFoodId) {
        List<FoodPlace> places = foodPlaceRepository.findByTrendFoodIdOrderByRating(trendFoodId);
        return places.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get nearest places for a specific food
     */
    public List<FoodPlaceDTO> getNearestPlacesForFood(Long trendFoodId, Double latitude, Double longitude) {
        List<FoodPlace> places = foodPlaceRepository.findNearestPlaces(trendFoodId, latitude, longitude);
        return places.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Convert TrendFood entity to DTO with category color
     */
    private TrendFoodDTO convertToDTO(TrendFood trendFood) {
        TrendFoodDTO dto = TrendFoodDTO.builder()
                .id(trendFood.getId())
                .name(trendFood.getName())
                .category(trendFood.getCategory())
                .search_frequency(trendFood.getSearch_frequency())
                .trend_rank(trendFood.getTrend_rank())
                .image_url(trendFood.getImage_url())
                .source(trendFood.getSource())
                .created_at(trendFood.getCreated_at())
                .updated_at(trendFood.getUpdated_at())
                .build();

        // Fetch category color
        if (trendFood.getCategory() != null) {
            Optional<Category> category = categoryRepository.findByName(trendFood.getCategory());
            category.ifPresent(cat -> dto.setColor(cat.getColor()));
        }

        return dto;
    }

    /**
     * Convert Page<TrendFood> to Page<TrendFoodDTO>
     */
    private Page<TrendFoodDTO> convertToDTO(Page<TrendFood> trendFoods) {
        List<TrendFoodDTO> dtos = trendFoods.getContent().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(dtos, trendFoods.getPageable(), trendFoods.getTotalElements());
    }

    /**
     * Convert FoodPlace entity to DTO
     */
    private FoodPlaceDTO convertToDTO(FoodPlace foodPlace) {
        return FoodPlaceDTO.builder()
                .id(foodPlace.getId())
                .trend_food_id(foodPlace.getTrend_food_id())
                .name(foodPlace.getName())
                .address(foodPlace.getAddress())
                .latitude(foodPlace.getLatitude())
                .longitude(foodPlace.getLongitude())
                .phone(foodPlace.getPhone())
                .website_url(foodPlace.getWebsite_url())
                .rating(foodPlace.getRating())
                .price_approx(foodPlace.getPrice_approx())
                .operating_hours(foodPlace.getOperating_hours())
                .google_place_id(foodPlace.getGoogle_place_id())
                .build();
    }

}
