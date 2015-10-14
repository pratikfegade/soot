package soot.JastAddJ;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Loads class files from a zip file (Jar file)
 *
 * @ast class
 */
public class ZipFilePart extends PathPart {

    private HashSet set = new HashSet();


    private ZipFile file;


    private String zipPath;


    public ZipFilePart(ZipFile file, String path) {
        zipPath = path;
        this.file = file;
        // process all entries in the zip file
        for (Enumeration e = file.entries(); e.hasMoreElements(); ) {
            ZipEntry entry = (ZipEntry) e.nextElement();
            String pathName = new File(entry.getName()).getParent();
            if (pathName != null)
                pathName = pathName.replace(File.separatorChar, '.');
            if (!set.contains(pathName)) {
                int pos = 0;
                while (pathName != null && -1 != (pos = pathName.indexOf('.', pos + 1))) {
                    String n = pathName.substring(0, pos);
                    if (!set.contains(n)) {
                        set.add(n);
                    }
                }
                set.add(pathName);
            }
            set.add(entry.getName());
        }
    }


    public ZipFilePart(ZipFile file) {
        this(file, file.getName());
    }

    public boolean hasPackage(String name) {
        return set.contains(name);
    }

    public boolean selectCompilationUnit(String canonicalName) throws IOException {
        String name = canonicalName.replace('.', '/'); // ZipFiles always use '/' as separator
        name = name + fileSuffix();
        if (set.contains(name)) {
            ZipEntry zipEntry = file.getEntry(name);
            if (zipEntry != null && !zipEntry.isDirectory()) {
                is = file.getInputStream(zipEntry);
                age = zipEntry.getTime();
                pathName = zipPath;
                relativeName = name + fileSuffix();
                fullName = canonicalName;
                return true;
            }
        }
        return false;
    }


}
