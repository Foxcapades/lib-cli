package io.vulpine.util.cli.def;

import io.vulpine.util.cli.CliMultiApplication;

public interface CliApplicationDef extends Runnable
{
  CliMultiApplication addArgument ( CliArgumentDef a );

  CliMultiApplication addParameter ( CliParameterDef def );
}
