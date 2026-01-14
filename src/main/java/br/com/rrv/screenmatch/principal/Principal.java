package br.com.rrv.screenmatch.principal;

import br.com.rrv.screenmatch.dtos.SerieDto;
import br.com.rrv.screenmatch.services.ConsumoApi;
import br.com.rrv.screenmatch.services.ConverteDados;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Principal {
    private Scanner scanner = new Scanner(System.in);
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

        System.out.println(serieDto);

        scanner.close();
    }
}
