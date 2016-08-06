package de.hhu.stups.plues.modelgenerator

import de.hhu.stups.plues.data.Store
import org.jtwig.JtwigModel
import org.jtwig.JtwigTemplate

import java.text.SimpleDateFormat

/**
 * Created by David Schneider on 02.02.15.
 */
class Renderer {

    Store store

    def Renderer(Store db) {
        this.store = db
    }

    protected loadTemplate(FileType tp) {
        def template = "data.${tp.extension}.twig"
        println("Using template: ${template}")
        this.loadTemplateFromResource("/" + template)
    }

    protected loadTemplateFromResource(String path) {
        return JtwigTemplate.classpathTemplate(path);
    }

    protected loadTemplate(String path) {
//        new SimpleTemplateEngine().createTemplate(new FileReader(path))
    }

    ByteArrayOutputStream renderWith(String path) {
        def template = loadTemplate(path)
        this.render(template, output)
    }

    public ByteArrayOutputStream renderFor(FileType tp) {
        def template = loadTemplate(tp)
        this.render(template)
    }

    protected ByteArrayOutputStream render(JtwigTemplate template) {
        return render(template, null);
    }

    protected ByteArrayOutputStream render(JtwigTemplate template, XMLHelper helper) {
        JtwigModel model = JtwigModel.newModel()
                .with("data", new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
                .with("info", store.info)
                .with("short_name", store.getInfoByKey("short-name"))
                .with("abstract_units", store.abstractUnits)
                .with("courses", store.courses)
                .with("groups", store.groups)
                .with("levels", store.levels)
                .with("modules", store.modules)
                .with("module_abstract_unit_semester", store.moduleAbstractUnitSemester)
                .with("module_abstract_unit_type", store.moduleAbstractUnitType)
                .with("sessions", store.sessions)
                .with("units", store.units)
                .with("helper", helper);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        template.render(model, out);
        return out;
    }
}
