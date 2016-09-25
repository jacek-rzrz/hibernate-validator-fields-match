package pl.rzrz.hibernate.validator;

import lombok.Data;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import javax.validation.*;
import javax.validation.Path.Node;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static pl.rzrz.hibernate.validator.Utils.validate;

@RunWith(Enclosed.class)
public class FieldsMatchTests {

    @Data
    @FieldsMatch(fields = MyBeanFields.class)
    @Ignore
    public static class MyBean {
        private final String field1;
        private final String field2;
    }

    @Ignore
    public static class MyBeanFields implements SelectFields<MyBean> {

        @Override
        public Object field(MyBean obj) {
            return obj.getField1();
        }

        @Override
        public Object confirmedField(MyBean obj) {
            return obj.getField2();
        }

        @Override
        public String getConfirmationFieldName() {
            return "field2";
        }
    }

    @RunWith(Parameterized.class)
    public static class WhenFieldsDontMatch {

        @Parameters(name = "{index}: {0}")
        public static MyBean[] data() {
            return new MyBean[] {
                    new MyBean(null, "v"),
                    new MyBean("v", null),
                    new MyBean("x", "y")
            };
        }

        @Parameterized.Parameter
        public MyBean myBean;

        @Test
        public void validatorReturnsErrors() {
            Set<ConstraintViolation<MyBean>> errors = validate(myBean);
            assertEquals(1, errors.size());
        }

        @Test
        public void errorIsAssociatedWithConfirmationField() {
            ConstraintViolation<MyBean> error = validate(myBean)
                    .iterator().next();

            Node node = error.getPropertyPath().iterator().next();

            assertEquals(ElementKind.PROPERTY, node.getKind());
            assertEquals("field2", node.getName());
        }
    }

    @RunWith(Parameterized.class)
    public static class WhenFieldsMatch {

        @Parameters(name = "{index}: {0}")
        public static MyBean[] data() {
            return new MyBean[] {
                    new MyBean(null, null),
                    new MyBean("x", "x")
            };
        }

        @Parameterized.Parameter
        public MyBean MyBean;

        @Test
        public void validatorReturnsNoErrors() {
            Set<ConstraintViolation<MyBean>> errors = validate(MyBean);
            assertEquals(0, errors.size());
        }
    }

}