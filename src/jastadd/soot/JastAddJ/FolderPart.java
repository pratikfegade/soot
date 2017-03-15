
package jastadd.soot.JastAddJ;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
// load files from a folder
public class FolderPart extends PathPart {
    // Declared in ClassPath.jrag at line 385

    private HashMap map = new HashMap();

    // Declared in ClassPath.jrag at line 386

    private File folder;

    // Declared in ClassPath.jrag at line 388


    public FolderPart(File folder) {
      this.folder = folder;
    }

    // Declared in ClassPath.jrag at line 392


    public boolean hasPackage(String name) {
      return filesInPackage(name) != null;
    }

    // Declared in ClassPath.jrag at line 396


    public boolean hasCompilationUnit(String canonicalName) {
      int index = canonicalName.lastIndexOf('.');
      String packageName = index == -1 ? "" : canonicalName.substring(0, index);
      String typeName = canonicalName.substring(index + 1, canonicalName.length());
      Collection c = filesInPackage(packageName);
      boolean result = c != null && c.contains(typeName + fileSuffix());
      return result;
    }

    // Declared in ClassPath.jrag at line 405

    
    private Collection filesInPackage(String packageName) {
      if(!map.containsKey(packageName)) {
        File f = new File(folder, packageName.replace('.', File.separatorChar));
        Collection c = Collections.EMPTY_LIST;
        if(f.exists() && f.isDirectory()) {
          String[] files = f.list();
          if(files.length > 0) {
            c = new HashSet();
            for(int i = 0; i < files.length; i++)
              c.add(files[i]);
          }
        }
        else
          c = null;
        map.put(packageName, c);
      }
      return (Collection)map.get(packageName);
    }

    // Declared in ClassPath.jrag at line 424

    
    public boolean selectCompilationUnit(String canonicalName) throws IOException {
      if(hasCompilationUnit(canonicalName)) {
        String fileName = canonicalName.replace('.', File.separatorChar);
        File classFile = new File(folder, fileName + fileSuffix());
        if(classFile.isFile()) {
          is = new FileInputStream(classFile);
          age = classFile.lastModified();
          pathName = classFile.getAbsolutePath();
          relativeName = fileName + fileSuffix();
          fullName = canonicalName;
          return true;
        }
      }
      return false;
    }


}
