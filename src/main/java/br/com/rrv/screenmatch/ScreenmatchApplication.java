package br.com.rrv.screenmatch;

import br.com.rrv.screenmatch.dtos.EpisodioDto;
import br.com.rrv.screenmatch.dtos.SerieDto;
import br.com.rrv.screenmatch.dtos.TemporadaDto;
import br.com.rrv.screenmatch.services.ConsumoApi;
import br.com.rrv.screenmatch.services.ConverteDados;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ScreenmatchApplication.class, args);
    }

    @Override
    public void run(String @NonNull ... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        var consumoApi = new ConsumoApi();

        var apiKeyOmdb = "b8662a59";

        var urlApi = "https://omdbapi.com/?t=gilmore+girls&apikey=" + apiKeyOmdb;
        var jsonResponse = consumoApi.obterDados(urlApi);

        ConverteDados converteDados = new ConverteDados();

        SerieDto serieDto = converteDados.obterDados(jsonResponse, SerieDto.class);

        System.out.println("Json response da série: " + jsonResponse);
        System.out.println("DTO da série: " + serieDto);

        urlApi = "https://omdbapi.com/?t=gilmore+girls&season=1&episode=2&apikey=" + apiKeyOmdb;

        jsonResponse = consumoApi.obterDados(urlApi);

        EpisodioDto episodioDto = converteDados.obterDados(jsonResponse, EpisodioDto.class);

        System.out.println("\nJson response do episódio: " + jsonResponse);
        System.out.println("DTO do episódio: " + episodioDto);

        List<TemporadaDto> temporadasDto = new ArrayList<>();

        for (int i = 1; i <= serieDto.totalTemporadas(); i++) {
            jsonResponse = consumoApi.obterDados(String.format("https://omdbapi.com/?t=gilmore+girls&season=%d&apikey=%s", i, apiKeyOmdb));

            TemporadaDto temporadaDto = converteDados.obterDados(jsonResponse, TemporadaDto.class);

            temporadasDto.add(temporadaDto);
        }

        System.out.println("\nLista de temporadas:");
        temporadasDto.forEach(System.out::println);

        scanner.close();
    }
}