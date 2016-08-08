package io.vulpine.util.cli.def;

import io.vulpine.util.cli.ArgumentParser;
import io.vulpine.util.cli.ArgumentSet;
import io.vulpine.util.cli.CliMode;

public interface CliModeInterface extends DescriptionInterface, NameInterface, HelpInterface
{
  CliModeInterface addArgument ( final CliArgumentInterface a );

  CliModeInterface addParameter ( final CliParameterInterface... parameter );

  void run( final ArgumentParser parser );

  boolean hasUnfilledParam ();

  CliMode parseParam ( String s );

  ArgumentSet getArgumentSet ();
}
