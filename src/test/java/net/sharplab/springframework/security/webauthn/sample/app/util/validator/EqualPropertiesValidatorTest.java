package net.sharplab.springframework.security.webauthn.sample.app.util.validator;

import net.sharplab.springframework.security.webauthn.sample.app.config.AppConfig;
import net.sharplab.springframework.security.webauthn.sample.app.config.WebSecurityConfig;
import net.sharplab.springframework.security.webauthn.sample.domain.config.DomainConfig;
import net.sharplab.springframework.security.webauthn.sample.infrastructure.config.InfrastructureMockConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for EqualPropertiesValidator
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Import(value = {WebSecurityConfig.class, AppConfig.class, DomainConfig.class, InfrastructureMockConfig.class})
public class EqualPropertiesValidatorTest {

    @Autowired
    Validator validator;

    @Test
    public void test1(){
        TestForm testForm = new TestForm();
        testForm.setRawPassword("rawPassword");
        testForm.setRawPasswordRetyped("rawPassword");

        Set<ConstraintViolation<TestForm>> violations = validator.validate(testForm);
        assertThat(violations.size()).isZero();

    }

    @Test
    public void test2(){
        TestForm testForm = new TestForm();
        testForm.setRawPassword("rawPassword");
        testForm.setRawPasswordRetyped("mismatch");

        Set<ConstraintViolation<TestForm>> violations = validator.validate(testForm);
        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("property rawPassword and rawPasswordRetyped mismatch");

    }

    @EqualProperties(property = "rawPassword", comparingProperty = "rawPasswordRetyped")
    class TestForm{
        private String rawPassword;
        private String rawPasswordRetyped;

        public String getRawPassword() {
            return rawPassword;
        }

        public void setRawPassword(String rawPassword) {
            this.rawPassword = rawPassword;
        }

        public String getRawPasswordRetyped() {
            return rawPasswordRetyped;
        }

        public void setRawPasswordRetyped(String rawPasswordRetyped) {
            this.rawPasswordRetyped = rawPasswordRetyped;
        }
    }

}
