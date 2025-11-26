package human.coejoder.mt4client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link MT4Exception}.
 * These tests use ObjectMapper only and do not require a live MT4 connection.
 */
public class TestExceptions {

    private static final Logger LOG = LoggerFactory.getLogger(TestExceptions.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String TEST_ERROR_CODE_DESCRIPTION = "This is a test error code description.";
    private static final String TEST_ERROR_MESSAGE = "This is a test error message.";

    @Test
    public void testAllPropertiesPresent() throws JsonProcessingException {
        MT4Exception.Code testErrorCode = MT4Exception.Code.ERR_COMMON_ERROR;
        String json = "{\n" +
                "\t\"error_code\": " + testErrorCode.id + ",\n" +
                "\t\"error_code_description\": \"" + TEST_ERROR_CODE_DESCRIPTION + "\",\n" +
                "\t\"error_message\": \"" + TEST_ERROR_MESSAGE + "\"\n" +
                "}";
        MT4Exception exc = buildExceptionFrom(json);
        LOG.trace(String.format("%s%n = %s", json, exc));

        assertEquals(exc.errorCode, testErrorCode);
        assertEquals(exc.errorCodeDescription, TEST_ERROR_CODE_DESCRIPTION);
        assertEquals(exc.message, TEST_ERROR_MESSAGE);
    }

    @Test
    public void testErrorCodeMissing() throws JsonProcessingException {
        MT4Exception.Code testErrorCode = MT4Exception.Code.UNKNOWN;
        String json = "{\n" +
                "\t\"error_code_description\": \"" + TEST_ERROR_CODE_DESCRIPTION + "\",\n" +
                "\t\"error_message\": \"" + TEST_ERROR_MESSAGE + "\"\n" +
                "}";
        MT4Exception exc = buildExceptionFrom(json);
        LOG.trace(String.format("%s%n = %s", json, exc));

        assertEquals(exc.errorCode, testErrorCode);
        assertEquals(exc.errorCodeDescription, TEST_ERROR_CODE_DESCRIPTION);
        assertEquals(exc.message, TEST_ERROR_MESSAGE);
    }

    @Test
    public void testErrorCodeDescriptionMissing() throws JsonProcessingException {
        MT4Exception.Code testErrorCode = MT4Exception.Code.ERR_SERVER_BUSY;
        String json = "{\n" +
                "\t\"error_code\": " + testErrorCode.id + ",\n" +
                "\t\"error_message\": \"" + TEST_ERROR_MESSAGE + "\"\n" +
                "}";
        MT4Exception exc = buildExceptionFrom(json);
        LOG.trace(String.format("%s%n = %s", json, exc));

        assertEquals(exc.errorCode, testErrorCode);
        assertNull(exc.errorCodeDescription);
        assertEquals(exc.message, TEST_ERROR_MESSAGE);
    }

    @Test
    public void testErrorMessageMissing() throws JsonProcessingException {
        MT4Exception.Code testErrorCode = MT4Exception.Code.ERR_ACCOUNT_DISABLED;
        String json = "{\n" +
                "\t\"error_code\": " + testErrorCode.id + ",\n" +
                "\t\"error_code_description\": \"" + TEST_ERROR_CODE_DESCRIPTION + "\"\n" +
                "}";
        MT4Exception exc = buildExceptionFrom(json);
        LOG.trace(String.format("%s%n = %s", json, exc));

        assertEquals(exc.errorCode, testErrorCode);
        assertEquals(exc.errorCodeDescription, TEST_ERROR_CODE_DESCRIPTION);
        assertNull(exc.message);
    }

    @Test
    public void testExtraPropertiesPresent() throws JsonProcessingException {
        MT4Exception.Code testErrorCode = MT4Exception.Code.ERR_COMMON_ERROR;
        String json = "{\n" +
                "\t\"error_code\": " + testErrorCode.id + ",\n" +
                "\t\"error_code_description\": \"" + TEST_ERROR_CODE_DESCRIPTION + "\",\n" +
                "\t\"error_message\": \"" + TEST_ERROR_MESSAGE + "\",\n" +
                "\t\"ignore_me\": \"This should be ignored.\"\n" +
                "}";
        MT4Exception exc = buildExceptionFrom(json);
        LOG.trace(String.format("%s%n = %s", json, exc));

        assertEquals(exc.errorCode, testErrorCode);
        assertEquals(exc.errorCodeDescription, TEST_ERROR_CODE_DESCRIPTION);
        assertEquals(exc.message, TEST_ERROR_MESSAGE);
    }

    @Test
    public void testInvalidErrorCode() throws JsonProcessingException {
        int invalidErrorCode = 10;
        String json = "{\n" +
                "\t\"error_code\": " + invalidErrorCode + ",\n" +
                "\t\"error_code_description\": \"" + TEST_ERROR_CODE_DESCRIPTION + "\",\n" +
                "\t\"error_message\": \"" + TEST_ERROR_MESSAGE + "\"\n" +
                "}";
        MT4Exception exc = buildExceptionFrom(json);
        LOG.trace(String.format("%s%n = %s", json, exc));

        assertEquals(exc.errorCode, MT4Exception.Code.UNKNOWN);
        assertEquals(exc.errorCodeDescription, TEST_ERROR_CODE_DESCRIPTION);
        assertEquals(exc.message, TEST_ERROR_MESSAGE);
    }

    private MT4Exception buildExceptionFrom(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, MT4Exception.class);
    }
}
