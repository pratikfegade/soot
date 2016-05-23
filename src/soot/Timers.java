/* Soot - a J*va Optimization Framework
 * Copyright (C) 2003 Ondrej Lhotak
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

public class Timers {
    public int totalFlowNodes;
    public int totalFlowComputations;
    public Timer defsTimer = new Timer("defs");
    public Timer usesTimer = new Timer("uses");
    public Timer liveTimer = new Timer("live");
    public Timer splitTimer = new Timer("split");
    public Timer packTimer = new Timer("pack");
    public Timer conversionTimer = new Timer("conversion");
    public Timer graphTimer = new Timer("graphTimer");
    public Timer assignTimer = new Timer("assignTimer");
    public Timer splitPhase1Timer = new Timer("splitPhase1");
    public Timer splitPhase2Timer = new Timer("splitPhase2");
    public Timer usePhase1Timer = new Timer("usePhase1");
    public Timer usePhase2Timer = new Timer("usePhase2");
    public Timer liveAnalysisTimer = new Timer("liveAnalysis");
    public Timer aggregationTimer = new Timer("aggregation");
    public Timer deadCodeTimer = new Timer("deadCode");
    public Timer propagatorTimer = new Timer("propagator");
    public Timer buildJasminTimer = new Timer("buildjasmin");
    public long stmtCount;
    public Timer fieldTimer = new soot.Timer();
    public Timer methodTimer = new soot.Timer();
    public Timer attributeTimer = new soot.Timer();
    public Timer readTimer = new soot.Timer();

    public Timers(Singletons.Global g) {
    }

    public static Timers v() {
        return G.v().soot_Timers();
    }


}

