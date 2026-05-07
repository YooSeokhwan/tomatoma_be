package com.tomatoma.service;

import com.tomatoma.dto.FoodPlaceDTO;
import com.tomatoma.dto.TrendFoodDTO;
import com.tomatoma.entity.FoodPlace;
import com.tomatoma.entity.TrendFood;
import com.tomatoma.repository.CategoryRepository;
import com.tomatoma.repository.FoodPlaceRepository;
import com.tomatoma.repository.TrendFoodRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FoodServiceTest {

    @Mock
    private TrendFoodRepository trendFoodRepository;
    @Mock
    private FoodPlaceRepository foodPlaceRepository;
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private FoodService foodService;

    @Test
    void getTrendingFoods_returnsPageOfDTOs() {
        TrendFood food = TrendFood.builder()
                .name("치킨").category("치킨")
                .search_frequency(1000).trend_rank(1).source("google_trends")
                .build();

        Pageable pageable = PageRequest.of(0, 10);
        when(trendFoodRepository.findAllByOrderBySearchFrequencyDesc(pageable))
                .thenReturn(new PageImpl<>(List.of(food)));
        when(categoryRepository.findByName("치킨")).thenReturn(Optional.empty());

        Page<TrendFoodDTO> result = foodService.getTrendingFoods(pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("치킨", result.getContent().get(0).getName());
        assertEquals("치킨", result.getContent().get(0).getCategory());
    }

    @Test
    void getTrendingFoodsByCategory_returnsFilteredResults() {
        TrendFood food = TrendFood.builder()
                .name("마라탕").category("중식")
                .search_frequency(500).trend_rank(1).source("google_trends")
                .build();

        Pageable pageable = PageRequest.of(0, 10);
        when(trendFoodRepository.findByCategory("중식", pageable))
                .thenReturn(new PageImpl<>(List.of(food)));
        when(categoryRepository.findByName("중식")).thenReturn(Optional.empty());

        Page<TrendFoodDTO> result = foodService.getTrendingFoodsByCategory("중식", pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("중식", result.getContent().get(0).getCategory());
    }

    @Test
    void searchTrendingFoods_returnsMatchingResults() {
        TrendFood food = TrendFood.builder()
                .name("마라탕").category("중식")
                .search_frequency(500).trend_rank(1).source("google_trends")
                .build();

        Pageable pageable = PageRequest.of(0, 10);
        when(trendFoodRepository.searchByKeyword("마라", pageable))
                .thenReturn(new PageImpl<>(List.of(food)));
        when(categoryRepository.findByName("중식")).thenReturn(Optional.empty());

        Page<TrendFoodDTO> result = foodService.searchTrendingFoods("마라", pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("마라탕", result.getContent().get(0).getName());
    }

    @Test
    void getTrendingFoods_emptyRepository_returnsEmptyPage() {
        Pageable pageable = PageRequest.of(0, 10);
        when(trendFoodRepository.findAllByOrderBySearchFrequencyDesc(pageable))
                .thenReturn(new PageImpl<>(List.of()));

        Page<TrendFoodDTO> result = foodService.getTrendingFoods(pageable);

        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
    }

    @Test
    void getPlacesForFood_returnsEmptyListWhenNoPlaces() {
        when(foodPlaceRepository.findByTrendFoodId(1L)).thenReturn(List.of());

        List<FoodPlaceDTO> result = foodService.getPlacesForFood(1L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
