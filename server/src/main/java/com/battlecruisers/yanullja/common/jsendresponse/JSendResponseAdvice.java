package com.battlecruisers.yanullja.common.jsendresponse;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice(basePackages = "com.battlecruisers.yanullja")
public class JSendResponseAdvice implements ResponseBodyAdvice<Object> {


    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {

        return returnType.getContainingClass()
            .isAnnotationPresent(RestController.class) ||
            returnType.hasMethodAnnotation(ResponseBody.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
        MediaType selectedContentType, Class selectedConverterType,
        ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof JSendResponse) {
            return body;
        }
        return JSendResponse.success(body);
    }
}
