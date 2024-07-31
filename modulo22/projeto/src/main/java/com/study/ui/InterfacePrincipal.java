package com.study.ui;

import com.study.dao.PessoaDao;
import com.study.domain.Pessoa;
import com.study.domain.builder.Diretor;
import com.study.domain.builder.HomemBuilder;
import com.study.domain.builder.MulherBuilder;
import com.study.domain.builder.OutrosBuilder;
import com.study.enums.Genero;
import com.study.exceptions.DadosInvalidosException;
import com.study.service.IPessoaService;
import com.study.service.PessoaService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InterfacePrincipal {

  IPessoaService service;

  public InterfacePrincipal() {
    service = new PessoaService(new PessoaDao());
  }

  public void mostrarCadastro() {
    Scanner scanner = new Scanner(System.in);
    String option = "N";
    do {
      try {
        System.out.println("Informe os seguintes dados");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Sobrenome: ");
        String sobrenome = scanner.nextLine();

        System.out.print("Idade: ");
        int idade = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Gênero: Masculino(M) | Feminino(F) | Outros(O) ");
        Genero genero = Genero.getBySigla(scanner.nextLine());

        switch (genero) {
          case MASCULINO -> gravarGeneroMasculino(nome, sobrenome, idade);
          case FEMININO -> gravarGeneroFeminino(nome, sobrenome, idade);
          case OUTROS -> gravarGeneroOutros(nome, sobrenome, idade);
          default -> throw new IllegalArgumentException("Opção de gênero inválida");
        }

        System.out.println("Pessoa cadastrada\n");
        System.out.print("Deseja continuar? S/N:");
        option = scanner.nextLine();
      } catch (Exception e) {
        throw new DadosInvalidosException("Dados inválidos: " + e.getMessage());
      }

    } while (option.equalsIgnoreCase("S"));
  }

  public void listarPessoas() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Informe o gênero desejado M/F: ");
    String genero = scanner.nextLine();
    listarPorGenero(genero);

  }

  public void mostrarDialogo() {
    Scanner scanner = new Scanner(System.in);
    String option = "";

    do {
      try{
        System.out.println("Escolha as seguintes opções: ");
        System.out.println("1 - Cadastrar Pessoa");
        System.out.println("2 - Listar Pessoas Por gênero");
        System.out.println("3 - Sair");
        option = scanner.nextLine();

        switch (option) {
          case "1" -> mostrarCadastro();
          case "2" -> listarPessoas();
          case "3" -> System.out.println("Saindo...");
          default -> throw new IllegalArgumentException("Opção inválida");
        }
      }catch (InputMismatchException | IllegalArgumentException e){
        System.out.println("Dados inválidos");
      } catch (Exception e){
        System.out.println(e.getMessage());
      }
      System.out.println();
    } while (!option.equals("3"));

  }

  private void gravarGeneroOutros(String nome, String sobrenome, int idade) {
    OutrosBuilder builder = new OutrosBuilder();
    Diretor diretor = new Diretor();
    diretor.setBuilder(builder);
    diretor.criarPessoa(nome, sobrenome, idade);
    Pessoa pessoa = builder.getPessoa();
    service.cadastrar(pessoa);
  }

  private void gravarGeneroFeminino(String nome, String sobrenome, int idade) {
    MulherBuilder builder = new MulherBuilder();
    Diretor diretor = new Diretor();
    diretor.setBuilder(builder);
    diretor.criarPessoa(nome, sobrenome, idade);
    Pessoa pessoa = builder.getPessoa();
    service.cadastrar(pessoa);
  }

  public void gravarGeneroMasculino(String nome, String sobrenome, int idade) {
    HomemBuilder builder = new HomemBuilder();
    Diretor diretor = new Diretor();
    diretor.setBuilder(builder);
    diretor.criarPessoa(nome, sobrenome, idade);
    Pessoa pessoa = builder.getPessoa();
    service.cadastrar(pessoa);
  }

  public void listarPorGenero(String siglaGenero) throws RuntimeException{
    PessoaService service = (PessoaService) this.service;
    service.listarPessoaPorGenero(siglaGenero).forEach(System.out::println);
  }
}
