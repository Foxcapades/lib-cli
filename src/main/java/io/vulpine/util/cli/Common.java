package io.vulpine.util.cli;

import io.vulpine.util.cli.def.DescriptionInterface;
import io.vulpine.util.cli.def.NameInterface;

class Common implements NameInterface, DescriptionInterface
{
  protected final String name, description;

  Common ( String name, String description )
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
