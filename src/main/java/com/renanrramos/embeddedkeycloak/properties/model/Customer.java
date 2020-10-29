/**
 *
 */
package com.renanrramos.embeddedkeycloak.properties.model;

import com.renanrramos.embeddedkeycloak.properties.base.model.BaseUser;

/**
 * @author renan.ramos
 *
 */
public class Customer extends BaseUser {

	private static final String CUSTOMER_USER_TYPE = "customer";

	private String type;

	public Customer() {
		this.type = CUSTOMER_USER_TYPE;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
