package com.tomatoma.service;

import com.tomatoma.entity.TrendFood;
import com.tomatoma.repository.TrendFoodRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrendServiceTest {

    @Mock
    private TrendFoodRepository trendFoodRepository;

    @InjectMocks
    private TrendService trendService;

    @Test
    void updateTrendingFoods_savesNewFood() {
        TrendFood food = TrendFood.builder()
                .name("마라탕").category("중식")
                .search_frequency(500).trend_rank(1).source("google_trends")
                .build();

        when(trendFoodRepository.findByName("마라탕")).thenReturn(Optional.empty());

        trendService.updateTrendingFoods(List.of(food));

        verify(trendFoodRepository).save(food);
    }

    @Test
    void updateTrendingFoods_updatesExistingFood() {
        TrendFood existing = TrendFood.builder()
                .name("마라탕").category("중식")
                .search_frequency(300).trend_rank(5).source("default")
                .build();

        TrendFood updated = TrendFood.builder()
                .name("마라탕").category("중식")
                .search_frequency(500).trend_rank(1).source("google_trends")
                .build();

        when(trendFoodRepository.findByName("마라탕")).thenReturn(Optional.of(existing));

        trendService.updateTrendingFoods(List.of(updated));

        verify(trendFoodRepository).save(existing);
        assertEquals(500, existing.getSearch_frequency());
        assertEquals(1, existing.getTrend_rank());
        assertEquals("google_trends", existing.getSource());
    }

    @Test
    void updateTrendingFoods_handlesEmptyList() {
        trendService.updateTrendingFoods(List.of());
        verifyNoInteractions(trendFoodRepository);
    }

    @Test
    void updateTrendingFoods_savesMultipleFoods() {
        List<TrendFood> foods = List.of(
                TrendFood.builder().name("치킨").category("치킨")
                        .search_frequency(1000).trend_rank(1).source("google_trends").build(),
                TrendFood.builder().name("떡볶이").category("분식")
                        .search_frequency(800).trend_rank(2).source("google_trends").build()
        );

        when(trendFoodRepository.findByName(anyString())).thenReturn(Optional.empty());

        trendService.updateTrendingFoods(foods);

        verify(trendFoodRepository, times(2)).save(any(TrendFood.class));
    }
}
