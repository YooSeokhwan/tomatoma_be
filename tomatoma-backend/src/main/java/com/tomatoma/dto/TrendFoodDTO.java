package com.tomatoma.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class TrendFoodDTO {

    private Long id;

    private String name;

    private String category;

    @JsonProperty("searchFrequency")
    private Integer search_frequency;

    @JsonProperty("trendRank")
    private Integer trend_rank;

    @JsonProperty("imageUrl")
    private String image_url;

    private String source;

    private String color; // From Category entity

    @JsonProperty("createdAt")
    private LocalDateTime created_at;

    @JsonProperty("updatedAt")
    private LocalDateTime updated_at;

    // No-args constructor
    public TrendFoodDTO() {
    }

    // All-args constructor
    public TrendFoodDTO(Long id, String name, String category, Integer search_frequency,
                        Integer trend_rank, String image_url, String source, String color,
                        LocalDateTime created_at, LocalDateTime updated_at) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.search_frequency = search_frequency;
        this.trend_rank = trend_rank;
        this.image_url = image_url;
        this.source = source;
        this.color = color;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public Integer getSearch_frequency() {
        return search_frequency;
    }

    public Integer getTrend_rank() {
        return trend_rank;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getSource() {
        return source;
    }

    public String getColor() {
        return color;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setSearch_frequency(Integer search_frequency) {
        this.search_frequency = search_frequency;
    }

    public void setTrend_rank(Integer trend_rank) {
        this.trend_rank = trend_rank;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    // Builder pattern
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String name;
        private String category;
        private Integer search_frequency;
        private Integer trend_rank;
        private String image_url;
        private String source;
        private String color;
        private LocalDateTime created_at;
        private LocalDateTime updated_at;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder category(String category) {
            this.category = category;
            return this;
        }

        public Builder search_frequency(Integer search_frequency) {
            this.search_frequency = search_frequency;
            return this;
        }

        public Builder trend_rank(Integer trend_rank) {
            this.trend_rank = trend_rank;
            return this;
        }

        public Builder image_url(String image_url) {
            this.image_url = image_url;
            return this;
        }

        public Builder source(String source) {
            this.source = source;
            return this;
        }

        public Builder color(String color) {
            this.color = color;
            return this;
        }

        public Builder created_at(LocalDateTime created_at) {
            this.created_at = created_at;
            return this;
        }

        public Builder updated_at(LocalDateTime updated_at) {
            this.updated_at = updated_at;
            return this;
        }

        public TrendFoodDTO build() {
            return new TrendFoodDTO(this.id, this.name, this.category, this.search_frequency,
                    this.trend_rank, this.image_url, this.source, this.color,
                    this.created_at, this.updated_at);
        }
    }

}
