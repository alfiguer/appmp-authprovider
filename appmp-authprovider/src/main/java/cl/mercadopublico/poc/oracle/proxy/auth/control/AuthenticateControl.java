package cl.mercadopublico.poc.oracle.proxy.auth.control;

import java.util.Optional;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import javax.ws.rs.client.Client;

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

public class AuthenticateControl {

	private static final String REQUEST_URL = "https://administradordeserviciosapi.azure-api.net/tienda/Login/Autenticacion.json";

	private static final String CONTENT_TYPE = "application/x-www-form-urlencoded";

	private static AuthenticateControl instance = null;

	public AuthenticateControl() {
		super();
	}

	/*
	 * <p> Obtiene la instancia de la clase. Forma parte de la implementaci&oacute;n
	 * del patr&oacute;n singleton. </p>
	 *
	 * @return instancia de <code>AuthentControl</code>.
	 */
	public static AuthenticateControl getInstance() {

		if (!Optional.ofNullable(AuthenticateControl.instance).isPresent()) {
			AuthenticateControl.instance = new AuthenticateControl();
		}
		return AuthenticateControl.instance;
	}

	public LoginResponse login(LoginRequest request) {
		LoginResponse response = null;
		Estado estado = null;
		Usuario usuario;
		StringBuilder reqBodyLogin;
		ObjectMapper mapper;
		SimpleModule module;
		Client client;
		WebTarget target;
		Invocation.Builder invocationBuilder;
		Response responseHttp;
		String responseEntity;

		try {
			response = new LoginResponse();
			if (Optional.ofNullable(request).isPresent() && Optional.ofNullable(request.getLogin()).isPresent()
					&& Optional.ofNullable(request.getPassword()).isPresent()) {

				reqBodyLogin = new StringBuilder("Login=");
				reqBodyLogin.append(request.getLogin());
				reqBodyLogin.append("&Password=").append(request.getPassword());

				client = ClientBuilder.newClient();
				target = client.target(AuthenticateControl.REQUEST_URL);
				invocationBuilder = target.request(AuthenticateControl.CONTENT_TYPE);
				responseHttp = invocationBuilder
						.post(Entity.entity(reqBodyLogin.toString(), AuthenticateControl.CONTENT_TYPE));

				responseEntity = (responseHttp.readEntity(String.class));

				if (responseHttp.getStatus() == 200) {
					if (Optional.ofNullable(responseEntity).isPresent()) {
						estado = new Estado(Estados.OK.toString(), "OK");
						mapper = new ObjectMapper();
						module = new SimpleModule("UsuarioDeserializer", new Version(1, 0, 0, null, null, null));
						module.addDeserializer(Usuario.class, new UsuarioDeserializer());
						mapper.registerModule(module);
						usuario = mapper.readValue(responseEntity, Usuario.class);
						response.setUsuario(usuario);
					} else {
						estado = new Estado(Estados.OK.toString(), "OK");
					}
				} else {
					mapper = new ObjectMapper();
					module = new SimpleModule("EstadoDeserializer", new Version(1, 0, 0, null, null, null));
					module.addDeserializer(Estado.class, new EstadoDeserializer());
					mapper.registerModule(module);
					estado = mapper.readValue(responseEntity, Estado.class);
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
		LoginResponse response = AuthenticateControl.getInstance().login(request);
		if (Optional.ofNullable(response).isPresent()) {
			System.out.println(response);
		}
	}

}
