/* Soot - a J*va Optimization Framework
 * Copyright (C) 1999 Patrick Lam
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






package soot.jimple.internal;

import soot.*;
import soot.jimple.IfStmt;
import soot.jimple.Jimple;
import soot.jimple.Stmt;
import soot.jimple.StmtSwitch;
import soot.util.Switch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JIfStmt extends AbstractStmt implements IfStmt
{
    final ValueBox conditionBox;
    final UnitBox targetBox;

    final List<UnitBox> targetBoxes;

    public JIfStmt(Value condition, Unit target)
    {
        this(condition, Jimple.newStmtBox(target));
    }

    public JIfStmt(Value condition, UnitBox target)
    {
        this(Jimple.newConditionExprBox(condition), target);
    }

    protected JIfStmt(ValueBox conditionBox, UnitBox targetBox)
    {
        this.conditionBox = conditionBox;
        this.targetBox = targetBox;

        targetBoxes = Collections.singletonList(targetBox);
    }
    
    public Object clone()
    {
        return new JIfStmt(Jimple.cloneIfNecessary(getCondition()), getTarget());
    }
    
    public String toString()
    {
        Unit t = getTarget();
        String target = "(branch)";
        if(!t.branches())
            target = t.toString();
        return Jimple.IF + " "  + getCondition().toString() + " " + Jimple.GOTO + " "  + target;
    }
    
    public void toString(UnitPrinter up) {
        up.literal(Jimple.IF);
        up.literal(" ");
        conditionBox.toString(up);
        up.literal(" ");
        up.literal(Jimple.GOTO);
        up.literal(" ");
        targetBox.toString(up);
    }
    
    public Value getCondition()
    {
        return conditionBox.getValue();
    }
    
    public void setCondition(Value condition)
    {
        conditionBox.setValue(condition);
    }

    public ValueBox getConditionBox()
    {
        return conditionBox;
    }

    public Stmt getTarget()
    {
        return (Stmt) targetBox.getUnit();
    }

    public void setTarget(Unit target)
    {
        targetBox.setUnit(target);
    }

    public UnitBox getTargetBox()
    {
        return targetBox;
    }
    
    @Override
    public List<ValueBox> getUseBoxes()
    {
        List<ValueBox> useBoxes = new ArrayList<ValueBox>();

        useBoxes.addAll(conditionBox.getValue().getUseBoxes());
        useBoxes.add(conditionBox);

        return useBoxes;
    }

    @Override
    public final List<UnitBox> getUnitBoxes()
    {
        return targetBoxes;
    }

    public void apply(Switch sw)
    {
        ((StmtSwitch) sw).caseIfStmt(this);
    }


    public boolean fallsThrough(){return true;}        
    public boolean branches(){return true;}

}
