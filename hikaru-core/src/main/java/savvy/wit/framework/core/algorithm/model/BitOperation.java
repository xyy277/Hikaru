package savvy.wit.framework.core.algorithm.model;


/**
 * λ������ϰ
 * @author Administrator
 *
 */
public class BitOperation {
	
	public int getMaxInt1(){
		return (1<<31)-1;
	}
	public int getMaxInt2(){
		return ~(1<<31);
	}
	public int getMaxInt3(){
		return (1<<-1)-1;
	}
	public long getMaxLong1(){
		return ~((long)1<<127);
	}
	public long getMaxLong2(){
		return ~((long)1<<-1);
	}
	// ����2
	public int mulTwo(int n){
		return n<<1;
	}
	// ����2
	public int divTwo(int n){//������������
		return n>>1;
	}
	// ����2��n�η�
	public int mulTwoPower(int m,int n){
		return m<<n;
	}
	// ����2��m�η�
	public int divTwoPower(int m,int n){
		return m>>n;
	}
	// �ж���ż��
	public boolean isOddNum(int n){
		return (n&1)==1;
	}
	// ����������
	public int swap(int a,int b){
		a^=b;
		b^=a;
		a^=b;
		return max(a, b);
	}
	// �����ֵ
	public int abs1(int n){
		return (n^(n>>31))-(n>>31);
		/* n>>31 ȡ��n�ķ��ţ���nΪ������n>>31����0����nΪ������n>>31����-1 
		��nΪ���� n^0=0,�����䣬��nΪ������n^-1 ��Ҫ����n��-1�Ĳ��룬Ȼ�����������㣬 
		���n��Ų���Ϊn�ľ���ֵ��1���ټ�ȥ-1���Ǿ���ֵ */   
	}
	public int abs2(int n){
		return n>0?n:-n;
	}
	// �����������ֵ
	public int max(int a,int b){
		return b & ((a-b)>>31) | a & (~(a-b)>>31);
	}
	public int max1(int a,int b){
		return a>b?a:b;
	}
	public int max2(int a,int b){
		if(a>b)return a;
		else return b;
	}
	// ����Сֵ
	public int min(int a,int b){
		return a& ((a-b)>>31) | b& (~(a-b)>>31);
	}
	public int min1(int a,int b){
		return a>b?b:a;
	}
	// �ж������������Ƿ���ͬ
	public boolean isSameSign(int a,int b){//��0���������
		return (a^b)>=0; // true ��ʾ x��y����ͬ�ķ��ţ� false��ʾx��y���෴�ķ��š�
	}
	// ����2��n�η�
	public int getFactorialofTwo(int n){//n>0
		return 1<<n;
	}
	// �ж�һ�����Ƿ���2����
	public boolean isFactorialofTwo(int n){
		return n>0? (n&(n-1))==0 : false; /*�����2���ݣ�nһ����100... n-1����1111.... ��������������Ϊ0*/
	}
	// ȡ��������ƽ��ֵ
	public int getAverage(int a,int b){
		return (a+b)>>1;
	}

	public static void main(String[] args) {
		BitOperation exercise=new BitOperation();
		System.out.println(exercise.getMaxInt2());
		System.out.println(exercise.getMaxLong2());
		System.out.println(exercise.mulTwo(10));
		System.out.println(exercise.divTwo(6));
		System.out.println(exercise.mulTwoPower(3, 2));
		System.out.println(exercise.divTwoPower(12, 2));
		System.out.println(exercise.isOddNum(3));
		System.out.println(exercise.swap(2, 3));
		System.out.println(exercise.abs1(-99));
		System.out.println(exercise.isFactorialofTwo(523612352));
		System.out.println(exercise.isSameSign(123, -12));
		System.out.println(exercise.getFactorialofTwo(3));
		System.out.println(exercise.getAverage(1235, 3312));
	}
}
