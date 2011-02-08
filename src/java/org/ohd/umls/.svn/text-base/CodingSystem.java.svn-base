package org.ohd.umls;
public class CodingSystem implements Comparable{
	private String id;
	private String name;
	private String version;
	
	/**
	 * @param id
	 * @param name
	 * @param version
	 */
	public CodingSystem(String id, String name, String version) {
		this.id = id;
		this.name = name;
		this.version = version;
	}

	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return Returns the version.
	 */
	public String getVersion() {
		return version;
	}

	public int compareTo(Object arg0) {
		CodingSystem other = (CodingSystem)arg0;
		return this.id.compareTo(other.id);
	}
	
}
