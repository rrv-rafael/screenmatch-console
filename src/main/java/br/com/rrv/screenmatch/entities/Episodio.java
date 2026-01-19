package br.com.rrv.screenmatch.entities;

import br.com.rrv.screenmatch.dtos.EpisodioDto;

import java.time.LocalDate;

public class Episodio {
    private Integer temporada;
    private String titulo;
    private Integer numero;
    private Double avaliacao;
    private LocalDate dataLancamento;

    public Episodio(Integer numeroTemporada, EpisodioDto episodioDto) {
        this.temporada = numeroTemporada;
        this.titulo = episodioDto.titulo();
        this.numero = episodioDto.numero();
        this.avaliacao = Double.valueOf(episodioDto.avaliacao());
    }

    public Episodio(Integer temporada, String titulo, Integer numero, Double avaliacao, LocalDate dataLancamento) {
        this.temporada = temporada;
        this.titulo = titulo;
        this.numero = numero;
        this.avaliacao = avaliacao;
        this.dataLancamento = dataLancamento;
    }

    public Integer getTemporada() {
        return temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public Integer getNumero() {
        return numero;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }
}
