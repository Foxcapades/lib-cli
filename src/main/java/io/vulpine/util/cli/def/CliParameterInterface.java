package io.vulpine.util.cli.def;

public interface CliParameterInterface < T > extends DescriptionInterface, NameInterface, HelpInterface
{
  T getValue();

  void parseValue( final String value );

  boolean isRequired();
}
