package io.vulpine.util.cli.def;

import java.util.Set;

public interface ArgumentSetDef extends HasHelpText
{

  CliArgumentDef getArgument( String name );

  CliArgumentDef getArgument( char key );

  Set< CliArgumentDef > getArguments();

  void addArgument( CliArgumentDef arg );

  boolean hasArgument( String name );

  boolean hasArgument( char key );
}
