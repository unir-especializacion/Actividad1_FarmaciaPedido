package controller;

import javax.swing.JOptionPane;

import model.Pedido;
import model.PedidoValidator;
import view.PedidoResumen;

public class PedidoController {

    public void procesarPedido(Pedido pedido) {
        try {
            PedidoValidator.validar(pedido);
            new PedidoResumen(pedido);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}