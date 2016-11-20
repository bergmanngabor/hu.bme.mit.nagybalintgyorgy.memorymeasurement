package hu.bme.mit.nagybalintgyorgy.memorymeasurement;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.mat.SnapshotException;
import org.eclipse.mat.snapshot.ISnapshot;
import org.eclipse.mat.snapshot.SnapshotFactory;
import org.eclipse.mat.snapshot.SnapshotInfo;
import org.eclipse.mat.snapshot.model.GCRootInfo;
import org.eclipse.mat.snapshot.model.IClass;
import org.eclipse.mat.snapshot.model.IObject;
import org.eclipse.mat.snapshot.model.IStackFrame;
import org.eclipse.mat.snapshot.model.IThreadStack;
import org.eclipse.mat.util.VoidProgressListener;

public class HeapDumpAnalyzer {

	Set<Integer> set;
	ISnapshot snapshot;
	public HeapDumpAnalyzer(){
		set= new HashSet<Integer>();
	}
	
	
	
	public long getHeapSize(String dumpname) {

		try {
			
			set= new HashSet<Integer>();
			snapshot = SnapshotFactory.openSnapshot(new File(dumpname), new VoidProgressListener());
			long size=0;
			VoidProgressListener vp= new VoidProgressListener();
			
			
			
			int[] ids= snapshot.getGCRoots();
			for(int id:ids){
				set.add(id);
			}
			size= snapshot.getHeapSize(snapshot.getRetainedSet(ids, vp));
			
			snapshot.dispose();

			return size;
			
			
		} catch (SnapshotException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;

	}
	

	private void getIDs(int nextid) throws SnapshotException {
			set.add(nextid);
			int[] ids= snapshot.getOutboundReferentIds(nextid);
			for(int i=0; i< ids.length; i++){
				if(!set.contains(ids[i]))
					getIDs(ids[i]);
			}
		
	}
	
	private long getRetainedSize(int id) throws SnapshotException{
		long size= snapshot.getRetainedHeapSize(id);
		int[] outgoing= snapshot.getImmediateDominatedIds(id);
		for(int i=0; i<1; i++){
			size= size - snapshot.getRetainedHeapSize(outgoing[i]);
		}
		return size;
	}



	public int[] toInt(Set<Integer> set) {
	  int[] a = new int[set.size()];
	  int i = 0;
	  for (Integer val : set) a[i++] = val;
	  return a;
	}
}
