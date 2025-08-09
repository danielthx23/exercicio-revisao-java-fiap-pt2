package br.com.fiap.trankalma.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("FIAP – Faculdade de Informática e Administração Paulista")
                        .description("""
                                Curso de Tecnologia em Análise e Desenvolvimento de Sistemas (TDS)
                                Professor: Dr. Marcel Stefan Wagner
                                Exercício de Revisão
                                Spring com Persistência, Lombok, HATEOAS e Deploy

                                **Autores:**
                                - Daniel Saburo Akiyama (RM 558263)
                                - Danilo Correia e Silva (RM 557540)
                                - João Pedro Rodrigues da Costa (RM 558199)
                                """)
                        .version("v0.0.1"))
                .externalDocs(new ExternalDocumentation()
                        .description("Repositório no GitHub")
                        .url("-"));
    }

    @Bean
    public OpenApiCustomizer customerGlocalHeaderOpenApiCustomiser() {
        return openApi -> openApi.getPaths().values().forEach(pathItem ->
                pathItem.readOperations().forEach(operation -> {

                    ApiResponses apiResponses = operation.getResponses();

                    addApiResponseIfAbsent(apiResponses, "200", "Operação realizada com sucesso.");
                    addApiResponseIfAbsent(apiResponses, "201", "Recurso criado com sucesso.");
                    addApiResponseIfAbsent(apiResponses, "204", "Recurso excluído com sucesso.");
                    addApiResponseIfAbsent(apiResponses, "400", "Erro na requisição.");
                    addApiResponseIfAbsent(apiResponses, "401", "Token JWT inválido ou ausente.");
                    addApiResponseIfAbsent(apiResponses, "403", "Você não tem permissão para acessar este recurso.");
                    addApiResponseIfAbsent(apiResponses, "404", "Recurso não encontrado: id=123");
                    addApiResponseIfAbsent(apiResponses, "500", "Erro interno do servidor. Por favor, tente novamente mais tarde.");

                })
        );
    }

    private void addApiResponseIfAbsent(ApiResponses apiResponses, String code, String message) {
        if (!apiResponses.containsKey(code)) {
            apiResponses.addApiResponse(code, new ApiResponse().description(message));
        }
    }
}
