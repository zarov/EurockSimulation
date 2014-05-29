package fr.utbm.gi.vi51.g3.framework.environment;

import java.util.UUID;

import org.janusproject.kernel.address.AbstractAddress;

public class ObjectAddress extends AbstractAddress {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5038457629805489755L;
	private String name;
	public static final String NO_NAME = ""; //$NON-NLS-1$


	public ObjectAddress(UUID id, String name) {
		super(id);
		this.name = (name==null) ? NO_NAME : name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String iname) {
		this.name = iname;
	}


	@Override
	public final String toString() {
		StringBuilder b = new StringBuilder();
		String n = getName();
		if (n!=null && !n.isEmpty()) {
			b.append(n);
		}
		b.append("::"); //$NON-NLS-1$
		b.append(getUUID());
		return b.toString();
	}

}
