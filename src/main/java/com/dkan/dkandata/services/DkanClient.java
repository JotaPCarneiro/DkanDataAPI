package com.dkan.dkandata.services;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

@Service
public class DkanClient {

    private static final String API_URL = ""; // Lembrar de colocar a URL do DKAN aqui!!!

    public JsonNode getDataset(String datasetId) throws IOException {
        // Cria o cliente HTTP
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // Constrói a URL da requisição
        String url = API_URL + datasetId;

        // Cria o objeto de requisição GET
        HttpGet request = new HttpGet(url);

        try {
            // Executa a requisição
            HttpResponse response = httpClient.execute(request);

            // Verifica o código de status da resposta
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                throw new RuntimeException("Falha Código do erro: " + statusCode);
            }

            // Obtém a entidade da resposta
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                // Converte a entidade da resposta em String
                String result = EntityUtils.toString(entity);

                // Converte a String JSON para um objeto JsonNode
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readTree(result);
            }

        } catch (ClientProtocolException e) {
            throw new RuntimeException("HTTP protocol error: " + e.getMessage(), e);
        } catch (SocketTimeoutException e) {
            throw new RuntimeException("Request timed out: " + e.getMessage(), e);
        } catch (UnknownHostException e) {
            throw new RuntimeException("Unknown host: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException("Network error: " + e.getMessage(), e);
        } finally {
            // Fechar o cliente HTTP
            httpClient.close();
        }

        return null;
    }
}
