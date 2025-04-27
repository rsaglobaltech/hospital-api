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
public class CqrsArchitectureTest {

    private JavaClasses importedClasses;

    @BeforeAll
    public void setup() {
        importedClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("com.example.hospitalapi");
    }

    @Test
    public void commandsShouldImplementCommandInterface() {
        ArchRule rule = classes().that().haveSimpleNameEndingWith("Command")
                .and().doNotHaveSimpleName("Command")
                .should().implement("com.example.hospitalapi.shared.domain.bus.Command");
        
        rule.check(importedClasses);
    }

    @Test
    public void queriesShouldImplementQueryInterface() {
        ArchRule rule = classes().that().haveSimpleNameEndingWith("Query")
                .and().doNotHaveSimpleName("Query")
                .should().implement("com.example.hospitalapi.shared.domain.bus.Query");
        
        rule.check(importedClasses);
    }

    @Test
    public void commandHandlersShouldOnlyAccessCommandsAndDomain() {
        ArchRule rule = classes().that().haveSimpleNameEndingWith("CommandHandler")
                .should().onlyAccessClassesThat().resideInAnyPackage(
                        "..domain..",
                        "..application.command..",
                        "java..",
                        "org.springframework..",
                        "lombok.."
                );
        
        rule.check(importedClasses);
    }

    @Test
    public void queryHandlersShouldOnlyAccessQueriesAndDomain() {
        ArchRule rule = classes().that().haveSimpleNameEndingWith("QueryHandler")
                .should().onlyAccessClassesThat().resideInAnyPackage(
                        "..domain..",
                        "..application.query..",
                        "java..",
                        "org.springframework..",
                        "lombok.."
                );
        
        rule.check(importedClasses);
    }

    @Test
    public void commandHandlersShouldNotDependOnQueries() {
        ArchRule rule = noClasses().that().haveSimpleNameEndingWith("CommandHandler")
                .should().dependOnClassesThat().resideInAPackage("..application.query..");
        
        rule.check(importedClasses);
    }

    @Test
    public void queryHandlersShouldNotDependOnCommands() {
        ArchRule rule = noClasses().that().haveSimpleNameEndingWith("QueryHandler")
                .should().dependOnClassesThat().resideInAPackage("..application.command..");
        
        rule.check(importedClasses);
    }

    @Test
    public void commandBusShouldBeImplementedInInfrastructure() {
        ArchRule rule = classes().that().implement("com.example.hospitalapi.shared.domain.bus.CommandBus")
                .should().resideInAPackage("..infrastructure.bus");
        
        rule.check(importedClasses);
    }

    @Test
    public void queryBusShouldBeImplementedInInfrastructure() {
        ArchRule rule = classes().that().implement("com.example.hospitalapi.shared.domain.bus.QueryBus")
                .should().resideInAPackage("..infrastructure.bus");
        
        rule.check(importedClasses);
    }
}