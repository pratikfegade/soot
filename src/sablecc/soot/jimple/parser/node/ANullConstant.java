/* This file was generated by SableCC (http://www.sablecc.org/). */

package sablecc.soot.jimple.parser.node;

import sablecc.soot.jimple.parser.analysis.Analysis;

@SuppressWarnings("nls")
public final class ANullConstant extends PConstant
{
    private TNull _null_;

    public ANullConstant()
    {
        // Constructor
    }

    public ANullConstant(
        @SuppressWarnings("hiding") TNull _null_)
    {
        // Constructor
        setNull(_null_);

    }

    @Override
    public Object clone()
    {
        return new ANullConstant(
            cloneNode(this._null_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseANullConstant(this);
    }

    public TNull getNull()
    {
        return this._null_;
    }

    public void setNull(TNull node)
    {
        if(this._null_ != null)
        {
            this._null_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._null_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._null_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._null_ == child)
        {
            this._null_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._null_ == oldChild)
        {
            setNull((TNull) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
