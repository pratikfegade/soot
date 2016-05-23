
/* Soot - a J*va Optimization Framework
 * Copyright (C) 2003 Ondrej Lhotak
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 */

/* THIS FILE IS AUTO-GENERATED FROM soot_options.xml. DO NOT MODIFY. */

package soot.options;
import soot.*;
import java.util.*;

/** Soot command-line options parser.
 * @author Ondrej Lhotak
 */

public class Options extends OptionsBase {
    public Options(Singletons.Global g) { }
    public static Options v() { return G.v().soot_options_Options(); }


    public static final int src_prec_class = 1;
    public static final int src_prec_only_class = 2;
    public static final int src_prec_jimple = 3;
    public static final int src_prec_java = 4;
    public static final int src_prec_apk = 5;
    public static final int output_format_jimple = 1;
    public static final int output_format_jimp = 2;
    public static final int output_format_shimple = 3;
    public static final int output_format_shimp = 4;
    public static final int output_format_dex = 10;
    public static final int output_format_force_dex = 11;
    public static final int output_format_none = 12;
    public static final int output_format_class = 14;
    public static final int output_format_dava = 15;
    public static final int output_format_template = 16;
    public static final int java_version_default = 1;
    public static final int java_version_1_1 = 2;
    public static final int java_version_1_2 = 3;
    public static final int java_version_1_3 = 4;
    public static final int java_version_1_4 = 5;
    public static final int java_version_1_5 = 6;
    public static final int java_version_1_6 = 7;
    public static final int java_version_1_7 = 8;
    public static final int java_version_1_8 = 9;
    public static final int throw_analysis_pedantic = 1;
    public static final int throw_analysis_unit = 2;
    public static final int check_init_throw_analysis_auto = 1;
    public static final int check_init_throw_analysis_pedantic = 2;
    public static final int check_init_throw_analysis_unit = 3;
    public static final int check_init_throw_analysis_dalvik = 4;

    public boolean coffi() { return coffi; }
    private boolean coffi = false;

    public boolean verbose() { return verbose; }
    private boolean verbose = false;

    public boolean interactive_mode() { return interactive_mode; }
    private boolean interactive_mode = false;
    public void set_interactive_mode( boolean setting ) { interactive_mode = setting; }

    public boolean app() { return app; }
    private boolean app = false;

    public boolean whole_program() { return whole_program; }
    private boolean whole_program = false;

    public boolean whole_shimple() { return whole_shimple; }
    private boolean whole_shimple = false;

    public boolean on_the_fly() { return on_the_fly; }
    private boolean on_the_fly = false;
    public void set_on_the_fly( boolean setting ) { on_the_fly = setting; }
  
    public boolean validate() { return validate; }
    private boolean validate = false;

    public boolean debug() { return debug; }
    private boolean debug = false;

    public boolean debug_resolver() { return debug_resolver; }
    private boolean debug_resolver = false;

    public String soot_classpath() { return soot_classpath; }
    public void set_soot_classpath( String setting ) { soot_classpath = setting; }
    private String soot_classpath = "";
    public boolean prepend_classpath() { return prepend_classpath; }
    private boolean prepend_classpath = false;

    public List<String> process_dir() { 
        if( process_dir == null )
            return java.util.Collections.emptyList();
        else
            return process_dir;
    }

    private List<String> process_dir = null;
    public boolean oaat() { return oaat; }
    private boolean oaat = false;

    public String android_jars() { return android_jars; }

    private String android_jars = "";
    public String force_android_jar() { return force_android_jar; }

    private String force_android_jar = "";

    public int src_prec() {
        if( src_prec == 0 ) return src_prec_class;
        return src_prec; 
    }

    private int src_prec = 0;
    public boolean full_resolver() { return full_resolver; }
    private boolean full_resolver = false;
    public void set_full_resolver( boolean setting ) { full_resolver = setting; }
  
    public boolean allow_phantom_refs() { return allow_phantom_refs; }
    private boolean allow_phantom_refs = false;
    public void set_allow_phantom_refs( boolean setting ) { allow_phantom_refs = setting; }
  
    public boolean no_bodies_for_excluded() { return no_bodies_for_excluded; }
    private boolean no_bodies_for_excluded = false;

