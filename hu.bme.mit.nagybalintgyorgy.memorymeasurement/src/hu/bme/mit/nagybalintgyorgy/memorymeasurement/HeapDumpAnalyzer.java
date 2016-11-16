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
			System.out.println("*************************************");
			System.out.println("-------------------------------------");
			
			set= new HashSet<Integer>();
			snapshot = SnapshotFactory.openSnapshot(new File(dumpname), new VoidProgressListener());
			//List<IClass> classes= (List<IClass>) snapshot.getClasses();
			long size=0;
			VoidProgressListener vp= new VoidProgressListener();
			
			
			/*
			for(int id : ids){
				if(!set.contains(id))
					getIDs(id);
			}
			
			/*
			Set<Integer> set= new HashSet<Integer>();
			for(int i=0; i< classes.size();i++){
				 IClass c= classes.get(i);
				 int[] ids= c.getObjectIds();
				 for(int j=0; j< ids.length; j++){
					 set.add(ids[j]);
				 }
			}
			
			int[] ids= toInt(set);
			*/
			/*
			for(int i=0; i<ids.length;i++){
				size += snapshot.getRetainedHeapSize(ids[i]);
			}
			*/
			
			
			//int[] idz= snapshot.getRetainedSet(ids, vp);
					
			//System.out.println("Heapdump:");
			//System.out.println("Objektumok száma: " + set.size() + "\n Mérete:  " + size);
			
			
			int[] ids= snapshot.getGCRoots();
			
			
			for(int id: ids){
				int type= snapshot.getGCRootInfo(id)[0].getType();
				String name= snapshot.getGCRootInfo(id)[0].getTypeAsString(type);
				if(name.equals("Thread")){
					set.add(id);
					System.out.println(snapshot.getObject(id).getClassSpecificName());
					System.out.println(snapshot.getRetainedHeapSize(id));
					
				}
			}
			
			
			
			size= snapshot.getHeapSize(snapshot.getRetainedSet(toInt(set), vp));
			
			/*
			int[] theids= snapshot.getRetainedSet(ids,vp);
			System.out.println("Objektumopk száma: " + theids.length);
			
			for(int id: theids){
				size+= snapshot.getHeapSize(id);
			}
			
			/*
			for(int id :ids){
				if(!set.contains(id)){
					getIDs(id);
				}
			}
				
			
			//System.out.println(set.size());
			
			
			for(int id: set){
				long s= snapshot.getHeapSize(id);
				//System.out.println(s);
				
				size += s;
			}
			*/
			
			snapshot.dispose();
			
			System.out.println("-------------------------------------");
			System.out.println("*************************************");
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
