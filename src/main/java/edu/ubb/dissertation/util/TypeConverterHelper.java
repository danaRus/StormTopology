package edu.ubb.dissertation.util;

import io.vavr.control.Try;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.time.ZoneOffset.UTC;
import static org.apache.commons.lang.math.NumberUtils.DOUBLE_ZERO;
import static org.apache.commons.lang.math.NumberUtils.LONG_ZERO;
import static org.apache.commons.lang3.StringUtils.EMPTY;

public class TypeConverterHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(TypeConverterHelper.class);

    private TypeConverterHelper() {
    }

    public static <T, U> Optional<U> cast(final T value, final Class<U> targetType) {
        return targetType.isInstance(value)
                ? Optional.of(targetType.cast(value))
                : Optional.empty();
    }

    public static String convert(final byte[] bytes, final Charset encoding) {
        return Try.of(() -> new String(bytes, encoding))
                .getOrElseGet(e -> new String(bytes, Charset.defaultCharset()));
    }

    public static Object convertToJsonObject(final String message, final Consumer<? super Throwable> failureAction) {
        return Try.of(() -> new JSONObject(message))
                .onFailure(failureAction)
                .getOrElseGet(e -> new JSONObject());
    }

    private static List<String> convert(final JSONArray jsonArray) {
        return IntStream.range(0, jsonArray.length())
                .mapToObj(jsonArray::getString)
                .collect(Collectors.toList());
    }

    public static Optional<LocalDateTime> extractTimestamp(final JSONObject json, final String key) {
        final String timestampAsString = extractString(json, key);
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-ddTHH:mm:ss");
        return Try.of(() -> LocalDateTime.parse(timestampAsString, formatter))
                .onFailure(t -> LOGGER.error("Could not parse timestamp. StackTrace: {}", getStackTraceAsList(t)))
                .map(Optional::ofNullable)
                .getOrElseGet(t -> Optional.empty());
    }

    public static Optional<LocalDateTime> extractTimestampFromEpoch(final JSONObject json, final String key) {
        final Long epochMillis = extractLong(json, key);
        return epochMillis == 0
                ? Optional.empty()
                : Optional.of(LocalDateTime.ofInstant(Instant.ofEpochSecond(epochMillis), ZoneId.from(UTC)));
    }

    public static Double extractDouble(final JSONObject json, final String key) {
        return Try.of(() -> json.getDouble(key))
                .onFailure(t -> LOGGER.error("JSON does not contain double value for key: {}. StackTrace: {}", key, getStackTraceAsList(t)))
                .getOrElseGet(e -> DOUBLE_ZERO);
    }

    public static String extractString(final JSONObject json, final String key) {
        return Try.of(() -> json.getString(key))
                .onFailure(t -> LOGGER.error("JSON does not contain string value for key: {}. StackTrace: {}", key, getStackTraceAsList(t)))
                .getOrElseGet(e -> EMPTY);
    }

    public static List<String> extractList(final JSONObject json, final String key) {
        return Try.of(() -> json.getJSONArray(key))
                .map(TypeConverterHelper::convert)
                .onFailure(t -> LOGGER.error("JSON does not contain a list of values for key: {}. StackTrace: {}", key, getStackTraceAsList(t)))
                .getOrElseGet(e -> new ArrayList<>());
    }

    public static Long extractLong(final JSONObject json, final String key) {
        return Try.of(() -> json.getLong(key))
                .onFailure(t -> LOGGER.error("JSON does not contain long value for key: {}. StackTrace: {}", key, getStackTraceAsList(t)))
                .getOrElseGet(e -> LONG_ZERO);
    }

    private static List<StackTraceElement> getStackTraceAsList(final Throwable throwable) {
        return Arrays.asList(throwable.getStackTrace());
    }
}
