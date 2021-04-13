package com.agosh.login.web.rest;

import com.agosh.login.LoginServiceApp;
import com.agosh.login.domain.State;
import com.agosh.login.repository.StateRepository;

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

/**
 * Integration tests for the {@link StateResource} REST controller.
 */
@SpringBootTest(classes = LoginServiceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class StateResourceIT {

    private static final Integer DEFAULT_SID = 1;
    private static final Integer UPDATED_SID = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_COUNTRY_ID = 1;
    private static final Integer UPDATED_COUNTRY_ID = 2;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private MockMvc restStateMockMvc;

    private State state;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static State createEntity() {
        State state = new State()
            .sid(DEFAULT_SID)
            .name(DEFAULT_NAME)
            .countryId(DEFAULT_COUNTRY_ID);
        return state;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static State createUpdatedEntity() {
        State state = new State()
            .sid(UPDATED_SID)
            .name(UPDATED_NAME)
            .countryId(UPDATED_COUNTRY_ID);
        return state;
    }

    @BeforeEach
    public void initTest() {
        stateRepository.deleteAll();
        state = createEntity();
    }

    @Test
    public void createState() throws Exception {
        int databaseSizeBeforeCreate = stateRepository.findAll().size();
        // Create the State
        restStateMockMvc.perform(post("/api/states")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(state)))
            .andExpect(status().isCreated());

        // Validate the State in the database
        List<State> stateList = stateRepository.findAll();
        assertThat(stateList).hasSize(databaseSizeBeforeCreate + 1);
        State testState = stateList.get(stateList.size() - 1);
        assertThat(testState.getSid()).isEqualTo(DEFAULT_SID);
        assertThat(testState.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testState.getCountryId()).isEqualTo(DEFAULT_COUNTRY_ID);
    }

    @Test
    public void createStateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stateRepository.findAll().size();

        // Create the State with an existing ID
        state.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restStateMockMvc.perform(post("/api/states")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(state)))
            .andExpect(status().isBadRequest());

        // Validate the State in the database
        List<State> stateList = stateRepository.findAll();
        assertThat(stateList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllStates() throws Exception {
        // Initialize the database
        stateRepository.save(state);

        // Get all the stateList
        restStateMockMvc.perform(get("/api/states?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(state.getId())))
            .andExpect(jsonPath("$.[*].sid").value(hasItem(DEFAULT_SID)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].countryId").value(hasItem(DEFAULT_COUNTRY_ID)));
    }
    
    @Test
    public void getState() throws Exception {
        // Initialize the database
        stateRepository.save(state);

        // Get the state
        restStateMockMvc.perform(get("/api/states/{id}", state.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(state.getId()))
            .andExpect(jsonPath("$.sid").value(DEFAULT_SID))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.countryId").value(DEFAULT_COUNTRY_ID));
    }
    @Test
    public void getNonExistingState() throws Exception {
        // Get the state
        restStateMockMvc.perform(get("/api/states/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateState() throws Exception {
        // Initialize the database
        stateRepository.save(state);

        int databaseSizeBeforeUpdate = stateRepository.findAll().size();

        // Update the state
        State updatedState = stateRepository.findById(state.getId()).get();
        updatedState
            .sid(UPDATED_SID)
            .name(UPDATED_NAME)
            .countryId(UPDATED_COUNTRY_ID);

        restStateMockMvc.perform(put("/api/states")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedState)))
            .andExpect(status().isOk());

        // Validate the State in the database
        List<State> stateList = stateRepository.findAll();
        assertThat(stateList).hasSize(databaseSizeBeforeUpdate);
        State testState = stateList.get(stateList.size() - 1);
        assertThat(testState.getSid()).isEqualTo(UPDATED_SID);
        assertThat(testState.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testState.getCountryId()).isEqualTo(UPDATED_COUNTRY_ID);
    }

    @Test
    public void updateNonExistingState() throws Exception {
        int databaseSizeBeforeUpdate = stateRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStateMockMvc.perform(put("/api/states")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(state)))
            .andExpect(status().isBadRequest());

        // Validate the State in the database
        List<State> stateList = stateRepository.findAll();
        assertThat(stateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteState() throws Exception {
        // Initialize the database
        stateRepository.save(state);

        int databaseSizeBeforeDelete = stateRepository.findAll().size();

        // Delete the state
        restStateMockMvc.perform(delete("/api/states/{id}", state.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<State> stateList = stateRepository.findAll();
        assertThat(stateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
