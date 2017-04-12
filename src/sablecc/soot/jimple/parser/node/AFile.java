/* This file was generated by SableCC (http://www.sablecc.org/). */

package sablecc.soot.jimple.parser.node;

import sablecc.soot.jimple.parser.analysis.Analysis;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

@SuppressWarnings("nls")
public final class AFile extends PFile
{
    private final LinkedList<PModifier> _modifier_ = new LinkedList<PModifier>();
    private PFileType _fileType_;
    private PClassName _className_;
    private PExtendsClause _extendsClause_;
    private PImplementsClause _implementsClause_;
    private PFileBody _fileBody_;

    public AFile()
    {
        // Constructor
    }

    public AFile(
        @SuppressWarnings("hiding") List<?> _modifier_,
        @SuppressWarnings("hiding") PFileType _fileType_,
        @SuppressWarnings("hiding") PClassName _className_,
        @SuppressWarnings("hiding") PExtendsClause _extendsClause_,
        @SuppressWarnings("hiding") PImplementsClause _implementsClause_,
        @SuppressWarnings("hiding") PFileBody _fileBody_)
    {
        // Constructor
        setModifier(_modifier_);

        setFileType(_fileType_);

        setClassName(_className_);

        setExtendsClause(_extendsClause_);

        setImplementsClause(_implementsClause_);

        setFileBody(_fileBody_);

    }

    @Override
    public Object clone()
    {
        return new AFile(
            cloneList(this._modifier_),
            cloneNode(this._fileType_),
            cloneNode(this._className_),
            cloneNode(this._extendsClause_),
            cloneNode(this._implementsClause_),
            cloneNode(this._fileBody_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAFile(this);
    }

    public LinkedList<PModifier> getModifier()
    {
        return this._modifier_;
    }

    public void setModifier(List<?> list)
    {
        for(PModifier e : this._modifier_)
        {
            e.parent(null);
        }
        this._modifier_.clear();

        for(Object obj_e : list)
        {
            PModifier e = (PModifier) obj_e;
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
            this._modifier_.add(e);
        }
    }

    public PFileType getFileType()
    {
        return this._fileType_;
    }

    public void setFileType(PFileType node)
    {
        if(this._fileType_ != null)
        {
            this._fileType_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._fileType_ = node;
    }

    public PClassName getClassName()
    {
        return this._className_;
    }

    public void setClassName(PClassName node)
    {
        if(this._className_ != null)
        {
            this._className_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._className_ = node;
    }

    public PExtendsClause getExtendsClause()
    {
        return this._extendsClause_;
    }

    public void setExtendsClause(PExtendsClause node)
    {
        if(this._extendsClause_ != null)
        {
            this._extendsClause_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._extendsClause_ = node;
    }

    public PImplementsClause getImplementsClause()
    {
        return this._implementsClause_;
    }

    public void setImplementsClause(PImplementsClause node)
    {
        if(this._implementsClause_ != null)
        {
            this._implementsClause_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._implementsClause_ = node;
    }

    public PFileBody getFileBody()
    {
        return this._fileBody_;
    }

    public void setFileBody(PFileBody node)
    {
        if(this._fileBody_ != null)
        {
            this._fileBody_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._fileBody_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._modifier_)
            + toString(this._fileType_)
            + toString(this._className_)
            + toString(this._extendsClause_)
            + toString(this._implementsClause_)
            + toString(this._fileBody_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._modifier_.remove(child))
        {
            return;
        }

        if(this._fileType_ == child)
        {
            this._fileType_ = null;
            return;
        }

        if(this._className_ == child)
        {
            this._className_ = null;
            return;
        }

        if(this._extendsClause_ == child)
        {
            this._extendsClause_ = null;
            return;
        }

        if(this._implementsClause_ == child)
        {
            this._implementsClause_ = null;
            return;
        }

        if(this._fileBody_ == child)
        {
            this._fileBody_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        for(ListIterator<PModifier> i = this._modifier_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PModifier) newChild);
                    newChild.parent(this);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

        if(this._fileType_ == oldChild)
        {
            setFileType((PFileType) newChild);
            return;
        }

        if(this._className_ == oldChild)
        {
            setClassName((PClassName) newChild);
            return;
        }

        if(this._extendsClause_ == oldChild)
        {
            setExtendsClause((PExtendsClause) newChild);
            return;
        }

        if(this._implementsClause_ == oldChild)
        {
            setImplementsClause((PImplementsClause) newChild);
            return;
        }

        if(this._fileBody_ == oldChild)
        {
            setFileBody((PFileBody) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
