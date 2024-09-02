package com.sudy.projeto.anotacoes;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Coluna {
    String dbName();
    String setJavaName();
}
