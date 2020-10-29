/**
 *
 */
package com.renanrramos.embeddedkeycloak.properties.base.model;

import static com.renanrramos.embeddedkeycloak.util.KeycloakPropertyConstants.EMAIL_PROPERTY;
import static com.renanrramos.embeddedkeycloak.util.KeycloakPropertyConstants.FIRST_NAME_PROPERTY;
import static com.renanrramos.embeddedkeycloak.util.KeycloakPropertyConstants.LAST_NAME_PROPERTY;
import static com.renanrramos.embeddedkeycloak.util.KeycloakPropertyConstants.PASSWORD_PROPERTY;
import static com.renanrramos.embeddedkeycloak.util.KeycloakPropertyConstants.ROLE_PROPERTY;
import static com.renanrramos.embeddedkeycloak.util.KeycloakPropertyConstants.USERNAME_PROPERTY;

import com.renanrramos.embeddedkeycloak.properties.model.UserInterface;
import com.renanrramos.embeddedkeycloak.util.PropertiesLoader;

/**
 * @author renan.ramos
 * @param <T>
 *
 */
public class BaseUser implements UserInterface<BaseUser> {

	private String firstName;

	private String lastName;

	private String username;

	private String password;

	private String email;

	private String role;

	private String type;

	public BaseUser() {
		// Intentionally empty
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public String getRole() {
		return role;
	}

	public BaseUser withFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public BaseUser withLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public BaseUser withUsername(String username) {
		this.username = username;
		return this;
	}

	public BaseUser withPassword(String password) {
		this.password = password;
		return this;
	}

	public BaseUser withEmail(String email) {
		this.email = email;
		return this;
	}

	public BaseUser withRole(String role) {
		this.role = role;
		return this;
	}

	public BaseUser withType(String type) {
		this.type = type;
		return this;
	}

	@Override
	public BaseUser getInstance(String userType) {
		return new BaseUser().withEmail(PropertiesLoader.getUserProperty(EMAIL_PROPERTY + userType))
				.withFirstName(PropertiesLoader.getUserProperty(FIRST_NAME_PROPERTY + userType))
				.withLastName(PropertiesLoader.getUserProperty(LAST_NAME_PROPERTY + userType))
				.withPassword(PropertiesLoader.getUserProperty(PASSWORD_PROPERTY + userType))
				.withRole(PropertiesLoader.getUserProperty(ROLE_PROPERTY + userType))
				.withUsername(PropertiesLoader.getUserProperty(USERNAME_PROPERTY + userType));
	}
}
