package br.com.fiap.toyquilo.controller;

import br.com.fiap.toyquilo.model.Brinquedo;
import br.com.fiap.toyquilo.service.BrinquedoService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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
    public ResponseEntity<List<EntityModel<Brinquedo>>> listarTodos() {
        List<EntityModel<Brinquedo>> brinquedos = service.listarTodos().stream()
                .map(this::toEntityModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(brinquedos);
    }

    @Operation(summary = "Buscar brinquedo por ID", responses = {
            @ApiResponse(responseCode = "200", description = "Brinquedo encontrado"),
            @ApiResponse(responseCode = "404", description = "Brinquedo não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Brinquedo>> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(toEntityModel(service.buscarPorId(id)));
    }

    @Operation(summary = "Criar novo brinquedo",
            requestBody = @RequestBody(
                    description = "Objeto Brinquedo a ser criado",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Brinquedo.class),
                            examples = @ExampleObject(value = """
                                {
                                  "nome": "Carrinho de Controle Remoto",
                                  "tipo": "Veículo",
                                  "classificacao": "Infantil",
                                  "tamanho": 0.5,
                                  "preco": 150.0
                                }
                            """))))
    @PostMapping
    public ResponseEntity<EntityModel<Brinquedo>> criar(@org.springframework.web.bind.annotation.RequestBody Brinquedo brinquedo) {
        Brinquedo salvo = service.salvar(brinquedo);
        return ResponseEntity.status(HttpStatus.CREATED).body(toEntityModel(salvo));
    }

    @Operation(summary = "Atualizar brinquedo por ID",
            requestBody = @RequestBody(
                    description = "Objeto Brinquedo atualizado",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Brinquedo.class),
                            examples = @ExampleObject(value = """
                                {
                                  "nome": "Carrinho de Controle Remoto",
                                  "tipo": "Veículo",
                                  "classificacao": "Infantil",
                                  "tamanho": 0.6,
                                  "preco": 160.0
                                }
                            """))))
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Brinquedo>> atualizar(@PathVariable Long id, @org.springframework.web.bind.annotation.RequestBody Brinquedo brinquedo) {
        return ResponseEntity.ok(toEntityModel(service.atualizar(id, brinquedo)));
    }

    @Operation(summary = "Atualização parcial do brinquedo por ID",
            requestBody = @RequestBody(
                    description = "Campos do Brinquedo a atualizar",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                {
                                  "preco": 170.0
                                }
                            """))))
    @PatchMapping("/{id}")
    public ResponseEntity<EntityModel<Brinquedo>> atualizarParcial(@PathVariable Long id, @org.springframework.web.bind.annotation.RequestBody Brinquedo brinquedo) {
        return ResponseEntity.ok(toEntityModel(service.atualizarParcial(id, brinquedo)));
    }

    @Operation(summary = "Deletar brinquedo por ID", responses = {
            @ApiResponse(responseCode = "204", description = "Brinquedo deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Brinquedo não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<Brinquedo> toEntityModel(Brinquedo brinquedo) {
        return EntityModel.of(brinquedo,
                linkTo(methodOn(BrinquedoController.class).buscarPorId(brinquedo.getId())).withSelfRel(),
                linkTo(methodOn(BrinquedoController.class).listarTodos()).withRel("brinquedos"));
    }
}
