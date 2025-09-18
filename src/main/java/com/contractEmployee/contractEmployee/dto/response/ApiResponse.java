package com.contractEmployee.contractEmployee.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;
    private int status;
    private boolean success;
    private String message;
    private String path;
    private T data;

    public static <T> ApiResponse<T> success(String msg, T data, String path) {
        return new ApiResponse<>(LocalDateTime.now(), 200, true, msg, path, data);
    }

    public static <T> ApiResponse<T> error(int status, String msg, String path) {
        return new ApiResponse<>(LocalDateTime.now(), status, false, msg, path, null);
    }
}
