package io.vulpine.util.cli;

import io.vulpine.util.cli.def.CliArgumentDef;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ArgumentSet
{
  protected final Map < String, CliArgumentDef >   byName;
  protected final Map < Character, CliArgumentDef > byKey;
  protected final Set < CliArgumentDef > arguments;

  public ArgumentSet ()
  {
    byKey  = new HashMap < Character, CliArgumentDef >();
    byName = new HashMap < String, CliArgumentDef >();
    arguments = new HashSet< CliArgumentDef >();
  }

  public CliArgumentDef getArgument( final String name )
  {
    return byName.get(name);
  }

  public CliArgumentDef getArgument ( final char key )
  {
    return byKey.get(key);
  }

  public Set< CliArgumentDef > getArguments()
  {
    return this.arguments;
  }

  public void addArgument( final CliArgumentDef arg )
  {
    this.byKey.put(arg.getKey(), arg);
    this.byName.put(arg.getName(), arg);
    this.arguments.add(arg);
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
