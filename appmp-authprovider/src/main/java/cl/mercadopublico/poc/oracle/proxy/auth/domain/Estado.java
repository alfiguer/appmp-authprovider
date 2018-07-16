package cl.mercadopublico.poc.oracle.proxy.auth.domain;

import java.util.Optional;

/**
 * <p>
 * Dominio <code>Estado</code>.
 * </p>
 *
 * Informacion con el estado de la respuesta del servicio. Ejemplo: 200 OK, 404
 * Not Found, etc.
 *
 * @author <a href="mailto:alcides.f.figueroa@oracle.com">Alcides Figueroa</a>
 * @version 1.0
 */
public class Estado {

	private String codigo;

	private String mensaje;

	/**
	 * Constructor sin argumentos.
	 */
	public Estado() {
		super();
	}

	public Estado(String codigo, String mensaje) {
		super();
		this.codigo = codigo;
		this.mensaje = mensaje;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	@Override
	public String toString() {
		StringBuilder trace = new StringBuilder();
		if (Optional.ofNullable(this.getCodigo()).isPresent()) {
			trace.append("codigo=").append(this.getCodigo()).append(",");
		}
		if (Optional.ofNullable(this.getMensaje()).isPresent()) {
			trace.append("mensaje=").append(this.getMensaje()).append(",");
		}
		return trace.toString();
	}

	public static final void main(String[] args) {
		Estado estado = new Estado("404", "Error al procesar la password");
		System.out.println(estado);
	}

}
