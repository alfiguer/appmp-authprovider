package cl.mercadopublico.poc.oracle.proxy.auth.json.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import cl.mercadopublico.poc.oracle.proxy.auth.domain.Estado;

public class EstadoDeserializer extends StdDeserializer<Estado> {

	private static final long serialVersionUID = 5085736487759057295L;

	public EstadoDeserializer() {
		this(null);
	}

	protected EstadoDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public Estado deserialize(JsonParser parser, DeserializationContext deserializer)
			throws IOException, JsonProcessingException {
		Estado estado;
		ObjectCodec codec;
		JsonNode node;

		try {
			estado = new Estado();
			codec = parser.getCodec();
			node = codec.readTree(parser);
			estado.setCodigo(node.get("Codigo").asText());
			estado.setMensaje(node.get("Mensaje").asText());
		} catch (Exception e) {
			e.printStackTrace();
			estado = null;
		}
		return estado;
	}

}
