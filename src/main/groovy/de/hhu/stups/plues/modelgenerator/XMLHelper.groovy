package de.hhu.stups.plues.modelgenerator

import de.hhu.stups.plues.data.entities.Course
import de.hhu.stups.plues.data.entities.Level
import de.hhu.stups.plues.data.entities.Module

class XMLHelper {

    static String INDENTATION = "    "

    @SuppressWarnings("GroovyUnusedDeclaration")
    static def formatCreditPoints(Module module) {
        if (module.creditPoints > -1) {
            return "cp=\"${module.creditPoints}\""
        }
        ""
    }

    @SuppressWarnings("GroovyUnusedDeclaration")
    static def formatCreditPoints(Course course) {
        if (course.creditPoints > -1) {
            return "cp=\"${course.creditPoints}\""
        }
        ""
    }

    @SuppressWarnings("GroovyUnusedDeclaration")
    static def formatCreditPoints(Level level) {
        if(level.minCreditPoints > -1 && level.maxCreditPoints > -1) {
            return "min-cp=\"${level.minCreditPoints}\" max-cp=\"${level.maxCreditPoints}\""
        }
        ""
    }

    @SuppressWarnings("GroovyUnusedDeclaration")
    static def formatMandatory(Module mod) {
        if (mod.mandatory) {
            "pflicht=\"j\""
        }
        ""
    }

    @SuppressWarnings("GroovyUnusedDeclaration")
    static def formatRequirements(Level level) {
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
        indent + "<l name=\"${level.name}\" ${formatCreditPoints(level)} ${formatRequirements(level)}>\n" + children + "\n" + indent + "</l>"
    }

    @SuppressWarnings("GroovyUnusedDeclaration")
    static def traverse(Module module, Integer depth) {
        (INDENTATION * depth) + "<m name=\"${module.title}\" ${formatMandatory(module)} pordnr=\"${module.pordnr}\" ${formatCreditPoints(module)} />"
    }
}
