package br.com.rrv.screenmatch.principal;

import br.com.rrv.screenmatch.dtos.EpisodioDto;
import br.com.rrv.screenmatch.dtos.SerieDto;
import br.com.rrv.screenmatch.dtos.TemporadaDto;
import br.com.rrv.screenmatch.entities.Episodio;
import br.com.rrv.screenmatch.services.ConsumoApi;
import br.com.rrv.screenmatch.services.ConverteDados;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Principal {
    private final Scanner scanner = new Scanner(System.in);
    private String urlApi;
    private final String URL_API = "https://omdbapi.com/?t=";
    private final String API_KEY = "&apikey=b8662a59";
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados converteDados = new ConverteDados();

    public void exibirMenu() {
        System.out.print("Digite o nome da série que deseja buscar: ");
        var nomeSerie = URLEncoder.encode(scanner.nextLine(), StandardCharsets.UTF_8);

        urlApi = String.format("%s%s%s", URL_API, nomeSerie, API_KEY);

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

        temporadasDto.forEach(temporadaDto -> {
            System.out.println("\nTemporada: " + temporadaDto.numero());
            temporadaDto.episodiosDto().forEach(episodioDto -> System.out.println(episodioDto.titulo()));
        });

        List<EpisodioDto> episodiosDto = temporadasDto
                .stream()
                .flatMap(t -> t.episodiosDto().stream())
                .toList();

        System.out.println("\nTop 10 episódios:");
        episodiosDto
                .stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("n/a"))
                .peek(e -> System.out.println("Primeiro filtro(N/A) " + e))
                .sorted(Comparator.comparing(EpisodioDto::avaliacao).reversed())
                .peek(e -> System.out.println("Ordenação " + e))
                .limit(10)
                .peek(e -> System.out.println("Limite " + e))
                .map(e -> e.titulo().toUpperCase())
                .peek(e -> System.out.println("Mapeamento " + e))
                .forEach(System.out::println);

        List<Episodio> episodios = temporadasDto
                .stream()
                .flatMap(t -> t.episodiosDto()
                        .stream()
                        .map(e -> new Episodio(t.numero(), e)))
                .toList();

        episodios.forEach(System.out::println);

        System.out.print("Informe o trecho do título desejado: ");
        var trechoTitulo = scanner.nextLine();

        episodios.stream()
                .filter(e -> e.getTitulo().toLowerCase(Locale.ROOT).contains(trechoTitulo.toLowerCase(Locale.ROOT)))
                .findFirst()
                .ifPresentOrElse(e -> System.out.println("Episódio encontrado.\n" + "Temporada: " + e.getTitulo()),
                        () -> System.out.println("Episódio não encontrado!")
                );

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.print("A partir de que ano vc deseja ver os episódios? ");
        var ano = scanner.nextInt();

        scanner.nextLine();

        LocalDate dataDesejada = LocalDate.of(ano, 1, 1);

        episodios.stream()
                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataDesejada))
                .forEach(e -> System.out.printf(
                        "Temporada: %d Episódio: %s Data de lançamento: %s%n",
                        e.getTemporada(), e.getTitulo(), e.getDataLancamento().format(dateTimeFormatter))
                );

        scanner.close();
    }
}