/**
 *
 */
package com.renanrramos.embeddedkeycloak.properties.model;

import com.renanrramos.embeddedkeycloak.properties.base.model.BaseUser;

/**
 * @author renan.ramos
 *
 */
public interface UserInterface<T extends BaseUser> {
	public T getInstance(String userType);
}
