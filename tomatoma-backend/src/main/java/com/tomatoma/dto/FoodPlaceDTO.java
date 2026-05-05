package com.tomatoma.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FoodPlaceDTO {

    private Long id;

    @JsonProperty("trendFoodId")
    private Long trend_food_id;

    private String name;

    private String address;

    private Double latitude;

    private Double longitude;

    private String phone;

    @JsonProperty("websiteUrl")
    private String website_url;

    private Double rating;

    @JsonProperty("priceApprox")
    private Integer price_approx;

    @JsonProperty("operatingHours")
    private String operating_hours;

    @JsonProperty("googlePlaceId")
    private String google_place_id;

    // No-args constructor
    public FoodPlaceDTO() {
    }

    // All-args constructor
    public FoodPlaceDTO(Long id, Long trend_food_id, String name, String address, Double latitude,
                        Double longitude, String phone, String website_url, Double rating,
                        Integer price_approx, String operating_hours, String google_place_id) {
        this.id = id;
        this.trend_food_id = trend_food_id;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phone = phone;
        this.website_url = website_url;
        this.rating = rating;
        this.price_approx = price_approx;
        this.operating_hours = operating_hours;
        this.google_place_id = google_place_id;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Long getTrend_food_id() {
        return trend_food_id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite_url() {
        return website_url;
    }

    public Double getRating() {
        return rating;
    }

    public Integer getPrice_approx() {
        return price_approx;
    }

    public String getOperating_hours() {
        return operating_hours;
    }

    public String getGoogle_place_id() {
        return google_place_id;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setTrend_food_id(Long trend_food_id) {
        this.trend_food_id = trend_food_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setWebsite_url(String website_url) {
        this.website_url = website_url;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void setPrice_approx(Integer price_approx) {
        this.price_approx = price_approx;
    }

    public void setOperating_hours(String operating_hours) {
        this.operating_hours = operating_hours;
    }

    public void setGoogle_place_id(String google_place_id) {
        this.google_place_id = google_place_id;
    }

    // Builder pattern
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private Long trend_food_id;
        private String name;
        private String address;
        private Double latitude;
        private Double longitude;
        private String phone;
        private String website_url;
        private Double rating;
        private Integer price_approx;
        private String operating_hours;
        private String google_place_id;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder trend_food_id(Long trend_food_id) {
            this.trend_food_id = trend_food_id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder latitude(Double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder longitude(Double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder website_url(String website_url) {
            this.website_url = website_url;
            return this;
        }

        public Builder rating(Double rating) {
            this.rating = rating;
            return this;
        }

        public Builder price_approx(Integer price_approx) {
            this.price_approx = price_approx;
            return this;
        }

        public Builder operating_hours(String operating_hours) {
            this.operating_hours = operating_hours;
            return this;
        }

        public Builder google_place_id(String google_place_id) {
            this.google_place_id = google_place_id;
            return this;
        }

        public FoodPlaceDTO build() {
            return new FoodPlaceDTO(this.id, this.trend_food_id, this.name, this.address,
                    this.latitude, this.longitude, this.phone, this.website_url, this.rating,
                    this.price_approx, this.operating_hours, this.google_place_id);
        }
    }

}
