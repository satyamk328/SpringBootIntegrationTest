package com.test.achitecture;

import org.junit.Test;
import org.springframework.stereotype.Service;

import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;

public class ApplicationComponentsTest extends ArchitectureTest {

	@Test
	public void useCaseClassesShouldBeAnnotatedWithServiceAnnotation() {
		ArchRule rule = ArchRuleDefinition.classes().that().haveSimpleNameEndingWith("UseCase").should()
				.beAnnotatedWith(Service.class);
		rule.check(classes);
	}

	@Test
	public void noUseCaseClassesShouldResideOutsideTheApplicationLayer() {
		ArchRule rule = ArchRuleDefinition.noClasses().that().haveSimpleNameEndingWith("Impl").should()
				.resideOutsideOfPackage(SERVICE_LAYER_PACKAGES);
		rule.check(classes);
	}

	@Test
	public void noClassesWithServiceAnnotationShouldResideOutsideTheApplicationLayer() {
		ArchRule rule = ArchRuleDefinition.noClasses().that().areAnnotatedWith(Service.class).should()
				.resideOutsideOfPackage(SERVICE_LAYER_PACKAGES);
		rule.check(classes);
	}

	

}
