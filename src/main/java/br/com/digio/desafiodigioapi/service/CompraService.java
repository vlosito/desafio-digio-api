package br.com.digio.desafiodigioapi.service;

import br.com.digio.desafiodigioapi.client.MockClient;
import br.com.digio.desafiodigioapi.dto.ClienteCompraDTO;
import br.com.digio.desafiodigioapi.dto.ClienteFielDTO;
import br.com.digio.desafiodigioapi.dto.CompraDetalhadaDTO;
import br.com.digio.desafiodigioapi.dto.ProdutoDTO;
import br.com.digio.desafiodigioapi.exception.RegisterNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompraService {

    private final MockClient mockClient;

    public List<CompraDetalhadaDTO> listarComprasOrdenadas() {
        List<ProdutoDTO> produtos = mockClient.getProdutos();
        List<ClienteCompraDTO> clientes = mockClient.getClientesCompras();

        return clientes.stream()
                .flatMap(cliente -> cliente.getCompras().stream()
                        .map(compra -> {
                            ProdutoDTO produto = produtos.stream()
                                    .filter(p -> p.getCodigo().equals(compra.getCodigoProduto()))
                                    .findFirst()
                                    .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + compra.getCodigoProduto()));
                            return new CompraDetalhadaDTO(
                                    cliente.getNome(),
                                    cliente.getCpf(),
                                    produto.getTipoVinho(),
                                    produto.getSafra(),
                                    produto.getAnoCompra(),
                                    compra.getQuantidade(),
                                    BigDecimal.valueOf(compra.getQuantidade()).multiply(produto.getPreco())
                            );
                        }))
                .sorted(Comparator.comparing(CompraDetalhadaDTO::getValorTotal))
                .collect(Collectors.toList());
    }

    public CompraDetalhadaDTO maiorCompraPorAno(int ano) {
        List<ProdutoDTO> produtos = mockClient.getProdutos();
        List<ClienteCompraDTO> clientes = mockClient.getClientesCompras();

        return clientes.stream()
                .flatMap(cliente -> cliente.getCompras().stream()
                        .map(compra -> {
                            ProdutoDTO produto = produtos.stream()
                                    .filter(p -> p.getCodigo().equals(compra.getCodigoProduto()) && p.getAnoCompra() == ano)
                                    .findFirst()
                                    .orElse(null);
                            if (produto == null) return null;
                            return new CompraDetalhadaDTO(
                                    cliente.getNome(),
                                    cliente.getCpf(),
                                    produto.getTipoVinho(),
                                    produto.getSafra(),
                                    produto.getAnoCompra(),
                                    compra.getQuantidade(),
                                    BigDecimal.valueOf(compra.getQuantidade()).multiply(produto.getPreco())
                            );
                        }))
                .filter(Objects::nonNull)
                .max(Comparator.comparing(CompraDetalhadaDTO::getValorTotal))
                .orElseThrow(() -> new RegisterNotFoundException("Nenhuma compra encontrada para o ano " + ano));
    }

    public List<ClienteFielDTO> clientesFieis() {
        List<ProdutoDTO> produtos = mockClient.getProdutos();
        List<ClienteCompraDTO> clientes = mockClient.getClientesCompras();

        return clientes.stream()
                .map(cliente -> {
                    BigDecimal valorTotal = cliente.getCompras().stream()
                            .map(compra -> {
                                ProdutoDTO produto = produtos.stream()
                                        .filter(p -> p.getCodigo().equals(compra.getCodigoProduto()))
                                        .findFirst()
                                        .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + compra.getCodigoProduto()));
                                return BigDecimal.valueOf(compra.getQuantidade()).multiply(produto.getPreco());
                            })
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    return new ClienteFielDTO(
                            cliente.getNome(),
                            cliente.getCpf(),
                            cliente.getCompras().size(),
                            valorTotal
                    );
                })
                .sorted(Comparator.comparing(ClienteFielDTO::getValorTotal).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    public String recomendacaoVinho(String cpfCliente) {
        List<ProdutoDTO> produtos = mockClient.getProdutos();
        List<ClienteCompraDTO> clientes = mockClient.getClientesCompras();

        return clientes.stream()
                .filter(cliente -> cliente.getCpf().equals(cpfCliente))
                .flatMap(cliente -> cliente.getCompras().stream()
                        .map(compra -> produtos.stream()
                                .filter(produto -> produto.getCodigo().equals(compra.getCodigoProduto()))
                                .findFirst()
                                .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + compra.getCodigoProduto()))))
                .collect(Collectors.groupingBy(ProdutoDTO::getTipoVinho, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new RegisterNotFoundException("Nenhuma recomendação encontrada para o cliente " + cpfCliente));
    }
}
