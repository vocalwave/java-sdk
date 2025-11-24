package io.qrng;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * QRNG API Client for Java
 */
public class QRNGClient {
    private final String apiKey;
    private final String baseUrl;
    private final HttpClient httpClient;
    private final Gson gson;

    public QRNGClient(String apiKey) {
        this(apiKey, "https://qrngapi.com");
    }

    public QRNGClient(String apiKey, String baseUrl) {
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(30))
                .build();
        this.gson = new Gson();
    }

    /**
     * Generate random entropy
     */
    public EntropyResult generate(GenerateOptions options) throws QRNGException {
        if (options == null) {
            options = new GenerateOptions();
        }

        StringBuilder urlBuilder = new StringBuilder(baseUrl + "/api/random?");
        urlBuilder.append("bytes=").append(options.getBytes());
        urlBuilder.append("&format=").append(options.getFormat());

        if (options.getMethod() != null) {
            urlBuilder.append("&method=").append(options.getMethod());
        }
        if (options.getSignatureType() != null) {
            urlBuilder.append("&signatureType=").append(options.getSignatureType());
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlBuilder.toString()))
                .header("X-API-Key", apiKey)
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 401) {
                throw new AuthenticationException("Invalid API key");
            } else if (response.statusCode() == 429) {
                throw new RateLimitException("Rate limit exceeded");
            } else if (response.statusCode() == 402) {
                throw new QuotaExceededException("Monthly quota exceeded");
            } else if (response.statusCode() != 200) {
                JsonObject error = gson.fromJson(response.body(), JsonObject.class);
                String message = error.has("error") ? error.get("error").getAsString()
                        : "HTTP " + response.statusCode();
                throw new QRNGException(message);
            }

            return gson.fromJson(response.body(), EntropyResult.class);

        } catch (IOException | InterruptedException e) {
            throw new QRNGException("Request failed: " + e.getMessage(), e);
        }
    }

    /**
     * Get system health status
     */
    public HealthStatus health() throws QRNGException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/api/health"))
                .header("X-API-Key", apiKey)
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new QRNGException("Health check failed: HTTP " + response.statusCode());
            }

            return gson.fromJson(response.body(), HealthStatus.class);

        } catch (IOException | InterruptedException e) {
            throw new QRNGException("Health check failed: " + e.getMessage(), e);
        }
    }
}