    public boolean j2me() { return j2me; }
    private boolean j2me = false;

    public String main_class() { return main_class; }
    public void set_main_class( String setting ) { main_class = setting; }
    private String main_class = "";
    public boolean polyglot() { return polyglot; }
    private boolean polyglot = false;

    public String output_dir() { return output_dir; }

    private String output_dir = "";
    public int output_format() {
        if( output_format == 0 ) return output_format_class;
        return output_format; 
    }

    private int output_format = 0;

    public boolean output_jar() { return output_jar; }
    private boolean output_jar = false;

    public boolean print_tags_in_output() { return print_tags_in_output; }
    private boolean print_tags_in_output = false;

    public boolean no_output_source_file_attribute() { return no_output_source_file_attribute; }
    private boolean no_output_source_file_attribute = false;

    public boolean no_output_inner_classes_attribute() { return no_output_inner_classes_attribute; }
    private boolean no_output_inner_classes_attribute = false;

    public List<String> dump_body() { 
        if( dump_body == null )
            return java.util.Collections.emptyList();
        else
            return dump_body;
    }

    private List<String> dump_body = null;
    public List<String> dump_cfg() { 
        if( dump_cfg == null )
            return java.util.Collections.emptyList();
        else
            return dump_cfg;
    }

    private List<String> dump_cfg = null;
    public boolean show_exception_dests() { return show_exception_dests; }
    private boolean show_exception_dests = false;


    public boolean force_overwrite() { return force_overwrite; }
    private boolean force_overwrite = false;

    public int throw_analysis() {
        if( throw_analysis == 0 ) return throw_analysis_unit;
        return throw_analysis; 
    }

    private int throw_analysis = 0;
    public int check_init_throw_analysis() {
        if( check_init_throw_analysis == 0 ) return check_init_throw_analysis_auto;
        return check_init_throw_analysis; 
    }

    private int check_init_throw_analysis = 0;
    public boolean omit_excepting_unit_edges() { return omit_excepting_unit_edges; }
    private boolean omit_excepting_unit_edges = false;

    public boolean ignore_resolution_errors() { return ignore_resolution_errors; }
    private boolean ignore_resolution_errors = false;

    public List<String> include() { 
        if( include == null )
            return java.util.Collections.emptyList();
        else
            return include;
    }

    private List<String> include = null;
    public List<String> exclude() { 
        if( exclude == null )
            return java.util.Collections.emptyList();
        else
            return exclude;
    }

    private List<String> exclude = null;
    public boolean include_all() { return include_all; }
    private boolean include_all = false;

    public List<String> dynamic_class() { 
        if( dynamic_class == null )
            return java.util.Collections.emptyList();
        else
            return dynamic_class;
    }

    private List<String> dynamic_class = null;
    public List<String> dynamic_dir() { 
        if( dynamic_dir == null )
            return java.util.Collections.emptyList();
        else
            return dynamic_dir;
    }

    private List<String> dynamic_dir = null;
    public List<String> dynamic_package() { 
        if( dynamic_package == null )
            return java.util.Collections.emptyList();
        else
            return dynamic_package;
    }

    private List<String> dynamic_package = null;
    public boolean keep_line_number() { return keep_line_number; }
    private boolean keep_line_number = false;
    public void set_keep_line_number( boolean setting ) { keep_line_number = setting; }
  
    public boolean keep_offset() { return keep_offset; }
    private boolean keep_offset = false;

    public boolean time() { return time; }
    private boolean time = false;

    public boolean subtract_gc() { return subtract_gc; }
    private boolean subtract_gc = false;


