package de.hhu.stups.plues.modelgenerator.twig;

import org.jtwig.environment.EnvironmentConfigurationBuilder;
import org.jtwig.extension.Extension;

public class HelperExtension implements Extension {
    @Override
    public void configure(final EnvironmentConfigurationBuilder configurationBuilder) {
        configurationBuilder.functions()
                .add(new TreeMapperFunction())
                .add(new CourseCPFunction());
    }
}
