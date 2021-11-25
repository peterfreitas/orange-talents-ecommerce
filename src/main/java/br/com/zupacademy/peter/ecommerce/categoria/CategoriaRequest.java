package br.com.zupacademy.peter.ecommerce.categoria;

import br.com.zupacademy.peter.ecommerce.validador.anotacao.UniqueValue;

import javax.validation.constraints.NotBlank;

public class CategoriaRequest {

    @NotBlank
    @UniqueValue(domainClass = Categoria.class, fieldName = "nome")
    private String nome;
    private Long categoriaMaeId;

    public CategoriaRequest(@NotBlank String nome, Long categoriaMaeId) {
        this.nome = nome;
        this.categoriaMaeId = categoriaMaeId;
    }

    public Categoria toModel(CategoriaRepository categoriaRepository) {
        Categoria categoriaMae = null;

        if (this.categoriaMaeId != null) {
            categoriaMae = categoriaRepository.getById(categoriaMaeId);
        }

        return new Categoria(nome, categoriaMae);
    }

    public String getNome() {
        return nome;
    }

    public Long getCategoriaMaeId() {
        return categoriaMaeId;
    }
}