/* Soot - a J*va Optimization Framework
 * Copyright (C) 1997-1999 Raja Vallee-Rai
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

import soot.util.ArraySet;
import soot.util.Chain;

import java.util.*;

/** Represents the class hierarchy.  It is closely linked to a Scene,
 * and must be recreated if the Scene changes. 
 *
 * The general convention is that if a method name contains 
 * "Including", then it returns the non-strict result; otherwise,
 * it does a strict query (e.g. strict superclass).  */
public class Hierarchy
{
    // These two maps are not filled in the constructor.
    private Map<SootClass, List<SootClass>> classToSubclasses;
    private Map<SootClass, List<SootClass>> interfaceToSubinterfaces;
    private Map<SootClass, List<SootClass>> interfaceToSuperinterfaces;

    private Map<SootClass, List<SootClass>> classToDirSubclasses;
    private Map<SootClass, List<SootClass>> interfaceToDirSubinterfaces;
    private Map<SootClass, List<SootClass>> interfaceToDirSuperinterfaces;

    // This holds the direct implementers.
    private Map<SootClass, List<SootClass>> interfaceToDirImplementers;

    private int state;
    private Scene sc;

    /** Constructs a hierarchy from the current scene. */
    public Hierarchy()
    {
        this.sc = Scene.getInstance();
        state = sc.getState();

        // Well, this used to be describable by 'Duh'.
        // Construct the subclasses hierarchy and the subinterfaces hierarchy.
        {
            Chain<SootClass> allClasses = sc.getClasses();

            classToSubclasses = new HashMap<>(allClasses.size() * 2 + 1, 0.7f);
            interfaceToSubinterfaces = new HashMap<>(allClasses.size() * 2 + 1, 0.7f);
            interfaceToSuperinterfaces = new HashMap<>(allClasses.size() * 2 + 1, 0.7f);

            classToDirSubclasses = new HashMap<>
                    (allClasses.size() * 2 + 1, 0.7f);
            interfaceToDirSubinterfaces = new HashMap<>
                    (allClasses.size() * 2 + 1, 0.7f);
            interfaceToDirSuperinterfaces = new HashMap<>
                    (allClasses.size() * 2 + 1, 0.7f);
            interfaceToDirImplementers = new HashMap<>
                    (allClasses.size() * 2 + 1, 0.7f);

            for (SootClass c : allClasses) {
                if( c.resolvingLevel() < SootClass.HIERARCHY ) continue;

                if (c.isInterface())
                {
                    interfaceToDirSubinterfaces.put(c, new ArrayList<>());
                    interfaceToDirSuperinterfaces.put(c, new ArrayList<>());
                    interfaceToDirImplementers.put(c, new ArrayList<>());
                }
                else
                    classToDirSubclasses.put(c, new ArrayList<>());
            }

            for (SootClass c : allClasses) {
                if( c.resolvingLevel() < SootClass.HIERARCHY ) continue;


                if (c.hasSuperclass()) {
                    if (c.isInterface()) {
                        List<SootClass> l2 = interfaceToDirSuperinterfaces.get(c);
                        for (SootClass i : c.getInterfaces()) {
                            if( c.resolvingLevel() < SootClass.HIERARCHY ) continue;
                            List<SootClass> l = interfaceToDirSubinterfaces.get(i);
                            if (l != null) l.add(c);
                            if (l2 != null) l2.add(i);
                        }
                    }
                    else {
                        List<SootClass> l = classToDirSubclasses.get(c.getSuperclass());
                        if (l != null) l.add(c);

                        for (SootClass i : c.getInterfaces()) {
                            if( c.resolvingLevel() < SootClass.HIERARCHY ) continue;
                            l = interfaceToDirImplementers.get(i);
                            if (l != null) l.add(c);
                        }
                    }
                }
            }

            // Fill the directImplementers lists with subclasses.
            for (SootClass c : allClasses) {
                if( c.resolvingLevel() < SootClass.HIERARCHY ) continue;
                if (c.isInterface()) {
                    List<SootClass> imp = interfaceToDirImplementers.get(c);
                    Set<SootClass> s = new ArraySet<>();
                    for (SootClass c0 : imp) {
                        if( c.resolvingLevel() < SootClass.HIERARCHY ) continue;
                        s.addAll(getSubclassesOfIncluding(c0));
                    }
                    imp.clear();
                    imp.addAll(s);
                }
            }
        }
    }

    private void checkState()
    {
        if (state != sc.getState())
            throw new ConcurrentModificationException("Scene changed for Hierarchy!");
    }

    // This includes c in the list of subclasses.
    /** Returns a list of subclasses of c, including itself. */
    private List<SootClass> getSubclassesOfIncluding(SootClass c)
    {
        c.checkLevel(SootClass.HIERARCHY);
        if (c.isInterface())
            throw new RuntimeException("class needed!");

        List<SootClass> l = new ArrayList<>();
        l.addAll(getSubclassesOf(c));
        l.add(c);

        return Collections.unmodifiableList(l);
    }

