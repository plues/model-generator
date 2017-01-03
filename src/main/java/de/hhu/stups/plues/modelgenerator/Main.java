package de.hhu.stups.plues.modelgenerator;

import de.hhu.stups.plues.data.IncompatibleSchemaError;
import de.hhu.stups.plues.data.SqliteStore;
import de.hhu.stups.plues.data.Store;
import de.hhu.stups.plues.data.StoreException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.File;
import java.io.IOException;

public class Main {
  private static final String OUTPUT = "output";
  private static final String VERSION = "4.3.0";

  private Main() {}

  /**
   * CLI entry point.
   *
   * @param args command line arguments arguments
   * @throws IncompatibleSchemaError if database is not compatible with current version.
   * @throws StoreException          if there is a problem opening the store.
   * @throws IOException             if there is an IO problem with the store or the generated
   *                                 files.
   */
  public static void main(final String[] args) throws IncompatibleSchemaError,
      StoreException, IOException {
    printVersion();
    final CommandLine line = getCommandLine(args);
    if (line == null) {
      return;
    }


    final Store store = new SqliteStore(line.getOptionValue("database"));
    final Renderer renderer = new Renderer(store);
    final String output = line.getOptionValue(OUTPUT)
        .replaceFirst("^~", System.getProperty("user.home"));

    final String template = line.getOptionValue("template");
    final FileType ft = getFileType(template);


    final File out = new File(output);
    if (ft == FileType.UNKNOWN) {
      final String tmpl = template.replaceFirst("^~", System.getProperty("user.home"));
      renderer.renderWith(tmpl, out);
    } else {
      renderer.renderFor(ft, out);
    }

    System.out.println("Wrote to " + line.getOptionValue(OUTPUT));
    store.close();
  }

  private static FileType getFileType(final String template) {
    /* If no template is provided we default to generating a B (.mch) file */
    if (template != null) {
      FileType ft = FileType.UNKNOWN;
      for (final FileType i : FileType.values()) {
        if (i.typeName.equals(template)) {
          ft = i;
        }
      }
      return ft;
    }
    return FileType.B_MACHINE;
  }

  private static CommandLine getCommandLine(final String[] args) {
    final String usage = "java -jar JARFILE --output=<path> "
        + "--database=<path> [--template=<path>]\n"
        + "If no template is provided, the default is to "
        + "generate a B-Machine (.mch)";

    final Options options = getOptions();

    final CommandLineParser parser = new DefaultParser();

    try {
      // parse the command line arguments
      return parser.parse(options, args);
    } catch (final ParseException exp) {
      // oops, something went wrong
      System.err.println("Parsing failed.  Reason: " + exp.getMessage());
      final HelpFormatter formatter = new HelpFormatter();
      formatter.printHelp(usage, options);
    }
    return null;
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
}
