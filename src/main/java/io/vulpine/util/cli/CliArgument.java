package io.vulpine.util.cli;

import io.vulpine.util.cli.def.CliArgumentInterface;
import io.vulpine.util.cli.def.CliParameterInterface;
import io.vulpine.util.cli.def.HelpInterface;

public class CliArgument < T > extends Common implements CliArgumentInterface < T >
{
  /**
   * Allows flagging an argument as required.
   */
  protected final boolean required;

  /**
   * Argument Parameter Handler
   */
  protected final CliParameterInterface < T > parameter;

  /**
   * Short Key used for this argument
   */
  protected final char key;

  /**
   * Whether or not this argument was used in the CLI call
   */
  protected boolean used;

  /**
   * Full Signature
   *
   * @param key      Argument short key ( example: -k )
   * @param name     Argument long key ( example --key )
   * @param help     Argument help text
   * @param required Is this argument required
   * @param param    Argument Parameter handler
   */
  public CliArgument ( char key, String name, String help, boolean required, CliParameterInterface < T > param )
  {
    super(name, help);

    this.required = required;
    this.parameter = param;
    this.key = key;
  }

  /**
   * No Short Key Signature
   *
   * @param name  Argument long key
   * @param help  Argument help text
   * @param req   Is this argument required
   * @param param Argument parameter handler
   */
  public CliArgument ( String name, String help, boolean req, CliParameterInterface < T > param )
  { this('\u0000', name, help, req, param); }

  /**
   * No Long Key Signature
   *
   * @param key   Argument short key
   * @param help  Argument help text
   * @param req   Is this argument required?
   * @param param Argument parameter handler
   */
  public CliArgument ( char key, String help, boolean req, CliParameterInterface < T > param )
  { this(key, String.valueOf(key), help, req, param); }

  /**
   * Not required Signature
   *
   * @param key   Argument Short Key
   * @param name  Argument Long Key
   * @param help  Argument Help Text
   * @param param Parameter Handler
   */
  public CliArgument ( char key, String name, String help, CliParameterInterface < T > param )
  { this(key, name, help, false, param); }

  /**
   * No Short Key, Not Required Signature
   *
   * @param name  Argument Long Key
   * @param help  Argument Help Text
   * @param param Parameter Handler
   */
  public CliArgument ( String name, String help, CliParameterInterface < T > param )
  { this('\u0000', name, help, false, param); }

  /**
   * No Long Key, Not Required Signature
   *
   * @param key   Argument Short Key
   * @param help  Argument Help Text
   * @param param Parameter Handler
   */
  public CliArgument ( char key, String help, CliParameterInterface < T > param )
  { this(key, null, help, false, param); }

  @Override
  public CliParameterInterface < T > getParameter ()
  { return parameter; }

  @Override
  public boolean hasShortKey () { return key != '\u0000'; }

  @Override
  public boolean hasLongKey () { return null == name; }

  @Override
  public boolean hasParameter () { return null == parameter; }

  @Override
  public char getKey () { return key; }

  @Override
  public void use () { this.used = true; }

  @Override
  public boolean wasUsed () { return used; }

  @Override
  public boolean isRequired () { return required; }

  @Override
  public String[] getHelpText ()
  {
    final String[] out = new String[2];

    if ( null == parameter ) {
      if ( '\u0000' == key ) {
        out[0] = String.format("--%s", name);
      } else if ( null == name ) {
        out[0] = String.format("-%s", key);
      } else {
        out[0] = String.format("-%s | --%s", key, name);
      }
    } else {
      final String p = parameter.getName();
      if ( '\u0000' == key ) {
        out[0] = String.format(required ? "--%s=<%s>" : "--%s[=%s]" , name, p);
      } else if ( null == name ) {
        out[0] = String.format(required ? "-%s <%s>" : "-%s [%s]" , key, p);
      } else {
        out[0] = String.format(required ? "-%s <%s> | --%s=<%s>" : "-%s [%s] | --%s[=%s]" , key, p, name, p);
      }
    }
    out[1] = HelpInterface.INDENT + description;

    return out;
  }
}
