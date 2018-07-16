package cl.mercadopublico.poc.oracle.proxy.auth.domain;

import java.util.Optional;

/**
 * <p>
 * Dominio <code>Organismo</code>.
 * </p>
 *
 * Informacion del organismo del usuario, posterior a la autenticacion en la
 * tienda de mercado publico.
 *
 * @author <a href="mailto:alcides.f.figueroa@oracle.com">Alcides Figueroa</a>
 * @version 1.0
 */
public class Organismo {

	private String codigo;

	private String nombre;

	private String tipo;

	/**
	 * Constructor sin argumentos.
	 */
	public Organismo() {
		super();
	}

	public Organismo(String codigo, String nombre, String tipo) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.tipo = tipo;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		StringBuilder trace = new StringBuilder();
		if (Optional.ofNullable(this.getCodigo()).isPresent()) {
			trace.append("codigo=").append(this.getCodigo()).append(",");
		}
		if (Optional.ofNullable(this.getNombre()).isPresent()) {
			trace.append("nombre=").append(this.getNombre()).append(",");
		}
		if (Optional.ofNullable(this.getTipo()).isPresent()) {
			trace.append("tipo=").append(this.getTipo()).append(",");
		}
		return trace.toString();
	}

}
