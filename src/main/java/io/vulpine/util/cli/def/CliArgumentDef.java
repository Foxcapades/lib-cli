package io.vulpine.util.cli.def;

import java.util.List;

public interface CliArgumentDef < T > extends NamableDef, DescribableDef, HasHelpText
{
  /**
   * Validate Parameter
   *
   * <p>
   *   Determine whether or not the text parsed as the parameter for this
   *   argument meets implementor defined requirements.
   * </p>
   *
   * @param p Parsed string from the command line
   */
  void parseParam ( String p ) throws IllegalArgumentException;

  CliParameterDef < T > getParameter ();

  boolean hasParameter ();

  /**
   * Get Argument Short Key
   *
   * @return Short name character
   */
  char getKey ();

  /**
   * Flag this argument as Used
   */
  void use();

  /**
   * Get whether or not this argument was used
   *
   * @return
   */
  boolean wasUsed();

  List < T > getValues ();

  boolean isRequired();
}
