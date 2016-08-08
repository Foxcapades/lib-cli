package io.vulpine.util.cli;

import io.vulpine.util.cli.def.CliParameterInterface;

public abstract class CliParameter < T > extends Common implements CliParameterInterface< T >
{
  protected T value;

  protected final boolean required;

  public CliParameter ( final String n, final String d, final boolean r )
  {
    super(n, d);
    this.required = r;
  }

  @Override
  public boolean isRequired ()
  {
    return required;
  }

  @Override
  public String[] getHelpText()
  {
    return new String[0];
  }

  @Override
  public T getValue ()
  {
    return value;
  }
}
