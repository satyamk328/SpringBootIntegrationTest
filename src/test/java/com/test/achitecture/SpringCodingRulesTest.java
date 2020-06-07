package com.test.achitecture;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;

import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;

public class SpringCodingRulesTest extends ArchitectureTest {

	@Test
	public void springComponentInteraction() {
		ArchRule rule = ArchRuleDefinition.classes().that().resideInAPackage("..service..").should().onlyBeAccessed()
				.byAnyPackage("..service..", "..controller..", "..model..", "..config..");
		rule.check(classes);
	}

	@Test
	public void springRepositoryInteraction() {

		ArchRule rule = ArchRuleDefinition.classes().that().resideInAPackage("..repository..").should().onlyBeAccessed()
				.byAnyPackage("..impl..");

		rule.check(classes);
	}

	@Test
	public void domainClassesShouldOnlyDependOnDomainOrStdLibClasses() {
		ArchRule rule = ArchRuleDefinition.classes().that().resideInAPackage(DOMAIN_LAYER_PACKAGES).and()
				.areAnnotatedWith(Entity.class).should().onlyDependOnClassesThat()
				.resideInAnyPackage(DOMAIN_LAYER_PACKAGES, "java..", "lombok..", "javax..", "",
						"com.fasterxml.jackson..", "org.hibernate.annotations", "org.apache.commons.lang3..",
						"org.springframework.security.core..");
		rule.check(classes);
	}

	@Test
	public void controllerClassesShouldBeAnnotatedWithControllerOrRestControllerAnnotation() {
		ArchRule rule = ArchRuleDefinition.classes().that().haveSimpleNameEndingWith("Controller").or()
				.haveSimpleNameEndingWith("Resource").should().beAnnotatedWith(Controller.class).orShould()
				.beAnnotatedWith(RestController.class);

		rule.check(classes);
	}

	@Test
	public void noClassesWithControllerOrRestControllerAnnotationShouldResideOutsideOfPrimaryAdaptersPackages() {
		ArchRule rule = ArchRuleDefinition.noClasses().that().areAnnotatedWith(Controller.class).or()
				.areAnnotatedWith(RestController.class).should().resideOutsideOfPackage(WEB_LAYER_CLASSES);

		rule.check(classes);
	}

	@Test
	public void controllerClassesShouldNotDependOnEachOther() {
		ArchRule rule = ArchRuleDefinition.noClasses().that().areAnnotatedWith(Controller.class).or()
				.areAnnotatedWith(RestController.class).should().dependOnClassesThat()
				.areAnnotatedWith(Controller.class);

		rule.check(classes);
	}

	@Test
	public void controllerClassesShouldNotDependOnEachOther2() {
		ArchRule rule = ArchRuleDefinition.noClasses().that().areAnnotatedWith(Controller.class).or()
				.areAnnotatedWith(RestController.class).should().dependOnClassesThat()
				.areAnnotatedWith(RestController.class);

		rule.check(classes);
	}

	@Test
	public void publicRestControllerMethodsShouldBeAnnotatedWithARequestMapping() {
		ArchRule rule = ArchRuleDefinition.methods().that().arePublic().and().areDeclaredInClassesThat()
				.areAnnotatedWith(RestController.class).should().beAnnotatedWith(RequestMapping.class).orShould()
				.beAnnotatedWith(GetMapping.class).orShould().beAnnotatedWith(PostMapping.class).orShould()
				.beAnnotatedWith(PutMapping.class).orShould().beAnnotatedWith(DeleteMapping.class);

		rule.check(classes);
	}

	@Test
	public void publicControllerMethodsShouldBeAnnotatedWithARequestMapping() {
		ArchRule rule = ArchRuleDefinition.methods().that().arePublic().and().areDeclaredInClassesThat()
				.areAnnotatedWith(Controller.class).should().beAnnotatedWith(RequestMapping.class).orShould()
				.beAnnotatedWith(GetMapping.class).orShould().beAnnotatedWith(PostMapping.class).orShould()
				.beAnnotatedWith(PutMapping.class).orShould().beAnnotatedWith(DeleteMapping.class);

		rule.check(classes);
	}

	@Test
	public void noClassShouldHaveMethodAnnotatedWithPostConstruct() {
		ArchRule rule = ArchRuleDefinition.methods().should().notBeAnnotatedWith(PostConstruct.class).because(
				"You need to implement InitializingBean and move your @PostConstruct logic into afterPropertiesSet() so that Spring handles bean initialization better ");

		rule.check(classes);
	}

	@Test
	public void noClassesWithEntityAnnotationShouldResideOutsideOfDomainPackage() {
		ArchRule rule = ArchRuleDefinition.noClasses().that().areAnnotatedWith(Entity.class).should()
				.resideOutsideOfPackage(DOMAIN_LAYER_PACKAGES);
		rule.check(classes);
	}

	@Test
	public void serviceImplClassesShouldBeAnnotatedWithServiceAnnotation() {
		ArchRule rule = ArchRuleDefinition.classes().that().haveSimpleNameEndingWith("ServiceImpl").should()
				.beAnnotatedWith(Service.class);

		rule.check(classes);
	}

	@Test
	public void serviceClassesShouldBeAnnotatedWithServiceAnnotation() {
		ArchRule rule = ArchRuleDefinition.classes().that().haveSimpleNameEndingWith("Service").and().areNotInterfaces()
				.should().beAnnotatedWith(Service.class);

		rule.check(classes);
	}

	@Test
	public void noServiceClassesShouldResideOutsideDesignatedPackages() {
		ArchRule rule = ArchRuleDefinition.noClasses().that().haveSimpleNameEndingWith("Service").or()
				.areAnnotatedWith(Service.class).should().resideOutsideOfPackages(SERVICE_LAYER_PACKAGES);

		rule.check(classes);

	}

	@Test
	public void noControllerClassesShouldResideOutsideDesignatedPackages() {
		ArchRule rule = ArchRuleDefinition.noClasses().that().haveSimpleNameEndingWith("Controller").or()
				.areAnnotatedWith(Controller.class).or().areAnnotatedWith(RestController.class).should()
				.resideOutsideOfPackages(WEB_LAYER_CLASSES);

		rule.check(classes);

	}
}