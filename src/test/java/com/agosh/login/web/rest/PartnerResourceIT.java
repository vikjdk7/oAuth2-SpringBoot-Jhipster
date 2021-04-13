package com.agosh.login.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.agosh.login.LoginServiceApp;
import com.agosh.login.domain.Partner;
import com.agosh.login.repository.PartnerRepository;

/**
 * Integration tests for the {@link PartnerResource} REST controller.
 */
@SpringBootTest(classes = LoginServiceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PartnerResourceIT {

	private static final String DEFAULT_LOGIN_ID = "AAAAAAAAAA";
	private static final String UPDATED_LOGIN_ID = "BBBBBBBBBB";

	@Autowired
	private PartnerRepository partnerRepository;

	@Autowired
	private MockMvc restPartnerMockMvc;

	private Partner partner;

	/**
	 * Create an entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static Partner createEntity() {
		Partner partner = new Partner().loginId(DEFAULT_LOGIN_ID);
		return partner;
	}

	/**
	 * Create an updated entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static Partner createUpdatedEntity() {
		Partner partner = new Partner().loginId(UPDATED_LOGIN_ID);
		return partner;
	}

	@BeforeEach
	public void initTest() {
		partnerRepository.deleteAll();
		partner = createEntity();
	}

	@Test
	public void createPartner() throws Exception {
		int databaseSizeBeforeCreate = partnerRepository.findAll().size();
		// Create the Partner
		restPartnerMockMvc.perform(post("/api/partners").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(partner))).andExpect(status().isCreated());

		// Validate the Partner in the database
		List<Partner> partnerList = partnerRepository.findAll();
		assertThat(partnerList).hasSize(databaseSizeBeforeCreate + 1);
		Partner testPartner = partnerList.get(partnerList.size() - 1);
		assertThat(testPartner.getLoginId()).isEqualTo(DEFAULT_LOGIN_ID);
	}

	@Test
	public void createPartnerWithExistingId() throws Exception {
		int databaseSizeBeforeCreate = partnerRepository.findAll().size();

		// Create the Partner with an existing ID
		partner.setId("existing_id");

		// An entity with an existing ID cannot be created, so this API call must fail
		restPartnerMockMvc.perform(post("/api/partners").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(partner))).andExpect(status().isBadRequest());

		// Validate the Partner in the database
		List<Partner> partnerList = partnerRepository.findAll();
		assertThat(partnerList).hasSize(databaseSizeBeforeCreate);
	}

	@Test
	public void getAllPartners() throws Exception {
		// Initialize the database
		partnerRepository.save(partner);

		// Get all the partnerList
		restPartnerMockMvc.perform(get("/api/partners?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(partner.getId())))
				.andExpect(jsonPath("$.[*].loginId").value(hasItem(DEFAULT_LOGIN_ID)));

	}

	@Test
	public void getPartner() throws Exception {
		// Initialize the database
		partnerRepository.save(partner);

		// Get the partner
		restPartnerMockMvc.perform(get("/api/partners/{id}", partner.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id").value(partner.getId()))
				.andExpect(jsonPath("$.loginId").value(DEFAULT_LOGIN_ID));

	}

	@Test
	public void getNonExistingPartner() throws Exception {
		// Get the partner
		restPartnerMockMvc.perform(get("/api/partners/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	@Test
	public void updatePartner() throws Exception {
		// Initialize the database
		partnerRepository.save(partner);

		int databaseSizeBeforeUpdate = partnerRepository.findAll().size();

		// Update the partner
		Partner updatedPartner = partnerRepository.findById(partner.getId()).get();
		updatedPartner.loginId(UPDATED_LOGIN_ID);

		restPartnerMockMvc.perform(put("/api/partners").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(updatedPartner))).andExpect(status().isOk());

		// Validate the Partner in the database
		List<Partner> partnerList = partnerRepository.findAll();
		assertThat(partnerList).hasSize(databaseSizeBeforeUpdate);
		Partner testPartner = partnerList.get(partnerList.size() - 1);
		assertThat(testPartner.getLoginId()).isEqualTo(UPDATED_LOGIN_ID);

	}

	@Test
	public void updateNonExistingPartner() throws Exception {
		int databaseSizeBeforeUpdate = partnerRepository.findAll().size();

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restPartnerMockMvc.perform(put("/api/partners").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(partner))).andExpect(status().isBadRequest());

		// Validate the Partner in the database
		List<Partner> partnerList = partnerRepository.findAll();
		assertThat(partnerList).hasSize(databaseSizeBeforeUpdate);
	}

	@Test
	public void deletePartner() throws Exception {
		// Initialize the database
		partnerRepository.save(partner);

		int databaseSizeBeforeDelete = partnerRepository.findAll().size();

		// Delete the partner
		restPartnerMockMvc.perform(delete("/api/partners/{id}", partner.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		// Validate the database contains one less item
		List<Partner> partnerList = partnerRepository.findAll();
		assertThat(partnerList).hasSize(databaseSizeBeforeDelete - 1);
	}
}
