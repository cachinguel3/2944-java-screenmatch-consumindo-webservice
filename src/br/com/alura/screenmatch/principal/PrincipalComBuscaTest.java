package br.com.alura.screenmatch.principal;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class PrincipalComBuscaTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://pokeapi.co/api/v2/pokemon/?limit=20&offset=20"))
                .build();

        HttpResponse <String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        String rawJson = response.body();

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        JsonElement tree = JsonParser.parseString(rawJson);
        String prettyJson = gson.toJson(tree);

        System.out.println(prettyJson);
    }
}
