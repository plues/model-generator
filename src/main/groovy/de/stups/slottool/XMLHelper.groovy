package de.stups.slottool

import de.stups.slottool.data.entities.Course
import de.stups.slottool.data.entities.Level
import de.stups.slottool.data.entities.Module

class XMLHelper {

    static String INDENTATION = "    "

    @SuppressWarnings("GroovyUnusedDeclaration")
    static def format_credit_points(Module module) {
        if (module.credit_points > -1) {
            return "cp=\"${module.credit_points}\""
        }
        ""
    }

    @SuppressWarnings("GroovyUnusedDeclaration")
    static def format_credit_points(Course course) {
        if (course.credit_points > -1) {
            return "cp=\"${course.credit_points}\""
        }
        ""
    }

    @SuppressWarnings("GroovyUnusedDeclaration")
    static def format_credit_points(Level level) {
        if(level.min_credit_points > -1 && level.max_credit_points > -1) {
            return "min-cp=\"${level.min_credit_points}\" max-cp=\"${level.max_credit_points}\""
        }
        ""
    }

    @SuppressWarnings("GroovyUnusedDeclaration")
    static def format_mandatory(Module mod) {
        if (mod.mandatory) {
            "pflicht=\"j\""
        }
        ""
    }

    @SuppressWarnings("GroovyUnusedDeclaration")
    static def format_requirements(Level level) {
        if (level.min > -1 && level.max > -1) {
            return "min=\"${level.min}\" max=\"${level.max}\""
        }
        ""
    }

    @SuppressWarnings("GroovyUnusedDeclaration")
    static def traverse(Level level, Integer depth) {
        def children
        if(level.children && level.children.size() > 0) {
           children = level.children
        } else {
            children = level.modules
        }
        children = children.collect{ l -> traverse(l, depth + 1) }.join("\n")
        def indent = INDENTATION * depth
        indent + "<l name=\"${level.name}\" ${format_credit_points(level)} ${format_requirements(level)}>\n" + children + "\n" + indent + "</l>"
    }

    @SuppressWarnings("GroovyUnusedDeclaration")
    static def traverse(Module module, Integer depth) {
        (INDENTATION * depth) + "<m name=\"${module.title}\" ${format_mandatory(module)} pordnr=\"${module.pordnr}\" ${format_credit_points(module)} />"
    }
}
