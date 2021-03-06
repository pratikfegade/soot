/* This file was generated by SableCC (http://www.sablecc.org/). */

package sablecc.soot.jimple.parser.node;

import sablecc.soot.jimple.parser.analysis.Analysis;

@SuppressWarnings("nls")
public final class ACmpltBinop extends PBinop
{
    private TCmplt _cmplt_;

    public ACmpltBinop()
    {
        // Constructor
    }

    public ACmpltBinop(
        @SuppressWarnings("hiding") TCmplt _cmplt_)
    {
        // Constructor
        setCmplt(_cmplt_);

    }

    @Override
    public Object clone()
    {
        return new ACmpltBinop(
            cloneNode(this._cmplt_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseACmpltBinop(this);
    }

    public TCmplt getCmplt()
    {
        return this._cmplt_;
    }

    public void setCmplt(TCmplt node)
    {
        if(this._cmplt_ != null)
        {
            this._cmplt_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._cmplt_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._cmplt_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._cmplt_ == child)
        {
            this._cmplt_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._cmplt_ == oldChild)
        {
            setCmplt((TCmplt) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
