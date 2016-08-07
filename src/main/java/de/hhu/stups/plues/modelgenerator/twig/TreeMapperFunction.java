package de.hhu.stups.plues.modelgenerator.twig;

import com.google.common.base.Strings;
import de.hhu.stups.plues.data.entities.Level;
import de.hhu.stups.plues.data.entities.Module;
import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;

import java.math.BigDecimal;

class TreeMapperFunction extends SimpleJtwigFunction {
    private static final String INDENTATION = "    ";

    @Override
    public String name() {
        return "traverse";
    }

    @Override
    public Object execute(final FunctionRequest request) {
        request.minimumNumberOfArguments(2).maximumNumberOfArguments(2);
        final Level level = (Level) request.get(0);
        final Integer depth = ((BigDecimal) request.get(1)).intValue();

        final StringBuilder sb = new StringBuilder();
        traverse(level, depth, sb);
        return sb.toString();
    }

    private void traverse(final Level level, final Integer depth,
                          final StringBuilder sb) {
        final int newDepth = depth + 1;
        final String indent = Strings.repeat(INDENTATION, depth);
        sb.append(indent)
                .append("<l name=\"")
                .append(level.getName())
                .append("\" ").append(formatCreditPoints(level)).append(" ")
                .append(formatRequirements(level)).append(">\n");

        if (level.getChildren() != null && level.getChildren().size() > 0) {
            level.getChildren().forEach(l -> traverse(l, newDepth, sb));
        } else {
            level.getModules().forEach(m -> traverse(m, newDepth, sb));
        }

        sb.append("\n").append(indent)
                .append("</l>\n");
    }

    private void traverse(final Module module, final Integer depth,
                          final StringBuilder sb) {
        final String indent = Strings.repeat(INDENTATION, depth);
        sb.append(indent).append("<m name=\"")
                .append(module.getTitle())
                .append("\" ")
                .append(formatMandatory(module))
                .append(" pordnr=\"")
                .append(module.getPordnr())
                .append("\" ")
                .append(formatCreditPoints(module))
                .append(" />\n");
    }

    private String formatCreditPoints(final Level level) {
        if (level.getMinCreditPoints() < 0 || level.getMaxCreditPoints() < 0) {
            return "";
        }
        return "min-cp=\"" + level.getMinCreditPoints() + "\" "
                + "max-cp=\"" + level.getMaxCreditPoints() + "\"";
    }

    private String formatRequirements(final Level level) {
        if (level.getMin() < 0 || level.getMax() < 0) {
            return "";
        }
        return "min=\"" + level.getMin() + "\" max=\"" + level.getMax() + "\"";
    }

    private String formatCreditPoints(final Module module) {
        if (module.getCreditPoints() < 0) {
            return "";
        }
        return "cp=\"" + module.getCreditPoints() + "\"";
    }

    private String formatMandatory(final Module mod) {
        if (!mod.getMandatory()) {
            return "";
        }
        return "pflicht=\"j\"";
    }
}
