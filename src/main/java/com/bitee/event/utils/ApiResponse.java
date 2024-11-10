package com.bitee.event.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private String responseCode ;
    private String  responseMessage;
    private T data;
    private T errors;

    //success response method
    public static <T> ApiResponse<T> success(String responseCode, String responseMessage, T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setResponseCode(responseCode);
        response.setResponseMessage(responseMessage);
        response.setData(data);
        return response;
    }

    public static <T> ApiResponse<T> error(String responseCode, String responseMessage, T error) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setResponseCode(responseCode);
        response.setResponseMessage(responseMessage);
        response.setErrors(error);
        return response;
    }

}
