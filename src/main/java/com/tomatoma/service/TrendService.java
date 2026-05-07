package com.tomatoma.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomatoma.entity.TrendFood;
import com.tomatoma.repository.TrendFoodRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;

@Service
public class TrendService {

    private static final Logger log = LoggerFactory.getLogger(TrendService.class);

    private final TrendFoodRepository trendFoodRepository;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    private static final List<String> DEFAULT_FOODS = Arrays.asList(
            "마라탕", "떡볶이", "순대국", "삼겹살", "치킨",
            "초밥", "라면", "김치찌개", "족발", "짜장면",
            "짬뽕", "칼국수", "곱창", "갈비탕", "냉면",
            "부대찌개", "양념치킨", "감자탕", "된장찌개", "돈까스"
    );

    private static final List<String> FOOD_KEYWORDS = Arrays.asList(
            "먹", "식당", "맛집", "음식", "요리", "밥", "국", "찌개", "탕", "볶음",
            "구이", "튀김", "전", "찜", "조림", "무침", "냉면", "라면", "떡", "케이크",
            "빵", "커피", "카페", "치킨", "피자", "버거", "초밥", "스시", "파스타",
            "스테이크", "샐러드", "디저트", "마라", "갈비", "삼겹", "보쌈", "족발",
            "순대", "어묵", "곱창", "막창", "쌈", "비빔", "만두", "국수", "우동",
            "짜장", "짬뽕", "돈까스", "오므라이스", "김밥", "도시락", "쌀국수"
    );

    // Popular Instagram food hashtags to monitor
    private static final List<String> INSTAGRAM_FOOD_TAGS = Arrays.asList(
            "마라탕", "떡볶이", "삼겹살", "치킨", "초밥", "라면", "부대찌개",
            "냉면", "갈비", "족발", "보쌈", "파스타", "피자", "버거",
            "비빔밥", "된장찌개", "김치찌개", "곱창", "돈까스", "순두부찌개"
    );

    public TrendService(TrendFoodRepository trendFoodRepository) {
        this.trendFoodRepository = trendFoodRepository;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Aggregate trending foods from Google Trends, X/Twitter, and Instagram
     */
    public List<TrendFood> fetchTrendingFoods() {
        log.info("Fetching trending foods from all sources...");

        List<TrendFood> googleTrends = fetchGoogleTrends();
        List<TrendFood> xTrends = fetchXTrends();
        List<TrendFood> instagramTrends = fetchInstagramTrends();

        log.info("Sources: Google={}, X={}, Instagram={}",
                googleTrends.size(), xTrends.size(), instagramTrends.size());

        // Merge with weighted frequencies, deduplicating by name
        Map<String, TrendFood> mergedMap = new LinkedHashMap<>();
        addToMergedMap(mergedMap, googleTrends, 1.0);
        addToMergedMap(mergedMap, xTrends, 1.2);
        addToMergedMap(mergedMap, instagramTrends, 0.8);

        List<TrendFood> merged = new ArrayList<>(mergedMap.values());

        if (merged.isEmpty()) {
            log.warn("All sources returned empty, using defaults");
            return getDefaultTrendingFoods();
        }

        // Re-sort by combined frequency and re-rank
        merged.sort((a, b) -> b.getSearch_frequency() - a.getSearch_frequency());
        for (int i = 0; i < merged.size(); i++) {
            merged.get(i).setTrend_rank(i + 1);
        }

        log.info("Total merged trending foods: {}", merged.size());
        return merged;
    }

    private void addToMergedMap(Map<String, TrendFood> mergedMap, List<TrendFood> foods, double weight) {
        for (TrendFood food : foods) {
            String name = food.getName();
            int weightedFreq = (int) (food.getSearch_frequency() * weight);

            if (mergedMap.containsKey(name)) {
                TrendFood existing = mergedMap.get(name);
                existing.setSearch_frequency(existing.getSearch_frequency() + weightedFreq);
                String combinedSource = existing.getSource() + "," + food.getSource();
                if (combinedSource.length() > 50) combinedSource = combinedSource.substring(0, 50);
                existing.setSource(combinedSource);
            } else {
                TrendFood copy = TrendFood.builder()
                        .name(name)
                        .category(food.getCategory())
                        .search_frequency(weightedFreq)
                        .trend_rank(food.getTrend_rank())
                        .source(food.getSource())
                        .build();
                mergedMap.put(name, copy);
            }
        }
    }

    /**
     * Google Trends realtime API - Food and Drink category, South Korea
     */
    private List<TrendFood> fetchGoogleTrends() {
        log.info("Fetching from Google Trends...");
        List<TrendFood> trendingFoods = new ArrayList<>();

        try {
            String url = "https://trends.google.com/trends/api/realtimetrends?hl=ko&tz=-540&cat=71&fi=0&fs=0&geo=KR&ri=300&rs=20&sort=0";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(10))
                    .GET()
                    .header("Accept", "application/json")
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String body = response.body();
                if (body.startsWith(")]}'\n")) {
                    body = body.substring(5);
                }

                JsonNode root = objectMapper.readTree(body);
                JsonNode trendingStories = root.path("storySummaries").path("trendingStories");
                int totalStories = trendingStories.isArray() ? trendingStories.size() : 0;

                if (trendingStories.isArray()) {
                    int rank = 1;
                    for (JsonNode story : trendingStories) {
                        if (rank > 20) break;
                        JsonNode entityNames = story.path("entityNames");
                        if (entityNames.isArray() && entityNames.size() > 0) {
                            String foodName = entityNames.path(0).asText();
                            int searchFrequency = (totalStories - rank + 1) * 100;

                            trendingFoods.add(TrendFood.builder()
                                    .name(foodName)
                                    .category(categorizeFoodName(foodName))
                                    .search_frequency(searchFrequency)
                                    .trend_rank(rank)
                                    .source("google_trends")
                                    .build());
                            rank++;
                        }
                    }
                }

                log.info("Google Trends: {} foods fetched", trendingFoods.size());
            } else {
                log.warn("Google Trends returned status {}", response.statusCode());
            }
        } catch (Exception e) {
            log.warn("Error fetching Google Trends: {}", e.getMessage());
        }

