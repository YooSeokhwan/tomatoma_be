package com.tomatoma.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "trend_foods")
public class TrendFood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 50)
    private String category;

    @Column
    private Integer search_frequency; // Relative value (0-100 or higher)

    @Column
    private Integer trend_rank;

    @Column(length = 500)
    private String image_url;

    @Column(length = 50)
    private String source; // "google_trends", "naver_trends", etc.

    @Column(nullable = false, updatable = false)
    private LocalDateTime created_at;

    @Column(nullable = false)
    private LocalDateTime updated_at;

    // No-args constructor
    public TrendFood() {
    }

    // All-args constructor
    public TrendFood(Long id, String name, String category, Integer search_frequency,
                     Integer trend_rank, String image_url, String source,
                     LocalDateTime created_at, LocalDateTime updated_at) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.search_frequency = search_frequency;
        this.trend_rank = trend_rank;
        this.image_url = image_url;
        this.source = source;
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

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    // equals, hashCode, toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrendFood trendFood = (TrendFood) o;

        if (id != null ? !id.equals(trendFood.id) : trendFood.id != null) return false;
        if (name != null ? !name.equals(trendFood.name) : trendFood.name != null) return false;
        if (category != null ? !category.equals(trendFood.category) : trendFood.category != null) return false;
        if (search_frequency != null ? !search_frequency.equals(trendFood.search_frequency) : trendFood.search_frequency != null)
            return false;
        if (trend_rank != null ? !trend_rank.equals(trendFood.trend_rank) : trendFood.trend_rank != null) return false;
        if (image_url != null ? !image_url.equals(trendFood.image_url) : trendFood.image_url != null) return false;
        if (source != null ? !source.equals(trendFood.source) : trendFood.source != null) return false;
        if (created_at != null ? !created_at.equals(trendFood.created_at) : trendFood.created_at != null) return false;
        return updated_at != null ? updated_at.equals(trendFood.updated_at) : trendFood.updated_at == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (search_frequency != null ? search_frequency.hashCode() : 0);
        result = 31 * result + (trend_rank != null ? trend_rank.hashCode() : 0);
        result = 31 * result + (image_url != null ? image_url.hashCode() : 0);
        result = 31 * result + (source != null ? source.hashCode() : 0);
        result = 31 * result + (created_at != null ? created_at.hashCode() : 0);
        result = 31 * result + (updated_at != null ? updated_at.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TrendFood{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", search_frequency=" + search_frequency +
                ", trend_rank=" + trend_rank +
                ", image_url='" + image_url + '\'' +
                ", source='" + source + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }

    // Builder pattern - required for TrendService.java compatibility
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private String category;
        private Integer search_frequency;
        private Integer trend_rank;
        private String image_url;
        private String source;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder category(String category) {
            this.category = category;
            return this;
        }

        public Builder search_frequency(Integer v) {
            this.search_frequency = v;
            return this;
        }

        public Builder trend_rank(Integer v) {
            this.trend_rank = v;
            return this;
        }

        public Builder image_url(String v) {
            this.image_url = v;
            return this;
        }

        public Builder source(String v) {
            this.source = v;
            return this;
        }

        public TrendFood build() {
            TrendFood f = new TrendFood();
            f.name = this.name;
            f.category = this.category;
            f.search_frequency = this.search_frequency;
            f.trend_rank = this.trend_rank;
            f.image_url = this.image_url;
            f.source = this.source;
            return f;
        }
    }

    @PrePersist
    protected void onCreate() {
        created_at = LocalDateTime.now();
        updated_at = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updated_at = LocalDateTime.now();
    }
}
