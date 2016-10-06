package de.hhu.stups.plues.modelgenerator;

import de.hhu.stups.plues.data.Store;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@SuppressWarnings("unused")
public class XmlExporter {

  private static final String DATA_TEMPLATE = "/xml/data.xml.twig";
  private static final String DATA_FILE = "Moduldaten.xml";
  private static final String TREE_TEMPLATE = "/xml/tree.xml.twig";
  private static final String TREE_FILE = "Modulbaum.xml";
  private final Logger logger = Logger.getLogger(getClass().getSimpleName());
  private final Renderer renderer;

  public XmlExporter(final Store db) {
    this.renderer = new Renderer(db);
  }

  /**
   * Export data to a zip file containting an xml file for the module tree and an xml file for
   * the module data.
   *
   * @return ByteArrayOutputStream representing the generated ZIP file
   * @throws IOException in case there is an error creating the ZIP file
   */
  public ByteArrayOutputStream export() throws IOException {
    final ByteArrayOutputStream renderedData = renderer.renderFor(FileType.MODULE_DATA);
    final ByteArrayOutputStream renderedTree = renderer.renderFor(FileType.MODULE_TREE);
    //
    return buildZipFile(renderedTree, renderedData);
  }

  private ByteArrayOutputStream buildZipFile(final ByteArrayOutputStream tree,
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
      logger.log(Level.SEVERE, "Exception creating ZIP file for xml data", ioe);
      throw ioe;
    }

    return baos;
  }

  private byte[] getBytesForRenderer(final ByteArrayOutputStream stream) {
    return stream.toByteArray();
  }
}
