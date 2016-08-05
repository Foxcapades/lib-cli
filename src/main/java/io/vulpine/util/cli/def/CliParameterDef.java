package io.vulpine.util.cli.def;

public interface CliParameterDef < T > extends DescribableDef, NamableDef, HasHelpText
{
  T parseValue( final String value );
  boolean isRequired();
}
