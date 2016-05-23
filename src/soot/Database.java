package soot;

import java.io.Closeable;
import java.io.Flushable;

public interface Database extends Closeable, Flushable
{
    void add(PredicateFile predFile, Column arg, Column... args);
    Column addEntity(PredicateFile predFile, String key);
    Column asColumn(String arg);
    Column asIntColumn(String arg);
    Column asEntity(String arg);
    Column asEntity(PredicateFile predFile, String arg);
}
