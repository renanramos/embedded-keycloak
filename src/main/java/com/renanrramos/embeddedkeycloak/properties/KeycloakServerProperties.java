/**
 * 
 */
package com.renanrramos.embeddedkeycloak.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import com.renanrramos.embeddedkeycloak.model.AdminUser;

/**
 * @author renan.ramos
 *
 */
@ConfigurationProperties(prefix = "keycloak.server")
public class KeycloakServerProperties {

	@Value("${keycloak.server.context-path}")
	private String contextPath;
	
	@Value("keycloak.server.real-import-file")
	private String realmImportFile;

	@Value("keycloak.server.easy-shopping-realm-file")
	private String easyShoppingRealmFile;

	private AdminUser adminUser;

	public KeycloakServerProperties() {
		this.adminUser = new AdminUser();
	}
	
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

	public String getEasyShoppingRealmFile() {
		return easyShoppingRealmFile;
	}

	public void setEasyShoppingRealmFile(String easyShoppingRealmFile) {
		this.easyShoppingRealmFile = easyShoppingRealmFile;
	}
}
