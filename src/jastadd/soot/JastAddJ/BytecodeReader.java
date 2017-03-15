
package jastadd.soot.JastAddJ;

import java.io.IOException;
import java.io.InputStream;

public interface BytecodeReader {
    // Declared in ClassPath.jrag at line 16

    CompilationUnit read(InputStream is, String fullName, Program p) throws IOException;

}
