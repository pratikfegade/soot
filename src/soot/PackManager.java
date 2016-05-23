/* Soot - a J*va Optimization Framework
 * Copyright (C) 2003, 2004 Ondrej Lhotak
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

package soot;

import soot.jimple.toolkits.base.Aggregator;
import soot.jimple.toolkits.scalar.*;
import soot.jimple.toolkits.typing.TypeAssigner;
import soot.toDex.DexPrinter;
import soot.toolkits.exceptions.TrapTightener;
import soot.toolkits.scalar.LocalPacker;
import soot.toolkits.scalar.LocalSplitter;
import soot.toolkits.scalar.UnusedLocalEliminator;

import java.util.*;
import java.util.jar.JarOutputStream;
// [AM]
//import soot.javaToJimple.toolkits.*;

/**
 * Manages the Packs containing the various phases and their options.
 */
public class PackManager {
    public static boolean DEBUG = false;
    private final Map<String, Pack> packNameToPack = new HashMap<>();
    private final List<Pack> packList = new LinkedList<>();
    private JarOutputStream jarFile = null;
    private DexPrinter dexPrinter = new DexPrinter();

    public PackManager(Singletons.Global g) {
        PhaseOptions.v().setPackManager(this);
        init();
    }

    public static PackManager v() {
        return G.v().soot_PackManager();
    }

    void notifyAddPack() {
    }

    private void init() {
        Pack p;

        // Jimple body creation
        addPack(p = new JimpleBodyPack());
        {
            p.add(new Transform("jb.tt", TrapTightener.v()));
            p.add(new Transform("jb.ls", LocalSplitter.v()));
            p.add(new Transform("jb.a", Aggregator.v()));
            p.add(new Transform("jb.ule", UnusedLocalEliminator.v()));
            p.add(new Transform("jb.tr", TypeAssigner.v()));
            p.add(new Transform("jb.ulp", LocalPacker.v()));
            p.add(new Transform("jb.lns", LocalNameStandardizer.v()));
            p.add(new Transform("jb.cp", CopyPropagator.v()));
            p.add(new Transform("jb.dae", DeadAssignmentEliminator.v()));
            p.add(new Transform("jb.cp-ule", UnusedLocalEliminator.v()));
            p.add(new Transform("jb.lp", LocalPacker.v()));
            p.add(new Transform("jb.ne", NopEliminator.v()));
            p.add(new Transform("jb.uce", UnreachableCodeEliminator.v()));
        }

    }

    private void addPack(Pack p) {
        if (packNameToPack.containsKey(p.getPhaseName()))
            throw new RuntimeException("Duplicate pack " + p.getPhaseName());
        packNameToPack.put(p.getPhaseName(), p);
        packList.add(p);
    }

    public boolean hasPack(String phaseName) {
        return getPhase(phaseName) != null;
    }

    public Pack getPack(String phaseName) {
        Pack p = packNameToPack.get(phaseName);
        return p;
    }

    public HasPhaseOptions getPhase(String phaseName) {
        int index = phaseName.indexOf(".");
        if (index < 0) return getPack(phaseName);
        String packName = phaseName.substring(0, index);
        if (!hasPack(packName)) return null;
        return getPack(packName).get(phaseName);
    }

    public Transform getTransform(String phaseName) {
        return (Transform) getPhase(phaseName);
    }

    public Collection<Pack> allPacks() {
        return Collections.unmodifiableList(packList);
    }

    public JarOutputStream getJarFile() {
        return jarFile;
    }

}
