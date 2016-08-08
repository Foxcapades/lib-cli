package io.vulpine.util.cli;

import io.vulpine.util.cli.def.CliApplicationInterface;
import io.vulpine.util.cli.def.CliArgumentInterface;
import io.vulpine.util.cli.def.CliParameterInterface;

import java.util.LinkedList;
import java.util.Queue;

abstract class CliApplication implements CliApplicationInterface
{
  protected final CliArgumentInterface< Void > helpFlag;

  protected final ArgumentSet arguments;

  protected final Queue< CliParameterInterface > parameters;

  protected final ArgumentParser                 parser;

  protected CliApplication ( final char key, final String name, final String[] args )
  {
    arguments = new ArgumentSet();
    parameters = new LinkedList< CliParameterInterface >();
    parser = new ArgumentParser(args, true);
    helpFlag = new CliFlag(key, name, "Print help text for this application");
    addArgument(helpFlag);
  }

  protected CliApplication( final String[] args )
  {
    this('\u0000', "help", args);
  }

  @Override
  public CliApplication addArgument ( final CliArgumentInterface... a )
  {
    for ( final CliArgumentInterface arg : a ) { arguments.addArgument(arg); }

    return this;
  }
}
