package br.com.digio.desafiodigioapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteFielDTO {

    private String nomeCliente;
    private String cpfCliente;
    private int totalCompras;
    private BigDecimal valorTotal;
}
