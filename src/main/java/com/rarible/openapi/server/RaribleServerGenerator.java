package com.rarible.openapi.server;

import org.openapitools.codegen.languages.KotlinSpringServerCodegen;

import java.util.stream.Collectors;

public class RaribleServerGenerator extends KotlinSpringServerCodegen {

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
        typeMapping.put("java.time.OffsetDateTime", "java.time.Instant");
        importMapping.put("Address", "scalether.domain.Address");
        importMapping.put("Word", "io.daonomic.rpc.domain.Word");
        importMapping.put("Binary", "io.daonomic.rpc.domain.Binary");
        importMapping.put("BigInteger", "java.math.BigInteger");
        importMapping.put("BigDecimal", "java.math.BigDecimal");
    }
}
