package com.battlecruisers.yanullja.common.jsendresponse;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class JSendResponse {

    private String status;
    private Object data;

    public static JSendResponse success(Object data) {
        JSendResponse response = new JSendResponse();
        response.setStatus("SUCCESS");
        response.setData(data);
        return response;
    }

    public static JSendResponse fail(Object data) {
        JSendResponse response = new JSendResponse();
        response.setStatus("FAIL");
        response.setData(data);
        return response;
    }

    public static JSendResponse error(Object data) {
        JSendResponse response = new JSendResponse();
        response.setStatus("ERROR");
        response.setData(data);
        return response;
    }

}
