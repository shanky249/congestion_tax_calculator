package com.volvocars.assignment.model.requests.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.volvocars.assignment.model.Bus;
import com.volvocars.assignment.model.Diplomat;
import com.volvocars.assignment.model.Emergency;
import com.volvocars.assignment.model.Foreign;
import com.volvocars.assignment.model.General;
import com.volvocars.assignment.model.Military;
import com.volvocars.assignment.model.Motorcycle;
import com.volvocars.assignment.model.interfaces.Vehicle;
import com.volvocars.assignment.model.requests.CalculationRequest;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.stream.StreamSupport;

public class CalculationRequestDeserializer extends StdDeserializer<CalculationRequest> {

    public CalculationRequestDeserializer() {
        super(CalculationRequest.class);
    }

    @Override
    public CalculationRequest deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        CalculationRequest request = new CalculationRequest();
        request.setVehicle(parseVehicle(node.get("vehicle")));
        request.setDates(parseDates(node.get("dates")));

        return request;
    }

    private Vehicle parseVehicle(JsonNode vehicleNode) {
        String vehicleType = vehicleNode.asText().toLowerCase();

        switch (vehicleType) {
            case "emergency":
                return new Emergency();
            case "bus":
                return new Bus();
            case "diplomat":
                return new Diplomat();
            case "motorcycle":
                return new Motorcycle();
            case "military":
                return new Military();
            case "foreign":
                return new Foreign();
            default:
                return new General();
        }
    }

    private Instant[] parseDates(JsonNode datesNode) {
        return StreamSupport.stream(datesNode.spliterator(), false)
                .map(JsonNode::asText)
                .map(this::parseInstant)
                .toArray(Instant[]::new);
    }

    private Instant parseInstant(String dateString) {
        return LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .atZone(ZoneId.systemDefault())
                .toInstant();
    }

}