    public String getUsage() {
        return ""

+"\nGeneral Options:\n"
      
+padOpt(" -coffi", "Use the good old Coffi front end for parsing Java bytecode (instead of using ASM)." )
+padOpt(" -asm-backend", "Use the ASM back end for generating Java bytecode (instead of using Jasmin)." )
+padOpt(" -h -help", "Display help and exit" )
+padOpt(" -pl -phase-list", "Print list of available phases" )
+padOpt(" -ph PHASE -phase-help PHASE", "Print help for specified PHASE" )
+padOpt(" -version", "Display version information and exit" )
+padOpt(" -v -verbose", "Verbose mode" )
+padOpt(" -interactive-mode", "Run in interactive mode" )
+padOpt(" -unfriendly-mode", "Allow Soot to run with no command-line options" )
+padOpt(" -app", "Run in application mode" )
+padOpt(" -w -whole-program", "Run in whole-program mode" )
+padOpt(" -ws -whole-shimple", "Run in whole-shimple mode" )
+padOpt(" -fly -on-the-fly", "Run in on-the-fly mode" )
+padOpt(" -validate", "Run internal validation on bodies" )
+padOpt(" -debug", "Print various Soot debugging info" )
+padOpt(" -debug-resolver", "Print debugging info from SootResolver" )
+"\nInput Options:\n"
      
+padOpt(" -cp PATH -soot-class-path PATH -soot-classpath PATH", "Use PATH as the classpath for finding classes." )
+padOpt(" -pp -prepend-classpath", "Prepend the given soot classpath to the default classpath." )
+padOpt(" -process-path DIR -process-dir DIR", "Process all classes found in DIR" )
+padOpt(" -oaat", "From the process-dir, processes one class at a time." )
+padOpt(" -android-jars PATH", "Use PATH as the path for finding the android.jar file" )
+padOpt(" -force-android-jar PATH", "Force Soot to use PATH as the path for the android.jar file." )
+padOpt(" -ast-metrics", "Compute AST Metrics if performing java to jimple" )
+padOpt(" -src-prec FORMAT", "Sets source precedence to FORMAT files" )
+padVal(" c class (default)", "Favour class files as Soot source" )
+padVal(" only-class", "Use only class files as Soot source" )
+padVal(" J jimple", "Favour Jimple files as Soot source" )
+padVal(" java", "Favour Java files as Soot source" )
+padVal(" apk", "Favour APK files as Soot source" )
+padOpt(" -full-resolver", "Force transitive resolving of referenced classes" )
+padOpt(" -allow-phantom-refs", "Allow unresolved classes; may cause errors" )
+padOpt(" -no-bodies-for-excluded", "Do not load bodies for excluded classes" )
+padOpt(" -j2me", "Use J2ME mode; changes assignment of types" )
+padOpt(" -main-class CLASS", "Sets the main class for whole-program analysis." )
+padOpt(" -polyglot", "Use Java 1.4 Polyglot frontend instead of JastAdd" )
+"\nOutput Options:\n"
      
+padOpt(" -d DIR -output-dir DIR", "Store output files in DIR" )
+padOpt(" -f FORMAT -output-format FORMAT", "Set output format for Soot" )
+padVal(" J jimple", "Produce .jimple Files" )
+padVal(" j jimp", "Produce .jimp (abbreviated Jimple) files" )
+padVal(" S shimple", "Produce .shimple files" )
+padVal(" s shimp", "Produce .shimp (abbreviated Shimple) files" )
+padVal(" B baf", "Produce .baf files" )
+padVal(" b", "Produce .b (abbreviated Baf) files" )
+padVal(" G grimple", "Produce .grimple files" )
+padVal(" g grimp", "Produce .grimp (abbreviated Grimp) files" )
+padVal(" X xml", "Produce .xml Files" )
+padVal(" dex", "Produce Dalvik Virtual Machine files" )
+padVal(" force-dex", "Produce Dalvik DEX files" )
+padVal(" n none", "Produce no output" )
+padVal(" jasmin", "Produce .jasmin files" )
+padVal(" c class (default)", "Produce .class Files" )
+padVal(" d dava", "Produce dava-decompiled .java files" )
+padVal(" t template", "Produce .java files with Jimple templates." )
+padVal(" a asm", "Produce .asm files as textual bytecode representation generated with the ASM back end." )
+padOpt(" -java-version VERSION", "Force Java version of bytecode generated by Soot." )
+padVal(" default", "Let Soot determine Java version of generated bytecode." )
+padVal(" 1.1 1", "Force Java 1.1 as output version." )
+padVal(" 1.2 2", "Force Java 1.2 as output version." )
+padVal(" 1.3 3", "Force Java 1.3 as output version." )
+padVal(" 1.4 4", "Force Java 1.4 as output version." )
+padVal(" 1.5 5", "Force Java 1.5 as output version." )
+padVal(" 1.6 6", "Force Java 1.6 as output version." )
+padVal(" 1.7 7", "Force Java 1.7 as output version." )
+padVal(" 1.8 8", "Force Java 1.8 as output version." )
+padOpt(" -outjar -output-jar", "Make output dir a Jar file instead of dir" )
+padOpt(" -xml-attributes", "Save tags to XML attributes for Eclipse" )
+padOpt(" -print-tags -print-tags-in-output", "Print tags in output files after stmt" )
+padOpt(" -no-output-source-file-attribute", "Don't output Source File Attribute when producing class files" )
+padOpt(" -no-output-inner-classes-attribute", "Don't output inner classes attribute in class files" )
+padOpt(" -dump-body PHASENAME", "Dump the internal representation of each method before and after phase PHASENAME" )
+padOpt(" -dump-cfg PHASENAME", "Dump the internal representation of each CFG constructed during phase PHASENAME" )
+padOpt(" -show-exception-dests", "Include exception destination edges as well as CFG edges in dumped CFGs" )
+padOpt(" -gzip", "GZip IR output files" )
+padOpt(" -force-overwrite", "Force Overwrite Output Files" )
+"\nProcessing Options:\n"
      
+padOpt(" -plugin FILE", "Load all plugins found in FILE" )
+padOpt(" -p PHASE OPT:VAL -phase-option PHASE OPT:VAL", "Set PHASE's OPT option to VALUE" )
+padOpt(" -O -optimize", "Perform intraprocedural optimizations" )
+padOpt(" -W -whole-optimize", "Perform whole program optimizations" )
+padOpt(" -via-grimp", "Convert to bytecode via Grimp instead of via Baf" )
+padOpt(" -via-shimple", "Enable Shimple SSA representation" )
+padOpt(" -throw-analysis ARG", "" )
+padVal(" pedantic", "Pedantically conservative throw analysis" )
+padVal(" unit (default)", "Unit Throw Analysis" )
+padOpt(" -check-init-ta ARG -check-init-throw-analysis ARG", "" )
+padVal(" auto (default)", "Automatically select a throw analysis" )
+padVal(" pedantic", "Pedantically conservative throw analysis" )
+padVal(" unit", "Unit Throw Analysis" )
+padVal(" dalvik", "Dalvik Throw Analysis" )
+padOpt(" -omit-excepting-unit-edges", "Omit CFG edges to handlers from excepting units which lack side effects" )
+padOpt(" -trim-cfgs", "Trim unrealizable exceptional edges from CFGs" )
+padOpt(" -ire -ignore-resolution-errors", "Does not throw an exception when a program references an undeclared field or method." )
+"\nApplication Mode Options:\n"
      
+padOpt(" -i PKG -include PKG", "Include classes in PKG as application classes" )
+padOpt(" -x PKG -exclude PKG", "Exclude classes in PKG from application classes" )
+padOpt(" -include-all", "Set default excluded packages to empty list" )
+padOpt(" -dynamic-class CLASS", "Note that CLASS may be loaded dynamically" )
+padOpt(" -dynamic-dir DIR", "Mark all classes in DIR as potentially dynamic" )
+padOpt(" -dynamic-package PKG", "Marks classes in PKG as potentially dynamic" )
+"\nInput Attribute Options:\n"
      
+padOpt(" -keep-line-number", "Keep line number tables" )
+padOpt(" -keep-bytecode-offset -keep-offset", "Attach bytecode offset to IR" )
+"\nAnnotation Options:\n"
      
+padOpt(" -annot-purity", "Emit purity attributes" )
+padOpt(" -annot-nullpointer", "Emit null pointer attributes" )
+padOpt(" -annot-arraybounds", "Emit array bounds check attributes" )
+padOpt(" -annot-side-effect", "Emit side-effect attributes" )
+padOpt(" -annot-fieldrw", "Emit field read/write attributes" )
+"\nMiscellaneous Options:\n"
      
+padOpt(" -time", "Report time required for transformations" )
+padOpt(" -subtract-gc", "Subtract gc from time" )
+padOpt(" -no-writeout-body-releasing", "Disables the release of method bodies after writeout. This flag is used internally." );
    }


