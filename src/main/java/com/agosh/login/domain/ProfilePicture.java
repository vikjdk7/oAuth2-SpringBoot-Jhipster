package com.agosh.login.domain;

import java.io.Serializable;
import java.time.Instant;

import javax.validation.constraints.NotNull;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A ProfilePicture.
 */
@Document(collection = "profile_picture")
public class ProfilePicture implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("user_email")

    private String userEmail;
    @NotNull
    @Field("picture_name")
    private String pictureName;

    
    @Field("picture_image")
    private Binary pictureImage;

    @Field("picture_image_content_type")
    private String pictureImageContentType;

    @Field("updated_on")
    private Instant updatedOn;

    @Field("updated_by")
    private String updatedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public ProfilePicture userEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPictureName() {
        return pictureName;
    }

    public ProfilePicture pictureName(String pictureName) {
        this.pictureName = pictureName;
        return this;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    

    public Binary getPictureImage() {
		return pictureImage;
	}

	public void setPictureImage(Binary pictureImage) {
		this.pictureImage = pictureImage;
	}

	public String getPictureImageContentType() {
        return pictureImageContentType;
    }

    public ProfilePicture pictureImageContentType(String pictureImageContentType) {
        this.pictureImageContentType = pictureImageContentType;
        return this;
    }

    public void setPictureImageContentType(String pictureImageContentType) {
        this.pictureImageContentType = pictureImageContentType;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public ProfilePicture updatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public ProfilePicture updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProfilePicture)) {
            return false;
        }
        return id != null && id.equals(((ProfilePicture) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProfilePicture{" +
            "id=" + getId() +
            ", userEmail='" + getUserEmail() + "'" +
            ", pictureName='" + getPictureName() + "'" +
            ", pictureImage='" + getPictureImage() + "'" +
            ", pictureImageContentType='" + getPictureImageContentType() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            "}";
    }
}
