package helloworld;

public class cisilka extends Number {
    protected int vnitrni_cislo;
	@Override
	public int intValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long longValue() {
		// TODO Auto-generated method stub
		System.out.println("zavolalo se longValue");
		vnitrni_cislo = 2;
		return 0;
	}

	@Override
	public float floatValue() {
		// TODO Auto-generated method stub
		System.out.println("zavolalo se floatValue");
		vnitrni_cislo = 6;
		return 0;
	}

	@Override
	public double doubleValue() {
		// TODO Auto-generated method stub
		System.out.println("zavolalo se doubleValue");
		return (double)vnitrni_cislo;
	}

}
