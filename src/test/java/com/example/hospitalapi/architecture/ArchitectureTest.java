package com.example.hospitalapi.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ArchitectureTest {

    private JavaClasses importedClasses;

    @BeforeAll
    public void setup() {
        importedClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("com.example.hospitalapi");
    }

    @Test
    public void domainShouldNotDependOnApplicationOrInfrastructure() {
        // This test is more focused and replaces the layeredArchitecture test
        // It ensures that domain classes don't depend on application or infrastructure
        ArchRule rule = noClasses().that().resideInAPackage("..domain..")
                .should().dependOnClassesThat().resideInAnyPackage("..application..", "..infrastructure..");

        rule.check(importedClasses);
    }

    @Test
    public void applicationShouldNotDependOnInfrastructure() {
        // This test ensures that application classes don't depend on infrastructure
        ArchRule rule = noClasses().that().resideInAPackage("..application..")
                .should().dependOnClassesThat().resideInAPackage("..infrastructure..");

        rule.check(importedClasses);
    }

    @Test
    public void boundedContextsShouldBeRespected() {
        ArchRule rule = classes().that().resideInAPackage("..patient..")
                .should().onlyDependOnClassesThat()
                .resideInAnyPackage(
                        "..patient..", 
                        "..shared..", 
                        "java..", 
                        "org..", 
                        "com.fasterxml..", 
                        "lombok..",
                        "jakarta..",
                        "io.swagger.."
                );

        rule.check(importedClasses);

        rule = classes().that().resideInAPackage("..appointment..")
                .should().onlyDependOnClassesThat()
                .resideInAnyPackage(
                        "..appointment..", 
                        "..patient.domain.valueobject..",
                        "..patient.domain.repository..",
                        "..shared..", 
                        "java..", 
                        "org..", 
                        "com.fasterxml..", 
                        "lombok..",
                        "jakarta..",
                        "io.swagger.."
                );

        rule.check(importedClasses);

        rule = classes().that().resideInAPackage("..medicalstaff..")
                .should().onlyDependOnClassesThat()
                .resideInAnyPackage(
                        "..medicalstaff..", 
                        "..patient.domain.valueobject..", 
                        "..shared..", 
                        "java..", 
                        "org..", 
                        "com.fasterxml..", 
                        "lombok..",
                        "jakarta..",
                        "io.swagger.."
                );

        rule.check(importedClasses);
    }

    @Test
    public void commandsShouldBeNamedCorrectly() {
        ArchRule rule = classes().that().resideInAPackage("..application.command")
                .and().doNotHaveSimpleName("CommandBus")
                .and().doNotHaveSimpleName("CommandHandler")
                .and().haveNameNotMatching(".*\\$.*") // Exclude Lombok-generated builder classes
                .should().haveSimpleNameEndingWith("Command")
                .orShould().haveSimpleNameEndingWith("CommandHandler");

        rule.check(importedClasses);
    }

    @Test
    public void queriesShouldBeNamedCorrectly() {
        ArchRule rule = classes().that().resideInAPackage("..application.query")
                .and().doNotHaveSimpleName("QueryBus")
                .and().doNotHaveSimpleName("QueryHandler")
                .and().haveNameNotMatching(".*\\$.*") // Exclude Lombok-generated builder classes
                .should().haveSimpleNameEndingWith("Query")
                .orShould().haveSimpleNameEndingWith("QueryHandler")
                .orShould().haveSimpleNameEndingWith("Response");

        rule.check(importedClasses);
    }

    @Test
    public void domainEntitiesShouldBeInDomainEntityPackage() {
        // Only check classes that are explicitly named as entities
        ArchRule rule = classes().that().haveSimpleName("Patient")
                .or().haveSimpleName("MedicalStaff")
                .or().haveSimpleName("Appointment")
                .should().resideInAPackage("..domain.entity");

        rule.check(importedClasses);
    }

    @Test
    public void valueObjectsShouldBeInDomainValueObjectPackage() {
        ArchRule rule = classes().that().haveSimpleNameNotEndingWith("Test")
                .and().haveSimpleNameNotEndingWith("JpaEntity")
                .and().haveSimpleNameNotEndingWith("Repository")
                .and().haveSimpleNameNotEndingWith("Controller")
                .and().haveSimpleNameNotEndingWith("Request")
                .and().haveSimpleNameNotEndingWith("Response")
                .and().haveSimpleNameNotEndingWith("Command")
                .and().haveSimpleNameNotEndingWith("Query")
                .and().haveSimpleNameNotEndingWith("Handler")
                .and().haveSimpleNameNotEndingWith("Adapter")
                .and().haveSimpleNameNotEndingWith("Config")
                .and().haveSimpleNameNotEndingWith("Application")
                .and().areNotAnnotatedWith("org.springframework.stereotype.Service")
                .and().areNotAnnotatedWith("org.springframework.stereotype.Component")
                .and().areNotAnnotatedWith("org.springframework.stereotype.Repository")
                .and().areNotAnnotatedWith("org.springframework.stereotype.Controller")
                .and().areNotAnnotatedWith("org.springframework.web.bind.annotation.RestController")
                .and().areNotAnnotatedWith("org.springframework.context.annotation.Configuration")
                .and().areNotAnnotatedWith("org.springframework.boot.autoconfigure.SpringBootApplication")
                .and().areNotInterfaces()
                .and().areNotEnums()
                .and().resideOutsideOfPackage("..domain.entity")
                .and().resideInAPackage("..domain..")
                .should().resideInAPackage("..domain.valueobject");

        rule.check(importedClasses);
    }
}
