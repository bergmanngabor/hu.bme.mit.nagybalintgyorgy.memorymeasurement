package hu.bme.mit.nagybalintgyorgy.memorymeasurement;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.xml.type.util.XMLTypeResourceImpl.StackFrame;
import org.eclipse.mat.SnapshotException;
import org.eclipse.mat.snapshot.ISnapshot;
import org.eclipse.mat.snapshot.SnapshotFactory;
import org.eclipse.mat.snapshot.SnapshotInfo;
import org.eclipse.mat.snapshot.model.IClass;
import org.eclipse.mat.snapshot.model.IObject;
import org.eclipse.mat.snapshot.model.IStackFrame;
import org.eclipse.mat.snapshot.model.IThreadStack;
import org.eclipse.mat.util.VoidProgressListener;

public class HeapDumpAnalizer {

	
	public HeapDumpAnalizer(){

	}
	
	
	public long getHeapSize(String dumpname) {

		try {
			ISnapshot snapshot = SnapshotFactory.openSnapshot(new File(dumpname), new VoidProgressListener());
			long size=0;
			
			IThreadStack stack= snapshot.getThreadStack((int) Thread.currentThread().getId());
			IStackFrame[] stackframes= stack.getStackFrames();
			
			for(int i=0; i< stackframes.length ; i++){
				int[] ids= stackframes[i].getLocalObjectsIds();
				for (int j=0; j<ids.length;i++ ){
					int id= ids[i];
					size += snapshot.getRetainedHeapSize(id);
					System.out.println(id + " *****   " + size);
					
				}
			}
			
			
			/*
			List<IClass> classes= (List<IClass>) snapshot.getClasses();
			VoidProgressListener vp= new VoidProgressListener();
			
			
			
	
			Set<Integer> set= new HashSet<Integer>();
			for(int i=0; i< classes.size();i++){
				 IClass c= classes.get(i);
				 int[] ids= c.getObjectIds();
				 for(int j=0; j< ids.length; j++){
					 set.add(ids[j]);
				 }
			}
			
			int[] ids= toInt(set);
			for(int i=0; i<ids.length;i++){
				size += snapshot.getRetainedHeapSize(ids[i]);
			}
			int[] idz= snapshot.getRetainedSet(ids, vp);
			long size2 = snapshot.getHeapSize(idz);
			
			System.out.println("Heapdump:");
			System.out.println("Objektumok sz�ma: " + set.size() + "\n M�rete:  " + size);
			
			
			System.out.println("vagy: sz�ma:" + idz.length  +  "  \n M�rete:" + size2);
			
			
			*/
			
			return size;
			
			
			
		} catch (SnapshotException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return 0;

	}
	


	public int[] toInt(Set<Integer> set) {
	  int[] a = new int[set.size()];
	  int i = 0;
	  for (Integer val : set) a[i++] = val;
	  return a;
	}
}
