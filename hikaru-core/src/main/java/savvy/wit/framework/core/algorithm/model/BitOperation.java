package savvy.wit.framework.core.algorithm.model;


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
	public int mulTwo(int n){
		return n<<1;
	}
	public int divTwo(int n){
		return n>>1;
	}
	public int mulTwoPower(int m,int n){
		return m<<n;
	}
	public int divTwoPower(int m,int n){
		return m>>n;
	}
	public boolean isOddNum(int n){
		return (n&1)==1;
	}
	public int swap(int a,int b){
		a^=b;
		b^=a;
		a^=b;
		System.out.println(a);
		System.out.println(b);
		return max(a, b);
	}
	public int abs1(int n){
		return (n^(n>>31))-(n>>31);
	}
	public int abs2(int n){
		return n>0?n:-n;
	}
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
	public int min(int a,int b){
		return a& ((a-b)>>31) | b& (~(a-b)>>31);
	}
	public int min1(int a,int b){
		return a>b?b:a;
	}
	public boolean isSameSign(int a,int b) {//��0���������
		return (a ^ b) >= 0;
	}
	public int getFactorialofTwo(int n){//n>0
		return 1<<n;
	}
	public boolean isFactorialofTwo(int n){
		return n>0? (n&(n-1))==0 : false;
	}
	public int getAverage(int a,int b){
		return (a+b)>>1;
	}

	public static void main(String[] args) {
		BitOperation exercise=new BitOperation();
		System.out.println(exercise.swap(1,2));
	}
}
