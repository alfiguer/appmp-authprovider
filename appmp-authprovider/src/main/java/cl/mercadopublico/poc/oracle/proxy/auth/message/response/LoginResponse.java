package cl.mercadopublico.poc.oracle.proxy.auth.message.response;

import java.util.Optional;

import cl.mercadopublico.poc.oracle.proxy.auth.domain.Estado;
import cl.mercadopublico.poc.oracle.proxy.auth.domain.Usuario;

public class LoginResponse {

	private Estado estado;

	private Usuario usuario;

	public LoginResponse() {
		super();
	}

	public LoginResponse(Estado estado, Usuario usuario) {
		super();
		this.estado = estado;
		this.usuario = usuario;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		StringBuilder trace = new StringBuilder();
		if (Optional.ofNullable(this.getEstado()).isPresent()) {
			trace.append(this.getEstado().toString()).append(",");
		}
		if (Optional.ofNullable(this.getUsuario()).isPresent()) {
			trace.append(this.getUsuario().toString()).append(",");
		}
		return trace.toString();
	}

}
