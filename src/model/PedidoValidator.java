package model;

public class PedidoValidator {
	public static void validar(Pedido pedido) throws Exception {
		if (pedido.getNombreMedicamento() == null || pedido.getNombreMedicamento().trim().isEmpty()) {
            throw new Exception("El nombre del medicamento es obligatorio.");
        }

        if (!pedido.getNombreMedicamento().matches(".*[a-zA-Z0-9].*")) {
            throw new Exception("El nombre del medicamento debe ser alfanumérico.");
        }
		
		if (pedido.getTipoMedicamento() == null || pedido.getTipoMedicamento().isEmpty()) {
	       throw new Exception("Debe seleccionar un tipo de medicamento.");
        }

		if (pedido.getCantidad() <= 0) {
		    throw new Exception("La cantidad debe ser un número entero positivo y válido.");
		}

        if (pedido.getDistribuidor() == null || pedido.getDistribuidor().isEmpty()) {
            throw new Exception("Debe seleccionar un distribuidor.");
        }

        if (!pedido.isSucursalPrincipal() && !pedido.isSucursalSecundaria()) {
            throw new Exception("Debe seleccionar al menos una sucursal.");
        }
	}
}