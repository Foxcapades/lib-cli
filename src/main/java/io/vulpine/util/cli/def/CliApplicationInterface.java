package io.vulpine.util.cli.def;

public interface CliApplicationInterface extends Runnable, HelpInterface
{
  CliApplicationInterface addArgument ( CliArgumentInterface... a );

  CliApplicationInterface addParameter ( CliParameterInterface... def );
}
