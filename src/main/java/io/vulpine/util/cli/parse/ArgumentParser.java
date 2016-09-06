package io.vulpine.util.cli.parse;

import io.vulpine.util.cli.def.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class ArgumentParser
{
  private final Application application;

  private final String[] arguments;

  private final Collection < String > errors;

  private final Collection < String > unassignedParameters;

  private OperationMode mode = null;

  /**
   * Whether or not argument parsing has finished.
   *
   * Turning off argument parsing can be achieved through use of the
   * pseudo-argument `--`
   *
   * Once argument parsing is disabled, all remaining text is treated as
   * parameters for the application or operation mode.
   */
  private boolean stillParsingArgs = true;

  /**
   * Whether or not the argument parser is currently looking for an optional
   * parameter.
   */
  private boolean parsingParameter = false;

  /**
   * Whether or not the argument parser is currently looking for a required
   * parameter.
   */
  private boolean parameterOnly = false;

  private Argument lastArgument = null;

  private boolean isShorthand = false;

  private boolean isLongform = false;

  /**
   * Class Constructor
   *
   * @param application Current CLI Application
   * @param arguments   Command Line Arguments
   */
  public ArgumentParser( final Application application, final String[] arguments )
  {
    this.application = application;
    this.arguments = arguments;

    this.errors = new ArrayList < String >();
    this.unassignedParameters = new LinkedList < String >();
  }

  /**
   * TODO: Handle space between arg and '=' character.
   * TODO: Handle strings in single quotes
   *
   * @return Status code. == 0 = good, > 0 = error.
   */
  public int run( final OperationMode mode )
  {
    /*
     * Argument Loop
     */
    for ( final String arg : this.arguments ) {

      // Consume entire argument if searching for a required parameter.
      if ( parameterOnly && null != lastArgument ) {

        lastArgument.getParameter().parseValue(arg);

        parsingParameter = false;
        parameterOnly = false;

        lastArgument = null;

      } else {

        final char[] chars = arg.toCharArray();

        // Reset Argument trackers
        isShorthand = false;
        isLongform = false;

        //
        // Character Loop
        //
        for ( int i = 0; i < chars.length; i++ ) {

          final char currentChar = chars[i];

          // Is a new argument starting
          if ( '-' == currentChar && stillParsingArgs && i < 2 ) {
            determineArgType(currentChar, i, arg);
            continue;
          }

          if ( stillParsingArgs ) {

            if ( isShorthand ) {
              if ( handleShortHand(currentChar, arg.substring(i + 1)) )
                break;
              continue;
            }

            if ( isLongform ) {
              handleLongForm(arg.substring(i));
              break;
            g}

            if ( !parsingParameter && !parameterOnly )
              continue;
          }

          if ( null != lastArgument ) {

            if ( lastArgument.hasParameter() ) {
              lastArgument.getParameter().parseValue(arg.substring(i));
              break;
            }

            lastArgument = null;
            continue;
          }

          if ( mode.hasUnfilledParam() ) {
            mode.parseParam(arg.substring(i));
            continue;
          }

          if ( this.application.hasUnfilledParameter() ) {
            this.application.parseParameter(arg.substring(i));
            continue;
          }

          this.unassignedParameters.add(arg.substring(i));
        }
      }
    }

    if ( errors.isEmpty() )
      return 0;

    //TODO Logger
    printErrors(this.errors);
    return 1;
  }

  public Collection < String > getUnassignedParameters()
  {
    return this.unassignedParameters;
  }

  private void determineArgType( final char current, final int index, final String arg )
  {
    // If this is the first char, this is a new argument
    if ( 0 == index ) {
      isShorthand = true;
      return;
    }

    // If this is the second char, this is a longform argument
    if ( 1 == index && isShorthand ) {

      isShorthand = false;

      // If arg string consisted of simply '--' stop parsing args;
      if ( 1 == arg.substring(index).length() ) {

        isLongform = false;
        stillParsingArgs = false;

      } else {

        isLongform = true;

      }
    }

  }

  private boolean handleShortHand( final char current, final String remainder )
  {
    final String    remains;
    final Argument  currentArg;
    final Parameter param;

    if ( '=' == current ) {

      // If an equals sign was used, but the previous argument does not
      // take a parameter, invalidate the rest of the argument line.
      if ( !parsingParameter ) {

        this.doesNotTakeParam(lastArgument.getKey());

      } else {

        // Argument takes param, = used. Use the remainder of the current
        // argument string as the param value. (+ 1 to remove '=' from
        // string)
        lastArgument.getParameter().parseValue(remainder);

        // No longer looking for parameter
        parsingParameter = false;
      }

      // Stop character loop, move to next argument string.
      return true;

    }

    remains = current + remainder;
    currentArg = findArgument(current);

    // If the current argument is not a known arg
    if ( null == currentArg ) {

      // If not currently looking for an optional param, this is an
      // unknown argument
      if ( !parsingParameter ) {
        this.unrecognized(current);
        return false;
      }


      lastArgument.getParameter().parseValue(remains);

      // No longer checking for parameter
      parsingParameter = false;

      // Rest of this arg string consumed, move to next argument string.
      return true;

    }

    lastArgument = currentArg;

    // If the current argument takes a value
    if ( currentArg.hasParameter() ) {

      param = currentArg.getParameter();

      parsingParameter = true;

      if ( param.isRequired() )
        parameterOnly = true;
    }

    return false;
  }

  private void handleLongForm( final String remainder )
  {
    final int      ind;
    final String   longKey;
    final boolean  isValueIncluded;
    final Argument currentArg;

    ind = remainder.indexOf('=');

    isValueIncluded = 0 < ind;

    longKey = isValueIncluded
      ? remainder.substring(0, ind)
      : remainder;

    currentArg = findArgument(longKey);

    if ( null == currentArg ) {
      this.unrecognized(remainder);
      return;
    }

    if ( !currentArg.hasParameter() ) {
      if ( isValueIncluded )
        this.doesNotTakeParam(longKey);
      return;
    }

    if ( isValueIncluded ) {
      currentArg.getParameter().parseValue(remainder.substring(ind + 1));
      return;
    }

    if ( currentArg.getParameter().isRequired() ) {
      parameterOnly = true;
      return;
    }

    parsingParameter = true;
  }

  private void unrecognized( final Object o )
  {
    errors.add(String.format("Unrecognized argument %s.", o));
  }

  private void doesNotTakeParam( final Object o )
  {
    errors.add(String.format("Argument %s does not take a parameter.", o));
  }

  private static void printErrors( final Collection < String > errors )
  {
    final Iterator < String > iterator = errors.iterator();

    while ( iterator.hasNext() )
      System.out.println(iterator.next());
  }

  private Argument findArgument( final String key )
  {

    if ( null != mode && mode.getArgumentSet().hasArgument(key) )
      return mode.getArgumentSet().getArgument(key);

    if ( this.application.getArgumentSet().hasArgument(key) )
      return this.application.getArgumentSet().getArgument(key);

    return null;
  }

  private Argument findArgument( final char key )
  {

    if ( null != mode && mode.getArgumentSet().hasArgument(key) )
      return mode.getArgumentSet().getArgument(key);

    if ( this.application.getArgumentSet().hasArgument(key) )
      return this.application.getArgumentSet().getArgument(key);

    return null;
  }
}
