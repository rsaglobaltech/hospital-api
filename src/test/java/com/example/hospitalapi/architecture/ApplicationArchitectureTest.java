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
public class ApplicationArchitectureTest {

    private JavaClasses importedClasses;

    @BeforeAll
    public void setup() {
        importedClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("com.example.hospitalapi");
    }

    @Test
    public void commandHandlersShouldHaveCorrectName() {
        ArchRule rule = classes().that().resideInAPackage("..application.command")
                .and().haveSimpleNameEndingWith("Handler")
                .should().haveSimpleNameEndingWith("CommandHandler");

        rule.check(importedClasses);
    }

    @Test
    public void queryHandlersShouldHaveCorrectName() {
        ArchRule rule = classes().that().resideInAPackage("..application.query")
                .and().haveSimpleNameEndingWith("Handler")
                .should().haveSimpleNameEndingWith("QueryHandler");

        rule.check(importedClasses);
    }

    @Test
    public void commandHandlersShouldBeInCorrectPackage() {
        ArchRule rule = classes().that().haveSimpleNameEndingWith("CommandHandler")
                .and().doNotBelongToAnyOf(com.example.hospitalapi.shared.domain.bus.CommandHandler.class)
                .should().resideInAPackage("..application.command");

        rule.check(importedClasses);
    }

    @Test
    public void queryHandlersShouldBeInCorrectPackage() {
        ArchRule rule = classes().that().haveSimpleNameEndingWith("QueryHandler")
                .and().doNotBelongToAnyOf(com.example.hospitalapi.shared.domain.bus.QueryHandler.class)
                .should().resideInAPackage("..application.query");

        rule.check(importedClasses);
    }

    @Test
    public void commandsShouldBeInCorrectPackage() {
        ArchRule rule = classes().that().haveSimpleNameEndingWith("Command")
                .and().doNotHaveSimpleName("Command")
                .should().resideInAPackage("..application.command");

        rule.check(importedClasses);
    }

    @Test
    public void queriesShouldBeInCorrectPackage() {
        ArchRule rule = classes().that().haveSimpleNameEndingWith("Query")
                .and().doNotHaveSimpleName("Query")
                .should().resideInAPackage("..application.query");

        rule.check(importedClasses);
    }

    @Test
    public void applicationClassesShouldNotDependOnInfrastructure() {
        ArchRule rule = noClasses().that().resideInAPackage("..application..")
                .should().dependOnClassesThat().resideInAPackage("..infrastructure..");

        rule.check(importedClasses);
    }

    @Test
    public void commandHandlersShouldImplementCommandHandlerInterface() {
        ArchRule rule = classes().that().haveSimpleNameEndingWith("CommandHandler")
                .and().doNotBelongToAnyOf(com.example.hospitalapi.shared.domain.bus.CommandHandler.class)
                .should().implement("com.example.hospitalapi.shared.domain.bus.CommandHandler");

        rule.check(importedClasses);
    }

    @Test
    public void queryHandlersShouldImplementQueryHandlerInterface() {
        ArchRule rule = classes().that().haveSimpleNameEndingWith("QueryHandler")
                .and().doNotBelongToAnyOf(com.example.hospitalapi.shared.domain.bus.QueryHandler.class)
                .should().implement("com.example.hospitalapi.shared.domain.bus.QueryHandler");

        rule.check(importedClasses);
    }
}
