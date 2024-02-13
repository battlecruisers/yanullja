package com.battlecruisers.yanullja.review.example;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

@Target(METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
    summary = "리뷰 등록 API",
    requestBody = @RequestBody(content = @Content(
        examples = {
            @ExampleObject(
                name = "NoPlaceOrRoomId",
                value = """ 
                { 
                    "placeId" : "", 
                    "roomId" : "", 
                    "content" : "review contents", 
                    "kindnessRate" : "5", 
                    "cleanlinessRate" : "5", 
                    "convenienceRate" : "5", 
                    "locationRate" : "5", 
                    "totalRate" : "5"
                } 
                """,
                description = "숙소 또는 방 ID가 없는 경우"
            ),
            @ExampleObject(
                name = "ContentLengthNotInRange",
                value = """
                    {
                        "placeId" : "1",
                        "roomId" : "1",
                        "content" : "",
                        "kindnessRate" : "5",
                        "cleanlinessRate" : "5",
                        "convenienceRate" : "5",
                        "locationRate" : "5",
                        "totalRate" : "5"
                    }
                    """,
                description = "후기가 1자 보다 작거나 1000자 보다 큰 경우"
            ),
            @ExampleObject(
                name = "RateNotInRange",
                value = """  
                { 
                    "placeId" : "1", 
                    "roomId" : "1", 
                    "content" : "review contents",
                    "kindnessRate" : "-1",
                    "cleanlinessRate" : "0",
                    "convenienceRate" : "0",
                    "locationRate" : "0",
                    "totalRate" : "0"
                } 
                """,
                description = "평점이 0보다 작거나 5보다 큰 경우"
            )
        }
    ))
)
public @interface WriteReviewOperation {

}
