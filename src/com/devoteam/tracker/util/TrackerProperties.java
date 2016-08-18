package com.devoteam.tracker.util;



import java.io.*;
import java.util.*;


/**
 * A static class for read-only access to the Tracker properties.
 *
 * <P>The properties are looked for in the following locations:</P>
 * <OL>
 *   <LI>If the system property <TT>EBillPropertiesFile</TT> is set, its value is interpreted
 *       as a file path and the properties are read from the according file.
 *   </LI>
 *   <LI>The class loader that loaded the <TT>EBillProperties</TT> class is asked for the
 *       resource <TT>tracker.properties</TT>. This means that the properties file has to be
 *       in the root directory of a jar file or in a directory contained in the classpath.
 *   </LI>
 * </OL>
 *
 * <P>A call to the {@link #reload() reload()} method reloads the properties.</P>
 * <P>To get a notification each time the properties file is reloaded,
 * {@link #addReloadListener(EBillProperties.ReloadListener) add} a
 * {@link ReloadListener} to this class.</P>
 * <P>Example:</P>
 * <PRE>class WorkingClass {
 *
 *   String workingPlace = null;
 *
 *   WorkingClass() {
 *     init();
 *     EBillProperties.addReloadListener(new ReloadListener() {
 *       public void reload() {
 *         init();
 *       }
 *     });
 *   }
 *
 *   void init() {
 *     workingPlace = EBillProperties.getProperty("Work.Location", "nowhere");
 *   }
 *
 *   ...
 * }</PRE>
 *
 * <P>Copyright (c) 2002  Danet Ltd</P>
 *
 * @author Werner Vollmer
 * @version 1.0
 */

public class TrackerProperties extends Properties {

  /**
	 * 
	 */
	private static final long serialVersionUID = -7855058724502531442L;



/**
   * Listener for properties reload.
   */
  public static interface ReloadListener {

    /**
     * This method is called each time the properties file is reloaded.
     */
    public void reload();
  }


  /** The listeners list */
  private static ArrayList<ReloadListener> listeners = new ArrayList<ReloadListener>();


  /** The Tracker properties singleton */
  private static TrackerProperties properties = null;

  /** A reload synchronization object */
  private static Object reloadSync = new Object();

  /** A flag indicating that a reload is in progress */
  private static boolean reloadFlag = false;


  /** The default properties file name (<TT>tracker.properties</TT>) */
  public static final String DEFAULT_PROPERTIES_FILE_NAME = File.separator + "tracker.properties";

  /** The local override of the properties file name (<TT>tracker.properties</TT>) */
  public static final String LOCAL_PROPERTIES_FILE_NAME = 
    System.getProperty("TrackerPropertiesName", DEFAULT_PROPERTIES_FILE_NAME);

  /** The system property name where to read the file path from (<TT>TrackerPropertiesFile</TT>) */
  public static final String PROPERTIES_FILE_NAME_SYSTEM_PROPERTY = "TrackerPropertiesFile";

  /** Properties which can be read from the file */
  public static final String CLOUDSQLURL = "cloudSQLURL";
  public static final String MYSQLURL = "mySQLURL";
  
  static {
    try {
      reload();
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new RuntimeException(ex.getMessage());
    }
  }

  /*private final LinkedHashSet<Object> keys = new LinkedHashSet<Object>();

    @Override
  public Enumeration<Object> keys() {
      return Collections.<Object>enumeration(keys);
  }

    @Override
  public Object put(Object key, Object value) {
      keys.add(key);
      return super.put(key, value);
  }*/

  /**
   * Add a reload listener to get notifications when the properties file is reloaded.
   *
   * @param  listener  the reload listener to add
   */
  public static void addReloadListener(ReloadListener listener) {
        listeners.add(listener);
  }


  /**
   * Remove a reload listener.
   *
   * @param  listener  the reload listener to remove
   */
  public static void removeReloadListener(ReloadListener listener) {
    listeners.remove(listener);
  }


  /**
   * Get an Tracker property.
   *
   * @param  key  the property key
   * @return  the property string or <TT>null</TT> is it was not accessible
   */
  public static String getTrackerProperty(String key)
  {
    return properties.getProperty(key);
  }


  /**
   * Get an Tracker property.
   *
   * @param  key  the property key
   * @param  defaultValue  the default value if the key does not exist
   * @return  the property string or <TT>null</TT> is it was not accessible
   */
  public static String getTrackerProperty(String key, String defaultValue)
  {

    return properties.getProperty(key, defaultValue);
  }


