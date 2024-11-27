package br.com.digio.desafiodigioapi.client;

import br.com.digio.desafiodigioapi.dto.ClienteCompraDTO;
import br.com.digio.desafiodigioapi.dto.ProdutoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "mockClient", url = "https://rgr3viiqdl8sikgv.public.blob.vercel-storage.com")
public interface MockClient {

    @GetMapping("/produtos-mnboX5IPl6VgG390FECTKqHsD9SkLS.json")
    List<ProdutoDTO> getProdutos();

    @GetMapping("/clientes-Vz1U6aR3GTsjb3W8BRJhcNKmA81pVh.json")
    List<ClienteCompraDTO> getClientesCompras();
}
