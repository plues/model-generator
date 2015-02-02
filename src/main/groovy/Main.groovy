import de.stups.slottool.Faculty
import de.stups.slottool.FileType
import de.stups.slottool.Renderer
import de.stups.slottool.data.Store

class Main {
    public static void main(String[] args) {
        // for tests args = '--database=data.sqlite3 --faculty=philfak --format=b --output=data.mch'.split(' ')
        println(args)
        /*
         * http://marxsoftware.blogspot.de/2010/07/explicitly-specifying-args-property.html
         */
        def cli = new CliBuilder(usage:'java -jar JARFILE --format=<format> --faculty=<faculty> --output=<path>')
        cli.with {
            _(longOpt:'format', "Output format [${FileType.values().join(", ")}]", args: 1, required: true)
            _(longOpt:'faculty', "Faculty to generate data for: [${Faculty.values().join(", ")}]", args: 1, required: true)
            o(longOpt:'output', 'Target file', args: 1, required: true)
            d(longOpt:'database', 'SQLite Database', args:1, required: true)
        }

        def options = cli.parse(args)

        if (!options) {
            System.exit(0)
        }

        def fac = Faculty.values().find { it.name == options.faculty }
        def tp = FileType.values().find { it.name == options.format }

        if( !fac || !tp) {
            cli.usage()
            System.exit(0)
        }

        def store = new Store(options.database)
        def renderer = new Renderer(store)

        renderer.renderFor(fac, tp).writeTo(new FileWriter(new File(options.output)))
        println("Wrote to " + options.output.toString())
    }

}
