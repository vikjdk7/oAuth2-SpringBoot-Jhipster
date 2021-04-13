package com.agosh.login.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.agosh.login.web.rest.TestUtil;

public class CountryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Country.class);
        Country country1 = new Country();
        country1.setId("id1");
        country1.setName("INDIA");
        country1.setPhoneCode(91);
        country1.setSortname("IN");
        Country country2 = new Country();
        country2.setId(country1.getId());
        assertThat(country1).isEqualTo(country2);
        country2.setId("id2");
        assertThat(country1).isNotEqualTo(country2);
        country1.setId(null);
        assertThat(country1).isNotEqualTo(country2);
    }
}
