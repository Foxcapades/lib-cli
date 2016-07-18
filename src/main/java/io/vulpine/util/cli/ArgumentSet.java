package io.vulpine.util.cli;

import io.vulpine.util.cli.def.CliArgumentDef;

import java.util.HashMap;
import java.util.Map;

public class ArgumentSet
{
  protected final Map < String, CliArgumentDef >   byName;
  protected final Map < Character, CliArgumentDef > byKey;

  public ArgumentSet ()
  {
    byKey  = new HashMap < Character, CliArgumentDef >();
    byName = new HashMap < String, CliArgumentDef >();
  }

  public CliArgumentDef getArgument( final String name )
  {
    return byName.get(name);
  }

  public CliArgumentDef getArgument ( final char key )
  {
    return byKey.get(key);
  }

  public void addArgument( final CliArgumentDef arg )
  {
    this.byKey.put(arg.getKey(), arg);
    this.byName.put(arg.getName(), arg);
  }

  public boolean hasArgument( final String name )
  {
    return byName.containsKey(name);
  }

  public boolean hasArgument ( final char key )
  {
    return byKey.containsKey(key);
  }
}
