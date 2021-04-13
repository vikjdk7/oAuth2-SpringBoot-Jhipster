package com.agosh.login.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.agosh.login.web.rest.TestUtil;

public class StateTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(State.class);
        State state1 = new State();
        state1.setId("id1");
        state1.setCountryId(1);
        state1.setName("Gujarat");
        state1.setSid(1);
        State state2 = new State();
        state2.setId(state1.getId());
        assertThat(state1).isEqualTo(state2);
        state2.setId("id2");
        assertThat(state1).isNotEqualTo(state2);
        state1.setId(null);
        assertThat(state1).isNotEqualTo(state2);
    }
}
