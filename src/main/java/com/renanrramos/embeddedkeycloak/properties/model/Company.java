/**
 *
 */
package com.renanrramos.embeddedkeycloak.properties.model;

import com.renanrramos.embeddedkeycloak.properties.base.model.BaseUser;

/**
 * @author renan.ramos
 *
 */
public class Company extends BaseUser {

	private static final String COMPANY_USER_TYPE = "company";

	private String type;

	public Company() {
		this.type = COMPANY_USER_TYPE;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
