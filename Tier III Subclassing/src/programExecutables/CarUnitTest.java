package programExecutables;

import vehicles.Car;



public class CarUnitTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Car A370z = new Car();
		A370z.accelerate(42);
		System.out.println(A370z.isEngineBlown());
		System.out.println(A370z.getSpeed());
		System.out.println(A370z.getRPM());
		
		A370z.shiftUp();
		System.out.println(A370z.getSpeed());
		System.out.println(A370z.getRPM());
	}

}
