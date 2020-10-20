/**
 * 
 */
package com.renanrramos.embeddedkeycloak.config;

import java.util.NoSuchElementException;

import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.services.managers.ApplianceBootstrap;
import org.keycloak.services.managers.RealmManager;
import org.keycloak.services.resources.KeycloakApplication;
import org.keycloak.services.util.JsonConfigProviderFactory;
import org.keycloak.util.JsonSerialization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.renanrramos.embeddedkeycloak.model.AdminUser;
import com.renanrramos.embeddedkeycloak.properties.KeycloakServerProperties;

/**
 * @author renan.ramos
 *
 */
public class EmbeddedKeycloakApp extends KeycloakApplication {
	private static final Logger LOG = LoggerFactory.getLogger(EmbeddedKeycloakApp.class);

	static KeycloakServerProperties keycloakServerProperties;
	
	@Override
	protected void loadConfig() {
		JsonConfigProviderFactory factory = new RegularJsonConfigProviderFactory();
		Config.init(factory.create().orElseThrow(() -> new NoSuchElementException("No value present")));
	}

	public EmbeddedKeycloakApp() {
		super();
		createMasterRealmAdminUser();
		createEasyShoppingRealm();
	}

	private void createMasterRealmAdminUser() {
		KeycloakSession session = getSessionFactory().create();
		ApplianceBootstrap applianceBootstrap = new ApplianceBootstrap(session);
		AdminUser admin = keycloakServerProperties.getAdminUser();
		
		try {
			session.getTransactionManager().begin();
			applianceBootstrap.createMasterRealmUser(admin.getUsername(), admin.getPassword());
			session.getTransactionManager().commit();			
		} catch (Exception e) {
			LOG.warn("Couldn't create keycloak master admin user: {}", e.getMessage());
			session.getTransactionManager().rollback();
		}
		session.close();
	}

	private void createEasyShoppingRealm() {
		KeycloakSession session = getSessionFactory().create();
		try {
			session.getTransactionManager().begin();
			RealmManager manager = new RealmManager(session);
			Resource realmImportFile = new ClassPathResource(keycloakServerProperties.getEasyShoppingRealmFile());
			manager.importRealm(JsonSerialization.readValue(realmImportFile.getInputStream(), RealmRepresentation.class));
			session.getTransactionManager().commit();
		}catch (Exception e) {
			LOG.warn("Failed to import Realm json file: {}", e.getMessage());
			session.getTransactionManager().rollback();
		}
		session.close();
	}
}
