module de.hhu.stups.plues.modelgenerator {
    exports de.hhu.stups.plues.data;
    exports de.hhu.stups.plues.data.entities;
    exports de.hhu.stups.plues.modelgenerator;

    requires commons.cli;
    requires guava;
    requires java.naming;
    requires java.persistence;
    requires java.sql;
    requires transitive jtwig.core;
    requires org.hibernate.orm.core;
    requires org.slf4j;
    requires com.github.spotbugs.annotations;
}