        return trendingFoods;
    }

    /**
     * X/Twitter trending topics from trends24.in (South Korea)
     * Filters results to food-related topics only
     */
    private List<TrendFood> fetchXTrends() {
        log.info("Fetching X/Twitter trends from trends24.in...");
        List<TrendFood> trendingFoods = new ArrayList<>();

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://trends24.in/south-korea/"))
                    .timeout(Duration.ofSeconds(15))
                    .GET()
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                    .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8")
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Document doc = Jsoup.parse(response.body());

                // Try primary selector, fall back to broader selectors
                Elements trendItems = doc.select(".trend-card__list li a");
                if (trendItems.isEmpty()) {
                    trendItems = doc.select("ol.trend-card__list a");
                }
                if (trendItems.isEmpty()) {
                    trendItems = doc.select(".trend-card ol li a");
                }

                Set<String> seen = new HashSet<>();
                int rank = 1;

                for (org.jsoup.nodes.Element item : trendItems) {
                    if (rank > 10) break;
                    String trendName = item.text().trim();
                    if (trendName.isEmpty() || seen.contains(trendName)) continue;

                    if (isFoodRelated(trendName)) {
                        seen.add(trendName);
                        trendingFoods.add(TrendFood.builder()
                                .name(trendName)
                                .category(categorizeFoodName(trendName))
                                .search_frequency(Math.max(100, 1500 - rank * 60))
                                .trend_rank(rank)
                                .source("x_twitter")
                                .build());
                        rank++;
                    }
                }

                log.info("X/Twitter Trends: {} food-related topics found", trendingFoods.size());
            } else {
                log.warn("trends24.in returned status {}", response.statusCode());
            }
        } catch (Exception e) {
            log.warn("Error fetching X/Twitter trends: {}", e.getMessage());
        }

        return trendingFoods;
    }

    /**
     * Instagram food trends via hashtag media counts
     * Uses Instagram's internal mobile API to get post counts per hashtag
     */
    private List<TrendFood> fetchInstagramTrends() {
        log.info("Fetching Instagram food trends...");
        List<TrendFood> trendingFoods = new ArrayList<>();
        int rank = 1;

        for (String tag : INSTAGRAM_FOOD_TAGS) {
            if (rank > 15) break;
            try {
                String encodedTag = URLEncoder.encode(tag, StandardCharsets.UTF_8);
                String url = "https://i.instagram.com/api/v1/tags/" + encodedTag + "/info/";

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .timeout(Duration.ofSeconds(10))
                        .GET()
                        .header("User-Agent", "Instagram 219.0.0.12.117 Android (28/9; 411dpi; 1080x2140; Xiaomi; Mi 9T; davinci; qcom; ko_KR; 340141937)")
                        .header("x-ig-app-id", "567067343352427")
                        .header("Accept", "application/json")
                        .build();

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    JsonNode root = objectMapper.readTree(response.body());
                    // Instagram can return media_count at different paths depending on API version
                    long mediaCount = root.path("data").path("media_count").asLong(0);
                    if (mediaCount == 0) mediaCount = root.path("media_count").asLong(0);

                    if (mediaCount > 0) {
                        int searchFrequency = (int) Math.min(mediaCount / 1000, 5000);
                        trendingFoods.add(TrendFood.builder()
                                .name(tag)
                                .category(categorizeFoodName(tag))
                                .search_frequency(searchFrequency)
                                .trend_rank(rank)
                                .source("instagram")
                                .build());
                        rank++;
                    }
                } else {
                    log.debug("Instagram returned {} for #{}", response.statusCode(), tag);
                }

                Thread.sleep(200);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                log.debug("Failed to fetch Instagram data for #{}: {}", tag, e.getMessage());
            }
        }

        log.info("Instagram Trends: {} foods fetched", trendingFoods.size());
        return trendingFoods;
    }

    /**
     * Save or update trending foods, checking for duplicates by name
     */
    public void updateTrendingFoods(List<TrendFood> trendingFoods) {
        log.info("Updating {} trending foods in database...", trendingFoods.size());

        for (TrendFood food : trendingFoods) {
            Optional<TrendFood> existing = trendFoodRepository.findByName(food.getName());

            if (existing.isPresent()) {
                TrendFood existingFood = existing.get();
                existingFood.setSearch_frequency(food.getSearch_frequency());
                existingFood.setTrend_rank(food.getTrend_rank());
                existingFood.setSource(food.getSource());
                trendFoodRepository.save(existingFood);
            } else {
                trendFoodRepository.save(food);
            }
        }

        log.info("Updated trending foods in database");
    }

    private List<TrendFood> getDefaultTrendingFoods() {
        log.info("Using default trending foods fallback");
        List<TrendFood> defaultFoods = new ArrayList<>();
        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int rotateOffset = currentHour % DEFAULT_FOODS.size();

        for (int i = 0; i < DEFAULT_FOODS.size(); i++) {
            int index = (i + rotateOffset) % DEFAULT_FOODS.size();
            String foodName = DEFAULT_FOODS.get(index);
            defaultFoods.add(TrendFood.builder()
                    .name(foodName)
                    .category(categorizeFoodName(foodName))
                    .search_frequency(new Random().nextInt(100) + 1)
                    .trend_rank(i + 1)
                    .source("default")
                    .build());
        }

        return defaultFoods;
    }

    private boolean isFoodRelated(String name) {
        if (name == null || name.isEmpty()) return false;
        String lower = name.toLowerCase();
        for (String keyword : FOOD_KEYWORDS) {
            if (lower.contains(keyword)) return true;
        }
        return false;
    }

    private String categorizeFoodName(String foodName) {
        String lower = foodName.toLowerCase();
        if (lower.contains("국") || lower.contains("밥") || lower.contains("찌개") ||
                lower.contains("갈비") || lower.contains("삼겹") || lower.contains("불고기") ||
                lower.contains("보쌈") || lower.contains("족발") || lower.contains("비빔") ||
                lower.contains("된장") || lower.contains("순대") || lower.contains("떡볶")) {
            return "한식";
        } else if (lower.contains("파스타") || lower.contains("스테이크") || lower.contains("리조또") ||
                lower.contains("샐러드") || lower.contains("오므라이")) {
            return "양식";
        } else if (lower.contains("짜장") || lower.contains("짬뽕") || lower.contains("탕수") ||
                lower.contains("마라") || lower.contains("훠궈")) {
            return "중식";
        } else if (lower.contains("커피") || lower.contains("카페") || lower.contains("라떼") ||
                lower.contains("아메리카노") || lower.contains("티")) {
            return "카페";
        } else if (lower.contains("초밥") || lower.contains("회") || lower.contains("우동") ||
                lower.contains("라멘") || lower.contains("돈까스")) {
            return "일식";
        } else if (lower.contains("치킨") || lower.contains("프라이드")) {
            return "치킨";
        } else if (lower.contains("피자")) {
            return "피자";
        } else if (lower.contains("버거") || lower.contains("햄버거")) {
            return "버거";
        } else if (lower.contains("떡") || lower.contains("어묵") || lower.contains("김밥") ||
                lower.contains("라면") || lower.contains("국수")) {
            return "분식";
        } else if (lower.contains("케이크") || lower.contains("초콜릿") || lower.contains("디저트") ||
                lower.contains("빵") || lower.contains("쿠키") || lower.contains("아이스")) {
            return "디저트";
        }
        return "기타";
    }
}
