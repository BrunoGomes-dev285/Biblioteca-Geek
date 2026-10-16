package com.example.bibliotecageek.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min =3, max = 100, message ="O titulo da obra deve conter de 3 a 100 caracteres.")
    @NotBlank(message = "O título é obrigatório")
    private String titulo;

    @NotBlank(message = "A categoria é obrigatória")
    private String categoria;

    @NotNull(message = "O ano é obrigatório")
    @Positive(message = "O ano não pode ser negativo")
    @Min(value = 1900, message = "O ano minimo é 1900")
    private Integer ano;

    @NotNull(message = "A quantidade é obrigatória")
    @PositiveOrZero(message = "A quantidade não pode ser negativa")
    private BigInteger quantidade;

    //Metodo para pegar o ano atual e valida-lo
    @AssertTrue(message = "O ano não pode ser maior que o ano atual")
    public boolean isAnoValido() {
        return ano == null || ano <= java.time.Year.now().getValue();
    }
}