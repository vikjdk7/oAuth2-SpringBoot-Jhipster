package com.agosh.login.web.rest;

import com.agosh.login.LoginServiceApp;
import com.agosh.login.domain.Seller;
import com.agosh.login.repository.SellerRepository;

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
 * Integration tests for the {@link SellerResource} REST controller.
 */
@SpringBootTest(classes = LoginServiceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SellerResourceIT {

    private static final String DEFAULT_LOGIN_ID = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN_ID = "BBBBBBBBBB";

    private static final String DEFAULT_BUSINESS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BUSINESS_WEBSITE = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESS_WEBSITE = "BBBBBBBBBB";

    private static final String DEFAULT_TAG_LINE = "AAAAAAAAAA";
    private static final String UPDATED_TAG_LINE = "BBBBBBBBBB";

    private static final String DEFAULT_LOGO = "AAAAAAAAAA";
    private static final String UPDATED_LOGO = "BBBBBBBBBB";

    private static final String DEFAULT_BUSINESS_LIC_NO = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESS_LIC_NO = "BBBBBBBBBB";

    private static final String DEFAULT_BUSINESS_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESS_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TAX_ID = "AAAAAAAAAA";
    private static final String UPDATED_TAX_ID = "BBBBBBBBBB";

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private MockMvc restSellerMockMvc;

    private Seller seller;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Seller createEntity() {
        Seller seller = new Seller();
//            .loginId(DEFAULT_LOGIN_ID)
//            .businessName(DEFAULT_BUSINESS_NAME)
//            .businessWebsite(DEFAULT_BUSINESS_WEBSITE)
//            .tagLine(DEFAULT_TAG_LINE)
//            .logo(DEFAULT_LOGO)
//            .businessLicNo(DEFAULT_BUSINESS_LIC_NO)
//            .businessType(DEFAULT_BUSINESS_TYPE)
//            .taxId(DEFAULT_TAX_ID);
        return seller;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Seller createUpdatedEntity() {
        Seller seller = new Seller();
//            .loginId(UPDATED_LOGIN_ID)
//            .businessName(UPDATED_BUSINESS_NAME)
//            .businessWebsite(UPDATED_BUSINESS_WEBSITE)
//            .tagLine(UPDATED_TAG_LINE)
//            .logo(UPDATED_LOGO)
//            .businessLicNo(UPDATED_BUSINESS_LIC_NO)
//            .businessType(UPDATED_BUSINESS_TYPE)
//            .taxId(UPDATED_TAX_ID);
        return seller;
    }

    @BeforeEach
    public void initTest() {
        sellerRepository.deleteAll();
        seller = createEntity();
    }

    @Test
    public void createSeller() throws Exception {
        int databaseSizeBeforeCreate = sellerRepository.findAll().size();
        // Create the Seller
        restSellerMockMvc.perform(post("/api/sellers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(seller)))
            .andExpect(status().isCreated());

        // Validate the Seller in the database
        List<Seller> sellerList = sellerRepository.findAll();
        assertThat(sellerList).hasSize(databaseSizeBeforeCreate + 1);
        Seller testSeller = sellerList.get(sellerList.size() - 1);
        assertThat(testSeller.getLoginId()).isEqualTo(DEFAULT_LOGIN_ID);
        assertThat(testSeller.getBusinessName()).isEqualTo(DEFAULT_BUSINESS_NAME);
        assertThat(testSeller.getBusinessWebsite()).isEqualTo(DEFAULT_BUSINESS_WEBSITE);
        assertThat(testSeller.getTagLine()).isEqualTo(DEFAULT_TAG_LINE);
        assertThat(testSeller.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testSeller.getBusinessLicNo()).isEqualTo(DEFAULT_BUSINESS_LIC_NO);
        assertThat(testSeller.getBusinessType()).isEqualTo(DEFAULT_BUSINESS_TYPE);
        assertThat(testSeller.getTaxId()).isEqualTo(DEFAULT_TAX_ID);
    }

    @Test
    public void createSellerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sellerRepository.findAll().size();

        // Create the Seller with an existing ID
        seller.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restSellerMockMvc.perform(post("/api/sellers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(seller)))
            .andExpect(status().isBadRequest());

        // Validate the Seller in the database
        List<Seller> sellerList = sellerRepository.findAll();
        assertThat(sellerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkBusinessNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = sellerRepository.findAll().size();
        // set the field null
        seller.setBusinessName(null);

        // Create the Seller, which fails.


        restSellerMockMvc.perform(post("/api/sellers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(seller)))
            .andExpect(status().isBadRequest());

        List<Seller> sellerList = sellerRepository.findAll();
        assertThat(sellerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkBusinessTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = sellerRepository.findAll().size();
        // set the field null
        seller.setBusinessType(null);

        // Create the Seller, which fails.


        restSellerMockMvc.perform(post("/api/sellers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(seller)))
            .andExpect(status().isBadRequest());

        List<Seller> sellerList = sellerRepository.findAll();
        assertThat(sellerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllSellers() throws Exception {
        // Initialize the database
        sellerRepository.save(seller);

        // Get all the sellerList
        restSellerMockMvc.perform(get("/api/sellers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seller.getId())))
            .andExpect(jsonPath("$.[*].loginId").value(hasItem(DEFAULT_LOGIN_ID)))
            .andExpect(jsonPath("$.[*].businessName").value(hasItem(DEFAULT_BUSINESS_NAME)))
            .andExpect(jsonPath("$.[*].businessWebsite").value(hasItem(DEFAULT_BUSINESS_WEBSITE)))
            .andExpect(jsonPath("$.[*].tagLine").value(hasItem(DEFAULT_TAG_LINE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(DEFAULT_LOGO)))
            .andExpect(jsonPath("$.[*].businessLicNo").value(hasItem(DEFAULT_BUSINESS_LIC_NO)))
            .andExpect(jsonPath("$.[*].businessType").value(hasItem(DEFAULT_BUSINESS_TYPE)))
            .andExpect(jsonPath("$.[*].taxId").value(hasItem(DEFAULT_TAX_ID)));
    }
    
    @Test
    public void getSeller() throws Exception {
        // Initialize the database
        sellerRepository.save(seller);

        // Get the seller
        restSellerMockMvc.perform(get("/api/sellers/{id}", seller.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(seller.getId()))
            .andExpect(jsonPath("$.loginId").value(DEFAULT_LOGIN_ID))
            .andExpect(jsonPath("$.businessName").value(DEFAULT_BUSINESS_NAME))
            .andExpect(jsonPath("$.businessWebsite").value(DEFAULT_BUSINESS_WEBSITE))
            .andExpect(jsonPath("$.tagLine").value(DEFAULT_TAG_LINE))
            .andExpect(jsonPath("$.logo").value(DEFAULT_LOGO))
            .andExpect(jsonPath("$.businessLicNo").value(DEFAULT_BUSINESS_LIC_NO))
            .andExpect(jsonPath("$.businessType").value(DEFAULT_BUSINESS_TYPE))
            .andExpect(jsonPath("$.taxId").value(DEFAULT_TAX_ID));
    }
    @Test
    public void getNonExistingSeller() throws Exception {
        // Get the seller
        restSellerMockMvc.perform(get("/api/sellers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateSeller() throws Exception {
        // Initialize the database
        sellerRepository.save(seller);

        int databaseSizeBeforeUpdate = sellerRepository.findAll().size();

        // Update the seller
        Seller updatedSeller = sellerRepository.findById(seller.getId()).get();
//        updatedSeller
//            .loginId(UPDATED_LOGIN_ID)
//            .businessName(UPDATED_BUSINESS_NAME)
//            .businessWebsite(UPDATED_BUSINESS_WEBSITE)
//            .tagLine(UPDATED_TAG_LINE)
//            .logo(UPDATED_LOGO)
//            .businessLicNo(UPDATED_BUSINESS_LIC_NO)
//            .businessType(UPDATED_BUSINESS_TYPE)
//            .taxId(UPDATED_TAX_ID);

        restSellerMockMvc.perform(put("/api/sellers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSeller)))
            .andExpect(status().isOk());

        // Validate the Seller in the database
        List<Seller> sellerList = sellerRepository.findAll();
        assertThat(sellerList).hasSize(databaseSizeBeforeUpdate);
        Seller testSeller = sellerList.get(sellerList.size() - 1);
        assertThat(testSeller.getLoginId()).isEqualTo(UPDATED_LOGIN_ID);
        assertThat(testSeller.getBusinessName()).isEqualTo(UPDATED_BUSINESS_NAME);
        assertThat(testSeller.getBusinessWebsite()).isEqualTo(UPDATED_BUSINESS_WEBSITE);
        assertThat(testSeller.getTagLine()).isEqualTo(UPDATED_TAG_LINE);
        assertThat(testSeller.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testSeller.getBusinessLicNo()).isEqualTo(UPDATED_BUSINESS_LIC_NO);
        assertThat(testSeller.getBusinessType()).isEqualTo(UPDATED_BUSINESS_TYPE);
        assertThat(testSeller.getTaxId()).isEqualTo(UPDATED_TAX_ID);
    }

    @Test
    public void updateNonExistingSeller() throws Exception {
        int databaseSizeBeforeUpdate = sellerRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSellerMockMvc.perform(put("/api/sellers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(seller)))
            .andExpect(status().isBadRequest());

        // Validate the Seller in the database
        List<Seller> sellerList = sellerRepository.findAll();
        assertThat(sellerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteSeller() throws Exception {
        // Initialize the database
        sellerRepository.save(seller);

        int databaseSizeBeforeDelete = sellerRepository.findAll().size();

        // Delete the seller
        restSellerMockMvc.perform(delete("/api/sellers/{id}", seller.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Seller> sellerList = sellerRepository.findAll();
        assertThat(sellerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
