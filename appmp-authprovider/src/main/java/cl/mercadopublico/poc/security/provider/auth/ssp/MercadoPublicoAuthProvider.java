package cl.mercadopublico.poc.security.provider.auth.ssp;

import java.util.HashMap;

import cl.mercadopublico.poc.security.provider.auth.module.MercadoPublicoLoginModule;
import cl.mercadopublico.poc.security.provider.auth.mbeans.MercadoPublicoAuthenticatorMBean;

import javax.security.auth.login.AppConfigurationEntry;
import weblogic.management.security.ProviderMBean;
import weblogic.security.provider.PrincipalValidatorImpl;
import weblogic.security.spi.AuthenticationProviderV2;
import weblogic.security.spi.IdentityAsserterV2;
import weblogic.security.spi.PrincipalValidator;
import weblogic.security.spi.SecurityServices;

public class MercadoPublicoAuthProvider implements AuthenticationProviderV2 {

	private static final String LOGIN_MODULE_NAME = MercadoPublicoLoginModule.class.getName();

	private String description;

	private AppConfigurationEntry.LoginModuleControlFlag controlFlag;

	@Override
	public void initialize(ProviderMBean mbean, SecurityServices services) {
		System.out.println("[MercadoPublicoAuthProvider] initialize");

		MercadoPublicoAuthenticatorMBean mercadoPublicoAuthenticatorMBean = (MercadoPublicoAuthenticatorMBean) mbean;

		this.description = mercadoPublicoAuthenticatorMBean.getDescription() + "\n"
				+ mercadoPublicoAuthenticatorMBean.getVersion();

		String flag = mercadoPublicoAuthenticatorMBean.getControlFlag();

		if (flag.equalsIgnoreCase("REQUIRED")) {
			this.controlFlag = AppConfigurationEntry.LoginModuleControlFlag.REQUIRED;
		} else if (flag.equalsIgnoreCase("OPTIONAL")) {
			this.controlFlag = AppConfigurationEntry.LoginModuleControlFlag.OPTIONAL;
		} else if (flag.equalsIgnoreCase("REQUISITE")) {
			this.controlFlag = AppConfigurationEntry.LoginModuleControlFlag.REQUISITE;
		} else if (flag.equalsIgnoreCase("SUFFICIENT")) {
			this.controlFlag = AppConfigurationEntry.LoginModuleControlFlag.SUFFICIENT;
		} else {
			throw new IllegalArgumentException("invalid flag value" + flag);
		}
	}

	@Override
	public String getDescription() {
		System.out.println("[MercadoPublicoAuthProvider] getDescription");
		return this.description;
	}

	@Override
	public void shutdown() {
		System.out.println("[MercadoPublicoAuthProvider] shutdown");
	}

	private AppConfigurationEntry getConfiguration(HashMap<String, Object> options) {
		// options.put("mbean", this.mbean);
		return new AppConfigurationEntry(LOGIN_MODULE_NAME, controlFlag, options);
	}

	/**
	 * Create a JAAS AppConfigurationEntry (which tells JAAS how to create the login
	 * module and how to use it) when the authenticator is used to authenticate (vs.
	 * to complete identity assertion).
	 *
	 * @return An AppConfigurationEntry that tells JAAS how to use the
	 *         authenticator's login module for authentication.
	 */
	@Override
	public AppConfigurationEntry getLoginModuleConfiguration() {
		System.out.println("[MercadoPublicoAuthProvider] getLoginModuleConfiguration");
		HashMap<String, Object> options = new HashMap<String, Object>();
		return getConfiguration(options);
	}

	/**
	 * Create a JAAS AppConfigurationEntry (which tells JAAS how to create the login
	 * module and how to use it) when the simple sample authenticator is used to
	 * complete identity assertion (vs. to authenticate).
	 *
	 * @return An AppConfigurationEntry that tells JAAS how to use the
	 *         authenticator's login module for identity assertion.
	 */
	@Override
	public AppConfigurationEntry getAssertionModuleConfiguration() {
		System.out.println("[MercadoPublicoAuthProvider] getAssertionModuleConfiguration");
		HashMap<String, Object> options = new HashMap<String, Object>();
		options.put("IdentityAssertion", "true");
		return getConfiguration(options);
	}

	/**
	 * Return the principal validator that can validate the principals that the
	 * authenticator's login module puts into the subject.
	 *
	 * Since the authenticator uses the built in WLSUserImpl and WLSGroupImpl
	 * principal classes, just returns the built in PrincipalValidatorImpl that
	 * knows how to handle these kinds of principals.
	 *
	 * @return A PrincipalValidator that can validate the principals that the simple
	 *         sample authenticator's login module puts in the subject.
	 */
	@Override
	public PrincipalValidator getPrincipalValidator() {
		System.out.println("[MercadoPublicoAuthProvider] getPrincipalValidator");
		return new PrincipalValidatorImpl();
	}

	/**
	 * Returns this providers identity asserter object.
	 *
	 * @return null since the authenticator doesn't support identity assertion (that
	 *         is, mapping a token to a user name). Do not confuse this with using a
	 *         login module in identity assertion mode where the login module
	 *         shouldn't try to validate the user.
	 */
	@Override
	public IdentityAsserterV2 getIdentityAsserter() {
		System.out.println("[MercadoPublicoAuthProvider] getIdentityAsserter");
		return null;
	}

}