    /** Returns a list of subclasses of c, excluding itself. */
    private List<SootClass> getSubclassesOf(SootClass c)
    {
        c.checkLevel(SootClass.HIERARCHY);
        if (c.isInterface())
            throw new RuntimeException("class needed!");

        checkState();

        // If already cached, return the value.
        if (classToSubclasses.get(c) != null)
            return classToSubclasses.get(c);

        // Otherwise, build up the hashmap.
        List<SootClass> l = new ArrayList<>();

        for (SootClass cls : classToDirSubclasses.get(c)) {
            if( cls.resolvingLevel() < SootClass.HIERARCHY ) continue;
            l.addAll(getSubclassesOfIncluding(cls));
        }

        l = Collections.unmodifiableList(l);
        classToSubclasses.put(c, l);

        return l;
    }

    /** Returns a list of superclasses of c, including itself. */
    private List<SootClass> getSuperclassesOfIncluding(SootClass c)
    {
        c.checkLevel(SootClass.HIERARCHY);
        List<SootClass> l = getSuperclassesOf(c);
        ArrayList<SootClass> al = new ArrayList<>(); al.add(c); al.addAll(l);
        return Collections.unmodifiableList(al);
    }

    /** Returns a list of strict superclasses of c, starting with c's parent. */
    private List<SootClass> getSuperclassesOf(SootClass c)
    {
        c.checkLevel(SootClass.HIERARCHY);
        if (c.isInterface())
            throw new RuntimeException("class needed!");

        checkState();

        ArrayList<SootClass> l = new ArrayList<>();
        SootClass cl = c;

        while (cl.hasSuperclass())
        {
            l.add(cl.getSuperclass());
            cl = cl.getSuperclass();
        }

        return Collections.unmodifiableList(l);
    }

    /** Returns a list of subinterfaces of c, including itself. */
    private List<SootClass> getSubinterfacesOfIncluding(SootClass c)
    {
        c.checkLevel(SootClass.HIERARCHY);
        if (!c.isInterface())
            throw new RuntimeException("interface needed!");

        List<SootClass> l = new ArrayList<>();
        l.addAll(getSubinterfacesOf(c));
        l.add(c);

        return Collections.unmodifiableList(l);
    }

    /** Returns a list of subinterfaces of c, excluding itself. */
    private List<SootClass> getSubinterfacesOf(SootClass c)
    {
        c.checkLevel(SootClass.HIERARCHY);
        if (!c.isInterface())
            throw new RuntimeException("interface needed!");

        checkState();

        // If already cached, return the value.
        if (interfaceToSubinterfaces.get(c) != null)
            return interfaceToSubinterfaces.get(c);

        // Otherwise, build up the hashmap.
        List<SootClass> l = new ArrayList<>();

        for (SootClass si : interfaceToDirSubinterfaces.get(c)) {
            l.addAll(getSubinterfacesOfIncluding(si));
        }

        interfaceToSubinterfaces.put(c, Collections.unmodifiableList(l));

        return Collections.unmodifiableList(l);
    }

    /** Returns a list of superinterfaces of c, including itself. */
    public List<SootClass> getSuperinterfacesOfIncluding(SootClass c)
    {
        c.checkLevel(SootClass.HIERARCHY);
        if (!c.isInterface())
            throw new RuntimeException("interface needed!");

        List<SootClass> l = new ArrayList<>();
        l.addAll(getSuperinterfacesOf(c));
        l.add(c);

        return Collections.unmodifiableList(l);
    }

    /** Returns a list of superinterfaces of c, excluding itself. */
    public List<SootClass> getSuperinterfacesOf(SootClass c)
    {
        c.checkLevel(SootClass.HIERARCHY);
        if (!c.isInterface())
            throw new RuntimeException("interface needed!");

        checkState();

        // If already cached, return the value.
        List<SootClass> cached = interfaceToSuperinterfaces.get(c);
        if (cached != null)
            return cached;

        // Otherwise, build up the hashmap.
        List<SootClass> l = new ArrayList<>();

        for (SootClass si : interfaceToDirSuperinterfaces.get(c)) {
            l.addAll(getSuperinterfacesOfIncluding(si));
        }

        interfaceToSuperinterfaces.put(c, Collections.unmodifiableList(l));

        return Collections.unmodifiableList(l);
    }

    /**
     * Returns true if child is, or is a subclass of, possibleParent. If one of
     * the known parent classes is phantom, we conservatively assume that the
     * current class might be a child.
     */
    public boolean isClassSubclassOfIncluding(SootClass child, SootClass possibleParent)
    {
        child.checkLevel(SootClass.HIERARCHY);
        possibleParent.checkLevel(SootClass.HIERARCHY);
        List<SootClass> parentClasses = getSuperclassesOfIncluding(child);
        if (parentClasses.contains(possibleParent))
            return true;

        for (SootClass sc : parentClasses)
            if (sc.isPhantom())
                return true;

        return false;
    }

    /** Returns true if child is a subinterface of possibleParent. */
    public boolean isInterfaceSubinterfaceOf(SootClass child, SootClass possibleParent)
    {
        child.checkLevel(SootClass.HIERARCHY);
        possibleParent.checkLevel(SootClass.HIERARCHY);
        return getSubinterfacesOf(possibleParent).contains(child);
    }
}
