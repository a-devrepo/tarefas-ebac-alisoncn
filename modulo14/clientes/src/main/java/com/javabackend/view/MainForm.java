package com.javabackend.view;

import com.javabackend.dao.ClienteMapDAO;
import com.javabackend.dao.IClienteDao;
import com.javabackend.domain.Cliente;
import java.util.List;
import java.util.Arrays;
import java.util.Collection;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author alison
 */
public class MainForm extends javax.swing.JFrame {

    private Cliente cliente = new Cliente();
    private List<Cliente> listaClientes;
    private IClienteDao iClienteDao = new ClienteMapDAO();

    /**
     * Creates new form MainForm
     */
    public MainForm() {
        initComponents();
    }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        nomeTextField = new javax.swing.JTextField();
        cpfTextField = new javax.swing.JTextField();
        telefoneTextField = new javax.swing.JTextField();
        enderecoTextField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        numeroTextField = new javax.swing.JTextField();
        cidadeTextField = new javax.swing.JTextField();
        estadoTextField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaClientes = new javax.swing.JTable();
        salvarButton = new javax.swing.JButton();
        atualizarButton = new javax.swing.JButton();
        excluirButton = new javax.swing.JButton();
        limparButton = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        buscaCpfField = new javax.swing.JTextField();
        buscarCpfButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Controle de Clientes");

        jLabel1.setText("Nome");

        jLabel2.setText("CPF");

        jLabel3.setText("Telefone");

        jLabel4.setText("Endereço");

        telefoneTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                telefoneTextFieldActionPerformed(evt);
            }
        });

        jLabel7.setText("Estado");

        jLabel6.setText("Cidade");

        jLabel5.setText("Número");

        cidadeTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cidadeTextFieldActionPerformed(evt);
            }
        });

        tabelaClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Nome", "CPF", "Telefone", "Endereço", "Número", "Cidade", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tabelaClientes);
        if (tabelaClientes.getColumnModel().getColumnCount() > 0) {
            tabelaClientes.getColumnModel().getColumn(0).setResizable(false);
            tabelaClientes.getColumnModel().getColumn(1).setResizable(false);
            tabelaClientes.getColumnModel().getColumn(2).setResizable(false);
            tabelaClientes.getColumnModel().getColumn(4).setResizable(false);
            tabelaClientes.getColumnModel().getColumn(5).setResizable(false);
            tabelaClientes.getColumnModel().getColumn(6).setResizable(false);
        }

        salvarButton.setText("Salvar");
        salvarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salvarButtonActionPerformed(evt);
            }
        });

        atualizarButton.setText("Atualizar");
        atualizarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                atualizarButtonActionPerformed(evt);
            }
        });

        excluirButton.setText("Excluir");
        excluirButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                excluirButtonActionPerformed(evt);
            }
        });

        limparButton.setText("Limpar");
        limparButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limparButtonActionPerformed(evt);
            }
        });

        jLabel8.setText("Busca por CPF");

        buscaCpfField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscaCpfFieldActionPerformed(evt);
            }
        });

        buscarCpfButton.setText("Buscar");
        buscarCpfButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarCpfButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nomeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cpfTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(telefoneTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(enderecoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(estadoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cidadeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(numeroTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(salvarButton)
                        .addGap(18, 18, 18)
                        .addComponent(atualizarButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(excluirButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(limparButton))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(12, 12, 12)
                        .addComponent(buscaCpfField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buscarCpfButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 608, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(nomeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cpfTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(telefoneTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(enderecoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(numeroTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(cidadeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(9, 9, 9))
                            .addComponent(estadoTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(salvarButton)
                            .addComponent(atualizarButton)
                            .addComponent(excluirButton)
                            .addComponent(limparButton))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(buscaCpfField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buscarCpfButton)
                            .addComponent(jLabel8)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cidadeTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cidadeTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cidadeTextFieldActionPerformed

    private void telefoneTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_telefoneTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_telefoneTextFieldActionPerformed

    private void salvarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salvarButtonActionPerformed

        String nome = nomeTextField.getText();
        String cpf = cpfTextField.getText();
        String telefone = telefoneTextField.getText();
        String endereco = enderecoTextField.getText();
        String numero = numeroTextField.getText();
        String cidade = cidadeTextField.getText();
        String estado = estadoTextField.getText();

        boolean cadastrado = false;
        StringBuilder message = new StringBuilder(
                "É necessário preencher o campo: ");

        if (!validaCamposCrud().isEmpty()) {

            message.append(validaCamposCrud());
            JOptionPane.showMessageDialog(null,
                    message, "ERRO",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {

            Cliente cliente = criarCliente(nome, cpf, telefone,
                    endereco, numero, cidade, estado);
            cadastrado = iClienteDao.cadastrar(cliente);

            if (cadastrado) {
                JOptionPane.showMessageDialog(null,
                        "Cliente cadastrado com sucesso", "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
                loadData();
            } else {
                JOptionPane.showMessageDialog(null,
                        "Erro ao cadastrar Cliente", "ERRO",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }


    }//GEN-LAST:event_salvarButtonActionPerformed

    private void atualizarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_atualizarButtonActionPerformed
        StringBuilder message = new StringBuilder();

        if (!validaCamposCrud().isEmpty()) {

            message.append(validaCamposCrud());
            JOptionPane.showMessageDialog(null,
                    message, "ERRO",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            Cliente cliente = iClienteDao.consultar(buscaCpfField.getText());

            if (cliente == null) {
                JOptionPane.showMessageDialog(null,
                        "Cliente não encontrado", "ERRO",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                cliente.setNome(nomeTextField.getText());
                cliente.setEndereco(enderecoTextField.getText());
                cliente.setNumero(numeroTextField.getText());
                cliente.setTelefone(telefoneTextField.getText());
                cliente.setCidade(cidadeTextField.getText());
                cliente.setEstado(estadoTextField.getText());

                iClienteDao.alterar(cliente);

                JOptionPane.showMessageDialog(null,
                        "Cliente atualizado com sucesso", "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
                loadData();
            }

        }
    }//GEN-LAST:event_atualizarButtonActionPerformed

    private void buscarCpfButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarCpfButtonActionPerformed

        if (campoBuscaValido()) {
            String cpf = buscaCpfField.getText();
            Cliente cliente = iClienteDao.consultar(cpf);

            if (cliente == null) {
                JOptionPane.showMessageDialog(null,
                        "Cliente não encontrado", "ERRO",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                nomeTextField.setText(cliente.getNome());
                cpfTextField.setText(cliente.getCpf());
                cidadeTextField.setText(cliente.getCidade());
                enderecoTextField.setText(cliente.getEndereco());
                estadoTextField.setText(cliente.getEstado());
                numeroTextField.setText(cliente.getNumero());
                telefoneTextField.setText(cliente.getTelefone());
            }

        } else {
            JOptionPane.showMessageDialog(null,
                    "É necessário informar um cpf", "ERRO",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_buscarCpfButtonActionPerformed

    private void excluirButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_excluirButtonActionPerformed

        if (campoBuscaValido()) {
            iClienteDao.excluir(buscaCpfField.getText());

            JOptionPane.showMessageDialog(null,
                    "Cliente excluído com sucesso", "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
            loadData();
        } else {
            JOptionPane.showMessageDialog(null,
                    "É necessário informar um cpf", "ERRO",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_excluirButtonActionPerformed

    private void limparButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limparButtonActionPerformed
        limparCampos();
    }//GEN-LAST:event_limparButtonActionPerformed

    private void buscaCpfFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscaCpfFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buscaCpfFieldActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton atualizarButton;
    private javax.swing.JTextField buscaCpfField;
    private javax.swing.JButton buscarCpfButton;
    private javax.swing.JTextField cidadeTextField;
    private javax.swing.JTextField cpfTextField;
    private javax.swing.JTextField enderecoTextField;
    private javax.swing.JTextField estadoTextField;
    private javax.swing.JButton excluirButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton limparButton;
    private javax.swing.JTextField nomeTextField;
    private javax.swing.JTextField numeroTextField;
    private javax.swing.JButton salvarButton;
    private javax.swing.JTable tabelaClientes;
    private javax.swing.JTextField telefoneTextField;
    // End of variables declaration//GEN-END:variables

    public void loadData() {
        DefaultTableModel model = new DefaultTableModel(
                new String[]{"Nome", "Cpf", "Telefone", "Endereco",
                    "Número", "Cidade", "Estado"}, 0);

        tabelaClientes.setModel(model);
        Collection<Cliente> listaClientes = iClienteDao.buscarTodos();

        for (Cliente cliente : listaClientes) {
            model.addRow(
                    new Object[]{cliente.getNome(), cliente.getCpf(),
                        cliente.getTelefone(), cliente.getEndereco(),
                        cliente.getNumero(), cliente.getCidade(),
                        cliente.getEstado()});
        }

    }

    private static Cliente criarCliente(String nome, String cpf,
            String telefone, String endereco, String numero, String cidade,
            String estado) {
        Cliente cliente = new Cliente(nome, cpf, telefone, endereco,
                numero, cidade, estado);
        return cliente;
    }

    private void limparCampos() {
        nomeTextField.setText("");
        cpfTextField.setText("");
        enderecoTextField.setText("");
        telefoneTextField.setText("");
        numeroTextField.setText("");
        cidadeTextField.setText("");
        estadoTextField.setText("");
        buscaCpfField.setText("");
    }

    private boolean campoBuscaValido() {
        boolean valido = true;
        if (buscaCpfField.getText().isBlank()) {
            valido = false;
        }
        return valido;
    }

    private String validaCamposCrud() {
        String message = "";
        if (nomeTextField.getText().isBlank()) {
            message = " nome";
        } else if (cpfTextField.getText().isBlank()) {
            message = " cpf";
        } else if (telefoneTextField.getText().isBlank()) {
            message = " telefone";
        } else if (enderecoTextField.getText().isBlank()) {
            message = " endereço";
        } else if (numeroTextField.getText().isBlank()) {
            message = " número";
        } else if (cidadeTextField.getText().isBlank()) {
            message = " cidade";
        } else if (estadoTextField.getText().isBlank()) {
            message = " estado";
        }

        return message;
    }

}
