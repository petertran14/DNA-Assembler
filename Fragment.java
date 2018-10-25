/*
 * Copyright 2017 Marc Liberatore.
 */

package sequencer;

public class Fragment {
	
	//store the nucleotide sequence
	private String fragment;
	
	/**
	 * Creates a new Fragment based upon a String representing a sequence of nucleotides, 
	 * containing only the uppercase characters G, C, A and T.
	 * @param nucleotides
	 * @throws IllegalArgumentException if invalid characters are in the sequence of nucleotides 
	 */
	public Fragment(String nucleotides) throws IllegalArgumentException {
		
		//loop through the string nucleotides in search of invalid chars
		for (int i = 0; i < nucleotides.length(); i++) {
			
			//check for invalid characters in the sequence
			if ((nucleotides.charAt(i) != 'G') && (nucleotides.charAt(i) != 'C') 
				&& (nucleotides.charAt(i) != 'A') && (nucleotides.charAt(i) != 'T')) {
				
				//stop the normal flow of the program
				throw new IllegalArgumentException();
			}
		}
		
		//initialize our fragment
		this.fragment = nucleotides;
	}
	
	/**
	 * Returns the length of this fragment.
	 * 
	 * @return the length of this fragment
	 */
	public int length() {
		
		return fragment.length();
	}
	
	/**
	 * Returns a String representation of this fragment, exactly as was passed to the constructor.
	 * 
	 * @return a String representation of this fragment
	 */
	@Override
	public String toString() {
		
		return this.fragment;
	}
	
	/**
	 * Return true if and only if this fragment contains the same sequence of nucleotides
	 * as another sequence.
	 */
	@Override
	public boolean equals(Object obj) {
		
		//check to see if both obj point to the same address
		if (this == obj)
			return true;
		
		//if one of them is null
		if (obj == null)
			return false;
		
		//if they are not in the same class
		if (getClass() != obj.getClass())
			return false;
		Fragment other = (Fragment) obj;
		
		//if one nSequence is empty and the other has stuff in it
		if (fragment == null) {
			if (other.fragment != null)
				return false;
			
			//if they both have stuff, but different stuff
		} else if (!fragment.equals(other.fragment))
			return false;
		
		//they have identical stuff 
		return true;
	}
	
	/**
	 * Returns the number of nucleotides of overlap between the end of this fragment and
	 * the start of another fragment, f.
	 * 
	 * The largest overlap is found, for example, CAA and AAG have an overlap of 2, not 1.
	 * 
	 * @param f the other fragment
	 * @return the number of nucleotides of overlap
	 */
	
	/*
	 * startsWith(String prefix)
	 * 	- tests if this string starts with the specified prefix
	 * 	- returns boolean
	 * 
	 * startsWith(String prefix, int toffset)
	 * 	- tests if the substring of this string beginning at the specified
	 *    index starts with the specified prefix
	 *  - returns boolean 
	 * 
	 * endsWith(String suffix)
	 * 	- tests if this string ends with the specified suffix
	 * 	- returns boolean
	 * 
	 * substring(int beginIndex)
	 * 	- returns a string that is a substring of this string
	 * 	- returns string
	 * 
	 * substring(int beginIndex, int endIndex)
	 * 	- returns a string that is a substring of this string
	 * 	- returns string
	 */
	//this needs to determine which overlap is greater and return that
	public int calculateOverlap(Fragment f) {
		
		//first off, if they are identical skip all this and return
		if (this.equals(f)) {
			
			return this.fragment.length();
		}
		
		//the amount of overlap can never be greater than the shortest fragment
		int shortest;
		int overlap = 0;
		
		//the f fragment is shorter
		if (this.fragment.length() > f.fragment.length()) {
			
			shortest = f.fragment.length();
		}
		
		//our fragment is shorter
		else {
			
			shortest = this.fragment.length();
		}
		
		//loop through and determine how much overlap there is
		for (int i = shortest; i > 0; i--) {
			
			//there is an overlap 
			if (this.hasOverlap(f, i)) {
				
				//set overlap to hold the largest overlap
				if (i > overlap) {
					
					overlap = i;
				}
			}
		}
		
		//return
		return overlap;
	}
	
	/*
	 * Returns true or false depending if the current fragment overlaps with another
	 * fragment, f with an overlap of overlapLength
	 * 
	 * @param f the other fragment, overlapLength the length of overlap
	 * @return true if overlap and matches overlapLength, false otherwise
	 */
	
	//this just tests if an overlapLength between our two fragments exist
	public boolean hasOverlap(Fragment f, int overlapLength) {
		
		boolean overLap = false;
		
		//check if overlapLength passed in is greater than one of our fragments
		//we cannot have an overlapLength that is greater than any one of our fragments
		if (overlapLength > this.fragment.length() || overlapLength > f.fragment.length()) {
			
			return overLap;
		}
		
		//if overlapLength = 3, then the last 3 char of the this fragment
		//should match with the first 3 char of the f fragment. And vice versa.
		if (this.fragment.endsWith(f.fragment.substring(0, overlapLength))) {
			
			overLap = true;
		}
		
		return overLap;
	}
	
	/**
	 * Returns a new fragment based upon merging this fragment with another fragment f.
	 * 
	 * This fragment will be on the left, the other fragment will be on the right; the
	 * fragments will be overlapped as much as possible during the merge.
	 *  
	 * @param f the other fragment
	 * @return a new fragment based upon merging this fragment with another fragment 
	 */
	//returns an object of the class Fragment that holds a fragment instance variable
	//strings in java are immutable
	public Fragment mergedWith(Fragment f) {
		
		//get the length of the overlap
		int rightSideLength = this.calculateOverlap(f);
		
		//get the substring of what is left over using the rightSideLength as a
		//starting index
		String rightSideSequence = f.fragment.substring(rightSideLength);
		
		//new fragment to hold the merged sequences
		Fragment newFragment = new Fragment(this.fragment);
		
		//concatenate the left over from the right fragment
		newFragment.fragment = newFragment.fragment.concat(rightSideSequence);
		
		//return the new fragment
		return newFragment;
	}
	
	public void display() {
		
		System.out.println("Displaying Fragment: ");
		
		for (int i = 0; i < this.fragment.length(); i++) {
			
			System.out.print(this.fragment.charAt(i));
		}
	}
}
