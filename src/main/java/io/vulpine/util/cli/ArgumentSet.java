package io.vulpine.util.cli;

import io.vulpine.util.cli.def.ArgumentSetDef;
import io.vulpine.util.cli.def.CliArgumentDef;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ArgumentSet implements ArgumentSetDef
{
  protected final Map < String, CliArgumentDef > byName;

  protected final Map < Character, CliArgumentDef > byKey;

  protected final Set < CliArgumentDef > arguments;

  public ArgumentSet()
  {
    byKey = new HashMap < Character, CliArgumentDef >();
    byName = new HashMap < String, CliArgumentDef >();
    arguments = new HashSet < CliArgumentDef >();
  }

  @Override
  public CliArgumentDef getArgument( final String name )
  {
    return byName.get(name);
  }

  @Override
  public CliArgumentDef getArgument( final char key )
  {
    return byKey.get(key);
  }

  @Override
  public Set < CliArgumentDef > getArguments()
  {
    return this.arguments;
  }

  @Override
  public void addArgument( final CliArgumentDef arg )
  {
    this.byKey.put(arg.getKey(), arg);
    this.byName.put(arg.getName(), arg);
    this.arguments.add(arg);
  }

  @Override
  public boolean hasArgument( final String name )
  {
    return byName.containsKey(name);
  }

  @Override
  public boolean hasArgument( final char key )
  {
    return byKey.containsKey(key);
  }

  @Override
  public String getHelpText()
  {
    final String ls   = System.getProperty("line.separator");
    final String ind  = "    ";
    final String indls = ls + ind;
    final String rarg = "< %s >";
    final String oarg = "[ %s ]";

    final StringBuilder sb = new StringBuilder("Arguments:").append(ls);

    for ( final CliArgumentDef arg : this.arguments ) {
      sb.append(indls).append(arg.getHelpText().replaceAll(ls, indls));
    }

    return sb.toString();
  }
}
