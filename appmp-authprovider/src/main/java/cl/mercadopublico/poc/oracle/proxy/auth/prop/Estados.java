package cl.mercadopublico.poc.oracle.proxy.auth.prop;

public enum Estados {
	OK("200"), NOT_FOUND("404"), BAD_REQUEST("400"), INTERNAL_ERROR("500"),;

	private final String text;

	/**
	 * @param text
	 */
	Estados(final String text) {
		this.text = text;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return text;
	}
}
