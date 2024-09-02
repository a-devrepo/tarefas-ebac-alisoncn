package com.sudy.projeto.domain.factory;

import com.sudy.projeto.domain.model.Cliente;
import com.sudy.projeto.domain.model.Persistente;
import com.sudy.projeto.enums.StatusCliente;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteFactory extends Factory {
  @Override
  public Persistente create(ResultSet resultSet) throws SQLException {
    Cliente cliente = new Cliente();
    cliente.setId(resultSet.getLong("id_cliente"));
    cliente.setCpf(resultSet.getString("cpf"));
    cliente.setNome(resultSet.getString("nome"));
    cliente.setTelefone(resultSet.getString("telefone"));
    cliente.setEndereco(resultSet.getString("endereco"));
    cliente.setNumero(resultSet.getString("numero"));
    cliente.setCidade(resultSet.getString("cidade"));
    cliente.setEstado(resultSet.getString("estado"));
    cliente.setStatus(StatusCliente.getByName(resultSet.getString("status")));
    return cliente;
  }

  public Persistente create(
      String cpf,
      String nome,
      String telefone,
      String endereco,
      String numero,
      String cidade,
      String estado,
      StatusCliente status) {
    Cliente cliente = new Cliente();
    cliente.setCpf(cpf);
    cliente.setNome(nome);
    cliente.setTelefone(telefone);
    cliente.setEndereco(endereco);
    cliente.setNumero(numero);
    cliente.setCidade(cidade);
    cliente.setEstado(estado);
    cliente.setStatus(status);
    return cliente;
  }
}
