/*
 * Copyright 2016 Elizabeth Harper
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.vulpine.util.cli;

import io.vulpine.util.cli.def.Parameter;
import io.vulpine.util.cli.def.Defined;

public class Argument < T > extends Common implements io.vulpine.util.cli.def.Argument< T >
{
  /**
   * Allows flagging an argument as required.
   */
  protected final boolean required;

  /**
   * Argument Parameter Handler
   */
  protected final Parameter< T > parameter;

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
  public Argument( char key, String name, String help, boolean required, Parameter< T > param )
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
  public Argument( String name, String help, boolean req, Parameter< T > param )
  { this('\u0000', name, help, req, param); }

  /**
   * No Long Key Signature
   *
   * @param key   Argument short key
   * @param help  Argument help text
   * @param req   Is this argument required?
   * @param param Argument parameter handler
   */
  public Argument( char key, String help, boolean req, Parameter< T > param )
  { this(key, String.valueOf(key), help, req, param); }

  /**
   * Not required Signature
   *
   * @param key   Argument Short Key
   * @param name  Argument Long Key
   * @param help  Argument Help Text
   * @param param Parameter Handler
   */
  public Argument( char key, String name, String help, Parameter< T > param )
  { this(key, name, help, false, param); }

  /**
   * No Short Key, Not Required Signature
   *
   * @param name  Argument Long Key
   * @param help  Argument Help Text
   * @param param Parameter Handler
   */
  public Argument( String name, String help, Parameter< T > param )
  { this('\u0000', name, help, false, param); }

  /**
   * No Long Key, Not Required Signature
   *
   * @param key   Argument Short Key
   * @param help  Argument Help Text
   * @param param Parameter Handler
   */
  public Argument( char key, String help, Parameter< T > param )
  { this(key, null, help, false, param); }

  @Override
  public Parameter< T > getParameter ()
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
  public String[] getDefinition()
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
    out[1] = Defined.INDENT + description;

    return out;
  }
}
