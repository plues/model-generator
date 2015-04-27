import de.stups.slottool.Faculty
import de.stups.slottool.FileType
import de.stups.slottool.Renderer
import de.stups.slottool.data.Store

class Main {

    static String VERSION = "0.5.2-SNAPSHOT"
    public static void main(String[] args) {
        printVersion()
        /*
         * http://marxsoftware.blogspot.de/2010/07/explicitly-specifying-args-property.html
         */
        def cli = new CliBuilder(usage:'java -jar JARFILE --output=<path> --database=<path> [--format=<format> --faculty=<faculty>] or [--template=<path>]')
        cli.with {
            _(longOpt:'format', "Output format [${FileType.values().join(", ")}]", args: 1)
            t(longOpt:'template', "External template to be used for rendering", args: 1)
            o(longOpt:'output', 'Target file', args: 1, required: true)
            d(longOpt:'database', 'SQLite Database', args:1, required: true)
        }

        def options = cli.parse(args)

        if (!options) {
            System.exit(0)
        }


        if( !options.template && !options.format) {
            println("Either template or format are required!!")
            cli.usage()
            System.exit(0)
        }

        def store = new Store(options.database)
        def renderer = new Renderer(store)
        def output = options.output.replaceFirst("^~", System.getProperty("user.home"));
        def result

        if (options.template) {
            result = renderer.renderWith(options.template)
        } else {
            def tp = FileType.values().find { it.name == options.format }
            result = renderer.renderFor(tp)
        }
        result.writeTo(new FileWriter(new File(output)))
        println("Wrote to " + options.output.toString())
    }

    static def printVersion() {
        println("model-generator: Version ${VERSION}")
    }
}
