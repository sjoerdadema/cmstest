package nl.kristalsoftware.kristalcms.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

@Retention(RUNTIME)
public @interface JcrContentNode {
	String name() default "";
}
