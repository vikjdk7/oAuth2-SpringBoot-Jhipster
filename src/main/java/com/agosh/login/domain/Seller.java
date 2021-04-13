package com.agosh.login.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Seller.
 */
@Document(collection = "seller")
public class Seller implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;

    @Field("login_id")
    private String loginId;
    
    @Field("user_name")
    private String userName;
    
    
    @Field("partner_login_id")
    private String partnerLoginId;

    @NotNull
    @Field("business_name")
    private String businessName;

    @Field("business_website")
    private String businessWebsite;

    @Field("tag_line")
    private String tagLine;

    @Field("logo")
    private String logo;

    @Field("business_lic_no")
    private String businessLicNo;

    @NotNull
    @Field("business_type")
    private String businessType;

    @Field("tax_id")
    private String taxId;
    
    @Field("nos_locations")
    private int nosLocations;
    
    @Field("is_direct_seller")
    boolean isDirectSeller;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPartnerLoginId() {
		return partnerLoginId;
	}

	public void setPartnerLoginId(String partnerLoginId) {
		this.partnerLoginId = partnerLoginId;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getBusinessWebsite() {
		return businessWebsite;
	}

	public void setBusinessWebsite(String businessWebsite) {
		this.businessWebsite = businessWebsite;
	}

	public String getTagLine() {
		return tagLine;
	}

	public void setTagLine(String tagLine) {
		this.tagLine = tagLine;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getBusinessLicNo() {
		return businessLicNo;
	}

	public void setBusinessLicNo(String businessLicNo) {
		this.businessLicNo = businessLicNo;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getTaxId() {
		return taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	public int getNosLocations() {
		return nosLocations;
	}

	public void setNosLocations(int nosLocations) {
		this.nosLocations = nosLocations;
	}

	public boolean isDirectSeller() {
		return isDirectSeller;
	}

	public void setDirectSeller(boolean isDirectSeller) {
		this.isDirectSeller = isDirectSeller;
	}

	@Override
	public int hashCode() {
		return 31;
	}
	
	 @Override
	    public boolean equals(Object o) {
	        if (this == o) {
	            return true;
	        }
	        if (!(o instanceof Seller)) {
	            return false;
	        }
	        return id != null && id.equals(((Seller) o).id);
	    }
	 
	 

	 
}
