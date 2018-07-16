package cl.mercadopublico.poc.oracle.proxy.auth.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * Dominio <code>Usuario</code>.
 * </p>
 *
 * Informacion del login de usuario, posterior a la autenticacion en la tienda
 * de mercado publico.
 *
 * @author <a href="mailto:alcides.f.figueroa@oracle.com">Alcides Figueroa</a>
 * @version 1.0
 */
public class Usuario {

	private String codigo;

	private String nombres;

	private String apellidos;

	private String rut;

	private List<String> logins;

	private List<String> correos;

	private List<String> fonos;

	private List<String> celulares;

	private String tipoPerfil;

	private String perfil;

	private String esDobleRol;

	private List<Organismo> organismos;

	/**
	 * Constructor sin argumentos.
	 */
	public Usuario() {
		super();
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getRut() {
		return rut;
	}

	public void setRut(String rut) {
		this.rut = rut;
	}

	public List<String> getLogins() {
		return logins;
	}

	public void setLogins(List<String> logins) {
		this.logins = logins;
	}

	public List<String> getCorreos() {
		return correos;
	}

	public void setCorreos(List<String> correos) {
		this.correos = correos;
	}

	public List<String> getFonos() {
		return fonos;
	}

	public void setFonos(List<String> fonos) {
		this.fonos = fonos;
	}

	public List<String> getCelulares() {
		return celulares;
	}

	public void setCelulares(List<String> celulares) {
		this.celulares = celulares;
	}

	public String getTipoPerfil() {
		return tipoPerfil;
	}

	public void setTipoPerfil(String tipoPerfil) {
		this.tipoPerfil = tipoPerfil;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public String getEsDobleRol() {
		return esDobleRol;
	}

	public void setEsDobleRol(String esDobleRol) {
		this.esDobleRol = esDobleRol;
	}

	public List<Organismo> getOrganismos() {
		return organismos;
	}

	public void setOrganismos(List<Organismo> organismos) {
		this.organismos = organismos;
	}

	@Override
	public String toString() {
		StringBuilder trace = new StringBuilder();
		if (Optional.ofNullable(this.getCodigo()).isPresent()) {
			trace.append("codigo=").append(this.getCodigo()).append(",");
		}
		if (Optional.ofNullable(this.getNombres()).isPresent()) {
			trace.append("nombres=").append(this.getNombres()).append(",");
		}
		if (Optional.ofNullable(this.getRut()).isPresent()) {
			trace.append("rut=").append(this.getRut()).append(",");
		}
		if (Optional.ofNullable(this.getTipoPerfil()).isPresent()) {
			trace.append("tipoPerfil=").append(this.getTipoPerfil()).append(",");
		}
		if (Optional.ofNullable(this.getPerfil()).isPresent()) {
			trace.append("perfil=").append(this.getPerfil()).append(",");
		}
		if (Optional.ofNullable(this.getEsDobleRol()).isPresent()) {
			trace.append("esDobleRol=").append(this.getEsDobleRol()).append(",");
		}
		if (Optional.ofNullable(this.getLogins()).isPresent()) {
			for (String login : this.getLogins()) {
				trace.append(login).append(",");
			}
		}
		if (Optional.ofNullable(this.getCorreos()).isPresent()) {
			for (String correo : this.getCorreos()) {
				trace.append(correo).append(",");
			}
		}
		if (Optional.ofNullable(this.getFonos()).isPresent()) {
			for (String fono : this.getFonos()) {
				trace.append(fono).append(",");
			}
		}
		if (Optional.ofNullable(this.getCelulares()).isPresent()) {
			for (String celular : this.getCelulares()) {
				trace.append(celular).append(",");
			}
		}
		if (Optional.ofNullable(this.getOrganismos()).isPresent()) {
			for (Organismo organismo : this.getOrganismos()) {
				trace.append(organismo.toString()).append(",");
			}
		}
		return trace.toString();
	}

	public static final void main(String[] args) {
		Usuario usuario = new Usuario();
		usuario.setCodigo("1299437_3234");
		usuario.setNombres("Miguel Angel");
		usuario.setApellidos("Garrido");
		usuario.setRut("15.094.974-2");
		usuario.setTipoPerfil("1");
		usuario.setPerfil("Supervisor");
		usuario.setEsDobleRol("1");

		String sampleLogins = "miguelangelg,miguelgarrido-mpp";
		List<String> logins = Arrays.asList(sampleLogins.split(","));
		usuario.setLogins(logins);

		String sampleMails = "miguel.garrido@chilecompra.cl,miguel.garrido@chilecompra.cl";
		List<String> mails = Arrays.asList(sampleMails.split(","));
		usuario.setCorreos(mails);

		String sampleFonos = "56-02-22995709-709,22-2-2222222";
		List<String> fonos = Arrays.asList(sampleFonos.split(","));
		usuario.setFonos(fonos);

		String sampleCelulares = "56-9-,22-2-22222222";
		List<String> celulares = Arrays.asList(sampleCelulares.split(","));
		usuario.setCelulares(celulares);

		List<Organismo> organismos = new ArrayList<Organismo>();
		organismos.add(new Organismo("6945", "Dirección de Compras y Contratación Pública", "2"));
		organismos.add(new Organismo("1038371", "Dpto. QA", "1"));
		usuario.setOrganismos(organismos);

		System.out.println(usuario);
	}

}
