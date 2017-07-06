/* This file was generated by SableCC (http://www.sablecc.org/). */

package sablecc.soot.jimple.parser.node;

import sablecc.soot.jimple.parser.analysis.Analysis;

@SuppressWarnings("nls")
public final class ACmplBinop extends PBinop
{
    private TCmpl _cmpl_;

    public ACmplBinop()
    {
        // Constructor
    }

    public ACmplBinop(
        @SuppressWarnings("hiding") TCmpl _cmpl_)
    {
        // Constructor
        setCmpl(_cmpl_);

    }

    @Override
    public Object clone()
    {
        return new ACmplBinop(
            cloneNode(this._cmpl_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseACmplBinop(this);
    }

    public TCmpl getCmpl()
    {
        return this._cmpl_;
    }

    public void setCmpl(TCmpl node)
    {
        if(this._cmpl_ != null)
        {
            this._cmpl_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._cmpl_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._cmpl_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._cmpl_ == child)
        {
            this._cmpl_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._cmpl_ == oldChild)
        {
            setCmpl((TCmpl) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}