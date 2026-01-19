package br.com.rrv.screenmatch.entities;

import br.com.rrv.screenmatch.dtos.EpisodioDto;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episodio {
    private final Integer temporada;
    private final String titulo;
    private final Integer numero;
    private final Double avaliacao;
    private final LocalDate dataLancamento;

    public Episodio(Integer numeroTemporada, EpisodioDto episodioDto) {
        this.temporada = numeroTemporada;
        this.titulo = episodioDto.titulo();
        this.numero = episodioDto.numero();
        this.avaliacao = parseAvaliacao(episodioDto.avaliacao());
        this.dataLancamento = parseDataLancamento(episodioDto.dataLancamento());
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

    private Double parseAvaliacao(String avaliacao) {
        try {
            return Double.valueOf(avaliacao);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private LocalDate parseDataLancamento(String dataLancamento) {
        try {
            return LocalDate.parse(dataLancamento);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return String.format(
                "Temporada: %d Título: %s Número do episódio: %d Avaliação: %.2f Data de lançamento: %s",
                temporada, titulo, numero, avaliacao, dataLancamento);
    }
}
