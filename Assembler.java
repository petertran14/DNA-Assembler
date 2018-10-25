/*
 * Copyright 2017 Marc Liberatore.
 */

package sequencer;

import java.util.ArrayList;
import java.util.List;

public class Assembler {
	
	List<Fragment> copyList;

	/**
	 * Creates a new Assembler containing a list of fragments.
	 * 
	 * The list is copied into this assembler so that the original list will not
	 * be modified by the actions of this assembler.
	 * 
	 * @param fragments
	 */
	public Assembler(List<Fragment> fragments) {
		
		copyList = new ArrayList<Fragment>(fragments);
	}

	/**
	 * Returns the current list of fragments this assembler contains.
	 * 
	 * @return the current list of fragments
	 */
	public List<Fragment> getFragments() {
		
		return copyList; 
	}

	/**
	 * Attempts to perform a single assembly, returning true iff an assembly was
	 * performed.
	 * 
	 * This method chooses the best assembly possible, that is, it merges the
	 * two fragments with the largest overlap, breaking ties between merged
	 * fragments by choosing the shorter merged fragment.
	 * 
	 * Merges must have an overlap of at least 1.
	 * 
	 * After merging two fragments into a new fragment, the new fragment is
	 * inserted into the list of fragments in this assembler, and the two
	 * original fragments are removed from the list.
	 * 
	 * @return true iff an assembly was performed
	 */
	
	//loop through everything, record largest overlap, return true if so
	//if there was nothing to merge (unlikely), return false
	public boolean assembleOnce() {
		
		//keep track of largest overlap
		int largest = 0;
		
		//tells us if we will be able to perform an assembly
		boolean mergeSuccessfully = false;
		
		//save the merged fragments for insertion to the list
		Fragment mergeTest, mergeKeep = null;
		
		//store the merge length for comparison should we need to
		int mergeLength = 0;
		
		//store the indices of the fragments we need to remove
		int removeModel = 0, removeCompare = 0;
		
		//loop for model
		for (int model = 0; model < this.copyList.size(); model++) {
			
			//loop for compare
			for (int compare = 0; compare < this.copyList.size(); compare++) {
				
				//check if we are comparing the same element in the list
				//although, there could be identical fragments, though not the same element
				if (model != compare) {
					
					//check for overlapLength among the list of Fragments, keeping note of the largest
					//if there is no overlap, then control will never enter this conditional
					if (copyList.get(model).calculateOverlap(copyList.get(compare)) > largest) {
						
						//record largest overlapLength
						largest = copyList.get(model).calculateOverlap(copyList.get(compare));
						removeModel = model;
						removeCompare = compare;
						
						//get the merged sequence
						mergeKeep = copyList.get(model).mergedWith(copyList.get(compare));
						mergeLength = mergeKeep.toString().length();
						
						
						//we have found an overlap therefore we will be able to merge and do an assembly
						mergeSuccessfully = true;
					}
					
					//we are able to do an assembly but we have to choose between two pairs that have equal overlap length
					else if (copyList.get(model).calculateOverlap(copyList.get(compare)) == largest && mergeSuccessfully) {
						
						//handle matching overlapLength condition, get the merge length
						mergeTest = copyList.get(model).mergedWith(copyList.get(compare));
						
						//first time will always enter for the purpose of setting the length of the fragment once merged
						//the second time will only enter if the length of the merged fragment is shorter than the previous
						if (mergeTest.toString().length() < mergeLength) {
							
							//filter out the shortest merge strand and "keep" that one for later usage
							mergeLength = mergeTest.toString().length();
							
							mergeKeep = mergeTest;
							
							//store the indices of the pair of fragments to remove
							removeModel = model;
							removeCompare = compare;
						}
					}
				}
			}	
		}
		
		//get the merged fragment and insert it to the list while getting rid of the two fragments used to merge
		if (mergeSuccessfully) {
			
			copyList.remove(removeModel);
			
			//if compare was at index 0, then that will be the only time it is not shifted left
			if (removeCompare == 0) {
				
				copyList.remove(removeCompare);
			}
			
			//have to remember that remove shifts all the elements down one, so
			//we must  accommidate for that
			else {
				
				copyList.remove(removeCompare - 1);
			}
			
			copyList.add(mergeKeep);
		}
		 
		//should only return true is we made an assembly/found overlap
		return mergeSuccessfully;
	}

	/**
	 * Repeatedly assembles fragments until no more assembly can occur.
	 */
	public void assembleAll() {
		
		while (copyList.size() > 1) {
			
			this.assembleOnce();
		}
	}
}
