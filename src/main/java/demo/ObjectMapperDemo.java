package demo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by huishen on 16/12/14.
 *
 */
public class ObjectMapperDemo {

    @Before
    public void before() {

    }

    @Test
    public void test() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        //把enum转成String
        // objectMapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        objectMapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);

        //把enum转成index  -- int
        // objectMapper.enable(SerializationFeature.WRITE_ENUMS_USING_INDEX);
        String str = objectMapper.writeValueAsString(User.Age.ADULT);
        System.out.println(str);
    }

    private ObjectMapper getObjectMapper() {
        Jackson2ObjectMapperBuilder builder = Jackson2ObjectMapperBuilder.json();
        builder.timeZone("GMT+8");
        builder.simpleDateFormat("yyyy-MM-dd HH:mm:ss");
        builder.featuresToEnable(new Object[]{SerializationFeature.WRITE_ENUMS_USING_INDEX});
        builder.serializationInclusion(JsonInclude.Include.NON_NULL);
        builder.propertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        builder.serializerByType(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
            @Override
            public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeString(value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
        });
        builder.deserializerByType(LocalDateTime.class, new JsonDeserializer() {
            public LocalDateTime deserialize(JsonParser p, DeserializationContext ctx) throws IOException, JsonProcessingException {
                return LocalDateTime.parse(p.getValueAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            }
        });

        ObjectMapper objectMapper = builder.build();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        //!!!!!!
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        return objectMapper;
    }

    // jackson处理复杂类型的两种方式
    @Test
    public void test1() {
        String jsonString = "['shen','hui']";
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, String.class);
        List<String> list = null;
        try {
            list = (List<String>) objectMapper.readValue(jsonString, javaType);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
