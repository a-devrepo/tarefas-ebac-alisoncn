package com.study.projeto.ui;

import com.study.projeto.domain.builder.Diretor;
import com.study.projeto.domain.builder.GeneroFemininoBuilder;
import com.study.projeto.domain.builder.GeneroMasculinoBuilder;
import com.study.projeto.domain.builder.GeneroOutroBuilder;
import com.study.projeto.domain.dao.ImplPessoaDAO;
import com.study.projeto.domain.model.Pessoa;
import com.study.projeto.enums.Genero;
import com.study.projeto.service.IPessoaService;
import com.study.projeto.service.ImplPessoaService;
import com.study.projeto.service.exceptions.DadosInvalidosException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InterfacePrincipal {
  IPessoaService service;
  Scanner scanner;

  public InterfacePrincipal() {
    service = new ImplPessoaService(new ImplPessoaDAO());
    scanner = new Scanner(System.in);
  }

  public void mostrarDialogo() {

    String option = "";

    do {
      try {

        System.out.printf(
            "1 Cadastrar Pessoa%s2 Listar por gênero%s3 Sair%s",
                System.lineSeparator(),System.lineSeparator(),System.lineSeparator());

        option = scanner.nextLine();

        switch (option) {
          case "1" -> mostrarCadastro();
          case "2" -> listarPessoas();
          case "3" -> System.out.println("Saindo");
          default -> throw new IllegalArgumentException("Opção inválida");
        }
      } catch (InputMismatchException | IllegalArgumentException e) {
        System.out.println("Dados inválidos");
      } catch (DadosInvalidosException e) {
        System.out.println(e.getMessage());
      }
    } while (!option.equals("3"));
  }

  public void mostrarCadastro() {

    String option;
    do {
      try {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Sobrenome: ");
        String sobrenome = scanner.nextLine();

        System.out.print("Idade: ");
        int idade = Integer.parseInt(scanner.nextLine());

        System.out.print("Masculino(M)|Feminino(F)|Outros(O) ");
        Genero genero = Genero.getBySigla(scanner.nextLine());

        switch (genero) {
          case MASCULINO -> gravarGeneroMasculino(nome, sobrenome, idade);
          case FEMININO -> gravarGeneroFeminino(nome, sobrenome, idade);
          case OUTROS -> gravarGeneroOutros(nome, sobrenome, idade);
          default -> throw new IllegalArgumentException("Opção inválida");
        }

        System.out.println("Pessoa cadastrada\n");
        System.out.print("Deseja continuar? S/N:");
        option = scanner.nextLine();
      } catch (Exception e) {
        throw new DadosInvalidosException("Dados inválidos");
      }

    } while (option.equalsIgnoreCase("S"));
  }

  public void listarPessoas() {

    System.out.print("Masculino(M)/Feminino(F): ");
    String genero = scanner.nextLine();
    listarPorGenero(genero);
  }

  private void gravarGeneroOutros(String nome, String sobrenome, int idade) {
    GeneroOutroBuilder builder = new GeneroOutroBuilder();
    Diretor diretor = new Diretor();
    diretor.setBuilder(builder);
    diretor.criarPessoa(nome, sobrenome, idade);
    Pessoa pessoa = builder.getPessoa();
    service.cadastrar(pessoa);
  }

  private void gravarGeneroFeminino(String nome, String sobrenome, int idade) {
    GeneroFemininoBuilder builder = new GeneroFemininoBuilder();
    Diretor diretor = new Diretor();
    diretor.setBuilder(builder);
    diretor.criarPessoa(nome, sobrenome, idade);
    Pessoa pessoa = builder.getPessoa();
    service.cadastrar(pessoa);
  }

  public void gravarGeneroMasculino(String nome, String sobrenome, int idade) {
    GeneroMasculinoBuilder builder = new GeneroMasculinoBuilder();
    Diretor diretor = new Diretor();
    diretor.setBuilder(builder);
    diretor.criarPessoa(nome, sobrenome, idade);
    Pessoa pessoa = builder.getPessoa();
    service.cadastrar(pessoa);
  }

  public void listarPorGenero(String siglaGenero) throws RuntimeException {
    ImplPessoaService service = (ImplPessoaService) this.service;
    System.out.println();
    service.listarPessoaPorGenero(siglaGenero).forEach(System.out::println);
  }
}
