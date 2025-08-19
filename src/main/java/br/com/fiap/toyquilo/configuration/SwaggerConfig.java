package br.com.fiap.toyquilo.configuration;

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
                        .title("API - Sistema de Gerenciamento de Brinquedos Esportivos")
                        .description("""
                                FIAP – Faculdade de Informática e Administração Paulista
                                Curso: Tecnologia em Análise e Desenvolvimento de Sistemas (TDS)
                                Professor: Dr. Marcel Stefan Wagner
                                Exercício de Revisão: Spring com Persistência, Lombok, HATEOAS e Deploy
                                
                                **Autores:**
                                - Daniel Saburo Akiyama (RM 558263)
                                - Danilo Correia e Silva (RM 557540)
                                - João Pedro Rodrigues da Costa (RM 558199)
                                """)
                        .version("1.0.0"))
                .externalDocs(new ExternalDocumentation()
                        .description("Repositório no GitHub")
                        .url("https://github.com/danielthx23/exercicio-revisao-java-fiap"));
    }

    @Bean
    public OpenApiCustomizer globalResponseMessages() {
        return openApi -> openApi.getPaths().values().forEach(pathItem ->
                pathItem.readOperations().forEach(operation -> {
                    ApiResponses apiResponses = operation.getResponses();
                    addApiResponse(apiResponses, "200", "Operação realizada com sucesso.");
                    addApiResponse(apiResponses, "201", "Recurso criado com sucesso.");
                    addApiResponse(apiResponses, "204", "Recurso excluído com sucesso.");
                    addApiResponse(apiResponses, "400", "Requisição inválida.");
                    addApiResponse(apiResponses, "404", "Recurso não encontrado.");
                    addApiResponse(apiResponses, "500", "Erro interno do servidor.");
                })
        );
    }

    private void addApiResponse(ApiResponses apiResponses, String code, String message) {
        if (!apiResponses.containsKey(code)) {
            apiResponses.addApiResponse(code, new ApiResponse().description(message));
        }
    }
}
