package com.agosh.login.web.rest;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agosh.login.domain.Partner;
import com.agosh.login.pojo.PartnerVO;
import com.agosh.login.repository.PartnerRepository;
import com.agosh.login.service.UserService;
import com.agosh.login.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.agosh.login.domain.Partner}.
 */
@RestController
@RequestMapping("/api")
public class PartnerResource {

	private final Logger log = LoggerFactory.getLogger(PartnerResource.class);

	private static final String ENTITY_NAME = "loginServicePartner";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final PartnerRepository partnerRepository;

	private final UserService userService;

	public PartnerResource(PartnerRepository partnerRepository, UserService userService) {
		this.partnerRepository = partnerRepository;
		this.userService = userService;
	}

	/**
	 * {@code PUT  /partners} : Updates an existing partner.
	 *
	 * @param partner the partner to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated partner, or with status {@code 400 (Bad Request)} if the
	 *         partner is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the partner couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/partnersterritory")
	public ResponseEntity<Partner> updatePartnerByLoginId(@RequestBody PartnerVO partner) throws URISyntaxException {
		log.debug("REST request to update Partner's territory : {}", partner);
		if (partner.getLoginId() == null) {
			throw new BadRequestAlertException("Invalid Login Id", ENTITY_NAME, "idnull");
		}

		Partner result = userService.updatePartner(partner.getLoginId(), partner.getCityId(), partner.getStateId(),
				partner.getCountryId());
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId()))
				.body(result);
	}

	/**
	 * {@code GET  /partners} : get all the partners.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of partners in body.
	 */
	@GetMapping("/partners")
	public List<Partner> getAllPartners() {
		log.debug("REST request to get all Partners");
		return partnerRepository.findAll();
	}

	/**
	 * {@code GET  /partners/:id} : get the "id" partner.
	 *
	 * @param id the id of the partner to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the partner, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/partners/{id}")
	public ResponseEntity<Partner> getPartner(@PathVariable String id) {
		log.debug("REST request to get Partner : {}", id);
		Optional<Partner> partner = partnerRepository.findById(id);
		return ResponseUtil.wrapOrNotFound(partner);
	}

	@GetMapping("/partners/loginuserid/{loginid}")
	public ResponseEntity<Partner> getPartnerByLoginUserId(@PathVariable String loginid) {
		log.debug("REST request to get Partner : {}", loginid);
		Optional<Partner> partnerOpt = partnerRepository.findByLoginId(loginid);
		return ResponseUtil.wrapOrNotFound(partnerOpt);
	}

	/**
	 * {@code DELETE  /partners/:id} : delete the "id" partner.
	 *
	 * @param id the id of the partner to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/partners/{id}")
	public ResponseEntity<Void> deletePartner(@PathVariable String id) {
		log.debug("REST request to delete Partner : {}", id);
		partnerRepository.deleteById(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
	}
}
