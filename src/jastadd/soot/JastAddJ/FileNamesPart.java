
package jastadd.soot.JastAddJ;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
// load files specified explicitly (on the command line)
public class FileNamesPart extends PathPart {
    // Declared in ClassPath.jrag at line 492

    private HashMap sourceFiles = new HashMap();

    // Declared in ClassPath.jrag at line 493

    private HashSet packages = new HashSet();

    // Declared in ClassPath.jrag at line 495


    public FileNamesPart(Program p) {
      isSource = true;
      program = p;
    }

    // Declared in ClassPath.jrag at line 500


    public boolean hasPackage(String name) { return packages.contains(name); }

    // Declared in ClassPath.jrag at line 501

    public boolean isEmpty() { return sourceFiles.isEmpty(); }

    // Declared in ClassPath.jrag at line 502

    public Collection keySet() { return sourceFiles.keySet(); }

    // Declared in ClassPath.jrag at line 504


    public boolean selectCompilationUnit(String canonicalName) throws IOException {
      if(sourceFiles.containsKey(canonicalName)) {
        String f = (String)sourceFiles.get(canonicalName);
        File classFile = new File(f);
        if(classFile.isFile()) {
          is = new FileInputStream(classFile);
          pathName = classFile.getAbsolutePath(); // TODO: check me"";
          relativeName = f;
          fullName = canonicalName;
          sourceFiles.remove(canonicalName);
          return true;
        }
      }
      return false;
    }

    // Declared in ClassPath.jrag at line 519

    public void addSourceFile(String name) {
      try {
        File classFile = new File(name);
        if(classFile.isFile()) {
          is = new FileInputStream(classFile);
          this.pathName = classFile.getAbsolutePath();
          relativeName = name;
          fullName = name; // is this ok
          CompilationUnit u = getCompilationUnit();
          if(u != null) {
            program.addCompilationUnit(u);
            String packageName = u.getPackageDecl();
            if(packageName != null && !packages.contains(packageName)) {
              packages.add(packageName);
              int pos = 0;
              while(packageName != null && -1 != (pos = packageName.indexOf('.', pos + 1))) {
                String n = packageName.substring(0, pos);
                if(!packages.contains(n))
                  packages.add(n);
              }
            }
          }
        }
      } catch (IOException e) {
      }
    }


}
