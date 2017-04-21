/* Soot - a J*va Optimization Framework
 * Copyright (C) 2004 Jennifer Lhotak
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

package soot.toolkits.graph.interaction;

import soot.*;
import soot.options.Options;
import soot.toolkits.graph.DirectedGraph;

import java.util.ArrayList;

public class InteractionHandler {

    public static InteractionHandler v() { return new InteractionHandler();}

    private ArrayList<Object> stopUnitList;
    public ArrayList<Object> getStopUnitList(){
        return stopUnitList;
    }

    public void handleCfgEvent(DirectedGraph<?> g){
        if (currentPhaseEnabled()){
            System.out.println("Analyzing: "+currentPhaseName());
            doInteraction(new InteractionEvent(IInteractionConstants.NEW_ANALYSIS, currentPhaseName()));
        }
        if (isInteractThisAnalysis()){
            doInteraction(new InteractionEvent(IInteractionConstants.NEW_CFG, g));
        }
    }

    public void handleStopAtNodeEvent(Object u){
        if (isInteractThisAnalysis()){
            doInteraction(new InteractionEvent(IInteractionConstants.STOP_AT_NODE, u));
        }
    }

    public void handleBeforeAnalysisEvent(Object beforeFlow){
        if (isInteractThisAnalysis()){
            if (autoCon()){
                doInteraction(new InteractionEvent(IInteractionConstants.NEW_BEFORE_ANALYSIS_INFO_AUTO, beforeFlow));
            }
            else{
                doInteraction(new InteractionEvent(IInteractionConstants.NEW_BEFORE_ANALYSIS_INFO, beforeFlow));
            }
        }
    }

    public void handleAfterAnalysisEvent(Object afterFlow){
        if (isInteractThisAnalysis()){
            if (autoCon()){
                doInteraction(new InteractionEvent(IInteractionConstants.NEW_AFTER_ANALYSIS_INFO_AUTO, afterFlow));
            }
            else {
                doInteraction(new InteractionEvent(IInteractionConstants.NEW_AFTER_ANALYSIS_INFO, afterFlow));
            }
        }
    }

    public void handleTransformDone(Transform t, Body b){
        doneCurrent(true);
        if (isInteractThisAnalysis()){
            doInteraction(new InteractionEvent(IInteractionConstants.DONE, null));
        }
    }


    private synchronized void doInteraction(InteractionEvent event){
        getInteractionListener().setEvent(event);
        getInteractionListener().handleEvent();

    }

    public synchronized void waitForContinue(){
        try {
            this.wait();
        }
        catch (InterruptedException e){
        }

    }

    private boolean interactThisAnalysis;
    public void setInteractThisAnalysis(boolean b){
        interactThisAnalysis = b;
    }
    public boolean isInteractThisAnalysis(){
        return interactThisAnalysis;
    }
    private boolean interactionCon;
    public synchronized void setInteractionCon(){
        this.notify();
    }

    public boolean isInteractionCon(){
        return interactionCon;
    }
    private IInteractionListener interactionListener;
    public void setInteractionListener(IInteractionListener listener){
        interactionListener = listener;
    }
    public IInteractionListener getInteractionListener(){
        return interactionListener;
    }

    private String currentPhaseName;
    public void currentPhaseName(String name){
        currentPhaseName = name;
    }
    public String currentPhaseName(){
        return currentPhaseName;
    }

    private boolean currentPhaseEnabled;
    public void currentPhaseEnabled(boolean b){
        currentPhaseEnabled = b;
    }
    public boolean currentPhaseEnabled(){
        return currentPhaseEnabled;
    }

    private boolean cgDone = false;
    public void cgDone(boolean b){
        cgDone = b;
    }
    public boolean cgDone(){
        return cgDone;
    }

    private boolean doneCurrent;
    public void doneCurrent(boolean b){
        doneCurrent = b;
    }
    public boolean doneCurrent(){
        return doneCurrent;
    }

    private boolean autoCon;
    public void autoCon(boolean b){
        autoCon = b;
    }
    public boolean autoCon(){
        return autoCon;
    }

    public void stopInteraction(boolean b){
        Options.getInstance().set_interactive_mode(false);
    }

}

