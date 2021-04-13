package com.agosh.login.web.rest;

import com.agosh.login.LoginServiceApp;
import com.agosh.login.domain.Country;
import com.agosh.login.repository.CountryRepository;

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
 * Integration tests for the {@link CountryResource} REST controller.
 */
@SpringBootTest(classes = LoginServiceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CountryResourceIT {

    private static final Integer DEFAULT_CID = 1;
    private static final Integer UPDATED_CID = 2;

    private static final String DEFAULT_SORTNAME = "AAAAAAAAAA";
    private static final String UPDATED_SORTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_PHONE_CODE = 1;
    private static final Integer UPDATED_PHONE_CODE = 2;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private MockMvc restCountryMockMvc;

    private Country country;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Country createEntity() {
        Country country = new Country()
            .cid(DEFAULT_CID)
            .sortname(DEFAULT_SORTNAME)
            .name(DEFAULT_NAME)
            .phoneCode(DEFAULT_PHONE_CODE);
        return country;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Country createUpdatedEntity() {
        Country country = new Country()
            .cid(UPDATED_CID)
            .sortname(UPDATED_SORTNAME)
            .name(UPDATED_NAME)
            .phoneCode(UPDATED_PHONE_CODE);
        return country;
    }

    @BeforeEach
    public void initTest() {
        countryRepository.deleteAll();
        country = createEntity();
    }

    @Test
    public void createCountry() throws Exception {
        int databaseSizeBeforeCreate = countryRepository.findAll().size();
        // Create the Country
        restCountryMockMvc.perform(post("/api/countries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(country)))
            .andExpect(status().isCreated());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeCreate + 1);
        Country testCountry = countryList.get(countryList.size() - 1);
        assertThat(testCountry.getCid()).isEqualTo(DEFAULT_CID);
        assertThat(testCountry.getSortname()).isEqualTo(DEFAULT_SORTNAME);
        assertThat(testCountry.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCountry.getPhoneCode()).isEqualTo(DEFAULT_PHONE_CODE);
    }

    @Test
    public void createCountryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = countryRepository.findAll().size();

        // Create the Country with an existing ID
        country.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restCountryMockMvc.perform(post("/api/countries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(country)))
            .andExpect(status().isBadRequest());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllCountries() throws Exception {
        // Initialize the database
        countryRepository.save(country);

        // Get all the countryList
        restCountryMockMvc.perform(get("/api/countries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(country.getId())))
            .andExpect(jsonPath("$.[*].cid").value(hasItem(DEFAULT_CID)))
            .andExpect(jsonPath("$.[*].sortname").value(hasItem(DEFAULT_SORTNAME)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].phoneCode").value(hasItem(DEFAULT_PHONE_CODE)));
    }
    
    @Test
    public void getCountry() throws Exception {
        // Initialize the database
        countryRepository.save(country);

        // Get the country
        restCountryMockMvc.perform(get("/api/countries/{id}", country.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(country.getId()))
            .andExpect(jsonPath("$.cid").value(DEFAULT_CID))
            .andExpect(jsonPath("$.sortname").value(DEFAULT_SORTNAME))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.phoneCode").value(DEFAULT_PHONE_CODE));
    }
    @Test
    public void getNonExistingCountry() throws Exception {
        // Get the country
        restCountryMockMvc.perform(get("/api/countries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateCountry() throws Exception {
        // Initialize the database
        countryRepository.save(country);

        int databaseSizeBeforeUpdate = countryRepository.findAll().size();

        // Update the country
        Country updatedCountry = countryRepository.findById(country.getId()).get();
        updatedCountry
            .cid(UPDATED_CID)
            .sortname(UPDATED_SORTNAME)
            .name(UPDATED_NAME)
            .phoneCode(UPDATED_PHONE_CODE);

        restCountryMockMvc.perform(put("/api/countries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCountry)))
            .andExpect(status().isOk());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeUpdate);
        Country testCountry = countryList.get(countryList.size() - 1);
        assertThat(testCountry.getCid()).isEqualTo(UPDATED_CID);
        assertThat(testCountry.getSortname()).isEqualTo(UPDATED_SORTNAME);
        assertThat(testCountry.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCountry.getPhoneCode()).isEqualTo(UPDATED_PHONE_CODE);
    }

    @Test
    public void updateNonExistingCountry() throws Exception {
        int databaseSizeBeforeUpdate = countryRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCountryMockMvc.perform(put("/api/countries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(country)))
            .andExpect(status().isBadRequest());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteCountry() throws Exception {
        // Initialize the database
        countryRepository.save(country);

        int databaseSizeBeforeDelete = countryRepository.findAll().size();

        // Delete the country
        restCountryMockMvc.perform(delete("/api/countries/{id}", country.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
