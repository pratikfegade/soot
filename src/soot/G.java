package soot;

import soot.jimple.toolkits.typing.ClassHierarchy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class G {
    public static final Map<Scene, ClassHierarchy> classHierarchyMap = new ConcurrentHashMap<>();
}
