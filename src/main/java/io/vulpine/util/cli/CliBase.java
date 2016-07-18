package io.vulpine.util.cli;

import io.vulpine.util.cli.def.DescribableDef;
import io.vulpine.util.cli.def.NamableDef;

public class CliBase implements NamableDef, DescribableDef
{
  protected final String name, description;

  public CliBase ( String name, String description )
  {
    this.name = name;
    this.description = description;
  }

  public String getDescription ()
  {
    return description;
  }

  public String getName ()
  {
    return name;
  }
}
