package de.hhu.stups.plues.modelgenerator;

import de.hhu.stups.plues.data.IncompatibleSchemaError;
import de.hhu.stups.plues.data.SQLiteStore;
import de.hhu.stups.plues.data.Store;
import de.hhu.stups.plues.data.StoreException;
import org.apache.commons.cli.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(final String[] args) throws IncompatibleSchemaError,
                                                        StoreException, IOException {
        printVersion();
        final CommandLine line = getCommandLine(args);


        final Store store = new SQLiteStore(line.getOptionValue("database"));
        final Renderer renderer = new Renderer(store);
        final String output = line.getOptionValue("output")
                .replaceFirst("^~", System.getProperty("user.home"));

        FileType ft;
        final String template = line.getOptionValue("template");
        /* If no template is provided we default to generating a B (.mch)
        file */
        if (template != null) {
            ft = FileType.Unknown;
            for (final FileType i : FileType.values()) {
                if (i.name.equals(template)) {
                    ft = i;
                }
            }
        } else {
            ft = FileType.BMachine;
        }


        final ByteArrayOutputStream result;
        File out = new File(output);
        if (ft == FileType.Unknown) {
            final String tmpl = template
                    .replaceFirst("^~", System.getProperty("user.home"));
            renderer.renderWith(tmpl, out);
        } else {
            renderer.renderFor(ft, out);
        }

        System.out.println("Wrote to " + line.getOptionValue("output"));
        store.close();
    }

    private static CommandLine getCommandLine(final String[] args) {
        final String usage = "java -jar JARFILE --output=<path> "
                + "--database=<path> [--template=<path>]\n"
                + "If no template is provided, the default is to "
                + "generate a B-Machine (.mch)";

        final Options options = getOptions();

        final CommandLineParser parser = new DefaultParser();
        CommandLine line = null;
        try {
            // parse the command line arguments
            line = parser.parse(options, args);
        } catch (final ParseException exp) {
            // oops, something went wrong
            System.err.println("Parsing failed.  Reason: " + exp.getMessage());
            final HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(usage, options);
            System.exit(0);
        }
        return line;
    }

    private static Options getOptions() {
        final Options options = new Options();

        final Option t = new Option("t", "template", true,
                "External template to be used for rendering");
        final Option o = new Option("o", "output", true, "Target File");
        final Option d = new Option("d", "database", true, "SQLite 3 Database");

        o.setRequired(true);
        d.setRequired(true);

        options.addOption(t);
        options.addOption(o);
        options.addOption(d);

        return options;
    }

    private static void printVersion() {
        System.out.println("model-generator: Version " + VERSION);
    }

    private static final String VERSION = "3.0.0-SNAPSHOT";
}
