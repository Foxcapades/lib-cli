package io.vulpine.util.cli;

import io.vulpine.util.cli.def.CliParameterDef;

public abstract class CliParameter < T > extends CliBase implements CliParameterDef < T >
{
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
  public String getHelpText()
  {
    return "";
  }
}
