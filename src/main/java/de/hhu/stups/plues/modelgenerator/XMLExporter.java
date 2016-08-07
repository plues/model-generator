package de.hhu.stups.plues.modelgenerator;

import de.hhu.stups.plues.data.Store;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@SuppressWarnings("unused")
public class XMLExporter extends Renderer {

    private static final String DATA_TEMPLATE = "/xml/data.xml.twig";
    private static final String DATA_FILE = "Moduldaten.xml";
    private static final String TREE_TEMPLATE = "/xml/tree.xml.twig";
    private static final String TREE_FILE = "Modulbaum.xml";

    public XMLExporter(final Store db) {
        super(db);
    }

    public ByteArrayOutputStream export() throws IOException {
        final ByteArrayOutputStream renderedData = exportFile(DATA_TEMPLATE);
        final ByteArrayOutputStream renderedTree = exportFile(TREE_TEMPLATE);
        //
        final ByteArrayOutputStream zipFile = buildZIPFile(renderedTree, renderedData);
        return zipFile;
    }

    private ByteArrayOutputStream buildZIPFile(final ByteArrayOutputStream tree,
                                               final ByteArrayOutputStream data)
            throws IOException {

        final byte[] treeBytes = getBytesForRenderer(tree);
        final byte[] dataBytes = getBytesForRenderer(data);
        //
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try (ZipOutputStream zos = new ZipOutputStream(baos)) {
            ZipEntry entry = new ZipEntry(TREE_FILE);
            zos.putNextEntry(entry);
            zos.write(treeBytes);
            zos.closeEntry();

            entry = new ZipEntry(DATA_FILE);
            zos.putNextEntry(entry);
            zos.write(dataBytes);
            zos.closeEntry();

        } catch (final IOException ioe) {
            ioe.printStackTrace();
            throw ioe;
        }

        return baos;
    }

    private byte[] getBytesForRenderer(final ByteArrayOutputStream stream) {
        return stream.toByteArray();
    }

    private ByteArrayOutputStream exportFile(String path) {
        return this.render(this.loadTemplateFromResource(path));
    }

}
