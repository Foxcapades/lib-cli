package io.vulpine.util.cli;

import io.vulpine.util.cli.def.CliArgumentInterface;
import io.vulpine.util.cli.def.ArgumentSetInterface;
import io.vulpine.util.cli.def.HelpInterface;

import java.util.*;

public class ArgumentSet implements ArgumentSetInterface
{
  protected final Map < String, CliArgumentInterface > byName;

  protected final Map < Character, CliArgumentInterface > byKey;

  protected final Set < CliArgumentInterface > arguments;

  public ArgumentSet()
  {
    byKey = new HashMap < Character, CliArgumentInterface >();
    byName = new HashMap < String, CliArgumentInterface >();
    arguments = new HashSet < CliArgumentInterface >();
  }

  @Override
  public CliArgumentInterface getArgument( final String name )
  {
    return byName.get(name);
  }

  @Override
  public CliArgumentInterface getArgument( final char key )
  {
    return byKey.get(key);
  }

  @Override
  public Set < CliArgumentInterface > getArguments()
  {
    return this.arguments;
  }

  @Override
  public void addArgument( final CliArgumentInterface arg )
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
  public String[] getHelpText()
  {
    final String[] out = new String[arguments.size() * 2 + 1];
    int i = 0;

    out[i++] = "Arguments:";

    for ( final CliArgumentInterface arg : this.arguments ) {
      for ( final String h : arg.getHelpText() ) { out[i++] = HelpInterface.INDENT + h; }
    }

    return out;
  }
}
