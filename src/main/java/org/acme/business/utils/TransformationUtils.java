package org.acme.business.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.acme.business.models.SimpleModel;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.io.IOUtils.toByteArray;

@Slf4j
public class TransformationUtils {

    public static String getBodyFromRequest(InputStream inputStream) throws Exception {
        try {
            return toString(inputStream);
        } catch (IOException ioe) {
            log.warn(ioe.getMessage(), ioe);
            throw new Exception("BAD_INPUT");
        }
    }

    public static String toString(InputStream in) throws IOException {
        return new String(toByteArray(in), UTF_8);
    }

    public static JsonNode stringToNode(ObjectMapper mapper, String string) throws Exception {
        try {
            return mapper.readTree(string);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new Exception("BAD_JSON");
        }
    }

    public static <T extends SimpleModel<T>> T nodeToObject(
            ObjectMapper mapper, TreeNode node, Class<T> clazz) throws Exception {
        try {
            return mapper.treeToValue(node, clazz);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw new Exception("BAD_JSON");
        }
    }

}
