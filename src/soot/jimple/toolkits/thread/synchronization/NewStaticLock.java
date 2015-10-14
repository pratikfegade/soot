package soot.jimple.toolkits.thread.synchronization;

import soot.*;
import soot.util.Switch;

import java.util.Collections;
import java.util.List;

// Written by Richard L. Halpert on August 11, 2007
// Acts as a dummy value that gets put in a transaction's lockset,
// indicating that a new static object needs to be inserted into the
// program for use as a lock.

public class NewStaticLock implements Value {
    static int nextidnum = 1;
    SootClass sc; // The class to which to add a static lock.
    int idnum;

    public NewStaticLock(SootClass sc) {
        this.sc = sc;
        this.idnum = nextidnum++;
    }

    public SootClass getLockClass() {
        return sc;
    }

    @Override
    public List<ValueBox> getUseBoxes() {
        return Collections.emptyList();
    }

    /**
     * Clones the object.  Not implemented here.
     */
    public Object clone() {
        return new NewStaticLock(sc);
    }

    /**
     * Returns true if this object is structurally equivalent to c.
     * AbstractDataSources are equal and equivalent if their sourcename is the same
     */
    public boolean equivTo(Object c) {
        return equals(c);
    }

    public boolean equals(Object c) {
        if (c instanceof NewStaticLock) {
            return ((NewStaticLock) c).idnum == idnum;
        }
        return false;
    }

    /**
     * Returns a hash code consistent with structural equality for this object.
     */
    public int equivHashCode() {
        return hashCode();
    }

    public int hashCode() {
        return idnum;
    }

    public void toString(UnitPrinter up) {
    }

    public Type getType() {
        return NullType.v();
    }

    public void apply(Switch sw) {
        throw new RuntimeException("Not Implemented");
    }

    public String toString() {
        return "<new static lock in " + sc.toString() + ">";
    }
}
