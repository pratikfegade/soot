/* This file was generated by SableCC (http://www.sablecc.org/). */

package sablecc.soot.jimple.parser.node;

import sablecc.soot.jimple.parser.analysis.Analysis;

@SuppressWarnings("nls")
public final class AModBinop extends PBinop
{
    private TMod _mod_;

    public AModBinop()
    {
        // Constructor
    }

    public AModBinop(
        @SuppressWarnings("hiding") TMod _mod_)
    {
        // Constructor
        setMod(_mod_);

    }

    @Override
    public Object clone()
    {
        return new AModBinop(
            cloneNode(this._mod_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAModBinop(this);
    }

    public TMod getMod()
    {
        return this._mod_;
    }

    public void setMod(TMod node)
    {
        if(this._mod_ != null)
        {
            this._mod_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._mod_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._mod_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._mod_ == child)
        {
            this._mod_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._mod_ == oldChild)
        {
            setMod((TMod) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
