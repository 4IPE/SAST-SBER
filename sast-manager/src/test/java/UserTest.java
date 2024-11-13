

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.SberTex.SastManager.enumeration.RoleName;
import ru.SberTex.SastManager.model.Project;
import ru.SberTex.SastManager.model.Role;
import ru.SberTex.SastManager.model.User;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .username("testUser")
                .password("password123")
                .roles(new HashSet<>())
                .projects(new HashSet<>())
                .build();
    }

    @Test
    void testUsername() {
        assertEquals("testUser", user.getUsername(), "Username should be 'testUser'");
        user.setUsername("newUser");
        assertEquals("newUser", user.getUsername(), "Username should be updated to 'newUser'");
    }

    @Test
    void testPassword() {
        assertEquals("password123", user.getPassword(), "Password should be 'password123'");
        user.setPassword("newPassword");
        assertEquals("newPassword", user.getPassword(), "Password should be updated to 'newPassword'");
    }

    @Test
    void testRoles() {
        Role role = new Role();
        role.setRole(RoleName.ROLE_USER);
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        user.setRoles(roles);
        assertTrue(user.getRoles().contains(role), "User should have role 'ROLE_USER'");
    }

    @Test
    void testProjects() {
        Project project = new Project();
        project.setName("Test Project");
        Set<Project> projects = new HashSet<>();
        projects.add(project);

        user.setProjects(projects);
        assertTrue(user.getProjects().contains(project), "User should be associated with 'Test Project'");
    }

    @Test
    void testIsAccountNonExpired() {
        assertTrue(user.isAccountNonExpired(), "Account should be non-expired by default");
    }

    @Test
    void testIsAccountNonLocked() {
        assertTrue(user.isAccountNonLocked(), "Account should be non-locked by default");
    }

    @Test
    void testIsCredentialsNonExpired() {
        assertTrue(user.isCredentialsNonExpired(), "Credentials should be non-expired by default");
    }

    @Test
    void testIsEnabled() {
        assertTrue(user.isEnabled(), "User should be enabled by default");
    }

    @Test
    void testGetAuthorities() {
        assertNotNull(user.getAuthorities(), "Authorities should not be null");
        assertTrue(user.getAuthorities().isEmpty(), "Authorities should be empty initially");
    }
}
