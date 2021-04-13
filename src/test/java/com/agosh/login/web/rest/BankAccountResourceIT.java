package com.agosh.login.web.rest;

import com.agosh.login.LoginServiceApp;
import com.agosh.login.domain.BankAccount;
import com.agosh.login.repository.BankAccountRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BankAccountResource} REST controller.
 */
@SpringBootTest(classes = LoginServiceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BankAccountResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_USER_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_USER_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IFSC_CODE = "AAAAAAAAAA";
    private static final String UPDATED_IFSC_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_HOLDER = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_HOLDER = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final int DEFAULT_CITY_ID = 1;
    private static final int UPDATED_CITY_ID = 2;

    private static final int DEFAULT_STATE_ID = 1;
    private static final int UPDATED_STATE_ID = 2;

    private static final int DEFAULT_COUNTRY_ID = 1;
    private static final int UPDATED_COUNTRY_ID = 2;

    private static final Instant DEFAULT_UPDATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private MockMvc restBankAccountMockMvc;

    private BankAccount bankAccount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankAccount createEntity() {
        BankAccount bankAccount = new BankAccount()
            .userId(DEFAULT_USER_ID)
            .userEmail(DEFAULT_USER_EMAIL)
            .bankName(DEFAULT_BANK_NAME)
            .ifscCode(DEFAULT_IFSC_CODE)
            .branch(DEFAULT_BRANCH)
            .accountHolder(DEFAULT_ACCOUNT_HOLDER)
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .cityId(DEFAULT_CITY_ID)
            .stateId(DEFAULT_STATE_ID)
            .countryId(DEFAULT_COUNTRY_ID)
            .updatedOn(DEFAULT_UPDATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY);
        return bankAccount;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankAccount createUpdatedEntity() {
        BankAccount bankAccount = new BankAccount()
            .userId(UPDATED_USER_ID)
            .userEmail(UPDATED_USER_EMAIL)
            .bankName(UPDATED_BANK_NAME)
            .ifscCode(UPDATED_IFSC_CODE)
            .branch(UPDATED_BRANCH)
            .accountHolder(UPDATED_ACCOUNT_HOLDER)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .cityId(UPDATED_CITY_ID)
            .stateId(UPDATED_STATE_ID)
            .countryId(UPDATED_COUNTRY_ID)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);
        return bankAccount;
    }

    @BeforeEach
    public void initTest() {
        bankAccountRepository.deleteAll();
        bankAccount = createEntity();
    }

    @Test
    public void createBankAccount() throws Exception {
        int databaseSizeBeforeCreate = bankAccountRepository.findAll().size();
        // Create the BankAccount
        restBankAccountMockMvc.perform(post("/api/bank-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bankAccount)))
            .andExpect(status().isCreated());

        // Validate the BankAccount in the database
        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeCreate + 1);
        BankAccount testBankAccount = bankAccountList.get(bankAccountList.size() - 1);
        assertThat(testBankAccount.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testBankAccount.getUserEmail()).isEqualTo(DEFAULT_USER_EMAIL);
        assertThat(testBankAccount.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testBankAccount.getIfscCode()).isEqualTo(DEFAULT_IFSC_CODE);
        assertThat(testBankAccount.getBranch()).isEqualTo(DEFAULT_BRANCH);
        assertThat(testBankAccount.getAccountHolder()).isEqualTo(DEFAULT_ACCOUNT_HOLDER);
        assertThat(testBankAccount.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testBankAccount.getCityId()).isEqualTo(DEFAULT_CITY_ID);
        assertThat(testBankAccount.getStateId()).isEqualTo(DEFAULT_STATE_ID);
        assertThat(testBankAccount.getCountryId()).isEqualTo(DEFAULT_COUNTRY_ID);
        assertThat(testBankAccount.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testBankAccount.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    public void createBankAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bankAccountRepository.findAll().size();

        // Create the BankAccount with an existing ID
        bankAccount.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankAccountMockMvc.perform(post("/api/bank-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bankAccount)))
            .andExpect(status().isBadRequest());

        // Validate the BankAccount in the database
        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankAccountRepository.findAll().size();
        // set the field null
        bankAccount.setUserId(null);

        // Create the BankAccount, which fails.


        restBankAccountMockMvc.perform(post("/api/bank-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bankAccount)))
            .andExpect(status().isBadRequest());

        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkUserEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankAccountRepository.findAll().size();
        // set the field null
        bankAccount.setUserEmail(null);

        // Create the BankAccount, which fails.


        restBankAccountMockMvc.perform(post("/api/bank-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bankAccount)))
            .andExpect(status().isBadRequest());

        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkBankNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankAccountRepository.findAll().size();
        // set the field null
        bankAccount.setBankName(null);

        // Create the BankAccount, which fails.


        restBankAccountMockMvc.perform(post("/api/bank-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bankAccount)))
            .andExpect(status().isBadRequest());

        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkIfscCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankAccountRepository.findAll().size();
        // set the field null
        bankAccount.setIfscCode(null);

        // Create the BankAccount, which fails.


        restBankAccountMockMvc.perform(post("/api/bank-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bankAccount)))
            .andExpect(status().isBadRequest());

        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkBranchIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankAccountRepository.findAll().size();
        // set the field null
        bankAccount.setBranch(null);

        // Create the BankAccount, which fails.


        restBankAccountMockMvc.perform(post("/api/bank-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bankAccount)))
            .andExpect(status().isBadRequest());

        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkAccountHolderIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankAccountRepository.findAll().size();
        // set the field null
        bankAccount.setAccountHolder(null);

        // Create the BankAccount, which fails.


        restBankAccountMockMvc.perform(post("/api/bank-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bankAccount)))
            .andExpect(status().isBadRequest());

        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkAccountNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankAccountRepository.findAll().size();
        // set the field null
        bankAccount.setAccountNumber(null);

        // Create the BankAccount, which fails.


        restBankAccountMockMvc.perform(post("/api/bank-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bankAccount)))
            .andExpect(status().isBadRequest());

        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllBankAccounts() throws Exception {
        // Initialize the database
        bankAccountRepository.save(bankAccount);

        // Get all the bankAccountList
        restBankAccountMockMvc.perform(get("/api/bank-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankAccount.getId())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].userEmail").value(hasItem(DEFAULT_USER_EMAIL)))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].ifscCode").value(hasItem(DEFAULT_IFSC_CODE)))
            .andExpect(jsonPath("$.[*].branch").value(hasItem(DEFAULT_BRANCH)))
            .andExpect(jsonPath("$.[*].accountHolder").value(hasItem(DEFAULT_ACCOUNT_HOLDER)))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].cityId").value(hasItem(DEFAULT_CITY_ID)))
            .andExpect(jsonPath("$.[*].stateId").value(hasItem(DEFAULT_STATE_ID)))
            .andExpect(jsonPath("$.[*].countryId").value(hasItem(DEFAULT_COUNTRY_ID)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }
    
    @Test
    public void getBankAccount() throws Exception {
        // Initialize the database
        bankAccountRepository.save(bankAccount);

        // Get the bankAccount
        restBankAccountMockMvc.perform(get("/api/bank-accounts/{id}", bankAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bankAccount.getId()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.userEmail").value(DEFAULT_USER_EMAIL))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME))
            .andExpect(jsonPath("$.ifscCode").value(DEFAULT_IFSC_CODE))
            .andExpect(jsonPath("$.branch").value(DEFAULT_BRANCH))
            .andExpect(jsonPath("$.accountHolder").value(DEFAULT_ACCOUNT_HOLDER))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER))
            .andExpect(jsonPath("$.cityId").value(DEFAULT_CITY_ID))
            .andExpect(jsonPath("$.stateId").value(DEFAULT_STATE_ID))
            .andExpect(jsonPath("$.countryId").value(DEFAULT_COUNTRY_ID))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY));
    }
    @Test
    public void getNonExistingBankAccount() throws Exception {
        // Get the bankAccount
        restBankAccountMockMvc.perform(get("/api/bank-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateBankAccount() throws Exception {
        // Initialize the database
        bankAccountRepository.save(bankAccount);

        int databaseSizeBeforeUpdate = bankAccountRepository.findAll().size();

        // Update the bankAccount
        BankAccount updatedBankAccount = bankAccountRepository.findById(bankAccount.getId()).get();
        updatedBankAccount
            .userId(UPDATED_USER_ID)
            .userEmail(UPDATED_USER_EMAIL)
            .bankName(UPDATED_BANK_NAME)
            .ifscCode(UPDATED_IFSC_CODE)
            .branch(UPDATED_BRANCH)
            .accountHolder(UPDATED_ACCOUNT_HOLDER)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .cityId(UPDATED_CITY_ID)
            .stateId(UPDATED_STATE_ID)
            .countryId(UPDATED_COUNTRY_ID)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);

        restBankAccountMockMvc.perform(put("/api/bank-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBankAccount)))
            .andExpect(status().isOk());

        // Validate the BankAccount in the database
        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeUpdate);
        BankAccount testBankAccount = bankAccountList.get(bankAccountList.size() - 1);
        assertThat(testBankAccount.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testBankAccount.getUserEmail()).isEqualTo(UPDATED_USER_EMAIL);
        assertThat(testBankAccount.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testBankAccount.getIfscCode()).isEqualTo(UPDATED_IFSC_CODE);
        assertThat(testBankAccount.getBranch()).isEqualTo(UPDATED_BRANCH);
        assertThat(testBankAccount.getAccountHolder()).isEqualTo(UPDATED_ACCOUNT_HOLDER);
        assertThat(testBankAccount.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testBankAccount.getCityId()).isEqualTo(UPDATED_CITY_ID);
        assertThat(testBankAccount.getStateId()).isEqualTo(UPDATED_STATE_ID);
        assertThat(testBankAccount.getCountryId()).isEqualTo(UPDATED_COUNTRY_ID);
        assertThat(testBankAccount.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testBankAccount.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    public void updateNonExistingBankAccount() throws Exception {
        int databaseSizeBeforeUpdate = bankAccountRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankAccountMockMvc.perform(put("/api/bank-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bankAccount)))
            .andExpect(status().isBadRequest());

        // Validate the BankAccount in the database
        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteBankAccount() throws Exception {
        // Initialize the database
        bankAccountRepository.save(bankAccount);

        int databaseSizeBeforeDelete = bankAccountRepository.findAll().size();

        // Delete the bankAccount
        restBankAccountMockMvc.perform(delete("/api/bank-accounts/{id}", bankAccount.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