    public static String getDeclaredOptionsForPhase( String phaseName ) {
    
        if( phaseName.equals( "jb" ) )
            return ""
                +"enabled "
                +"use-original-names "
                +"preserve-source-annotations ";
    
        if( phaseName.equals( "jb.ls" ) )
            return ""
                +"enabled ";
    
        if( phaseName.equals( "jb.a" ) )
            return ""
                +"enabled "
                +"only-stack-locals ";
    
        if( phaseName.equals( "jb.ule" ) )
            return ""
                +"enabled ";
    
        if( phaseName.equals( "jb.tr" ) )
            return ""
                +"enabled "
                +"ignore-wrong-staticness "
                +"use-older-type-assigner "
                +"compare-type-assigners ";
    
        if( phaseName.equals( "jb.ulp" ) )
            return ""
                +"enabled "
                +"unsplit-original-locals ";
    
        if( phaseName.equals( "jb.lns" ) )
            return ""
                +"enabled "
                +"only-stack-locals ";
    
        if( phaseName.equals( "jb.cp" ) )
            return ""
                +"enabled "
                +"only-regular-locals "
                +"only-stack-locals ";
    
        if( phaseName.equals( "jb.dae" ) )
            return ""
                +"enabled "
                +"only-stack-locals ";
    
        if( phaseName.equals( "jb.cp-ule" ) )
            return ""
                +"enabled ";
    
        if( phaseName.equals( "jb.lp" ) )
            return ""
                +"enabled "
                +"unsplit-original-locals ";
    
        if( phaseName.equals( "jb.ne" ) )
            return ""
                +"enabled ";
    
        if( phaseName.equals( "jb.uce" ) )
            return ""
                +"enabled "
                +"remove-unreachable-traps ";
    
        if( phaseName.equals( "jb.tt" ) )
            return ""
                +"enabled ";
    

    
        // The default set of options is just enabled.
        return "enabled";
    }

