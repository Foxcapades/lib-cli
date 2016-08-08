package io.vulpine.util.cli.def;

import java.util.Set;

public interface ArgumentSetInterface extends HelpInterface
{

  CliArgumentInterface getArgument( String name );

  CliArgumentInterface getArgument( char key );

  Set< CliArgumentInterface > getArguments();

  void addArgument( CliArgumentInterface arg );

  boolean hasArgument( String name );

  boolean hasArgument( char key );
}
