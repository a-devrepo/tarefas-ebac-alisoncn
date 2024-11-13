package com.study.projeto.beans;

import com.study.projeto.beans.util.ReplaceUtils;
import com.study.projeto.domain.model.relational.Cliente;
import com.study.projeto.domain.model.relational.Endereco;
import com.study.projeto.exceptions.DAOException;
import com.study.projeto.exceptions.RegistroNaoEncontradoException;
import com.study.projeto.services.impl.IClienteService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collection;

@Named
@ViewScoped
public class ClienteBean implements Serializable {

    private static final long serialVersionUID = 8714108176917436440L;
    private Cliente cliente;
    private Collection<Cliente> clientes;
    @Inject
    private IClienteService clienteService;
    private Boolean isUpdate;
    private String cpfMask;
    private String telMask;
    private String logradouro;
    private String numero;
    private String cidade;
    private String estado;

    @Inject
    FacesContext facesContext;

    @PostConstruct
    public void init() {
        try {
            this.isUpdate = false;
            criarCliente();
            this.clientes = clienteService.listar();
        } catch (DAOException e) {
            facesContext.getCurrentInstance().addMessage("growl",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info", e.getMessage()));
        }

    }

    private void criarCliente() {
        this.cliente = new Cliente();
        this.cliente.setEndereco(new Endereco());
    }

    public void cancel() {
        this.isUpdate = false;
    }

    public String add() {
        try {
            removerCaracteresInvalidos();
            limparCampos();
            clienteService.salvar(cliente);
            this.clientes = clienteService.listar();
            criarCliente();
            facesContext.getCurrentInstance().addMessage("growl",
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Cliente cadastrado com sucesso"));
        } catch (DAOException e) {
            facesContext.getCurrentInstance().addMessage("growl",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
        }
        return null;
    }

    public void delete(Cliente cliente) {
        try {
            clienteService.excluir(cliente);
            clientes.remove(cliente);
            facesContext.getCurrentInstance().addMessage("growl",
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Cliente removido com sucesso"));
        } catch (Exception e) {
            facesContext.getCurrentInstance().addMessage("growl",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
        }

    }

    public void edit(Cliente cliente) {
        try {
            this.isUpdate = true;
            this.cliente = cliente;
        } catch (Exception e) {
            facesContext.getCurrentInstance().addMessage("growl",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
        }

    }

    public void update() {
        try {
            removerCaracteresInvalidos();
            clienteService.atualizar(this.cliente);
            this.clientes = clienteService.listar();
            criarCliente();
            setIsUpdate(false);
            facesContext.getCurrentInstance().addMessage("growl",
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Cliente atualizado com sucesso"));
        } catch (DAOException | RegistroNaoEncontradoException e) {
            FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(e.getMessage()));
        }
    }

    private void removerCaracteresInvalidos() {
        String cpf = ReplaceUtils.replace(cliente.getCpf(), ".", "-");
        this.cliente.setCpf(cpf);

        String tel = ReplaceUtils.replace(cliente.getTelefone(), "(", ")", " ", "-");
        this.cliente.setTelefone(tel);
    }

    private void limparCampos() {
        setCpfMask(null);
        setTelMask(null);
    }

    public String voltarTelaInicial() {
        return "/index.xhtml";
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }


    public Collection<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(Collection<Cliente> clientes) {
        this.clientes = clientes;
    }

    public String getCpfMask() {
        return cpfMask;
    }

    public void setCpfMask(String cpfMask) {
        this.cpfMask = cpfMask;
    }

    public String getTelMask() {
        return telMask;
    }

    public void setTelMask(String telMask) {
        this.telMask = telMask;
    }

    public Boolean getIsUpdate() {
        return isUpdate;
    }

    public Boolean setIsUpdate(Boolean isUpdate) {
        return this.isUpdate = isUpdate;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
