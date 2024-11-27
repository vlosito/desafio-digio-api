package br.com.digio.desafiodigioapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompraDetalhadaDTO {

    private String nomeCliente;
    private String cpfCliente;
    private String tipoVinho;
    private Integer safra;
    private Integer anoCompra;
    private Integer quantidade;
    private BigDecimal valorTotal;
}
