package de.hhu.stups.plues.modelgenerator

import de.hhu.stups.plues.data.Store

import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

class XMLExporter extends Renderer {
    final String DATA_TEMPLATE = "/xml/data.xml.template"
    final String DATA_FILE = "Moduldaten.xml"

    final String TREE_TEMPLATE = "/xml/tree.xml.template"
    final String TREE_FILE = "Modulbaum.xml"


    @SuppressWarnings("GroovyUnusedDeclaration")
    XMLExporter(Store db) {
        super(db)
    }

    public ByteArrayOutputStream export(File file) {
        final def renderedData = exportFile(DATA_TEMPLATE)
        final def renderedTree = exportFile(TREE_TEMPLATE)
        //
        ByteArrayOutputStream zipFile = buildZIPFile(renderedTree, renderedData)
    }

    private ByteArrayOutputStream buildZIPFile(def tree, def data) {
        def treeBytes = getBytesForRenderer(tree)
        def dataBytes = getBytesForRenderer(data)
        //
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        ZipOutputStream zos = new ZipOutputStream(baos)
        try {
            ZipEntry entry = new ZipEntry(TREE_FILE)
            zos.putNextEntry(entry)
            zos.write(treeBytes)
            zos.closeEntry()

            entry = new ZipEntry(DATA_FILE)
            zos.putNextEntry(entry)
            zos.write(dataBytes)
            zos.closeEntry()

        } catch (IOException ioe) {
            ioe.printStackTrace()
            throw ioe
        } finally {
            zos.close()
        }
        baos

    }

    private def getBytesForRenderer(def renderer) {
        StringWriter sw = new StringWriter()
        renderer.writeTo(sw)
        sw.toString().bytes
    }

    private def exportFile(String path) {
        this.render(this.loadTemplateFromResource(path), XMLHelper)
    }
}
