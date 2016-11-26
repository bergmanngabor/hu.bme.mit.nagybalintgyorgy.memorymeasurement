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

	public HeapDumpAnalyzer(){
	}
	
	
	
	public long getHeapSize(String dumpname) {

		try {	
			ISnapshot snapshot = SnapshotFactory.openSnapshot(new File(dumpname), new VoidProgressListener());
			long size=0;
			VoidProgressListener vp= new VoidProgressListener();
			
			int[] ids= snapshot.getGCRoots();

			size= snapshot.getHeapSize(snapshot.getRetainedSet(ids, vp));
			return size;
			
		} catch (SnapshotException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;

	}
	


}
