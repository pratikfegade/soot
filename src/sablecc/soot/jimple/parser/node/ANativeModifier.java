/* This file was generated by SableCC (http://www.sablecc.org/). */

package sablecc.soot.jimple.parser.node;

import sablecc.soot.jimple.parser.analysis.Analysis;

@SuppressWarnings("nls")
public final class ANativeModifier extends PModifier
{
    private TNative _native_;

    public ANativeModifier()
    {
        // Constructor
    }

    public ANativeModifier(
        @SuppressWarnings("hiding") TNative _native_)
    {
        // Constructor
        setNative(_native_);

    }

    @Override
    public Object clone()
    {
        return new ANativeModifier(
            cloneNode(this._native_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseANativeModifier(this);
    }

    public TNative getNative()
    {
        return this._native_;
    }

    public void setNative(TNative node)
    {
        if(this._native_ != null)
        {
            this._native_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._native_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._native_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._native_ == child)
        {
            this._native_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._native_ == oldChild)
        {
            setNative((TNative) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
