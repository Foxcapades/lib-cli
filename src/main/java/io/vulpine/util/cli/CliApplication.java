package io.vulpine.util.cli;

import io.vulpine.util.cli.def.CliApplicationInterface;
import io.vulpine.util.cli.def.CliArgumentInterface;

abstract class CliApplication implements CliApplicationInterface
{
  protected final CliArgumentInterface< Void > helpFlag;

  protected CliApplication ( final char key, final String name )
  {
    helpFlag = new CliFlag(key, name, "Print help text for this application");
    addArgument(helpFlag);
  }

  protected CliApplication ()
  {
    this('\u0000', "help");
  }
}
