package de.stups.slottool

import de.stups.slottool.data.Store
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
        println(template)
        def resource = this.class.getResourceAsStream("/" + template)
        new SimpleTemplateEngine().createTemplate(resource.text)
    }

    protected loadTemplate(String path) {
        new SimpleTemplateEngine().createTemplate(new FileReader(path))
    }

    def renderWith(String path) {
        def template = loadTemplate(path)
        this.render(template)
    }

    public Writable renderFor(FileType tp) {
        def template = loadTemplate(tp)
        this.render(template)
    }

    private Writable render(def template) {
        def binding = [
                info: store.infoDAO,

                abstract_units: store.abstractUnitDAO,
                abstract_unit_unit_semester: store.abstractUnitUnitSemesterDAO,
                courses: store.courseDAO,
                groups: store.groupDAO,
                levels: store.levelDAO,
                modules: store.moduleDAO,
                module_abstract_unit_semester: store.moduleAbstractUnitSemesterDAO,
                sessions: store.sessionDAO,
                units: store.unitDAO,
        ]
        template.make(binding)
    }
}
