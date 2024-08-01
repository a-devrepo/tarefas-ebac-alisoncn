package com.study.projeto.ui;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import com.study.projeto.App;
import org.junit.Assert;
import org.junit.Test;

public class InterfacePrincipalTest {
    
    @Test
    public void testeListaPessoasGeneroFeminino(){

        String dados=  String.format("1%sMaria%sSilva%s50%sF%sN%s2%sF%s3",
                System.lineSeparator(),System.lineSeparator(),System.lineSeparator(),System.lineSeparator()
        ,System.lineSeparator(),System.lineSeparator(),System.lineSeparator(),System.lineSeparator());
        byte[] bytes = dados.getBytes();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        System.setIn(byteArrayInputStream);

    String expected =
        "Pessoa{nome='Maria', sobreNome='Silva', idade=50, genero=FEMININO}";

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);
        System.setOut(printStream);

        App.main(new String[0]);

        String[] linhas = baos.toString().split(System.lineSeparator());
        Assert.assertEquals(expected,linhas[9]);

    }

}
