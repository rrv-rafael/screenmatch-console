package br.com.rrv.screenmatch.services;

import tools.jackson.databind.ObjectMapper;

public class ConverteDados implements IConverteDados {
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T obterDados(String json, Class<T> classe) {
        return objectMapper.readValue(json, classe);
    }
}