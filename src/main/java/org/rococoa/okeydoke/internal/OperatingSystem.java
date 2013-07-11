package org.rococoa.okeydoke.internal;

public enum OperatingSystem {
    WINDOWS("Windows", "(?i).*win.*"), //$NON-NLS-1$ //$NON-NLS-2$
    LINUX("Linux", "(?i).*lin.*"), //$NON-NLS-1$ //$NON-NLS-2$
    MAC("MacOS", "(?i).*mac.*"); //$NON-NLS-1$ //$NON-NLS-2$
   
    private static final OperatingSystem current = 
        fromString(System.getProperty("os.name")); //$NON-NLS-1$
    
    private final String name;
    private final String regexp;
   
    private OperatingSystem(String name, String regexp) {
        this.name = name;
        this.regexp = regexp;
    }
    
    public static OperatingSystem current() {
        return current;
    }
    
    public static OperatingSystem fromString(String propertyValue) {
        for (OperatingSystem os : values()) {
            if (propertyValue.matches(os.regexp))
                return os;
        }
        return null;
    }
    
    public String getName() {
        return name;
    }
    
    public String toString() {
        return name;
    }
    
    public boolean isMyOS() {
        return is(this);
    }

    public static boolean is(OperatingSystem os) {
        return current() == os;
    }

    public static boolean isnt(OperatingSystem os) {
        return !is(os);
    }
}