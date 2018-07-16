package cl.mercadopublico.poc.security.provider.auth.mbeans;

import javax.management.*;
import javax.management.modelmbean.ModelMBean;
import weblogic.management.commo.CommoMBeanInstance;
import java.lang.reflect.*;

public class MercadoPublicoAuthenticatorImpl extends weblogic.management.security.authentication.AuthenticatorImpl
		implements java.io.Serializable {
	static final long serialVersionUID = 1L;

	/**
	 * @deprecated Replaced by MercadoPublicoAuthenticatorImpl (ModelMBean base).
	 */
	public MercadoPublicoAuthenticatorImpl(CommoMBeanInstance base) throws MBeanException {
		super(base);
	}

	// ****************************************************************************************************
	// ***************************************** GENERATED METHODS
	// ****************************************
	// ****************************************************************************************************

	// ****************************************************************************************************
	// ******************************************* METHODS STUBS
	// ******************************************
	// ****************************************************************************************************
	// @constructorMethods

}
