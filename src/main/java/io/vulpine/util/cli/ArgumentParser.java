package io.vulpine.util.cli;

import java.util.*;

public class ArgumentParser
{
  /**
   * Passed Arguments Mapped To Values Indexed By Given Argument Name
   */
  protected final Map < String, List < String > > byName;

  /**
   * Passed Arguments Mapped To Values Indexed By Given Argument Key
   */
  protected final Map < Character, List < String > > byKey;

  /**
   * Passed Flags By Given Name
   */
  protected final Set < String > nameFlags;

  /**
   * Passed Flags By Given Key
   */
  protected final Set < Character > keyFlags;

  /**
   * Passed Parameters In Order Of Appearance
   */
  protected final Queue < String > params;

  /**
   * Application Mode
   */
  protected final String mode;

  public ArgumentParser ( final String[] args, final boolean moded )
  {
    byName    = new HashMap < String, List < String > >();
    byKey     = new HashMap < Character, List < String > >();
    nameFlags = new HashSet < String >();
    keyFlags  = new HashSet < Character >();
    params    = new LinkedList < String >();

    parseRemainder(args);

    mode = moded ? params.poll() : null;
  }

  public String getCliMode()
  {
    return mode;
  }

  public boolean hasValue( final String k )
  {
    return byName.containsKey(k);
  }

  public List < String > getValue ( final String k )
  {
    return byName.get(k);
  }

  public boolean hasFlag ( final String k )
  {
    return nameFlags.contains(k);
  }

  private void parseRemainder ( final String[] args )
  {
    String openName = null;
    Character openKey = null;

    for ( int i = null == mode ? 0 : 1; i < args.length; i++ ) {

      final String a = args[i];

      final boolean isFlag = a.charAt(0) == '-';
      final boolean isLong = a.charAt(1) == '-';

      if (isFlag) {

        if (isLong) {

          final int b = a.indexOf('=');

          openName = null;

          if (b > -1) {
            addValue(a.substring(2, b), a.substring(b + 1));
          } else {
            openName = a.substring(2);
            nameFlags.add(openName);
          }

        } else if (a.length() > 2) {

          final char[] b = a.toCharArray();

          openKey = null;

          for ( int j = 1; j < b.length; j++ ) {
            final char tk = b[j];

            if (j + 1 == b.length) {
              openKey = tk;
            }

            keyFlags.add(tk);
          }

        } else {

          openKey = a.charAt(1);
          keyFlags.add(openKey);

        }

      } else {

        if (null != openName) {
          nameFlags.remove(openName);
          addValue(openName, a);
          openName = null;
        }

        if (null != openKey) {
          keyFlags.remove(openKey);
          addValue(openKey, a);
          openKey = null;
        }

        params.add(a);
      }
    }
  }

  public Map < String, List < String > > getArgumentsByName()
  {
    return byName;
  }

  public Map < Character, List < String > > getArgumentsByKey()
  {
    return byKey;
  }

  public Set < String > getFlagsByName()
  {
    return nameFlags;
  }

  public Set < Character > getFlagsByKey()
  {
    return keyFlags;
  }

  public Queue < String > getParameters()
  {
    return params;
  }

  private void addValue ( final String k, final String v )
  {
    if (byName.containsKey(k)) {
      byName.get(k).add(v);
    } else {
      final List < String > l = new ArrayList < String >();
      byName.put(k, l);
      l.add(v);
    }
  }

  private void addValue ( final char k, final String v )
  {
    if (byKey.containsKey(k)) {
      byKey.get(k).add(v);
    } else {
      final List < String > l = new ArrayList < String >();
      byKey.put(k, l);
      l.add(v);
    }
  }
}
