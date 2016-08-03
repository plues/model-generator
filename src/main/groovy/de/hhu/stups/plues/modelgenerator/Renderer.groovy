package de.hhu.stups.plues.modelgenerator

import de.hhu.stups.plues.data.Store
import groovy.text.SimpleTemplateEngine

/**
 * Created by David Schneider on 02.02.15.
 */
class Renderer {

    Store store

    def Renderer(Store db) {
        this.store = db
    }

    protected loadTemplate(FileType tp) {
        def template = "data.${tp.extension}.template"
        println("Using template: ${template}")
        this.loadTemplateFromResource("/" + template)
    }

    protected loadTemplateFromResource(String path) {
        def resource = this.class.getResourceAsStream(path)
        new SimpleTemplateEngine().createTemplate(resource.text)

    }

    protected loadTemplate(String path) {
        new SimpleTemplateEngine().createTemplate(new FileReader(path))
    }

    def renderWith(String path) {
        def template = loadTemplate(path)
        this.render(template, output)
    }

    public Writable renderFor(FileType tp) {
        def template = loadTemplate(tp)
        this.render(template)
    }

    protected Writable render(def template, def helper=null) {
        def binding = [
                info: store.info,
                short_name: store.getInfoByKey("short-name"),
                abstract_units: store.abstractUnits,
                courses: store.courses,
                groups: store.groups,
                levels: store.levels,
                modules: store.modules,
                module_abstract_unit_semester: store.moduleAbstractUnitSemester,
                module_abstract_unit_type: store.moduleAbstractUnitType,
                sessions: store.sessions,
                units: store.units,
                helper: helper,
        ]
        template.make(binding)
    }
}
