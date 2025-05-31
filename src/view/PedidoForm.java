package view;

import model.Pedido;

import javax.swing.*;

import controller.PedidoController;

import java.awt.*;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class PedidoForm extends JFrame {

    private JTextField txtNombreMedicamento, txtCantidad;
    private JComboBox<String> comboTipo;
    private JRadioButton radioCofarma, radioEmpsephar, radioCemefar;
    private JCheckBox chkPrincipal, chkSecundaria;
    private ButtonGroup distribuidorGroup;

    public PedidoForm() {
        setTitle("Formulario de Pedido de Medicamentos");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        initUI();
        setVisible(true);
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;

        addLabel(panel, "Nombre del Medicamento:", gbc, 0, y);
        txtNombreMedicamento = new JTextField();
        addField(panel, txtNombreMedicamento, gbc, 1, y++);

        addLabel(panel, "Tipo del Medicamento:", gbc, 0, y);
        comboTipo = new JComboBox<>(new String[]{
                "", "Analgésico", "Analéptico", "Anestésico", "Antiácido", "Antidepresivo", "Antibiótico"
        });
        addField(panel, comboTipo, gbc, 1, y++);

        addLabel(panel, "Cantidad:", gbc, 0, y);
        txtCantidad = new JTextField();
        addField(panel, txtCantidad, gbc, 1, y++);

        addLabel(panel, "Distribuidor:", gbc, 0, y);
        JPanel distribuidorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        radioCofarma = new JRadioButton("Cofarma");
        radioEmpsephar = new JRadioButton("Empsephar");
        radioCemefar = new JRadioButton("Cemefar");
        distribuidorGroup = new ButtonGroup();
        distribuidorGroup.add(radioCofarma);
        distribuidorGroup.add(radioEmpsephar);
        distribuidorGroup.add(radioCemefar);
        distribuidorPanel.add(radioCofarma);
        distribuidorPanel.add(radioEmpsephar);
        distribuidorPanel.add(radioCemefar);
        addField(panel, distribuidorPanel, gbc, 1, y++);

        addLabel(panel, "Sucursal:", gbc, 0, y);
        JPanel sucursalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        chkPrincipal = new JCheckBox("Sucursal Principal");
        chkSecundaria = new JCheckBox("Sucursal Secundaria");
        sucursalPanel.add(chkPrincipal);
        sucursalPanel.add(chkSecundaria);
        addField(panel, sucursalPanel, gbc, 1, y++);

        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnConfirmar = new JButton("Confirmar");
        JButton btnBorrar = new JButton("Borrar");
        btnConfirmar.setPreferredSize(new Dimension(120, 30));
        btnBorrar.setPreferredSize(new Dimension(120, 30));
        
        PedidoController controller = new PedidoController();

        btnConfirmar.addActionListener((ActionEvent e) -> {
        	Pedido pedido;
            try {
            	pedido = obtenerDatosFormulario();
                controller.procesarPedido(pedido);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnBorrar.addActionListener(e -> limpiarFormulario());

        botonesPanel.add(btnConfirmar);
        botonesPanel.add(btnBorrar);

        add(panel, BorderLayout.CENTER);
        add(botonesPanel, BorderLayout.SOUTH);
    }

    private void addLabel(JPanel panel, String text, GridBagConstraints gbc, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        panel.add(new JLabel(text), gbc);
    }

    private void addField(JPanel panel, JComponent field, GridBagConstraints gbc, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        panel.add(field, gbc);
    }

    private void limpiarFormulario() {
        txtNombreMedicamento.setText("");
        comboTipo.setSelectedIndex(0);
        txtCantidad.setText("");
        distribuidorGroup.clearSelection();
        chkPrincipal.setSelected(false);
        chkSecundaria.setSelected(false);
    }

    private Pedido obtenerDatosFormulario() throws Exception {
        String nombre = txtNombreMedicamento.getText().trim();
        String tipo = (String) comboTipo.getSelectedItem();
        String cantidadTexto = txtCantidad.getText().trim();

        if (cantidadTexto.isEmpty()) {
            throw new Exception("La cantidad es obligatoria.");
        }

        int cantidad;
        try {
            cantidad = Integer.parseInt(cantidadTexto);
        } catch (NumberFormatException ex) {
            throw new Exception("La cantidad debe ser un número entero.");
        }

        String distribuidor = null;
        if (radioCofarma.isSelected()) distribuidor = "Cofarma";
        else if (radioEmpsephar.isSelected()) distribuidor = "Empsephar";
        else if (radioCemefar.isSelected()) distribuidor = "Cemefar";

        boolean principal = chkPrincipal.isSelected();
        boolean secundaria = chkSecundaria.isSelected();

        return new Pedido(nombre, tipo, cantidad, distribuidor, principal, secundaria);
    }
}