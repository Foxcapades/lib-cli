package io.vulpine.util.cli;

import io.vulpine.util.cli.def.CliArgumentInterface;
import io.vulpine.util.cli.def.CliParameterInterface;
import io.vulpine.util.cli.def.HelpInterface;
import io.vulpine.util.cli.def.CliModeInterface;

import java.util.*;

public abstract class CliMode extends Common implements CliModeInterface
{
  protected final Queue < CliParameterInterface > parameters;

  protected final ArgumentSet arguments;

  public CliMode ( final String n, final String d )
  {
    super(n, d);

    parameters = new LinkedList < CliParameterInterface >();
    arguments = new ArgumentSet();
  }

  @Override
  public CliMode addParameter ( final CliParameterInterface... p )
  {
    for ( final CliParameterInterface q : p ) { parameters.offer(q); }

    return this;
  }

  @Override
  public CliMode addArgument ( final CliArgumentInterface a )
  {
    arguments.addArgument(a);

    return this;
  }

  @Override
  public boolean hasUnfilledParam ()
  {
    return null != parameters.peek();
  }

  @Override
  public CliMode parseParam ( final String s )
  {
    parameters.poll().parseValue(s);

    return this;
  }

  @Override
  public ArgumentSet getArgumentSet ()
  {
    return arguments;
  }

  @Override
  public String[] getHelpText ()
  {
    final String[]      arg = this.arguments.getHelpText();
    final String[]      out = new String[arg.length + 3];
    final StringBuilder sb  = new StringBuilder(this.name);

    for ( final CliParameterInterface p : parameters ) { sb.append(" [").append(p.getName()).append(']'); }

    out[0] = sb.toString();
    out[1] = HelpInterface.INDENT + description;
    out[2] = "";

    for ( int i = 0; i < arg.length; i++ ) { out[i + 3] = HelpInterface.INDENT + arg[i]; }

    return out;
  }
}
