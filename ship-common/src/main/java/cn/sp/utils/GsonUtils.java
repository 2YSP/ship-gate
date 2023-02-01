package cn.sp.utils;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author: Ship
 * @Description: gson工具类
 * @Date: Created in 2021/6/4
 */
public class GsonUtils {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 线程安全的gson实例
     */
    private static final Gson GSON = new GsonBuilder()
            .setDateFormat(DATE_FORMAT)
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
            .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
            .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
            .create();

    /**
     * 处理下换线转驼峰的gson实例
     */
    private static final Gson GSON_WITH_UNDERSCORE = new GsonBuilder()
            .setDateFormat(DATE_FORMAT)
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
            .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
            .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
            .create();


    private GsonUtils() {
    }

    /**
     * 对象转json字符串
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> String toJson(T t) {
        return GSON.toJson(t);
    }

    /**
     * json字符串转对象
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        return GSON.fromJson(json, clazz);
    }

    /**
     * json字符串转对象
     *
     * @param json
     * @param typeOfT
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json, Type typeOfT) {
        return GSON.fromJson(json, typeOfT);
    }

    /**
     * 对象转json字符串,支持字段驼峰转下划线
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> String toJsonWithUnderscore(T t) {
        return GSON_WITH_UNDERSCORE.toJson(t);
    }

    /**
     * json字符串转对象,支持字段下划线转驼峰
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T fromJsonWithUnderscore(String json, Class<T> clazz) {
        return GSON_WITH_UNDERSCORE.fromJson(json, clazz);
    }

    /**
     * json字符串转对象,支持字段下划线转驼峰
     *
     * @param json
     * @param typeOfT
     * @return
     */
    public static <T> T fromJsonWithUnderscore(String json, Type typeOfT) {
        return GSON_WITH_UNDERSCORE.fromJson(json, typeOfT);
    }


    private static class LocalDateDeserializer implements JsonDeserializer<LocalDate> {

        @Override
        public LocalDate deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            String value = jsonElement.getAsJsonPrimitive().getAsString();
            if (value == null || "".equals(value)) {
                return null;
            }
            return LocalDate.parse(value, DATE_FORMATTER);
        }
    }

    private static class LocalDateSerializer implements JsonSerializer<LocalDate> {

        @Override
        public JsonElement serialize(LocalDate localDate, Type type, JsonSerializationContext jsonSerializationContext) {
            String value = "";
            if (localDate != null) {
                value = DATE_FORMATTER.format(localDate);
            }
            return new JsonPrimitive(value);
        }
    }

    private static class LocalDateTimeDeserializer implements JsonDeserializer<LocalDateTime> {

        @Override
        public LocalDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            final String value = jsonElement.getAsJsonPrimitive().getAsString();
            if (value == null || "".equals(value)) {
                return null;
            }
            return LocalDateTime.parse(value, DATE_TIME_FORMATTER);
        }
    }

    private static class LocalDateTimeSerializer implements JsonSerializer<LocalDateTime> {

        @Override
        public JsonElement serialize(LocalDateTime localDateTime, Type type, JsonSerializationContext jsonSerializationContext) {
            String value = "";
            if (localDateTime != null) {
                value = DATE_TIME_FORMATTER.format(localDateTime);
            }
            return new JsonPrimitive(value);
        }
    }


}
