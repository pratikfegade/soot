/* Soot - a J*va Optimization Framework
 * Copyright (C) 2003 Jennifer Lhotak
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

package ca.mcgill.sable.soot.editors.parser;

import ca.mcgill.sable.soot.editors.JimpleOutlineObject;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.StringTokenizer;


public class JimpleMethod {


    private String val;
    private String label;
    private String type;
    private ArrayList modifiers;
    private int imageType;

    public JimpleMethod(String val) {
        setVal(val);
    }

    public static boolean isMethod(String val) {
        return (val.indexOf("(") != -1) && (val.indexOf(")") != -1);
    }

    public void parseMethod() {
        StringTokenizer st = new StringTokenizer(getVal());
        int numTokens = st.countTokens();
        String tempLabel = "";
        boolean addLabel = false;
        while (st.hasMoreTokens()) {
            String next = st.nextToken();
            if (JimpleModifier.isModifier(next)) {
                if (getModifiers() == null) {
                    setModifiers(new ArrayList());
                }
                getModifiers().add(next);
            }
            if (next.indexOf("(") != -1) {
                addLabel = true;
            }
            if (addLabel) {
                tempLabel = tempLabel + next;
                tempLabel = tempLabel + " ";
            }

        }
        setLabel(tempLabel);
    }

    public void findImageType() {
        if (getModifiers() == null) {
            setImageType(JimpleOutlineObject.NONE_METHOD);
            return;
        }
        if (getModifiers().contains("public")) {
            setImageType(JimpleOutlineObject.PUBLIC_METHOD);
        } else if (getModifiers().contains("protected")) {
            setImageType(JimpleOutlineObject.PROTECTED_METHOD);
        } else if (getModifiers().contains("private")) {
            setImageType(JimpleOutlineObject.PRIVATE_METHOD);
        } else {
            setImageType(JimpleOutlineObject.NONE_METHOD);
        }
    }

    public BitSet findDecorators() {
        BitSet bits = new BitSet();
        if (getModifiers() == null) return bits;
        if (getModifiers().contains("abstract")) {
            bits.set(JimpleOutlineObject.ABSTRACT_DEC);
        }
        if (getModifiers().contains("final")) {
            bits.set(JimpleOutlineObject.FINAL_DEC);
        }
        if (getModifiers().contains("static")) {
            bits.set(JimpleOutlineObject.STATIC_DEC);
        }
        if (getModifiers().contains("synchronized")) {
            bits.set(JimpleOutlineObject.SYNCHRONIZED_DEC);
        }
        return bits;
    }

    /**
     * @return
     */
    public int getImageType() {
        return imageType;
    }

    /**
     * @param i
     */
    public void setImageType(int i) {
        imageType = i;
    }

    /**
     * @return
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param string
     */
    public void setLabel(String string) {
        label = string;
    }

    /**
     * @return
     */
    public ArrayList getModifiers() {
        return modifiers;
    }

    /**
     * @param list
     */
    public void setModifiers(ArrayList list) {
        modifiers = list;
    }

    /**
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * @param string
     */
    public void setType(String string) {
        type = string;
    }

    /**
     * @return
     */
    public String getVal() {
        return val;
    }

    /**
     * @param string
     */
    public void setVal(String string) {
        val = string;
    }

}
