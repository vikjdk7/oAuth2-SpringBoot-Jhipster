package com.agosh.login.web.rest;

import com.agosh.login.LoginServiceApp;
import com.agosh.login.domain.LoginUser;
import com.agosh.login.repository.LoginUserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.agosh.login.domain.enumeration.AuthProvider;
import com.agosh.login.domain.enumeration.UserType;
import com.agosh.login.domain.enumeration.UserStatus;
/**
 * Integration tests for the {@link LoginUserResource} REST controller.
 */
@SpringBootTest(classes = LoginServiceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class LoginUserResourceIT {

    private static final String DEFAULT_LOGIN_ID = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EMAIL_VERIFIED = false;
    private static final Boolean UPDATED_EMAIL_VERIFIED = true;

    private static final AuthProvider DEFAULT_PROVIDER = AuthProvider.local;
    private static final AuthProvider UPDATED_PROVIDER = AuthProvider.facebook;

    private static final String DEFAULT_PROVIDER_ID = "AAAAAAAAAA";
    private static final String UPDATED_PROVIDER_ID = "BBBBBBBBBB";

    private static final UserType DEFAULT_USER_TYPE = UserType.ADMIN;
    private static final UserType UPDATED_USER_TYPE = UserType.PARTNER;

    private static final UserStatus DEFAULT_USER_STATUS = UserStatus.ACTIVE;
    private static final UserStatus UPDATED_USER_STATUS = UserStatus.INACTIVE;

    @Autowired
    private LoginUserRepository loginUserRepository;

    @Autowired
    private MockMvc restLoginUserMockMvc;

    private LoginUser loginUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoginUser createEntity() {
        LoginUser loginUser = new LoginUser()
            .loginId(DEFAULT_LOGIN_ID)
            .password(DEFAULT_PASSWORD)
            .name(DEFAULT_NAME)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .middleName(DEFAULT_MIDDLE_NAME)
            .phone(DEFAULT_PHONE)
            .email(DEFAULT_EMAIL)
            .imageUrl(DEFAULT_IMAGE_URL)
            .emailVerified(DEFAULT_EMAIL_VERIFIED)
            .provider(DEFAULT_PROVIDER)
            .providerId(DEFAULT_PROVIDER_ID)
            .userType(DEFAULT_USER_TYPE)
            .userStatus(DEFAULT_USER_STATUS);
        return loginUser;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoginUser createUpdatedEntity() {
        LoginUser loginUser = new LoginUser()
            .loginId(UPDATED_LOGIN_ID)
            .password(UPDATED_PASSWORD)
            .name(UPDATED_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .imageUrl(UPDATED_IMAGE_URL)
            .emailVerified(UPDATED_EMAIL_VERIFIED)
            .provider(UPDATED_PROVIDER)
            .providerId(UPDATED_PROVIDER_ID)
            .userType(UPDATED_USER_TYPE)
            .userStatus(UPDATED_USER_STATUS);
        return loginUser;
    }

    @BeforeEach
    public void initTest() {
        loginUserRepository.deleteAll();
        loginUser = createEntity();
    }

    @Test
    public void createLoginUser() throws Exception {
        int databaseSizeBeforeCreate = loginUserRepository.findAll().size();
        // Create the LoginUser
        restLoginUserMockMvc.perform(post("/api/login-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(loginUser)))
            .andExpect(status().isCreated());

        // Validate the LoginUser in the database
        List<LoginUser> loginUserList = loginUserRepository.findAll();
        assertThat(loginUserList).hasSize(databaseSizeBeforeCreate + 1);
        LoginUser testLoginUser = loginUserList.get(loginUserList.size() - 1);
        assertThat(testLoginUser.getLoginId()).isEqualTo(DEFAULT_LOGIN_ID);
        assertThat(testLoginUser.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testLoginUser.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLoginUser.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testLoginUser.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testLoginUser.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testLoginUser.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testLoginUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testLoginUser.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testLoginUser.isEmailVerified()).isEqualTo(DEFAULT_EMAIL_VERIFIED);
        assertThat(testLoginUser.getProvider()).isEqualTo(DEFAULT_PROVIDER);
        assertThat(testLoginUser.getProviderId()).isEqualTo(DEFAULT_PROVIDER_ID);
        assertThat(testLoginUser.getUserType()).isEqualTo(DEFAULT_USER_TYPE);
        assertThat(testLoginUser.getUserStatus()).isEqualTo(DEFAULT_USER_STATUS);
    }

    @Test
    public void createLoginUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = loginUserRepository.findAll().size();

        // Create the LoginUser with an existing ID
        loginUser.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restLoginUserMockMvc.perform(post("/api/login-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(loginUser)))
            .andExpect(status().isBadRequest());

        // Validate the LoginUser in the database
        List<LoginUser> loginUserList = loginUserRepository.findAll();
        assertThat(loginUserList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = loginUserRepository.findAll().size();
        // set the field null
        loginUser.setName(null);

        // Create the LoginUser, which fails.


        restLoginUserMockMvc.perform(post("/api/login-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(loginUser)))
            .andExpect(status().isBadRequest());

        List<LoginUser> loginUserList = loginUserRepository.findAll();
        assertThat(loginUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = loginUserRepository.findAll().size();
        // set the field null
        loginUser.setEmail(null);

        // Create the LoginUser, which fails.


        restLoginUserMockMvc.perform(post("/api/login-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(loginUser)))
            .andExpect(status().isBadRequest());

        List<LoginUser> loginUserList = loginUserRepository.findAll();
        assertThat(loginUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllLoginUsers() throws Exception {
        // Initialize the database
        loginUserRepository.save(loginUser);

        // Get all the loginUserList
        restLoginUserMockMvc.perform(get("/api/login-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loginUser.getId())))
            .andExpect(jsonPath("$.[*].loginId").value(hasItem(DEFAULT_LOGIN_ID)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].emailVerified").value(hasItem(DEFAULT_EMAIL_VERIFIED.booleanValue())))
            .andExpect(jsonPath("$.[*].provider").value(hasItem(DEFAULT_PROVIDER.toString())))
            .andExpect(jsonPath("$.[*].providerId").value(hasItem(DEFAULT_PROVIDER_ID)))
            .andExpect(jsonPath("$.[*].userType").value(hasItem(DEFAULT_USER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].userStatus").value(hasItem(DEFAULT_USER_STATUS.toString())));
    }
    
    @Test
    public void getLoginUser() throws Exception {
        // Initialize the database
        loginUserRepository.save(loginUser);

        // Get the loginUser
        restLoginUserMockMvc.perform(get("/api/login-users/{id}", loginUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(loginUser.getId()))
            .andExpect(jsonPath("$.loginId").value(DEFAULT_LOGIN_ID))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL))
            .andExpect(jsonPath("$.emailVerified").value(DEFAULT_EMAIL_VERIFIED.booleanValue()))
            .andExpect(jsonPath("$.provider").value(DEFAULT_PROVIDER.toString()))
            .andExpect(jsonPath("$.providerId").value(DEFAULT_PROVIDER_ID))
            .andExpect(jsonPath("$.userType").value(DEFAULT_USER_TYPE.toString()))
            .andExpect(jsonPath("$.userStatus").value(DEFAULT_USER_STATUS.toString()));
    }
    @Test
    public void getNonExistingLoginUser() throws Exception {
        // Get the loginUser
        restLoginUserMockMvc.perform(get("/api/login-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateLoginUser() throws Exception {
        // Initialize the database
        loginUserRepository.save(loginUser);

        int databaseSizeBeforeUpdate = loginUserRepository.findAll().size();

        // Update the loginUser
        LoginUser updatedLoginUser = loginUserRepository.findById(loginUser.getId()).get();
        updatedLoginUser
            .loginId(UPDATED_LOGIN_ID)
            .password(UPDATED_PASSWORD)
            .name(UPDATED_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .imageUrl(UPDATED_IMAGE_URL)
            .emailVerified(UPDATED_EMAIL_VERIFIED)
            .provider(UPDATED_PROVIDER)
            .providerId(UPDATED_PROVIDER_ID)
            .userType(UPDATED_USER_TYPE)
            .userStatus(UPDATED_USER_STATUS);

        restLoginUserMockMvc.perform(put("/api/login-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedLoginUser)))
            .andExpect(status().isOk());

        // Validate the LoginUser in the database
        List<LoginUser> loginUserList = loginUserRepository.findAll();
        assertThat(loginUserList).hasSize(databaseSizeBeforeUpdate);
        LoginUser testLoginUser = loginUserList.get(loginUserList.size() - 1);
        assertThat(testLoginUser.getLoginId()).isEqualTo(UPDATED_LOGIN_ID);
        assertThat(testLoginUser.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testLoginUser.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLoginUser.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testLoginUser.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testLoginUser.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testLoginUser.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testLoginUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testLoginUser.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testLoginUser.isEmailVerified()).isEqualTo(UPDATED_EMAIL_VERIFIED);
        assertThat(testLoginUser.getProvider()).isEqualTo(UPDATED_PROVIDER);
        assertThat(testLoginUser.getProviderId()).isEqualTo(UPDATED_PROVIDER_ID);
        assertThat(testLoginUser.getUserType()).isEqualTo(UPDATED_USER_TYPE);
        assertThat(testLoginUser.getUserStatus()).isEqualTo(UPDATED_USER_STATUS);
    }

    @Test
    public void updateNonExistingLoginUser() throws Exception {
        int databaseSizeBeforeUpdate = loginUserRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoginUserMockMvc.perform(put("/api/login-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(loginUser)))
            .andExpect(status().isBadRequest());

        // Validate the LoginUser in the database
        List<LoginUser> loginUserList = loginUserRepository.findAll();
        assertThat(loginUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteLoginUser() throws Exception {
        // Initialize the database
        loginUserRepository.save(loginUser);

        int databaseSizeBeforeDelete = loginUserRepository.findAll().size();

        // Delete the loginUser
        restLoginUserMockMvc.perform(delete("/api/login-users/{id}", loginUser.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LoginUser> loginUserList = loginUserRepository.findAll();
        assertThat(loginUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
