package org.cytoscape.myapp.internal;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Vector;

public class AddToClasspath {
	
	  private static java.lang.reflect.Field LIBRARIES ;
      static {
          try {
				LIBRARIES = ClassLoader.class.getDeclaredField("loadedLibraryNames");
				LIBRARIES.setAccessible(true);
			} catch (NoSuchFieldException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
          
      }
      public static String[] getLoadedLibraries(final ClassLoader loader) throws IllegalArgumentException, IllegalAccessException {
          final Vector<String> libraries = (Vector<String>) LIBRARIES.get(loader);
          return libraries.toArray(new String[] {});
      }
	
	//final String[] libraries = ClassScope.getLoadedClasses(ClassLoader.getSystemClassLoader()); //MyClassName.class.getClassLoader()
/*
        @SuppressWarnings("unchecked")
        private static final Class[] parameters = new Class[] { URL.class };

        @SuppressWarnings("unchecked")
        public static void addToClassPath(URL ClassPath_URL) throws
Exception{
                URLClassLoader sysloader = (URLClassLoader)
ClassLoader.getSystemClassLoader();
                Class sysclass = URLClassLoader.class;
                Method method = sysclass.getDeclaredMethod("addURL", parameters);
                method.setAccessible(true);
                method.invoke(sysloader, new Object[] {ClassPath_URL});

                String Classpath = System.getProperty("java.class.path");
                Classpath += System.getProperty("path.separator") +
ClassPath_URL.toString().substring(5);
                System.setProperty("java.class.path", Classpath);
        }

        public static void addFile(String s) throws Exception {
                File f = new File(s);
                addFile(f);
        }// end method

        public static void addFile(File f) throws Exception {
                addToClassPath(f.toURL());
        }// end method
        
        
        */
      
}
