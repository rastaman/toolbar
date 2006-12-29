/*
 * ResourceLocator.java
 *
 * Created on 11 May 2003, 22:40
 */

package org.tigris.toolbar.toolbutton;

import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.ImageIcon;

/**
 *
 * @author Bob Tarling
 * @stereotype singleton
 */
public class ResourceLocator {
    
    private static ResourceLocator instance = new ResourceLocator();
    
    private ArrayList resourcePaths = new ArrayList();
    
    public static ResourceLocator getInstance() {
        return instance;
    }
    
    /** Creates a new instance of ResourceLocator */
    private ResourceLocator() {
    }
    
    /**
     * Attempt to find an icon resource within the registered paths.
     * @param filename The filename of the icon resource.
     */
    public ImageIcon getIcon(String filename) {
        Iterator it = this.resourcePaths.iterator();
        while (it.hasNext()) {
            String resource = it.next().toString() + filename;
            java.net.URL imgURL = ResourceLocator.class.getResource(resource);
            try {
                ImageIcon icon = new ImageIcon(imgURL, resource);
                return icon;
            }
            catch(Exception ex) {
            }
        }
        return null;
    }
    
    public void addResourcePath(String path) {
        resourcePaths.add(path);
    }
}
