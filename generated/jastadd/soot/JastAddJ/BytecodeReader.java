package soot.JastAddJ;

import java.io.IOException;
import java.io.InputStream;
/**
  * @ast interface
 * 
 */
public interface BytecodeReader {

     
    CompilationUnit read(InputStream is, String fullName, Program p) throws IOException;
}
