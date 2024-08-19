package com.sudy.projeto.anotacoes;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Codigo {
    String value();
}
