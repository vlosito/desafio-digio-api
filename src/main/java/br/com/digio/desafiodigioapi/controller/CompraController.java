package br.com.digio.desafiodigioapi.controller;

import br.com.digio.desafiodigioapi.dto.ClienteFielDTO;
import br.com.digio.desafiodigioapi.dto.CompraDetalhadaDTO;
import br.com.digio.desafiodigioapi.service.CompraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CompraController {
    private final CompraService compraService;

    @GetMapping("/compras")
    public ResponseEntity<List<CompraDetalhadaDTO>> listarCompras() {
        return ResponseEntity.ok(compraService.listarComprasOrdenadas());
    }

    @GetMapping("/maior-compra/{ano}")
    public ResponseEntity<CompraDetalhadaDTO> maiorCompraPorAno(@PathVariable int ano) {
        CompraDetalhadaDTO maiorCompra = compraService.maiorCompraPorAno(ano);
        if (maiorCompra == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(maiorCompra);
    }

    @GetMapping("/clientes-fieis")
    public ResponseEntity<List<ClienteFielDTO>> clientesFieis() {
        return ResponseEntity.ok(compraService.clientesFieis());
    }

    @GetMapping("/recomendacao/{cpf}/tipo")
    public ResponseEntity<String> recomendacaoVinho(@PathVariable String cpf) {
        return ResponseEntity.ok(compraService.recomendacaoVinho(cpf));
    }
}
