package com.agosh.login.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * A State.
 */
@Document(collection = "state")
public class State implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("sid")
    private Integer sid;

    @Field("name")
    private String name;

    @Field("country_id")
    private Integer countryId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSid() {
        return sid;
    }

    public State sid(Integer sid) {
        this.sid = sid;
        return this;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public State name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public State countryId(Integer countryId) {
        this.countryId = countryId;
        return this;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof State)) {
            return false;
        }
        return id != null && id.equals(((State) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "State{" +
            "id=" + getId() +
            ", sid=" + getSid() +
            ", name='" + getName() + "'" +
            ", countryId=" + getCountryId() +
            "}";
    }
}
