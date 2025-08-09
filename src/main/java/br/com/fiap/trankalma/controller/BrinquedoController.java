package br.com.fiap.trankalma.controller;

import br.com.fiap.trankalma.model.Brinquedo;
import br.com.fiap.trankalma.service.BrinquedoService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/brinquedos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@Tag(name = "Brinquedos", description = "Gerenciamento de brinquedos do sistema")
public class BrinquedoController {

    private final BrinquedoService service;

    @Operation(summary = "Listar todos os brinquedos", responses = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Brinquedo.class)))
    })
    @GetMapping
    public List<EntityModel<Brinquedo>> listarTodos() {
        return service.listarTodos().stream()
                .map(this::toEntityModel)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Buscar brinquedo por ID", responses = {
            @ApiResponse(responseCode = "200", description = "Brinquedo encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Brinquedo.class))),
            @ApiResponse(responseCode = "404", description = "Brinquedo não encontrado")
    })
    @GetMapping("/{id}")
    public EntityModel<Brinquedo> buscarPorId(@PathVariable Long id) {
        return toEntityModel(service.buscarPorId(id));
    }

    @Operation(summary = "Criar novo brinquedo",
            requestBody = @RequestBody(description = "Objeto Brinquedo a ser criado", required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Brinquedo.class),
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(value = """
                    {
                      "nome": "Carrinho de Controle Remoto",
                      "tipo": "Veículo",
                      "classificacao": "Infantil",
                      "tamanho": 0.5,
                      "preco": 150.0
                    }
                """))),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Brinquedo criado com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Brinquedo.class)))
            })
    @PostMapping
    public EntityModel<Brinquedo> criar(@RequestBody Brinquedo brinquedo) {
        Brinquedo salvo = service.salvar(brinquedo);
        return toEntityModel(salvo);
    }

    @Operation(summary = "Atualizar brinquedo por ID",
            requestBody = @RequestBody(description = "Objeto Brinquedo atualizado", required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Brinquedo.class),
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(value = """
                    {
                      "nome": "Carrinho de Controle Remoto",
                      "tipo": "Veículo",
                      "classificacao": "Infantil",
                      "tamanho": 0.6,
                      "preco": 160.0
                    }
                """))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Brinquedo atualizado com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Brinquedo.class))),
                    @ApiResponse(responseCode = "404", description = "Brinquedo não encontrado")
            })
    @PutMapping("/{id}")
    public EntityModel<Brinquedo> atualizar(@PathVariable Long id, @RequestBody Brinquedo brinquedo) {
        return toEntityModel(service.atualizar(id, brinquedo));
    }

    @Operation(summary = "Atualização parcial do brinquedo por ID",
            requestBody = @RequestBody(description = "Campos do Brinquedo a atualizar", required = true,
                    content = @Content(mediaType = "application/json",
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(value = """
                    {
                      "preco": 170.0
                    }
                """))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Brinquedo atualizado parcialmente",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Brinquedo.class))),
                    @ApiResponse(responseCode = "404", description = "Brinquedo não encontrado")
            })
    @PatchMapping("/{id}")
    public EntityModel<Brinquedo> atualizarParcial(@PathVariable Long id, @RequestBody Brinquedo brinquedo) {
        return toEntityModel(service.atualizarParcial(id, brinquedo));
    }

    @Operation(summary = "Deletar brinquedo por ID", responses = {
            @ApiResponse(responseCode = "204", description = "Brinquedo deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Brinquedo não encontrado")
    })
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }

    private EntityModel<Brinquedo> toEntityModel(Brinquedo brinquedo) {
        return EntityModel.of(brinquedo,
                linkTo(methodOn(BrinquedoController.class).buscarPorId(brinquedo.getId())).withSelfRel(),
                linkTo(methodOn(BrinquedoController.class).listarTodos()).withRel("brinquedos"));
    }
}
