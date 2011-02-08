package org.ohd.umls;

public class Code {
	private String value;
	private CodingSystem codeSystem;
        private String term;
	/**
	 * @param system
	 * @param value
	 */
	public Code(CodingSystem system, String value, String term) {
		// TODO Auto-generated constructor stub
		codeSystem = system;
		this.value = value;
                this.term = term;
	}
	/**
	 * @return Returns the codeSystem.
	 */
	public CodingSystem getCodeSystem() {
		return codeSystem;
	}
	/**
	 * @return Returns the value.
	 */
	public String getValue() {
		return value;
	}

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
	
}