    public static String getDefaultOptionsForPhase( String phaseName ) {
    
        if( phaseName.equals( "jb" ) )
            return ""
              +"enabled:true "
              +"use-original-names:false "
              +"preserve-source-annotations:false ";
    
        if( phaseName.equals( "jb.ls" ) )
            return ""
              +"enabled:true ";
    
        if( phaseName.equals( "jb.a" ) )
            return ""
              +"enabled:true "
              +"only-stack-locals:true ";
    
        if( phaseName.equals( "jb.ule" ) )
            return ""
              +"enabled:true ";
    
        if( phaseName.equals( "jb.tr" ) )
            return ""
              +"enabled:true "
              +"ignore-wrong-staticness:false "
              +"use-older-type-assigner:false "
              +"compare-type-assigners:false ";
    
        if( phaseName.equals( "jb.ulp" ) )
            return ""
              +"enabled:true "
              +"unsplit-original-locals:true ";
    
        if( phaseName.equals( "jb.lns" ) )
            return ""
              +"enabled:true "
              +"only-stack-locals:false ";
    
        if( phaseName.equals( "jb.cp" ) )
            return ""
              +"enabled:true "
              +"only-regular-locals:false "
              +"only-stack-locals:true ";
    
        if( phaseName.equals( "jb.dae" ) )
            return ""
              +"enabled:true "
              +"only-stack-locals:true ";
    
        if( phaseName.equals( "jb.cp-ule" ) )
            return ""
              +"enabled:true ";
    
        if( phaseName.equals( "jb.lp" ) )
            return ""
              +"enabled:false "
              +"unsplit-original-locals:false ";
    
        if( phaseName.equals( "jb.ne" ) )
            return ""
              +"enabled:true ";
    
        if( phaseName.equals( "jb.uce" ) )
            return ""
              +"enabled:true "
              +"remove-unreachable-traps:true ";
    
        if( phaseName.equals( "jb.tt" ) )
            return ""
              +"enabled:false ";
    

        // The default default value is enabled.
        return "enabled";
    }

}
