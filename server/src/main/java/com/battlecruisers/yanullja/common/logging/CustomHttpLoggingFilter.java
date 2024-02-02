package com.battlecruisers.yanullja.common.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
@Slf4j
public class CustomHttpLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
        HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain)
        throws ServletException, IOException {

        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(
            request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(
            response);

        logRequest(wrappedRequest);

        // Continue the filter chain
        filterChain.doFilter(wrappedRequest, wrappedResponse);

        logResponse(wrappedResponse);

        // Important: copy the content of the wrapped response to the actual response
        wrappedResponse.copyBodyToResponse();
    }

    private String buildReqHeaderString(HttpServletRequest request) {
        var headerNames = request.getHeaderNames();

        if (headerNames == null) {
            return "No Headers";
        }

        return Collections.list(headerNames).stream()
            .map(
                headerName -> headerName + ": " + request.getHeader(headerName))
            .collect(Collectors.joining("\n"));
    }

    private String buildResHeaderString(HttpServletResponse response) {
        return response.getHeaderNames().stream()
            .map(headerName -> headerName + ": " + response.getHeader(
                headerName))
            .collect(Collectors.joining("\n"));
    }

    private void logRequest(ContentCachingRequestWrapper request)
        throws IOException {
        String requestBody = new String(request.getContentAsByteArray(),
            Charset.defaultCharset());

        // Log the request URI, headers, and body
        log.info(
            "\n\nRequest: {} {}\n{} \n\nRequest Body: {}\n",
            request.getMethod(),
            request.getRequestURI(),
            buildReqHeaderString(request),
            requestBody.isEmpty() ? "<<No Request Body>>" : requestBody);
    }

    private void logResponse(ContentCachingResponseWrapper response)
        throws IOException {
        String responseBody =
            new String(response.getContentAsByteArray(),
                Charset.defaultCharset());

        log.info(
            "\n\nResponse Status: {}\n{}\n\nResponse Body: {}\n",
            response.getStatus(),
            buildResHeaderString(response),
            responseBody.isEmpty() ? "<<No Response Body>>" : responseBody);
    }
}
