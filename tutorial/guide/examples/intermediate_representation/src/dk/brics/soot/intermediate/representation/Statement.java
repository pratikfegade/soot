package dk.brics.soot.intermediate.representation;


import dk.brics.soot.intermediate.representation.Variable.Type;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Superclass of all statements.
 * <p/>
 * A statements belongs to the body of some method.
 * It has control flow edges to and from other statements.
 */
public abstract class Statement {
    /* The method whose body contains this statement. */
    protected Method method;
    protected int index;
    /*
     * The target variable for assignment statements
     */
    protected Variable assignmentTarget;
    private Collection<Statement> succs;
    private Collection<Statement> preds;

    public Statement() {
        succs = new LinkedList<Statement>();
        preds = new LinkedList<Statement>();
        this.assignmentTarget = new Variable(Type.OTHER);
    }

    /**
     * Adds a control flow edge from this statement to the given.
     *
     * @param s the target statement of the edge.
     */
    public void addSucc(Statement s) {
        succs.add(s);
        s.addPred(this);
    }

    void addPred(Statement s) {
        preds.add(s);
    }

    /**
     * Returns all targets of control flow edges
     * originating from this node.
     *
     * @return a collection of {@link dk.brics.soot.intermediate.Statement} objects.
     */
    public Collection<Statement> getSuccs() {
        return succs;
    }

    /**
     * Returns all origins of control flow edges
     * going to this node.
     *
     * @return a collection of {@link dk.brics.soot.intermediate.Statement} objects.
     */
    public Collection<Statement> getPreds() {
        return preds;
    }

    /**
     * Returns the method whose body contains this statement.
     *
     * @return the method.
     */
    public Method getMethod() {
        return method;
    }

    /**
     * Set the method whose body contains this statement.
     */
    void setMethod(Method m) {
        method = m;
    }

    /**
     * Returns the index of this statement, indicating the sequence
     * number in which the statement was added to its method.
     *
     * @return the index.
     */
    public int getIndex() {
        return index;
    }

    void setIndex(int index) {
        this.index = index;
    }

    /**
     * Returns a string representation of this statement.
     * This is handled by a {@link dk.brics.soot.intermediate.ToStringVisitor}.
     *
     * @return the statement as a string.
     */
    public String toString() {
        ToStringVisitor tsv = new ToStringVisitor();
        return process(tsv);
    }

    /**
     * Visit this statement by the given statement visitor.
     * This will invoke the corresponding method in the visitor.
     *
     * @param <T> return type
     * @param v   Entry processor
     * @return result from processor
     */
    abstract public <T> T process(StatementProcessor<T> v);

    public Variable getAssignmentTarget() {
        return assignmentTarget;
    }

    public void setAssignmentTarget(Variable assignmentTarget) {
        this.assignmentTarget = assignmentTarget;
    }
}
