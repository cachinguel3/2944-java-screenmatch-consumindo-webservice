package br.com.alura.screenmatch.principal;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class PrincipalComBusca {
    public static String prettyPrintJson(String json) {
        if (json == null || json.isBlank()) return json;

        StringBuilder out = new StringBuilder();
        int indent = 0;
        boolean inString = false;
        boolean escape = false;

        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);

            if (inString) {
                out.append(c);
                if (escape) {
                    escape = false;
                } else if (c == '\\') {
                    escape = true;
                } else if (c == '"') {
                    inString = false;
                }
                continue;
            }

            switch (c) {
                case '"':
                    inString = true;
                    out.append(c);
                    break;

                case '{':
                case '[':
                    out.append(c).append('\n');
                    indent++;
                    appendIndent(out, indent);
                    break;

                case '}':
                case ']':
                    out.append('\n');
                    indent = Math.max(0, indent - 1);
                    appendIndent(out, indent);
                    out.append(c);
                    break;

                case ',':
                    out.append(c).append('\n');
                    appendIndent(out, indent);
                    break;

                case ':':
                    out.append(c).append(' ');
                    break;

                default:
                    if (!Character.isWhitespace(c)) out.append(c);
                    break;
            }
        }
        return out.toString();
    }

    private static void appendIndent(StringBuilder sb, int indent) {
        sb.append("  ".repeat(Math.max(0, indent))); // 2 spaces per level
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://pokeapi.co/api/v2/pokemon/?limit=20&offset=20"))
                .build();

        HttpResponse <String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());


        System.out.println(prettyPrintJson(response.body()));
    }
}
