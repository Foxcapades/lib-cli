package io.vulpine.util.cli.def;

public interface CliArgumentInterface < T > extends NameInterface, DescriptionInterface, HelpInterface
{
  CliParameterInterface< T > getParameter ();

  boolean hasShortKey ();

  boolean hasLongKey ();

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
   * @return whether or not this argument was used
   */
  boolean wasUsed();

  boolean isRequired();
}
