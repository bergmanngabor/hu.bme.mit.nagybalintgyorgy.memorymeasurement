package hu.bme.mit.nagybalintgyorgy.memorymeasurement;
import javax.management.MBeanServer;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.UUID;

import com.sun.management.HotSpotDiagnosticMXBean;

public class HeapDumper {
	// This is the name of the HotSpot Diagnostic MBean
    private static final String HOTSPOT_BEAN_NAME =
         "com.sun.management:type=HotSpotDiagnostic";
	
	
	// field to store the hotspot diagnostic MBean 
    private static volatile HotSpotDiagnosticMXBean hotspotMBean;
	private HeapDumpAnalyzer heapDumpAnalyzer;
    
    
	public HeapDumper(){
		initHotspotMBean();
		heapDumpAnalyzer= new HeapDumpAnalyzer();
	}
	
	public boolean createHeapDump(String filename){
		
		try {
			deleteFile(filename);
			// second arg is true because I need only live objects
			hotspotMBean.dumpHeap(filename, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	
	private void deleteFile(String filename){
	    
	    File file= new File(filename);
        if(file.exists()){
            file.delete();
        } 
	}
	
	// initialize the hotspot diagnostic MBean field
    private static void initHotspotMBean() {
        if (hotspotMBean == null) {
            synchronized (HeapDumper.class) {
                if (hotspotMBean == null) {
                    hotspotMBean = getHotspotMBean();
                }
            }
        }
    }
    
    // get the hotspot diagnostic MBean from the
    // platform MBean server
    private static HotSpotDiagnosticMXBean getHotspotMBean() {
        try {
            MBeanServer server = ManagementFactory.getPlatformMBeanServer();
            HotSpotDiagnosticMXBean bean = 
                ManagementFactory.newPlatformMXBeanProxy(server,
                HOTSPOT_BEAN_NAME, HotSpotDiagnosticMXBean.class);
            return bean;
        } catch (RuntimeException re) {
            throw re;
        } catch (Exception exp) {
            throw new RuntimeException(exp);
        }
    }
}
