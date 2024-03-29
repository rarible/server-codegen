package com.rarible.openapi.server;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.tags.Tag;
import org.openapitools.codegen.languages.KotlinSpringServerCodegen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RaribleServerGenerator extends KotlinSpringServerCodegen {

    private static final String X_CONTROLLER = "x-controller";

    /**
     * Configures a friendly name for the generator.  This will be used by the generator
     * to select the library with the -g flag.
     *
     * @return the friendly name for the generator
     */
    @Override
    public String getName() {
        return "rarible-server";
    }

    private final Map<String, String> tagMapping = new HashMap<>();

    @Override
    public void preprocessOpenAPI(OpenAPI openAPI) {
        super.preprocessOpenAPI(openAPI);
        List<Tag> tags = openAPI.getTags();
        if (tags == null) {
            return;
        }
        for (Tag tag: tags) {
            Map<String, Object> extensions = tag.getExtensions();
            if (extensions != null && extensions.containsKey(X_CONTROLLER)) {
                tagMapping.put(tag.getName(), extensions.get(X_CONTROLLER).toString());
            }
        }
    }

    @Override
    public String sanitizeTag(String tag) {
        String controller = tagMapping.get(tag);
        if (controller != null) {
            return controller;
        }
        return super.sanitizeTag(tag);
    }

    public RaribleServerGenerator() {
        embeddedTemplateDir = templateDir = "rarible-server";
        setUseTags(true);
        setInterfaceOnly(true);
        setModelNameSuffix("Dto");
        setModelPackage("com.rarible.protocol.dto");
        setReactive(true);
        setExceptionHandler(false);
        setUseBeanValidation(false);
    }

    @Override
    public void processOpts() {
        super.processOpts();
        supportingFiles = supportingFiles.stream().filter(it -> !it.getTemplateFile().toLowerCase().contains("gradle")).collect(Collectors.toList());
        typeMapping.put("OffsetDateTime", "Instant");
        typeMapping.put("java.time.OffsetDateTime", "java.time.Instant");
        importMapping.put("OffsetDateTime", "java.time.Instant");
        importMapping.put("Address", "scalether.domain.Address");
        importMapping.put("Word", "io.daonomic.rpc.domain.Word");
        importMapping.put("Binary", "io.daonomic.rpc.domain.Binary");
        importMapping.put("BigInteger", "java.math.BigInteger");
        importMapping.put("BigDecimal", "java.math.BigDecimal");
    }
}
