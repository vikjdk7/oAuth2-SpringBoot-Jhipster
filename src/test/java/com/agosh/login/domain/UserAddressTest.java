package com.agosh.login.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.agosh.login.domain.enumeration.AddressType;
import com.agosh.login.web.rest.TestUtil;

public class UserAddressTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAddress.class);
        UserAddress userAddress1 = new UserAddress();
        userAddress1.setId("id1");
        userAddress1.setAddressType(AddressType.PRIMARY);
        UserAddress userAddress2 = new UserAddress();
        userAddress2.setId(userAddress1.getId());
        userAddress2.setAddressType(AddressType.SECONDARY);
        assertThat(userAddress1).isEqualTo(userAddress2);
        userAddress2.setId("id2");
        assertThat(userAddress1).isNotEqualTo(userAddress2);
        userAddress1.setId(null);
        assertThat(userAddress1).isNotEqualTo(userAddress2);
    }
}
