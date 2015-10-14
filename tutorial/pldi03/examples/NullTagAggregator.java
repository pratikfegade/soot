import soot.jimple.toolkits.annotation.tags.NullCheckTag;
import soot.tagkit.ImportantTagAggregator;
import soot.tagkit.Tag;

/**
 * The aggregator for NullCheckAttribute.
 */

public class NullTagAggregator extends ImportantTagAggregator {
    public NullTagAggregator() {
    }

    public boolean wantTag(Tag t) {
        return (t instanceof NullCheckTag);
    }

    public String aggregatedName() {
        return "NullCheckAttribute";
    }
}








