package com.tomatoma.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO<T> {

    private String status; // "success" or "error"

    private T data;

    private String message;

    private Integer code; // HTTP status code

    // No-args constructor
    public ResponseDTO() {
    }

    // All-args constructor
    public ResponseDTO(String status, T data, String message, Integer code) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.code = code;
    }

    // Getters
    public String getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

    // Setters
    public void setStatus(String status) {
        this.status = status;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    // Builder pattern with generic type support
    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public static class Builder<T> {
        private String status;
        private T data;
        private String message;
        private Integer code;

        public Builder<T> status(String status) {
            this.status = status;
            return this;
        }

        public Builder<T> data(T data) {
            this.data = data;
            return this;
        }

        public Builder<T> message(String message) {
            this.message = message;
            return this;
        }

        public Builder<T> code(Integer code) {
            this.code = code;
            return this;
        }

        public ResponseDTO<T> build() {
            return new ResponseDTO<>(this.status, this.data, this.message, this.code);
        }
    }

    // Static factory methods
    public static <T> ResponseDTO<T> success(T data) {
        return ResponseDTO.<T>builder()
                .status("success")
                .data(data)
                .code(200)
                .build();
    }

    public static <T> ResponseDTO<T> success(T data, String message) {
        return ResponseDTO.<T>builder()
                .status("success")
                .data(data)
                .message(message)
                .code(200)
                .build();
    }

    public static <T> ResponseDTO<T> error(String message, Integer code) {
        return ResponseDTO.<T>builder()
                .status("error")
                .message(message)
                .code(code)
                .build();
    }

}
