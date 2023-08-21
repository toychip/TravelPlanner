package travelplanner.project.demo.global.security.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import travelplanner.project.demo.global.exception.ErrorType;
import travelplanner.project.demo.global.exception.TokenExpiredException;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, IOException {
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/oauth") || requestURI.startsWith("/login")) {
            // /oauth로 시작하는 URL은 처리 생략
            return;
        }


        if (authException instanceof TokenExpiredException) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token Expired");
        }else if(authException instanceof UsernameNotFoundException){
            ErrorType errorCode = ErrorType.CHECK_EMAIL_AGAIN;
            setResponse(response, errorCode);
        }else if(authException instanceof BadCredentialsException){
            ErrorType errorCode = ErrorType.CHECK_PASSWORD_AGAIN;
            setResponse(response, errorCode);
        }else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
    }

    // 클라이언트에게 직접 메세지를 보내기 위한 커스텀 response
    private void setResponse(HttpServletResponse response, ErrorType errorCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().println("{ \"message\" : \"" + errorCode.getMessage()
                + "\", \"code\" : \"" +  errorCode.getStatus()
                + "\"}");
    }
}

