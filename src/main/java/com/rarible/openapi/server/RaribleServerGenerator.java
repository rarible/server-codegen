package com.rarible.openapi.server;

import org.openapitools.codegen.CodegenConfig;
import org.openapitools.codegen.languages.KotlinSpringServerCodegen;

import java.util.stream.Collectors;

public class RaribleServerGenerator extends KotlinSpringServerCodegen implements CodegenConfig {

  /**
   * Configures a friendly name for the generator.  This will be used by the generator
   * to select the library with the -g flag.
   * 
   * @return the friendly name for the generator
   */
  public String getName() {
    return "rarible-server";
  }

  public RaribleServerGenerator() {
    super();
    embeddedTemplateDir = templateDir = "rarible-server";
    setUseTags(true);
    setInterfaceOnly(true);
    setModelPackage("com.rarible.protocol.dto");
    setReactive(true);
    setExceptionHandler(false);
  }

  @Override
  public void processOpts() {
    super.processOpts();
    supportingFiles = supportingFiles.stream().filter(it -> !it.getTemplateFile().toLowerCase().contains("gradle")).collect(Collectors.toList());
  }
}