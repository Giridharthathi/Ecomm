package com.ns.task.dto;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Response<T> {
    private T data;
    private String responseDescription;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getResponseDescription() {
        return responseDescription;
    }

    public void setResponseDescription(String responseDescription) {
        this.responseDescription = responseDescription;
    }
}
