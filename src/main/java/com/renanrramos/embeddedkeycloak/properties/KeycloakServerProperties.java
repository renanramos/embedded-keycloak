/**
 * 
 */
package com.renanrramos.embeddedkeycloak.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author renan.ramos
 *
 */
@ConfigurationProperties(prefix = "keycloak.server")
public class KeycloakServerProperties {

	String contextPath = "/auth";
	String realmImportFile = "realm.json";
	AdminUser adminUser = new AdminUser();

	public String getContextPath() {
		return contextPath;
	}

	public String getRealmImportFile() {
		return realmImportFile;
	}

	public AdminUser getAdminUser() {
		return adminUser;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public void setRealmImportFile(String realmImportFile) {
		this.realmImportFile = realmImportFile;
	}

	public void setAdminUser(AdminUser adminUser) {
		this.adminUser = adminUser;
	}

	public static class AdminUser {
		String username = "admin@mail.com";
		String password = "admin";

		public String getUsername() {
			return username;
		}

		public String getPassword() {
			return password;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}
}
