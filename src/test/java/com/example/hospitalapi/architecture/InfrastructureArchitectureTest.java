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
public class InfrastructureArchitectureTest {

    private JavaClasses importedClasses;

    @BeforeAll
    public void setup() {
        importedClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("com.example.hospitalapi");
    }

    @Test
    public void controllersShouldBeInInfrastructureRestPackage() {
        ArchRule rule = classes().that().haveSimpleNameEndingWith("Controller")
                .should().resideInAPackage("..infrastructure.rest..*");
        rule.check(importedClasses);
    }

    @Test
    public void controllersShouldHaveCorrectName() {
        ArchRule rule = classes().that().areAnnotatedWith("org.springframework.web.bind.annotation.RestController")
                .should().haveSimpleNameEndingWith("Controller");

        rule.check(importedClasses);
    }

    @Test
    public void jpaEntitiesShouldBeInInfrastructurePersistenceEntityPackage() {
        ArchRule rule = classes().that().haveSimpleNameEndingWith("JpaEntity")
                .should().resideInAPackage("..infrastructure.persistence.entity");

        rule.check(importedClasses);
    }

    @Test
    public void jpaRepositoriesShouldBeInInfrastructurePersistenceRepositoryPackage() {
        ArchRule rule = classes().that().haveSimpleNameEndingWith("JpaRepository")
                .should().resideInAPackage("..infrastructure.persistence.repository");

        rule.check(importedClasses);
    }

    @Test
    public void adaptersShouldBeInInfrastructurePersistenceAdapterPackage() {
        ArchRule rule = classes().that().haveSimpleNameEndingWith("Adapter")
                .should().resideInAPackage("..infrastructure.persistence.adapter");

        rule.check(importedClasses);
    }

    @Test
    public void controllersShouldBeAnnotatedWithRestController() {
        ArchRule rule = classes().that().haveSimpleNameEndingWith("Controller")
                .should().beAnnotatedWith("org.springframework.web.bind.annotation.RestController");

        rule.check(importedClasses);
    }

    @Test
    public void jpaRepositoriesShouldBeAnnotatedWithRepository() {
        ArchRule rule = classes().that().haveSimpleNameEndingWith("JpaRepository")
                .should().beAnnotatedWith("org.springframework.stereotype.Repository");

        rule.check(importedClasses);
    }

    @Test
    public void jpaRepositoriesShouldExtendJpaRepository() {
        ArchRule rule = classes().that().haveSimpleNameEndingWith("JpaRepository")
                .should().beAssignableTo("org.springframework.data.jpa.repository.JpaRepository");

        rule.check(importedClasses);
    }

    @Test
    public void requestDtosShouldBeInInfrastructureRestRequestPackage() {
        ArchRule rule = classes().that().haveSimpleNameEndingWith("Request")
                .should().resideInAPackage("..infrastructure.rest.request");

        rule.check(importedClasses);
    }

    @Test
    public void infrastructureClassesShouldNotBeAccessedByDomain() {
        ArchRule rule = noClasses().that().resideInAPackage("..domain..")
                .should().dependOnClassesThat().resideInAPackage("..infrastructure..");

        rule.check(importedClasses);
    }
}
