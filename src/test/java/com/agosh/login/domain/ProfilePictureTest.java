package com.agosh.login.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.bson.types.Binary;
import org.junit.jupiter.api.Test;

import com.agosh.login.web.rest.TestUtil;

public class ProfilePictureTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfilePicture.class);
        ProfilePicture profilePicture1 = new ProfilePicture();
        profilePicture1.setId("id1");
        profilePicture1.setPictureImageContentType("gif");
        profilePicture1.setPictureName("my pic");
        profilePicture1.setUserEmail("email");
        profilePicture1.setPictureImage(new Binary("hello".getBytes()));
        ProfilePicture profilePicture2 = new ProfilePicture();
        profilePicture2.setId(profilePicture1.getId());
        assertThat(profilePicture1).isEqualTo(profilePicture2);
        profilePicture2.setId("id2");
        assertThat(profilePicture1).isNotEqualTo(profilePicture2);
        profilePicture1.setId(null);
        assertThat(profilePicture1).isNotEqualTo(profilePicture2);
    }
}
