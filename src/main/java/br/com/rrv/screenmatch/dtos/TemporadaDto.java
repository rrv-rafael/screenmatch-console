package br.com.rrv.screenmatch.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TemporadaDto(@JsonAlias("Season") Integer numero,
                           @JsonAlias("Episodes") List<EpisodioDto> episodiosDto) {
}
