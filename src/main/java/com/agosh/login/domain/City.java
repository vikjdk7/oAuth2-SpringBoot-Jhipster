package com.agosh.login.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * A City.
 */
@Document(collection = "city")
public class City implements Serializable {

    private static final long serialVersionUID = 1L;

    
    @Id
    private String id;

    @Field("ctid")
    private Integer ctid;

    @Field("name")
    private String name;

    @Field("state_id")
    private Integer stateId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCtid() {
        return ctid;
    }

    public City ctid(Integer ctid) {
        this.ctid = ctid;
        return this;
    }

    public void setCtid(Integer ctid) {
        this.ctid = ctid;
    }

    public String getName() {
        return name;
    }

    public City name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStateId() {
        return stateId;
    }

    public City stateId(Integer stateId) {
        this.stateId = stateId;
        return this;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof City)) {
            return false;
        }
        return id != null && id.equals(((City) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "City{" +
            "id=" + getId() +
            ", ctid=" + getCtid() +
            ", name='" + getName() + "'" +
            ", stateId=" + getStateId() +
            "}";
    }
}
