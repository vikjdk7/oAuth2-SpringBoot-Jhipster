package com.agosh.login.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agosh.login.domain.Country;
import com.agosh.login.repository.CountryRepository;
import com.agosh.login.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.agosh.login.domain.Country}.
 */
@RestController
@RequestMapping("/unauth")
public class CountryResource {

    private final Logger log = LoggerFactory.getLogger(CountryResource.class);

    private static final String ENTITY_NAME = "loginServiceCountry";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CountryRepository countryRepository;

    public CountryResource(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    /**
     * {@code POST  /countries} : Create a new country.
     *
     * @param country the country to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new country, or with status {@code 400 (Bad Request)} if the country has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/countries")
    public ResponseEntity<Country> createCountry(@RequestBody Country country) throws URISyntaxException {
        log.debug("REST request to save Country : {}", country);
        if (country.getId() != null) {
            throw new BadRequestAlertException("A new country cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Country result = countryRepository.save(country);
        return ResponseEntity.created(new URI("/api/countries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /countries} : Updates an existing country.
     *
     * @param country the country to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated country,
     * or with status {@code 400 (Bad Request)} if the country is not valid,
     * or with status {@code 500 (Internal Server Error)} if the country couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/countries")
    public ResponseEntity<Country> updateCountry(@RequestBody Country country) throws URISyntaxException {
        log.debug("REST request to update Country : {}", country);
        if (country.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Country result = countryRepository.save(country);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, country.getId()))
            .body(result);
    }

    /**
     * {@code GET  /countries} : get all the countries.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of countries in body.
     */
    @GetMapping("/countries")
    public List<Country> getAllCountries() {
        log.debug("REST request to get all Countries");
        return countryRepository.findAll();
    }

    /**
     * {@code GET  /countries/:id} : get the "id" country.
     *
     * @param id the id of the country to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the country, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/countries/{id}")
    public ResponseEntity<Country> getCountry(@PathVariable String id) {
        log.debug("REST request to get Country : {}", id);
        Optional<Country> country = countryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(country);
    }
    
    /**
     * {@code GET  /countries/:id} : get the country by country id
     *
     * @param id the id of the country to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the country, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/countries/countryid/{countryId}")
    public ResponseEntity<Country> getCountryId(@PathVariable Integer countryId) {
        log.debug("REST request to get Country : {}", countryId);
        Optional<Country> country = countryRepository.findByCid(countryId);
        return ResponseUtil.wrapOrNotFound(country);
    }

    /**
     * {@code DELETE  /countries/:id} : delete the "id" country.
     *
     * @param id the id of the country to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/countries/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable String id) {
        log.debug("REST request to delete Country : {}", id);
        countryRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
