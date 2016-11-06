package hu.bme.mit.nagybalintgyorgy.memorymeasurement;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.mat.SnapshotException;
import org.eclipse.mat.snapshot.ISnapshot;
import org.eclipse.mat.snapshot.SnapshotFactory;
import org.eclipse.mat.snapshot.SnapshotInfo;
import org.eclipse.mat.snapshot.model.IClass;
import org.eclipse.mat.snapshot.model.IObject;
import org.eclipse.mat.util.VoidProgressListener;

public class HeapDumpAnalizer {

	
	public HeapDumpAnalizer(){

	}
	
	
	public long getHeapSize(String dumpname) {

		try {
			ISnapshot snapshot = SnapshotFactory.openSnapshot(new File(dumpname), new VoidProgressListener());
			List<IClass> classes= (List<IClass>) snapshot.getClasses();
			VoidProgressListener vp= new VoidProgressListener();
			
			long size=0;
			
	
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
			System.out.println("Objektumok száma: " + set.size() + "\n Mérete:  " + size);
			
			
			System.out.println("vagy: száma:" + idz.length  +  "  \n Mérete:" + size2);
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
