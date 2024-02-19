package com.battlecruisers.yanullja.room.example;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

@Target(METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(parameters = {
    @Parameter(name="parameter", description = "방 상세 조회",examples = {
        @ExampleObject(name = "NoRequiredParameter", value = """ 
                { 
                    "checkInDate" : "", 
                    "checkOutDate" : "", 
                    "roomType" : ""
                } 
            """),
        @ExampleObject(name = "NotValidFormat", value = """ 
                { 
                    "checkInDate" : "2020-05-01", 
                    "checkOutDate" : "2020-05/01", 
                    "roomType" : "RENT"
                } 
            """)
    })
})
public @interface RoomDetailOperation {

}
