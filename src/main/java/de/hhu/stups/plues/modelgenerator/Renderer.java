package de.hhu.stups.plues.modelgenerator;

import de.hhu.stups.plues.data.Store;
import de.hhu.stups.plues.modelgenerator.twig.HelperExtension;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.environment.EnvironmentConfiguration;
import org.jtwig.environment.EnvironmentConfigurationBuilder;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Renderer {


    private final Store store;

    private final static EnvironmentConfiguration config
            = EnvironmentConfigurationBuilder.configuration().extensions()
            .add(new HelperExtension()).and().build();

    public Renderer(final Store db) {
        this.store = db;
    }

    @SuppressWarnings("WeakerAccess")
    protected JtwigTemplate loadTemplate(final FileType tp) {
        final String template = "data." + tp.extension + ".twig";
        System.out.println("Using template: " + template);
        return this.loadTemplateFromResource("/" + template);
    }

    @SuppressWarnings("WeakerAccess")
    protected JtwigTemplate loadTemplateFromResource(final String path) {
        return JtwigTemplate.classpathTemplate(path, config);
    }

    @SuppressWarnings("WeakerAccess")
    protected JtwigTemplate loadTemplate(final String path) {
        return JtwigTemplate.fileTemplate(path, config);
    }

    @SuppressWarnings("WeakerAccess")
    public ByteArrayOutputStream renderWith(final String path) {
        final JtwigTemplate template = loadTemplate(path);
        return this.render(template);
    }

    public void renderWith(final String path, File out) {
        final JtwigTemplate template = loadTemplate(path);
        this.render(template, out);
    }

    @SuppressWarnings("WeakerAccess")
    public ByteArrayOutputStream renderFor(final FileType tp) {
        final JtwigTemplate template = loadTemplate(tp);
        return this.render(template);
    }

    public void renderFor(final FileType tp, File out) {
        final JtwigTemplate template = loadTemplate(tp);
        this.render(template, out);
    }

    protected ByteArrayOutputStream render(final JtwigTemplate template) {
        final JtwigModel model = geModel();

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        template.render(model, out);
        return out;
    }

    protected void render(final JtwigTemplate template, File out) {
        final JtwigModel model = geModel();

        try (FileOutputStream fos = new FileOutputStream(out)) {
            try (BufferedOutputStream bos = new BufferedOutputStream(fos)) {
                template.render(model, bos);
            }
        } catch (IOException e) {
            e.printStackTrace();
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
