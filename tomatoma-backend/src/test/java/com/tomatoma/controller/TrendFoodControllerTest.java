package com.tomatoma.controller;

import com.tomatoma.dto.TrendFoodDTO;
import com.tomatoma.service.FoodService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TrendFoodControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FoodService foodService;

    private static final Pageable DEFAULT_PAGEABLE = PageRequest.of(0, 20);

    @Test
    void getTrendingFoods_returnsSuccessResponse() throws Exception {
        TrendFoodDTO dto = TrendFoodDTO.builder()
                .id(1L).name("마라탕").category("중식")
                .search_frequency(500).trend_rank(1).source("google_trends")
                .build();

        when(foodService.getTrendingFoods(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dto), DEFAULT_PAGEABLE, 1));

        mockMvc.perform(get("/trends").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.content[0].name").value("마라탕"))
                .andExpect(jsonPath("$.data.content[0].category").value("중식"));
    }

    @Test
    void getTrendingFoods_withCategory_delegatesToCategoryFilter() throws Exception {
        TrendFoodDTO dto = TrendFoodDTO.builder()
                .id(1L).name("짜장면").category("중식")
                .search_frequency(400).trend_rank(2).source("google_trends")
                .build();

        when(foodService.getTrendingFoodsByCategory(eq("중식"), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dto), DEFAULT_PAGEABLE, 1));

        mockMvc.perform(get("/trends").param("category", "중식"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.content[0].category").value("중식"));
    }

    @Test
    void searchTrendingFoods_withKeyword_returnsResults() throws Exception {
        TrendFoodDTO dto = TrendFoodDTO.builder()
                .id(1L).name("마라탕").category("중식")
                .search_frequency(500).trend_rank(1).source("google_trends")
                .build();

        when(foodService.searchTrendingFoods(eq("마라"), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dto), DEFAULT_PAGEABLE, 1));

        mockMvc.perform(get("/trends/search").param("q", "마라"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.content[0].name").value("마라탕"));
    }

    @Test
    void getTrendingFoods_emptyResult_returnsEmptyPage() throws Exception {
        when(foodService.getTrendingFoods(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(), DEFAULT_PAGEABLE, 0));

        mockMvc.perform(get("/trends"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.content").isEmpty());
    }

    @Test
    void getTrendingFood_byId_returnsSuccessResponse() throws Exception {
        TrendFoodDTO dto = TrendFoodDTO.builder()
                .id(1L).name("마라탕").category("중식")
                .search_frequency(500).trend_rank(1).source("google_trends")
                .build();

        when(foodService.getTrendingFoodById(1L)).thenReturn(Optional.of(dto));

        mockMvc.perform(get("/trends/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.name").value("마라탕"));
    }

    @Test
    void getTrendingFood_byId_notFound_returns404() throws Exception {
        when(foodService.getTrendingFoodById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/trends/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("error"));
    }
}
