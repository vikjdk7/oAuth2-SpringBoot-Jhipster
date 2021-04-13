package com.agosh.login.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * A Country.
 */
@Document(collection = "country")
public class Country implements Serializable {

	
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("cid")
    private Integer cid;

    @Field("sortname")
    private String sortname;

    @Field("name")
    private String name;

    @Field("phoneCode")
    private Integer phoneCode;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCid() {
        return cid;
    }

    public Country cid(Integer cid) {
        this.cid = cid;
        return this;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getSortname() {
        return sortname;
    }

    public Country sortname(String sortname) {
        this.sortname = sortname;
        return this;
    }

    public void setSortname(String sortname) {
        this.sortname = sortname;
    }

    public String getName() {
        return name;
    }

    public Country name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPhoneCode() {
        return phoneCode;
    }

    public Country phoneCode(Integer phoneCode) {
        this.phoneCode = phoneCode;
        return this;
    }

    public void setPhoneCode(Integer phoneCode) {
        this.phoneCode = phoneCode;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Country)) {
            return false;
        }
        return id != null && id.equals(((Country) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Country{" +
            "id=" + getId() +
            ", cid=" + getCid() +
            ", sortname='" + getSortname() + "'" +
            ", name='" + getName() + "'" +
            ", phoneCode=" + getPhoneCode() +
            "}";
    }
}
