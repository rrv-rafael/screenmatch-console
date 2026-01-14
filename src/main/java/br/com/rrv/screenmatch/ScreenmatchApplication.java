package br.com.rrv.screenmatch;

import br.com.rrv.screenmatch.principal.Principal;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ScreenmatchApplication.class, args);
    }

    @Override
    public void run(String @NonNull ... args) throws Exception {
        Principal principal = new Principal();

        principal.exibirMenu();
    }
}