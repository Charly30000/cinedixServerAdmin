package com.cinedix.server.admin.app.controllers;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cinedix.server.admin.app.constantes.CEstadoEntrada;
import com.cinedix.server.admin.app.constantes.CTransaccionPago;
import com.cinedix.server.admin.app.models.entity.Entrada;
import com.cinedix.server.admin.app.models.service.IEntradaService;

@Controller
@RequestMapping("/cinedix")
public class TransaccionPagoController {
	
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	private IEntradaService entradaService;

	@Secured("ROLE_USER")
	@GetMapping("/transaccion/sendtobank")
	public String enviarAlBanco(@RequestParam(name = "codigo") String codigo, Authentication authentication) {
		Entrada entrada = entradaService.findByCodigoAndUsuarioUsername(codigo, authentication.getName());
		String codComercio = CTransaccionPago.COD_COMERCIO;
		String codPedido = entrada.getCodigo();
		double importe = entrada.getSitiosOcupados().size() * CTransaccionPago.COSTE_ENTRADA;
		String concepto = "Comprar entrada";
		
		return String.format("redirect:http://192.168.1.39/DAM/tfg/pasarela-simulacion-master/index.php?"
				+ "cod_comercio=%s&cod_pedido=%s&importe=%.2f&concepto=%s", 
				codComercio, codPedido, importe, concepto);
	}
	
	@RequestMapping("/informa")
	public @ResponseBody Map<String, String> informarAlBanco(
			@RequestParam(name = "cod_pedido") String codPedido,
			@RequestParam(name = "importe") String importeExternal,
			@RequestParam(name = "estado") String estado,
			@RequestParam(name = "cod_operacion") int codOperacion) {
		Map<String, String> retorno = new HashMap<>();
		try {
			double importe = Double.parseDouble(importeExternal.replace(",", "."));
			Entrada entrada = entradaService.findByCodigo(codPedido);
			switch (estado) {
			case "ok":
				if (entrada.getSitiosOcupados().size() * CTransaccionPago.COSTE_ENTRADA == importe) {
					entrada.setEstado(CEstadoEntrada.PAGADO);
					entradaService.save(entrada);
					retorno.put("message", "ok");
				} else {
					retorno.put("error", "Operacion no realizada, precios distintos");
				}
				break;
			case "nook":
				retorno.put("message", "Pago no realizado, no se ha completado con exito");
				break;
			case "cancelado":
				entrada.setEstado(CEstadoEntrada.CANCELADO);
				entradaService.save(entrada);
				retorno.put("message", "Pago cancelado por el usuario");
				break;

			default:
				retorno.put("error", "estado de la compra desconocido, no se ha completado el pago correctamente");
				break;
			}
			
		} catch(NumberFormatException ex) {
			retorno.put("error", "Error al intentar parsear el precio");
			logger.error("Error al parsear el precio: " + ex.getMessage());
			return retorno;
		} catch(Exception ex) {
			logger.error("Error desconocido: " + ex.getMessage());
			ex.printStackTrace();
			retorno.put("error", "Error desconocido, vuelve a intentarlo por favor");
			return retorno;
		}
		
		return retorno;
	}
	
	@Secured("ROLE_USER")
	@RequestMapping("/retorno")
	public String finalizarTransaccionPago(Model model) {
		model.addAttribute("titulo", "Transaccion finalizada");
		return "transactionEnded";
	}
}
