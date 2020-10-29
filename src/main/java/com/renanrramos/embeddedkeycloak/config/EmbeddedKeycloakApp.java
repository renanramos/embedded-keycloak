/**
 *
 */
package com.renanrramos.embeddedkeycloak.config;

import java.util.NoSuchElementException;

import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RoleModel;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserModel;
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
import com.renanrramos.embeddedkeycloak.properties.base.model.BaseUser;
import com.renanrramos.embeddedkeycloak.properties.model.Administrator;

/**
 * @author renan.ramos
 *
 */
public class EmbeddedKeycloakApp extends KeycloakApplication {
	private static final Logger LOG = LoggerFactory.getLogger(EmbeddedKeycloakApp.class);

	static KeycloakServerProperties keycloakServerProperties;

	private static final String EASY_SHOPPING_REALM = "easy-shopping-realm.json";

	private static final String ADMINISTRATOR_USER_TYPE = "administrator";
	private static final String COMPANY_USER_TYPE = "company";
	private static final String CUSTOMER_USER_TYPE = "customer";

	@Override
	protected void loadConfig() {
		JsonConfigProviderFactory factory = new RegularJsonConfigProviderFactory();
		Config.init(factory.create().orElseThrow(() -> new NoSuchElementException("No value present")));
	}

	public EmbeddedKeycloakApp() {
		super();
		createMasterRealmAdminUser();
		createEasyShoppingRealm();
		createEasyShoppingAdminUser();
		createEasyShoppingCompanyUser();
		createEasyShoppingCustomerUser();
	}

	private void createEasyShoppingAdminUser() {
		BaseUser user = new Administrator().getInstance(ADMINISTRATOR_USER_TYPE);
		createEasyShoppingUser(user);
	}

	private void createEasyShoppingCompanyUser() {
		BaseUser user = new Administrator().getInstance(COMPANY_USER_TYPE);
		createEasyShoppingUser(user);
	}

	private void createEasyShoppingCustomerUser() {
		BaseUser user = new Administrator().getInstance(CUSTOMER_USER_TYPE);
		createEasyShoppingUser(user);
	}

	private void createEasyShoppingUser(BaseUser user) {

		KeycloakSession session = getSessionFactory().create();

		session.getTransactionManager().begin();
		String easyShoppingRealm = "easy-shopping";

		RealmModel realm = session.realms().getRealm(easyShoppingRealm);
		session.getContext().setRealm(realm);

		if (session.users().getUsersCount(realm) > 0) {
			throw new IllegalStateException("Can't create initial user as users already exists");
		}

		UserModel easyShoppingUser = session.users().addUser(realm, user.getUsername());
		easyShoppingUser.setEnabled(true);
		easyShoppingUser.setEmail(user.getEmail());
		easyShoppingUser.setFirstName(user.getFirstName());
		easyShoppingUser.setLastName(user.getLastName());
		easyShoppingUser.setEmailVerified(true);
		easyShoppingUser.setUsername(user.getUsername());

		UserCredentialModel usrCredModel = UserCredentialModel.password(user.getPassword());
		session.userCredentialManager().updateCredential(realm, easyShoppingUser, usrCredModel);

		RoleModel adminRole = realm.getRole(user.getRole());
		easyShoppingUser.grantRole(adminRole);
		session.getTransactionManager().commit();
		session.close();
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
		LOG.info("Creating Easy Shopping Realm");
		KeycloakSession session = getSessionFactory().create();
		try {
			session.getTransactionManager().begin();
			RealmManager manager = new RealmManager(session);
			Resource realmFile = new ClassPathResource(EASY_SHOPPING_REALM);
			LOG.info("Path: {}", realmFile.getFilename());
			manager.importRealm(JsonSerialization.readValue(realmFile.getInputStream(), RealmRepresentation.class));
			session.getTransactionManager().commit();
		}catch (Exception e) {
			LOG.warn("Failed to import Realm json file: {}", e.getMessage());
			session.getTransactionManager().rollback();
		}
		session.close();
		LOG.info("Easy Shopping Realm created");
	}
}