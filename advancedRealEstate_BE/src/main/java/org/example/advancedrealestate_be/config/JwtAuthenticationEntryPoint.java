package org.example.advancedrealestate_be.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.advancedrealestate_be.dto.request.ApiResponse;
import org.example.advancedrealestate_be.exception.ErrorCode;
import org.springframework.http.MediaType;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


import javax.naming.AuthenticationException;
import java.io.IOException;
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
//    @Override
//    public void commence(
//            HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
//            throws IOException, ServletException {
//        ErrorCode errorCode = ErrorCode.UNAUTHENTICATED;
//
//        response.setStatus(errorCode.getStatusCode().value());
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//
//        ApiResponse<?> apiResponse = ApiResponse.builder()
//                .code(errorCode.getCode())
//                .message(errorCode.getMessage())
//                .build();
//
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
//        response.flushBuffer();
//    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.AuthenticationException authException) throws IOException, ServletException {
        ErrorCode errorCode = ErrorCode.UNAUTHENTICATED;

        response.setStatus(errorCode.getStatusCode().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        response.flushBuffer();
    }
}
