package cl.mercadopublico.poc.security.provider.auth.module;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Vector;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.spi.LoginModule;
import cl.mercadopublico.poc.oracle.proxy.auth.control.AuthentControl;
import cl.mercadopublico.poc.oracle.proxy.auth.message.request.LoginRequest;
import cl.mercadopublico.poc.oracle.proxy.auth.message.response.LoginResponse;
import weblogic.security.principal.WLSAbstractPrincipal;
import weblogic.security.principal.WLSUserImpl;

public class MercadoPublicoLoginModule implements LoginModule {

	/**
	 * The subject for this login
	 */
	private Subject subject;

	/**
	 * Where to get user names, passwords, ... for this login
	 */
	private CallbackHandler callbackHandler;

	/**
	 * Determine whether this is a login or assert identity
	 */
	private boolean isIdentityAssertion;

	/**
	 * Authentication status
	 */
	private boolean loginSucceeded;

	/**
	 * Did we add principals to the subject?
	 */
	private boolean principalsInSubject;

	/**
	 * if so, what principals did we add to the subject (so we can remove the
	 * principals we added if the login is aborted)
	 */
	private Vector<WLSAbstractPrincipal> principalsForSubject = new Vector<WLSAbstractPrincipal>();

	/**
	 * Initialize a login attempt.
	 *
	 * @param subject
	 *            the Subject this login attempt will populate.
	 *
	 * @param callbackHandler
	 *            the CallbackHandler that can be used to get the user name, and in
	 *            authentication mode, the user's password
	 *
	 * @param sharedState
	 *            A Map containing data shared between login modules when there are
	 *            multiple authenticators configured. This simple sample does not
	 *            use this parameter.
	 *
	 * @param options
	 *            A Map containing options that the authenticator's authentication
	 *            provider impl wants to pass to its login module impl. For example,
	 *            it can be used to pass in configuration data (where is the
	 *            database holding user and group info) and to pass in whether the
	 *            login module is used for authentication or to complete identity
	 *            assertion. The SimpleSampleAuthenticationProviderImpl adds an
	 *            option named "database". The value is a DatabaseAuthenticatorUtil
	 *            object. It gives the login module access to the user and group
	 *            definitions. When the authenticator is being used in identity
	 *            assertion mode, the SimpleSampleAuthenticationProviderImpl also
	 *            adds an option named "IdentityAssertion". It indicates that the
	 *            login module should only verify that the user exists (vs. checking
	 *            the password too). If this option is not specified (or is set to
	 *            false), then the login module checks the user's password too (that
	 *            is, it assumes authentication mode).
	 */
	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState,
			Map<String, ?> options) {
		// only called (once!) after the constructor and before login
		System.out.println("[MercadoPublicoLoginModulel] initialize");
		this.subject = subject;
		this.callbackHandler = callbackHandler;
		// Check for Identity Assertion option
		this.isIdentityAssertion = "true".equalsIgnoreCase((String) options.get("IdentityAssertion"));
	}

	/**
	 * Attempt to login.
	 *
	 * If we're in authentication mode, extract the user name and password from the
	 * callback handler. If the user exists and the password matches, then populate
	 * the subject with the user and the user's group. Otherwise, the login fails.
	 *
	 * If we're in identity assertion mode, extract the user name (only) from the
	 * callback handler. If the user exists, then populate the subject with the user
	 * and the user's groups. Otherwise, the login fails.
	 *
	 * @return A boolean indicating whether or not the login for this login module
	 *         succeeded.
	 */
	@Override
	public boolean login() throws LoginException {
		// only called (once!) after initialize
		System.out.println("[MercadoPublicoLoginModule] login");
		// loginSucceeded should be false
		// principalsInSubject should be false
		String password;
		LoginRequest request;
		LoginResponse response;

		Callback[] callbacks = getCallbacks();
		String userName = getUserName(callbacks);

		if (userName.length() > 0) {
			password = getPassword(userName, callbacks);
			System.out.println("[MercadoPublicoLoginModule] process authentication.. userName " + userName
					+ ", password " + password);
			request = new LoginRequest(userName, password);
			response = AuthentControl.getInstance().login(request);

			if (!("200".equalsIgnoreCase(response.getEstado().getCodigo()))) {
				throwFailedLoginException("Authentication Failed:  " + response.getEstado().getMensaje());
			}
		} else {
			throwFailedLoginException("Authentication Failed: User is empty ");
		}
		loginSucceeded = true;
		this.principalsForSubject.add(new WLSUserImpl(userName));

		return this.loginSucceeded;
	}

	/**
	 * Completes the login by adding the user and the user's groups to the subject.
	 *
	 * @return A boolean indicating whether or not the commit succeeded.
	 */
	@Override
	public boolean commit() throws LoginException {
		// only called (once!) after login
		// loginSucceeded should be true or false
		// principalsInSubject should be false
		// user should be null if !loginSucceeded, null or not-null otherwise
		// group should be null if user == null, null or not-null otherwise

		System.out.println("[MercadoPublicoLoginModule] commit");
		if (loginSucceeded) {
			subject.getPrincipals().addAll(this.principalsForSubject);
			principalsInSubject = true;
			printPrincipals();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Aborts the login attempt. Remove any principals we put into the subject
	 * during the commit method from the subject.
	 *
	 * @return A boolean indicating whether or not the abort succeeded.
	 */
	@Override
	public boolean abort() throws LoginException {
		// The abort method is called to abort the authentication process. This is
		// phase 2 of authentication when phase 1 fails. It is called if the
		// LoginContext's overall authentication failed.
		// loginSucceeded should be true or false
		// user should be null if !loginSucceeded, otherwise null or not-null
		// group should be null if user == null, otherwise null or not-null
		// principalsInSubject should be false if user is null, otherwise true
		// or false

		System.out.println("[MercadoPublicoLoginModule] abort");
		if (this.principalsInSubject) {
			subject.getPrincipals().removeAll(this.principalsForSubject);
			principalsInSubject = false;
		}
		return true;
	}

	/**
	 * Logout. This should never be called.
	 *
	 * @return A boolean indicating whether or not the logout succeeded.
	 */
	@Override
	public boolean logout() throws LoginException {
		// should never be called
		System.out.println("[MercadoPublicoLoginModule] logout");
		return true;
	}

	private void printPrincipals() {
		System.out.println("[MercadoPublicoLoginModule] printPrincipals");
		Set<Principal> localSet = this.subject.getPrincipals();
		for (Principal localPrincipal : localSet) {
			System.out.println("[MercadoPublicoLoginModule] Principal: " + localPrincipal.getName());
		}
	}

	/**
	 * Throw an invalid login exception.
	 *
	 * @param msg
	 *            A String containing the text of the LoginException.
	 *
	 * @throws LoginException
	 */
	private void throwLoginException(String msg) throws LoginException {
		System.out.println("[MercadoPublicoLoginModule] Throwing LoginException(" + msg + ")");
		throw new LoginException(msg);
	}

	/**
	 * Throws a failed login excception.
	 *
	 * @param msg
	 *            A String containing the text of the FailedLoginException.
	 *
	 * @throws LoginException
	 */
	private void throwFailedLoginException(String msg) throws FailedLoginException {
		System.out.println("[MercadoPublicoLoginModule] Throwing FailedLoginException(" + msg + ")");
		throw new FailedLoginException(msg);
	}

	/**
	 * Get the list of callbacks needed by the login module.
	 *
	 * @return The array of Callback objects by the login module. Returns one for
	 *         the user name and password if in authentication mode. Returns one for
	 *         the user name if in identity assertion mode.
	 */
	private Callback[] getCallbacks() throws LoginException {
		Callback[] callbacks = null;

		if (Optional.ofNullable(callbackHandler).isPresent()) {
			if (this.isIdentityAssertion) {
				callbacks = new Callback[1]; // need one for the user name
			} else {
				callbacks = new Callback[2]; // need one for the user name and one for the password
				callbacks[1] = new PasswordCallback("password: ", false); // add in the password callback
			}
			callbacks[0] = new NameCallback("username: "); // add in the user name callback
			try {
				callbackHandler.handle(callbacks);
			} catch (IOException e) {
				throwLoginException(e.toString());
			} catch (UnsupportedCallbackException e) {
				throwLoginException(e.toString() + " " + e.getCallback().toString());
			}
		} else {
			throwLoginException("No CallbackHandler Specified");
		}
		return callbacks;
	}

	/**
	 * Get the user name from the callbacks (that the callback handler has already
	 * handed the user name to).
	 *
	 * @param callbacks
	 *            The array of Callback objects used by this login module. The first
	 *            in the list must be the user name callback object.
	 *
	 * @return A String containing the user name (from the user name callback
	 *         object)
	 */
	private String getUserName(Callback[] callbacks) throws LoginException {
		String userName = ((NameCallback) callbacks[0]).getName();
		if (Optional.ofNullable(userName).isPresent()) {
			System.out.println("[MercadoPublicoLoginModule] userName= " + userName);
		} else {
			throwLoginException("Username not supplied.");
		}
		return userName;
	}

	/**
	 * Get the password from the callbacks (that the callback handler has already
	 * handed the password to) - that is, the password from the login attempt. Must
	 * only be used for authentication mode, not for identity assertion mode.
	 *
	 * @param userName
	 *            A String containing the name of the user (already retrieved from
	 *            the callbacks). Only passed in so that we can print a better error
	 *            message if the password is bogus.
	 *
	 * @param callbacks
	 *            The array of Callback objects used by this login module. The
	 *            second in the list must be the password callback object.
	 *
	 * @return A String containing the password from the login attempt
	 *
	 * @throws LoginException
	 *             if no password was supplied in the login attempt.
	 */
	private String getPassword(String userName, Callback[] callbacks) throws LoginException {
		PasswordCallback passwordCallback = (PasswordCallback) callbacks[1];
		char[] password = passwordCallback.getPassword();
		passwordCallback.clearPassword();
		String passwd = null;

		if (Optional.ofNullable(password).isPresent() && password.length > 1) {
			passwd = new String(password);
			System.out.println("[MercadoPublicoLoginModule] password= " + passwd);
		} else {
			throwLoginException("Authentication Failed: User " + userName + ". Password not supplied");
		}
		return passwd;
	}

}
