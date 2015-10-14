package soot.JastAddJ;

import java.io.IOException;
import java.io.InputStream;

/**
 * @ast interface
 */
public interface JavaParser {


    CompilationUnit parse(InputStream is, String fileName) throws IOException, beaver.Parser.Exception;
}
