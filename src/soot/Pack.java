/* Soot - a J*va Optimization Framework
 * Copyright (C) 1997-1999 Raja Vallee-Rai and Patrick Lam
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 */

/*
 * Modified by the Sable Research Group and others 1997-1999.  
 * See the 'credits' file distributed with Soot for the complete list of
 * contributors.  (Soot is distributed at http://www.sable.mcgill.ca/soot)
 */

package soot;

import soot.util.Chain;
import soot.util.HashChain;

import java.util.Iterator;

/**
 * A wrapper object for a pack of optimizations. Provides chain-like operations,
 * except that the key is the phase name.
 */
public abstract class Pack implements Iterable<Transform> {
	private String name;

	public String getPhaseName() {
		return name;
	}

	public Pack(String name) {
		this.name = name;
	}

	Chain<Transform> opts = new HashChain<Transform>();

	public Iterator<Transform> iterator() {
		return opts.iterator();
	}

	public void add(Transform t) {
		opts.add(t);
	}

	protected void internalApply() {
		throw new RuntimeException("wrong type of pack");
	}

	protected void internalApply(Body b) {
		throw new RuntimeException("wrong type of pack");
	}

	public final void apply() {
		internalApply();
	}

	public final void apply(Body b) {
		internalApply(b);
	}
}
