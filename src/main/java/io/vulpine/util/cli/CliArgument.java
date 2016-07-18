package io.vulpine.util.cli;

import io.vulpine.util.cli.def.CliArgumentDef;
import io.vulpine.util.cli.def.CliParameterDef;

import java.util.ArrayList;
import java.util.List;

public abstract class CliArgument < T > extends CliBase implements CliArgumentDef < T >
{
  protected final boolean required;
  protected final CliParameterDef < T > parameter;
  protected final char key;
  protected final List < T > values;

  protected boolean used;

  public CliArgument (
    final char key,
    final String name,
    final String description,
    final boolean required,
    final CliParameterDef < T > param
  )
  {
    super(name, description);
    this.required = required;

    this.parameter = param;
    this.key = key;

    values = new ArrayList < T >();
  }

  @Override
  public CliParameterDef < T > getParameter ()
  {
    return parameter;
  }

  @Override
  public boolean hasParameter ()
  {
    return null == parameter;
  }

  @Override
  public char getKey ()
  {
    return key;
  }

  @Override
  public void use ()
  {
    this.used = true;
  }

  @Override
  public boolean wasUsed ()
  {
    return used;
  }

  @Override
  public List < T > getValues ()
  {
    return values;
  }

  @Override
  public boolean isRequired()
  {
    return required;
  }
}
