package io.vulpine.util.cli;

import io.vulpine.util.cli.def.CliApplicationDef;
import io.vulpine.util.cli.def.CliArgumentDef;

abstract class CliApplication implements CliApplicationDef
{
  protected final CliArgumentDef < Void > helpFlag;

  protected CliApplication( final char key, final String name )
  {
    helpFlag = new CliFlag(key, name, "Print help text for this application", false);
    addArgument(helpFlag);
  }

  protected CliApplication()
  {
    this('?', "help");
  }
}
