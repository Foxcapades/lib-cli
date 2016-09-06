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

import java.util.Set;

/**
 * = Argument Set
 *
 * A collection of arguments belonging to a CLI component.
 *
 * Arguments in this collection (and application wide) should be unique by name
 * or long form as well as by key or shorthand form.
 *
 * Implementations of this interface are not required to validate that child
 * arguments are unique, so care should be taken when adding new arguments to
 * prevent issues with overlapping or competing arguments.
 *
 * @author https://github.com/EllieFops[Elizabeth Harper]
 * @version 1.0.0
 * @since 0.1.0
 */
public interface ArgumentCollection extends Defined
{
  /**
   * Retrieves an argument from this ArgumentSet that has the given name.
   *
   * @param name Name of the argument to retrieve.
   *
   * @return The argument with the given name, or null if this ArgumentSet does
   *         not contain an argument with that name.
   */
  Argument getArgument( String name );

  /**
   * Retrieves an argument from this ArgumentSet that has the given key.
   *
   * @param key Name of the argument to retrieve.
   *
   * @return The argument with the given shortcut key, or null if this argument
   * set does not contain an argument with that name.
   */
  Argument getArgument( char key );

  /**
   * Retrieves a read-only set of all arguments contained in this ArgumentSet.
   *
   * @return A read-only copy of the internal argument collection for this
   *         ArgumentSet.
   */
  Set < Argument > getArguments();

  /**
   * Adds an argument to the current ArgumentSet.
   *
   * @param arg Argument to add.
   */
  void addArgument( Argument arg );

  /**
   * Checks if this ArgumentSet contains an argument with the given name.
   *
   * @param name Name of the argument to check for.
   *
   * @return Whether or not the given argument is in this ArgumentSet.
   */
  boolean hasArgument( String name );

  /**
   * Checks if this ArgumentSet contains an argument with the given key.
   *
   * @param key shortcut key of the argument to check for.
   *
   * @return Whether or not the given argument is in this ArgumentSet.
   */
  boolean hasArgument( char key );

  boolean isEmpty();
}
