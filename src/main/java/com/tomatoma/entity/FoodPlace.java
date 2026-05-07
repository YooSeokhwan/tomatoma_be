package com.tomatoma.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "food_places")
public class FoodPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long trend_food_id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(nullable = false, length = 300)
    private String address;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(length = 20)
    private String phone;

    @Column(length = 300)
    private String website_url;

    @Column
    private Double rating; // Google Places rating (0.0 - 5.0)

    @Column
    private Integer price_approx; // Approximate price in KRW

    @Column(length = 200)
    private String operating_hours;

    @Column(length = 100)
    private String google_place_id; // For linking to Google Places

    @Column(nullable = false, updatable = false)
    private LocalDateTime created_at;

    @Column(nullable = false)
    private LocalDateTime updated_at;

    // No-args constructor
    public FoodPlace() {
    }

    // All-args constructor
    public FoodPlace(Long id, Long trend_food_id, String name, String address,
                     Double latitude, Double longitude, String phone, String website_url,
                     Double rating, Integer price_approx, String operating_hours,
                     String google_place_id, LocalDateTime created_at, LocalDateTime updated_at) {
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
        this.created_at = created_at;
        this.updated_at = updated_at;
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

        FoodPlace foodPlace = (FoodPlace) o;

        if (id != null ? !id.equals(foodPlace.id) : foodPlace.id != null) return false;
        if (trend_food_id != null ? !trend_food_id.equals(foodPlace.trend_food_id) : foodPlace.trend_food_id != null)
            return false;
        if (name != null ? !name.equals(foodPlace.name) : foodPlace.name != null) return false;
        if (address != null ? !address.equals(foodPlace.address) : foodPlace.address != null) return false;
        if (latitude != null ? !latitude.equals(foodPlace.latitude) : foodPlace.latitude != null) return false;
        if (longitude != null ? !longitude.equals(foodPlace.longitude) : foodPlace.longitude != null) return false;
        if (phone != null ? !phone.equals(foodPlace.phone) : foodPlace.phone != null) return false;
        if (website_url != null ? !website_url.equals(foodPlace.website_url) : foodPlace.website_url != null)
            return false;
        if (rating != null ? !rating.equals(foodPlace.rating) : foodPlace.rating != null) return false;
        if (price_approx != null ? !price_approx.equals(foodPlace.price_approx) : foodPlace.price_approx != null)
            return false;
        if (operating_hours != null ? !operating_hours.equals(foodPlace.operating_hours) : foodPlace.operating_hours != null)
            return false;
        if (google_place_id != null ? !google_place_id.equals(foodPlace.google_place_id) : foodPlace.google_place_id != null)
            return false;
        if (created_at != null ? !created_at.equals(foodPlace.created_at) : foodPlace.created_at != null) return false;
        return updated_at != null ? updated_at.equals(foodPlace.updated_at) : foodPlace.updated_at == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (trend_food_id != null ? trend_food_id.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (website_url != null ? website_url.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        result = 31 * result + (price_approx != null ? price_approx.hashCode() : 0);
        result = 31 * result + (operating_hours != null ? operating_hours.hashCode() : 0);
        result = 31 * result + (google_place_id != null ? google_place_id.hashCode() : 0);
        result = 31 * result + (created_at != null ? created_at.hashCode() : 0);
        result = 31 * result + (updated_at != null ? updated_at.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FoodPlace{" +
                "id=" + id +
                ", trend_food_id=" + trend_food_id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", phone='" + phone + '\'' +
                ", website_url='" + website_url + '\'' +
                ", rating=" + rating +
                ", price_approx=" + price_approx +
                ", operating_hours='" + operating_hours + '\'' +
                ", google_place_id='" + google_place_id + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
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
