package sequencer;

public class FragmentTester {
	
	public static void main(String[] args) {
		
		Fragment frag = new Fragment("GGAAC");
		Fragment test = new Fragment("AACGG");
		
		if (frag.hasOverlap(test, 1)) {
			
			System.out.println("We have an overlap.");
		}
		
		else {
			
			System.out.println("No overlap.");
		}
		
		System.out.println(frag.calculateOverlap(test));
		
		frag = frag.mergedWith(test);
		
		frag.display();
	}

}
