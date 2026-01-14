package br.com.rrv.screenmatch.principal;

import br.com.rrv.screenmatch.dtos.SerieDto;
import br.com.rrv.screenmatch.dtos.TemporadaDto;
import br.com.rrv.screenmatch.services.ConsumoApi;
import br.com.rrv.screenmatch.services.ConverteDados;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private final Scanner scanner = new Scanner(System.in);
    private String urlApi;
    private final String URL_API = "https://omdbapi.com/?t=";
    private final String API_KEY = "&apikey=b8662a59";
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados converteDados = new ConverteDados();

    public void exibirMenu() {
        System.out.print("Digite o nome da série que deseja buscar: ");
        var nomeSerie = scanner.nextLine();

        urlApi = String.format("%s%s%s", URL_API, URLEncoder.encode(nomeSerie, StandardCharsets.UTF_8), API_KEY);

        consumoApi = new ConsumoApi();

        var jsonResponse = consumoApi.obterDados(urlApi);

        SerieDto serieDto = converteDados.obterDados(jsonResponse, SerieDto.class);

        System.out.println("Json response da série: " + jsonResponse);
        System.out.println("DTO da série: " + serieDto);

        List<TemporadaDto> temporadasDto = new ArrayList<>();

        for (int i = 1; i <= serieDto.totalTemporadas(); i++) {
            jsonResponse = consumoApi.obterDados(String.format("https://omdbapi.com/?t=%s&season=%d%s", nomeSerie, i, API_KEY));

            TemporadaDto temporadaDto = converteDados.obterDados(jsonResponse, TemporadaDto.class);

            temporadasDto.add(temporadaDto);
        }

        System.out.println("\nLista de temporadas:");
        temporadasDto.forEach(System.out::println);

        scanner.close();
    }
}
