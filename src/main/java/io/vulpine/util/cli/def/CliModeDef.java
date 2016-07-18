package io.vulpine.util.cli.def;

import io.vulpine.util.cli.ArgumentParser;
import io.vulpine.util.cli.ArgumentSet;
import io.vulpine.util.cli.CliMode;

public interface CliModeDef extends DescribableDef, NamableDef
{
  CliModeDef addArgument ( final CliArgumentDef a );

  CliModeDef addParameter ( final String parameter );

  void run( final ArgumentParser parser );

  boolean hasUnfilledParam ();

  CliMode parseParam ( String s );

  ArgumentSet getArgumentSet ();
}
