package br.com.rrv.screenmatch.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SerieDto(@JsonAlias("Title") String titulo,
                       @JsonAlias("totalSeasons") Integer totalTemporadas,
                       @JsonAlias("imdbRating") String avaliacao) {
}