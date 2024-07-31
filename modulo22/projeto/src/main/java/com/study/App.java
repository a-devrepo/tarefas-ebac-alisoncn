package com.study;

import com.study.dao.IGenericDao;
import com.study.dao.PessoaDao;
import com.study.domain.Pessoa;
import com.study.domain.builder.Diretor;
import com.study.domain.builder.HomemBuilder;
import com.study.enums.Genero;
import com.study.ui.InterfacePrincipal;

import java.util.Scanner;

public class App {
  public static void main(String[] args) {
    InterfacePrincipal principal = new InterfacePrincipal();
    principal.mostrarDialogo();
  }
}
