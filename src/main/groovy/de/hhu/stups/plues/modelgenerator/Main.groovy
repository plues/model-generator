package de.hhu.stups.plues.modelgenerator

import de.hhu.stups.plues.data.SQLiteStore

class Main {

    static String VERSION = "3.0.0-SNAPSHOT"

    public static void main(String[] args) {
        printVersion()
        /*
         * http://marxsoftware.blogspot.de/2010/07/explicitly-specifying-args-property.html
         */
        def cli = new CliBuilder(usage: 'java -jar JARFILE --output=<path> --database=<path> [--template=<path>]\n' +
                'If no template is provided, the default is to generate a B-Machine (.mch)')
        cli.with {
            t(longOpt: 'template', "External template to be used for rendering", args: 1)
            o(longOpt: 'output', 'Target file', args: 1, required: true)
            d(longOpt: 'database', 'SQLite Database', args: 1, required: true)
        }

        def options = cli.parse(args)

        if (!options) {
            System.exit(0)
        }


        def store = new SQLiteStore(options.database)
        def renderer = new Renderer(store)
        def output = options.output.replaceFirst("^~", System.getProperty("user.home"));
        def result

        FileType ft;

        /* If no template is provided we default to generating a B (.mch) file */
        if(options.template) {
            ft = FileType.Unknown;
            // try to detect if we are requesting a builtin template by name
            FileType.each { i ->
                if(i.name == options.template) {
                    ft = i
                }
            }
        } else {
            ft = FileType.BMachine
        }

        if (ft == FileType.Unknown) {
            def template = options.template.replaceFirst("^~", System.getProperty("user.home"));
            result = renderer.renderWith(template)
        } else {
            result = renderer.renderFor(ft)
        }
        def fw = new FileWriter(new File(output))
        result.writeTo(fw)
        fw.flush()

        println("Wrote to " + options.output.toString())
        store.close()
    }

    static def printVersion() {
        println("model-generator: Version ${VERSION}")
    }
}
