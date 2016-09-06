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
package io.vulpine.util.cli.def;

/**
 * = CLI Argument
 *
 * [[shorthand-key]]
 * == Shorthand Argument Key

 * A shorthand key is a single dash, single character form for an argument.
 *
 * Valid characters for shorthand keys include letter characters, numbers, and
 * any other single characters not given special treatment by your CLI
 * environment.
 *
 * .Examples
 * [source, text]
 * ----
 * # Valid
 *   -a
 *   -1 -2 -3
 *   -?
 *   -_
 *
 * # Likely Invalid
 *   -'
 *   -"
 *   -*
 *   -\
 *   --
 * ----
 *
 * Shorthand keys, when provided a parameter, do not require the parameter value
 * to be assigned using an equals character.  This is, however  a supported
 * feature, meaning both of the following are correct:
 *
 * [source, text]
 * ----
 *  -t=yes
 *  -v "extra"
 * ----
 *
 * [[long-form]]
 * == Full Argument Key
 *
 * A long-form key is defined by 2 dashes followed by one or more characters.
 *
 * Valid characters for long-form keys include letter characters, numbers,
 * dashes, and underscore characters.
 *
 * .Examples
 * [source, text]
 * ----
 *   --this-is-valid
 *   --this/is/not
 *   --this_works_too
 *   --42
 * ----
 *
 * Long-form keys, unlike shorthand keys, require an equals character directly
 * between the name definition and the parameter value.  This is not a technical
 * limitation, but rather a convention for CLI applications.  This limitation
 * may be removed in future versions.
 *
 * [source, text]
 * ----
 * # Valid
 *   --this="is okay"
 *   --so-is=this
 *
 * # Invalid
 *   --this = "is not"
 *   --this "isn't either"
 * ----
 *
 * @param <T> Type of this argument's parameter (Void) if none.
 *
 * @author https://github.com/EllieFops[Elizabeth Harper]
 * @since 0.0.4
 * @version 1.0.7
 */
public interface Argument < T > extends Named, Described, Defined
{
  Parameter< T > getParameter ();

  /**
   * Gets whether or not this Argument has a <<shorthand-key,shorthand>> key.
   *
   * @return whether or not this Argument has a <<shorthand-key,shorthand>> key.
   */
  boolean hasShortKey ();

  /**
   * Gets whether or not this Argument has a <<long-form,long-form>> key
   *
   * @return whether or not this Argument has a <<long-form,long-form>> key
   */
  boolean hasLongKey ();

  boolean hasParameter ();

  /**
   * Gets Argument Short Key
   *
   * @return Short name character
   */
  char getKey ();

  /**
   * Flags this Argument as having been used in the CLI call of this application
   */
  void use();

  /**
   * Gets whether or not this Argument was used
   *
   * @return whether or not this argument was used
   */
  boolean wasUsed();

  /**
   * Gets whether or not this Argument is required.
   *
   * Required arguments provide an alternative to parameters, allowing required
   * values to be provided in an arbitrary order.
   *
   * @return whether or not this argument is required by it's parent component.
   */
  boolean isRequired();
}
