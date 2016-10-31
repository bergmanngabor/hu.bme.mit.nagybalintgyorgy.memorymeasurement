package hu.bme.mit.nagybalintgyorgy.memorymeasurement;

import java.io.File;

import org.eclipse.mat.SnapshotException;
import org.eclipse.mat.snapshot.ISnapshot;
import org.eclipse.mat.snapshot.SnapshotFactory;
import org.eclipse.mat.snapshot.SnapshotInfo;
import org.eclipse.mat.util.VoidProgressListener;

public class HeapDumpAnalizer {

	public HeapDumpAnalizer(){
		
	}
	
	
	public long getHeapSize(String dumpname){
		
		try {
			ISnapshot snapshot= SnapshotFactory.openSnapshot(new File(dumpname), new VoidProgressListener());
			SnapshotInfo snapshotInfo = snapshot.getSnapshotInfo();
            return snapshotInfo.getUsedHeapSize();
			
		} catch (SnapshotException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
		
	}
}
