package io.vulpine.util.cli.def;

public interface CliParameterDef < T > extends DescribableDef, NamableDef
{
  T parseValue( final String value );
  boolean isRequired();
}
