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

import io.vulpine.util.cli.ArgumentParser;

/**
 * = Command Line Run Mode
 *
 * Defines a mode of operation for a command line application.
 *
 * Many command line applications provide more than a single function or mode of
 * operation, providing different feature sets for each.  These can be achieved
 * through use of different flags or different "Modes".
 *
 * In the following example there are 2 different operating modes, "find" and
 * "scene-for" which have the arguments `--containing` and `--line-id`
 * respectively.
 *
 * [source, yaml]
 * .Example
 * ----
 *   # holy-grail find --containing="three"
 *   results:
 *     1214: Then, shalt thou count to three.
 *     1217: Three shalt be the number thou shalt count, and the number o...
 *     1218: ...r count thou two, excepting that thou then proceed to three.
 *     1220: Once the number three, being the third number, be reached, t...
 *   # holy-grail scene-for --line-id=1220
 *   results:
 *     Scene 20 - Rabbit of Caerbannog
 * ----
 *
 * While different behaviors can be achieved through different structures and
 * use of flags, arguments, and parameters, operation modes can provide a clear
 * separation in logic sets, behaviors, or expectations for an application.
 *
 * @author https://github.com/EllieFops[Elizabeth Harper]
 * @since 0.1.0
 * @version 1.0.1
 */
public interface CliModeInterface extends HasDescription, HasName, HasHelpText
{
  /**
   * Add an Argument to the current run mode.
   *
   * @param argument argument to add.
   *
   * @return The current run mode
   *
   * @chainable
   */
  CliModeInterface addArgument( final CliArgumentInterface argument );

  /**
   * Add a Parameter to the current run mode.
   *
   * @param parameter parameter to add.
   *
   * @return The current run mode
   *
   * @chainable
   */
  CliModeInterface addParameter( final CliParameterInterface... parameter );

  /**
   * Execute the current run mode.
   *
   * @param parser Argument parser
   */
  void run( final ArgumentParser parser );

  /**
   * @return
   */
  boolean hasUnfilledParam();

  /**
   * Parse a given parameter
   *
   * @param str Parameter string from the CLI call
   *
   * @return The current run mode
   *
   * @chainable
   */
  CliModeInterface parseParam( String str );

  /**
   * @return
   */
  ArgumentSetInterface getArgumentSet();
}
