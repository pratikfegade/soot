package soot.toDex.instructions;

import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.builder.BuilderInstruction;
import soot.toDex.LabelAssigner;
import soot.toDex.Register;

import java.util.BitSet;
import java.util.List;

/**
 * Interface for the dalvik instruction formats.
 */
public interface Insn extends Cloneable {

    Opcode getOpcode();

    List<Register> getRegs();

    BitSet getIncompatibleRegs();

    boolean hasIncompatibleRegs();

    int getMinimumRegsNeeded();

    BuilderInstruction getRealInsn(LabelAssigner assigner);

    int getSize();
}