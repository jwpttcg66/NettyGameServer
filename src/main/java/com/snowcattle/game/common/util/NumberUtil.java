package com.snowcattle.game.common.util;


public class NumberUtil {
	
	public static long merge(int hi,int lo){
		long redoLong =0;
		long a1 = ((long)hi << 32) & 0xFFFFFFFF00000000L;
		long b1 = lo & 0x00000000FFFFFFFFL;
		redoLong = a1 | b1;
		return redoLong;
	}
	
	public static int cutHi(long v){
		return (int)(v>>32);
	}
	
	public static int cutLo(long v){
		v &= 0xFFFFFFFFL;
		return (int)v;
	}
	
	public static int distanceSquare(int x1,int y1,int x2,int y2){
		int xDiff = x1 - x2;
		int yDiff = y1 - y2;
		return xDiff*xDiff + yDiff*yDiff;
	}
	
	

	public static int parseInt( final String s )
	{
	    if ( s == null )
	        throw new NumberFormatException( "Null string" );

	    int num  = 0;
	    int sign = -1;
	    final int len  = s.length( );
	    final char ch  = s.charAt( 0 );
	    if ( ch == '-' )
	    {
	        if ( len == 1 )
	            throw new NumberFormatException( "Missing digits:  " + s );
	        sign = 1;
	    }
	    else
	    {
	        final int d = ch - '0';
	        if ( d < 0 || d > 9 )
	            throw new NumberFormatException( "Malformed:  " + s );
	        num = -d;
	    }

	    final int max = (sign == -1) ?
	        -Integer.MAX_VALUE : Integer.MIN_VALUE;
	    final int multmax = max / 10;
	    int i = 1;
	    while ( i < len )
	    {
	        int d = s.charAt(i++) - '0';
	        if ( d < 0 || d > 9 )
	            throw new NumberFormatException( "Malformed:  " + s );
	        if ( num < multmax )
	            throw new NumberFormatException( "Over/underflow:  " + s );
	        num *= 10;
	        if ( num < (max+d) )
	            throw new NumberFormatException( "Over/underflow:  " + s );
	        num -= d;
	    }

	    return sign * num;
	}
	

	public static int parseValidInt(final String s){
	    int num  = 0;
	    int sign = -1;
	    final int len  = s.length( );
	    final char ch  = s.charAt( 0 );
	    if ( ch == '-' )
	        sign = 1;
	    else
	        num = '0' - ch;

	    int i = 1;
	    while ( i < len )
	        num = num*10 + '0' - s.charAt( i++ );

	    return sign * num;

	}
	public static void main(String[] args){
//		Random rnd = new Random();
//		long t1 = System.nanoTime();
//		String s = "-23445";
//		for(int i=0;i<1000000;i++){
//			int j = Integer.parseInt(s);
//		}
//		long t2 = System.nanoTime();
//		for(int i=0;i<1000000;i++){
//			int j = parseInt(s);
//		}
//		long t3 = System.nanoTime();
//		for(int i=0;i<1000000;i++){
//			int j = parseValidInt(s);
//		}
//		long t4 = System.nanoTime();
//		System.out.printf("%s\n%s\n%s\n", (t2 - t1)/1000000L,(t3-t2)/1000000L,(t4-t3)/1000000L );
		System.out.println(67132441>>25);
	}
}
