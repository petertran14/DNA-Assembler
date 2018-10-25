package sequencer;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AssemblyPersonalTest {

	public static void main(String[] args) {
		
//		List<Fragment> three = new ArrayList<Fragment>();
//		three.add(new Fragment("ACTGTG"));
//		three.add(new Fragment("ACACAC"));
//		three.add(new Fragment("TGTGGG"));
//		
//		for (int i = 0; i < three.size(); i++) {
//			
//			System.out.println(three.get(i));
//		}
//		
//		List<Fragment> copy = new ArrayList<Fragment>(three);
//		
//		for (int i = 0; i < three.size(); i++) {
//			
//			System.out.println(copy.get(i));
//		}
//		Assembler a = new Assembler(three);
//		a.assembleOnce();

		List<Fragment> l = 
				new ArrayList<Fragment>(Arrays.asList(
					new Fragment("GGGAAAC"),
					new Fragment("AAACGGG"),
					new Fragment("CCCGTTTA"),
					new Fragment("TTTAGCCC")));
		
		Assembler a = new Assembler(l);
		
		for (int i = 0; i < a.getFragments().size(); i++) {
			
			System.out.println(a.getFragments().get(i).toString());
		}
		
		a.assembleOnce();
		
		System.out.println();
		System.out.println("After: ");
		
		for (int i = 0; i < a.getFragments().size(); i++) {
			
			System.out.println(a.getFragments().get(i).toString());
		}
	}
}
