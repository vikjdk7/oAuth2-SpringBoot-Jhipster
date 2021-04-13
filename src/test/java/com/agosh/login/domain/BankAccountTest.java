package com.agosh.login.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;

import com.agosh.login.web.rest.TestUtil;

public class BankAccountTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankAccount.class);
        BankAccount bankAccount1 = new BankAccount();
        bankAccount1.setId("id1");
        bankAccount1.setAccountHolder("accountHolder");
        bankAccount1.setAccountNumber("accountNumber");
        bankAccount1.setBankName("bankName");
        bankAccount1.setBranch("branch");
        bankAccount1.setCityId(1);
        bankAccount1.setCountryId(1);
        bankAccount1.setIfscCode("ifscCode");
        bankAccount1.setStateId(1);
        bankAccount1.setUpdatedBy("updatedBy");
        bankAccount1.setUpdatedOn(Instant.now());
        bankAccount1.setUserEmail("email");
        bankAccount1.setUserId("userId");
        BankAccount bankAccount2 = new BankAccount();
        bankAccount2.setId(bankAccount1.getId());
        assertThat(bankAccount1).isEqualTo(bankAccount2);
        bankAccount2.setId("id2");
        assertThat(bankAccount1).isNotEqualTo(bankAccount2);
        bankAccount1.setId(null);
        assertThat(bankAccount1).isNotEqualTo(bankAccount2);
    }
}
