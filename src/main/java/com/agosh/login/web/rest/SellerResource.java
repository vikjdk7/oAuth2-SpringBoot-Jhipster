package com.agosh.login.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
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

import com.agosh.login.domain.Seller;
import com.agosh.login.pojo.SellerDetails;
import com.agosh.login.repository.SellerRepository;
import com.agosh.login.service.UserService;
import com.agosh.login.service.UserServiceImpl;
import com.agosh.login.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.agosh.login.domain.Seller}.
 */
@RestController
@RequestMapping("/api")
public class SellerResource {

	private final Logger log = LoggerFactory.getLogger(SellerResource.class);

	private static final String ENTITY_NAME = "loginServiceSeller";
	
	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final SellerRepository sellerRepository;
	
	private final UserService userService;
	
	

	public SellerResource(SellerRepository sellerRepository, UserServiceImpl userService) {
		this.sellerRepository = sellerRepository;
		this.userService = userService;
	}

	/**
	 * {@code POST  /sellers} : Create a new seller.
	 *
	 * @param seller the seller to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new seller, or with status {@code 400 (Bad Request)} if the
	 *         seller has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/sellers")
	public ResponseEntity<Seller> createSeller(@Valid @RequestBody Seller seller) throws URISyntaxException {
		log.debug("REST request to save Seller : {}", seller);
		if (seller.getId() != null) {
			throw new BadRequestAlertException("A new seller cannot already have an ID", ENTITY_NAME, "idexists");
		}
		Seller result = sellerRepository.save(seller);
		return ResponseEntity.created(new URI("/api/sellers/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
				.body(result);
	}

	/**
	 * {@code PUT  /sellers} : Updates an existing seller.
	 *
	 * @param seller the seller to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated seller, or with status {@code 400 (Bad Request)} if the
	 *         seller is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the seller couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/sellers")
	public ResponseEntity<Seller> updateSeller(@Valid @RequestBody Seller seller) throws URISyntaxException {
		log.debug("REST request to update Seller : {}", seller);
		if (seller.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		Seller result = sellerRepository.save(seller);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, seller.getId()))
				.body(result);
	}

	/**
	 * {@code GET  /sellers} : get all the sellers.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of sellers in body.
	 */
	@GetMapping("/sellers")
	public List<Seller> getAllSellers() {
		log.debug("REST request to get all Sellers");
		return sellerRepository.findAll();
	}

	/**
	 * {@code GET  /sellers} : get sellers for partner
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of sellers in body.
	 */
	@GetMapping("/sellers/partner/{partnerLoginId}")
	public List<Seller> getSellersByPartner(@PathVariable String partnerLoginId) {
		log.debug("REST request to get Sellers for provided partnerId");
		return sellerRepository.findByPartnerLoginId(partnerLoginId);
	}

	/**
	 * {@code GET  /sellers} : get sellers for Admin (not assigned to any partner)
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of sellers in body.
	 */
	@GetMapping("/sellers/admin")
	public List<Seller> getSellersForAdmin() {
		log.debug("REST request to get Sellers for Admin");
		return sellerRepository.findByPartnerLoginIdIsNull();
	}

	/**
	 * {@code GET  /sellers/:id} : get the "id" seller.
	 *
	 * @param id the id of the seller to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the seller, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/sellers/{id}")
	public ResponseEntity<Seller> getSeller(@PathVariable String id) {
		log.debug("REST request to get Seller : {}", id);
		Optional<Seller> seller = sellerRepository.findById(id);
		return ResponseUtil.wrapOrNotFound(seller);
	}

	@GetMapping("/sellers/loginuserid/{loginid}")
	public ResponseEntity<Seller> getSellerByLoginUserId(@PathVariable String loginid) {
		log.debug("REST request to get Seller : {}", loginid);
		List<Seller> sellerList = sellerRepository.findByLoginId(loginid);
		Optional<Seller> sellerOpt = Optional.empty();
		if (!sellerList.isEmpty()) {
			Seller seller = sellerList.get(0);
			sellerOpt = Optional.of(seller);
		}
		return ResponseUtil.wrapOrNotFound(sellerOpt);
	}
	
	@GetMapping("/sellers/username/{email}")
	public ResponseEntity<SellerDetails> getSellerByUserName(@PathVariable String email) {
		log.debug("REST request to get Seller by User name : {}", email);
		SellerDetails sellerDetails = userService.findSellerDetailsByEmail(email);
		return ResponseUtil.wrapOrNotFound(Optional.of(sellerDetails));
	}

	/**
	 * {@code DELETE  /sellers/:id} : delete the "id" seller.
	 *
	 * @param id the id of the seller to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/sellers/{id}")
	public ResponseEntity<Void> deleteSeller(@PathVariable String id) {
		log.debug("REST request to delete Seller : {}", id);
		sellerRepository.deleteById(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
	}
}
