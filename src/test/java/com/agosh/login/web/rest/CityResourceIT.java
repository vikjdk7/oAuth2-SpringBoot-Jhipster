package com.agosh.login.web.rest;

import com.agosh.login.LoginServiceApp;
import com.agosh.login.domain.City;
import com.agosh.login.repository.CityRepository;

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
 * Integration tests for the {@link CityResource} REST controller.
 */
@SpringBootTest(classes = LoginServiceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CityResourceIT {

    private static final Integer DEFAULT_CTID = 1;
    private static final Integer UPDATED_CTID = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_STATE_ID = 1;
    private static final Integer UPDATED_STATE_ID = 2;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private MockMvc restCityMockMvc;

    private City city;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static City createEntity() {
        City city = new City()
            .ctid(DEFAULT_CTID)
            .name(DEFAULT_NAME)
            .stateId(DEFAULT_STATE_ID);
        return city;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static City createUpdatedEntity() {
        City city = new City()
            .ctid(UPDATED_CTID)
            .name(UPDATED_NAME)
            .stateId(UPDATED_STATE_ID);
        return city;
    }

    @BeforeEach
    public void initTest() {
        cityRepository.deleteAll();
        city = createEntity();
    }

    @Test
    public void createCity() throws Exception {
        int databaseSizeBeforeCreate = cityRepository.findAll().size();
        // Create the City
        restCityMockMvc.perform(post("/api/cities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(city)))
            .andExpect(status().isCreated());

        // Validate the City in the database
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeCreate + 1);
        City testCity = cityList.get(cityList.size() - 1);
        assertThat(testCity.getCtid()).isEqualTo(DEFAULT_CTID);
        assertThat(testCity.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCity.getStateId()).isEqualTo(DEFAULT_STATE_ID);
    }

    @Test
    public void createCityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cityRepository.findAll().size();

        // Create the City with an existing ID
        city.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restCityMockMvc.perform(post("/api/cities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(city)))
            .andExpect(status().isBadRequest());

        // Validate the City in the database
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllCities() throws Exception {
        // Initialize the database
        cityRepository.save(city);

        // Get all the cityList
        restCityMockMvc.perform(get("/api/cities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(city.getId())))
            .andExpect(jsonPath("$.[*].ctid").value(hasItem(DEFAULT_CTID)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].stateId").value(hasItem(DEFAULT_STATE_ID)));
    }
    
    @Test
    public void getCity() throws Exception {
        // Initialize the database
        cityRepository.save(city);

        // Get the city
        restCityMockMvc.perform(get("/api/cities/{id}", city.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(city.getId()))
            .andExpect(jsonPath("$.ctid").value(DEFAULT_CTID))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.stateId").value(DEFAULT_STATE_ID));
    }
    @Test
    public void getNonExistingCity() throws Exception {
        // Get the city
        restCityMockMvc.perform(get("/api/cities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateCity() throws Exception {
        // Initialize the database
        cityRepository.save(city);

        int databaseSizeBeforeUpdate = cityRepository.findAll().size();

        // Update the city
        City updatedCity = cityRepository.findById(city.getId()).get();
        updatedCity
            .ctid(UPDATED_CTID)
            .name(UPDATED_NAME)
            .stateId(UPDATED_STATE_ID);

        restCityMockMvc.perform(put("/api/cities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCity)))
            .andExpect(status().isOk());

        // Validate the City in the database
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeUpdate);
        City testCity = cityList.get(cityList.size() - 1);
        assertThat(testCity.getCtid()).isEqualTo(UPDATED_CTID);
        assertThat(testCity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCity.getStateId()).isEqualTo(UPDATED_STATE_ID);
    }

    @Test
    public void updateNonExistingCity() throws Exception {
        int databaseSizeBeforeUpdate = cityRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCityMockMvc.perform(put("/api/cities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(city)))
            .andExpect(status().isBadRequest());

        // Validate the City in the database
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteCity() throws Exception {
        // Initialize the database
        cityRepository.save(city);

        int databaseSizeBeforeDelete = cityRepository.findAll().size();

        // Delete the city
        restCityMockMvc.perform(delete("/api/cities/{id}", city.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
