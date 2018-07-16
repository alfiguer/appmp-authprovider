package cl.mercadopublico.poc.security.provider.auth.mbeans;

import javax.management.*;
import weblogic.management.commo.RequiredModelMBeanWrapper;

/**
 * No description provided.
 * 
 * @root MercadoPublicoAuthenticator
 * @customizer cl.mercadopublico.poc.security.provider.auth.mbeans.MercadoPublicoAuthenticatorImpl(new
 *             RequiredModelMBeanWrapper(this))
 * @dynamic false
 * 
 */
public interface MercadoPublicoAuthenticatorMBean extends weblogic.management.commo.StandardInterface,
		weblogic.descriptor.DescriptorBean, weblogic.management.security.authentication.AuthenticatorMBean {

	/**
	 * No description provided.
	 * 
	 * @preprocessor weblogic.management.configuration.LegalHelper.checkClassName(value)
	 * @default "cl.mercadopublico.poc.security.provider.auth.ssp.MercadoPublicoAuthProvider"
	 * @dynamic false
	 * @non-configurable
	 * @validatePropertyDeclaration false
	 * 
	 * @preserveWhiteSpace
	 */
	public java.lang.String getProviderClassName();

	/**
	 * No description provided.
	 * 
	 * @default "Authentication Provider Mercado Publico"
	 * @dynamic false
	 * @non-configurable
	 * @validatePropertyDeclaration false
	 * 
	 * @preserveWhiteSpace
	 */
	public java.lang.String getDescription();

	/**
	 * No description provided.
	 * 
	 * @default "1.0"
	 * @dynamic false
	 * @non-configurable
	 * @validatePropertyDeclaration false
	 * 
	 * @preserveWhiteSpace
	 */
	public java.lang.String getVersion();

	/**
	 * @default "MercadoPublicoAuthenticator"
	 * @dynamic false
	 * @owner RealmAdministrator
	 * @VisibleToPartitions ALWAYS
	 */
	public java.lang.String getName();

}
