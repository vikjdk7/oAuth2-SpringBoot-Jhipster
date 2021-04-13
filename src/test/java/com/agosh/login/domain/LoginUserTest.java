package com.agosh.login.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.agosh.login.web.rest.TestUtil;

public class LoginUserTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoginUser.class);
        LoginUser loginUser1 = new LoginUser();
        loginUser1.setId("id1");
        LoginUser loginUser2 = new LoginUser();
        loginUser2.setId(loginUser1.getId());
        assertThat(loginUser1).isEqualTo(loginUser2);
        loginUser2.setId("id2");
        assertThat(loginUser1).isNotEqualTo(loginUser2);
        loginUser1.setId(null);
        assertThat(loginUser1).isNotEqualTo(loginUser2);
    }
}
