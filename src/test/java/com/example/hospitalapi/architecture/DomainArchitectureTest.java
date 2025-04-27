package com.example.hospitalapi.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DomainArchitectureTest {

    private JavaClasses importedClasses;

    @BeforeAll
    public void setup() {
        importedClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("com.example.hospitalapi");
    }

    @Test
    public void repositoriesShouldBeInterfaces() {
        ArchRule rule = classes().that().haveSimpleNameEndingWith("Repository")
                .and().resideInAPackage("..domain.repository")
                .should().beInterfaces();

        rule.check(importedClasses);
    }

    @Test
    public void domainClassesShouldNotHaveSpringAnnotations() {
        ArchRule rule = noClasses().that().resideInAPackage("..domain..")
                .should().beAnnotatedWith("org.springframework.stereotype.Service");

        rule.check(importedClasses);

        rule = noClasses().that().resideInAPackage("..domain..")
                .should().beAnnotatedWith("org.springframework.stereotype.Component");

        rule.check(importedClasses);

        rule = noClasses().that().resideInAPackage("..domain..")
                .should().beAnnotatedWith("org.springframework.stereotype.Repository");

        rule.check(importedClasses);

        rule = noClasses().that().resideInAPackage("..domain..")
                .should().beAnnotatedWith("org.springframework.stereotype.Controller");

        rule.check(importedClasses);

        rule = noClasses().that().resideInAPackage("..domain..")
                .should().beAnnotatedWith("org.springframework.web.bind.annotation.RestController");

        rule.check(importedClasses);

        rule = noClasses().that().resideInAPackage("..domain..")
                .should().beAnnotatedWith("org.springframework.context.annotation.Configuration");

        rule.check(importedClasses);
    }

    @Test
    public void domainClassesShouldNotHavePersistenceAnnotations() {
        ArchRule rule = noClasses().that().resideInAPackage("..domain..")
                .should().beAnnotatedWith("jakarta.persistence.Entity");

        rule.check(importedClasses);

        rule = noClasses().that().resideInAPackage("..domain..")
                .should().beAnnotatedWith("jakarta.persistence.Table");

        rule.check(importedClasses);

        rule = noClasses().that().resideInAPackage("..domain..")
                .should().beAnnotatedWith("jakarta.persistence.Id");

        rule.check(importedClasses);

        rule = noClasses().that().resideInAPackage("..domain..")
                .should().beAnnotatedWith("jakarta.persistence.Column");

        rule.check(importedClasses);
    }

    @Test
    public void idClassesShouldExtendBaseIdClass() {
        ArchRule rule = classes().that().haveSimpleNameEndingWith("Id")
                .and().resideInAPackage("..domain.valueobject")
                .and().doNotHaveSimpleName("Id")
                .should().beAssignableTo("com.example.hospitalapi.shared.domain.valueobject.Id");

        rule.check(importedClasses);
    }
}
