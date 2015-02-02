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
    protected loadTemplate(Faculty faculty, FileType tp) {
        def template = faculty.templates[tp.name]
        def resource = this.class.getResourceAsStream("/" + template)
        new SimpleTemplateEngine().createTemplate(resource.text)
    }

    public Writable renderFor(Faculty faculty, FileType tp) {
        def template = loadTemplate(faculty, tp)
        def generated = store.getModelInfo('generated')
        def seed = store.getModelInfo('hashseed')
        def modules = store.getModules()
        def courses = store.getCourses()
        def departments = store.getDepartments()
        def sessions = store.getSessions()
        def mapping = store.getMapping()
        def units = store.getUnits()
        def focus_areas = store.getFocusAreas()
        def major_module_requirements = store.getMajorModuleRequirement()

        def binding = [
                generated: generated,
                seed: seed,
                modules: modules,
                courses: courses,
                departments: departments,
                sessions: sessions,
                mapping: mapping,
                units: units,
                focus_areas: focus_areas,
                major_module_requirements: major_module_requirements,
        ]
        template.make(binding)
    }
}
