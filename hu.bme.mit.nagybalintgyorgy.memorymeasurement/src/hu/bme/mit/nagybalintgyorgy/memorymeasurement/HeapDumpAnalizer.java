package hu.bme.mit.nagybalintgyorgy.memorymeasurement;

import java.io.File;
import java.util.Collection;

import org.eclipse.mat.SnapshotException;
import org.eclipse.mat.snapshot.ISnapshot;
import org.eclipse.mat.snapshot.SnapshotFactory;
import org.eclipse.mat.snapshot.model.IClass;
import org.eclipse.mat.util.VoidProgressListener;

public class HeapDumpAnalizer {

	public HeapDumpAnalizer(){
		
	}
	
	
	public long getHeapSize(String dumpname){
		
		try {
			long size=0;
			ISnapshot snapshot = SnapshotFactory.openSnapshot(new File(dumpname), new VoidProgressListener());
			Collection<IClass> classes = snapshot.getClassesByName("java.lang.Object",false);
			
			for(int i=0; i< classes.size();i++){
				IClass iclass = classes.iterator().next();
				int[] objIds= iclass.getObjectIds();
				long sizeOfClassObjects =  snapshot.getHeapSize(objIds);
				size= size+ sizeOfClassObjects;
			}
			
			return size;
			
		} catch (SnapshotException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
		
	}
}
