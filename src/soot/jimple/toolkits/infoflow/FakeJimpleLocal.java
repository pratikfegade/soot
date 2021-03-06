package soot.jimple.toolkits.infoflow;

import soot.Local;
import soot.Type;
import soot.jimple.internal.JimpleLocal;

// A wrapper for a JimpleLocal that defines equivalence and equality
// as having the same name and type.  This is useful for comparing
// InstanceFieldRefs and ArrayRefs from different parts of a program
// (without removing the FieldRef part, which is not a Jimple Value).
// FakeJimpleLocal can also hold a real JimpleLocal
// and some additional object, which together can make it easier to
// later reconstruct the original piece of Jimple code, or to construct
// a new meaningful piece of Jimple code base on this one.

public class FakeJimpleLocal extends JimpleLocal
{
	Local realLocal;
	Object info; // whatever you want to attach to it...
	
    /** Constructs a FakeJimpleLocal of the given name and type. */
    public FakeJimpleLocal(String name, Type t, Local realLocal)
    {
    	this(name, t, realLocal, null);
    }
    
    public FakeJimpleLocal(String name, Type t, Local realLocal, Object info)
    {
    	super(name, t, -1, -1);
    	this.realLocal = realLocal;
    	this.info = info;
    }

    /** Returns true if the given object is structurally equal to this one. */
    public boolean equivTo(Object o)
    {
    	if(o == null)
    		return false;
    	if(o instanceof JimpleLocal) 
    	{
    		if(getName() != null && getType() != null)
	        	return getName().equals(((Local) o).getName()) && getType().equals(((Local) o).getType());
	        else if(getName() != null)
	        	return getName().equals(((Local) o).getName()) && ((Local) o).getType() == null;
	        else if(getType() != null)
	        	return ((Local) o).getName() == null && getType().equals(((Local) o).getType());
	        else
	        	return ((Local) o).getName() == null && ((Local) o).getType() == null;
    	}
        return false;
    }
    
    public boolean equals(Object o)
    {
    	return equivTo(o);
    }

	/**
	 * Returns a hash code for this object, consistent with structural equality.
	 */
	public int equivHashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
		result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
		return result;
	}

    public int hashCode() {
        return equivHashCode();
    }
    
    /** Returns a clone of the current JimpleLocal. */
    public Object clone()
    {
        return new FakeJimpleLocal(getName(), getType(), realLocal, info);
    }
    
    public Local getRealLocal()
    {
    	return realLocal;
    }
    
    public Object getInfo()
    {
    	return info;
    }
    
    public void setInfo(Object o)
    {
    	info = o;
    }
}

