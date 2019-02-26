package savvy.wit.framework.core.algorithm.model;


/**
 * 位运算练习
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
	// 乘以2
	public int mulTwo(int n){
		return n<<1;
	}
	// 除以2
	public int divTwo(int n){//负奇数不可用
		return n>>1;
	}
	// 乘以2的n次方
	public int mulTwoPower(int m,int n){
		return m<<n;
	}
	// 除以2的m次方
	public int divTwoPower(int m,int n){
		return m>>n;
	}
	// 判断奇偶性
	public boolean isOddNum(int n){
		return (n&1)==1;
	}
	// 交换两个数
	public int swap(int a,int b){
		a^=b;
		b^=a;
		a^=b;
		return max(a, b);
	}
	// 求绝对值
	public int abs1(int n){
		return (n^(n>>31))-(n>>31);
		/* n>>31 取得n的符号，若n为正数，n>>31等于0，若n为负数，n>>31等于-1 
		若n为正数 n^0=0,数不变，若n为负数有n^-1 需要计算n和-1的补码，然后进行异或运算， 
		结果n变号并且为n的绝对值减1，再减去-1就是绝对值 */   
	}
	public int abs2(int n){
		return n>0?n:-n;
	}
	// 两个数的最大值
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
	// 求最小值
	public int min(int a,int b){
		return a& ((a-b)>>31) | b& (~(a-b)>>31);
	}
	public int min1(int a,int b){
		return a>b?b:a;
	}
	// 判断两个数符号是否相同
	public boolean isSameSign(int a,int b){//有0的情况例外
		return (a^b)>=0; // true 表示 x和y有相同的符号， false表示x，y有相反的符号。
	}
	// 计算2的n次方
	public int getFactorialofTwo(int n){//n>0
		return 1<<n;
	}
	// 判断一个数是否是2的幂
	public boolean isFactorialofTwo(int n){
		return n>0? (n&(n-1))==0 : false; /*如果是2的幂，n一定是100... n-1就是1111.... 所以做与运算结果为0*/
	}
	// 取两个数的平均值
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
