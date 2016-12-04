package hu.bme.mit.nagybalintgyorgy.memorymeasurement;

import java.io.File;

public class MemoryMeasureTool {
	
	HeapDumper heapdumper;
	HeapDumpAnalyzer heapdumpanalyzer;
	boolean firstrun= true;
	
	public MemoryMeasureTool(){
		heapdumpanalyzer= new HeapDumpAnalyzer();
		heapdumper= new HeapDumper();
	}
	
	public long getHeapSize(){
		if(firstrun){
			init();
			firstrun=false;
		}
		
		String filename= "dump";
	    heapdumper.createHeapDump(filename);
        long size=  heapdumpanalyzer.getHeapSize(filename);
        deleteAllFile(filename);
        
        return size;
	}
	

	private void deleteAllFile(String name) {
		deleteFile(name);
		deleteFile(name + ".a2s.index");
		deleteFile(name + ".domIn.index");
		deleteFile(name + ".domOut.index");
		deleteFile(name + ".idx.index");
		deleteFile(name + ".inbound.index");
		deleteFile(name + ".index");
		deleteFile(name + ".o2c.index");
		deleteFile(name + ".o2hprof.index");
		deleteFile(name + ".o2ret.index");
		deleteFile(name + ".outbound.index");
		deleteFile(name + ".threads");	
	}
	
	private void deleteFile(String filename){
	    
	    File file= new File(filename);
        if(file.exists()){
            file.delete();
        } 
	}

	private void init() {
		heapdumper.createHeapDump("test");
		deleteFile("test");
		try {
			Thread.sleep(25000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		heapdumper.createHeapDump("test");
		deleteFile("test");
		
	}
}
