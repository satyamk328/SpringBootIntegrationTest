package com.test.achitecture;



import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import org.junit.BeforeClass;
import org.junit.Test;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;

public class ArchitectureTest {
	
    static final String DOMAIN_LAYER_PACKAGES = "com.test.model..";
    static final String SERVICE_LAYER_PACKAGES = "com.test.service..";
    static final String DAO_LAYER_CLASSES = "com.test.repository..";
    static final String WEB_LAYER_CLASSES = "com.test.controller..";

    static JavaClasses classes;

    @BeforeClass
    public static void setUp() {
        classes = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_ARCHIVES)
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_JARS)
            .importPackages("com.test");
    }
    
    @Test
    public void archUnitTest() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.test.achitecture");
        ArchRule rule = classes().should().bePublic();
        rule.check(importedClasses);
    }
    
}