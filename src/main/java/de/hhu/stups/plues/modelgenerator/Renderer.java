package de.hhu.stups.plues.modelgenerator;

import de.hhu.stups.plues.data.Store;
import de.hhu.stups.plues.modelgenerator.twig.HelperExtension;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.environment.EnvironmentConfiguration;
import org.jtwig.environment.EnvironmentConfigurationBuilder;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Renderer {

  private final Logger logger = LoggerFactory.logger(getClass());


  private static final EnvironmentConfiguration config = EnvironmentConfigurationBuilder
      .configuration().extensions().add(new HelperExtension()).and()
      .render().withOutputCharset(Charset.forName("utf8"))
      .and().build();
  private final Store store;

  public Renderer(final Store db) {
    this.store = db;
  }

  @SuppressWarnings("WeakerAccess")
  protected JtwigTemplate loadTemplateFromResource(final String path) {
    return JtwigTemplate.classpathTemplate(path, config);
  }

  @SuppressWarnings("WeakerAccess")
  protected JtwigTemplate loadTemplate(final String path) {
    final String tmpl = new File(path).getAbsolutePath();
    return JtwigTemplate.fileTemplate(tmpl, config);
  }

  @SuppressWarnings("WeakerAccess")
  protected JtwigTemplate loadTemplate(final FileType tp) {
    final String template = tp.template;
    logger.info("Using template: " + template);
    return this.loadTemplateFromResource(template);
  }

  @SuppressWarnings("WeakerAccess")
  public ByteArrayOutputStream renderWith(final String path) {
    final JtwigTemplate template = loadTemplate(path);
    return this.render(template);
  }

  @SuppressWarnings("WeakerAccess")
  public void renderWith(final String path, final File out) {
    final JtwigTemplate template = loadTemplate(path);
    this.render(template, out);
  }

  @SuppressWarnings("WeakerAccess")
  public ByteArrayOutputStream renderFor(final FileType tp) {
    final JtwigTemplate template = loadTemplate(tp);
    return this.render(template);
  }

  public void renderFor(final FileType tp, final File out) {
    final JtwigTemplate template = loadTemplate(tp);
    this.render(template, out);
  }

  @SuppressWarnings("WeakerAccess")
  protected ByteArrayOutputStream render(final JtwigTemplate template) {
    final JtwigModel model = geModel();

    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    template.render(model, out);
    return out;
  }

  @SuppressWarnings("WeakerAccess")
  protected void render(final JtwigTemplate template, final File out) {
    final JtwigModel model = geModel();

    try (FileOutputStream fos = new FileOutputStream(out)) {
      try (BufferedOutputStream bos = new BufferedOutputStream(fos)) {
        template.render(model, bos);
      }
    } catch (final IOException exception) {
      logger.error("Exception writing template", exception);
    }
  }

  private JtwigModel geModel() {
    return JtwigModel.newModel()
      .with("date", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        .format(new Date()))
      .with("info", store.getInfo())
      .with("short_name", store.getInfoByKey("short-name"))
      .with("abstract_units", store.getAbstractUnits())
      .with("courses", store.getCourses())
      .with("groups", store.getGroups())
      .with("levels", store.getLevels())
      .with("modules", store.getModules())
      .with("module_abstract_unit_semester",
        store.getModuleAbstractUnitSemester())
      .with("module_abstract_unit_type",
        store.getModuleAbstractUnitType())
      .with("sessions", store.getSessions())
      .with("units", store.getUnits());
  }
}