  /**
   * Get all properties where the key starts with the given prefix.
   *
   * <P>The matching properties are copied to a new {@link Properties} object where the
   * given prefix is removed from the key names.</P>
   *
   * @param  key  the property key
   * @return  the property string or <TT>null</TT> is it was not accessible
   */
  public static Properties getTrackerProperties(String keyPrefix)
  {
    Properties pp = new Properties();
    int prefixLength = keyPrefix.length();

    for (Enumeration keyEn = properties.keys(); keyEn.hasMoreElements(); ) {
      String key = keyEn.nextElement().toString();
      if (key.startsWith(keyPrefix) && key.length() > prefixLength) {
        pp.put(key.substring(prefixLength), properties.get(key));
      }
    }
    return pp;
  }

  public static Properties getTrackerPropertiesSorted(String keyPrefix)
  {
    Properties pp = new Properties();
    int prefixLength = keyPrefix.length();
    
    Map<String, String> map = new HashMap<String, String>(); 
    for (Enumeration keyEn = properties.keys(); keyEn.hasMoreElements(); ) {
      String key = keyEn.nextElement().toString();
      if (key.startsWith(keyPrefix) && key.length() > prefixLength) {
          map.put(key, (String)properties.get(key));
      }
    }
    Map<String, String> treeMap = new TreeMap<String, String>(map);
    for (String key : treeMap.keySet()) {
        System.out.println("key: "+key+ " " + properties.get(key));
        pp.put(key.substring(prefixLength), properties.get(key));
    }

    return pp;
  }

  public static Properties getTrackerPropertiesToSort(String keyPrefix)
  {
    Properties pp = new Properties();
    int prefixLength = keyPrefix.length();

    for (Enumeration keyEn = properties.keys(); keyEn.hasMoreElements(); ) {
      String key = keyEn.nextElement().toString();
      if (key.startsWith(keyPrefix) && key.length() > prefixLength) {
        pp.put(key, properties.get(key));
      }
    }
    return pp;
  }


  /**
   * Reload the properties file and notify all listeners of the reload.
   *
   * <P>If the properties have already been loaded and the reload fails, the old properties
   * are preserved.</P>
   */
  public static void reload()
        throws IOException
  {
    boolean doReload = false;
    synchronized (reloadSync) {
      if (!reloadFlag) {
        reloadFlag = true;
        doReload = true;
      }
    }
    if (doReload) {
      try {
        TrackerProperties newProperties = new TrackerProperties();
        TrackerProperties oldProperties = properties;
        properties = newProperties;
        for (Iterator it = listeners.iterator(); it.hasNext(); ((ReloadListener)it.next()).reload());
        if (oldProperties != null) {
          oldProperties.clear();
        }
      } finally {
        reloadFlag = false;
      }
    }
  }



  /**
   * Create the properties singleton.
   */
  private TrackerProperties()
        throws IOException
  {
    InputStream propStream = null;
    String filePath = "";
    filePath = System.getProperty(PROPERTIES_FILE_NAME_SYSTEM_PROPERTY);
    String msg = null;
    if (filePath == null) {
      try {  
            msg = "Loading properties file '" + LOCAL_PROPERTIES_FILE_NAME + "' from classpath.";
            InputStream ps = getClass().getClassLoader().getResourceAsStream(LOCAL_PROPERTIES_FILE_NAME);
            BufferedInputStream bis = new BufferedInputStream(ps);
            bis.available();
            bis.close();
            ps.close(); //all of this just to test if it's there!
            propStream = getClass().getClassLoader().getResourceAsStream(LOCAL_PROPERTIES_FILE_NAME);
      } catch (Exception ex) {
            msg = "Unable to load " + LOCAL_PROPERTIES_FILE_NAME + 
                ". Loading properties file '" + DEFAULT_PROPERTIES_FILE_NAME + "' from classpath.";
            propStream = getClass().getClassLoader().getResourceAsStream(DEFAULT_PROPERTIES_FILE_NAME);
      }  
    } else {
      msg = "Loading properties file '" + filePath + "'.";
      File propertiesFile = new File(filePath);
      if (!propertiesFile.exists()) {
        throw new FileNotFoundException("Cannot find the properties file '"
                                        + propertiesFile.getCanonicalPath() + "'");
      }
      propStream = new FileInputStream(propertiesFile);
    }
    load(new BufferedInputStream(propStream));
    System.out.println(msg);
  }

}
