package utils;

public class Serializator {
    public String serializetoJson(CollectionHandler collectionHandler){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        try {
            return objectMapper.writeValueAsString(collectionHandler.getCollection());
        }catch (JsonProcessingException e){
            e.printStackTrace();
            return null;
        }
    }
}
