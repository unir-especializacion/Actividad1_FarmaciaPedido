package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.*;

import model.Pedido;

@SuppressWarnings("serial")
public class PedidoResumen extends JFrame {

    public PedidoResumen(Pedido pedido) {
        setTitle("Pedido al distribuidor " + pedido.getDistribuidor());
        setSize(500, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initUI(pedido);
        setVisible(true);
    }

    private void initUI(Pedido pedido) {
        JPanel panelMain = new JPanel();
        panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.Y_AXIS));
        panelMain.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        String medicamentoTexto = String.format(
            "%d unidades del %s %s",
            pedido.getCantidad(),
            pedido.getTipoMedicamento().toLowerCase(),
            pedido.getNombreMedicamento().toLowerCase()
        );

        JLabel lblTitulo = new JLabel("Resumen del Pedido");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblPedido = new JLabel(medicamentoTexto);
        lblPedido.setFont(new Font("Arial", Font.PLAIN, 14));
        lblPedido.setAlignmentX(CENTER_ALIGNMENT);

        JPanel panelBotones = new JPanel();
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.X_AXIS));
        panelBotones.setAlignmentX(CENTER_ALIGNMENT);

        JButton btnEnviar = new JButton("Enviar");
        btnEnviar.setPreferredSize(new Dimension(100, 30));
        btnEnviar.addActionListener(e -> {
            System.out.println("Pedido enviado");
            JOptionPane.showMessageDialog(this, "Pedido enviado correctamente");
            this.dispose();
        });

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setPreferredSize(new Dimension(100, 30));
        btnCancelar.addActionListener(e -> this.dispose());

        panelBotones.add(Box.createHorizontalGlue());
        panelBotones.add(btnEnviar);
        panelBotones.add(Box.createRigidArea(new Dimension(20, 0)));
        panelBotones.add(btnCancelar);
        panelBotones.add(Box.createHorizontalGlue());

        panelMain.add(lblTitulo);
        panelMain.add(Box.createRigidArea(new Dimension(0, 15)));
        panelMain.add(lblPedido);
        panelMain.add(Box.createRigidArea(new Dimension(0, 10)));
        
        String direccion;
        boolean p = pedido.isSucursalPrincipal();
        boolean s = pedido.isSucursalSecundaria();

        if (p && s) {
            direccion = "<html><div style='text-align: center;'>"
                + "Para la farmacia situada en Calle de la Rosa n. 28 y para la situada en Calle Alcazabilla n. 3"
                + "</div></html>";

            JLabel lblDireccion = new JLabel(direccion);
            lblDireccion.setFont(new Font("Arial", Font.PLAIN, 14));
            lblDireccion.setAlignmentX(CENTER_ALIGNMENT);
            panelMain.add(lblDireccion);
        } else {
            if (p) {
                direccion = "Para la farmacia situada en Calle de la Rosa n. 28";
            } else if (s) {
                direccion = "Para la farmacia situada en Calle Alcazabilla n. 3";
            } else {
                direccion = "No se ha seleccionado ninguna sucursal";
            }
            
            JLabel lblDireccion = new JLabel(direccion);
            lblDireccion.setFont(new Font("Arial", Font.PLAIN, 14));
            lblDireccion.setAlignmentX(CENTER_ALIGNMENT);
            panelMain.add(lblDireccion);
        }
        
        panelMain.add(panelBotones);

        add(panelMain, BorderLayout.CENTER);
    }
}