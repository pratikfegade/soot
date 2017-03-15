
package jastadd.soot.JastAddJ;

import java.io.IOException;
import java.io.InputStream;

public interface JavaParser {
    // Declared in ClassPath.jrag at line 19

    CompilationUnit parse(InputStream is, String fileName) throws IOException, jastadd.beaver.Parser.Exception;

}
