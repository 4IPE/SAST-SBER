import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.SberTex.SastManager.security.jwt.JwtAuthorizationFilter;
import ru.SberTex.SastManager.security.jwt.JwtTokenProvider;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class JwtAuthorizationFilterTest {

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private JwtAuthorizationFilter jwtAuthorizationFilter;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        filterChain = mock(FilterChain.class);
    }

    @Test
    void testDoFilterInternal_withValidToken() throws ServletException, IOException {
        String token = "validToken";
        request.addHeader("Authorization", "Bearer " + token);
        when(jwtTokenProvider.resolveToken(request)).thenReturn(token);
        when(jwtTokenProvider.validateToken(token)).thenReturn(true);
        when(jwtTokenProvider.getUsername(token)).thenReturn("username");

        jwtAuthorizationFilter.doFilterInternal(request, response, filterChain);

        verify(jwtTokenProvider, times(1)).validateToken(token);
        verify(filterChain, times(1)).doFilter(request, response);
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testDoFilterInternal_withInvalidToken() throws ServletException, IOException {
        String token = "invalidToken";
        request.addHeader("Authorization", "Bearer " + token);
        when(jwtTokenProvider.resolveToken(request)).thenReturn(token);
        when(jwtTokenProvider.validateToken(token)).thenReturn(false);

        jwtAuthorizationFilter.doFilterInternal(request, response, filterChain);

        verify(jwtTokenProvider, times(1)).validateToken(token);
        verify(filterChain, times(1)).doFilter(request, response);

        // Проверка, что контекст аутентификации был очищен
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

}
