/* This file was generated by SableCC (http://www.sablecc.org/). */

package sablecc.soot.jimple.parser.node;

import sablecc.soot.jimple.parser.analysis.*;

@SuppressWarnings("nls")
public final class AShrBinop extends PBinop
{
    private TShr _shr_;

    public AShrBinop()
    {
        // Constructor
    }

    public AShrBinop(
        @SuppressWarnings("hiding") TShr _shr_)
    {
        // Constructor
        setShr(_shr_);

    }

    @Override
    public Object clone()
    {
        return new AShrBinop(
            cloneNode(this._shr_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAShrBinop(this);
    }

    public TShr getShr()
    {
        return this._shr_;
    }

    public void setShr(TShr node)
    {
        if(this._shr_ != null)
        {
            this._shr_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._shr_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._shr_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._shr_ == child)
        {
            this._shr_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._shr_ == oldChild)
        {
            setShr((TShr) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
