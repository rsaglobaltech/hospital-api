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
public class BoundedContextArchitectureTest {

    private JavaClasses importedClasses;

    @BeforeAll
    public void setup() {
        importedClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("com.example.hospitalapi");
    }

    @Test
    public void patientBoundedContextShouldNotDependOnOtherBoundedContexts() {
        ArchRule rule = classes().that().resideInAPackage("..patient..")
                .should().onlyDependOnClassesThat().resideInAnyPackage(
                        "..patient..",
                        "..shared..",
                        "java..",
                        "org.springframework..",
                        "jakarta..",
                        "lombok..",
                        "com.fasterxml.."
                );

        rule.check(importedClasses);
    }

    @Test
    public void appointmentBoundedContextShouldOnlyDependOnPatientValueObjectsAndShared() {
        ArchRule rule = classes().that().resideInAPackage("..appointment..")
                .should().onlyDependOnClassesThat().resideInAnyPackage(
                        "..appointment..",
                        "..patient.domain.valueobject..",
                        "..patient.domain.repository..",
                        "..shared..",
                        "java..",
                        "org.springframework..",
                        "jakarta..",
                        "lombok..",
                        "com.fasterxml..",
                        "io.swagger.."
                );

        rule.check(importedClasses);
    }

    @Test
    public void medicalStaffBoundedContextShouldOnlyDependOnPatientValueObjectsAndShared() {
        ArchRule rule = classes().that().resideInAPackage("..medicalstaff..")
                .should().onlyDependOnClassesThat().resideInAnyPackage(
                        "..medicalstaff..",
                        "..patient.domain.valueobject..",
                        "..shared..",
                        "java..",
                        "org.springframework..",
                        "jakarta..",
                        "lombok..",
                        "com.fasterxml.."
                );

        rule.check(importedClasses);
    }

    @Test
    public void eachBoundedContextShouldHaveItsOwnDomainLayer() {
        ArchRule rule = classes().that().resideInAPackage("..domain.entity")
                .should().resideInAnyPackage(
                        "..patient.domain.entity..",
                        "..appointment.domain.entity..",
                        "..medicalstaff.domain.entity.."
                );

        rule.check(importedClasses);
    }

    @Test
    public void eachBoundedContextShouldHaveItsOwnApplicationLayer() {
        ArchRule rule = classes().that().resideInAPackage("..application..")
                .should().resideInAnyPackage(
                        "..patient.application..",
                        "..appointment.application..",
                        "..medicalstaff.application.."
                );

        rule.check(importedClasses);
    }

    @Test
    public void eachBoundedContextShouldHaveItsOwnInfrastructureLayer() {
        ArchRule rule = classes().that().resideInAPackage("..infrastructure..")
                .and().resideOutsideOfPackage("..shared.infrastructure..")
                .should().resideInAnyPackage(
                        "..patient.infrastructure..",
                        "..appointment.infrastructure..",
                        "..medicalstaff.infrastructure.."
                );

        rule.check(importedClasses);
    }

    @Test
    public void boundedContextsShouldFollowVerticalSlicing() {
        ArchRule rule = classes().that().resideInAPackage("..patient..")
                .should().resideInAnyPackage(
                        "..patient.domain..",
                        "..patient.application..",
                        "..patient.infrastructure.."
                );

        rule.check(importedClasses);

        rule = classes().that().resideInAPackage("..appointment..")
                .should().resideInAnyPackage(
                        "..appointment.domain..",
                        "..appointment.application..",
                        "..appointment.infrastructure.."
                );

        rule.check(importedClasses);

        rule = classes().that().resideInAPackage("..medicalstaff..")
                .should().resideInAnyPackage(
                        "..medicalstaff.domain..",
                        "..medicalstaff.application..",
                        "..medicalstaff.infrastructure.."
                );

        rule.check(importedClasses);
    }
}
