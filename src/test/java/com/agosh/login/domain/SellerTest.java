package com.agosh.login.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.agosh.login.web.rest.TestUtil;

public class SellerTest {

	@Test
	public void equalsVerifier() throws Exception {
		TestUtil.equalsVerifier(Seller.class);
		Seller seller1 = new Seller();
		seller1.setId("id1");
		seller1.setLogo("logo");
		seller1.setDirectSeller(true);
		Seller seller2 = new Seller();
		seller2.setId(seller1.getId());
		assertThat(seller1).isEqualTo(seller2);
		seller2.setId("id2");
		assertThat(seller1).isNotEqualTo(seller2);
		seller1.setId(null);
		assertThat(seller1).isNotEqualTo(seller2);
	}
}
