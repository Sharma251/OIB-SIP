package com.reservation.reservation.ReservationServices;

import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservation.reservation.model.Schedule;
import com.reservation.reservation.model.TrainDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.asynchttpclient.*;

@Service
public class RapidApiAsyncService {

    private static final String API_KEY = "5a0bcc2b55msh716347db2daca29p1dacefjsn40e1d5c914d3"; // <-- put your real RapidAPI key here
    private static final String API_HOST = "trains.p.rapidapi.com";
    private static final String TRAIN_SEARCH_URL = "https://trains.p.rapidapi.com/v1/railways/trains/india";

		
	
    // 1. Train Search (GET /train/{trainNumber})
    public String getTrainSearch(String searchQuery) {
        String requestBody = String.format("{\"search\":\"%s\"}", searchQuery);
        try (AsyncHttpClient client = new DefaultAsyncHttpClient()) {
            return client.prepare("POST", TRAIN_SEARCH_URL)
                .setHeader("x-rapidapi-key", API_KEY)
                .setHeader("x-rapidapi-host", API_HOST)
                .setHeader("Content-Type", "application/json")
                .setBody(requestBody).execute()
                .toCompletableFuture()
                .thenApply(r -> r.getResponseBody())
                .join();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 2. Train Status (GET /train/status?trainNumberber=...&departure_date=...)
    public CompletableFuture<String> getTrainStatus(String trainNumber, String departureDate) {
        String url = String.format(
                "https://irctc-api2.p.rapidapi.com/trainSchedule?trainNumber=%s",
                trainNumber);
        AsyncHttpClient client = new DefaultAsyncHttpClient();
        return client.prepare("GET", url)
                .setHeader("x-rapidapi-key", API_KEY)
                .setHeader("x-rapidapi-host", "irctc-api2.p.rapidapi.com")
                .execute()
                .toCompletableFuture()
                .thenApply(resp -> {
                    try {
                        client.close();
                    } catch (Exception ignored) {}
                    return resp.getResponseBody();
                });
    }

    // 3. Parse Train Status JSON as JsonNode
    public JsonNode getParsedTrainStatus(String trainNumber, String departureDate) {
        String json = getTrainStatus(trainNumber, departureDate).join();
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(json);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 4. Get details as TrainDetails object (using Train Search endpoint)
    public TrainDetails getTrainDetails(String trainNumber) {
        String json = getTrainSearch(trainNumber);
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);
            if (root.has("data")) {
                JsonNode data = root.get("data");
                TrainDetails td = new TrainDetails();
                // Fill fields as available, for example:
                td.settrainNumber(trainNumber);
                td.setTrainName(data.has("train_name") ? data.get("train_name").asText() : "");
                // Add more fields as needed ...
                return td;
            } else if (root.has("message")) {
                System.err.println("API error: " + root.get("message").asText());
                return null;
            } else {
                System.err.println("Unexpected API response: " + json);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<TrainDetails> searchAndParseTrains(String searchQuery) {
        List<TrainDetails> trainList = new ArrayList<>();
        String json = getTrainSearch(searchQuery);

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);

            // According to your JSON structure
            JsonNode trainsNode = root.at("/body/0/trains");
            if (root.isArray()) {
                for (JsonNode tNode : root) {
                    TrainDetails train = new TrainDetails();

                    // Always use .path(...).asText("") for safety and correct field names for your API
                    train.settrainNumber(tNode.path("train_num").asText(""));
                    train.setTrainName(tNode.path("name").asText(""));
                    train.setOrigin(tNode.path("train_from").asText(""));
                    train.setDestination(tNode.path("train_to").asText(""));


                    // Parse schedule
                    List<Schedule> scheduleList = new ArrayList<>();
                    JsonNode runs = tNode.path("train_runs");
                    if (runs.isArray()) {
                        for (JsonNode sNode : runs) {
                            Schedule s = new Schedule();
                            s.setStationCode(sNode.path("station_code").asText(""));
                            s.setStationName(sNode.path("station_name").asText(""));
                            s.setArrivalTime(sNode.path("arrival_time").asText(""));
                            s.setDepartureTime(sNode.path("departure_time").asText(""));
                            s.setDistance(sNode.path("distance").asText(""));
                            scheduleList.add(s);
                        }
                    }
                    train.setSchedule(scheduleList);

                    trainList.add(train);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return trainList;
    }

}
