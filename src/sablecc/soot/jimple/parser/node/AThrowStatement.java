/* This file was generated by SableCC (http://www.sablecc.org/). */

package sablecc.soot.jimple.parser.node;

import sablecc.soot.jimple.parser.analysis.Analysis;

@SuppressWarnings("nls")
public final class AThrowStatement extends PStatement
{
    private TThrow _throw_;
    private PImmediate _immediate_;
    private TSemicolon _semicolon_;

    public AThrowStatement()
    {
        // Constructor
    }

    public AThrowStatement(
        @SuppressWarnings("hiding") TThrow _throw_,
        @SuppressWarnings("hiding") PImmediate _immediate_,
        @SuppressWarnings("hiding") TSemicolon _semicolon_)
    {
        // Constructor
        setThrow(_throw_);

        setImmediate(_immediate_);

        setSemicolon(_semicolon_);

    }

    @Override
    public Object clone()
    {
        return new AThrowStatement(
            cloneNode(this._throw_),
            cloneNode(this._immediate_),
            cloneNode(this._semicolon_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAThrowStatement(this);
    }

    public TThrow getThrow()
    {
        return this._throw_;
    }

    public void setThrow(TThrow node)
    {
        if(this._throw_ != null)
        {
            this._throw_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._throw_ = node;
    }

    public PImmediate getImmediate()
    {
        return this._immediate_;
    }

    public void setImmediate(PImmediate node)
    {
        if(this._immediate_ != null)
        {
            this._immediate_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._immediate_ = node;
    }

    public TSemicolon getSemicolon()
    {
        return this._semicolon_;
    }

    public void setSemicolon(TSemicolon node)
    {
        if(this._semicolon_ != null)
        {
            this._semicolon_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._semicolon_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._throw_)
            + toString(this._immediate_)
            + toString(this._semicolon_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._throw_ == child)
        {
            this._throw_ = null;
            return;
        }

        if(this._immediate_ == child)
        {
            this._immediate_ = null;
            return;
        }

        if(this._semicolon_ == child)
        {
            this._semicolon_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._throw_ == oldChild)
        {
            setThrow((TThrow) newChild);
            return;
        }

        if(this._immediate_ == oldChild)
        {
            setImmediate((PImmediate) newChild);
            return;
        }

        if(this._semicolon_ == oldChild)
        {
            setSemicolon((TSemicolon) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
