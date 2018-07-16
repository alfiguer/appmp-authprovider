package cl.mercadopublico.poc.oracle.proxy.auth.json.deserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import cl.mercadopublico.poc.oracle.proxy.auth.domain.Organismo;
import cl.mercadopublico.poc.oracle.proxy.auth.domain.Usuario;

public class UsuarioDeserializer extends StdDeserializer<Usuario> {

	private static final long serialVersionUID = 7295001682908640777L;

	public UsuarioDeserializer() {
		this(null);
	}

	public UsuarioDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public Usuario deserialize(JsonParser parser, DeserializationContext deserializer)
			throws IOException, JsonProcessingException {
		Usuario usuario;
		Organismo organismo;
		List<Organismo> organismos;
		List<String> logins;
		List<String> mails;
		List<String> fonos;
		List<String> mobiles;

		try {
			if (parser.getCurrentToken() != JsonToken.START_OBJECT) {
				throw new IOException("invalid start marker");
			}
			usuario = new Usuario();
			while (parser.nextToken() != JsonToken.END_OBJECT) {
				String fieldname = parser.getCurrentName();
				parser.nextToken(); // move to next token in string
				if ("CodigoUsuario".equals(fieldname)) {
					usuario.setCodigo(parser.getText());
				} else if ("Nombres".equals(fieldname)) {
					usuario.setNombres(parser.getText());
				} else if ("Apellidos".equals(fieldname)) {
					usuario.setApellidos(parser.getText());
				} else if ("Rut".equals(fieldname)) {
					usuario.setRut(parser.getText());
				} else if ("Login".equals(fieldname)) {
					logins = Arrays.asList(parser.getText().split(","));
					usuario.setLogins(logins);
				} else if ("Correo".equals(fieldname)) {
					mails = Arrays.asList(parser.getText().split(","));
					usuario.setCorreos(mails);
				} else if ("Fono".equals(fieldname)) {
					fonos = Arrays.asList(parser.getText().split(","));
					usuario.setFonos(fonos);
				} else if ("Celular".equals(fieldname)) {
					mobiles = Arrays.asList(parser.getText().split(","));
					usuario.setCelulares(mobiles);
				} else if ("TipoPerfil".equals(fieldname)) {
					usuario.setTipoPerfil(parser.getText());
				} else if ("Perfil".equals(fieldname)) {
					usuario.setPerfil(parser.getText());
				} else if ("EsDobleRol".equals(fieldname)) {
					usuario.setEsDobleRol(parser.getText());
				}
			}
			// if (parser.getCurrentToken() == JsonToken.START_ARRAY) {
			organismos = new ArrayList<Organismo>();

			while (parser.nextToken() != JsonToken.END_ARRAY) {
				organismo = new Organismo();
				while (parser.nextToken() != JsonToken.END_OBJECT) {
					String fieldname = parser.getCurrentName();
					parser.nextToken(); // move to next token in string
					if ("CodigoOrganismo".equals(fieldname)) {
						organismo.setCodigo(parser.getText());
					} else if ("NombreOrganismo".equals(fieldname)) {
						organismo.setNombre(parser.getText());
					} else if ("Tipo".equals(fieldname)) {
						organismo.setTipo(parser.getText());
					}
				}
				organismos.add(organismo);
			}

			if (!organismos.isEmpty()) {
				usuario.setOrganismos(organismos);
			}
		} catch (Exception e) {
			e.printStackTrace();
			usuario = null;
		}

		return usuario;
	}

}
