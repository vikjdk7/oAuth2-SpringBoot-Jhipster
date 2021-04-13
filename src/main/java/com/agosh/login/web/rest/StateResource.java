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
import com.agosh.login.domain.State;
import com.agosh.login.repository.StateRepository;
import com.agosh.login.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.agosh.login.domain.State}.
 */
@RestController
@RequestMapping("/unauth")
public class StateResource {

    private final Logger log = LoggerFactory.getLogger(StateResource.class);

    private static final String ENTITY_NAME = "loginServiceState";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StateRepository stateRepository;
  
    public StateResource(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    /**
     * {@code POST  /states} : Create a new state.
     *
     * @param state the state to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new state, or with status {@code 400 (Bad Request)} if the state has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/states")
    public ResponseEntity<State> createState(@RequestBody State state) throws URISyntaxException {
        log.debug("REST request to save State : {}", state);
        if (state.getId() != null) {
            throw new BadRequestAlertException("A new state cannot already have an ID", ENTITY_NAME, "idexists");
        }
        State result = stateRepository.save(state);
        return ResponseEntity.created(new URI("/api/states/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /states} : Updates an existing state.
     *
     * @param state the state to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated state,
     * or with status {@code 400 (Bad Request)} if the state is not valid,
     * or with status {@code 500 (Internal Server Error)} if the state couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/states")
    public ResponseEntity<State> updateState(@RequestBody State state) throws URISyntaxException {
        log.debug("REST request to update State : {}", state);
        if (state.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        State result = stateRepository.save(state);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, state.getId()))
            .body(result);
    }

    /**
     * {@code GET  /states} : get all the states.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of states in body.
     */
    @GetMapping("/states")
    public List<State> getAllStates() {
        log.debug("REST request to get all States");
        return stateRepository.findAll();
    }

    /**
     * {@code GET  /states/:id} : get the "id" state.
     *
     * @param id the id of the state to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the state, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/states/{id}")
    public ResponseEntity<State> getState(@PathVariable String id) {
        log.debug("REST request to get State : {}", id);
        Optional<State> state = stateRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(state);
    }
    
    /**
     * {@code GET  /states/:id} : get the "id" state.
     *
     * @param id the id of the state to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the state, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/states/state/{stateId}")
    public ResponseEntity<State> getStateId(@PathVariable Integer stateId) {
        log.debug("REST request to get State by stateId: {}", stateId);
        Optional<State> state = stateRepository.findBySid(stateId);
        return ResponseUtil.wrapOrNotFound(state);
    }
    
    /**
     * {@code GET  /states/:id} : get the states by country
     *
     * @param id the id of the state to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of states in body.
     */
    @GetMapping("/states/country/{countryId}")
    public List<State> getStateByCountry(@PathVariable int countryId) {
        log.debug("REST request to get State : {}", countryId);
        return stateRepository.findByCountryId(countryId);
    }
    
    
    /**
     * {@code DELETE  /states/:id} : delete the "id" state.
     *
     * @param id the id of the state to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/states/{id}")
    public ResponseEntity<Void> deleteState(@PathVariable String id) {
        log.debug("REST request to delete State : {}", id);
        stateRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
