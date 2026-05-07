package com.tomatoma.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CategoryDTO {

    private Long id;

    private String name;

    private String color;

    @JsonProperty("iconEmoji")
    private String icon_emoji;

    // No-args constructor
    public CategoryDTO() {
    }

    // All-args constructor
    public CategoryDTO(Long id, String name, String color, String icon_emoji) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.icon_emoji = icon_emoji;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public String getIcon_emoji() {
        return icon_emoji;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setIcon_emoji(String icon_emoji) {
        this.icon_emoji = icon_emoji;
    }

    // Builder pattern
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String name;
        private String color;
        private String icon_emoji;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder color(String color) {
            this.color = color;
            return this;
        }

        public Builder icon_emoji(String icon_emoji) {
            this.icon_emoji = icon_emoji;
            return this;
        }

        public CategoryDTO build() {
            return new CategoryDTO(this.id, this.name, this.color, this.icon_emoji);
        }
    }

}
