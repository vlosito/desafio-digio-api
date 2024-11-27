package br.com.digio.desafiodigioapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {

    private Long codigo;

    @JsonProperty("tipo_vinho")
    private String tipoVinho;

    private BigDecimal preco;

    private Integer safra;

    @JsonProperty("ano_compra")
    private Integer anoCompra;
}
