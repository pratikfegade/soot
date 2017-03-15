/* This file was generated by SableCC (http://www.sablecc.org/). */

package sablecc.soot.jimple.parser.node;

import sablecc.soot.jimple.parser.analysis.*;

@SuppressWarnings("nls")
public final class AFieldReference extends PReference
{
    private PFieldRef _fieldRef_;

    public AFieldReference()
    {
        // Constructor
    }

    public AFieldReference(
        @SuppressWarnings("hiding") PFieldRef _fieldRef_)
    {
        // Constructor
        setFieldRef(_fieldRef_);

    }

    @Override
    public Object clone()
    {
        return new AFieldReference(
            cloneNode(this._fieldRef_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAFieldReference(this);
    }

    public PFieldRef getFieldRef()
    {
        return this._fieldRef_;
    }

    public void setFieldRef(PFieldRef node)
    {
        if(this._fieldRef_ != null)
        {
            this._fieldRef_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._fieldRef_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._fieldRef_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._fieldRef_ == child)
        {
            this._fieldRef_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._fieldRef_ == oldChild)
        {
            setFieldRef((PFieldRef) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
