package cl.mercadopublico.poc.oracle.proxy.auth.control;

import java.net.URI;
import java.util.Optional;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import cl.mercadopublico.poc.oracle.proxy.auth.domain.Estado;
import cl.mercadopublico.poc.oracle.proxy.auth.domain.Usuario;
import cl.mercadopublico.poc.oracle.proxy.auth.json.deserializer.EstadoDeserializer;
import cl.mercadopublico.poc.oracle.proxy.auth.json.deserializer.UsuarioDeserializer;
import cl.mercadopublico.poc.oracle.proxy.auth.message.request.LoginRequest;
import cl.mercadopublico.poc.oracle.proxy.auth.message.response.LoginResponse;
import cl.mercadopublico.poc.oracle.proxy.auth.prop.Estados;

public class AuthentControl {

	private static final String REQUEST_URL = "https://administradordeserviciosapi.azure-api.net/tienda/Login/Autenticacion.json";

	private static final String CONTENT_TYPE = "application/x-www-form-urlencoded";

	private static AuthentControl instance = null;

	/**
	 * Constructor sin argumentos.
	 */
	private AuthentControl() {
		super();
	}

	/**
	 * <p>
	 * Obtiene la instancia de la clase. Forma parte de la implementaci&oacute;n del
	 * patr&oacute;n singleton.
	 * </p>
	 *
	 * @return instancia de <code>AuthentControl</code>.
	 */
	public static AuthentControl getInstance() {

		if (!Optional.ofNullable(AuthentControl.instance).isPresent()) {
			AuthentControl.instance = new AuthentControl();
		}
		return AuthentControl.instance;
	}

	public LoginResponse login(LoginRequest request) {
		LoginResponse response = null;
		Estado estado = null;
		Usuario usuario;
		HttpClient httpclient;
		URIBuilder builder;
		URI uri;
		HttpPost reqHttpPost;
		HttpResponse responseHttp;
		HttpEntity entity;
		StringEntity reqEntity;
		StringBuilder reqBodyLogin;
		ObjectMapper mapper;
		SimpleModule module;

		try {
			response = new LoginResponse();
			if (Optional.ofNullable(request).isPresent() && Optional.ofNullable(request.getLogin()).isPresent()
					&& Optional.ofNullable(request.getPassword()).isPresent()) {
				httpclient = HttpClients.createDefault();
				builder = new URIBuilder(AuthentControl.REQUEST_URL);
				uri = builder.build();
				reqHttpPost = new HttpPost(uri);
				reqHttpPost.setHeader("Content-Type", AuthentControl.CONTENT_TYPE);

				reqBodyLogin = new StringBuilder("Login=");
				reqBodyLogin.append(request.getLogin());
				reqBodyLogin.append("&Password=").append(request.getPassword());
				reqEntity = new StringEntity(reqBodyLogin.toString());
				// reqEntity = new
				// StringEntity("Login=15.094.974-2&Password=rd6earPukDxt0ekribebIw==");
				reqHttpPost.setEntity(reqEntity);

				responseHttp = httpclient.execute(reqHttpPost);
				entity = responseHttp.getEntity();

				System.out.println("StatusCode: " + responseHttp.getStatusLine().getStatusCode());

				if (responseHttp.getStatusLine().getStatusCode() == 200) {
					if (Optional.ofNullable(entity).isPresent()) {
						estado = new Estado(Estados.OK.toString(), "OK");
						mapper = new ObjectMapper();
						module = new SimpleModule("UsuarioDeserializer", new Version(1, 0, 0, null, null, null));
						module.addDeserializer(Usuario.class, new UsuarioDeserializer());
						mapper.registerModule(module);
						usuario = mapper.readValue(EntityUtils.toString(entity), Usuario.class);
						response.setUsuario(usuario);
					} else {
						estado = new Estado(Estados.OK.toString(), "OK");
					}
				} else {
					mapper = new ObjectMapper();
					module = new SimpleModule("EstadoDeserializer", new Version(1, 0, 0, null, null, null));
					module.addDeserializer(Estado.class, new EstadoDeserializer());
					mapper.registerModule(module);
					estado = mapper.readValue(EntityUtils.toString(entity), Estado.class);
				}
			} else {
				estado = new Estado(Estados.BAD_REQUEST.toString(), "Bad Request");
			}
		} catch (Exception e) {
			estado = new Estado(Estados.INTERNAL_ERROR.toString(), "Internal Server Error");
			e.printStackTrace();
		}
		response.setEstado(estado);
		return response;
	}

	public static final void main(String[] args) {
		LoginRequest request = new LoginRequest("15.094.974-2", "rd6earPukDxt0ekribebIw==");
		LoginResponse response = AuthentControl.getInstance().login(request);
		if (Optional.ofNullable(response).isPresent()) {
			System.out.println(response);
		}
	}
}
