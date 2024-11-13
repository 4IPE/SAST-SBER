import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import ru.SberTex.SastManager.security.jwt.JwtTokenProvider;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;


    @BeforeEach
    public void setUp() {
        String secretKey = "jl1929MyfUeRgmRVHx07Qg/nicNEeRf5JJJF+s46mcA=";
        jwtTokenProvider = new JwtTokenProvider(secretKey);
    }

    @Test
    void testCreateAndValidateToken() {
        String username = "testUser";
        String token = jwtTokenProvider.createToken(username);

        assertNotNull(token);
        assertTrue(jwtTokenProvider.validateToken(token));
    }

    @Test
    void testGetUsernameFromToken() {
        String username = "testUser";
        String token = jwtTokenProvider.createToken(username);

        String extractedUsername = jwtTokenProvider.getUsername(token);
        assertEquals(username, extractedUsername);
    }

    @Test
    void testResolveToken() {
        String token = "Bearer validToken";
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", token);

        String resolvedToken = jwtTokenProvider.resolveToken(request);
        assertEquals("validToken", resolvedToken);
    }

    @Test
    void testValidateInvalidToken() {
        String invalidToken = "invalidToken";

        assertFalse(jwtTokenProvider.validateToken(invalidToken));
    }
}
