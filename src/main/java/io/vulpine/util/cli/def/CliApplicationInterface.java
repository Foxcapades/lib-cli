package io.vulpine.util.cli.def;

import io.vulpine.util.cli.MultiApplication;

public interface CliApplicationInterface extends Runnable, HelpInterface
{
  MultiApplication addArgument ( CliArgumentInterface... a );

  MultiApplication addParameter ( CliParameterInterface... def );
}
