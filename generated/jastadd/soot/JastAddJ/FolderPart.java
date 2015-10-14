package soot.JastAddJ;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * @ast class
 */
public class FolderPart extends PathPart {


    /**
     * The root folder of this path part.
     */
    private final File folder;
    /**
     * Maps package names to a collection of the names of files in
     * the corresponding package directory.
     */
    private Map<String, Collection<String>> packageMap =
            new HashMap<String, Collection<String>>();


    public FolderPart(File folder) {
        this.folder = folder;
    }


    /**
     * @param name The qualified package name
     * @return <code>true</code> if the given package exists in this source
     * folder
     */
    public boolean hasPackage(String name) {
        return !filesInPackage(name).isEmpty();
    }


    public boolean hasCompilationUnit(String canonicalName) {
        int index = canonicalName.lastIndexOf('.');
        String packageName = index == -1 ? "" : canonicalName.substring(0, index);
        String typeName = canonicalName.substring(index + 1, canonicalName.length());
        String fileName = typeName + fileSuffix();
        return filesInPackage(packageName).contains(fileName);
    }


    /**
     * We need to use getCanonicalFile in order to get the case-sensitive
     * package name on case-insensitive file systems or we might incorrectly
     * report a package name conflict.
     * <p/>
     * NB: This does not work well with symlinks!
     *
     * @param packageName The qualified name of the package
     * @return The names of the files and folders in the package
     */
    private Collection<String> filesInPackage(String packageName) {
        if (!packageMap.containsKey(packageName)) {
            int index = packageName.lastIndexOf('.');
            String name = packageName.substring(index == -1 ? 0 : index + 1);
            String folderName = packageName.replace('.', File.separatorChar);
            File pkgFolder = new File(folder, folderName);
            Collection<String> fileSet = Collections.emptyList();
            try {
                // Make sure that there exists a directory with the same name
                // (case-sensitive) as the requested package
                File canonical = pkgFolder.getCanonicalFile();
                if (canonical.isDirectory() && (packageName.isEmpty() ||
                        canonical.getName().equals(name))) {
                    String[] files = canonical.list();
                    if (files.length > 0) {
                        fileSet = new HashSet<String>();
                        for (String file : files) {
                            fileSet.add(file);
                        }
                    }
                }
            } catch (Exception e) {
                // Catch IOExceptions etc.
                // if the exception was thrown by getCanonicalFile we will put
                // the empty list in the packageMap, indicating that the package
                // does not exist
            }
            packageMap.put(packageName, fileSet);
        }
        return packageMap.get(packageName);
    }


    public boolean selectCompilationUnit(String canonicalName) throws IOException {
        if (hasCompilationUnit(canonicalName)) {
            String typeName = canonicalName.replace('.', File.separatorChar);
            String fileName = typeName + fileSuffix();
            File classFile = new File(folder, fileName);
            if (classFile.isFile()) {
                is = new FileInputStream(classFile);
                age = classFile.lastModified();
                pathName = classFile.getPath();
                relativeName = fileName + fileSuffix();
                fullName = canonicalName;
                return true;
            }
        }
        return false;
    }


    @Override
    public String toString() {
        return folder.toString();
    }


}
