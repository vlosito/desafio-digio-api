package br.com.digio.desafiodigioapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteCompraDTO {

    private String nome;

    private String cpf;

    private List<CompraDTO> compras;
}
