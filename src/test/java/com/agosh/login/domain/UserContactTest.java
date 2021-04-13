package com.agosh.login.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;

import com.agosh.login.web.rest.TestUtil;

public class UserContactTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserContact.class);
        UserContact userContact1 = new UserContact();
        userContact1.setId("id1");
        userContact1.setAddress1("address1");
        userContact1.setAddress2("address2");
        userContact1.setCityId(1);
        userContact1.setContactEmail("email");
        userContact1.setLandmark("landmark");
        userContact1.setLoginUserEmail("loginUserEmail");
        userContact1.setName("name");
        userContact1.setPhone("phone");
        userContact1.setRelation("relation");
        userContact1.setStateId(1);
        userContact1.setCountryId(1);
        userContact1.setUpdatedBy("updatedBy");
        userContact1.setUpdatedOn(Instant.now());
        userContact1.setZipcode("54353");
        
        UserContact userContact2 = new UserContact();
        userContact2.setId(userContact1.getId());
        assertThat(userContact1).isEqualTo(userContact2);
        userContact2.setId("id2");
        assertThat(userContact1).isNotEqualTo(userContact2);
        userContact1.setId(null);
        assertThat(userContact1).isNotEqualTo(userContact2);
    }
}
