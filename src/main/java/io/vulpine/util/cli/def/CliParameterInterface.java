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
 * = CLI Parameter
 *
 * @param <T> Type of the expected input value from the command line.
 *
 * @author https://github.com/EllieFops[Elizabeth Harper]
 */
public interface CliParameterInterface < T > extends HasDescription, HasName, HasHelpText
{
  /**
   * Gets the contained value having been parsed by this Parameter.
   *
   * @return Value parsed by this parameter of the parameter's defined type.
   */
  T getValue();

  void parseValue( final String value );

  /**
   * Gets whether or not this Parameter is required.
   *
   * Parameters can be defined as required if their parent component must have
   * user input at call time.
   *
   * @return if this Parameter is required by it's parent component.
   */
  boolean isRequired();
}
