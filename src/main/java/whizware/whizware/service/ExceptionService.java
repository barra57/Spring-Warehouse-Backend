package whizware.whizware.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Service;
import whizware.whizware.dto.BaseResponse;

import java.io.IOException;
import java.lang.annotation.Annotation;

@Service
public class ExceptionService implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(BaseResponse.builder()
                .message("You have no access to this resource")
                .build());
        authException.printStackTrace();

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter().write(json);
    }
}
