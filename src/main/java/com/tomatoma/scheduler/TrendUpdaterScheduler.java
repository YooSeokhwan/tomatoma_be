package com.tomatoma.scheduler;

import com.tomatoma.entity.TrendFood;
import com.tomatoma.repository.TrendFoodRepository;
import com.tomatoma.service.PlacesService;
import com.tomatoma.service.TrendService;
import com.tomatoma.dto.FoodPlaceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TrendUpdaterScheduler {

    private static final Logger log = LoggerFactory.getLogger(TrendUpdaterScheduler.class);

    private final TrendService trendService;
    private final PlacesService placesService;
    private final TrendFoodRepository trendFoodRepository;

    @Value("${scheduler.enabled:true}")
    private boolean schedulerEnabled;

    public TrendUpdaterScheduler(TrendService trendService,
                                 PlacesService placesService,
                                 TrendFoodRepository trendFoodRepository) {
        this.trendService = trendService;
        this.placesService = placesService;
        this.trendFoodRepository = trendFoodRepository;
    }

    // 서울 중심 좌표
    private static final double SEOUL_LAT = 37.5665;
    private static final double SEOUL_LNG = 126.9780;

    @Scheduled(cron = "${scheduler.trend.cron:0 0 * * * ?}")
    public void updateTrendingFoods() {
        if (!schedulerEnabled) {
            log.info("Scheduler is disabled");
            return;
        }
        log.info("=== Starting scheduled trend update ===");
        try {
            // 1. 트렌드 음식 크롤링
            List<TrendFood> trendingFoods = trendService.fetchTrendingFoods();
            trendService.updateTrendingFoods(trendingFoods);
            log.info("Trend update completed. Updated {} foods", trendingFoods.size());

            // 2. 각 트렌드 음식의 판매처 검색 및 저장
            List<TrendFood> savedFoods = trendFoodRepository.findAll();
            for (TrendFood food : savedFoods) {
                try {
                    log.info("Fetching places for: {}", food.getName());
                    List<FoodPlaceDTO> places = placesService.searchPlacesForFood(
                            food.getName(), SEOUL_LAT, SEOUL_LNG);
                    placesService.savePlaces(food.getId(), places);
                    log.info("Saved {} places for: {}", places.size(), food.getName());
                    // Places API rate limit 방지
                    Thread.sleep(200);
                } catch (Exception e) {
                    log.error("Error fetching places for {}: {}", food.getName(), e.getMessage());
                }
            }
            log.info("=== Trend update cycle complete ===");
        } catch (Exception e) {
            log.error("Error during scheduled trend update: {}", e.getMessage(), e);
        }
    }
}
