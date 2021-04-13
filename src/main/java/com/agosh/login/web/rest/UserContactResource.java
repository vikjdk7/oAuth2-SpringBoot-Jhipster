package com.agosh.login.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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

import com.agosh.login.domain.UserContact;
import com.agosh.login.pojo.UserContactVO;
import com.agosh.login.repository.UserContactRepository;
import com.agosh.login.service.UserService;
import com.agosh.login.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.agosh.login.domain.UserContact}.
 */
@RestController
@RequestMapping("/api")
public class UserContactResource {

    private final Logger log = LoggerFactory.getLogger(UserContactResource.class);

    private static final String ENTITY_NAME = "loginServiceUserContact";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserContactRepository userContactRepository;
    
    private final UserService userService;
    

    public UserContactResource(UserContactRepository userContactRepository, UserService userService) {
        this.userContactRepository = userContactRepository;
        this.userService = userService;
    }

    /**
     * {@code POST  /user-contacts} : Create a new userContact.
     *
     * @param userContact the userContact to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userContact, or with status {@code 400 (Bad Request)} if the userContact has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-contacts")
    public ResponseEntity<UserContact> createUserContact(@Valid @RequestBody UserContact userContact) throws URISyntaxException {
        log.debug("REST request to save UserContact : {}", userContact);
        if (userContact.getId() != null) {
            throw new BadRequestAlertException("A new userContact cannot already have an ID", ENTITY_NAME, "idexists");
        }
        userContact.setUpdatedBy(userContact.getLoginUserEmail());
        userContact.setUpdatedOn(Instant.now());
        UserContact result = userContactRepository.save(userContact);
        return ResponseEntity.created(new URI("/api/user-contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /user-contacts} : Updates an existing userContact.
     *
     * @param userContact the userContact to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userContact,
     * or with status {@code 400 (Bad Request)} if the userContact is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userContact couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-contacts")
    public ResponseEntity<UserContact> updateUserContact(@Valid @RequestBody UserContact userContact) throws URISyntaxException {
        log.debug("REST request to update UserContact : {}", userContact);
        if (userContact.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserContact result = userContactRepository.save(userContact);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userContact.getId()))
            .body(result);
    }

    /**
     * {@code GET  /user-contacts} : get all the userContacts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userContacts in body.
     */
    @GetMapping("/user-contacts")
    public List<UserContact> getAllUserContacts() {
        log.debug("REST request to get all UserContacts");
        return userContactRepository.findAll();
    }

    /**
     * {@code GET  /user-contacts/:id} : get the "id" userContact.
     *
     * @param id the id of the userContact to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userContact, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-contacts/{id}")
    public ResponseEntity<UserContact> getUserContact(@PathVariable String id) {
        log.debug("REST request to get UserContact : {}", id);
        Optional<UserContact> userContact = userContactRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(userContact);
    }
    
    /**
     * {@code GET  /user-contacts/:email} : get the userContacts by user's email
     *
     * @param id the id of the userContact to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userContact, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-contacts/email/{email}")
    public List<UserContactVO> getUserContactsByEmail(@PathVariable String email) {
        log.debug("REST request to get UserContact : {}", email);
        return userService.getUserContactsByEmail(email);
        
    }

    /**
     * {@code DELETE  /user-contacts/:id} : delete the "id" userContact.
     *
     * @param id the id of the userContact to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-contacts/{id}")
    public ResponseEntity<Void> deleteUserContact(@PathVariable String id) {
        log.debug("REST request to delete UserContact : {}", id);
        userContactRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
